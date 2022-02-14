package Processes;

import Structures.Block;

public class Syntax {
    
    public Syntax(Lexical codeBase){
        
        for(int crntBlock = 0; crntBlock < codeBase.amountBlocks(); crntBlock++){
            Block block = codeBase.getBlock();
            Block crntChild = block.getChild();
            
            while(crntChild != null){
                





                crntChild = block.getChild();
            }
        }

    }
}
