package compiler;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import parser.*;
import semanticanalysis.SemanticAnalysis;
import syntaxtree.*;

/**
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */

public class CompilerMain {

	// Instance Variables
	public static Parser parse;
	public static String filename;
	public static SemanticAnalysis semAnalysis;

	public static void main(String[] args) {

		// taking in the argument which should be a filename in this case
		filename = args[0];
		parse = new Parser(filename, true);

		String noExtFileName = filename.substring(0, filename.lastIndexOf('.'));

		// Creates symbol table and parse tree strings
		ProgramNode progNode = parse.program();

		// creates semantic analysis object given the program node
		semAnalysis = new SemanticAnalysis(progNode);

		// semantic analysis is analyzed and put into a string
		String parseTree = semAnalysis.analyze().indentedToString(0);

		// symbol table is put into a string
		String symbolTable = parse.getSymbolTableStr();

		// prints the tree and the symbol table to the console
		System.out.print(parseTree);
		System.out.print(symbolTable);

		// prints the symbol table to a text file called [input name].table
		PrintWriter symTabWriter;
		try {
			symTabWriter = new PrintWriter(noExtFileName + ".table");
			symTabWriter.println(symbolTable);
			symTabWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// prints the parse tree to a text file called [input name].tree
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
