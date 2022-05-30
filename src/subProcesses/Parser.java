package subProcesses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import Structures.Meta.LineMeta;

public class Parser {

	public static Queue < LineMeta > parse(String statements) {

		statements = statements.replace(" ", "");
		statements = statements.replace("\n", ";");

		ArrayList < String > raw = new ArrayList < String > (Arrays.asList(statements.split(";")));



		ArrayList<LineMeta> lines = new ArrayList<LineMeta>();

		for(int i = 0; i < raw.size(); i++)
			lines.add(new LineMeta(raw.get(i), i +1));




		for (int i = 0; i < lines.size(); i++) {
			String lineText = lines.get(i).lineText;
			int index = lineText.indexOf("#");
			if (index == 0) {
				lines.remove(i);
				i = -1;
				continue;
			} else if (index > -1)
				lines.get(i).lineText = lineText.substring(0, index);

			// === No comments from here === \\

			// === Sorting out multiple close braces on one line === \\
			int braceIndex = lineText.indexOf("}");
			if (braceIndex > -1 && lineText.length() > 1) {
				if(braceIndex == 0){
					lines.add(i + 1, new LineMeta(lineText.substring(braceIndex+1),
						lines.get(i).lineNumber));
					lines.get(i).lineText = lineText.substring(braceIndex, 1);
				}else{
					lines.add(i + 1, new LineMeta(
						lineText.substring(braceIndex), 
						lines.get(i).lineNumber)
					);
					lines.get(i).lineText = lineText.substring(0, braceIndex);

				}
				i = -1;
				continue;
			}

			braceIndex = lineText.indexOf("{");
			if (braceIndex > -1 && lineText.length() > 1) {
				if(braceIndex == 0){
					lines.add(i + 1, new LineMeta(lineText.substring(braceIndex+1),
						lines.get(i).lineNumber));
					lines.get(i).lineText = lineText.substring(braceIndex, 1);
				}else{
					lines.add(i + 1, new LineMeta(
						lineText.substring(braceIndex), 
						lines.get(i).lineNumber)
					);
					lines.get(i).lineText = lineText.substring(0, braceIndex);

				}
				i = -1;
				continue;
			}


			// === Removes a blank line === \\
			if (lineText.equals("")) {
				lines.remove(i);
				i = -1;
			}
		}
		Queue<LineMeta> ret = new LinkedList<LineMeta>(lines);

		return ret;
	}
}