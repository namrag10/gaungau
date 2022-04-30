package GarbageControl;

import java.util.HashMap;

// Doesnt yet handle overflows i.e. addresses over 255
public class FunctionManager {
    
    private static int memoryHolder = 0;
    

    private static HashMap<String, Integer> functions = new HashMap<String, Integer>();

    public static int has(String funcName){
        if(functions.containsKey(funcName)){
            return functions.get(funcName);
        }
        return -1;
    }

    public static boolean newFunction(String name){
        if(functions.containsKey(name))
            return false;
        
        functions.put(name, memoryHolder);

        memoryHolder++;
        return true;
        
    }

    public static Integer getAddress(String varName){
        return functions.get(varName);
    }
}
