package mainProcesses;

import java.util.ArrayList;

import Structures.*;
import Structures.Functions.Function;

public class Syntax {
	
	public Syntax(Tokenise codeBase){
		ArrayList<Struc> code = codeBase.getFullCode();
		
		for (Struc structure : code) {

			if(structure.getClass() == Variable.class){ // Found a variable
				
			}else if(structure.getClass() == Function.class){ // Found a function (look inside)
				Function crnt = (Function) structure;
				// for (Struc struc : ) {
					
				// }
			}
		}
		
		
		
			
        
        return;
        
	}
}