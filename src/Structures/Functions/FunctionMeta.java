package Structures.Functions;

import java.util.ArrayList;

public class FunctionMeta {
    public ArrayList<String> preBlock = new ArrayList<String>();
    public ArrayList<String> postBlock = new ArrayList<String>();

    protected int starting;
    protected int closing;

    public FunctionMeta(int open){
        starting = open;
    }
}
