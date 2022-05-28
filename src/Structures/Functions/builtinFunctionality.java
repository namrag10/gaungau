package Structures.Functions;

import java.util.ArrayList;

import Structures.Meta.Condition;

public class builtinFunctionality {
    protected ArrayList<String> preBlock = new ArrayList<String>();
    protected ArrayList<String> postBlock = new ArrayList<String>();
    public Condition funcCondition;
    protected boolean closed = false;
    protected boolean elseAttached = false;

    protected int IStarting;
    protected int closingILine;

    public builtinFunctionality(int IOpen, Condition condition, boolean hasElse){
        this(IOpen, condition);
        elseAttached = hasElse;
    }

    public builtinFunctionality(int IOpen, Condition condition){
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
