package Structures;

import java.util.Stack;

public class Function extends struct {

    public String raw;
    public String condition = "";
    public Stack<struct> block = new Stack<struct>();
    // private struct type;

    public Function(String rawStatement){
        hasChild = true;
        raw = rawStatement;
    }

    @Override
    public boolean execute(){
        condition = "done";
        for (struct structure : block) {
            structure.execute();    
        }
        // determine if the function is standard or a custom function
        // Set private type to an instance of either class
        return true;
    }

    public void addStatement(struct line){
        block.add(line);
    }
}
