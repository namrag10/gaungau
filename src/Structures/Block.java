package Structures;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class Block {

    private Queue<String> rawBlock = new LinkedList<String>(); 
    private Stack<Block> children = new Stack<Block>();

    public String condition = "";

    public Block(Queue<String> block, String condition){
        rawBlock = new LinkedList<String>(block);
        this.condition = condition;
    }

    public void addChild(Block childToAdd){
        this.children.push(childToAdd);
    }
    public boolean hasChild(){
        return (children.size() > 0);
    }
    public Block getChild(){
        return (hasChild()) ? children.pop() : null;
    }

    public Queue<String> getCode(){
        return rawBlock;
    }

}
