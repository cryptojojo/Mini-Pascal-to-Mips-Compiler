package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import scanner.*;
import semanticanalysis.SemanticAnalysis;
import syntaxtree.*;

/**
 * class that parses the code and verifies the inputed code is a pascal program,
 * and also creates the syntax tree from the nodes in the syntaxtree package
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class Parser {

	SymbolTable symTab = new SymbolTable();
	

	
	ArrayList<String> allVarNames = new ArrayList<String>();

	// Instance Variables
	private Token lookahead;
	private Scanner scanner;
	String lexi = "";
	// has to declare here so IDs can be added be added recursively

	ArrayList<String> paramList = new ArrayList<String>();
	CompoundStatementNode comStat = new CompoundStatementNode();
	ArrayList<StatementNode> statNodeList = new ArrayList<StatementNode>();

	/**
	 * Constructor the parser facet of the compiler
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
	 * verify if the code in correct pascal and create top node of the syntax tree
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

		progNode.setAllVarNames(allVarNames);

		return progNode; // if it makes it this far, it is a pascal program

	}

	/**
	 * variable input or series of variable inputs
	 */
	public ArrayList<String> identifier_list() {
		ArrayList<String> idList = new ArrayList<>();
		lexi = this.lookahead.getLexeme();
		String name = lexi;
		match(TokenType.ID);
		symTab.addVariableName(lexi);
		idList.add(name);
		if (lookahead != null && (this.lookahead.getType() == TokenType.COMMA)) {
			match(TokenType.COMMA);
			idList.addAll(identifier_list());
		} else {
			// just the id option
		}
		return idList;

	}

	/**
	 * declaring a variable to its type, or a series of variables to their type
	 */
	public DeclarationsNode declarations() {
		DeclarationsNode decNode = new DeclarationsNode();
		if (lookahead != null && (this.lookahead.getType() == TokenType.VAR)) {
			match(TokenType.VAR);
			ArrayList<String> identList = identifier_list();

			match(TokenType.COLON);
			TokenType t = type(identList);
			for (String ident : identList) {
				decNode.addVariable(new VariableNode(ident, t));

				if (!allVarNames.contains(ident))
					allVarNames.add(ident);
			}
			match(TokenType.SEMI);
			decNode.addDeclarations(declarations());
		} else {
			// lambda option
		}

		return decNode;
	}

	/**
	 * can be just standard_type or can also be an array of standard_type
	 */
	public TokenType type(ArrayList<String> idList) {
		TokenType t = null;
		if (lookahead != null && (lookahead.getType() == TokenType.ARRAY)) {
			match(TokenType.ARRAY);
			match(TokenType.LEFTBRACKET);

			if (lookahead != null && (lookahead.getType() == TokenType.INTEGER))
				match(TokenType.INTEGER);
			if (lookahead != null && (lookahead.getType() == TokenType.REAL))
				match(TokenType.REAL);

			match(TokenType.COLON);

			if (lookahead != null && (lookahead.getType() == TokenType.INTEGER))
				match(TokenType.INTEGER);
			if (lookahead != null && (lookahead.getType() == TokenType.REAL))
				match(TokenType.REAL);

			match(TokenType.RIGHTBRACKET);
			match(TokenType.OF);
			t = standard_type();

		} else if (lookahead != null
				&& (lookahead.getType() == TokenType.INTEGER || lookahead.getType() == TokenType.REAL)) {
			t = standard_type();

		} else
			error("type");
		return t;
	}

	/**
	 * taking in a number, either an integer or a real
	 */
	public TokenType standard_type() {
		if (lookahead != null && (this.lookahead.getType() == TokenType.INTEGER)) {
			match(TokenType.INTEGER);
			return TokenType.INTEGER;
		} else if (lookahead != null && (this.lookahead.getType() == TokenType.REAL)) {
			match(TokenType.REAL);
			return TokenType.REAL;
		} else {
			error("in the standard_type function");
			return TokenType.ERROR;
		}
	}

	/**
	 * taking in a subprogram_declaration or a series of subprogram_declarations
	 * separated by commas
	 */
	public SubProgramDeclarationsNode subprogram_declarations() {
		SubProgramDeclarationsNode subPorgDecNode = new SubProgramDeclarationsNode();
		if (lookahead != null
				&& (lookahead.getType() == TokenType.FUNCTION || lookahead.getType() == TokenType.PROCEDURE)) {
			subPorgDecNode.addSubProgramDeclaration(subprogram_declaration());
			match(TokenType.SEMI);
			subPorgDecNode.addall(subprogram_declarations().getSubProgs());
		}
		// else lambda case
		return subPorgDecNode;
	}

	/**
	 * calling a series of other production rules in order to get a proper order
	 */
	public SubProgramNode subprogram_declaration() {
		SubProgramNode subProgNode = subprogram_head();
		subProgNode.setVariables(declarations());
		subProgNode.setFunctions(subprogram_declarations());
		subProgNode.setMain(compound_statement());
		return subProgNode;
	}

	/**
	 * creating a function or a procedure
	 */
	public SubProgramNode subprogram_head() {
		SubProgramNode spNode = null;
		if (lookahead != null && (lookahead.getType() == TokenType.FUNCTION)) {
			match(TokenType.FUNCTION);
			String functName = lookahead.getLexeme();
			spNode = new SubProgramNode(functName);
			match(TokenType.ID);
			arguments();
			match(TokenType.COLON);
			symTab.addFunctionName(functName);
			match(TokenType.SEMI);
		} else if (lookahead != null && (lookahead.getType() == TokenType.PROCEDURE)) {
			match(TokenType.PROCEDURE);
			String procName = lookahead.getLexeme();
			spNode = new SubProgramNode(procName);
			match(TokenType.ID);
			arguments();
			symTab.addProcedureName(procName);
			match(TokenType.SEMI);
		} else
			error("subprogram_head");
		return spNode;
	}

	/**
	 * takes in parameters inside of parenthesis
	 */
	public ArrayList<String> arguments() {
		ArrayList<String> args = new ArrayList<String>();
		if (lookahead != null && (this.lookahead.getType() == TokenType.LEFTPAR)) {
			match(TokenType.LEFTPAR);
			args = parameter_list();
			match(TokenType.RIGHTPAR);
		} else {
			// lambda option
		}

		return args;
	}

	/**
	 * takes in an ID and it's corresponding types or a series of parameters
	 * separated by semicolons
	 */
	public ArrayList<String> parameter_list() {
		ArrayList<String> idList = identifier_list();
		match(TokenType.COLON);
		type(idList);
		if (lookahead != null && (this.lookahead.getType() == TokenType.SEMI)) {
			match(TokenType.SEMI);
			idList.addAll(parameter_list());
		} else {
			// just the first option
		}
		return paramList;
	}

	/**
	 * verifying the main code, starting with begin and ending with end
	 */
	public CompoundStatementNode compound_statement() {
		CompoundStatementNode comStatNode = new CompoundStatementNode();
		match(TokenType.BEGIN);
		comStatNode = optional_statements();
		match(TokenType.END);
		return comStatNode;
	}

	/**
	 * for statement_list or nothing (lamda)
	 */
	public CompoundStatementNode optional_statements() {
		CompoundStatementNode compStatNode = new CompoundStatementNode();
		if (lookahead != null && (this.lookahead.getType() == TokenType.ID
				|| (this.lookahead.getType() == TokenType.BEGIN) || (this.lookahead.getType() == TokenType.IF)
				|| (this.lookahead.getType() == TokenType.WHILE) || (this.lookahead.getType() == TokenType.READ)
				|| (this.lookahead.getType() == TokenType.WRITE))) {
			compStatNode.addAllStateNodes(statement_list());

		} else {
			// lambda option
		}
		return compStatNode; // not sure what to put here
	}

	/**
	 * statements or a series of statements separated by semicolons
	 */
	public ArrayList<StatementNode> statement_list() {
		ArrayList<StatementNode> statNodeList = new ArrayList();

		StatementNode statNode = statement();

		if (statNode != null) {
			statNodeList.add(statNode);
		}

		if (lookahead != null && (lookahead.getType() == TokenType.SEMI)) {
			match(TokenType.SEMI);
			statNodeList.addAll(statement_list());
		}
		// else lambda case
		return statNodeList;
	}

	/**
	 * creates all possible statements in the Pascal cod
	 */
	public StatementNode statement() {
		StatementNode state = null;
		if (lookahead != null && (lookahead.getType() == TokenType.ID)) {
			if (symTab.isVariableName(lookahead.getLexeme()) || symTab.isArrayName((lookahead.getLexeme()))) {
				AssignmentStatementNode assign = new AssignmentStatementNode();
				assign.setLvalue(variable());
				assignop();
				assign.setExpression(expression());
				return assign;
			} else if (symTab.isProcedureName(lookahead.getLexeme())) {
				return procedure_statement();
			} else
				error("Name not found in symbol table.");
		} else if (lookahead != null && (lookahead.getType() == TokenType.BEGIN))
			state = compound_statement();
		else if (lookahead != null && (lookahead.getType() == TokenType.IF)) {
			IfStatementNode ifState = new IfStatementNode();
			match(TokenType.IF);
			ifState.setTest(expression());
			match(TokenType.THEN);
			ifState.setThenStatement(statement());
			match(TokenType.ELSE);
			ifState.setElseStatement(statement());

			return ifState;
		} else if (lookahead != null && (lookahead.getType() == TokenType.WHILE)) {
			WhileStatementNode whileState = new WhileStatementNode();
			match(TokenType.WHILE);
			whileState.setTest(expression());
			match(TokenType.DO);
			whileState.setDoStatement(statement());
			return whileState;
		} else if (lookahead != null && (lookahead.getType() == TokenType.READ)) {
			match(TokenType.READ);
			match(TokenType.LEFTPAR);
			String varName = lookahead.getLexeme();
			match(TokenType.ID);
			match(TokenType.RIGHTPAR);
			if (!allVarNames.contains(varName))
				allVarNames.add(varName);
			return new ReadNode(new VariableNode(varName));
		} else if (lookahead != null && (lookahead.getType() == TokenType.WRITE)) {
			match(TokenType.WRITE);
			match(TokenType.LEFTPAR);
			WriteNode write = new WriteNode(expression());
			match(TokenType.RIGHTPAR);
			return write;
		}

		else {
			// nothing
		}
		return state;
	}

	/**
	 * a variable ID or a variable ID with an expression after it surrounded by
	 * brackets
	 */
	public VariableNode variable() {
		VariableNode var = new VariableNode(lookahead.getLexeme());
		match(TokenType.ID);
		if (lookahead != null && (lookahead.getType() == TokenType.LEFTBRACKET)) {
			match(TokenType.LEFTBRACKET);
			expression();
			match(TokenType.RIGHTBRACKET);
		}
		// else lambda case

		if (!allVarNames.contains(var.getName()))
			allVarNames.add(var.getName());
		return var;
	}

	/**
	 * a procedure ID or a production ID with an expression after it surrounded by
	 * parenthesis
	 */
	public ProcedureNode procedure_statement() {
		ProcedureNode psNode = new ProcedureNode();
		String procName = lookahead.getLexeme();
		psNode.setVariable(new VariableNode(procName));
		match(TokenType.ID);
		if (lookahead != null && (lookahead.getType() == TokenType.LEFTPAR)) {
			match(TokenType.LEFTPAR);
			psNode.addAllExpressions(expression_list());
			match(TokenType.RIGHTPAR);
		}
		symTab.addProcedureName(procName);
		return psNode;
	}

	/**
	 * expressions or a series of expressions separated by commas
	 */
	public ArrayList<ExpressionNode> expression_list() {
		ArrayList<ExpressionNode> exList = new ArrayList<>();
		exList.add(expression());
		if (lookahead != null && (lookahead.getType() == TokenType.COMMA)) {
			match(TokenType.COMMA);
			exList.addAll(expression_list());
		}
		return exList;
	}

	/**
	 * simple expression or a simple_expression compared to another
	 * simple_expression with a relop
	 * 
	 * @return
	 */
	public ExpressionNode expression() {
		ExpressionNode left = simple_expression();
		if (isRelop(lookahead)) {
			OperationNode opNode = new OperationNode(lookahead.getType());
			opNode.setLeft(left);
			match(lookahead.getType());
			opNode.setRight(simple_expression());
			return opNode;
		}
		return left;
	}

	/**
	 * either a term and a simple_part or a sign and then a term and a simmple_part
	 */
	public ExpressionNode simple_expression() {
		ExpressionNode expNode = null;
		if (lookahead != null && (lookahead.getType() == TokenType.ID || lookahead.getType() == TokenType.INTEGER
				|| lookahead.getType() == TokenType.REAL || lookahead.getType() == TokenType.LEFTPAR
				|| lookahead.getType() == TokenType.NOT)) {
			expNode = term();
			expNode = simple_part(expNode);
		} else if (lookahead != null
				&& (lookahead.getType() == TokenType.PLUS || lookahead.getType() == TokenType.MINUS)) {
			SignNode uoNode = sign();
			expNode = term();
			uoNode.setExpression(simple_part(expNode));
			return uoNode;
		} else
			error(" in simple_expression");

		return expNode;
	}

	/**
	 * an addop and then a term and simple_part
	 */
	public ExpressionNode simple_part(ExpressionNode left) {

		if (isAddop(lookahead)) {
			OperationNode operNode = new OperationNode(lookahead.getType());
			match(lookahead.getType());
			ExpressionNode right = term();
			operNode.setLeft(left);
			operNode.setRight(right);
			return simple_part(operNode);
		}
		// else lambda case
		return left;
	}

	/**
	 * a factor then a term_part
	 * 
	 * @return
	 */
	public ExpressionNode term() {
		ExpressionNode left = factor();
		return term_part(left);
	}

	/**
	 * a mulop and then a factor and term_part
	 */
	public ExpressionNode term_part(ExpressionNode posLeft) {
		if (isMulop(lookahead)) {
			OperationNode operNode = new OperationNode(lookahead.getType());
			match(lookahead.getType());
			ExpressionNode right = factor();
			operNode.setLeft(posLeft);
			operNode.setRight(term_part(right));
			return operNode;
		}
		// else lambda case
		return posLeft;
	}

	/**
	 * an ID, or an ID with an expression surrounding by brackets or an
	 * expression_list surrounded by parenthesis or a number, a single expression or
	 * a 'NOT' factor
	 */
	public ExpressionNode factor() {
		ExpressionNode exper = null;
		if (lookahead != null && (lookahead.getType() == TokenType.ID)) {
			String name = lookahead.getLexeme();
			match(TokenType.ID);
			if (lookahead != null && (lookahead.getType() == TokenType.LEFTBRACKET)) {
				ArrayNode arrNode = new ArrayNode(name);
				match(TokenType.LEFTBRACKET);
				ExpressionNode temp = expression();
				arrNode.setExpNode(temp);
				match(TokenType.RIGHTBRACKET);
				return arrNode;
			} else if (lookahead != null && (lookahead.getType() == TokenType.LEFTPAR)) {
				FunctionNode funcNode = new FunctionNode(name);
				match(TokenType.LEFTPAR);
				funcNode.setExpNode(expression_list());
				match(TokenType.RIGHTPAR);
				return funcNode;
			} else {
				if (!allVarNames.contains(name))
					allVarNames.add(name);
				return new VariableNode(name);
			}
		} else if (lookahead != null
				&& (lookahead.getType() == TokenType.INTEGER || lookahead.getType() == TokenType.REAL)) {

			if (lookahead.getLexeme().contains("."))
				exper = new ValueNode(lookahead.getLexeme(), TokenType.REAL);
			else
				exper = new ValueNode(lookahead.getLexeme(), TokenType.INTEGER);

			if (lookahead != null && (lookahead.getType() == TokenType.INTEGER)) {
				match(TokenType.INTEGER);
			} else if (lookahead != null && (lookahead.getType() == TokenType.REAL)) {
				match(TokenType.REAL);
			}

		} else if (lookahead != null && (lookahead.getType() == TokenType.LEFTPAR)) {
			match(TokenType.LEFTPAR);
			exper = expression();
			match(TokenType.RIGHTPAR);
		} else if (lookahead != null && (lookahead.getType() == TokenType.NOT)) {
			SignNode uoNode = new SignNode(TokenType.NOT);
			match(TokenType.NOT);
			uoNode.setExpression(factor());
			return uoNode;
		} else
			error(" in factor");
		return exper;
	}

	/**
	 * a plus or minus
	 */
	public SignNode sign() {
		SignNode sig = null;
		if (lookahead != null && (lookahead.getType() == TokenType.PLUS)) {
			sig = new SignNode(TokenType.PLUS);
			match(TokenType.PLUS);
		} else if (lookahead != null && (lookahead.getType() == TokenType.MINUS)) {
			sig = new SignNode(TokenType.MINUS);
			match(TokenType.MINUS);
		} else
			error("sign");
		return sig;
	}

	// RELOP, ADDOP, MULOP, ASSIGNOP

	/**
	 * whether or not the token is a relative operator
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
	 * matching the relop and recognizing the token
	 */
	public void relop() {
		if (lookahead != null && (lookahead.getType() == TokenType.EQUAL)) {
			match(TokenType.EQUAL);
		} else if (lookahead != null && (lookahead.getType() == TokenType.NOTEQUAL)) {
			match(TokenType.NOTEQUAL);
		} else if (lookahead != null && (lookahead.getType() == TokenType.LESSTHAN)) {
			match(TokenType.LESSTHAN);
		} else if (lookahead != null && (lookahead.getType() == TokenType.LESSTHANEQ)) {
			match(TokenType.LESSTHANEQ);
		} else if (lookahead != null && (lookahead.getType() == TokenType.GREATERTHANEQ)) {
			match(TokenType.GREATERTHANEQ);
		} else if (lookahead != null && (lookahead.getType() == TokenType.GREATERTHAN)) {
			match(TokenType.GREATERTHAN);
		} else {
			error("Relop");
		}
	}

	/**
	 * determining whether or not the token is an addop
	 * 
	 * @param token
	 *            the token to be determined
	 * @return whether or not the token is a relop
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
	 * matching the addop and recognizing the token
	 */
	public void addop() {
		if (lookahead != null && (lookahead.getType() == TokenType.PLUS)) {
			match(TokenType.PLUS);
		} else if (lookahead != null && (lookahead.getType() == TokenType.MINUS)) {
			match(TokenType.MINUS);
		} else if (lookahead != null && (lookahead.getType() == TokenType.OR)) {
			match(TokenType.OR);
		} else {
			error("Addop");
		}
	}

	/**
	 * determining whether or not the token is a mulop
	 * 
	 * @param token
	 *            the token to be determined
	 * @return whether or not the token is a mulop
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
	 * matching the mulop and recognizing the token
	 */
	public void mulop() {
		if (lookahead != null && (lookahead.getType() == TokenType.MULTIPLY)) {
			match(TokenType.MULTIPLY);
		} else if (lookahead != null && (lookahead.getType() == TokenType.DIVIDE)) {
			match(TokenType.DIVIDE);
		} else if (lookahead != null && (lookahead.getType() == TokenType.DIV)) {
			match(TokenType.DIV);
		} else if (lookahead != null && (lookahead.getType() == TokenType.MOD)) {
			match(TokenType.MOD);
		} else if (lookahead != null && (lookahead.getType() == TokenType.AND)) {
			match(TokenType.AND);
		} else {
			error(" in mulop");
		}
	}

	/**
	 * recognizing the assign operator
	 */
	public void assignop() {
		if (lookahead != null && (lookahead.getType() == TokenType.COLON)) {
			match(TokenType.COLON);
			match(TokenType.EQUAL);
		} else {
			error("in assignop");
		}
	}

	/**
	 * Matches the expected token
	 * 
	 * @param The
	 *            expected token type
	 */
	public void match(TokenType expected) {
		// System.out.println("match (" + expected + ")");
		if (lookahead != null && (this.lookahead.getType() == expected)) {
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
	public String getSymbolTableStr() {
		return symTab.toString();
	}
	
	public SymbolTable getSymbolTable() {
		return symTab;
	}

	/**
	 * Error message printing
	 * 
	 * @param to
	 *            be printed
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
