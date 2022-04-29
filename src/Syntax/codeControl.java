package Syntax;

public class codeControl {

    public static String loadLiteral(String value){
        return "&LDA " + value + "\n";
    }

    public static String loadFromPointer(String pointer){
        return "LDA " + pointer + "\n";
    }

    public static String saveVariable(String varAddr){
        return "STO " + varAddr + "\n";
    }

    public static String addLiteral(String literal){
        return "&ADD " + literal + "\n";
    }

    public static String addPointer(String pointer){
        return "ADD " + pointer + "\n";
    }

    public static String subLiteral(String literal){
        return "&SUB " + literal + "\n";
    }

    public static String subPointer(String pointer){
        return "SUB " + pointer + "\n";
    }
}
