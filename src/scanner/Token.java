package scanner;

/**
 * Represents the token read by the scanner, contains a lexeme and the type of
 * token
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */

public class Token {
	public String lexeme;
	public TokenType type;

	/**
	 * Sets the TokenType for each token retrieved by the scanner
	 *
	 *
	 * @param content
	 *            the lexeme that the scanner retrieved
	 * @param og
	 *            the type of lexeme: 1 = word, 2 = symbol, 3 = other, 4 = integer,
	 *            5 = float, 6 = positive exponent, 7 = negative exponent, 8 =
	 *            comment
	 * 
	 * @return The token type of the lexeme
	 */

	public TokenType setType(String content, Integer og) {
		TokenType set = null;
		String lcontent = content.toLowerCase();
		if (og == 1) {
			switch (lcontent) {
			case "and":
				set = TokenType.AND;
				break;
			case "array":
				set = TokenType.ARRAY;
				break;
			case "begin":
				set = TokenType.BEGIN;
				break;
			case "div":
				set = TokenType.DIV;
				break;
			case "do":
				set = TokenType.DO;
				break;
			case "else":
				set = TokenType.ELSE;
				break;
			case "end":
				set = TokenType.END;
				break;
			case "function":
				set = TokenType.FUNCTION;
				break;
			case "if":
				set = TokenType.IF;
				break;
			case "integer":
				set = TokenType.INTEGER;
				break;
			case "mod":
				set = TokenType.MOD;
				break;
			case "not":
				set = TokenType.NOT;
				break;
			case "of":
				set = TokenType.OF;
				break;
			case "or":
				set = TokenType.OR;
				break;
			case "procedure":
				set = TokenType.PROCEDURE;
				break;
			case "program":
				set = TokenType.PROGRAM;
				break;
			case "real":
				set = TokenType.REAL;
				break;
			case "then":
				set = TokenType.THEN;
				break;
			case "var":
				set = TokenType.VAR;
				break;
			case "while":
				set = TokenType.WHILE;
				break;
			case "read":
				set = TokenType.READ;
				break;
			case "write":
				set = TokenType.WRITE;
				break;

			default:
				set = TokenType.ID;
			}
		}

		else if (og == 2) {
			switch (lcontent) {
			case ";":
				set = TokenType.SEMI;
				break;
			case ",":
				set = TokenType.COMMA;
				break;
			case ".":
				set = TokenType.PERIOD;
				break;
			case ":":
				set = TokenType.COLON;
				break;
			case "[":
				set = TokenType.LEFTBRACKET;
				break;
			case "]":
				set = TokenType.RIGHTBRACKET;
				break;
			case "(":
				set = TokenType.LEFTPAR;
				break;
			case ")":
				set = TokenType.RIGHTPAR;
				break;
			case "+":
				set = TokenType.PLUS;
				break;
			case "-":
				set = TokenType.MINUS;
				break;
			case "=":
				set = TokenType.EQUAL;
				break;
			case "<>":
				set = TokenType.NOTEQUAL;
				break;
			case "<":
				set = TokenType.LESSTHAN;
				break;
			case "<=":
				set = TokenType.LESSTHANEQ;
				break;
			case ">":
				set = TokenType.GREATERTHAN;
				break;
			case ">=":
				set = TokenType.GREATERTHANEQ;
				break;
			case "*":
				set = TokenType.MULTIPLY;
				break;
			case "/":
				set = TokenType.DIVIDE;
				break;
			case ":=":
				set = TokenType.ASSIGN;
				break;
			default:
				set = TokenType.ERROR;
			}

		} else if (og == 3) {
			set = TokenType.ERROR;
		} else if (og == 4) {
			set = TokenType.INTEGER;
		} else if (og == 5) {
			set = TokenType.FLOAT;
		} else if (og == 6) {
			set = TokenType.POSEXP;
		} else if (og == 7) {
			set = TokenType.NEGEXP;
		}

		return set;
	}

	public String getLexeme() {
		return this.lexeme;
	}

	public TokenType getType() {
		return this.type;
	}

	/**
	 * Gets called by the scanner to create a token
	 *
	 *
	 * @param input
	 *            the lexeme from the scanner
	 * @param ogType
	 *            represents the type of lexeme, used by setType();
	 * 
	 * @return The token type of the lexeme
	 */

	public Token(String input, Integer ogType) {
		this.lexeme = input;

		this.type = setType(input, ogType);
	};

	/**
	 * Prints the token
	 *
	 *
	 * 
	 * @return A string sentence with the TokenType and the lexeme
	 */

	@Override
	public String toString() {
		return "Token: " + this.type + ", " + this.lexeme;
	}

}