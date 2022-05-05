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

	Stack < Function > functionOpens = new Stack < Function > ();

	public boolean errors = false;

	public SyntaxAnalysis(String rawSource) {
		Queue < LineMeta > statements = Parser.parse(rawSource); // Removes comments


		while (statements.size() > 0) { // Loops through each statement (;)

			LineMeta lineMeta = statements.poll();
			String statementText = lineMeta.lineText;


			boolean lineValid = false;
			if (statementText.indexOf(variableOperand) > -1) { // Identifies a variable based on the presence of ':='
				if (functionOpens.size() > 0) {
					functionOpens.peek().addStatement(new Variable(lineMeta));
				} else
					topCommands.add(new Variable(lineMeta));
				lineValid = true;
			} else if (statementText.indexOf("}") > -1) { // Identifies a close bracket to end a block
				if (functionOpens.size() > 0) {
					functionOpens.pop();
				} else {
					Error.syntaxError("Stray '}' on line: ", lineMeta.lineNumber);
					setError();
					return;
				}
				lineValid = true;
			} else { // Whatever is left is checked against keywords and a 'function' is created for it
				
				Boolean funcId = (statementText.indexOf("()") > -1);
				for (int i = 0; i < keywords.length || funcId;i++)
					if (statementText.indexOf(keywords[i]) > -1 || funcId) {
						if (functionOpens.size() == 0) {
							topCommands.add(new Function(lineMeta));
							functionOpens.push((Function) topCommands.get(topCommands.size() - 1));
						} else {
							functionOpens.peek().addStatement(new Function(lineMeta));
							functionOpens.push((Function) functionOpens.peek().block.peek());
						}
						lineValid = true;
						break;
					}
			}


			if (!lineValid) {
				Error.syntaxError(statementText, lineMeta.lineNumber);
				setError();
			}
		}

		if(functionOpens.size() > 0){
			for (Function openFunc : functionOpens) {
				Error.syntaxError("Missing '}' for function", openFunc.lineNumber);
			}
			setError();
		}

		if (!errors) {
			for (int i = 0; i < topCommands.size(); i++){ // All the code at this point makes sense, but not necesserily correct
				if(!setError(!topCommands.get(i).parse(instructionCount))){

					instructionCount += topCommands.get(i).instructionCount();
				}
				if(errors) break;
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