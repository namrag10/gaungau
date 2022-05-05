package Structures;

import java.util.ArrayList;

import Syntax.SyntaxCfg;

public class Struc implements SyntaxCfg {
    public int lineNumber;
    protected String raw;
    protected int instructionsInBlock = 0;

    public ArrayList < String > instructions = new ArrayList < String > ();
    protected ArrayList < String > tokens = new ArrayList < String > ();

    public boolean parse(int fromLine) {
        return false;
    }

    public boolean create(){
        return false;
    }

    // Gets the amount of instructions added after a parse
    public int instructionCount(){
        return instructions.size() + instructionsInBlock;
    }

    public ArrayList<String> buildAndGetInstructions(){
        return instructions;
    }

}