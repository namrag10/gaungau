package Structures.Functions;

import Structures.Meta.Condition;
import Syntax.codeControl;

public class ifFunc extends builtinFunctionality {


    public ifFunc(int IOpen, Condition condition, boolean hasElse){
        super(IOpen, condition, -1, hasElse);
    }

    @Override
    public void closeHandle(int close){
        super.closeHandle(close);
        if(elseAttached)
            close++;
        preBlock.add(codeControl.unconditionalBranch(close));
    }

    @Override
    public int preInstructionCount(){
        if(closed)
            return preBlock.size();
        return preBlock.size() +1;
    }
}