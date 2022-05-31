package Syntax;

public class codeControl {

    public static String load(String value){
        return handle("LDA", value);
    }

    
    public static String add(String number){
        return handle("ADD", number);
    }
    public static String sub(int number){
        return sub(Integer.toString(number));
    }
    public static String sub(String number){
        return handle("SUB", number);
    }


    public static String store(int param){
        return store(Integer.toString(param));
    }
    public static String store(String param){
        return "STO " + param + "\n";
    }


    
    
    public static String unconditionalBranch(String ILine){
        return "BRA " + ILine + "\n";
    }
    public static String carryBranch(String ILine){
        return "BRC " + ILine + "\n";
    }
    public static String zeroBranch(String ILine){
        return "BRZ " + ILine + "\n";
    }
    public static String posBranch(String ILine){
        return "BRP " + ILine + "\n";
    }
    
    public static String unconditionalBranch(int ILine){
        return unconditionalBranch(Integer.toString(ILine));
    }
    public static String carryBranch(int ILine){
        return carryBranch(Integer.toString(ILine));
    }
    public static String zeroBranch(int ILine){
        return zeroBranch(Integer.toString(ILine));
    }
    public static String posBranch(int ILine){
        return posBranch(Integer.toString(ILine));
    }

    


    public static String halt(){
        return "HLT\n";
    }

    public static String print(){
        return "OUT\n";
    }




    private static String handle(String command, String number){
        String ret = "&";
        if(number.indexOf(SyntaxCfg.variableID) > -1){
            ret = "";
            number = number.substring(1);
        }
        return ret +  command + " " + number + "\n";
    }
}
