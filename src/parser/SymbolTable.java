package parser;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * the symbol table for putting all identifiers in to check later regarding
 * their type
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class SymbolTable {
	private LinkedHashMap<String, SymbolType> table;
	private LinkedHashMap<String, Integer> arrayTable;

	/**
	 * Constructor for the SymbolTable class
	 */
	public SymbolTable() {
		table = new LinkedHashMap<>();
		arrayTable = new LinkedHashMap<>();
	}

	/**
	 * adds a symbol name and type to the hashmap
	 * 
	 * @param string
	 *            for the name of the symbol
	 * @param SymbolType
	 *            for the type of the symbol
	 */
	public void add(String name, SymbolType type) {

		table.put(name, type);
	}

	public void updatedTable(LinkedHashMap<String, SymbolType> updatedTable) {
		this.table = updatedTable;
	}

	/**
	 * adds the program name
	 * 
	 * @param the
	 *            name of the program symbol
	 */
	public void addProgramName(String name) {
		this.add(name, SymbolType.PROGRAMTYPE);
	}

	/**
	 * adds the variable name
	 * 
	 * @param the
	 *            name of the variable symbol
	 */
	public void addVariableName(String name) {
		this.add(name, SymbolType.VARIABLETYPE);
	}

	/**
	 * function to add the function name
	 * 
	 * @param the
	 *            name of the function symbol
	 */
	public void addFunctionName(String name) {
		this.add(name, SymbolType.FUNCTIONTYPE);
	}

	/**
	 * adds the procedure name
	 * 
	 * @param the
	 *            name of the procedure symbol
	 */
	public void addProcedureName(String name) {
		this.add(name, SymbolType.PROCEDURETYPE);
	}

	/**
	 * adds the array name
	 * 
	 * @param the
	 *            name of the array symbol
	 */
	public void addArrayName(String name) {
		this.add(name, SymbolType.ARRAYTYPE);
	}

	public void addArrayNameTable(String name, Integer size) {
		// this.add(name, SymbolType.ARRAYTYPE);
		this.arrayTable.put(name, size);
	}

	/**
	 * determines whether or not the symbol is a program
	 * 
	 * @param the
	 *            name of the symbol to be determined
	 * @return whether or not the symbol is a program
	 */
	public Boolean isProgramName(String name) {
		Boolean check;

		if (table.containsKey(name) && table.get(name) == SymbolType.PROGRAMTYPE) {
			check = true;
		} else {
			check = false;
		}

		return check;
	}

	/**
	 * determines whether or not the symbol is a variable
	 * 
	 * @param the
	 *            name of the symbol to be determined
	 * @return whether or not the symbol is a variable
	 */
	public Boolean isVariableName(String name) {
		Boolean check;
		if (table.containsKey(name) && table.get(name) == SymbolType.VARIABLETYPE) {
			check = true;
		} else {
			check = false;
		}

		return check;
	}

	/**
	 * determines whether or not the symbol is a function
	 * 
	 * @param the
	 *            name of the symbol to be determined
	 * @return whether or not the symbol is a function
	 */
	public Boolean isFunctionName(String name) {
		Boolean check;
		if (table.containsKey(name) && table.get(name) == SymbolType.FUNCTIONTYPE) {
			check = true;
		} else {
			check = false;
		}

		return check;
	}

	/**
	 * determines whether or not the symbol is a procedure
	 * 
	 * @param the
	 *            name of the symbol to be determined
	 * @return whether or not the symbol is a procedure
	 */
	public Boolean isProcedureName(String name) {
		Boolean check;
		if (table.containsKey(name) && table.get(name) == SymbolType.PROCEDURETYPE) {
			check = true;
		} else {
			check = false;
		}
		return check;
	}

	/**
	 * determines whether or not the symbol is an array
	 * 
	 * @param the
	 *            name of the symbol to be determined
	 * @return whether or not the symbol is an array
	 */
	public Boolean isArrayName(String name) {
		Boolean check;
		if (table.containsKey(name) && table.get(name) == SymbolType.ARRAYTYPE) {
			check = true;
		} else {
			check = false;
		}

		return check;

	}

	/**
	 * gets the symbol table (linkedhashmap)
	 * 
	 * @return the symbol table
	 */
	public LinkedHashMap<String, SymbolType> getTable() {
		return table;
	}

	public LinkedHashMap<String, Integer> getArrayTable() {
		return arrayTable;
	}

	/**
	 * prints the symbol table to console
	 * 
	 * @throws FileNotFoundException
	 */

	public SymbolType getType(String name) {

		return table.get(name);

	}

	public String toString() {

		String symTableString = "Symbol Table:";
		for (String name : table.keySet()) {

			String key = name.toString();
			String value = table.get(name).toString();
			symTableString += "\n  " + value + ": " + key;
		}
		return symTableString;

	}

}
