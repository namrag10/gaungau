package Structures;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import Identify.tokenise;


public class Block {

    private Queue<String[]> blockCode = new LinkedList<String[]>(); 
    private Stack<Block> children = new Stack<Block>();

    public String condition = "";

    public Block(Queue<String> block, String condition){
        while(block.size() > 0)
            blockCode.add(tokenise.parse(block.poll()));
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
    public Queue<String[]> getCode(){
        return blockCode;
    }
    public String[] getLine(){
        return blockCode.poll();
    }
    public boolean hasCode(){
        return (blockCode.size() > 0);
    }

}
