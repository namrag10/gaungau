package Structures.Functions;

import java.util.Stack;

import Structures.Struc;
import Structures.Meta.Condition;
import Structures.Meta.LineMeta;

public class Function extends Struc {

    public Condition condition;
    public Stack<Struc> block = new Stack<Struc>();
    private Object functionality;
    public String raw;
    public int lineNumber;
    // private struct type;

    public Function(LineMeta rawStatement){
        raw = rawStatement.lineText;
        condition = new Condition(extractCondition());
        lineNumber = rawStatement.lineNumber;
    }

    @Override
    public boolean parse(){
        
        switch(getType()){
            case "if":
                functionality = new ifFunc();
                break;
            case "while":
                functionality = new whileFunc();
                break;
            default:
                functionality = new CustomFunction();
                System.out.println(raw + " NEW FUNCTION - noted by not implemented");
                break;
        }

        // Discover structures in block
        for (Struc structure : block)  
            if(!structure.parse())
                return false;
        return true;
    }

    public void addStatement(Struc line){
        block.add(line);
    }

    private String extractCondition(){
        return raw.substring(raw.indexOf("("));
    }

    private String getType(){
        return raw.substring(0, raw.indexOf("("));
    }
}
