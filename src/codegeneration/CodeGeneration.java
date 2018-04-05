package codegeneration;

import syntaxtree.*;

/**
 * generates the assembly code from the syntax tree
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class CodeGeneration {

	private int currentReg;
	private ProgramNode progNode;
	private String asmCode;

	public CodeGeneration(ProgramNode progNode) {
		this.progNode = progNode;
		currentReg = 0;
		asmCode = "";
	}

	public void generate() {
		// variable declarations
		asmCode += ".data\n\n";
		for (VariableNode varNode : progNode.getVariables().getDeclarations()) {
			asmCode += varNode.getName() + " : .word 0\n";
		}

		asmCode += "__newline__: .asciiz \"\\n\"\n";

		// set up main
		asmCode += "\n.text\n\nmain:\n";

		// statements
		for (StatementNode statNode : progNode.getMain().getStateNodes()) {
			codeStatement(statNode, currentReg);
		}

	}

	public void codeStatement(StatementNode statNode, int reg) {

		if (statNode instanceof AssignmentStatementNode) {

		} else if (statNode instanceof WhileStatementNode) {

		} else if (statNode instanceof ProcedureNode) {

		} else if (statNode instanceof IfStatementNode) {

		} else if (statNode instanceof CompoundStatementNode) {
			for (StatementNode compStatNode : ((CompoundStatementNode) statNode).getStateNodes()) {
				codeStatement(compStatNode, reg);
			}

		} else if (statNode instanceof ReadNode) {

		} else if (statNode instanceof WriteNode) {
			codeWrite((WriteNode) statNode, reg);

		}

	}

	private void codeWrite(WriteNode writeNode, int reg) {
		asmCode += "\n#Syscall\n" + /* Expression */ "addi\t$v0,\t$zero,\t1\n" + "add\t$a0,\t" + reg + ",\t$zero\n"
				+ "syscall\n" + "li\t$v0,\t4" + "\nla\t$a0,__newline__\n" + "syscall\n";
	}

	public String getAsmCode() {
		return asmCode;
	}

}
