package codegeneration;

import java.util.HashMap;

import parser.DataType;
import parser.SymbolType;
import scanner.TokenType;
import syntaxtree.*;

/**
 * generates the assembly code from the syntax tree (program node)
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class CodeGeneration {

	private int currentReg;
	private ProgramNode progNode;
	private String asmCode;
	private int whileDoNum;
	private int ifNum;
	private HashMap<String, String> memTable = new HashMap<String, String>();

	public CodeGeneration(ProgramNode progNode) {
		this.progNode = progNode;
		currentReg = 0;
		whileDoNum = 0;
		ifNum = 0;

		asmCode = "";
	}

	public void generate() {
		// variable declarations
		asmCode += ".data\n\n";
		for (VariableNode varNode : progNode.getVariables().getDeclarations()) {

			memTable.put(varNode.getName(), varNode.getName());

			asmCode += varNode.getName() + " : .word 0\n";
		}

		asmCode += "newLine: .asciiz \"\\n\"\n";

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

		// exits the program
		asmCode += "\n\n#Exit Program \nli  $v0, 10 \n";

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

		asmCode += "\n#Assignment Statement\n";
		codeExp(assignNode.getExpression(), reg);
		asmCode += "sw  " + reg + ",   " + memTable.get(assignNode.getLvalue().getName()) + '\n';

	}

	private void codeWhile(WhileStatementNode whileStat, String reg) {

		asmCode += "\n# while-do loop\n";
		asmCode += "whileDoNum" + whileDoNum + ":\n";

		codeExp(whileStat.getTest(), reg);

		asmCode += "endWhile" + whileDoNum + "\n";

		reg = "$s" + ++currentReg;

		codeStatement(whileStat.getDo(), reg);

		asmCode += "j whileDoNum" + whileDoNum + "\n";
		asmCode += "endWhile" + whileDoNum + ":\n";

		whileDoNum++;
		currentReg--;

	}

	private void codeProc(ProcedureNode procStat) {

	}

	private void codeIf(IfStatementNode ifStat, String reg) {

		String secReg;
		asmCode += "\n# If statement\n";

		if (ifStat.getTest() instanceof ValueNode) {
			codeExp(ifStat.getTest(), reg);
			secReg = "$s" + ++currentReg;
			asmCode += "li  " + secReg + ",  " + "1\n";
			asmCode += "bne  " + reg + ",  " + secReg + ",  ";
			asmCode += "else" + ifNum + "\n";
		} else {
			codeOperation((OperationNode) ifStat.getTest(), reg);
			asmCode += "else" + ifNum + "\n";
		}

		reg = "$s" + currentReg++;
		codeStatement(ifStat.getThenStatement(), reg);
		asmCode += "j  endIf" + ifNum + "\n";

		reg = "$s" + currentReg++;
		asmCode += "else" + ifNum + ":\n";
		codeStatement(ifStat.getElseStatement(), reg);
		asmCode += "endIf" + ifNum + ":\n";

		ifNum++;
		currentReg -= 2;

	}

	private void codeRead(ReadNode readNode) {

	}

	private void codeWrite(WriteNode writeNode, String reg) {
		asmCode += "\n#Write Statement\n";
		codeExp(writeNode.getContent(), reg);
		asmCode += "addi   $v0,   $zero,   1\n" + "add   $a0,   " + reg + ",   $zero\n" + "syscall\n" + "li   $v0,   4"
				+ "\nla   $a0, newLine\n" + "syscall\n";
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
			String var = ((VariableNode) expNode).getName();

			asmCode += "lw   " + reg + ",  " + var + "\n";

		}

	}

	private void codeValue(ValueNode valNode, String reg) {
		asmCode += "li   " + reg + ",   " + valNode.getAttribute() + "\n";

	}

	private void codeOperation(OperationNode opNode, String reg) {

		ExpressionNode leftExp = opNode.getLeft();
		String regL = "$s" + currentReg++;
		codeExp(leftExp, regL);

		ExpressionNode rightExp = opNode.getRight();
		String regR = "$s" + currentReg++;
		codeExp(rightExp, regR);

		TokenType opType = opNode.getOperation();

		if (opType == TokenType.PLUS)
			asmCode += "add   " + reg + ",   " + regL + ",   " + regR + "\n";
		else if (opType == TokenType.MINUS)
			asmCode += "sub   " + reg + ",   " + regL + ",   " + regR + "\n";
		else if (opType == TokenType.MULTIPLY) {
			asmCode += "mult   " + regL + ",   " + regR + "\n";
			asmCode += "mflo   " + reg + "\n";
		} else if (opType == TokenType.DIVIDE) {
			asmCode += "div   " + regL + ",   " + regR + "\n";
			asmCode += "mflo   " + reg + "\n";
		} else if (opType == TokenType.DIV) {
			asmCode += "div   " + regL + ",   " + regR + "\n";
			asmCode += "mflo   " + reg + "\n";
		} else if (opType == TokenType.MOD) {
			asmCode += "div   " + regL + ",   " + regR + "\n";
			asmCode += "mfhi   " + reg + "\n";
		} else if (opType == TokenType.AND)
			asmCode += "and   " + reg + ",   " + regL + ",   " + regR + "\n";
		else if (opType == TokenType.OR)
			asmCode += "or   " + reg + ",   " + regL + ",   " + regR + "\n";
		else if (opType == TokenType.LESSTHAN)
			asmCode += "bge   " + regL + ",   " + regR + ",   ";
		else if (opType == TokenType.GREATERTHAN)
			asmCode += "ble   " + regL + ",   " + regR + ",   ";
		else if (opType == TokenType.LESSTHANEQ)
			asmCode += "bgt   " + regL + ",   " + regR + ",   ";
		else if (opType == TokenType.GREATERTHANEQ)
			asmCode += "blt   " + regL + ",   " + regR + ",   ";
		else if (opType == TokenType.EQUAL)
			asmCode += "bne   " + regL + ",   " + regR + ",   ";
		else if (opType == TokenType.NOTEQUAL)
			asmCode += "beq   " + regL + ",   " + regR + ",   ";

		this.currentReg -= 2;

	}

	// -----
	//
	// Pop and push stack
	//
	// -----

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
