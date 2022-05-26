package Structures.Functions;

import java.util.ArrayList;
import java.util.Stack;

import ErrorHandle.Error;
import Structures.Struc;
import Structures.Meta.Condition;
import Structures.Meta.LineMeta;

public class Function extends Struc {


	public Condition condition;
	public Stack<Struc> block = new Stack<Struc>();
	private builtinFunctionality functionality;
	private int startingInstrucLine = 0;
	public boolean hasElse = false;
	public elseStatement elseStatement = null;
	// private struct type;

	public Function(LineMeta rawStatement){
		raw = rawStatement.lineText;
		lineNumber = rawStatement.lineNumber;
	}
	
	@Override
	public boolean parse(int crntILine){
		startingInstrucLine = crntILine;
		condition = new Condition(extractCondition(), lineNumber);
		boolean requiresCondition = true;

		switch(getType()){
			case "if":
				functionality = new ifFunc(startingInstrucLine, condition);
				break;
			case "while":
				functionality = new whileFunc(startingInstrucLine, condition);
				break;
			case "exit":
				functionality = new exitFunc(startingInstrucLine, condition);
				break;
			case "else":
				requiresCondition = false; 
				functionality = new elseStatement(startingInstrucLine);
				break;
			default:
				functionality = new CustomFunction(startingInstrucLine, condition);
				System.out.println(raw + " NEW FUNCTION - noted by not implemented");
				break;
		}

		// Sort out the condition
		if(requiresCondition){
			if(condition.getRaw() == null){
				Error.syntaxError("No condition found", lineNumber);
				return false;
			}

			if(!functionality.generateCondition())
				return false;
		}
		crntILine += functionality.preInstructionCount();

		// Discover structures in block
		// Hot recursion going on here
		for (Struc structure : block) {
			if(!structure.parse(crntILine))
				return false;
			crntILine += structure.instructionCount();
		}

		functionality.closeHandle(crntILine);
		crntILine += functionality.postBlock.size();
		instructionsInBlock += crntILine - startingInstrucLine;
		return true;
	}

	// Adds a statement to the block
	public void addStatement(Struc line){
		block.add(line);
	}

	private String extractCondition(){
		int bracketIndex = raw.indexOf("(");
		return (bracketIndex > -1) ? raw.substring(bracketIndex) : null;
	}

	public void setElse(){
		hasElse = true;
	}


	// Sexy polymorphism going on here
	@Override
	public ArrayList<String> buildAndGetInstructions(){
		
		for (String string : functionality.preBlock) 
			instructions.add(string);

		for (Struc structure : block)
			for (String string : structure.buildAndGetInstructions()) {
				instructions.add(string);
			}
		
		for (String string : functionality.postBlock) 
			instructions.add(string);
		
		return instructions;
	}
}
