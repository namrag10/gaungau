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

	
	public static String comparatorTranslate(String comparator, int param, String comparatorKey){
		return comparatorTranslate(comparator, Integer.toString(param), comparatorKey);
	}


	public static String comparatorTranslate(String comparator, String param, String comparatorKey) {
		switch (comparatorKey) {
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
		switch (comparator) {
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