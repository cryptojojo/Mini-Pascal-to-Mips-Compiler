package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;

/**
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */

public class Parser {

	// Instance Variables
	private Token lookahead;
	private Scanner scanner;

	// Constructors
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

	public void program() {
		match(TokenType.PROGRAM);
		match(TokenType.ID);
		match(TokenType.SEMI);
		declarations();
		subprogram_declarations();
		compound_statement();
		match(TokenType.PERIOD);

	}

	private void identifier_list() {
		match(TokenType.ID);
		if (this.lookahead.getType() == TokenType.COMMA) {
			match(TokenType.COMMA);
			identifier_list();
		} else {
			error("in the indetifier_list function");
		}

	}

	private void declarations() {
		if (this.lookahead.getType() == TokenType.VAR) {
			match(TokenType.VAR);
			identifier_list();
			match(TokenType.COLON);
			type();
			match(TokenType.SEMI);
			declarations();
		} else {
			// lambda option
		}
	}

	private void type() {
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

	private void standard_type() {
		if (this.lookahead.getType() == TokenType.INTEGER) {
			match(TokenType.INTEGER);
		} else if (this.lookahead.getType() == TokenType.REAL)
			match(TokenType.REAL);
		else {
			error("in the standard_type function");
		}

	}

	private void subprogram_declarations() {
		if (this.lookahead.getType() == (TokenType.FUNCTION) || this.lookahead.getType() == (TokenType.PROCEDURE)) {
			subprogram_declaration();
			match(TokenType.SEMI);
			subprogram_declarations();
		} else {
			// lambda option
		}
	}

	private void subprogram_declaration() {
		subprogram_head();
		declarations();
		subprogram_declarations();
		compound_statement();
	}

	private void subprogram_head() {

		if (this.lookahead.getType() == TokenType.FUNCTION) {
			match(TokenType.FUNCTION);
			match(TokenType.ID);
			arguments();
			match(TokenType.COLON);
			standard_type();
			match(TokenType.SEMI);
		} else if (this.lookahead.getType() == TokenType.PROCEDURE) {
			match(TokenType.PROCEDURE);
			match(TokenType.ID);
			arguments();
		} else {
			error("in the subprogram_head function");
		}

	}

	private void arguments() {
		if (this.lookahead.getType() == TokenType.LEFTPAR) {
			match(TokenType.LEFTPAR);
			parameter_list();
			match(TokenType.LEFTPAR);
		} else {
			// lambda option
		}

	}

	private void parameter_list() {
		identifier_list();
		match(TokenType.COLON);
		type();
		if (this.lookahead.getType() == TokenType.SEMI) {
			match(TokenType.SEMI);
			parameter_list();
		} else {
			error("in the parameter_list function");
		}
	}

	private void compound_statement() {
		match(TokenType.BEGIN);
		optional_statements();
		match(TokenType.END);

	}

	private void optional_statements() {
		if (this.lookahead.getType() == TokenType.ID || (this.lookahead.getType() == TokenType.BEGIN)
				|| (this.lookahead.getType() == TokenType.IF) || (this.lookahead.getType() == TokenType.WHILE)
				|| (this.lookahead.getType() == TokenType.READ) || (this.lookahead.getType() == TokenType.WRITE)) {
			statement_list();
		} else {
			// lambda option
		}
	}

	private void statement_list() {
		statement();
		if (this.lookahead.getType() == TokenType.SEMI) {
			match(TokenType.SEMI);
			statement_list();
		} else {
			// just the statement option
		}
	}

	private void statement() {
		if (this.lookahead.getType() == TokenType.ID) {
			variable();
			match(TokenType.ASSIGN);
			expression();
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

	private void variable() {
		match(TokenType.ID);
		if (this.lookahead.getType() == TokenType.LEFTBRACKET) {
			match(TokenType.LEFTBRACKET);
			expression_list();
			match(TokenType.RIGHTBRACKET);
		} else {
			// just the id option
		}

	}

	// private void procedure_statement() {
	// Ignoring for now
	// }

	private void expression_list() {
		expression();
		if (this.lookahead.getType() == TokenType.COMMA) {
			match(TokenType.COMMA);
			expression_list();
		} else {
			// just the expression option
		}
	}

	private void expression() {
		simple_expression();
		if (isRelop(lookahead)) {
			relop(); // consumes the relop
			simple_expression();
		} else {
			// just the simple expression option
		}
	}

	private void simple_expression() {
		if (this.lookahead.getType() == TokenType.PLUS || this.lookahead.getType() == TokenType.MINUS) {
			sign();
			term();
			simple_part();
		} else {
			term();
			simple_part();
		}
	}

	private void simple_part() {
		if (isAddop(lookahead)) {
			addop();
			term();
			simple_part();
		} else {
			// lambda option
		}
	}

	public void term() {
		factor();
		term_part();
	}

	public void term_part() {
		if (isMulop(lookahead)) {
			mulop();
			factor();
			term_part();
		} else {
			// lambda option
		}
	}

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

	private void sign() {
		if (this.lookahead.getType() == TokenType.PLUS) {
			match(TokenType.PLUS);
		} else if (this.lookahead.getType() == TokenType.MINUS) {
			match(TokenType.MINUS);
		} else {
			error("in sign function");
		}
	}

	// RELOP, ADDOP, MULOP, ASSIGNOP

	private boolean isRelop(Token token) {
		boolean answer = false;
		if (token.getType() == TokenType.EQUAL || token.getType() == TokenType.NOTEQUAL
				|| token.getType() == TokenType.LESSTHAN || token.getType() == TokenType.LESSTHANEQ
				|| token.getType() == TokenType.GREATERTHANEQ || token.getType() == TokenType.GREATERTHAN) {
			answer = true;
		}
		return answer;
	}

	/**
	 * 
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
	 * 
	 * @param token
	 * @return
	 */
	private boolean isAddop(Token token) {
		boolean answer = false;
		if (token.getType() == TokenType.PLUS || token.getType() == TokenType.MINUS
				|| token.getType() == TokenType.OR) {
			answer = true;
		}
		return answer;
	}

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

	private boolean isMulop(Token token) {
		boolean answer = false;
		if (token.getType() == TokenType.MULTIPLY || token.getType() == TokenType.DIVIDE
				|| token.getType() == TokenType.DIV || token.getType() == TokenType.MOD
				|| token.getType() == TokenType.AND) {
			answer = true;
		}
		return answer;
	}

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
	 * Matches the expected token
	 * 
	 * @param expected
	 *            The expected token type
	 */
	public void match(TokenType expected) {
		//System.out.println("match (" + expected + ")");
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
	 * Error message printing
	 * 
	 * @param message
	 *            to be printed
	 * 
	 */
	public void error(String message) throws RuntimeException {
		System.out.println("Error " + message);
		//System.exit( 1);
		throw new java.lang.RuntimeException("Runtime Error");
	}
	
}
