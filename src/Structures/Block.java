package Structures;
import java.util.LinkedList;
import java.util.Queue;


public class Block {

    public Queue<String> rawBlock = new LinkedList<String>(); 
    private Block child = null;

    public String condition = "";

    public Block(Queue<String> block, String condition){
        rawBlock = new LinkedList<String>(block);
        this.condition = condition;
    }

    public void addChild(Block childToAdd){
        this.child = childToAdd;
    }
    public boolean hasChild(){
        return (child != null);
    }

}
