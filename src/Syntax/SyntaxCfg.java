package Syntax;

public interface SyntaxCfg {
    
    String[] keywords = new String[]{"if", "while", "else"};
	String[] callables = new String[]{"print", "exit"};
    String[] comparatorKeys = new String[]{"&&", "||"};
	
	String[] comparators = new String[]{"==", "!=", "<=", ">=", "<", ">"};
	String[] singles = new String[]{"=", "!", "<", ">", "&", "|"};

	String[] braces = new String[]{"{", "}", "(", ")"};
	String[] operators = new String[]{"+", "-"}; // Coinsidently these are the only things allowed on RHS of variable which isn't a condition
	

	String functionIdentifier = "()";
    String variableOperand = ":="; // Variable set syntax
	String variableID = "&"; // To show where a variable is present, and thus the pointer should be updated


	String conditionPointerID = "$"; // Used behind the scenes to note where a pointer is needed to be updated before generated

}