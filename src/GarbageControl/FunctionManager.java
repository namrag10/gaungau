package GarbageControl;

import java.util.HashMap;

// Doesnt yet handle overflows i.e. addresses over 255
public class FunctionManager {
    
    

    private static HashMap<String, Integer> functions = new HashMap<String, Integer>();

    public static int has(String funcName){
        if(functions.containsKey(funcName)){
            return functions.get(funcName);
        }
        return -1;
    }

    public static boolean newFunction(String name, int ILineOpen){
        if(functions.containsKey(name))
            return false;
        
        functions.put(name, ILineOpen);

        return true;
        
    }

    public static int getILine(String funcName){
        return functions.get(funcName);
    }
}
