package codegeneration;

import java.util.HashMap;

import parser.DataType;
import parser.SymbolType;
import scanner.TokenType;
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
	private HashMap<String, String> memTable = new HashMap<String, String>();

	public CodeGeneration(ProgramNode progNode) {
		this.progNode = progNode;
		currentReg = 0;
		asmCode = "";
	}

	public void generate() {
		// variable declarations
		asmCode += ".data\n\n";
		for (VariableNode varNode : progNode.getVariables().getDeclarations()) {

			memTable.put(varNode.getName(), "MemAddressNULL");

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
		asmCode += "\n#Syscall\n" /* figure out */ + "addi   $v0,   $zero,   1\n" + "add   $a0,   " + reg
				+ ",   $zero\n" + "syscall\n" + "li   $v0,   4" + "\nla   $a0, __newline__\n" + "syscall\n";
	}

	// -----
	//
	// Coding the expression stuff
	//
	// -----

	private void codeExp(ExpressionNode expNode, String reg) {

		asmCode += "\n#Expression statement\n";
		if (expNode instanceof ValueNode) {
			codeValue((ValueNode) expNode, reg);
		} else if (expNode instanceof OperationNode) {
			codeOperation((OperationNode) expNode, reg);
		} else if (expNode instanceof VariableNode) {
			String var = memTable.get(((VariableNode) expNode).getName());

			asmCode += "lw   " + reg + ",  " + var + "\n";

			asmCode += "lw   " + reg + ",  " + var + "\n";
		}

	}

	private void codeValue(ValueNode valNode, String reg) {
		asmCode += "li   " + reg + ",   " + valNode.getAttribute() + "\n";

	}

	private void codeOperation(OperationNode opNode, String reg) {

	}

	// -----
	//
	// Pop and push stack
	//
	// -----

	/**
	 * Push all the $s registers, $fp and $sp to the stack
	 *
	 * @return The assembly code which executes this operation
	 */
	private void stackPush() {

		asmCode += "\n#Stack Push \n";
		asmCode += "addi   $sp,  $sp,  -";
		asmCode += 8 * 4 + 8 + '\n';

		for (int i = 8 - 1; i >= 0; i--) {
			asmCode += "sw   " + "$s" + i + ",   " + 4 * (i + 2) + "($sp)\n";
		}

		asmCode += "sw   $fp,   4($sp)\n";
		asmCode += "sw   $ra,   0($sp)\n";

	}

	/**
	 * Pop all the $s registers, $fp and $sp from the stack
	 *
	 * @return The assembly code which executes this operation
	 */
	private void stackPop() {

		asmCode += "\n#Stack Pop \n";

		for (int i = 8 - 1; i >= 0; i--) {
			asmCode += "lw   " + "$s" + i + ",   " + 4 * (i + 2) + "($sp)\n";
		}
		asmCode += "lw   $fp,   4($sp)\n";
		asmCode += "lw   $ra,   0($sp)\n";
		asmCode += "addi   $sp,   $sp,   ";
		asmCode += (8 * 4 + 8) + "\n";
		asmCode += "jr   $ra\n";

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
