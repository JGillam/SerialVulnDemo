package demo.serialization;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class DemoInvoker implements Serializable{
    static final long serialVersionUID = 42L;
    private List invokerChain;
    private static final Class[] emptyParamTypes = {};
    private static final Object[] emptyParams = {};
    private transient String executed = "no";

    public DemoInvoker(List invokerChain){
        this.invokerChain = invokerChain;
    }

    public void executeChain(){
        System.out.println("executeChain() called.");
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
                        System.out.println(method.getName() + "called...");
                    } // else handle construction of new object (not needed for this demo since we start with static method).
                } else {
                    Class callerClass = subject.getClass();
                    Method method = callerClass.getMethod((String) map.get("method"), paramTypes);
                    subject = method.invoke(subject, params);
                    // TODO: Handle a Void return type
                }
            }
            executed = "yes";
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            executed = sw.toString();
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
        System.out.println("readObject called.");
        in.defaultReadObject();
        this.executeChain();
    }

    public List getInvokerChain() {
        return invokerChain;
    }

    @Override
    public String toString() {
        return getInvokerChain().toString() + " <br>Executed: <br><pre>"+executed+"</pre>";
    }


    public static void main(String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("--help")) {
            System.out.println("USAGE: java demo.serialization.DemoInvoker <OS> \"<COMMAND>\"");
            System.exit(0);
        }


        Map first = new HashMap();
        first.put("class", "java.lang.Thread");
        first.put("method", "currentThread");

        Map second = new HashMap();
        second.put("method", "sleep");
        second.put("paramTypes", new Class[]{Long.TYPE});

        second.put("params", new Object[]{5000});


        List invokeChain = new ArrayList();
        invokeChain.add(first);
        invokeChain.add(second);

        DemoInvoker demo = new DemoInvoker(invokeChain);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream( baos );
            oos.writeObject( demo );
            oos.close();
            String encodedTicket = Base64.getEncoder().encodeToString(baos.toByteArray());
            System.out.println("Serialized DemoInvoker: "+encodedTicket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

