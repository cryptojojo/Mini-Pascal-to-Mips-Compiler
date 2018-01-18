/**
 * 
 */
package scanner;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */

public class ScannerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link scanner.Scanner#nextToken()}. Verifies that each token
	 * in the string 'pasc' is being interoeted properly
	 * 
	 * @throws IOException
	 */
	@Test

	public void testNextToken() throws IOException {

		// GETTING SCANNER SET UP
		String pasc = "{ This is the simplest pascal program } program foo; begin end .";
		StringReader sr = new StringReader(pasc);

		Scanner scanner = new Scanner(sr);
		Token aToken = null;

		// TESTING
		aToken = scanner.nextToken();
		System.out.println(aToken);
		assertEquals(aToken.lexeme, "{ This is the simplest pascal program }");
		assertEquals(aToken.type, TokenType.COMMENT);

		aToken = scanner.nextToken();
		System.out.println(aToken);
		assertEquals(aToken.lexeme, "program");
		assertEquals(aToken.type, TokenType.PROGRAM);

		aToken = scanner.nextToken();
		System.out.println(aToken);
		assertEquals(aToken.lexeme, "foo");
		assertEquals(aToken.type, TokenType.ID);

		aToken = scanner.nextToken();
		System.out.println(aToken);
		assertEquals(aToken.lexeme, ";");
		assertEquals(aToken.type, TokenType.SEMI);

		aToken = scanner.nextToken();
		System.out.println(aToken);
		assertEquals(aToken.lexeme, "begin");
		assertEquals(aToken.type, TokenType.BEGIN);

		aToken = scanner.nextToken();
		System.out.println(aToken);
		assertEquals(aToken.lexeme, "end");
		assertEquals(aToken.type, TokenType.END);

		aToken = scanner.nextToken();
		System.out.println(aToken);
		assertEquals(aToken.lexeme, ".");
		assertEquals(aToken.type, TokenType.PERIOD);

	}

}
