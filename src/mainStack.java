import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class mainStack {
    
    List<String> lexStack = new ArrayList<String>();

    public mainStack(String rawSource){
        //Lexical analysis
        lexStack = Arrays.asList(rawSource.split(";"));
    }


    public String toString(){
        return lexStack.toString();
    }
}
