package Structures.Functions;

import ErrorHandle.Error;
import Identify.codeMatrix;
import Structures.Meta.Condition;
import Syntax.codeControl;

public class rawStatement extends builtinFunctionality {

	private String parameter = "";

	public rawStatement(int openAt, Condition condition, int Line) {
		super(openAt, condition, Line);
		parameter = getValue(condition.getRaw()).toUpperCase();
		String value = "";
		int chars = (parameter.indexOf("&") == 0) ? 4 : 3;
		codeMatrix matrix = new codeMatrix();

		
		if(parameter.length() > chars){
			value = parameter.substring(chars);
			parameter = parameter.substring(0, chars);
		}


		if(matrix.code(parameter) > -1){
			preBlock.add(codeControl.custom(parameter + " " + value));
		}else
			Error.syntaxError("Instruction not found", Line);
		
		
	}

	private String getValue(String raw){
		String ret = raw.substring(0, raw.length() -1);
		return ret.substring(1);

	}
}