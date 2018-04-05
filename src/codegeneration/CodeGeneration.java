package codegeneration;

import syntaxtree.ProgramNode;
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
		asmCode += ".data\n\n";
		for (VariableNode varNodes : progNode.getVariables().getDeclarations()) {
			asmCode += varNodes.getName() + " : .word\n";
		}

		asmCode += "\n.text\n\nmain:\n";

	}

	public String getAsmCode() {
		return asmCode;
	}

}
