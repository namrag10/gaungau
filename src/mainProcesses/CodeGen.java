package mainProcesses;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ErrorHandle.Error;
import Structures.*;

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
			for(String instruction : structure.buildAndGetInstructions()){
				if(!append(instruction)){
					Error.customError("Could not write to file!");
					return false;
				}
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