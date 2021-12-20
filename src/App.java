import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
				txtScript = txtScript + myReader.nextLine();
				
			myReader.close();
		} catch (FileNotFoundException e) {
			print("Script was not found at path '" + args[0] + ".gng'!");
			return;
		}

		mainStack codeBase = new mainStack(txtScript);

		print(codeBase.toString());



	}

	private static <T> void print(T msg){
		System.out.println(msg);
	}
}