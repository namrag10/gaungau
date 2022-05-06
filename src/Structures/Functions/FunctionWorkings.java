package Structures.Functions;

import java.util.ArrayList;

import Structures.Meta.Condition;

public class FunctionWorkings {
    public ArrayList<String> preBlock = new ArrayList<String>();
    public ArrayList<String> postBlock = new ArrayList<String>();
    Condition funcCondition;
    protected boolean closed = false;

    protected int starting;
    protected int closing;

    public FunctionWorkings(int open, Condition condish){
        starting = open;
        funcCondition = condish;
    }

    public void closeHandle(int close){
        closing = close;
        closed = true;
    }

    public boolean generateCondition(){
        return true;
    }

    public int preInstructionCount(){
        return preBlock.size();
    }
}
