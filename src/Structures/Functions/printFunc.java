package Structures.Functions;

import Structures.Meta.Condition;
import Syntax.codeControl;

public class printFunc extends builtinFunctionality {

    public printFunc(int openAt, Condition condition) {
        super(openAt, condition);
        System.out.println(condition.getRaw());
        preBlock.add(codeControl.print());
    }
}