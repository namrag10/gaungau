package mainProcesses;
import java.util.LinkedList;
import java.util.Queue;
import subProcesses.Parser;

public class Tokenise {
	
	String[] keywords = new String[]{"if", "while"};
	char[] operators = new char[]{'=', '<', '>', '!', '+', '-'};
	char[] syntax = new char[]{'{', '}', '(', ')'};
	Queue<String[]> Tokens = new LinkedList<String[]>();

	public Tokenise(String rawSource){
		Queue<String> statements = Parser.parse(rawSource);								// Removes comments
	
		

		while(statements.size() > 0){ 									// Loops through each statement (;)
			
			String statement = statements.poll();

			System.out.println(statement);

			boolean hasKeyword = false;
			for (String keyword : keywords) {
				if(statement.indexOf(keyword) == 0){
					hasKeyword = true;
					//addToken("KEY", keyword);
				}
			}

			if(!hasKeyword){
				// check against known functions
				int posOfEq = statement.indexOf("=");
				if(posOfEq > -1){
					String varName = statement.substring(0, posOfEq);
					String varVal = statement.substring(posOfEq +1);
					if(validateVar(varName) && validateVarVal(varVal)){
						addToken("ID", varName);
						addToken("EQ");
						addToken("VAL", varVal);
					}else{
						// SYNTAX ERROR!! - invalid var name at line x
					}
				}else{
					// SYNTAX ERROR!! - What are you doing with the variable on line x?
				}
			}
				


			
			

			
			
		}

		System.out.println("YEET");

	}

	private boolean validateVar(String varName){
		return true;
	}

	private boolean validateVarVal(String varVal){
		return true;
	}

	private void addToken(String key, String token){
		Tokens.add(new String[]{key, token});
	}
	private void addToken(String key){
		Tokens.add(new String[]{key});
	}
}
