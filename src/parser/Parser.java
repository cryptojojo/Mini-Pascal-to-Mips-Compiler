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
	ArrayList<String> idList = new ArrayList<>();
	DeclarationsNode decNode = new DeclarationsNode();

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
		symTab.addProgramName(lexi);
		match(TokenType.SEMI);
		DeclarationsNode declarations = declarations();
		SubProgramDeclarationsNode subProgramDeclarations = subprogram_declarations();
		CompoundStatementNode compoundStatement = compound_statement();
		match(TokenType.PERIOD);

		ProgramNode progNode = new ProgramNode(progName, declarations, subProgramDeclarations, compoundStatement);
		return progNode; // if it makes it this far, it is a pascal program

	}

	/**
	 * identidier_list production rule for variable input or series of variable
	 * inputs
	 */
	public ArrayList<String> identifier_list() {
		lexi = this.lookahead.getLexeme();
		String name = lexi;
		match(TokenType.ID);
		symTab.addVariableName(lexi);
		idList.add(name);
		if (this.lookahead.getType() == TokenType.COMMA) {
			match(TokenType.COMMA);
			identifier_list();
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
	public void type() {
		if (this.lookahead.getType() == TokenType.ARRAY) {
			match(TokenType.ARRAY);
			match(TokenType.LEFTBRACKET);
			match(TokenType.NUMBER);
			match(TokenType.COLON);
			match(TokenType.NUMBER);
			match(TokenType.OF);
			standard_type();
		} else {
			standard_type();
		}

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
			subprogram_declaration();
			match(TokenType.SEMI);
			subprogram_declarations();
		} else {
			// lambda option
		}
		SubProgramDeclarationsNode subProDecNode = new SubProgramDeclarationsNode();
		return subProDecNode;
	}

	/**
	 * subprogram_declaration production rule for calling a series of other
	 * production rules in order to get a proper order
	 */
	public void subprogram_declaration() {
		subprogram_head();
		declarations();
		subprogram_declarations();
		compound_statement();
	}

	/**
	 * subprogram_head production rule for creating a function or a procedure
	 */
	public void subprogram_head() {

		if (this.lookahead.getType() == TokenType.FUNCTION) {
			match(TokenType.FUNCTION);
			String lexi = this.lookahead.getLexeme();
			match(TokenType.ID);
			this.symTab.addFunctionName(lexi);
			arguments();
			match(TokenType.COLON);
			standard_type();
			match(TokenType.SEMI);
		} else if (this.lookahead.getType() == TokenType.PROCEDURE) {
			match(TokenType.PROCEDURE);
			String lexi = this.lookahead.getLexeme();
			match(TokenType.ID);
			this.symTab.addProgramName(lexi);
			arguments();
			match(TokenType.SEMI);
		} else {
			error("in the subprogram_head function");
		}

	}

	/**
	 * arguments production rule for taking in parameters inside of parenthesis
	 */
	public void arguments() {
		if (this.lookahead.getType() == TokenType.LEFTPAR) {
			match(TokenType.LEFTPAR);
			parameter_list();
			match(TokenType.RIGHTPAR);
		} else {
			// lambda option
		}

	}

	/**
	 * parameter_list production rule for taking in an ID and it's corresponding
	 * types or a series of parameters separated by semicolons
	 */
	public void parameter_list() {
		identifier_list();
		match(TokenType.COLON);
		type();
		if (this.lookahead.getType() == TokenType.SEMI) {
			match(TokenType.SEMI);
			parameter_list();
		} else {
			// just the first option
		}
	}

	/**
	 * compound_statement production rule for verifying the main code, starting with
	 * begin and ending with end
	 */
	public CompoundStatementNode compound_statement() {
		match(TokenType.BEGIN);
		optional_statements();
		match(TokenType.END);
		CompoundStatementNode comStatNode = new CompoundStatementNode();
		return comStatNode;
	}

	/**
	 * optional_statements production rule for statement_list or lambda
	 */
	public void optional_statements() {
		if (this.lookahead.getType() == TokenType.ID || (this.lookahead.getType() == TokenType.BEGIN)
				|| (this.lookahead.getType() == TokenType.IF) || (this.lookahead.getType() == TokenType.WHILE)
				|| (this.lookahead.getType() == TokenType.READ) || (this.lookahead.getType() == TokenType.WRITE)) {
			statement_list();
		} else {
			// lambda option
		}
	}

	/**
	 * statement_list production rule for statements or a series of statements
	 * separated by semicolons
	 */
	public void statement_list() {
		statement();
		if (this.lookahead.getType() == TokenType.SEMI) {
			match(TokenType.SEMI);
			statement_list();
		} else {
			// just the statement option
		}
	}

	/**
	 * statement production rule that creates all possible statements in the Pascal
	 * code
	 */
	public void statement() {
		if (this.lookahead.getType() == TokenType.ID) {
			if (symTab.isVariableName(this.lookahead.getLexeme())) {
				variable();
				assignop();
				expression();
			} else if (symTab.isProcedureName(this.lookahead.getLexeme())) {
				procedure_statement();
			} else {
				error("in statement function");
			}
		} else if (this.lookahead.getType() == TokenType.BEGIN) {
			compound_statement();
		} else if (this.lookahead.getType() == TokenType.IF) {
			match(TokenType.IF);
			expression();
			match(TokenType.THEN);
			statement();
			match(TokenType.ELSE);
			statement();
		} else if (this.lookahead.getType() == TokenType.WHILE) {
			match(TokenType.WHILE);
			expression();
			match(TokenType.DO);
			statement();
		} else if (this.lookahead.getType() == TokenType.READ) {
			match(TokenType.READ);
			match(TokenType.LEFTPAR);
			match(TokenType.ID);
			match(TokenType.RIGHTPAR);
		} else if (this.lookahead.getType() == TokenType.WRITE) {
			match(TokenType.WRITE);
			match(TokenType.LEFTPAR);
			expression();
			match(TokenType.RIGHTPAR);
		}
	}

	/**
	 * variable production rule for a variable ID or a variable ID with an
	 * expression after it surrounded by brackets
	 */
	public void variable() {
		match(TokenType.ID);
		if (this.lookahead.getType() == TokenType.LEFTBRACKET) {
			match(TokenType.LEFTBRACKET);
			expression();
			match(TokenType.RIGHTBRACKET);
		} else {
			// just the variable id option
		}

	}

	/**
	 * procedure_statement production rule for a procedure ID or a production ID
	 * with an expression after it surrounded by parenthesis
	 */
	private void procedure_statement() {
		match(TokenType.ID);
		if (this.lookahead.getType() == TokenType.LEFTCURL) {
			match(TokenType.LEFTCURL);
			expression_list();
			match(TokenType.RIGHTBRACKET);
		} else {
			// just the procedure id option
		}
	}

	/**
	 * expression_list production rule for expressions or a series of expressions
	 * separated by commas
	 */
	public void expression_list() {
		expression();
		if (this.lookahead.getType() == TokenType.COMMA) {
			match(TokenType.COMMA);
			expression_list();
		} else {
			// just the expression option
		}
	}

	/**
	 * expression production rule for a simple expression or a simple_expression
	 * compared to another simple_expression with a relop
	 */
	public void expression() {
		simple_expression();
		if (isRelop(lookahead)) {
			relop(); // consumes the relop
			simple_expression();
		} else {
			// just the simple expression option
		}
	}

	/**
	 * simple_expression for either a term and a simple_part or a sign and then a
	 * term and a simmple_part
	 */
	public void simple_expression() {
		if (this.lookahead.getType() == TokenType.PLUS || this.lookahead.getType() == TokenType.MINUS) {
			sign();
			term();
			simple_part();
		} else {
			term();
			simple_part();
		}
	}

	/**
	 * simple_part production rule for an addop and then a term and simple_part
	 */
	public void simple_part() {
		if (isAddop(lookahead)) {
			addop();
			term();
			simple_part();
		} else {
			// lambda option
		}
	}

	/**
	 * term production rule for a factor then a term_part
	 */
	public void term() {
		factor();
		term_part();
	}

	/**
	 * term_part production rule for a mulop and then a factor and term_part
	 */
	public void term_part() {
		if (isMulop(lookahead)) {
			mulop();
			factor();
			term_part();
		} else {
			// lambda option
		}
	}

	/**
	 * factor production rule for an ID, or an ID with an expression surrounding by
	 * brackets or an expression_list surrounded by parenthesis or a num, a single
	 * expression or a 'NOT' factor
	 */
	public void factor() {

		if (this.lookahead.getType() == TokenType.ID) {
			match(TokenType.ID);
			if (this.lookahead.getType() == TokenType.LEFTBRACKET) {
				match(TokenType.LEFTBRACKET);
				expression();
				match(TokenType.RIGHTBRACKET);
			} else if (this.lookahead.getType() == TokenType.LEFTPAR) {
				match(TokenType.LEFTPAR);
				expression_list();
				match(TokenType.RIGHTPAR);
			} else {
				// lambda option (Just ID)
			}
		} else if (this.lookahead.getType() == TokenType.NUMBER) {
			match(TokenType.NUMBER);
		} else if (this.lookahead.getType() == TokenType.LEFTPAR) {
			match(TokenType.LEFTPAR);
			expression();
			match(TokenType.RIGHTPAR);
		} else if (this.lookahead.getType() == TokenType.NOT) {
			match(TokenType.NOT);
			factor();
		} else {
			error("in factor function");
		}
	}

	/**
	 * sign production rule for a plus or minus
	 */
	public void sign() {
		if (this.lookahead.getType() == TokenType.PLUS) {
			match(TokenType.PLUS);
		} else if (this.lookahead.getType() == TokenType.MINUS) {
			match(TokenType.MINUS);
		} else {
			error("in sign function");
		}
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
