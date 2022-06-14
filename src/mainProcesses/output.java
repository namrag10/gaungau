package mainProcesses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Structures.Meta.LineMeta;
import Structures.Meta.Struc;

public class output {

    public ArrayList<Struc> stack = new ArrayList<Struc>();
    public Queue<LineMeta> lex = new LinkedList<LineMeta>();

    public output(Queue<LineMeta> lexStack){
        lex = lexStack;
    }

    public output(ArrayList<Struc> synStack){
        stack = synStack;
    }
}
