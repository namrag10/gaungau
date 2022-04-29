package Syntax;

public interface SyntaxCfg {
    
    String[] keywords = new String[]{"if(", "while(", "else"};
	String[] operands = new String[]{"==", "!=", "<", ">", "<=", ">=", "(", ")"};
	String[] validChars = new String[] {"=", "!", "<", ">", "(", ")", "+", "-"};
	String[] syntax = new String[]{"{", "}"};
	String[] operators = new String[]{"+", "-"};

    String variableOperand = ":=";
}
