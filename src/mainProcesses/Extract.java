package mainProcesses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Extract {
    private Queue<String> Lines = new LinkedList<String>();

	public Extract(String rawSource){
        Queue<String>parsedStatements = new LinkedList<String>(Arrays.asList(rawSource.split(";")));
		rawSource = removeComents(rawSource);
		
	}


    private String removeComents(String statements){
		statements = statements.replace(" ", "");

		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(statements.split("\n")));

		for(int i = 0; i < lines.size(); i++){
			String line = lines.get(i);
			int index = line.indexOf("#");
			if(index == 0){
				lines.remove(i);
				i = -1;
			}else if(index > -1)
				lines.set(i, line.substring(0, index));
		}
		statements = String.join("", lines);

		return statements;
	}
}
