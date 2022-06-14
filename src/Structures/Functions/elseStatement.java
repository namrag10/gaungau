package Structures.Functions;

import Syntax.codeControl;

public class elseStatement extends builtinFunctionality {
    public elseStatement(int openAt){
        super(openAt, null, -1);
    }

    @Override
    public void closeHandle(int IClose){
        super.closeHandle(IClose);
        preBlock.add(codeControl.unconditionalBranch(IClose));
    }
}