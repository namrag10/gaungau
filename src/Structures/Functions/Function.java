package Structures.Functions;

import java.util.ArrayList;
import java.util.Stack;

import ErrorHandle.Error;
import Structures.Meta.Condition;
import Structures.Meta.LineMeta;
import Structures.Meta.Struc;

public class Function extends Struc {


	public Condition condition;
	public Stack < Struc > block = new Stack < Struc > ();
	private builtinFunctionality functionality;
	private int startingILine = 0;
	// private struct type;

	public Function(LineMeta rawStatement) {
		raw = rawStatement.lineText;
		lineNumber = rawStatement.lineNumber;
	}

	@Override
	public boolean parse(int crntILine) {
		startingILine = crntILine;
		condition = new Condition(extractCondition(), lineNumber);
		boolean requiresCondition = true;

		switch (getType()) {
			case "if":
				functionality = new ifFunc(startingILine, condition, hasElse);
				break;
			case "while":
				functionality = new whileFunc(startingILine, condition);
				break;
			case "exit":
				requiresCondition = false;
				functionality = new exitFunc(startingILine, condition);
				break;
			case "print":
				requiresCondition = false;
				functionality = new printFunc(startingILine, condition);
				break;
			case "else":
				requiresCondition = false;
				functionality = new elseStatement(startingILine);
				break;
			case "NOOPENBRACKET":
				Error.syntaxError("No open bracket on condition", lineNumber);
				return false;
			default:
				requiresCondition = false;
				functionality = new customFunction(startingILine, condition);
				System.out.println(raw + " NEW FUNCTION - noted but not implemented");
				break;
		}

		// Sort out the condition if needed
		if (requiresCondition) {
			if (condition.getRaw() == null) {
				Error.syntaxError("No condition found", lineNumber);
				return false;
			}

			if (!functionality.generateCondition())
				return false;
		}
		crntILine += functionality.preInstructionCount();

		// Discover structures in block
		// Hot recursion going on here
		for (Struc structure: block) {
			if (!structure.parse(crntILine))
				return false;
			crntILine += structure.instructionCount();
		}

		functionality.closeHandle(crntILine);
		crntILine += functionality.postBlock.size();
		instructionsInBlock += crntILine - startingILine;
		return true;
	}

	// Adds a statement to the block
	public void addStatement(Struc line) {
		block.add(line);
	}

	private String extractCondition() {
		int bracketIndex = raw.indexOf("(");
		return (bracketIndex > -1) ? raw.substring(bracketIndex) : null;
	}


	// Sexy polymorphism going on here
	@Override
	public ArrayList < String > buildAndGetInstructions() {

		for (String string: functionality.preBlock)
			instructions.add(string);


		for (Struc structure: block)
			for (String string: structure.buildAndGetInstructions())
				instructions.add(string);


		for (String string: functionality.postBlock)
			instructions.add(string);

		return instructions;
	}
}