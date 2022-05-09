package Structures.Functions;

import Structures.Meta.Condition;
import Syntax.codeControl;

public class ifFunc extends FunctionWorkings {



    public ifFunc(int open, Condition condition){
        super(open, condition);
        preBlock.add("Open If at: " + starting);
    }

    @Override
    public void closeHandle(int close){
        preBlock.add(codeControl.unconditionalBranch(close));
    }

    @Override
    public boolean generateCondition(){
        if(!funcCondition.parse(starting))
            return false;
        preBlock = funcCondition.instructions;
        return true;
    }

    @Override
    public int preInstructionCount(){
        if(closed)
            return preBlock.size();
        return preBlock.size() +1;
    }

}