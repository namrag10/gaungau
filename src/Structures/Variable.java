package Structures;

import Syntax.Syntax;

public class Variable extends struct implements Syntax {

    public String raw;
    public String lhs = "";
    public String rhs = "";
    public int lineNumber;

    public Variable(Line rawStatement){
        raw = rawStatement.line;
        lineNumber = rawStatement.lineNumber;
    }

    @Override
    public boolean execute(){
        return parse();
    }

    public boolean parse(){
        try{
            rhs = raw.substring(raw.indexOf(variableOperand) +2);
            lhs = raw.substring(0, raw.indexOf(variableOperand));
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }


}
