package Structures.Functions;

import Structures.Meta.Condition;
import Syntax.codeControl;

public class whileFunc extends builtinFunctionality {

    public whileFunc(int IOpen, Condition condition) {
        super(IOpen, condition, -1);
    }

    @Override
    public void closeHandle(int closeILine){
        super.closeHandle(closeILine);
        preBlock.add(codeControl.unconditionalBranch(closingILine + 1));
        postBlock.add(codeControl.unconditionalBranch(IStarting));
    }    

    @Override
    public int preInstructionCount(){
        if(closed)
            return preBlock.size();
        return preBlock.size() +1;
    }
}