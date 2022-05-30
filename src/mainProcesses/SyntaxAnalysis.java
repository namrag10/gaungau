package mainProcesses;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import ErrorHandle.Error;
import GarbageControl.FunctionManager;
import Structures.*;
import Structures.Functions.Function;
import Structures.Meta.LineMeta;
import Syntax.SyntaxCfg;
import subProcesses.Parser;

public class SyntaxAnalysis implements SyntaxCfg {


	private int instructionCount = 0;

	private ArrayList < Struc > topCommands = new ArrayList < Struc > ();
	private Stack < Function > openFunctions = new Stack < Function > ();

	private boolean errors = false;


	public SyntaxAnalysis(String rawSource) {
		Queue < LineMeta > statements = Parser.parse(rawSource); // Removes comments
		boolean fromFunction = false;

		while (statements.size() > 0) { // Loops through each statement (;)

			LineMeta lineMeta = statements.poll();
			String statementText = lineMeta.lineText;


			boolean lineValid = true;

			// ======= ELSE STATEMENT ======= \\
			if (funcWordStartEquals(statementText, "else")) {
				if (topCommands.size() > 0 && !topCommands.get(topCommands.size() - 1).getType().equals("if")) {
					Error.syntaxError("Invalid placment of else statement", lineMeta.lineNumber);
					setError();
					return;
				} else
					topCommands.get(topCommands.size() - 1).setElse();
			}


			// ======= VARIABLE ======= \\
			if (statementText.indexOf(variableOperand) > -1) { // Identifies a variable based on the presence of ':='
				if (openFunctions.size() > 0) {
					openFuncAddStatement(new Variable(lineMeta), fromFunction);
				} else
					topCommands.add(new Variable(lineMeta));


				// ======= OPEN BLOCK ======== \\
			} else if(statementText.indexOf("{") > -1){
				if(fromFunction){
					fromFunction = false;
				}else{
					Error.syntaxError("Stray '{'", lineMeta.lineNumber);
					setError();
				}
			

				// ======= CLOSE BLOCK ======= \\
			} else if (statementText.indexOf("}") > -1) { // Identifies a close bracket to end a block
				if (openFunctions.size() > 0) {
					openFunctions.pop();
				} else {
					Error.syntaxError("Stray '}'", lineMeta.lineNumber);
					setError();
				}


				// ======= POSSIBLE FUNCTION ======= \\
			} else { // Whatever is left is checked against keywords and a 'function' is created for it

				// ======= CHECKS FOR KEYWORDS ======= \\
				int i = 0;
				for (i = 0; i < keywords.length; i++) // Checks against keywords
					if (funcWordStartEquals(statementText, keywords[i])) {
						if (openFunctions.size() == 0) {
							topCommands.add(new Function(lineMeta));
							openFunctions.push((Function) topCommands.get(topCommands.size() - 1));
						} else {
							Function newFunc = new Function(lineMeta);
							openFuncAddStatement(newFunc, fromFunction);
							openFunctions.push((Function) newFunc);
						}
						fromFunction = true;
						break;
					}

				// line has NO keyword in it
				if (i == keywords.length) { // The for loop condition failed, and no break was triggered (reaches end of condition)
					int funcIdLoc = statementText.indexOf(functionIdentifier);
					if (funcIdLoc > -1) {

						for (i = 0; i < callables.length; i++)
							if(funcWordStartEquals(statementText, callables[i]))
								break;

						if (i < callables.length || FunctionManager.has(statementText)) {
							if (openFunctions.size() == 0) {
								topCommands.add(new Function(lineMeta));								
							} else {
								openFuncAddStatement(new Function(lineMeta), fromFunction);
							}
						}else{
							if (openFunctions.size() == 0) {
								topCommands.add(new Function(lineMeta));
								openFunctions.push((Function) topCommands.get(topCommands.size() - 1));
								fromFunction = true;
								// FunctionManager.newFunction()
							} else {
								Error.syntaxError("Cannot create a function within another function", lineMeta.lineNumber);
								setError();
								return;
							}
						}
					} else {
						lineValid = false;
					}
				}
			}

			// ===== Catch an Error for this line ===== \\
			if (!lineValid) {
				Error.syntaxError("'" + statementText + "'", lineMeta.lineNumber);
				setError();
			}
		}

		// ===== Ensures all functions have a close bracket ===== \\
		if (openFunctions.size() > 0) {
			for (Function openFunc: openFunctions) {
				Error.syntaxError("Missing '}' for function", openFunc.lineNumber);
			}
			setError();
		}

		// ===== Parsing ===== \\
		if (!errors) {
			// All the code at this point makes sense, but not necesserily correct \\
			for (int i = 0; i < topCommands.size(); i++) {
				if (!setError(!topCommands.get(i).parse(instructionCount)))
					instructionCount += topCommands.get(i).instructionCount();

				if (errors) break;
			}
		}
	}

	private boolean funcWordStartEquals(String raw, String key){
		int openIndex = raw.indexOf("(");
		if(openIndex == -1){
			setError();
			return false;
		} 
		return (raw.substring(0, openIndex).equals(key));
	}

	private void openFuncAddStatement(Struc newStatement, boolean fromFunction){
		openFunctions.peek().addStatement(newStatement);
		if(fromFunction)
			openFunctions.pop();
	}

	public Struc popStructure() {
		return topCommands.remove(0);
	}


	public ArrayList < Struc > getFullCode() {
		return topCommands;
	}

	public void setError() {
		errors = true;
	}
	public boolean setError(boolean state) {
		if (state) errors = true;
		return errors;
	}

	public boolean hasError() {
		return errors;
	}

	public ArrayList < Struc > getCommands() {
		return topCommands;
	}

}