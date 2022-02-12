import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import Processes.Lexical;
import Processes.Syntax;

public class App {
	public static void main(String[] args) throws Exception {
		if(args.length > 1){
			print("Interpreter requires ONLY the path of 1 script, not the " + String.valueOf(args.length) + " that you provided");
			return;
		}else if(args.length < 1){
			print("Interpreter requires the path of ONE script");
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
			print("Script was not found at path '" + args[0] + ".gng'!");
			return;
		}

		Lexical lex = new Lexical(txtScript);


		print(lex.toString());

		Syntax Syn = new Syntax(lex);

		print(Syn);

	}

	private static <T> void print(T msg){
		System.out.println(msg);
	}
}