package mainProcesses;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import Structures.StandardFunction;
import Structures.Line;
import Structures.Variable;
import Structures.struct;
import Syntax.Syntax;
import subProcesses.Parser;

public class Tokenise implements Syntax {
	
	final int variable = 1;
	final int function = 2;

	private String[] keywords = new String[]{"if", "while", "else"};
	private String[] operands = new String[]{"==", "!=", "<", ">", "<=", ">="};
	private String[] syntax = new String[]{"{", "}", "(", ")"};
	private String[] operators = new String[]{"+", "-"};
	private String[][] test = new String[][]{keywords, operands, syntax, operands};
	private ArrayList<struct> topCommands = new ArrayList<struct>();
	
	Stack<StandardFunction> functionOpens = new Stack<StandardFunction>();

	public Tokenise(String rawSource){
		Queue<Line> statements = Parser.parse(rawSource);				// Removes comments
	

		while (statements.size() > 0) { // Loops through each statement (;)

			Line line = statements.poll();
			String statementText = line.line;


			System.out.println(statementText);


			boolean valid = false;
			if (statementText.indexOf(variableOperand) > -1) {
				if(functionOpens.size() > 0){
					functionOpens.peek().addStatement(new Variable(line));
				}else
					topCommands.add(new Variable(line));
				System.out.println("Create a variable!");
				valid = true;
			} else if (statementText.indexOf("}") > -1) {
					if(functionOpens.size() > 0){
						functionOpens.pop();
					}else{
						System.out.println("Error, cannot close an unopened thing");
					}
					valid = true;
			}else{
				for (String keyword: keywords) 
					if (statementText.indexOf(keyword + "(") > -1) {
						if(functionOpens.size() == 0){
							topCommands.add(new StandardFunction(statementText));
							functionOpens.push((StandardFunction) topCommands.get(topCommands.size() -1));
						}else{
							functionOpens.peek().addStatement(new StandardFunction(statementText));
							functionOpens.push((StandardFunction) functionOpens.peek().block.peek());	
						}

						System.out.println("This is a function");
						valid = true;
						break;
					}
			}
				

			if (!valid) {
				System.out.println("SYNTAX ERROR: " + statementText + "\nAt line: " + line.lineNumber);
				return;
			}
		}
	}

	public Queue<struct> getCode(){
		return new LinkedList<struct>(topCommands);
	}
	
}