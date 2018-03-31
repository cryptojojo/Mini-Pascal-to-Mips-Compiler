package semanticanalysis;

import java.util.ArrayList;

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

	ProgramNode progNode = null;

	public SemanticAnalysis(ProgramNode progNode) {
		this.progNode = progNode;

	}

	public ProgramNode analyze() {

		verifyVarDecs();
		assignExpTypes();
		verifyTypesMatch();

		return progNode;
	}

	private void verifyVarDecs() {
		// creates an array list of the variable names that were declared
		ArrayList<String> varsDeclared = new ArrayList<String>();
		for (int i = 0; i < progNode.getVariables().getDeclarations().size(); i++)
			varsDeclared.add(progNode.getVariables().getDeclarations().get(i).getName());

		// gets an array list of the variable names that were used
		ArrayList<String> varsUsed = progNode.getAllVarNames();

		// checks to see if a variable is used but wasn't declared
		for (int i = 0; i < varsUsed.size(); i++) {
			if (!varsDeclared.contains(varsUsed.get(i)))
				System.out.println("DECLARATION ERROR: The variable '" + varsUsed.get(i) + "' was never declared");
		}

	}

	private void assignExpTypes() {

		// maybe something like this

		ArrayList<VariableNode> declarations = progNode.getVariables().getDeclarations();
		ArrayList<StatementNode> statements = progNode.getMain().getStateNodes();
		ArrayList<SubProgramNode> subprograms = progNode.getFunctions().getSubProgs();

		
		
		
	}

	private void verifyTypesMatch() {
		// TODO Auto-generated method stub

	}

}
