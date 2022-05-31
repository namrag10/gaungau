package GarbageControl;

import java.util.ArrayList;
import java.util.Queue;

import Structures.Struc;
import Structures.Meta.LineMeta;

public class output {

    public ArrayList<Struc> stack = new ArrayList<Struc>();

    public output(Queue<LineMeta> lexStack){

    }

    public output(ArrayList<Struc> synStack){
        stack = synStack;
    }
}
