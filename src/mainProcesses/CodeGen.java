package mainProcesses;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ErrorHandle.Error;
import Structures.*;
import Structures.Functions.Function;

public class CodeGen {

	private SyntaxAnalysis codeTree;

	public CodeGen(SyntaxAnalysis codeBase) {
		codeTree = codeBase;

		try {
			FileWriter myWriter = new FileWriter("output.asm");
			myWriter.write("");
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public boolean generate() {
		ArrayList < Struc > code = codeTree.getFullCode();

		for (Struc structure: code) {

			if (structure.getClass() == Variable.class) { // Found a variable
				Variable command = (Variable) structure;
				for (String instruction: command.instructions) {
					if(!append(instruction)){
						Error.customError("Could not write to file!");
						return false;
					}
				}

			} else if (structure.getClass() == Function.class) { // Found a function (look inside)
				
			}
		}
		System.out.println("GNG Success! Written to file output.asm");
		return true;
	}

	private boolean append(String instructions) {
		try {
			FileWriter myWriter = new FileWriter("output.asm", true);
			myWriter.append(instructions);
			myWriter.close();
			return true;
		} catch (IOException e) {
			System.out.println("An error occurred.");
			return false;
		}
	}

}