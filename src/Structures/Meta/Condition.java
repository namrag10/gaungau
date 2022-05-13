package Structures.Meta;


import java.util.ArrayList;

import ErrorHandle.Error;
import GarbageControl.MemoryManager;
import Identify.logicGen;
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


        if (!tokenise()) 
            System.out.print("Yeet");
        //return false;
        build();




        return true;
    }


    private boolean build() {
        // ArrayList<String> temp = new ArrayList<String>();
        int count = 0;
        tokensList.add(new ArrayList < String > ());
        for (String token: tokens) {
            tokensList.get(count).add(token);
            if (isOperatorKey(token)) {
                count++;
                tokensList.add(new ArrayList < String > ());
            }
        }
        
        
        for (ArrayList < String > tokenList: tokensList) {
            ArrayList <ArrayList<String>> instructionsHold = new ArrayList <ArrayList<String>> ();
            String operatorTokenHold = "";

            String operatorKeyHold = "none";
            
            
            while (tokenList.size() > 0) {
                // Current side of the operand (left or right of operand)
                ArrayList<String> crntSideInstructions = new ArrayList<String>();
                String firstTerm = tokenList.remove(0);
                if (firstTerm.length() == 0) {
                    Error.syntaxError("No condition", lineNumber);
                    return false;
                }

                // Loading the first term
                crntSideInstructions.add(
                    codeControl.load(firstTerm)
                );

                // need to add logic to reverse the lhs and rhs without affecting eachother or operand

                if(tokenList.size() > 0 && isOperand(tokenList.get(0))){
                    operatorTokenHold = tokenList.remove(0);
                    instructionsHold.add(crntSideInstructions);
                    continue;
                }
                // Add subsequent terms
                while (tokenList.size() > 0) {
                    if(isOperatorKey(tokenList.get(0))){
                        operatorKeyHold = tokenList.remove(0);
                        continue;
                    }

                    String token = tokenList.remove(0);
                    if (isOperand(token)) {
                        // operatorInstrucHold = logicGen.operandTranslate(token, ILine + instructionCount() + 1);
                        operatorTokenHold = token;
                        break;
                    } else
                        crntSideInstructions.add(logicGen.operatorTranslate(
                            extractOperands(token),
                            extractAfterOperands(token)));
                }
                if(instructionsHold.size() > 0)
                    crntSideInstructions.add(codeControl.store(255));
                instructionsHold.add(crntSideInstructions);
            }

            // Both sides are now built, and operand recorded
            // Adds both sides, and the operator loop check
            for (String instruc : instructionsHold.get(1))
                instructions.add(instruc);
            
            for (String instruc : instructionsHold.get(0))
                instructions.add(instruc);
            
            // Adds the subtract check - manditory for any check
            instructions.add(codeControl.sub(255));
            
            String[] logicInstructs = logicGen.operandTranslate(
                operatorTokenHold,
                ILine + instructionCount(),
                operatorKeyHold
            ).split("#");

            // Adds loop for type of operand
            for(int i = 0; i < logicInstructs.length; i++)
                instructions.add(logicInstructs[i]);

            // above:
            // or => should point to the block
            // and => should point to skip the block (last line in the condition instructions)
            // true => should + 2



            // String operandInstruction = logicGen.operatorKeywordTranslate(
            //     operatorKeyHold,
            //     ILine + instructionCount(),
            //     operatorKeyHold
            // );

            // if(operandInstruction != null)
            //     instructions.add(operandInstruction);
        }
        // change skip pointers
        finalisePointers();
        return true;
    }


    private void finalisePointers(){
        String toBlock = instructions.get(instructions.size() -1);
        toBlock = toBlock.substring(toBlock.indexOf(" ") +1);

        for (int i = 0; i < instructions.size(); i++) {
            if(instructions.get(i).indexOf(conditionPointerID) > -1){
                String statement = instructions.get(i).substring(0, instructions.get(i).indexOf(" ") +1);

                if(instructions.get(i).indexOf("true") > -1){
                    instructions.set(i, statement + toBlock);
                }else{
                    instructions.set(i, statement + (ILine + instructionCount() + 1) + "\n");
                }

            }
        }
    }


    private boolean tokenise() {
        if (condition.indexOf(variableOperand) > -1) {
            Error.syntaxError("Cannot include multiple defining syntax on one line!", lineNumber);
            return false;
        }

        String temp = "";
        for (char t: condition.toCharArray()) {
            if (isOperandSingleChar(t + "")) {
                if (!temp.equals("") && !isOperandSingleChar(temp.substring(temp.length() - 1))) {
                    tokens.add(temp);
                    temp = "";
                }
            }
            temp += t;
            if (isOperand(temp) || isOperatorKey(temp)) {
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