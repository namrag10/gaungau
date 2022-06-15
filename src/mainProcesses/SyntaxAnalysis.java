package mainProcesses;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

import ErrorHandle.Error;
import GarbageControl.FunctionManager;
import Structures.*;
import Structures.Functions.Function;
import Structures.Meta.LineMeta;
import Structures.Meta.Struc;
import Syntax.SyntaxCfg;

public class SyntaxAnalysis implements main {


	private int instructionCount = 0;

	private ArrayList < Struc > topCommands = new ArrayList < Struc > ();
	private Stack < Function > openFunctions = new Stack < Function > ();

	private boolean errors = false;
	private boolean fromFunction = false;
	private Function lastClosed = null;

	public SyntaxAnalysis(LexicalAnalysis lex) {
		Queue < LineMeta > statements = lex.getStatements(); // Removes comments

		while (statements.size() > 0) { // Loops through each statement (;)

			LineMeta lineMeta = statements.poll();
			String statementText = lineMeta.lineText;



			// ======= ELSE STATEMENT ======= \\
			if (funcWordStartEquals(statementText, "else")) {
				if (lastClosed != null && lastClosed.getType().equals("if")) {
				
					lastClosed.setElse();
						
				} else {
					Error.syntaxError("Invalid placment of else statement", lineMeta.lineNumber);
					setError();
				}
			}


			// ======= VARIABLE ======= \\
			if (statementText.indexOf(SyntaxCfg.variableOperand) > -1) { // Identifies a variable based on the presence of ':='
				if (openFunctions.size() > 0) {
					openFuncAddStatement(new Variable(lineMeta));
				} else
					addTopCommand(new Variable(lineMeta));


				// ======= OPEN BLOCK ======== \\
			} else if (statementText.indexOf("{") > -1) {
				if (fromFunction) {
					fromFunction = false;
				} else {
					Error.syntaxError("Stray '{'", lineMeta.lineNumber);
					setError();
				}


				// ======= CLOSE BLOCK ======= \\
			} else if (statementText.indexOf("}") > -1) { // Identifies a close bracket to end a block
				if (openFunctions.size() > 0) {
					lastClosed = openFunctions.pop();
				} else {
					Error.syntaxError("Stray '}'", lineMeta.lineNumber);
					setError();
				}

			} else if (FunctionManager.has(statementText)) {
				System.out.println("FUNCTION CALL AT " + lineMeta.lineNumber + " - cannot call at present, but noted here!");


				// ======= POSSIBLE FUNCTION ======= \\
			} else { // Whatever is left is checked against keywords and a 'function' is created for it

				// ======= CHECKS FOR KEYWORDS ======= \\
				int i = 0;
				for (i = 0; i < SyntaxCfg.keywords.length; i++) // Checks against keywords
					if (funcWordStartEquals(statementText, SyntaxCfg.keywords[i])) {
						if (openFunctions.size() == 0) {
							addTopCommand(new Function(lineMeta));
							openFunctions.push((Function) topCommands.get(topCommands.size() - 1));
						} else {
							Function newFunc = new Function(lineMeta);
							openFuncAddStatement(newFunc);
							openFunctions.push((Function) newFunc);
						}
						fromFunction = true;
						break;
					}

				// line has NO keyword in it
				if (i == SyntaxCfg.keywords.length) { // The for loop condition failed, and no break was triggered (reaches end of condition)

					if (statementText.indexOf("(") > -1) {
						// ======= CHECKS FOR BUILT IN FUNCTIONS ======= \\
						for (i = 0; i < SyntaxCfg.callables.length; i++)
							if (funcWordStartEquals(statementText, SyntaxCfg.callables[i]))
								break;


						if (i < SyntaxCfg.callables.length) {
							// ======= IS A BUILT IN ======= \\
							if (openFunctions.size() == 0) {
								addTopCommand(new Function(lineMeta));
							} else {
								openFuncAddStatement(new Function(lineMeta));
							}


							// ======= POSSIBLE NEW FUNCTION ======= \\
						} else {
							Error.syntaxError("Cannot create or call custom methods yet sorry", lineMeta.lineNumber);
							setError();
						}
					} else {

						Error.syntaxError("'" + statementText + "'", lineMeta.lineNumber);
						setError();

					}
				}
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


	private boolean funcWordStartEquals(String raw, String key) {
		int openIndex = raw.indexOf("(");
		if (openIndex == -1)
			return (raw.equals(key));;

		return (raw.substring(0, openIndex).equals(key));
	}

	private void openFuncAddStatement(Struc newStatement) {
		openFunctions.peek().addStatement(newStatement);
		if (fromFunction) {
			lastClosed = openFunctions.pop();
			fromFunction = false;
		}
	}

	private void addTopCommand(Struc command){
		topCommands.add(command);
		lastClosed = null;

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

	public output view() {
		return new output(topCommands);
	}

}