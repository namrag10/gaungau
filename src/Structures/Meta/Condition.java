package Structures.Meta;


import java.util.ArrayList;

import ErrorHandle.Error;
import GarbageControl.MemoryManager;
import Identify.logicGen;
import Syntax.SyntaxCfg;
import Syntax.codeControl;
public class Condition extends Struc {

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

        for (String bracket : SyntaxCfg.braces) {
            if(condition.indexOf(bracket) > -1){
                Error.syntaxError("Embedding conditions not supported, remove additional brackets", lineNumber);
                return false;
            }
        }

        if (tokenise())
            return build();
        
        Error.syntaxError("Invalid condition syntax", lineNumber);
        return false;
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
            ArrayList < ArrayList < String >> instructionsHold = new ArrayList < ArrayList < String >> ();
            String operatorTokenHold = "";

            String operatorKeyHold = "none";


            while (tokenList.size() > 0) {
                // Current side of the operand (left or right of operand)
                ArrayList < String > crntSideInstructions = new ArrayList < String > ();
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

                if (tokenList.size() > 0 && isComparator(tokenList.get(0))) {
                    operatorTokenHold = tokenList.remove(0);
                    instructionsHold.add(crntSideInstructions);
                    continue;
                }
                // Add subsequent terms
                while (tokenList.size() > 0) {
                    if (isOperatorKey(tokenList.get(0))) {
                        operatorKeyHold = tokenList.remove(0);
                        continue;
                    }

                    String token = tokenList.remove(0);
                    if (isComparator(token)) {
                        // operatorInstrucHold = logicGen.operandTranslate(token, ILine + instructionCount() + 1);
                        operatorTokenHold = token;
                        break;
                    } else
                        crntSideInstructions.add(logicGen.operatorTranslate(
                            extractOperands(token),
                            extractAfterOperands(token)));
                }
                if (instructionsHold.size() > 0)
                    crntSideInstructions.add(codeControl.store(255));
                instructionsHold.add(crntSideInstructions);
            }

            // Both sides are now built, and operand recorded
            // Adds both sides, and the operator loop check
            for (String instruc: instructionsHold.get(1))
                instructions.add(instruc);

            for (String instruc: instructionsHold.get(0))
                instructions.add(instruc);

            // Adds the subtract check - manditory for any check
            instructions.add(codeControl.sub("&255"));

            String[] logicInstructs = logicGen.comparatorTranslate(
                operatorTokenHold,
                ILine + instructionCount(),
                operatorKeyHold
            ).split("#");

            if(logicInstructs[0].equals("")){
                Error.syntaxError("Unsupported comparator used", lineNumber);
                return false;
            }

            // Adds loop for type of operand
            for (int i = 0; i < logicInstructs.length; i++)
                instructions.add(logicInstructs[i]);
        }

        // change skip pointers
        finalisePointers();
        return true;
    }

    private void finalisePointers() {
        String toBlock = instructions.get(instructions.size() - 1);
        toBlock = toBlock.substring(toBlock.indexOf(" ") + 1);

        for (int i = 0; i < instructions.size(); i++) {
            if (instructions.get(i).indexOf(SyntaxCfg.conditionPointerID) > -1) {
                String statement = instructions.get(i).substring(0, instructions.get(i).indexOf(" ") + 1);

                if (instructions.get(i).indexOf("true") > -1) {
                    instructions.set(i, statement + toBlock);
                } else {
                    instructions.set(i, statement + (ILine + instructionCount() + 1) + "\n");
                }

            }
        }
    }

    private boolean tokenise() {
        if (condition.indexOf(SyntaxCfg.variableOperand) > -1) {
            Error.syntaxError("Cannot include defining syntax in condition!", lineNumber);
            return false;
        }

        String temp = "";
        for (char t: condition.toCharArray()) {
            if (isSingleChar(t + "")) {
                if (!temp.equals("") && !isSingleChar(temp.substring(temp.length() - 1))) {
                    tokens.add(temp);
                    temp = "";
                }
            }else{
                if (isComparator(temp) || isOperatorKey(temp)) {
                    tokens.add(temp);
                    temp = "";
                }
            }
            temp += t;
        }
        tokens.add(temp);

        // ===== Validation check ===== \\
        boolean valid = false;
        for (String token : tokens) 
            for (String comparator : SyntaxCfg.comparators) 
                if(token.equals(comparator))
                    valid = true;
        
        if(valid)
            return relateVariables();
        return false;
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
                tokens.set(i, SyntaxCfg.variableID + addr + "");
            }
        }
        return true;
    }

    private boolean isSingleChar(String term) {
        for (String symbol: SyntaxCfg.singles)
            if (symbol.equals(term)) return true;

        for (String symbol: SyntaxCfg.operators)
            if (symbol.equals(term)) return true;
        return false;
    }

    public boolean isComparator(String term) {
        for (String symbol: SyntaxCfg.comparators)
            if (symbol.equals(term)) return true;
        return false;
    }

    public boolean isOperatorKey(String term) {
        for (String symbol: SyntaxCfg.comparatorKeys)
            if (symbol.equals(term)) return true;
        return false;
    }

    public String getRaw() {
        return raw;
    }
}