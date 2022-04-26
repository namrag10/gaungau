package Structures;

import java.util.Stack;

public class StandardFunction extends struct {

    public String raw;
    public String condition = "";
    public Stack<struct> block = new Stack<struct>();

    public StandardFunction(String rawStatement){
        raw = rawStatement;
    }

    @Override
    public boolean execute(){
        
        return true;
    }

    public void addStatement(struct line){
        block.add(line);
    }
}
