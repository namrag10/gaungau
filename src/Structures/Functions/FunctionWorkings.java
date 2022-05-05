package Structures.Functions;

import java.util.ArrayList;

import Structures.Meta.Condition;

public class FunctionWorkings {
    public ArrayList<String> preBlock = new ArrayList<String>();
    public ArrayList<String> postBlock = new ArrayList<String>();

    protected int starting;
    protected int closing;

    public FunctionWorkings(int open, Condition condish){
        starting = open;
    }

    public void closeHandle(int close){
        closing = close;
    }

    public boolean generateCondition(){
        return true;
    }
}
