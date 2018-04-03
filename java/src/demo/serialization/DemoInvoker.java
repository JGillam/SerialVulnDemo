package demo.serialization;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class DemoInvoker implements Serializable{
    static final long serialVersionUID = 42L;
    private List invokerChain;
    private static final Class[] emptyParamTypes = {};
    private static final Object[] emptyParams = {};

    public DemoInvoker(List invokerChain){
        this.invokerChain = invokerChain;
    }

    public void executeChain(){
        Object subject = null;
        try {
            for (Object chainLink: invokerChain) {
                Map map = (Map)chainLink;

                Class[] paramTypes;
                if(map.containsKey("paramTypes")){
                    paramTypes = (Class[]) map.get("paramTypes");
                } else {
                    paramTypes = emptyParamTypes;
                }
                Object[] params;
                if(map.containsKey("params")){
                    params = (Object[]) map.get("params");
                }else{
                    params = emptyParams;
                }
                if (subject == null) {
                    Class callerClass = Class.forName((String) map.get("class"));
                    Method method = callerClass.getMethod((String) map.get("method"), paramTypes);
                    if (Modifier.isStatic(method.getModifiers())) {
                        subject = method.invoke(callerClass, params);

                    } // else handle construction of new object (not needed for this demo since we start with static method).
                } else {
                    Class callerClass = subject.getClass();
                    Method method = callerClass.getMethod((String) map.get("method"), paramTypes);
                    subject = method.invoke(subject, params);
                    // TODO: Handle a Void return type
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        this.executeChain();
    }



//    private final Object caller;
//    private final String iMethodName;
//    private final Class<?>[] iParamTypes;
//    private final Object[] iArgs;

    public static void main(String[] args) {
        Map first = new HashMap();
        first.put("class", "java.lang.Runtime");
        first.put("method", "getRuntime");

        Map second = new HashMap();
        second.put("method", "exec");
        second.put("paramTypes", new Class[]{String.class});
        if(System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            second.put("params", new Object[]{new String("cmd.exe /c dir > output.txt")});
        }else{
            second.put("params", new Object[]{new String("ls > output.txt")});
        }

        List invokeChain = new ArrayList();
        invokeChain.add(first);
        invokeChain.add(second);

        DemoInvoker demo = new DemoInvoker(invokeChain);
        demo.executeChain();
    }
}

