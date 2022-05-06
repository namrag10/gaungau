package Structures.Functions;

import Structures.Meta.Condition;
import Syntax.codeControl;

public class whileFunc extends FunctionWorkings {

    public whileFunc(int openAt, Condition condition) {
        super(openAt, condition);
        preBlock.add("Open while at line: " + starting + "\n");
    }

    @Override
    public void closeHandle(int close){
        postBlock.add(codeControl.unconditionalBranch(starting));
    }
    
}