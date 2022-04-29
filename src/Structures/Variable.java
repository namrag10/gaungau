package Structures;

import java.util.ArrayList;

import ErrorHandle.Error;
import GarbageControl.MemoryManager;
import Structures.Meta.LineMeta;
import Syntax.codeControl;

public class Variable extends Struc {

	private final String variableID = "&";

	public String raw;
	public String lhs = "";
	public String rhs = "";
	public int lineNumber;
	public int address = -1;

	private ArrayList<String> tokens = new ArrayList<String>();
	public ArrayList<String> instructions = new ArrayList<String>();
	
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
			// Variable name validation
			for (String keyword: keywords) {
				if (lhs.equals(keyword)) {
					Error.namingError("Cannot create a variable whose LHS is a keyword", lineNumber);
					return false;
				}
			}
			for (String character: validChars) {
				if (lhs.indexOf(character) > 0) {
					Error.namingError("Cannot create a variable whose LHS contains an operator or operand", lineNumber);
					return false;
				}
			}
			// RHS validation
			if(parseRHS())
				valid = build();
				
		} catch (NumberFormatException e) {
			valid = false;
		}

		if (valid) create(); // False = variable already exists, True = variable created

		
		instructions.add(codeControl.saveVariable(lhs)); // Store Instruction

		return valid;
	}

	@Override
	public boolean create() {
		boolean ret = MemoryManager.newVariable(lhs);
		lhs = MemoryManager.getAddress(lhs) + "";

		return ret;
	}

	public boolean parseRHS() {
        String temp = "";
        for (char t : rhs.toCharArray()) {

            if (isOperator(t + "")) {
                if (!(temp.equals("")) && !(isOperator(temp.substring(temp.length() - 1)))) {
                    tokens.add(temp);
                    temp = "";
                }
            }
            temp += t;
        }
        tokens.add(temp);
		relateVariables();
        return true;
    }

	public boolean build(){
		String firstTerm = tokens.remove(0);

		// Loading the first term
		if(firstTerm.substring(0, 1).equals(variableID)){
			instructions.add(
				codeControl.loadFromPointer(
					firstTerm.substring(1))
			);
		}else{
			instructions.add(
				codeControl.loadLiteral(
					firstTerm)
			);
		}

		// Add subsequent terms
		for (String token : tokens) {
			System.out.println(token);

			switch(token.substring(0, 1)){
				case variableID:
					instructions.add(
						codeControl.loadFromPointer(token.substring(1)));
					break;
				case "+":
					instructions.add(
					 	codeControl.addLiteral(token.substring(1))
					);
					break;
				case "-":
					instructions.add(
						codeControl.subLiteral(token.substring(1))
			   		);
					break;
			}
		}

		return true;
	}

    public static boolean isOperator(String term) {
        for (String symbol : validChars) {
            if (symbol.equals(term)) return true;
        }
        return false;
    }

	private void relateVariables(){
		for (int i = 0; i < tokens.size(); i++) {

            String crnt = tokens.get(i);
			int index = 0;
			String[] crntChars = crnt.split("");
			for (String c : crntChars) 
				for (String d : validChars)
					if(d.equals(c))
						index++;
					
            int addr = MemoryManager.has(crnt.substring(index));
            if(addr > -1)
                tokens.set(i, crnt.substring(0, index) + variableID + addr);
        }
	}
}