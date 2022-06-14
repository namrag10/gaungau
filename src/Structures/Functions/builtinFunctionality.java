package Structures.Functions;

import java.util.ArrayList;


import Structures.Meta.Condition;

public class builtinFunctionality {
    protected ArrayList<String> preBlock = new ArrayList<String>();
    protected ArrayList<String> postBlock = new ArrayList<String>();
    public Condition funcCondition;
    protected boolean closed = false;
    protected boolean elseAttached = false;
    public boolean validUse = true;

    protected int IStarting;
    protected int closingILine;

    public builtinFunctionality(int IOpen, Condition condition, int Line, boolean hasElse){
        this(IOpen, condition, Line);
        elseAttached = hasElse;
    }

    public builtinFunctionality(int IOpen, Condition condition, int Line){
        IStarting = IOpen;
        funcCondition = condition;
    }

    public void closeHandle(int IClose){
        closingILine = IClose;
        closed = true;
    }

    public boolean generateCondition(){
        if(!funcCondition.parse(IStarting))
            return false;
        preBlock = funcCondition.instructions;
        return true;
    }

    public int preInstructionCount(){
        return preBlock.size();
    }
}
