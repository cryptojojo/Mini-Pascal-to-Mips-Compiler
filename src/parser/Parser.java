package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import scanner.*;
import syntaxtree.*;

/**
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */

public class Parser {

	SymbolTable symTab = new SymbolTable();

	// Instance Variables
	private Token lookahead;
	private Scanner scanner;
	String lexi = "";
	// has to declare here so IDs can be added be added recursively
	DeclarationsNode decNode = new DeclarationsNode();
	SubProgramDeclarationsNode subProDecsNode = new SubProgramDeclarationsNode();
	ArrayList<String> paramList = new ArrayList<String>();
	CompoundStatementNode comStat = new CompoundStatementNode();
	ArrayList<StatementNode> statNodeList = new ArrayList<StatementNode>();

	/**
	 * Constructor function for the parser facet of the compiler
	 * 
	 * @param text
	 *            takes in a string - either a string of the code or a file name
	 *            containing the code, differentiation depends on the next parameter
	 * @param isFilename
	 *            true if the string is a file name, false if its code
	 */
	public Parser(String text, boolean isFilename) {
		if (isFilename) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(text);
			} catch (FileNotFoundException ex) {
				error("No file");
			}
			InputStreamReader isr = new InputStreamReader(fis);
			scanner = new Scanner(isr);

		} else {
			scanner = new Scanner(new StringReader(text));
		}
		try {
			lookahead = scanner.nextToken();
		} catch (IOException ex) {
			error("Scan error");
		}

	}

	// Production rule functions

	/**
	 * program production rule, function to verify if the code in correct pascal
	 * 
	 * @return
	 */
	public ProgramNode program() {
		match(TokenType.PROGRAM);
		lexi = this.lookahead.getLexeme();
		String progName = lexi;
		match(TokenType.ID);
		ProgramNode progNode = new ProgramNode(progName);
		symTab.addProgramName(lexi);
		match(TokenType.SEMI);
		DeclarationsNode declarations = declarations();
		SubProgramDeclarationsNode subProgramDeclarations = subprogram_declarations();
		CompoundStatementNode compoundStatement = compound_statement();
		match(TokenType.PERIOD);

		progNode.setVariables(declarations);
		progNode.setMain(compoundStatement);
		progNode.setFunctions(subProgramDeclarations);

		return progNode; // if it makes it this far, it is a pascal program

	}

	/**
	 * identidier_list production rule for variable input or series of variable
	 * inputs
	 */
	public ArrayList<String> identifier_list() {
		ArrayList<String> idList = new ArrayList<>();
		lexi = this.lookahead.getLexeme();
		String name = lexi;
		match(TokenType.ID);
		symTab.addVariableName(lexi);
		idList.add(name);
		if (this.lookahead.getType() == TokenType.COMMA) {
			match(TokenType.COMMA);
			idList.addAll(identifier_list());
		} else {
			// just the id option
		}
		return idList;

	}

	/**
	 * declarations production rule for declaring a variable to its type, or a
	 * series of variables to their type
	 */
	public DeclarationsNode declarations() {
		if (this.lookahead.getType() == TokenType.VAR) {
			match(TokenType.VAR);
			ArrayList<String> identList = identifier_list();

			for (String ident : identList) {
				decNode.addVariable(new VariableNode(ident));
			}

			match(TokenType.COLON);
			type();
			match(TokenType.SEMI);
			declarations();
		} else {
			// lambda option
		}

		return decNode;
	}

	/**
	 * type production rule - can be just standard_type or can also be an array of
	 * standard_type
	 */
	public TokenType type() {
		TokenType tType;
		if (this.lookahead.getType() == TokenType.ARRAY) {
			match(TokenType.ARRAY);
			match(TokenType.LEFTBRACKET);
			match(TokenType.NUMBER);
			match(TokenType.COLON);
			match(TokenType.NUMBER);
			match(TokenType.OF);
			tType = standard_type();
		} else {
			tType = standard_type();
		}
		return tType;

	}

	/**
	 * standard_type production rule for taking in a number, either an integer or a
	 * real
	 */
	public TokenType standard_type() {
		if (this.lookahead.getType() == TokenType.INTEGER) {
			match(TokenType.INTEGER);
			return TokenType.INTEGER;
		} else if (this.lookahead.getType() == TokenType.REAL) {
			match(TokenType.REAL);
			return TokenType.REAL;
		} else {
			error("in the standard_type function");
			return TokenType.ERROR;
		}
	}

	/**
	 * subprogram_declarations production rule for taking in a
	 * subprogram_declaration or a series of subprogram_declarations separated by
	 * commas
	 */
	public SubProgramDeclarationsNode subprogram_declarations() {
		if (this.lookahead.getType() == (TokenType.FUNCTION) || this.lookahead.getType() == (TokenType.PROCEDURE)) {
			subProDecsNode.addSubProgramDeclaration(subprogram_declaration());
			match(TokenType.SEMI);
			subprogram_declaration();
		} else {
			// lambda option
		}

		return subProDecsNode;
	}

	/**
	 * subprogram_declaration production rule for calling a series of other
	 * production rules in order to get a proper order
	 */
	public SubProgramNode subprogram_declaration() {

		SubProgramHeadNode head = subprogram_head();
		DeclarationsNode vars = declarations();
		CompoundStatementNode main = compound_statement();
		SubProgramDeclarationsNode functions = subprogram_declarations();

		return new SubProgramNode(head, vars, main, functions);

	}

	/**
	 * subprogram_head production rule for creating a function or a procedure
	 */
	public SubProgramHeadNode subprogram_head() {
		String name = null;
		ArrayList<String> args = new ArrayList<String>();

		if (this.lookahead.getType() == TokenType.FUNCTION) {
			match(TokenType.FUNCTION);
			String lexi = this.lookahead.getLexeme();
			match(TokenType.ID);
			name = lexi;
			this.symTab.addFunctionName(lexi);
			args = arguments();
			match(TokenType.COLON);
			standard_type();
			match(TokenType.SEMI);
		} else if (this.lookahead.getType() == TokenType.PROCEDURE) {
			match(TokenType.PROCEDURE);
			String lexi = this.lookahead.getLexeme();
			match(TokenType.ID);
			name = lexi;
			this.symTab.addProgramName(lexi);
			args = arguments();
			match(TokenType.SEMI);
		} else {
			error("in the subprogram_head function");
		}

		return new SubProgramHeadNode(name, args);

	}

	/**
	 * arguments production rule for taking in parameters inside of parenthesis
	 */
	public ArrayList<String> arguments() {
		ArrayList<String> args = new ArrayList<String>();
		if (this.lookahead.getType() == TokenType.LEFTPAR) {
			match(TokenType.LEFTPAR);
			args = parameter_list();
			match(TokenType.RIGHTPAR);
		} else {
			// lambda option
		}

		return args;
	}

	/**
	 * parameter_list production rule for taking in an ID and it's corresponding
	 * types or a series of parameters separated by semicolons
	 */
	public ArrayList<String> parameter_list() {
		ArrayList<String> idList = identifier_list();
		match(TokenType.COLON);
		type();
		if (this.lookahead.getType() == TokenType.SEMI) {
			match(TokenType.SEMI);
			idList.addAll(parameter_list());
		} else {
			// just the first option
		}
		return paramList;
	}

	/**
	 * compound_statement production rule for verifying the main code, starting with
	 * begin and ending with end
	 */
	public CompoundStatementNode compound_statement() {
		CompoundStatementNode comStatNode = new CompoundStatementNode();
		match(TokenType.BEGIN);
		comStatNode = optional_statements();
		match(TokenType.END);
		return comStatNode;
	}

	/**
	 * optional_statements production rule for statement_list or lambda
	 */
	public CompoundStatementNode optional_statements() {
		CompoundStatementNode compStatNode = new CompoundStatementNode();
		if (this.lookahead.getType() == TokenType.ID || (this.lookahead.getType() == TokenType.BEGIN)
				|| (this.lookahead.getType() == TokenType.IF) || (this.lookahead.getType() == TokenType.WHILE)
				|| (this.lookahead.getType() == TokenType.READ) || (this.lookahead.getType() == TokenType.WRITE)) {
			compStatNode.addAllStateNodes(statement_list());

		} else {
			// lambda option
		}
		return compStatNode; // not sure what to put here
	}

	/**
	 * statement_list production rule for statements or a series of statements
	 * separated by semicolons
	 */
	public ArrayList<StatementNode> statement_list() {

		comStat.addStatement(statement());
		if (this.lookahead.getType() == TokenType.SEMI) {
			match(TokenType.SEMI);
			statNodeList.addAll(statement_list());
		} else {
			// just the statement option
		}
		return statNodeList;
	}

	/**
	 * statement production rule that creates all possible statements in the Pascal
	 * code
	 */
	public StatementNode statement() {
		StatementNode statNode = null;
		if (this.lookahead.getType() == TokenType.ID) {
			if (symTab.isVariableName(this.lookahead.getLexeme())) {
				AssignmentStatementNode assignStat = new AssignmentStatementNode();
				VariableNode var = variable();
				assignStat.setLvalue(var);
				assignop();
				ExpressionNode exp = expression();
				assignStat.setExpression(exp);
				return assignStat;
			} else if (symTab.isProcedureName(this.lookahead.getLexeme())) {
				return procedure_statement();
			} else {
				error("in statement function");
			}
		} else if (this.lookahead.getType() == TokenType.BEGIN) {
			statNode = compound_statement();
		} else if (this.lookahead.getType() == TokenType.IF) {
			IfStatementNode ifStatNode = new IfStatementNode();
			match(TokenType.IF);
			ifStatNode.setTest(expression());
			match(TokenType.THEN);
			ifStatNode.setThenStatement(statement());
			match(TokenType.ELSE);
			ifStatNode.setElseStatement(statement());
			return ifStatNode;
		} else if (this.lookahead.getType() == TokenType.WHILE) {
			WhileStatementNode whileStatNode = new WhileStatementNode();
			match(TokenType.WHILE);
			whileStatNode.setTest(expression());
			match(TokenType.DO);
			whileStatNode.setDoStatement(statement());
			return whileStatNode;
		} else if (this.lookahead.getType() == TokenType.READ) {
			match(TokenType.READ);
			match(TokenType.LEFTPAR);
			String id = lookahead.getLexeme();
			match(TokenType.ID);
			match(TokenType.RIGHTPAR);
			VariableNode varNode = new VariableNode(id);
			return new ReadNode(varNode);
		} else if (this.lookahead.getType() == TokenType.WRITE) {
			match(TokenType.WRITE);
			match(TokenType.LEFTPAR);
			WriteNode writeNode = new WriteNode(expression());
			match(TokenType.RIGHTPAR);
			return writeNode;
		}
		return statNode;
	}

	/**
	 * variable production rule for a variable ID or a variable ID with an
	 * expression after it surrounded by brackets
	 */
	private VariableNode variable() {
		String varName = lookahead.getLexeme();
		if (!symTab.isVariableName(varName))
			error(varName + " has not been declared");
		if (!symTab.isArrayName(varName)) {
			VariableNode var = new VariableNode(varName);
			match(TokenType.ID);
			return var;
		} else {
			ArrayNode arrVar = new ArrayNode(varName);
			match(TokenType.ID);
			if (lookahead.getType() == TokenType.LEFTBRACKET) {
				match(TokenType.LEFTBRACKET);
				arrVar.setExpNode(expression());
				match(TokenType.RIGHTBRACKET);
			}
			return arrVar;
		}
	}

	/**
	 * procedure_statement production rule for a procedure ID or a production ID
	 * with an expression after it surrounded by parenthesis
	 */
	public ProcedureNode procedure_statement() {
		ArrayList<ExpressionNode> expList = null;
		ProcedureNode psNode = new ProcedureNode(lookahead.getLexeme());
		match(TokenType.ID);
		if (this.lookahead.getType() == TokenType.LEFTCURL) {
			match(TokenType.LEFTCURL);
			expList = expression_list();
			match(TokenType.RIGHTBRACKET);
		} else {
			// just the procedure id option
		}
		psNode.addAllExpressions(expList);
		return psNode;

	}

	/**
	 * expression_list production rule for expressions or a series of expressions
	 * separated by commas
	 */
	public ArrayList<ExpressionNode> expression_list() {
		ArrayList<ExpressionNode> exList = new ArrayList<>();
		exList.add(expression());
		if (lookahead.getType() == TokenType.COMMA) {
			match(TokenType.COMMA);
			exList.addAll(expression_list());
		}
		return exList;
	}

	/**
	 * expression production rule for a simple expression or a simple_expression
	 * compared to another simple_expression with a relop
	 * 
	 * @return
	 */
	public ExpressionNode expression() {
		ExpressionNode left = simple_expression();
		TokenType leftType = left.getType();
		if (isRelop(lookahead)) {
			OperationNode operNode = new OperationNode(lookahead.getType());
			if (leftType.equals(TokenType.REAL)) {
				operNode.setType(TokenType.REAL);
			} else {
				operNode.setType(TokenType.INTEGER);
			}
			operNode.setLeft(left);
			match(lookahead.getType());
			operNode.setRight(simple_expression());
			return operNode;
		}
		return left;
	}

	/**
	 * simple_expression for either a term and a simple_part or a sign and then a
	 * term and a simmple_part
	 */
	public ExpressionNode simple_expression() {
		ExpressionNode exp;
		if (this.lookahead.getType() == TokenType.PLUS || this.lookahead.getType() == TokenType.MINUS) {
			sign();
			exp = term();
			simple_part(exp);
		} else {
			exp = term();
			simple_part(exp);
		}
		return exp;
	}

	/**
	 * simple_part production rule for an addop and then a term and simple_part
	 */
	public ExpressionNode simple_part(ExpressionNode pos) {
		if (isAddop(lookahead)) {
			OperationNode oper = new OperationNode(lookahead.getType());
			addop();
			ExpressionNode right = term();
			oper.setLeft(pos);
			oper.setRight(simple_part(right));
			return oper;
		} else {
			return pos;
			// lambda option
		}
	}

	/**
	 * term production rule for a factor then a term_part
	 * 
	 * @return
	 */
	public ExpressionNode term() {
		ExpressionNode left = factor();
		return term_part(left);
	}

	/**
	 * term_part production rule for a mulop and then a factor and term_part
	 */
	public ExpressionNode term_part(ExpressionNode pos) {
		if (isMulop(lookahead)) {
			OperationNode oper = new OperationNode(lookahead.getType());
			mulop();
			ExpressionNode right = factor();
			oper.setLeft(pos);
			oper.setRight(term_part(right));
			return oper;
		} else {
			// lambda option
			return pos;
		}
	}

	/**
	 * factor production rule for an ID, or an ID with an expression surrounding by
	 * brackets or an expression_list surrounded by parenthesis or a num, a single
	 * expression or a 'NOT' factor
	 */
	public ExpressionNode factor() {
		ExpressionNode exper = null;
		if (this.lookahead.getType() == TokenType.ID) {
			String id = lookahead.getLexeme();
			match(TokenType.ID);
			if (this.lookahead.getType() == TokenType.LEFTBRACKET) {
				ArrayNode arrNode = new ArrayNode(id);
				match(TokenType.LEFTBRACKET);
				ExpressionNode exp = expression();
				arrNode.setExpNode(exp);
				match(TokenType.RIGHTBRACKET);
				return arrNode;
			} else if (this.lookahead.getType() == TokenType.LEFTPAR) {
				match(TokenType.LEFTPAR);
				expression_list();
				match(TokenType.RIGHTPAR);
				return exper; // no functions - returns null
			} else {
				VariableNode varNode = new VariableNode(id);
				return varNode;
			}
		} else if (this.lookahead.getType() == TokenType.NUMBER) {
			String num = lookahead.getLexeme();
			ValueNode valNode = new ValueNode(num);
			match(TokenType.NUMBER);
			return valNode;

		} else if (this.lookahead.getType() == TokenType.LEFTPAR) {
			match(TokenType.LEFTPAR);
			exper = expression();
			match(TokenType.RIGHTPAR);
		} else if (this.lookahead.getType() == TokenType.NOT) {
			match(TokenType.NOT);
			exper = factor();
		} else {
			error("in factor function");
		}
		return exper;
	}

	/**
	 * sign production rule for a plus or minus
	 */
	public TokenType sign() {
		TokenType tokenOut;
		if (this.lookahead.getType() == TokenType.PLUS) {
			tokenOut = TokenType.PLUS;
			match(TokenType.PLUS);
		} else if (this.lookahead.getType() == TokenType.MINUS) {
			tokenOut = TokenType.MINUS;
			match(TokenType.MINUS);
		} else {
			tokenOut = TokenType.ERROR;
			error("in sign function");
		}
		return tokenOut;
	}

	// RELOP, ADDOP, MULOP, ASSIGNOP

	/**
	 * For determining whether or not the token is a relative operator
	 * 
	 * @param token
	 *            the token to be determined
	 * @return answer whether or not the token is a relop (T/F)
	 */
	public boolean isRelop(Token token) {
		boolean answer = false;
		if (token.getType() == TokenType.EQUAL || token.getType() == TokenType.NOTEQUAL
				|| token.getType() == TokenType.LESSTHAN || token.getType() == TokenType.LESSTHANEQ
				|| token.getType() == TokenType.GREATERTHANEQ || token.getType() == TokenType.GREATERTHAN) {
			answer = true;
		}
		return answer;
	}

	/**
	 * For matching the relop and recognizing the token
	 */
	public void relop() {
		if (lookahead.getType() == TokenType.EQUAL) {
			match(TokenType.EQUAL);
		} else if (lookahead.getType() == TokenType.NOTEQUAL) {
			match(TokenType.NOTEQUAL);
		} else if (lookahead.getType() == TokenType.LESSTHAN) {
			match(TokenType.LESSTHAN);
		} else if (lookahead.getType() == TokenType.LESSTHANEQ) {
			match(TokenType.LESSTHANEQ);
		} else if (lookahead.getType() == TokenType.GREATERTHANEQ) {
			match(TokenType.GREATERTHANEQ);
		} else if (lookahead.getType() == TokenType.GREATERTHAN) {
			match(TokenType.GREATERTHAN);
		} else {
			error("Relop");
		}
	}

	/**
	 * For determining whether or not the token is an addop
	 * 
	 * @param token
	 *            the token to be determined
	 * @return answer whether or not the token is a relop
	 */
	public boolean isAddop(Token token) {
		boolean answer = false;
		if (token.getType() == TokenType.PLUS || token.getType() == TokenType.MINUS
				|| token.getType() == TokenType.OR) {
			answer = true;
		}
		return answer;
	}

	/**
	 * For matching the addop and recognizing the token
	 */
	public void addop() {
		if (lookahead.getType() == TokenType.PLUS) {
			match(TokenType.PLUS);
		} else if (lookahead.getType() == TokenType.MINUS) {
			match(TokenType.MINUS);
		} else if (lookahead.getType() == TokenType.OR) {
			match(TokenType.OR);
		} else {
			error("Addop");
		}
	}

	/**
	 * For determining whether or not the token is a mulop
	 * 
	 * @param token
	 *            the token to be determined
	 * @return answer whether or not the token is a mulop
	 */
	public boolean isMulop(Token token) {
		boolean answer = false;
		if (token.getType() == TokenType.MULTIPLY || token.getType() == TokenType.DIVIDE
				|| token.getType() == TokenType.DIV || token.getType() == TokenType.MOD
				|| token.getType() == TokenType.AND) {
			answer = true;
		}
		return answer;
	}

	/**
	 * For matching the mulop and recognizing the token
	 */
	public void mulop() {
		if (lookahead.getType() == TokenType.MULTIPLY) {
			match(TokenType.MULTIPLY);
		} else if (lookahead.getType() == TokenType.DIVIDE) {
			match(TokenType.DIVIDE);
		} else if (lookahead.getType() == TokenType.DIV) {
			match(TokenType.DIV);
		} else if (lookahead.getType() == TokenType.MOD) {
			match(TokenType.MOD);
		} else if (lookahead.getType() == TokenType.AND) {
			match(TokenType.AND);
		} else {
			error("Mulop");
		}
	}

	/**
	 * For recognizing the assign operator
	 */
	public void assignop() {
		if (lookahead.getType() == TokenType.COLON) {
			match(TokenType.COLON);
			match(TokenType.EQUAL);
		} else {
			error("in assignop");
		}
	}

	/**
	 * Matches the expected token
	 * 
	 * @param expected
	 *            The expected token type
	 */
	public void match(TokenType expected) {
		// System.out.println("match (" + expected + ")");
		if (this.lookahead.getType() == expected) {
			try {
				this.lookahead = scanner.nextToken();
				if (this.lookahead == null) {
					this.lookahead = new Token("End of File", -1);
				}
			} catch (IOException ex) {
				error("Scanner exception");
			}
		} else {
			error("Match of " + expected + " found " + this.lookahead.getType() + " instead.");
		}
	}

	/**
	 * Gets the symbol table as a string from SymbolTable
	 * 
	 * @return the symbol table as a string
	 */
	public String printSymbolTable() {
		return symTab.printOut();
	}

	/**
	 * Error message printing
	 * 
	 * @param message
	 *            to be printed
	 * 
	 */

	public void addToTable(String lexeme, SymbolType type) {
		symTab.add(lexeme, type);
	}

	public void error(String message) throws RuntimeException {
		System.out.println("Error " + message);
		// System.exit( 1);
		throw new java.lang.RuntimeException("Runtime Error");
	}

}
