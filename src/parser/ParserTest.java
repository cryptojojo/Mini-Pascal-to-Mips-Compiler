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
		
		Parser p = new Parser("test/program_test.txt", true);
		
		try {
			p.program();
			System.out.println("Program part 1 pass");
		} catch (Exception e) {
			allTrue = false;
			System.out.println("Program part 1 fail");
		}

		
		
		
		assertTrue(allTrue);
}
	
	
	

}
