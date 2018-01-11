/**
 * 
 */
package scanner;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Joe
 *
 */
public class MyScannerTest {

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
	 * Test method for {@link scanner.MyScanner#nextToken()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testNextToken() throws IOException {
		// build scanner , point at file , call next token , expected lexeme and actual
		// lexeme

		// GETTING SCANNER SET UP
		String filename = "test/test1.txt";
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputStreamReader isr = new InputStreamReader(fis);
		MyScanner scanner = new MyScanner(isr);
		Token aToken = null;

		// TESTING
		aToken = scanner.nextToken();
		System.out.println(aToken);

		assertEquals(aToken.lexeme, "Program");
		assertEquals(aToken.type, TokenType.PROGRAM);

	}



}
