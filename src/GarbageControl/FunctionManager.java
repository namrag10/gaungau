package GarbageControl;

import java.util.HashMap;

// Doesnt yet handle overflows i.e. addresses over 255
public class FunctionManager {
    
    

    private static HashMap<String, Integer> functions = new HashMap<String, Integer>();

    public static boolean has(String funcName){
        if(functions.containsKey(funcName)){
            return true;
        }
        return false;
    }

    public static boolean newFunction(String name){
        if(functions.containsKey(name))
            return false;
        
        functions.put(name, -1);

        return true;
        
    }

    public static void updateFunction(String name, int ILineOpen){
        functions.replace(name, ILineOpen);
    }

    public static int getILine(String funcName){
        return functions.get(funcName);
    }
}
