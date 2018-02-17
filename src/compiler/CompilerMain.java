package compiler;

import parser.*;

/**
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */

public class CompilerMain {

	// Instance Variables
	public static Parser parse;
	public static String filename;

	public static void main(String[] args) {

		// taking in the argument which should be a filename in thhis case
		filename = args[0];
		parse = new Parser(filename, true);

		// prints "passed" if it is a Pascal program and "failed if it isnt"
		if (parse.program()) {
			System.out.println("Passed \n");
		}else {
			System.out.println("Failed \n");
		}

		// prints the symbol table
		System.out.println("Symbol Table:");
		parse.printSymbolTable();

	}

}
