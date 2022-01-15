import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import Structures.Block;

public class mainStack {
	
	
	Queue<Block> lexStatements = new LinkedList<Block>();

	public mainStack(String rawSource){
		rawSource = rawSource.replace(" ", "");

		//Lexical analysis
		Queue<String>rawStatements = new LinkedList<String>(Arrays.asList(rawSource.split(";")));
		String statement = rawStatements.poll();

		while(rawStatements.size() > 0){ 									// Loops through each statement (;)
			Queue<String> crntBlock = new LinkedList<String>();
		
			do{	// Cleans the beginning of the script (before any code blocks)
				crntBlock.add(statement);
				statement = rawStatements.poll();
			}while(rawStatements.size() > 1 && statement.indexOf("{") < 0); // While statment doesnt contain a {
			lexStatements.add(new Block(crntBlock, "true"));
			
			// Found first open bracket

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
		print(lexStatements.toString());
	}

	public String toString(){
		System.out.println("\n");
		return lexStatements.toString();
	}

	public <T> void print(T item){
		System.out.println(item);
	}

	public int amountChars(String inpString, char inpChar){

		int totalCharacters = 0;
		char temp;
		for (int i = 0; i < inpString.length(); i++) {
			temp = inpString.charAt(i);
			if (temp == inpChar)
				totalCharacters++;
		}
		return totalCharacters;
	}
}
