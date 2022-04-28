package mainProcesses;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Structures.*;
import Structures.Functions.Function;
import Syntax.codeControl;

public class CodeGen {

	private SyntaxAnalysis codeTree;

	public CodeGen(SyntaxAnalysis codeBase) {
		codeTree = codeBase;
	}

	public boolean generate(){
		ArrayList < Struc > code = codeTree.getFullCode();

		for (Struc structure: code) {

			if (structure.getClass() == Variable.class) { // Found a variable
				Variable command = (Variable) structure;
				append(codeControl.literalVariableCode(command.rhs));
				append(codeControl.saveVariable(command.address));
			} else if (structure.getClass() == Function.class) { // Found a function (look inside)

			}
		}
		return true;
	}

	private void append(String instructions){
		try {
			FileWriter myWriter = new FileWriter("output.asm", true);
     		myWriter.append(instructions);
      		myWriter.close();
      		System.out.println("Successfully wrote to the file.");
		  } catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		  }
	}

}