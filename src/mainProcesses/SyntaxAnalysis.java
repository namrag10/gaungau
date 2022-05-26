package mainProcesses;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import ErrorHandle.Error;
import Structures.*;
import Structures.Functions.Function;
import Structures.Meta.LineMeta;
import Syntax.SyntaxCfg;
import subProcesses.Parser;

public class SyntaxAnalysis implements SyntaxCfg {


	private int instructionCount = 0;

	private ArrayList < Struc > topCommands = new ArrayList < Struc > ();

	Stack < Function > openFunctions = new Stack < Function > ();

	public boolean errors = false;

	public SyntaxAnalysis(String rawSource) {
		Queue < LineMeta > statements = Parser.parse(rawSource); // Removes comments


		while (statements.size() > 0) { // Loops through each statement (;)

			LineMeta lineMeta = statements.poll();
			String statementText = lineMeta.lineText;


			boolean lineValid = true;
			// ======= VARIABLE ======= \\
			if (statementText.indexOf(variableOperand) > -1) { // Identifies a variable based on the presence of ':='
				if (openFunctions.size() > 0) {
					openFunctions.peek().addStatement(new Variable(lineMeta));
				} else
					topCommands.add(new Variable(lineMeta));

			// ======= CLOSE FUNCTION ======= \\
			} else if (statementText.indexOf("}") > -1) { // Identifies a close bracket to end a block
				if (openFunctions.size() > 0) {
					openFunctions.pop();
				} else {
					Error.syntaxError("Stray '}' on line: ", lineMeta.lineNumber);
					setError();
				}

			// ======= ELSE STATEMENT ======= \\
			}else if (statementText.indexOf("else") > -1){
				if(topCommands.size() > 0 && !topCommands.get(topCommands.size() -1).getType().equals("if")){
					Error.syntaxError("Invalid placment of else statement", lineMeta.lineNumber);
					setError();
					return;					
				}
			// ======= POSSIBLE FUNCTION ======= \\
			} else { // Whatever is left is checked against keywords and a 'function' is created for it
			 
				// ======= CHECKS FOR KEYWORDS ======= \\
				// todo - should not be able to create function while inside another block
				int i = 0;
				for (i = 0; i < keywords.length; i++) // Checks against keywords
					if (statementText.indexOf(keywords[i]) > -1) {
						if (openFunctions.size() == 0) {
							topCommands.add(new Function(lineMeta));
							openFunctions.push((Function) topCommands.get(topCommands.size() - 1));
						} else {
							openFunctions.peek().addStatement(new Function(lineMeta));
							openFunctions.push((Function) openFunctions.peek().block.peek());
						}
						break;
					}

				// line has NO keyword in it
				if (i == keywords.length) { // The for loop condition failed, and no break was triggered (reaches end of condition)
					Boolean hasFuncId = (statementText.indexOf(functionIdentifier) > -1);
					if (hasFuncId) {
						if (openFunctions.size() == 0) {
							topCommands.add(new Function(lineMeta));
							openFunctions.push((Function) topCommands.get(topCommands.size() - 1));
						} else{
							Error.syntaxError("Cannot create a function within another function", lineMeta.lineNumber);
							setError();
							return;
						}
					} else {
						lineValid = false;
					}
				}
			}


			if (!lineValid) {
				Error.syntaxError("'" + statementText + "'", lineMeta.lineNumber);
				setError();
			}
		}

		if (openFunctions.size() > 0) {
			for (Function openFunc: openFunctions) {
				Error.syntaxError("Missing '}' for function", openFunc.lineNumber);
			}
			setError();
		}

		// Parsing
		if (!errors) {
			for (int i = 0; i < topCommands.size(); i++) { // All the code at this point makes sense, but not necesserily correct
				if (!setError(!topCommands.get(i).parse(instructionCount))) {
					instructionCount += topCommands.get(i).instructionCount();
				}
				if (errors) break;
			}
		}
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

}