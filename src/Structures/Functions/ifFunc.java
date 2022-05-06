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
        preBlock.add(codeControl.unconditionalBranch(close +1));
    }

    @Override
    public boolean generateCondition(){
        funcCondition.parse();
        preBlock = funcCondition.instructions;
        return true;
    }

}
