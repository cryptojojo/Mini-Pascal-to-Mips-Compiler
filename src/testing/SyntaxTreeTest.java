package testing;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.Parser;

public class SyntaxTreeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {

		// taking in the argument which should be a filename in this case
		Parser parse = new Parser("program potato; begin end .", false);

		String expectedResult = "Program: potato\n" + "|-- Declarations\n" + "|-- SubProgramDeclarations\n"
				+ "|-- Compound Statement\n" + "";

		// Creates symbol table and parse tree strings
		String parseTree = parse.program().indentedToString(0);

		System.out.println(parseTree);

		assertEquals(expectedResult, parseTree);

	}

}
