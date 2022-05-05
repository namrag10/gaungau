package Structures.Meta;

import Structures.Struc;
public class Condition extends Struc {

    private String raw;
    private String condition = "";
    private String operator = "";

    // x < y === x - y === if carries (BRC <skip BRA>) then allow, else skip (BRA <end of block>)
    // 2 - 1 (FALSE)

    // LDA x
    // SUB y
    // BRC <crnt + 2>
    // BRA <Skip block>

    public Condition(String rawStatement){
        raw = rawStatement;
    }

    public boolean parse(){

        return false;
    }





}
