package testing;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.Parser;
import scanner.TokenType;
import syntaxtree.DeclarationsNode;
import syntaxtree.ProgramNode;
import syntaxtree.SyntaxTreeNode;
import syntaxtree.VariableNode;

public class SyntaxTreeTest {

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

		String parseTree = parse.program().indentedToString(0);
		String expectedResult = "Program: BitcoinConversion\n" + "|-- Declarations\n" + "|-- --- Name: dollars\n"
				+ "|-- --- Name: yen\n" + "|-- --- Name: bitcoins\n" + "|-- SubProgramDeclarations\n"
				+ "|-- Compound Statement\n" + "|-- --- Assignment\n" + "|-- --- --- Name: dollars\n"
				+ "|-- --- --- Value: 1000000\n" + "|-- --- Assignment\n" + "|-- --- --- Name: yen\n"
				+ "|-- --- --- Operation: MULTIPLY\n" + "|-- --- --- --- Name: dollars\n"
				+ "|-- --- --- --- Value: 102\n" + "|-- --- Assignment\n" + "|-- --- --- Name: bitcoins\n"
				+ "|-- --- --- Operation: DIVIDE\n" + "|-- --- --- --- Name: potato\n" + "|-- --- --- --- Value: 8500\n"
				+ "|-- --- Write\n" + "|-- --- --- Name: dollars\n" + "|-- --- Write\n" + "|-- --- --- Name: yen\n"
				+ "|-- --- Write\n" + "|-- --- --- Name: bitcoins\n" + "";
		assertEquals(expectedResult, parseTree);

		// Illegal program test
		//
		//
		parse = new Parser("test/syntaxtree/illegal.pas", true);
		parseTree = parse.program().indentedToString(0);
		if (parseTree == expectedResult)
			assertTrue(false);
		else
			assertTrue(true);

		// Procedure call test
		//
		//
		parse = new Parser("test/syntaxtree/procedure.pas", true);
		parseTree = parse.program().indentedToString(0);

		expectedResult = "Program: ProcedureTest\n" + "|-- Declarations\n" + "|-- --- Name: a\n"
				+ "|-- SubProgramDeclarations\n" + "|-- --- SubProgram: test\n" + "|-- --- --- Declarations\n"
				+ "|-- --- --- SubProgramDeclarations\n" + "|-- --- --- Compound Statement\n"
				+ "|-- --- --- --- Assignment\n" + "|-- --- --- --- --- Name: a\n" + "|-- --- --- --- --- Value: 5\n"
				+ "|-- Compound Statement\n" + "|-- --- Procedure: test\n" + "|-- --- Write\n" + "|-- --- --- Name: a\n"
				+ "";

		assertEquals(expectedResult, parseTree);

		// if-then test (variable vs variable operation )
		//
		//
		parse = new Parser("test/syntaxtree/if_then_var_var.pas", true);
		parseTree = parse.program().indentedToString(0);

		expectedResult = "Program: ifVar\n" + "|-- Declarations\n" + "|-- --- Name: a\n" + "|-- --- Name: b\n"
				+ "|-- --- Name: c\n" + "|-- --- Name: d\n" + "|-- SubProgramDeclarations\n"
				+ "|-- Compound Statement\n" + "|-- --- Assignment\n" + "|-- --- --- Name: a\n"
				+ "|-- --- --- Value: 4\n" + "|-- --- Assignment\n" + "|-- --- --- Name: b\n" + "|-- --- --- Value: 5\n"
				+ "|-- --- If\n" + "|-- --- --- Operation: LESSTHAN\n" + "|-- --- --- --- Name: a\n"
				+ "|-- --- --- --- Name: b\n" + "|-- --- --- Assignment\n" + "|-- --- --- --- Name: a\n"
				+ "|-- --- --- --- Value: 1\n" + "|-- --- --- Assignment\n" + "|-- --- --- --- Name: b\n"
				+ "|-- --- --- --- Value: 2\n" + "";

		assertEquals(expectedResult, parseTree);

		// if-then test (variable vs number operation )
		//
		//
		parse = new Parser("test/syntaxtree/if_then_var_num.pas", true);
		parseTree = parse.program().indentedToString(0);
		System.out.println(parseTree);

		expectedResult = "Program: ifNum\n" + "|-- Declarations\n" + "|-- --- Name: a\n" + "|-- --- Name: b\n"
				+ "|-- --- Name: c\n" + "|-- --- Name: d\n" + "|-- SubProgramDeclarations\n"
				+ "|-- Compound Statement\n" + "|-- --- Assignment\n" + "|-- --- --- Name: a\n"
				+ "|-- --- --- Value: 4\n" + "|-- --- Assignment\n" + "|-- --- --- Name: b\n" + "|-- --- --- Value: 5\n"
				+ "|-- --- If\n" + "|-- --- --- Operation: LESSTHAN\n" + "|-- --- --- --- Name: a\n"
				+ "|-- --- --- --- Value: 10\n" + "|-- --- --- Assignment\n" + "|-- --- --- --- Name: a\n"
				+ "|-- --- --- --- Value: 1\n" + "|-- --- --- Assignment\n" + "|-- --- --- --- Name: b\n"
				+ "|-- --- --- --- Value: 2\n" + "";

		assertEquals(expectedResult, parseTree);

	}

}
