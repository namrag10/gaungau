import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ErrorHandle.Error;
import GarbageControl.output;
import mainProcesses.CodeGen;
import mainProcesses.LexicalAnalysis;
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

		LexicalAnalysis lex = new LexicalAnalysis(txtScript);

		SyntaxAnalysis syn = new SyntaxAnalysis(lex);
		output yeet = syn.view();

		if(!syn.hasError()){
			CodeGen Gen = new CodeGen("output.asm");
			Gen.generate(syn);
		}


	}
}