package codegeneration;

import syntaxtree.ProgramNode;

public class CodeGeneration {

	private int currentTReg = 0;
	private ProgramNode progNode;
	private String asmCode = "";

	public CodeGeneration(ProgramNode progNode) {
		this.progNode = progNode;
	}

	
	
	public String getAsmCode() {
		return asmCode;

	}

}
