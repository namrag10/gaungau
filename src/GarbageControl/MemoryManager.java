package GarbageControl;

import java.util.HashMap;


public class MemoryManager {
    
    private static int memoryHolder = 0;
    

    private static HashMap<String, Integer> variables = new HashMap<String, Integer>();

    

    public static boolean newVariable(String name){ // 0 = memory address, 1 = value
        if(variables.containsKey(name))
            return false;
        
        variables.put(name, memoryHolder);

        memoryHolder++;
        return true;
        
    }

    public static Integer getAddress(String varName){
        return variables.get(varName);
    }
}
