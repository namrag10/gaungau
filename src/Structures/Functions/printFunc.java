package Structures.Functions;

import GarbageControl.MemoryManager;
import Structures.Meta.Condition;
import Syntax.SyntaxCfg;
import Syntax.codeControl;

public class printFunc extends builtinFunctionality {

    private String parameter = "";

    public printFunc(int openAt, Condition condition) {
        super(openAt, condition);
        parameter = getValue(condition.getRaw());
        
        int address = MemoryManager.has(parameter);
        if(address > -1)
            preBlock.add(codeControl.load(SyntaxCfg.variableID + Integer.toString(address)));
            
        preBlock.add(codeControl.print());
        
    }

    private String getValue(String raw){
        String ret = raw.substring(0, raw.length() -1);
        return ret.substring(1);
    }
}