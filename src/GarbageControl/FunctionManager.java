package GarbageControl;

import java.util.HashMap;

// TODO Doesnt yet handle overflows i.e. addresses over 255
public class FunctionManager {

    
    // Primary hold for function names and associated instruction lines \\
    private static HashMap<String, Integer> functions = new HashMap<String, Integer>();

    // Checks to see if a function name exists \\
    public static boolean has(String funcName){
        if(functions.containsKey(funcName)){
            return true;
        }
        return false;
    }

    // Registers a new function with a blank instruction line store \\
    public static boolean newFunction(String name){
        if(functions.containsKey(name))
            return false;
        
        functions.put(name, -1);

        return true;
        
    }

    // After registering the function, the intruction line is now set \\
    public static void updateFunction(String name, int ILineOpen){
        functions.replace(name, ILineOpen);
    }

    // Gets the Intruction line of a given function name \\
    public static int getILine(String funcName){
        return functions.get(funcName);
    }
}
