package Structures.Functions;

import Structures.Meta.Condition;
import Syntax.codeControl;

public class exitFunc extends builtinFunctionality {

    public exitFunc(int openAt, Condition condition) {
        super(openAt, condition);
        preBlock.add(codeControl.halt());
    }
}