package testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.Parser;
import parser.SymbolType;

public class ParserTest {

	/**
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Program test - for part confirms that it accepts pascal code that should pass
	 * and the second part confirms that it rejects pascal code that shouldn't pass
	 */
	@Test
	public void testProgram() {
		boolean allTrue = true;

		Parser pPass = new Parser("test/parser/program_test_pass.pas", true);
		Parser pFail = new Parser("test/parser/program_test_fail.pas", true);

		// should pass
		try {
			pPass.program();
			System.out.println("Program part 1 pass");
		} catch (Exception e) {
			allTrue = false;
			System.out.println("Program part 1 fail");
		}

		// should catch
		try {
			pFail.program();
			System.out.println("Program part 2 fail");
			allTrue = false;
		} catch (Exception e) {
			System.out.println("Program part 2 pass");
		}

		System.out.println();
		assertTrue(allTrue);

	}

	/**
	 * Declaration test - for part confirms that it accepts pascal code that should
	 * pass and the second part confirms that it rejects pascal code that shouldn't
	 * pass
	 */
	@Test
	public void testDeclaration() {
		boolean allTrue = true;

		Parser pPass = new Parser("test/parser/declaration_test_pass.pas", true);
		Parser pFail = new Parser("test/parser/declaration_test_fail.pas", true);

		// should pass
		try {
			pPass.declarations();
			System.out.println("Declaration part 1 pass");
		} catch (Exception e) {
			allTrue = false;
			System.out.println("Declaration part 1 fail");
		}

		// should catch
		try {
			pFail.declarations();
			System.out.println("Declaration part 2 fail");
			allTrue = false;
		} catch (Exception e) {
			System.out.println("Declaration part 2 pass");
		}
		System.out.println();
		assertTrue(allTrue);
	}

	/**
	 * Subprogram Declarations test - for part confirms that it accepts pascal code
	 * that should pass and the second part confirms that it rejects pascal code
	 * that shouldn't pass
	 */
	@Test
	public void testSubprogramDeclarations() {
		boolean allTrue = true;

		Parser pPass = new Parser("test/parser/subprogramdeclarations_test_pass.pas", true);
		Parser pFail = new Parser("test/parser/subprogramdeclarations_test_fail.pas", true);

		// should pass
		try {
			pPass.subprogram_declaration();
			System.out.println("Subprogram Declaration part 1 pass");
		} catch (Exception e) {
			allTrue = false;
			System.out.println("Subprogram Declaration part 1 fail");
		}

		// should catch
		try {
			pFail.subprogram_declaration();
			System.out.println("Subprogram Declaration part 2 fail");
			allTrue = false;
		} catch (Exception e) {
			System.out.println("Subprogram Declaration part 2 pass");
		}

		System.out.println();
		assertTrue(allTrue);

	}

	/**
	 * Statement test - for part confirms that it accepts pascal code that should
	 * pass and the second part confirms that it rejects pascal code that shouldn't
	 * pass
	 */
	@Test
	public void testStatement() {
		boolean allTrue = true;

		
		
		Parser pPass = new Parser("test/parser/statement_test_pass.pas", true);
		Parser pFail = new Parser("test/parser/statement_test_fail.pas", true);

		pPass.addToTable("someVar", SymbolType.VARIABLETYPE);
		
		// should pass
		try {
			pPass.statement();
			System.out.println("Statement part 1 pass");
		} catch (Exception e) {
			allTrue = false;
			System.out.println("Statement part 1 fail");
		}

		// should catch
		try {
			pFail.statement();
			System.out.println("Statement part 2 fail");
			allTrue = false;
		} catch (Exception e) {
			System.out.println("Statement part 2 pass");
		}

		System.out.println();
		assertTrue(allTrue);
	}

	/**
	 * Simple expression test - for part confirms that it accepts pascal code that
	 * should pass and the second part confirms that it rejects pascal code that
	 * shouldn't pass
	 */
	@Test
	public void testSimpleExpression() {
		boolean allTrue = true;

		Parser pPass = new Parser("test/parser/simpleexpression_test_pass.pas", true);
		Parser pFail = new Parser("test/parser/simpleexpression_test_fail.pas", true);

		// should pass
		try {
			pPass.simple_expression();
			System.out.println("Simple Expression part 1 pass");
		} catch (Exception e) {
			allTrue = false;
			System.out.println("Simple Expression part 1 fail");
		}

		// should catch
		try {
			pFail.simple_expression();
			System.out.println("Simple Expression part 2 fail");
			allTrue = false;
		} catch (Exception e) {
			System.out.println("Simple Expression part 2 pass");
		}

		System.out.println();
		assertTrue(allTrue);
	}

	/**
	 * Factor test - for part confirms that it accepts pascal code that should pass
	 * and the second part confirms that it rejects pascal code that shouldn't pass
	 */
	@Test
	public void testFactor() {
		boolean allTrue = true;

		Parser pPass = new Parser("test/parser/factor_test_pass.pas", true);
		Parser pFail = new Parser("test/parser/factor_test_fail.pas", true);

		// should pass
		try {
			pPass.factor();
			System.out.println("Factor part 1 pass");
		} catch (Exception e) {
			allTrue = false;
			System.out.println("Factor part 1 fail");
		}

		// should catch
		try {
			pFail.factor();
			System.out.println("Factor part 2 fail");
			allTrue = false;
		} catch (Exception e) {
			System.out.println("Factor part 2 pass");
		}

		System.out.println();
		assertTrue(allTrue);
	}

}
