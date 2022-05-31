package mainProcesses;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ErrorHandle.Error;
import Structures.Meta.Struc;

public class CodeGen {

	private String filePath;

	public CodeGen(String outputFile) {
		filePath = outputFile;

		// === Clears the output file === \\
		try {
			FileWriter myWriter = new FileWriter(filePath);
			myWriter.write("");
			myWriter.close();
		} catch (IOException e) {
			System.out.println("Could not write to file!");
			e.printStackTrace();
		}
	}

	public boolean generate(SyntaxAnalysis syntaxAnalysis) {
		ArrayList < Struc > code = syntaxAnalysis.getFullCode();

		for (Struc structure: code) {
			for (String instruction: structure.buildAndGetInstructions()) {
				if (!append(instruction)) {
					Error.customError("Could not write to file!");
					return false;
				}
			}
		}
		return true;
	}

	private boolean append(String instructions) {
		try {
			FileWriter myWriter = new FileWriter(filePath, true);
			myWriter.append(instructions);
			myWriter.close();
			return true;
		} catch (IOException e) {
			System.out.println("Could not write to file!");
			return false;
		}
	}

}