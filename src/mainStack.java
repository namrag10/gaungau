import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class mainStack {
    
    // List<List<String>> lexStack = new ArrayList<List<String>>();
    List<String> lexStack = new ArrayList<String>();

    public mainStack(String rawSource){
        rawSource = rawSource.replace(" ", "");

        //Lexical analysis
        lexStack = Arrays.asList(rawSource.split(";"));
    }


    public String toString(){
        System.out.println("\n");

        for (String string : lexStack) {
            System.out.println(string + "\n");
        }
        return lexStack.toString();
    }
}
