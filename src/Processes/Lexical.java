package Processes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import Structures.Block;

public class Lexical {
	
	private Queue<Block> lexStatements = new LinkedList<Block>();
	private int amountOfBlocks = 0;

	public Lexical(String rawSource){
		rawSource = removeComents(rawSource);
		//Lexical analysis
		Queue<String>rawStatements = new LinkedList<String>(Arrays.asList(rawSource.split(";")));
		String statement = rawStatements.poll();

		while(rawStatements.size() > 0){ 									// Loops through each statement (;)
			Queue<String> crntBlock = new LinkedList<String>();
		
			do{																// Cleans the beginning of the script (before any code blocks)
				crntBlock.add(statement);
				statement = rawStatements.poll();
			}while(rawStatements.size() > 1 && statement.indexOf("{") < 0); // While statment doesnt contain a {
			lexStatements.add(new Block(crntBlock, "true"));
			
			Stack<String> Conditions = new Stack<String>();
			Stack<Queue<String>> openBlocks = new Stack<Queue<String>>();
			Queue<String> openBlock = new LinkedList<String>();

			crntBlock.clear();

			Stack<Block> blockTree = new Stack<Block>();
			do{
				if(statement.indexOf("{") > -1){
					openBlock = new LinkedList<String>();
					Conditions.push(statement.substring(0, statement.indexOf("{")));
					openBlocks.push(openBlock);

					openBlock.add(statement.substring(statement.indexOf("{") +1));
				}else if(statement.indexOf("}") > -1){
					for(int i = 0; i < amountChars(statement, '}');i++)
						blockTree.push(new Block(openBlocks.pop(), Conditions.pop()));

				}else{
					openBlock.add(statement);
				}
				statement = rawStatements.poll();
				
			}while(statement != null);

			Block parentBlock = blockTree.pop();
			while(blockTree.size() > 0)
				parentBlock.addChild(blockTree.pop());
			lexStatements.add(parentBlock);
			
		}
		amountOfBlocks = lexStatements.size();
	}

	public Queue<Block> getLex(){
		return lexStatements;
	}


	private int amountChars(String inpString, char inpChar){

		int totalCharacters = 0;
		char temp;
		for (int i = 0; i < inpString.length(); i++) {
			temp = inpString.charAt(i);
			if (temp == inpChar)
				totalCharacters++;
		}
		return totalCharacters;
	}

	private String removeComents(String statements){
		statements = statements.replace(" ", "");

		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(statements.split("\n")));

		for(int i = 0; i < lines.size(); i++){
			String line = lines.get(i);
			int index = line.indexOf("//");
			if(index == 0){
				lines.remove(i);
				i = -1;
			}else if(index > -1)
				lines.set(i, line.substring(0, index));
		}
		statements = String.join("", lines);

		return statements;
	}

	public int amountBlocks(){
		return amountOfBlocks;
	}

	public Block getBlock(){
		return (lexStatements.size() > 0) ? lexStatements.remove() : null;	
	}
}
