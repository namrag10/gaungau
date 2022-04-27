package Syntax;

public interface Syntax {
    
    String[] keywords = new String[]{"if", "while", "else"};
	String[] operands = new String[]{"==", "!=", "<", ">", "<=", ">="};
	String[] syntax = new String[]{"{", "}", "(", ")"};
	String[] operators = new String[]{"+", "-"};

    String variableOperand = ":=";
}
