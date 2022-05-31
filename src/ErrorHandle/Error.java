package ErrorHandle;

// ===== CLASS TO HANDLE ERROR MESSAGES ===== \\
public class Error {

    // === Handles a standard syntax error === \\
    public static void syntaxError(String msg, int lineNumber){
        System.out.println("Syntax Error: " + msg + " at line: " + lineNumber);
    }

    // === Will print a fully custom error === \\
    public static void customError(String msg){
        System.out.println(msg);
    }

    // === Handles error with variable name === \\
    public static void namingError(String msg, int lineNumber){
        System.out.println("Naming Error: " + msg + " at line: " + lineNumber);
    }

    // === Prints message for logical error === \\
    public static void logicError(String msg, int lineNumber){
        System.out.println("Logic Error: " + msg + " at line: " + lineNumber);
    }
}
