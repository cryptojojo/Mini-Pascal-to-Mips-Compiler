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
		
		
		
		

	}

}
