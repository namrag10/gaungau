package Structures.Functions;

import Structures.Meta.Condition;

public class ifFunc extends FunctionWorkings {



    public ifFunc(int open, Condition condition){
        super(open, condition);
        preBlock.add("Open If at: " + starting);
    }

    @Override
    public void closeHandle(int close){
        postBlock.add("Closing if at: " + close);
    }

    @Override
    public boolean generateCondition(){
        return true;
    }

}
