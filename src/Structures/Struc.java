package Structures;

import java.util.ArrayList;

import Syntax.SyntaxCfg;

public class Struc implements SyntaxCfg {
    
    public int lineNumber;
    protected String raw;
    protected int instructionsInBlock = 0;

    public ArrayList < String > instructions = new ArrayList < String > ();
    protected ArrayList < String > tokens = new ArrayList < String > ();

    public boolean parse(int crntILine) {
        return false;
    }

    public boolean create(){
        return false;
    }

    // Gets the amount of instructions added after a parse
    public int instructionCount(){
        return instructions.size() + instructionsInBlock;
    }

    public ArrayList<String> buildAndGetInstructions(){
        return instructions;
    }
    
    public boolean isOperator(String term) {
		for (String symbol: operators)
			if (symbol.equals(term)) return true;
		return false;
	}

    public String extractOperands(String name) {
		int index = 0;
		String[] crntChars = name.split("");
		for (String chr: crntChars)
			for (String vChr: operators)
				if (vChr.equals(chr))
					index++;
		return name.substring(0, index);
	}

	public String extractAfterOperands(String name) {
		int index = 0;
		String[] crntChars = name.split("");
		for (String chr: crntChars)
			for (String vChr: operators)
				if (vChr.equals(chr))
					index++;
		return name.substring(index);
	}

	public String getType(){
		if(raw.indexOf("(") > -1)
        	return raw.substring(0, raw.indexOf("("));
		return raw.substring(0, raw.indexOf("{"));
    }

}