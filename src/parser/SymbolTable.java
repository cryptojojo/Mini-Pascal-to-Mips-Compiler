package parser;

import java.util.HashMap;

public class SymbolTable {
	private HashMap<String, symData> table;

	public SymbolTable() {
		table = new HashMap<>();
	}

	public void add(String name, SymbolType type) {
		symData data = new symData();
		data.name = name;
		data.kind = type;
		this.table.put(name, data);
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

	private class symData {
		public String name;
		public SymbolType kind;

		public symData() {
		};
	}

}