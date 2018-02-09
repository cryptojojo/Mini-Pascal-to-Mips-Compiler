package parser;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parser.SymbolTable;

/**
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */

public class SymbolTableTest {

	SymbolTable table = new SymbolTable();

	@Before
	public void setUp() throws Exception {
		this.table = new SymbolTable();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void addTest() {

		// adding identifiers to the hashmap with their corresponding types
		table.add("programName", SymbolType.PROGRAMTYPE);
		table.add("variableName", SymbolType.VARIABLETYPE);
		table.add("functionName", SymbolType.FUNCTIONTYPE);
		table.add("procedureName", SymbolType.PROCEDURETYPE);
		table.add("arrayName", SymbolType.ARRAYTYPE);

		// should all yield true
		assertTrue(table.isProgramName("programName"));
		assertTrue(table.isVariableName("variableName"));
		assertTrue(table.isFunctionName("functionName"));
		assertTrue(table.isProcedureName("procedureName"));
		assertTrue(table.isArrayName("arrayName"));

		// should all yield false - type being tested for is not correct
		assertFalse(table.isProgramName("arrayName"));
		assertFalse(table.isVariableName("functionName"));
		assertFalse(table.isFunctionName("variableName"));
		assertFalse(table.isProcedureName("programName"));
		assertFalse(table.isArrayName("procedureName"));

	}

	@Test
	public void addProgramNameTest() {

		table.addProgramName("programName");

		// should yield true
		assertTrue(table.isProgramName("programName"));

		// should yield false
		assertFalse(table.isArrayName("programName"));

	}

	@Test
	public void addVariableNameTest() {

		table.addVariableName("variableName");

		// should yield true
		assertTrue(table.isVariableName("variableName"));

		// should yield false
		assertFalse(table.isProgramName("variableName"));

	}

	@Test
	public void addFunctionNameTest() {

		table.addFunctionName("functionName");

		// should yield true
		assertTrue(table.isFunctionName("functionName"));

		// should yield false
		assertFalse(table.isProcedureName("functionName"));

	}

	@Test
	public void addProcedureNameTest() {

		table.addProcedureName("procedureName");

		// should yield true
		assertTrue(table.isProcedureName("procedureName"));

		// should yield false
		assertFalse(table.isProgramName("procedureName"));

	}

	@Test
	public void addArrayNameTest() {

		table.addArrayName("arrayName");

		// should yield true
		assertTrue(table.isArrayName("arrayName"));

		// should yield false
		assertFalse(table.isProgramName("arrayName"));

	}

}
