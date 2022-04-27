package Structures;


public class StandardFunction extends struct {

    public String raw;
    public String condition = "";

    public StandardFunction(String rawStatement){
        raw = rawStatement;
    }

    @Override
    public boolean execute(){
        
        return true;
    }
}
