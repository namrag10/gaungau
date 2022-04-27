package Structures.Meta;

public class LineMeta {
    public int lineNumber;
    public String lineText;

    public LineMeta(String line, int number){
        this.lineText = line;
        lineNumber = number;
    }
}