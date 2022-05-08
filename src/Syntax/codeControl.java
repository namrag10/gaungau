package Syntax;

public class codeControl implements SyntaxCfg{

    public static String load(String value){
        return handle("LDA", value);
    }
    public static String add(String number){
        return handle("ADD", number);
    }

    public static String sub(String number){
        return handle("SUB", number);
    }

    public static String saveVariable(String varAddr){
        return "STO " + varAddr + "\n";
    }


    public static String unconditionalBranch(int ILine){
        return "BRA " + ILine + "\n";
    }
    public static String carryBranch(int ILine){
        return "BRC " + ILine + "\n";
    }
    public static String zeroBranch(int ILine){
        return "BRZ " + ILine + "\n";
    }
    public static String posBranch(int ILine){
        return "BRP " + ILine + "\n";
    }

    public static String halt(){
        return "HLT\n";
    }



    private static String handle(String command, String number){
        String ret = "&";
        if(number.indexOf(variableID) > -1){
            ret = "";
            number = number.substring(1);
        }
        return ret +  command + " " + number + "\n";
    }
}
