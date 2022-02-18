package Identify;

// Turns string command into program readable e.g. $test=4 -> ["$test", "4"]
public class tokenise {

    private static char operators[] = {'=', '<', '>'};

    public static String[] parse(String instruction){
        

        String test[] = {"", ""};
        int index = instruction.indexOf("=");
        if(index > -1)

            test[0] = instruction.substring(0, index);
            test[1] = instruction.substring(index +1);


        return test;
    }
}
