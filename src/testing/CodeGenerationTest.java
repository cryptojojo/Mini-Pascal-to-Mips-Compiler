package testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.Parser;
import codegeneration.CodeGeneration;
import semanticanalysis.SemanticAnalysis;
import syntaxtree.ProgramNode;

public class CodeGenerationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {

		//
		// Testing for basic assignment (bitcoin example)
		//

		Parser parse = new Parser("test/codegen/assignment_bitcoin.pas", true);
		SemanticAnalysis semAnalysis = new SemanticAnalysis(parse.program(), parse.getSymbolTable());
		ProgramNode progNode = semAnalysis.analyze();
		CodeGeneration codeGen = new CodeGeneration(progNode);
		codeGen.generate();
		String assembly = codeGen.getAsmCode();

		String expectedResult = ".data\n" + "\n" + "dollars : .word 0\n" + "yen : .word 0\n" + "bitcoins : .word 0\n"
				+ "newLine: .asciiz \"\\n\"\n" + "\n" + ".text\n" + "\n" + "main:\n" + "\n" + "#Assignment Statement\n"
				+ "\n" + "#Expression statement\n" + "li   $s0,   10000\n" + "sw  $s0,   dollars\n" + "\n"
				+ "#Assignment Statement\n" + "\n" + "#Expression statement\n" + "\n" + "#Expression statement\n"
				+ "lw   $s0,  dollars\n" + "\n" + "#Expression statement\n" + "li   $s1,   107\n"
				+ "mult   $s0,   $s1\n" + "mflo   $s0\n" + "sw  $s0,   yen\n" + "\n" + "#Assignment Statement\n" + "\n"
				+ "#Expression statement\n" + "\n" + "#Expression statement\n" + "lw   $s0,  dollars\n" + "\n"
				+ "#Expression statement\n" + "li   $s1,   8000\n" + "div   $s0,   $s1\n" + "mflo   $s0\n"
				+ "sw  $s0,   bitcoins\n" + "\n" + "#Write Statement\n" + "\n" + "#Expression statement\n"
				+ "lw   $s0,  dollars\n" + "addi   $v0,   $zero,   1\n" + "add   $a0,   $s0,   $zero\n" + "syscall\n"
				+ "li   $v0,   4\n" + "la   $a0, newLine\n" + "syscall\n" + "\n" + "#Write Statement\n" + "\n"
				+ "#Expression statement\n" + "lw   $s0,  yen\n" + "addi   $v0,   $zero,   1\n"
				+ "add   $a0,   $s0,   $zero\n" + "syscall\n" + "li   $v0,   4\n" + "la   $a0, newLine\n" + "syscall\n"
				+ "\n" + "#Write Statement\n" + "\n" + "#Expression statement\n" + "lw   $s0,  bitcoins\n"
				+ "addi   $v0,   $zero,   1\n" + "add   $a0,   $s0,   $zero\n" + "syscall\n" + "li   $v0,   4\n"
				+ "la   $a0, newLine\n" + "syscall\n" + "\n" + "\n" + "#Exit Program \n" + "li  $v0, 10 \n";
		assertEquals(expectedResult, assembly);

		//
		// testing for an if statement where statement is false
		//

		parse = new Parser("test/codegen/if_statement_F.pas", true);
		semAnalysis = new SemanticAnalysis(parse.program(), parse.getSymbolTable());
		progNode = semAnalysis.analyze();
		codeGen = new CodeGeneration(progNode);
		codeGen.generate();
		assembly = codeGen.getAsmCode();

		expectedResult = ".data\n" + "\n" + "uno : .word 0\n" + "newLine: .asciiz \"\\n\"\n" + "\n" + ".text\n" + "\n"
				+ "main:\n" + "\n" + "#Assignment Statement\n" + "\n" + "#Expression statement\n" + "li   $s0,   10\n"
				+ "sw  $s0,   uno\n" + "\n" + "# If statement\n" + "\n" + "#Expression statement\n" + "lw   $s0,  uno\n"
				+ "\n" + "#Expression statement\n" + "li   $s1,   20\n" + "ble   $s0,   $s1,   else0\n" + "\n"
				+ "#Write Statement\n" + "\n" + "#Expression statement\n" + "li   $s0,   10\n"
				+ "addi   $v0,   $zero,   1\n" + "add   $a0,   $s0,   $zero\n" + "syscall\n" + "li   $v0,   4\n"
				+ "la   $a0, newLine\n" + "syscall\n" + "j  endIf0\n" + "else0:\n" + "\n" + "#Write Statement\n" + "\n"
				+ "#Expression statement\n" + "li   $s1,   20\n" + "addi   $v0,   $zero,   1\n"
				+ "add   $a0,   $s1,   $zero\n" + "syscall\n" + "li   $v0,   4\n" + "la   $a0, newLine\n" + "syscall\n"
				+ "endIf0:\n" + "\n" + "\n" + "#Exit Program \n" + "li  $v0, 10 \n";
		assertEquals(expectedResult, assembly);

		//
		// testing for an if statement where statement is true
		//

		parse = new Parser("test/codegen/if_statement_T.pas", true);
		semAnalysis = new SemanticAnalysis(parse.program(), parse.getSymbolTable());
		progNode = semAnalysis.analyze();
		codeGen = new CodeGeneration(progNode);
		codeGen.generate();
		assembly = codeGen.getAsmCode();

		expectedResult = ".data\n" + "\n" + "uno : .word 0\n" + "newLine: .asciiz \"\\n\"\n" + "\n" + ".text\n" + "\n"
				+ "main:\n" + "\n" + "#Assignment Statement\n" + "\n" + "#Expression statement\n" + "li   $s0,   10\n"
				+ "sw  $s0,   uno\n" + "\n" + "# If statement\n" + "\n" + "#Expression statement\n" + "lw   $s0,  uno\n"
				+ "\n" + "#Expression statement\n" + "li   $s1,   5\n" + "ble   $s0,   $s1,   else0\n" + "\n"
				+ "#Write Statement\n" + "\n" + "#Expression statement\n" + "li   $s0,   10\n"
				+ "addi   $v0,   $zero,   1\n" + "add   $a0,   $s0,   $zero\n" + "syscall\n" + "li   $v0,   4\n"
				+ "la   $a0, newLine\n" + "syscall\n" + "j  endIf0\n" + "else0:\n" + "\n" + "#Write Statement\n" + "\n"
				+ "#Expression statement\n" + "li   $s1,   20\n" + "addi   $v0,   $zero,   1\n"
				+ "add   $a0,   $s1,   $zero\n" + "syscall\n" + "li   $v0,   4\n" + "la   $a0, newLine\n" + "syscall\n"
				+ "endIf0:\n" + "\n" + "\n" + "#Exit Program \n" + "li  $v0, 10 \n";
		assertEquals(expectedResult, assembly);

	}

}
