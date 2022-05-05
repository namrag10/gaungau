package Structures.Functions;


public class ifFunc extends FunctionMeta {



    public ifFunc(int open){
        super(open);
        preBlock.add("===Open If at: " + starting);
        postBlock.add("Closed If at: " + closing);

    }

}
