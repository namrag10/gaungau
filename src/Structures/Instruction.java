package Structures;

public class Instruction {
    private int parameter = 0;
    private String operation = "";

    private String codeLine;


    // Instruction only
    public Instruction(String Line){
        // codeLine = Line
        System.out.println(Line);
    }


    // Instruction with parameter
    public Instruction(String Line, String Parameter){
        // codeLine = Line
        System.out.println(Line);
    }
    
    // Instruction with parameter
    public Instruction(String[] Command){
        // codeLine = Line
        System.out.println(Command[0]);
        System.out.println(Command[1]);
    }




}
