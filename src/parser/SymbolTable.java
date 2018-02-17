package parser;

import java.util.HashMap;

/**
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class SymbolTable {
	private HashMap<String, SymbolType> table;

	/**
	 * constructer for the SymbolTable class
	 */
	public SymbolTable() {
		table = new HashMap<>();

	}

	/**
	 * adds a symbol name and type to the hashmap
	 * 
	 * @param name
	 *            string for the name of the symbol
	 * @param type
	 *            SymbolType for the type of the symbol
	 */
	public void add(String name, SymbolType type) {

		table.put(name, type);
	}

	/**
	 * function to add the program name
	 * 
	 * @param name
	 *            the name of the program symbol
	 */
	public void addProgramName(String name) {
		this.add(name, SymbolType.PROGRAMTYPE);
	}

	/**
	 * function to add the variable name
	 * 
	 * @param name
	 *            the name of the variable symbol
	 */
	public void addVariableName(String name) {
		this.add(name, SymbolType.VARIABLETYPE);
	}

	/**
	 * function to add the function name
	 * 
	 * @param name
	 *            the name of the function symbol
	 */
	public void addFunctionName(String name) {
		this.add(name, SymbolType.FUNCTIONTYPE);
	}

	/**
	 * function to add the procedure name
	 * 
	 * @param name
	 *            the name of the procedure symbol
	 */
	public void addProcedureName(String name) {
		this.add(name, SymbolType.PROCEDURETYPE);
	}

	/**
	 * function to add the array name
	 * 
	 * @param name
	 *            the name of the array symbol
	 */
	public void addArrayName(String name) {
		this.add(name, SymbolType.ARRAYTYPE);
	}

	/**
	 * function for determining whether or not the symbol is a program
	 * 
	 * @param name
	 *            the name of the symbol to be determined
	 * @return boolean whether or not the symbol is a program
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
	 * function for determining whether or not the symbol is a variable
	 * 
	 * @param name
	 *            the name of the symbol to be determined
	 * @return boolean whether or not the symbol is a variable
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
	 * function for determining whether or not the symbol is a function
	 * 
	 * @param name
	 *            the name of the symbol to be determined
	 * @return boolean whether or not the symbol is a function
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
	 * function for determining whether or not the symbol is a procedure
	 * 
	 * @param name
	 *            the name of the symbol to be determined
	 * @return boolean whether or not the symbol is a procedure
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
	 * function for determining whether or not the symbol is an array
	 * 
	 * @param name
	 *            the name of the symbol to be determined
	 * @return boolean whether or not the symbol is an array
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
	 * for getting the symbol table (hashmap)
	 * 
	 * @return the symbol table
	 */
	public HashMap<String, SymbolType> getTable() {
		return table;
	}

	/**
	 * prints the symbol table to console
	 */
	public void printOut() {

		for (String name : table.keySet()) {

			String key = name.toString();
			String value = table.get(name).toString();
			System.out.println("  " + value + ": " + key);
		}

	}

}
