package ErrorHandle;

public class Error {
    public static void syntaxError(String msg, int lineNumber){
        System.out.println("Syntax Error: " + msg + " at line: " + lineNumber);
    }

    public static void customError(String msg){
        System.out.println(msg);
    }

    public static void namingError(String msg, int lineNumber){
        System.out.println("Naming Error: " + msg + " at line: " + lineNumber);
    }

    public static void logicError(String msg, int lineNumber){
        System.out.println("Logic Error: " + msg + " at line: " + lineNumber);
    }
}
