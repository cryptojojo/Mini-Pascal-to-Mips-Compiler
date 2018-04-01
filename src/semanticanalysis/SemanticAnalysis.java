package semanticanalysis;

import java.util.ArrayList;
import java.util.HashMap;

import parser.SymbolTable;
import parser.SymbolType;
import scanner.TokenType;
import syntaxtree.*;

/**
 * class that takes in the synatx tree and checks that all variables are
 * declared before they are used, assigns a type, integer or real, to each
 * expression and checks to make sure that types match across assignment
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class SemanticAnalysis {

	private ProgramNode progNode = null;
	private SymbolTable symTab = null;
	private HashMap<String, TokenType> varTypes = new HashMap<String, TokenType>();

	public SemanticAnalysis(ProgramNode progNode, SymbolTable symTab) {
		this.progNode = progNode;
		this.symTab = symTab;

	}

	public ProgramNode analyze() {

		// Puts declared variables in a hashmap with their types so that the type can be
		// easily accessed when setting types to expressions
		ArrayList<VariableNode> varNodes = progNode.getVariables().getDeclarations();
		for (int i = 0; i < varNodes.size(); i++) {
			varTypes.put(varNodes.get(i).getName(), varNodes.get(i).getType());
		}

		verifyVarDecs();
		assignExpTypes();
		verifyTypesMatch();

		return progNode;
	}

	private void verifyVarDecs() {

		// creates an array list of the variable names that were declared
		ArrayList<String> varsDeclaredNames = new ArrayList<String>();
		for (int i = 0; i < progNode.getVariables().getDeclarations().size(); i++)
			varsDeclaredNames.add(progNode.getVariables().getDeclarations().get(i).getName());

		// gets an array list of the variable names that were used
		ArrayList<String> varsUsedNames = progNode.getAllVarNames();

		// checks to see if a variable is used but wasn't declared
		for (int i = 0; i < varsUsedNames.size(); i++) {
			if (!varsDeclaredNames.contains(varsUsedNames.get(i)))
				System.out.println("DECLARATION ERROR: The variable '" + varsUsedNames.get(i) + "' was never declared");
		}

	}

	private void assignExpTypes() {

		CompoundStatementNode mainCompStatNode = progNode.getMain();
		setExpTypes(mainCompStatNode);

		for (int i = 0; i < progNode.getFunctions().getSubProgs().size(); i++) {
			CompoundStatementNode subCompStatNode = progNode.getFunctions().getSubProgs().get(i).getMain();
			setExpTypes(subCompStatNode);
		}

	}

	private void setExpTypes(CompoundStatementNode compStatNode) {

		ArrayList<StatementNode> statementList = compStatNode.getStateNodes();
		for (StatementNode currentStat : statementList) {
			if (currentStat instanceof AssignmentStatementNode) {

			}
			if (currentStat instanceof WhileStatementNode) {

			}
			if (currentStat instanceof ProcedureNode) {

			}
			if (currentStat instanceof IfStatementNode) {

			}
			if (currentStat instanceof CompoundStatementNode) {

			}
			if (currentStat instanceof WriteNode) {

			}
			if (currentStat instanceof ReadNode) {

			}

		}

	}

	private void verifyTypesMatch() {
		// TODO Auto-generated method stub

	}

}
