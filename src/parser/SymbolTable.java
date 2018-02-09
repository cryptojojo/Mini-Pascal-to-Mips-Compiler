package parser;

import java.util.Arrays;
import java.util.HashMap;

public class SymbolTable {
	private HashMap<String, SymbolType> table;

	public SymbolTable() {
		table = new HashMap<>();
	}

	public void add(String name, SymbolType type) {

		this.table.put(name, type);
	}

	public void addProgramName(String name) {
		this.add(name, SymbolType.PROGRAMTYPE);
	}

	public void addVariableName(String name) {
		this.add(name, SymbolType.VARIABLETYPE);
	}

	public void addFunctionName(String name) {
		this.add(name, SymbolType.FUNCTIONTYPE);
	}

	public void addProcedureName(String name) {
		this.add(name, SymbolType.PROCEDURETYPE);
	}

	public void addArrayName(String name) {
		this.add(name, SymbolType.ARRAYTYPE);
	}

	public Boolean isProgramName(String name) {
		Boolean check;
		
		if (table.containsKey(name) && table.get(name) == SymbolType.PROGRAMTYPE) {
			check = true;
		} else {
			check = false;
		}

		return check;
	}

	public Boolean isVariableName(String name) {
		Boolean check;
		if (table.containsKey(name)) {
			check = true;
		} else {
			check = false;
		}

		return check;
	}

	public Boolean isFunctionName(String name) {
		Boolean check;
		if (table.containsKey(name)) {
			check = true;
		} else {
			check = false;
		}

		return check;
	}

	public Boolean isProcedureName(String name) {
		Boolean check;
		if (table.containsKey(name)) {
			check = true;
		} else {
			check = false;
		}
		return check;
	}

	public Boolean isArrayName(String name) {
		Boolean check;
		if (table.containsKey(name)) {
			check = true;
		} else {
			check = false;
		}

		return check;

	}

	public void printOut() {
		System.out.println(Arrays.asList(table));

	}

}