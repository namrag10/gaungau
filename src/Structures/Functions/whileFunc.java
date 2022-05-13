package Structures.Functions;

import Structures.Meta.Condition;
import Syntax.codeControl;

public class whileFunc extends FunctionWorkings {

    public whileFunc(int openAt, Condition condition) {
        super(openAt, condition);
    }

    @Override
    public void closeHandle(int close){
        super.closeHandle(close);
        preBlock.add(codeControl.unconditionalBranch(closing +1));
        postBlock.add(codeControl.unconditionalBranch(starting));
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