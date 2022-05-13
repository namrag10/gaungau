package Identify;

import Syntax.codeControl;

public class logicGen {

	public static String operatorTranslate(String operator, String param) {
		switch (operator) {
			case "+":
				return codeControl.add(param);
			case "-":
				return codeControl.sub(param);
			default:
				return null;
		}
	}

	public static String operandTranslate(String operand, int param, String operandKey){
		return operandTranslate(operand, Integer.toString(param), operandKey);
	}

	public static String operandTranslate(String operand, String param, String operandKey) {
		switch (operandKey) {
			case "&&":
				String  branch = codeControl.unconditionalBranch("$false");
				param = "#" + (Integer.parseInt(param) + 2) + "\n" + 
					branch.substring(0, branch.length()-1);
				break;
			case "||":
				param = "$true";
				break;
			default:
				param = "" + (Integer.parseInt(param) + 2);
		}
		switch (operand) {
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

	// public static String operatorKeywordTranslate(String operandKey, int param) {
	//     switch (operandKey) {
	//         case "&&":
	//             return codeControl.unconditionalBranch("$skip");
	//         case "||":
	//             return null;
	//         default:
	//             return null;
	//     }
	// }
}