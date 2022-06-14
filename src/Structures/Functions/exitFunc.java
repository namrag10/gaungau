package Structures.Functions;

import Structures.Meta.Condition;
import Syntax.codeControl;

public class exitFunc extends builtinFunctionality {

    public exitFunc(int openAt, Condition condition) {
        super(openAt, condition, -1);
        preBlock.add(codeControl.halt());
    }
}