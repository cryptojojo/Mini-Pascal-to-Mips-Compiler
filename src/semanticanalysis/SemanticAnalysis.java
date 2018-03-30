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
		assignTypes();
		verifyTypesMatch();

		return progNode;
	}

	private void verifyVarDecs() {
		// gets all the variables declared with their corresponding types.
		DeclarationsNode decsNode = progNode.getVariables();
		ArrayList<VariableNode> decs = decsNode.getDeclarations();

	}

	private void assignTypes() {
		// TODO Auto-generated method stub

	}

	private void verifyTypesMatch() {
		// TODO Auto-generated method stub

	}

}
