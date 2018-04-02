package testing;

import static org.junit.Assert.*;
import syntaxtree.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.Parser;
import semanticanalysis.SemanticAnalysis;

/**
 * testing for the SemanticAnalysis class
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class SemanticAnalysisTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {

		// Regular, valid program test
		//
		//
		Parser parse = new Parser("test/syntaxtree/bitcoin.pas", true);
		SemanticAnalysis semAnalysis = new SemanticAnalysis(parse.program(), parse.getSymbolTable());

		String parseTree = semAnalysis.analyze().indentedToString(0);

		String expectedResult = "Program: BitcoinConversion\n" + "|-- Declarations\n"
				+ "|-- --- Name: dollars (Expression Type: INTEGER)\n"
				+ "|-- --- Name: yen (Expression Type: INTEGER)\n"
				+ "|-- --- Name: bitcoins (Expression Type: INTEGER)\n" + "|-- SubProgramDeclarations\n"
				+ "|-- Compound Statement\n" + "|-- --- Assignment\n"
				+ "|-- --- --- Name: dollars (Expression Type: INTEGER)\n"
				+ "|-- --- --- Value: 1000000 (Expression Type: INTEGER)\n" + "|-- --- Assignment\n"
				+ "|-- --- --- Name: yen (Expression Type: INTEGER)\n"
				+ "|-- --- --- operation: MULTIPLY (Expression Type: INTEGER)\n"
				+ "|-- --- --- --- Name: dollars (Expression Type: INTEGER)\n"
				+ "|-- --- --- --- Value: 102 (Expression Type: INTEGER)\n" + "|-- --- Assignment\n"
				+ "|-- --- --- Name: bitcoins (Expression Type: INTEGER)\n"
				+ "|-- --- --- operation: DIVIDE (Expression Type: null)\n"
				+ "|-- --- --- --- Name: potato (Expression Type: null)\n"
				+ "|-- --- --- --- Value: 8500 (Expression Type: INTEGER)\n" + "|-- --- Write\n"
				+ "|-- --- --- Name: dollars (Expression Type: INTEGER)\n" + "|-- --- Write\n"
				+ "|-- --- --- Name: yen (Expression Type: INTEGER)\n" + "|-- --- Write\n"
				+ "|-- --- --- Name: bitcoins (Expression Type: INTEGER)\n" + "";

		assertEquals(expectedResult, parseTree);

		// Illegal program test
		//
		//
		parse = new Parser("test/syntaxtree/illegal.pas", true);
		semAnalysis = new SemanticAnalysis(parse.program(), parse.getSymbolTable());
		parseTree = semAnalysis.analyze().indentedToString(0);
		if (parseTree == expectedResult)
			assertTrue(false);
		else
			assertTrue(true);

		// Procedure call test
		//
		//
		parse = new Parser("test/syntaxtree/procedure.pas", true);
		semAnalysis = new SemanticAnalysis(parse.program(), parse.getSymbolTable());
		parseTree = semAnalysis.analyze().indentedToString(0);

		expectedResult = "Program: ProcedureTest\n" + "|-- Declarations\n"
				+ "|-- --- Name: a (Expression Type: INTEGER)\n" + "|-- SubProgramDeclarations\n"
				+ "|-- --- SubProgram: test\n" + "|-- --- --- Declarations\n" + "|-- --- --- SubProgramDeclarations\n"
				+ "|-- --- --- Compound Statement\n" + "|-- --- --- --- Assignment\n"
				+ "|-- --- --- --- --- Name: a (Expression Type: INTEGER)\n"
				+ "|-- --- --- --- --- Value: 5 (Expression Type: INTEGER)\n" + "|-- Compound Statement\n"
				+ "|-- --- Procedure: test\n" + "|-- --- Write\n" + "|-- --- --- Name: a (Expression Type: INTEGER)\n"
				+ "";

		assertEquals(expectedResult, parseTree);

		// if-then test (variable vs variable operation )
		//
		//
		parse = new Parser("test/syntaxtree/if_then_var_var.pas", true);
		semAnalysis = new SemanticAnalysis(parse.program(), parse.getSymbolTable());
		parseTree = semAnalysis.analyze().indentedToString(0);

		expectedResult = "Program: ifVar\n" + "|-- Declarations\n" + "|-- --- Name: a (Expression Type: INTEGER)\n"
				+ "|-- --- Name: b (Expression Type: INTEGER)\n" + "|-- --- Name: c (Expression Type: INTEGER)\n"
				+ "|-- --- Name: d (Expression Type: INTEGER)\n" + "|-- SubProgramDeclarations\n"
				+ "|-- Compound Statement\n" + "|-- --- Assignment\n"
				+ "|-- --- --- Name: a (Expression Type: INTEGER)\n"
				+ "|-- --- --- Value: 4 (Expression Type: INTEGER)\n" + "|-- --- Assignment\n"
				+ "|-- --- --- Name: b (Expression Type: INTEGER)\n"
				+ "|-- --- --- Value: 5 (Expression Type: INTEGER)\n" + "|-- --- If\n"
				+ "|-- --- --- operation: LESSTHAN (Expression Type: INTEGER)\n"
				+ "|-- --- --- --- Name: a (Expression Type: INTEGER)\n"
				+ "|-- --- --- --- Name: b (Expression Type: INTEGER)\n" + "|-- --- --- Assignment\n"
				+ "|-- --- --- --- Name: a (Expression Type: INTEGER)\n"
				+ "|-- --- --- --- Value: 1 (Expression Type: INTEGER)\n" + "|-- --- --- Assignment\n"
				+ "|-- --- --- --- Name: b (Expression Type: INTEGER)\n"
				+ "|-- --- --- --- Value: 2 (Expression Type: INTEGER)\n" + "";

		assertEquals(expectedResult, parseTree);

		// if-then test (variable vs number operation )
		//
		//
		parse = new Parser("test/syntaxtree/if_then_var_num.pas", true);
		semAnalysis = new SemanticAnalysis(parse.program(), parse.getSymbolTable());
		parseTree = semAnalysis.analyze().indentedToString(0);

		System.out.println(parseTree);

		expectedResult = "Program: ifNum\n" + "|-- Declarations\n" + "|-- --- Name: a (Expression Type: INTEGER)\n"
				+ "|-- --- Name: b (Expression Type: INTEGER)\n" + "|-- --- Name: c (Expression Type: INTEGER)\n"
				+ "|-- --- Name: d (Expression Type: INTEGER)\n" + "|-- SubProgramDeclarations\n"
				+ "|-- Compound Statement\n" + "|-- --- Assignment\n"
				+ "|-- --- --- Name: a (Expression Type: INTEGER)\n"
				+ "|-- --- --- Value: 4 (Expression Type: INTEGER)\n" + "|-- --- Assignment\n"
				+ "|-- --- --- Name: b (Expression Type: INTEGER)\n"
				+ "|-- --- --- Value: 5 (Expression Type: INTEGER)\n" + "|-- --- If\n"
				+ "|-- --- --- operation: LESSTHAN (Expression Type: INTEGER)\n"
				+ "|-- --- --- --- Name: a (Expression Type: INTEGER)\n"
				+ "|-- --- --- --- Value: 10 (Expression Type: INTEGER)\n" + "|-- --- --- Assignment\n"
				+ "|-- --- --- --- Name: a (Expression Type: INTEGER)\n"
				+ "|-- --- --- --- Value: 1 (Expression Type: INTEGER)\n" + "|-- --- --- Assignment\n"
				+ "|-- --- --- --- Name: b (Expression Type: INTEGER)\n"
				+ "|-- --- --- --- Value: 2 (Expression Type: INTEGER)\n" + "";

		assertEquals(expectedResult, parseTree);

	}

}
