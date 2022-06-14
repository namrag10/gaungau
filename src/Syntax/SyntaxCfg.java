package Syntax;

public class SyntaxCfg {
    
    public static String[] keywords = new String[]{"if", "while", "else"};
	public static String[] callables = new String[]{"print", "exit"};
    public static String[] comparatorKeys = new String[]{"&&", "||"};
	
	public static String[] comparators = new String[]{"==", "!=", "<=", ">=", "<", ">"};
	public static String[] singles = new String[]{"=", "!", "<", ">", "&", "|"};

	public static String[] braces = new String[]{"{", "}", "(", ")"};
	public static String[] operators = new String[]{"+", "-"}; // Coinsidently these are the only things allowed on RHS of variable which isn't a condition
	

    public static String variableOperand = ":="; // Variable set syntax
	public static String variableID = "&"; // To show where a variable is present, and thus the pointer should be updated


	public static String conditionPointerID = "$"; // Used behind the scenes to note where a pointer is needed to be updated before generated

}