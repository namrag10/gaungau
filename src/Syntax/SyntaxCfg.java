package Syntax;

public interface SyntaxCfg {
    
    String[] keywords = new String[]{"if(", "while(", "else"};
	String[] operands = new String[]{"==", "!=", "<", ">", "<=", ">=", "(", ")"};
	String[] syntax = new String[]{"{", "}"};
	String[] operators = new String[]{"+", "-"}; // Coinsidently these are the only things allowed on RHS of variable which isn't a condition
	
	String functionIdentifier = "()";
    String variableOperand = ":=";
}