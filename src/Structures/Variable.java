package Structures;

import Syntax.Syntax;

public class Variable extends struct implements Syntax {

    public String raw;
    public String name = "";
    public int value = -1;
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
            value = Integer.parseInt(raw.substring(raw.indexOf(variableOperand) +2));
            name = raw.substring(0, raw.indexOf(variableOperand));
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }


}
