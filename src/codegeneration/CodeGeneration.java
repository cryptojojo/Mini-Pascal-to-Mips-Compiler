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
			String strReg = "$s" + currentReg;
			codeStatement(statNode, strReg);
		}

		for (SubProgramNode subNode : progNode.getFunctions().getSubProgs()) {
			codeSubprogs(subNode);
		}

	}

	private void codeStatement(StatementNode statNode, String reg) {

		if (statNode instanceof AssignmentStatementNode) {

			codeAssignment((AssignmentStatementNode) statNode, reg);

		} else if (statNode instanceof WhileStatementNode) {

			codeWhile((WhileStatementNode) statNode, reg);

		} else if (statNode instanceof ProcedureNode) {

			codeProc((ProcedureNode) statNode);

		} else if (statNode instanceof IfStatementNode) {

			codeIf((IfStatementNode) statNode, reg);

		} else if (statNode instanceof CompoundStatementNode) {
			for (StatementNode compStatNode : ((CompoundStatementNode) statNode).getStateNodes()) {
				codeStatement(compStatNode, reg);
			}

		} else if (statNode instanceof ReadNode) {
			codeRead((ReadNode) statNode);

		} else if (statNode instanceof WriteNode) {
			codeWrite((WriteNode) statNode, reg);

		}

	}

	private void codeSubprogs(SubProgramNode subNode) {

	}

	// -----
	//
	// Coding the statements
	//
	// -----

	private void codeAssignment(AssignmentStatementNode assignNode, String reg) {

	}

	private void codeWhile(WhileStatementNode whileStat, String reg) {

	}

	private void codeProc(ProcedureNode procStat) {

	}

	private void codeIf(IfStatementNode ifStat, String reg) {

	}

	private void codeRead(ReadNode readNode) {

	}

	private void codeWrite(WriteNode write, String reg) {
		asmCode += "\n#Syscall\n" /* figure out */ + "addi\t$v0,\t$zero,\t1\n" + "add\t$a0,\t" + reg + ",\t$zero\n"
				+ "syscall\n" + "li\t$v0,\t4" + "\nla\t$a0, __newline__\n" + "syscall\n";
	}

	// -----
	//
	// Coding the expression stuff
	//
	// -----

	private void codeExp(ExpressionNode expNode, String reg) {

	}

	private void codeValue(ValueNode valNode, String reg) {

	}

	private void codeOperation(OperationNode opNode, String reg) {

	}

	// -----
	//
	// Return the asm code
	//
	// -----

	public String getAsmCode() {
		return asmCode;
	}

}
