package Structures;

import java.util.ArrayList;

import ErrorHandle.Error;
import GarbageControl.MemoryManager;
import Structures.Meta.LineMeta;

public class Variable extends Struc {

	public String raw;
	public String lhs = "";
	public String rhs = "";
	public int lineNumber;
	public int address = -1;
	private char[] chars = new char[] {'=', '!', '<', '>', '(', ')', '+', '-'};


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
			for (String keyword: keywords) {
				if (lhs.equals(keyword)) {
					Error.namingError("Cannot create a variable whose LHS is a keyword", lineNumber);
					return false;
				}
			}
			for (char character: chars) {
				if (lhs.indexOf(character)) {
					Error.namingError("Cannot create a variable whose LHS is a keyword", lineNumber);
					return false;
				}
			}
			valid = sortOutRHS();
		} catch (NumberFormatException e) {
			valid = false;
		}

		if (valid) create(); // False = variable already exists, True = variable created
		return valid;
	}

	//BACKUP IN LOL TRASH
	@Override
	public boolean create() {
		boolean ret = MemoryManager.newVariable(lhs);
		address = MemoryManager.getAddress(lhs);

		return ret;
	}

	public boolean sortOutRHS() {

        ArrayList<String> result = new ArrayList<String>();

        String operandTemp = "";
        String operatorTemp = "";

        for (char crntChar : rhs.replace(" ", "").toCharArray()) {
            
            if (isOperator(crntChar)) {
                if (!operandTemp.equals("")) {
                    result.add(operandTemp);
                    operandTemp = "";
                }
                operatorTemp = operatorTemp + crntChar;
            } else {
                if (!operatorTemp.equals("")) {
                    result.add(operatorTemp);
                    operatorTemp = "";
                }
                operandTemp = operandTemp + crntChar;
            }
        }
        if (!operatorTemp.equals("")){
			Error.syntaxError("Invalid RHS of variable", lineNumber);
			return false;
		}

		result.add(operandTemp);
        return true;
    }


	public boolean isOperator(char term) {
        for (char symbol : chars) 
            if (symbol == term) return true;
        return false;
    }
}