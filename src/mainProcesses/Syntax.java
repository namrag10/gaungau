package mainProcesses;

import java.util.ArrayList;

import Structures.*;

public class Syntax {
	
	public Syntax(Tokenise codeBase){
		ArrayList<struct> code = codeBase.getFullCode();
		
		for(int i = 0; i < code.size(); i++) // All the code at this point makes sense, but not necesserily correct
			code.get(i).execute();
		
		
			
        
        return;
        
	}
}