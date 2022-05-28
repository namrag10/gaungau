import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import ErrorHandle.Error;
import Structures.Struc;
import mainProcesses.CodeGen;
import mainProcesses.SyntaxAnalysis;

public class App {
	public static void main(String[] args) throws Exception {
		if(args.length > 1){
			Error.customError("Interpreter requires ONLY the path of 1 script, not the ");
			return;
		}else if(args.length < 1){
			Error.customError("Interpreter requires the path of ONE script");
			return;
		}


		String txtScript = "";


		try {
			File myObj = new File(args[0] + ".gng");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine())
				txtScript = txtScript + myReader.nextLine() + "\n";

			myReader.close();
		} catch (FileNotFoundException e) {
			Error.customError("Script was not found at path '" + args[0] + ".gng'!");
			return;
		}

		SyntaxAnalysis syn = new SyntaxAnalysis(txtScript);

		ArrayList<Struc> yeet = syn.getCommands();

		if(!syn.hasError()){
			CodeGen Gen = new CodeGen(syn);
			Gen.generate();
		}


	}
}