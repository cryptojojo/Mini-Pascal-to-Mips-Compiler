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
				fis = new FileInputStream("test/in.txt");
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
			// lambda option
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
			error("standard_type");
		}

	}

	private void subprogram_declarations() {

	}

	private void subprogram_declaration() {
		subprogram_head();
		declarations();
		subprogram_declarations();
		compound_statement();
	}

	private void subprogram_head() {

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
			// lambda option (first option: no semicolon or parameter_list call)
		}
	}

	private void compound_statement() {
		match(TokenType.BEGIN);
		optional_statements();
		match(TokenType.END);

	}

	private void optional_statements() {

	}

	private void statement_list() {
		if (this.lookahead.getType() == TokenType.SEMI) {
			statement();
			match(TokenType.SEMI);
			statement_list();
		} else {
			statement();
		}
	}

	private void statement() {

	}

	private void variable() {
		match(TokenType.ID);
		if (this.lookahead.getType() == TokenType.LEFTBRACKET) {
			match(TokenType.LEFTBRACKET);
			expression_list();
			match(TokenType.RIGHTBRACKET);
		} else {
			// lambda option (just ID)
		}

	}

	private void procedure_statement() {
		// Ignoring for now
	}

	private void expression_list() {

	}

	private void expression() {

	}

	private void simple_expression() {

	}

	private void simple_part() {

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
		}

	}

	private void sign() {
		if (this.lookahead.getType() == TokenType.PLUS) {
			match(TokenType.PLUS);
		} else if (this.lookahead.getType() == TokenType.MINUS) {
			match(TokenType.MINUS);
		}
	}

	// Methods

	public void exp() {
		term();
		exp_prime();
	}

	public void exp_prime() {
		if (lookahead.getType() == TokenType.PLUS || lookahead.getType() == TokenType.MINUS) {
			addop();
			term();
			exp_prime();
		} else {
			// lambda option
		}
	}

	private boolean isAddop(Token token) {
		boolean answer = false;
		if (token.getType() == TokenType.PLUS || token.getType() == TokenType.MINUS) {
			answer = true;
		}
		return answer;
	}

	public void addop() {
		if (lookahead.getType() == TokenType.PLUS) {
			match(TokenType.PLUS);
		} else if (lookahead.getType() == TokenType.MINUS) {
			match(TokenType.MINUS);
		} else {
			error("Addop");
		}
	}

	private boolean isMulop(Token token) {
		boolean answer = false;
		if (token.getType() == TokenType.MULTIPLY || token.getType() == TokenType.DIVIDE) {
			answer = true;
		}
		return answer;
	}

	public void mulop() {
		if (lookahead.getType() == TokenType.MULTIPLY) {
			match(TokenType.MULTIPLY);
		} else if (lookahead.getType() == TokenType.DIVIDE) {
			match(TokenType.DIVIDE);
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
		System.out.println("match( " + expected + ")");
		if (this.lookahead.getType() == expected) {
			try {
				this.lookahead = scanner.nextToken();
				if (this.lookahead == null) {
					this.lookahead = new Token("End of File", null);
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
	public void error(String message) {
		System.out.println("Error" + message);
		// System.exit( 1);
	}
}
