package Structures.Meta;

import ErrorHandle.Error;
import GarbageControl.MemoryManager;
import Structures.Struc;
import Syntax.codeControl;
public class Condition extends Struc {

    private String raw;
    private String condition = "";
    private int ILine = 0;
    

    // x < y === x - y === if carries (BRC <skip BRA>) then allow, else skip (BRA <end of block>)
    // 2 - 1 (FALSE)

    // LDA x
    // SUB y
    // BRC <crnt + 2>
    // BRA <Skip block>

    public Condition(String rawStatement, int lineNo, int ILine){
        raw = rawStatement;
        lineNumber = lineNo;
        this.ILine = ILine;
    }

    public boolean parse(){
        condition = raw.substring(1, raw.indexOf(")"));
        tokenise();


        String lhs = "";
        String rhs = "";
        String operand = "";
        for (String token : tokens) {
            if(lhs.equals("") && rhs.equals("") && operand.equals("")){
                if(!isOperand(token)){
                    lhs = token;
                    continue;
                } Error.syntaxError("inside condition #1", lineNumber);
            }

            if(!lhs.equals("") && rhs.equals("") && operand.equals("")){
                if(isOperand(token)){
                    operand = token;
                    continue;
                }else Error.syntaxError("inside condition #2", lineNumber);
            }

            if(!lhs.equals("") && rhs.equals("") && !operand.equals("")){
                if(!isOperand(token)){
                    rhs = token;
                    continue;
                } Error.syntaxError("inside condition #4", lineNumber);
            }

        }

        if(!lhs.equals("") && !rhs.equals("") && !operand.equals("")){
            switch(operand){
                case "<":
                    instructions.add(codeControl.load(lhs));
                    instructions.add(codeControl.sub(rhs));
                    instructions.add(codeControl.carryBranch(ILine + 4));
                    break;
                default:
                    Error.syntaxError("unsupported operand in condition", lineNumber);
                    return false;
            }
        }

        return true;
    }


    public boolean tokenise() {
		if(condition.indexOf(variableOperand) > -1) {
			Error.syntaxError("Cannot include multiple defining syntax on one line!", lineNumber);
			return false;
		}

        String operandTemp = "";
        String otherTemp = "";

		for (char t : condition.toCharArray()) {
			if (isOperandSingleChar(t + "")) {
                operandTemp += t;
				if (!otherTemp.equals("")) {
					tokens.add(otherTemp);
					otherTemp = "";
				}
			}else{
                otherTemp += t;
                if (!operandTemp.equals("")) {
					tokens.add(operandTemp);
					operandTemp = "";
				}
            }
		}
		tokens.add(otherTemp);
		return relateVariables();
	}



    private boolean relateVariables() {
		for (int i = 0; i < tokens.size(); i++) {

			String value = tokens.get(i);

			int addr = -2;
			if (!value.toUpperCase().equals(value.toLowerCase())) {
				addr = MemoryManager.has(value);
				if (addr == -1) {
					Error.syntaxError("Variable: " + value + " does not exist", lineNumber);
					return false;
				}
				tokens.set(i, variableID + addr + "");
			}
		}
		return true;
	}

    public boolean isOperandSingleChar(String term) {
		for (String symbol: operandSingle)
			if (symbol.equals(term)) return true;
		return false;
	}

    public boolean isOperand(String term) {
		for (String symbol: operands)
			if (symbol.equals(term)) return true;
		return false;
	}

}
