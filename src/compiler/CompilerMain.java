package compiler;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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

		// taking in the argument which should be a filename in this case
		filename = args[0];
		parse = new Parser(filename, true);

		// prints "passed" if it is a Pascal program and "failed if it isn't"
		if (parse.program()) {
			System.out.println("Passed \n");
		} else {
			System.out.println("Failed \n");
		}

		// prints the symbol table to the console
		System.out.println(parse.printSymbolTable());

		// prints the symbol table to a text file called SymbolTableOut.txt
		PrintWriter writer;
		try {
			writer = new PrintWriter("SymbolTableOut.txt");
			writer.println(parse.printSymbolTable());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
