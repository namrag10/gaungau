package Structures.Functions;

import Structures.Meta.Condition;
import Syntax.codeControl;

public class whileFunc extends builtinFunctionality {

    public whileFunc(int openAt, Condition condition) {
        super(openAt, condition);
    }

    @Override
    public void closeHandle(int closeILine){
        super.closeHandle(closeILine);
        preBlock.add(codeControl.unconditionalBranch(closingILine +1));
        postBlock.add(codeControl.unconditionalBranch(starting));
    }

    @Override
    public boolean generateCondition(){
        if(!funcCondition.parse(starting))
            return false;
        preBlock = funcCondition.instructions;
        return true;
    }

    
}