package codegeneration;

import syntaxtree.ProgramNode;

/**
 * generates the assembly code from the syntax tree
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class CodeGeneration {

	private int currentTReg = 0;
	private ProgramNode progNode;
	private String asmCode = "";

	public CodeGeneration(ProgramNode progNode) {
		this.progNode = progNode;
	}

	public void generate() {
	}

	public String getAsmCode() {
		return asmCode;
	}

}
