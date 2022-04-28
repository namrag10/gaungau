package Syntax;

public class codeControl {

    public static String literalVariableCode(String value){
        return "&LDA " + value + "\n";
    }

    public static String loadFromPointer(int pointer){
        return "LDA " + pointer + "\n";
    }

    public static String saveVariable(int varAddr){
        return "STO " + varAddr + "\n";
    }
}
