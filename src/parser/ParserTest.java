package parser;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProgram() {
		Parser p = new Parser("test/program_test.txt", true);
		boolean allTrue = true;
		try {
			p.program();
		} catch (Exception e) {
			e.printStackTrace();
			allTrue = false;
			System.out.println("Youre dead");
		}

		assertTrue(allTrue);
}

}
