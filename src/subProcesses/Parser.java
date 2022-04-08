package subProcesses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {

	public static Queue < String > parse(String statements) {

		statements = statements.replace(" ", "");

		ArrayList < String > lines = new ArrayList < String > (Arrays.asList(statements.split("\n")));

		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			int index = line.indexOf("#");
			if (index == 0) {
				lines.remove(i);
				i = -1;
			} else if (index > -1)
				lines.set(i, line.substring(0, index));

			int braceIndex = line.indexOf("}");
			if (braceIndex > -1 && line.length() > 1) {
				if(amountChars(line, '}') > 1)
					braceIndex++;
				lines.add(i + 1, line.substring(braceIndex));
				lines.set(i, line.substring(0, braceIndex));
				i = -1;
			}

			if (line.equals("")) {
				lines.remove(i);
				i = -1;
			}
		}

		
		return new LinkedList < String > (lines);
	}
	private static int amountChars(String inpString, char inpChar) {
	
		int totalCharacters = 0;
		char temp;
		for (int i = 0; i < inpString.length(); i++) {
			temp = inpString.charAt(i);
			if (temp == inpChar)
				totalCharacters++;
		}
		return totalCharacters;
	}
}