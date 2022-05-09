package Identify;

import Syntax.codeControl;

public class asmGen {

    public static String operatorTranslate(String operator, String param){
        switch (operator) {
            case "+":
                return codeControl.add(param);
            case "-":
                return codeControl.sub(param);
            default:
                return null;
        }
    }

    public static String operandTranslate(String operand, int param){
        switch(operand){
            case "<":
                return codeControl.carryBranch(param);
            case ">":
                return codeControl.posBranch(param);
            case "==":
                return codeControl.zeroBranch(param);
            default:
                return "";
        }
    }
}
