package Structures.Meta;


import java.util.ArrayList;

import ErrorHandle.Error;
import GarbageControl.MemoryManager;
import Identify.asmGen;
import Structures.Struc;
import Syntax.codeControl;
public class Condition extends Struc {

    private String raw;
    private String condition = "";
    private ArrayList < ArrayList < String > > tokensList = new ArrayList < ArrayList < String > > ();
    private int ILine = 0;


    public Condition(String rawStatement, int lineNumber) {
        raw = rawStatement;
        super.lineNumber = lineNumber;
    }

    @Override
    public boolean parse(int ILine) {
        this.ILine = ILine;
        if (raw.indexOf(")") == -1) {
            Error.syntaxError("No close bracket on condition", lineNumber);
            return false;
        }
        condition = raw.substring(1, raw.indexOf(")"));


        if (!tokenise()) return false;
        build();




        return true;
    }


    private boolean build() {
        // ArrayList<String> temp = new ArrayList<String>();
        int count = 0;
        tokensList.add(new ArrayList < String > ());
        for (String token: tokens) {
            if (isOperatorKey(token)) {
                count++;
                tokensList.add(new ArrayList < String > ());
            } else {
                tokensList.get(count).add(token);
            }
        }

        for (ArrayList < String > tokenList: tokensList) {
            String operatorInstrucHold = "";
            while (tokenList.size() > 0) {
                String firstTerm = tokenList.remove(tokenList.size() -1);
                if (firstTerm.length() == 0) {
                    Error.syntaxError("No condition", lineNumber);
                    return false;
                }

                // Loading the first term
                instructions.add(
                    codeControl.load(firstTerm)
                );

                // need to add logic to reverse the lhs and rhs without affecting eachother or operand

                if(isOperand(tokenList.get(tokenList.size() -1))){
                    operatorInstrucHold = tokenList.remove(tokenList.size() -1);
                    continue;
                }
                // Add subsequent terms
                while (tokenList.size() > 0) {
                    String token = tokenList.remove(tokenList.size() -1);
                    if (isOperand(token)) {
                        // operatorInstrucHold = asmGen.operandTranslate(token, ILine + instructionCount() + 1);
                        operatorInstrucHold = token;
                        instructions.add(codeControl.store("255"));
                        break;
                    } else
                        instructions.add(asmGen.operatorTranslate(
                            extractOperands(token),
                            extractAfterOperands(token)));
                }
            }
            instructions.add(asmGen.operandTranslate(operatorInstrucHold, ILine + instructionCount() + 1));
        }
        return true;
    }



    private boolean tokenise() {
        if (condition.indexOf(variableOperand) > -1) {
            Error.syntaxError("Cannot include multiple defining syntax on one line!", lineNumber);
            return false;
        }

        String temp = "";
        for (char t: condition.toCharArray()) {
            if (isOperandSingleChar(t + "")) {
                if (!(temp.equals("")) && !(isOperandSingleChar(temp.substring(temp.length() - 1)))) {
                    tokens.add(temp);
                    temp = "";
                }
            }
            temp += t;
            if (isOperand(temp)) {
                tokens.add(temp);
                temp = "";
            }
        }
        tokens.add(temp);

        return relateVariables();
    }

    private boolean relateVariables() {
        for (int i = 0; i < tokens.size(); i++) {

            String value = tokens.get(i);

            int addr = -2;
            if (!value.toUpperCase().equals(value.toLowerCase())) {
                addr = MemoryManager.has(value);
                if (addr == -1) {
                    Error.syntaxError("Variable: " + value + " does not exist", lineNumber);
                    return false;
                }
                tokens.set(i, variableID + addr + "");
            }
        }
        return true;
    }

    private boolean isOperandSingleChar(String term) {
        for (String symbol: singles)
            if (symbol.equals(term)) return true;

        for (String symbol: operators)
            if (symbol.equals(term)) return true;
        return false;
    }

    public boolean isOperand(String term) {
        for (String symbol: operands)
            if (symbol.equals(term)) return true;
        return false;
    }

    public boolean isOperatorKey(String term) {
        for (String symbol: operatorKeywords)
            if (symbol.equals(term)) return true;
        return false;
    }


    public String getRaw() {
        return raw;
    }

}