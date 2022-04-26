package mainProcesses;

public class Syntax {
	
	public Syntax(Tokenise codeBase){
		for(int i = 0; i < codeBase.getCode().size(); i++)
            codeBase.getCode().poll().execute();
        
            return;
        
	}
}