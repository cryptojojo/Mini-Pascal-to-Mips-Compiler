package codegeneration;

import syntaxtree.*;

/**
 * generates the assembly code from the syntax tree
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class CodeGeneration {

	private int currentTReg;
	private ProgramNode progNode;
	private String asmCode;

	public CodeGeneration(ProgramNode progNode) {
		this.progNode = progNode;
		currentTReg = 0;
		asmCode = "";
	}

	public void generate() {
		// variable declarations
		asmCode += ".data\n\n";
		for (VariableNode varNode : progNode.getVariables().getDeclarations()) {
			asmCode += varNode.getName() + " : .word\n";
		}

		// set up main
		asmCode += "\n.text\n\nmain:\n";

		// statements
		for (StatementNode statNode : progNode.getMain().getStateNodes()) {
			codeStatement(statNode);
		}

	}

	public void codeStatement(StatementNode statNode) {

	}

	public String getAsmCode() {
		return asmCode;
	}

}
