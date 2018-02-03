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
		boolean allTrue = true;

		Parser pPass = new Parser("test/parser/program_test_pass.txt", true);
		Parser pFail = new Parser("test/parser/program_test_fail.txt", true);

		// should run
		try {
			pPass.program();
			System.out.println("Program part 1 pass");
		} catch (Exception e) {
			allTrue = false;
			System.out.println("Program part 1 fail");
		}

		// shouldn't run
		try {
			pFail.program();
			System.out.println("Program part 2 fail");
			allTrue = false;
		} catch (Exception e) {
			System.out.println("Program part 2 pass");
		}

		assertTrue(allTrue);
	}

}
