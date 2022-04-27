package Structures.Functions;

import java.util.Stack;

import Structures.Struc;
import Structures.Meta.Condition;

public class Function extends Struc {

    public Condition condition;
    public Stack<Struc> block = new Stack<Struc>();
    // private struct type;

    public Function(String rawStatement){
        raw = rawStatement;
    }

    @Override
    public boolean parse(){
        



        // Discover structures in block
        for (Struc structure : block)  
            if(!structure.parse())
                return false;
        return true;
    }

    public void addStatement(Struc line){
        block.add(line);
    }
}
