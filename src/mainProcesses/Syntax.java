package mainProcesses;

import java.util.LinkedList;
import java.util.Queue;

import Structures.Block;
import Structures.Instruction;

public class Syntax {
	
	private Queue<Instruction> Lines = new LinkedList<Instruction>();

	public Syntax(Lexical codeBase){
		
		while(codeBase.hasBlocks()){
			Block block = codeBase.getBlock();
			parseBlock(block);
			
				
			while(block.hasChild())
				parseBlock(block.getChild());


			
			// Lines.add(new Instruction(Line))
		}
		
		System.out.println();

	}

	private void parseBlock(Block block){
		if(block.condition != "true")
			Lines.add(new Instruction(block.condition));
			
		while(block.hasCode())
			Lines.add(new Instruction(block.getLine()));	
	}
}