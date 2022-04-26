package GarbageControl;

import java.util.ArrayList;
import java.util.HashMap;


public class MemoryManager {
    
    private static int memoryHolder = 0;

    private static HashMap<String, ArrayList<Integer>> variables = new HashMap<String, ArrayList<Integer>>();

    

    public static ArrayList<Integer> newVariable(String name, int value){ // 0 = memory address, 1 = value
        variables.put(name, new ArrayList<Integer>());
        variables.get(name).add(memoryHolder);
        variables.get(name).add(value);
        memoryHolder++;
        return variables.get(name);
    }

    public static Integer getAddress(String varName){
        return variables.get(varName).get(0);
    }
    public static Integer getValue(String varName){
        return variables.get(varName).get(1);
    }

    public static ArrayList<String> listVariables(){
        ArrayList<String> keys = new ArrayList<String>();
        for (String key : variables.keySet())
            keys.add(key);
        
        return keys;
    }
}
