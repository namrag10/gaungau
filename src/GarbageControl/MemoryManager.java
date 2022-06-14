package GarbageControl;

import java.util.HashMap;

public class MemoryManager {
    
    private static int memoryHolder = 0;
    private static boolean full = false;

    private static HashMap<String, Integer> variables = new HashMap<String, Integer>();

    public static int has(String varName){
        if(variables.containsKey(varName))
            return variables.get(varName);
        
        return -1;
    }

    public static boolean newVariable(String name){ // 0 = memory address, 1 = value
        if(variables.containsKey(name) || full)
            return false;
        
        variables.put(name, memoryHolder);

        memoryHolder++;
        full = memoryHolder >= 255;
        
        return true;
        
    }

    public static Integer getAddress(String varName){
        return variables.get(varName);
    }
}
