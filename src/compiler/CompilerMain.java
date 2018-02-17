package compiler;

import parser.*;

public class CompilerMain {

	public static Parser parse;
	public static String filename;

	public static void main(String[] args) {

		filename = args[0];
		parse = new Parser(filename, true);

		if (parse.program()) {
			System.out.println("Passed");
		}
		
		parse.printSymbolTable();
		
	}

}
