package Structures;

import ErrorHandle.Error;
import GarbageControl.MemoryManager;
import Structures.Meta.LineMeta;

public class Variable extends Struc {

    public String raw;
    public String lhs = "";
    public String rhs = "";
    public int lineNumber;

    public Variable(LineMeta rawStatement) {
        raw = rawStatement.lineText;
        lineNumber = rawStatement.lineNumber;
    }

    @Override
    public boolean parse() {
        boolean valid = false;
        try {
            rhs = raw.substring(raw.indexOf(variableOperand) + 2);
            lhs = raw.substring(0, raw.indexOf(variableOperand));
            for (String keyword : keywords) {
                if (lhs.equals(keyword)) {
                    Error.namingError("Cannot create a variable whose LHS is a keyword", lineNumber);
                    return false;
                }
            }
            valid = true;
        } catch (NumberFormatException e) {
            valid = false;
        }

        if (valid) return create();
        return false;
    }

    public boolean create() {
        return MemoryManager.newVariable(lhs);
        
    }
}