package scanner;

/**
 * @author      Joseph Miller <miller12 @ augsburg.edu>
 * @version     JDK/JRE 1.8.0_141  
 */

/**
 * creates the predefined constants for TokenType
 */
public enum TokenType {
	// keywords
	LEFTPAR, RIGHTPAR, LEFTCURL, RIGHTCURL, LEFTBRACKET, RIGHTBRACKET, PLUS, MINUS, EQUAL, LESSTHAN, LESSTHANEQ, GREATERTHAN, GREATERTHANEQ, MULTIPLY, DIVIDE, COLON, SEMI, COMMA, PERIOD, ASSIGN, NOTEQUAL,

	// symbols
	AND, OR, MOD, DIV, VAR, OF, NOT, IF, ELSE, THEN, DO, WHILE, BEGIN, END, PROGRAM, ARRAY, FUNCTION, REAL, INTEGER, FLOAT, PROCEDURE, NUMBER, ID, POSEXP, NEGEXP, ERROR
}
