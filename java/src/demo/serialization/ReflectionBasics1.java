package demo.serialization;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class shows how reflection can be used to call a static method. The static method being called is the "getProperty"
 * method of the class java.lang.System.  Note that at no point do we reference the class or method directly.
 */
@SuppressWarnings("unchecked")
public class ReflectionBasics1 {
    public static void main(String[] args) {
        try {
            Class callerClass = Class.forName("java.lang.System");
            Class[] classes = {String.class};
            Object[] params = {"os.name"};
            Method method = callerClass.getMethod("getProperty", classes);
            Object result = method.invoke(callerClass, params);
            System.out.println("" + result.getClass());
            System.out.println("" + result);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
