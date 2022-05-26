package Structures.Functions;

import Structures.Meta.Condition;
import Syntax.codeControl;

public class ifFunc extends builtinFunctionality {


    public ifFunc(int open, Condition condition){
        super(open, condition);
    }

    @Override
    public void closeHandle(int close){
        super.closeHandle(close);
        preBlock.add(codeControl.unconditionalBranch(close));
    }

    @Override
    public boolean generateCondition(){
        if(!funcCondition.parse(starting))
            return false;
        preBlock = funcCondition.instructions;
        return true;
    }

    

}