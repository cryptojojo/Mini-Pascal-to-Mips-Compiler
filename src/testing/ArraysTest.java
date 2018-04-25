package testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import codegeneration.CodeGeneration;
import parser.Parser;
import semanticanalysis.SemanticAnalysis;
import syntaxtree.ProgramNode;

/**
 * Testing for array implementation all tests also verified in simulation
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class ArraysTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {

		//
		// Testing array in while statement (expression inside array index)
		//

		Parser parse = new Parser("test/arrays/array_while.pas", true);
		SemanticAnalysis semAnalysis = new SemanticAnalysis(parse.program(), parse.getSymbolTable());
		ProgramNode progNode = semAnalysis.analyze();
		CodeGeneration codeGen = new CodeGeneration(progNode, parse.getSymbolTable());
		codeGen.generate();
		String assembly = codeGen.getAsmCodeNoComments();

		String expectedResult = ".data\n" + "iter : .word 0\n" + "arr : .word  0,  0,  0,  0,  0, 0\n"
				+ "input:  .asciiz  \"Input: \" \n" + "newLine: .asciiz \"\\n\"\n" + ".text\n" + "main:\n"
				+ "li   $s0,   1\n" + "sw  $s0,   iter\n" + "whileDoNum0:\n" + "lw   $s0,  iter\n" + "li   $s1,   20\n"
				+ "bge   $s0,   $s1,   endWhile0\n" + "lw   $s1,  iter\n" + "li   $s2,   5\n" + "mult   $s1,   $s2\n"
				+ "mflo   $s1\n" + "lw   $s2,  iter\n" + "li   $t0,   4\n" + "mult   $t0,   $s2\n" + "mflo   $s2\n"
				+ "la   $s3,   arr\n" + "add   $s3,   $s2,   $s3\n" + "sw   $s1,   0($s3)\n" + "lw   $s1,  iter\n"
				+ "li   $s2,   1\n" + "add   $s1,   $s1,   $s2\n" + "sw  $s1,   iter\n" + "j whileDoNum0\n"
				+ "endWhile0:\n" + "li   $s0,   1\n" + "sw  $s0,   iter\n" + "whileDoNum1:\n" + "lw   $s0,  iter\n"
				+ "li   $s1,   5\n" + "bge   $s0,   $s1,   endWhile1\n" + "lw   $s2,  iter\n" + "li   $t0,   4\n"
				+ "mult   $t0,   $s2\n" + "mflo   $s2\n" + "la   $s3,   arr\n" + "add   $s3,   $s2,   $s3\n"
				+ "lw   $s1,   0($s3) \n" + "addi   $v0,   $zero,   1\n" + "add   $a0,   $s1,   $zero\n" + "syscall\n"
				+ "li   $v0,   4\n" + "la   $a0, newLine\n" + "syscall\n" + "lw   $s1,  iter\n" + "li   $s2,   1\n"
				+ "add   $s1,   $s1,   $s2\n" + "sw  $s1,   iter\n" + "j whileDoNum1\n" + "endWhile1:\n"
				+ "li  $v0, 10 \n" + "";
		assertEquals(expectedResult, assembly);

		//
		// Testing assignments in arrays
		//

		parse = new Parser("test/arrays/array_assignment.pas", true);
		semAnalysis = new SemanticAnalysis(parse.program(), parse.getSymbolTable());
		progNode = semAnalysis.analyze();
		codeGen = new CodeGeneration(progNode, parse.getSymbolTable());
		codeGen.generate();
		assembly = codeGen.getAsmCodeNoComments();

		expectedResult = ".data\n" + "iter : .word 0\n" + "arr : .word  0,  0,  0,  0, 0\n"
				+ "input:  .asciiz  \"Input: \" \n" + "newLine: .asciiz \"\\n\"\n" + ".text\n" + "main:\n"
				+ "li   $s0,   20\n" + "li   $s1,   0\n" + "li   $t0,   4\n" + "mult   $t0,   $s1\n" + "mflo   $s1\n"
				+ "la   $s2,   arr\n" + "add   $s2,   $s1,   $s2\n" + "sw   $s0,   0($s2)\n" + "li   $s1,   0\n"
				+ "li   $t0,   4\n" + "mult   $t0,   $s1\n" + "mflo   $s1\n" + "la   $s2,   arr\n"
				+ "add   $s2,   $s1,   $s2\n" + "lw   $s0,   0($s2) \n" + "addi   $v0,   $zero,   1\n"
				+ "add   $a0,   $s0,   $zero\n" + "syscall\n" + "li   $v0,   4\n" + "la   $a0, newLine\n" + "syscall\n"
				+ "li   $s0,   45\n" + "li   $s1,   1\n" + "li   $t0,   4\n" + "mult   $t0,   $s1\n" + "mflo   $s1\n"
				+ "la   $s2,   arr\n" + "add   $s2,   $s1,   $s2\n" + "sw   $s0,   0($s2)\n" + "li   $s1,   1\n"
				+ "li   $t0,   4\n" + "mult   $t0,   $s1\n" + "mflo   $s1\n" + "la   $s2,   arr\n"
				+ "add   $s2,   $s1,   $s2\n" + "lw   $s0,   0($s2) \n" + "addi   $v0,   $zero,   1\n"
				+ "add   $a0,   $s0,   $zero\n" + "syscall\n" + "li   $v0,   4\n" + "la   $a0, newLine\n" + "syscall\n"
				+ "li   $s2,   0\n" + "li   $t0,   4\n" + "mult   $t0,   $s2\n" + "mflo   $s2\n" + "la   $s3,   arr\n"
				+ "add   $s3,   $s2,   $s3\n" + "lw   $s0,   0($s3) \n" + "li   $s3,   1\n" + "li   $t0,   4\n"
				+ "mult   $t0,   $s3\n" + "mflo   $s3\n" + "la   $s4,   arr\n" + "add   $s4,   $s3,   $s4\n"
				+ "lw   $s1,   0($s4) \n" + "add   $s0,   $s0,   $s1\n" + "li   $s1,   2\n" + "li   $t0,   4\n"
				+ "mult   $t0,   $s1\n" + "mflo   $s1\n" + "la   $s2,   arr\n" + "add   $s2,   $s1,   $s2\n"
				+ "sw   $s0,   0($s2)\n" + "li   $s1,   2\n" + "li   $t0,   4\n" + "mult   $t0,   $s1\n"
				+ "mflo   $s1\n" + "la   $s2,   arr\n" + "add   $s2,   $s1,   $s2\n" + "lw   $s0,   0($s2) \n"
				+ "addi   $v0,   $zero,   1\n" + "add   $a0,   $s0,   $zero\n" + "syscall\n" + "li   $v0,   4\n"
				+ "la   $a0, newLine\n" + "syscall\n" + "li   $s2,   2\n" + "li   $t0,   4\n" + "mult   $t0,   $s2\n"
				+ "mflo   $s2\n" + "la   $s3,   arr\n" + "add   $s3,   $s2,   $s3\n" + "lw   $s0,   0($s3) \n"
				+ "li   $s1,   2\n" + "div   $s0,   $s1\n" + "mflo   $s0\n" + "addi   $v0,   $zero,   1\n"
				+ "add   $a0,   $s0,   $zero\n" + "syscall\n" + "li   $v0,   4\n" + "la   $a0, newLine\n" + "syscall\n"
				+ "li  $v0, 10 \n";
		assertEquals(expectedResult, assembly);

		//
		// Testing if statement within loop, reads and arrays
		//

		parse = new Parser("test/arrays/array_deep.pas", true);
		semAnalysis = new SemanticAnalysis(parse.program(), parse.getSymbolTable());
		progNode = semAnalysis.analyze();
		codeGen = new CodeGeneration(progNode, parse.getSymbolTable());
		codeGen.generate();
		assembly = codeGen.getAsmCodeNoComments();
		System.out.println(assembly);

		expectedResult = ".data\n" + "inOne : .word 0\n" + "inTwo : .word 0\n" + "arr : .word  0,  0,  0, 0\n"
				+ "input:  .asciiz  \"Input: \" \n" + "newLine: .asciiz \"\\n\"\n" + ".text\n" + "main:\n"
				+ "li  $v0,  4\n" + "la  $a0,  input \n" + "syscall\n" + "li  $v0, 5\n" + "syscall\n"
				+ "sw  $v0,  inOne\n" + "li  $v0,  4\n" + "la  $a0,  input \n" + "syscall\n" + "li  $v0, 5\n"
				+ "syscall\n" + "sw  $v0,  inTwo\n" + "lw   $s0,  inOne\n" + "li   $s1,   0\n" + "li   $t0,   4\n"
				+ "mult   $t0,   $s1\n" + "mflo   $s1\n" + "la   $s2,   arr\n" + "add   $s2,   $s1,   $s2\n"
				+ "sw   $s0,   0($s2)\n" + "lw   $s0,  inTwo\n" + "li   $s1,   1\n" + "li   $t0,   4\n"
				+ "mult   $t0,   $s1\n" + "mflo   $s1\n" + "la   $s2,   arr\n" + "add   $s2,   $s1,   $s2\n"
				+ "sw   $s0,   0($s2)\n" + "li   $s2,   0\n" + "li   $t0,   4\n" + "mult   $t0,   $s2\n"
				+ "mflo   $s2\n" + "la   $s3,   arr\n" + "add   $s3,   $s2,   $s3\n" + "lw   $s0,   0($s3) \n"
				+ "li   $s3,   1\n" + "li   $t0,   4\n" + "mult   $t0,   $s3\n" + "mflo   $s3\n" + "la   $s4,   arr\n"
				+ "add   $s4,   $s3,   $s4\n" + "lw   $s1,   0($s4) \n" + "mult   $s0,   $s1\n" + "mflo   $s0\n"
				+ "li   $s1,   2\n" + "li   $t0,   4\n" + "mult   $t0,   $s1\n" + "mflo   $s1\n" + "la   $s2,   arr\n"
				+ "add   $s2,   $s1,   $s2\n" + "sw   $s0,   0($s2)\n" + "whileDoNum0:\n" + "li   $s2,   2\n"
				+ "li   $t0,   4\n" + "mult   $t0,   $s2\n" + "mflo   $s2\n" + "la   $s3,   arr\n"
				+ "add   $s3,   $s2,   $s3\n" + "lw   $s0,   0($s3) \n" + "li   $s1,   0\n"
				+ "ble   $s0,   $s1,   endWhile0\n" + "li   $s3,   2\n" + "li   $t0,   4\n" + "mult   $t0,   $s3\n"
				+ "mflo   $s3\n" + "la   $s4,   arr\n" + "add   $s4,   $s3,   $s4\n" + "lw   $s1,   0($s4) \n"
				+ "li   $s2,   5\n" + "ble   $s1,   $s2,   else0\n" + "li   $s3,   2\n" + "li   $t0,   4\n"
				+ "mult   $t0,   $s3\n" + "mflo   $s3\n" + "la   $s4,   arr\n" + "add   $s4,   $s3,   $s4\n"
				+ "lw   $s1,   0($s4) \n" + "addi   $v0,   $zero,   1\n" + "add   $a0,   $s1,   $zero\n" + "syscall\n"
				+ "li   $v0,   4\n" + "la   $a0, newLine\n" + "syscall\n" + "j  endIf0\n" + "else0:\n"
				+ "li   $s2,   999\n" + "addi   $v0,   $zero,   1\n" + "add   $a0,   $s2,   $zero\n" + "syscall\n"
				+ "li   $v0,   4\n" + "la   $a0, newLine\n" + "syscall\n" + "endIf0:\n" + "li   $s3,   2\n"
				+ "li   $t0,   4\n" + "mult   $t0,   $s3\n" + "mflo   $s3\n" + "la   $s4,   arr\n"
				+ "add   $s4,   $s3,   $s4\n" + "lw   $s1,   0($s4) \n" + "li   $s2,   1\n"
				+ "sub   $s1,   $s1,   $s2\n" + "li   $s2,   2\n" + "li   $t0,   4\n" + "mult   $t0,   $s2\n"
				+ "mflo   $s2\n" + "la   $s3,   arr\n" + "add   $s3,   $s2,   $s3\n" + "sw   $s1,   0($s3)\n"
				+ "j whileDoNum0\n" + "endWhile0:\n" + "li  $v0, 10 \n";
		assertEquals(expectedResult, assembly);

	}

}
