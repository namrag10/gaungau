package Structures;

import ErrorHandle.Error;
import GarbageControl.MemoryManager;
import Structures.Meta.LineMeta;
import Syntax.codeControl;

public class Variable extends Struc {

	public String raw;
	public String lhs = "";
	public String rhs;
	public int lineNumber;
	public int address = -1;
	

	public Variable(LineMeta rawStatement) {
		raw = rawStatement.lineText;
		lineNumber = rawStatement.lineNumber;
	}

	// Main method
	@Override
	public boolean parse(int fromLine) {
		boolean valid = false;
		try {
			// Gets the left and right basic strings based on variable identifier
			rhs = raw.substring(raw.indexOf(variableOperand) + 2);
			lhs = raw.substring(0, raw.indexOf(variableOperand));
			if (lhs.length() == 0) {
				Error.namingError("No name", lineNumber);
				return false;
			}
			// Variable name validation
			for (String keyword: keywords) {
				if (lhs.equals(keyword)) {
					Error.namingError("Cannot create a variable whose LHS is a keyword", lineNumber);
					return false;
				}
			}
			for (String character: operators) {
				if (lhs.indexOf(character) > -1) {
					Error.namingError("Cannot create a variable whose LHS contains an operator or operand", lineNumber);
					return false;
				}
			}
			// RHS into like terms
			if (parseRHS())
				// like terms to asm
				valid = build();

		} catch (NumberFormatException e) {
			valid = false;
		}

		// Vaariable is now valid and ready to be registered
		if (valid) {
			create(); // False = variable already exists, True = variable created
			instructions.add(codeControl.saveVariable(lhs)); // Store Instruction
		}

		return valid;
	}

	// Registers the variable name with memory management to get address
	@Override
	public boolean create() {
		boolean ret = MemoryManager.newVariable(lhs);
		lhs = MemoryManager.getAddress(lhs) + "";

		return ret;
	}

	// Collects the like terms
	public boolean parseRHS() {
		if(rhs.indexOf(variableOperand) > -1) {
			Error.syntaxError("Cannot include multiple defining syntax on one line!", lineNumber);
			return false;
		}
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
		return relateVariables();
	}

	// Builds the assembly code based off like terms
	public boolean build() {
		String firstTerm = tokens.remove(0);
		if (firstTerm.length() == 0) {
			Error.syntaxError("No value set for varible", lineNumber);
			return false;
		}

		// Loading the first term
		instructions.add(
			codeControl.load(
				firstTerm)
		);
		

		// Add subsequent terms
		for (String token: tokens) {
			// System.out.println(token);

			switch (extractOperands(token)) {
				case "+":
					instructions.add(
						codeControl.add(extractAfterOperands(token))
					);
					break;
				case "-":
					instructions.add(
						codeControl.sub(extractAfterOperands(token))
					);
					break;
				default:
					Error.syntaxError("RHS of variable invalid", lineNumber);
					return false;
			}
		}

		return true;
	}

	public boolean isPointer(String name) {
		return (name.indexOf(variableID) > -1);
	}

	public boolean isOperator(String term) {
		for (String symbol: operators)
			if (symbol.equals(term)) return true;
		return false;
	}

	// Translates variable names to address values
	private boolean relateVariables() {
		for (int i = 0; i < tokens.size(); i++) {

			String value = extractAfterOperands(tokens.get(i));

			int addr = -2;
			if (!value.toUpperCase().equals(value.toLowerCase())) {
				addr = MemoryManager.has(value);
				if (addr == -1) {
					Error.syntaxError("Variable: " + value + " does not exist", lineNumber);
					return false;
				}
				tokens.set(i, extractOperands(tokens.get(i)) + variableID + addr);
			}
		}
		return true;
	}



	private String extractOperands(String name) {
		int index = 0;
		String[] crntChars = name.split("");
		for (String chr: crntChars)
			for (String vChr: operators)
				if (vChr.equals(chr))
					index++;
		return name.substring(0, index);
	}

	private String extractAfterOperands(String name) {
		int index = 0;
		String[] crntChars = name.split("");
		for (String chr: crntChars)
			for (String vChr: operators)
				if (vChr.equals(chr))
					index++;
		return name.substring(index);
	}
}