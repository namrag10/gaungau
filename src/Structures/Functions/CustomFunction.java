package Structures.Functions;

import Structures.Meta.Condition;

// This is not yet implemnted on the hardware
public class customFunction extends builtinFunctionality {

    public customFunction(int open, Condition condition, int Line) {
        super(open, condition, Line);
    }

    public boolean parse(int fromLine) {
        return true;
    }
}