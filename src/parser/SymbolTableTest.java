package parser;

import static org.junit.Assert.*;

import java.security.KeyStore.Entry;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parser.SymbolTable;

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
		table.add("programName", SymbolType.PROGRAMTYPE);
		table.add("variableName", SymbolType.VARIABLETYPE);
		table.add("functionName", SymbolType.FUNCTIONTYPE);
		table.add("procedureName", SymbolType.PROCEDURETYPE);
		table.add("arrayName", SymbolType.ARRAYTYPE);

		assertTrue(table.isProgramName("programName"));
		assertTrue(table.isVariableName("variableName"));
		assertTrue(table.isFunctionName("functionName"));
		assertTrue(table.isProcedureName("procedureName"));
		assertTrue(table.isArrayName("arrayName"));

		table.printOut();

	}

}
