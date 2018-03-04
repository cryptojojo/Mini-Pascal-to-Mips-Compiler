package compiler;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import parser.*;
import syntaxtree.*;

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

		String noExtFileName = filename.substring(0, filename.lastIndexOf('.'));

		/**
		 * prints "passed" if it is a Pascal program and "failed if it isn't" if
		 * (parse.program()) { System.out.println("Passed \n"); } else {
		 * System.out.println("Failed \n"); }
		 */

		// Creates symbol table and parse tree strings
		String parseTree = parse.program().indentedToString(0);
		String symbolTable = parse.printSymbolTable();

		System.out.print(parseTree);
		System.out.print(symbolTable);
		
		// prints the symbol table to a text file called SymbolTableOut.txt
		PrintWriter symTabWriter;
		try {
			symTabWriter = new PrintWriter(noExtFileName + ".table");
			symTabWriter.println(symbolTable);
			symTabWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintWriter pareseTreeWriter;
		try {
			pareseTreeWriter = new PrintWriter(noExtFileName + ".tree");
			pareseTreeWriter.println(parseTree);
			pareseTreeWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
