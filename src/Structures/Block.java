package Structures;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import Identify.tokenise;


public class Block {

    private Queue<Block> blockCode = new LinkedList<Block>();


    public String condition = "";

    public Block() { // String condition){
        // this.condition = condition;
    }

    public void addStatement(Block statementBlock){
        blockCode.add(statementBlock);
    }


    // public void addChild(Block childToAdd){
    //     this.children.push(childToAdd);
    // }
    // public boolean hasChild(){
    //     return (children.size() > 0);
    // }
    // public Block getChild(){
    //     return (hasChild()) ? children.pop() : null;
    // }
    // public Queue<String[]> getCode(){
    //     return blockCode;
    // }
    // public String[] getLine(){
    //     return blockCode.poll();
    // }
    public boolean hasCode(){
        return (blockCode.size() > 0);
    }

}
