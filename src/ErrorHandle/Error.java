package ErrorHandle;

public class Error {
    public static void syntaxError(String msg, int line){
        System.out.println("Syntax Error: " + msg + " at line: " + line);
    }

    public static void customError(String msg){
        System.out.println(msg);
    }

    public static void namingError(String msg, int line){
        System.out.println("Naming Error: " + msg + " at line: " + line);
    }
}
