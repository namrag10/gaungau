package Structures.Functions;

import java.util.ArrayList;

import Structures.Meta.Condition;

public class builtinFunctionality {
    protected ArrayList<String> preBlock = new ArrayList<String>();
    protected ArrayList<String> postBlock = new ArrayList<String>();
    public Condition funcCondition;
    protected boolean closed = false;

    protected int starting;
    protected int closingILine;

    public builtinFunctionality(int open, Condition condition){
        starting = open;
        funcCondition = condition;
    }

    public void closeHandle(int IClose){
        closingILine = IClose;
        closed = true;
    }

    public boolean generateCondition(){
        return true;
    }

    public int preInstructionCount(){
        if(closed)
            return preBlock.size();
        return preBlock.size() +1;
    }
}
