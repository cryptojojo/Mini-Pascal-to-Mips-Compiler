package semanticanalysis;

import java.util.ArrayList;
import java.util.HashMap;

import parser.SymbolTable;
import parser.DataType;
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
	// Switch to datatype!! not too hard :)
	private HashMap<String, DataType> varTypes = new HashMap<String, DataType>();

	public SemanticAnalysis(ProgramNode progNode, SymbolTable symTab) {
		this.progNode = progNode;
		this.symTab = symTab;

	}

	/**
	 * function called by main, organizing/executing semantic analysis, puts
	 * declared variables with their types into a hashmap, and gets the compound
	 * statements so their expressions can be given types
	 * 
	 * @return the program node after its gone through semantic analysis
	 */
	public ProgramNode analyze() {

		// Puts declared variables in a hashmap with their types so that the type can be
		// easily accessed when setting types to expressions
		ArrayList<VariableNode> varNodes = progNode.getVariables().getDeclarations();
		for (int i = 0; i < varNodes.size(); i++) {
			varTypes.put(varNodes.get(i).getName(), varNodes.get(i).getType());
		}

		verifyVarDecs();

		// gets the compound statement nodes from the main program and the subdeclartion
		// node and sends them to setExpTypes so their expressions can be assigned types
		CompoundStatementNode mainCompStatNode = progNode.getMain();
		assignExpTypes(mainCompStatNode);

		for (int i = 0; i < progNode.getFunctions().getSubProgs().size(); i++) {
			CompoundStatementNode subCompStatNode = progNode.getFunctions().getSubProgs().get(i).getMain();
			assignExpTypes(subCompStatNode);
		}

		return progNode;
	}

	/**
	 * verifies that all variable in the program are declared before use, yields an
	 * error to console when one has not been declared but doesn't stop compilation
	 */
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

	/**
	 * parses through the statements and assigns a type (real or integer) to each
	 * expression
	 * 
	 * @param compStatNode
	 */
	private void assignExpTypes(CompoundStatementNode compStatNode) {

		ArrayList<StatementNode> statementList = compStatNode.getStateNodes();
		for (StatementNode currentStat : statementList) {
			if (currentStat instanceof AssignmentStatementNode) {
				setExpTypes(((AssignmentStatementNode) currentStat).getExpression());

				if ((varTypes.get(((AssignmentStatementNode) currentStat).getLvalue().getName()) != null))
					((AssignmentStatementNode) currentStat).getLvalue()
							.setType(varTypes.get(((AssignmentStatementNode) currentStat).getLvalue().getName()));

			} else if (currentStat instanceof WhileStatementNode) {

				setExpTypes(((WhileStatementNode) currentStat).getTest());

			} else if (currentStat instanceof ProcedureNode) {

				ArrayList<ExpressionNode> expNodes = ((ProcedureNode) currentStat).getExpNode();
				for (ExpressionNode exp : expNodes)
					setExpTypes(exp);

			} else if (currentStat instanceof IfStatementNode) {

				setExpTypes(((IfStatementNode) currentStat).getTest());
				StatementNode thenStat = (((IfStatementNode) currentStat).getThenStatement());
				StatementNode elseStat = (((IfStatementNode) currentStat).getElseStatement());

				CompoundStatementNode ifThenComStat = new CompoundStatementNode();

				ifThenComStat.addStatement(thenStat);
				ifThenComStat.addStatement(elseStat);

				assignExpTypes(ifThenComStat);

			} else if (currentStat instanceof CompoundStatementNode) {

				assignExpTypes(((CompoundStatementNode) currentStat));

			} else if (currentStat instanceof WriteNode) {

				ExpressionNode writeExp = (((WriteNode) currentStat).getContent());

				if (writeExp instanceof VariableNode)
					((WriteNode) currentStat).getContent().setType(varTypes.get(((VariableNode) writeExp).getName()));
				else if (writeExp instanceof OperationNode) {
					setExpTypes((((WriteNode) currentStat).getContent()));

				}

			} else if (currentStat instanceof ReadNode) {
				((ReadNode) currentStat).getName();
				((ReadNode) currentStat).getName().setType(varTypes.get(((ReadNode) currentStat).getName().getName()));
			}

		}

	}

	/**
	 * sets the expression types for the given expression node
	 * 
	 * @param expNode
	 */
	private void setExpTypes(ExpressionNode expNode) {

		if (getLNode(expNode) instanceof OperationNode)
			setExpTypes(getLNode(expNode));
		else if (getLNode(expNode) instanceof VariableNode || getLNode(expNode) instanceof ValueNode)
			setVarVal(getLNode(expNode));

		if (getRNode(expNode) instanceof OperationNode)
			setExpTypes(getRNode(expNode));
		else if (getRNode(expNode) instanceof VariableNode || getRNode(expNode) instanceof ValueNode)
			setVarVal(getRNode(expNode));

		if (expNode instanceof OperationNode) {

			// if one is a real number the expression should be real, only if both are
			// integer should the expression be integer
			if (getLNode(expNode).getType() == DataType.DATREAL || getRNode(expNode).getType() == DataType.DATREAL)
				expNode.setType(DataType.DATREAL);
			else
				expNode.setType(DataType.DATINTEGER);

		}

		if (expNode instanceof ValueNode && expNode.getType() == null)
			setVarVal(expNode);

	}

	/**
	 * sets the variable or value node for the given expression node
	 * 
	 * @param expNode
	 */
	private void setVarVal(ExpressionNode expNode) {

		if (expNode instanceof ValueNode) {
			if (((ValueNode) expNode).getAttribute().contains("."))
				expNode.setType(DataType.DATREAL);
			else
				expNode.setType(DataType.DATINTEGER);
		}

		else if (expNode instanceof VariableNode)
			expNode.setType(varTypes.get((((VariableNode) expNode).getName())));

	}

	/**
	 * gets the left expression node from the given expression node
	 * 
	 * @param expNode
	 * @return the left expression node
	 */
	private ExpressionNode getLNode(ExpressionNode expNode) {
		ExpressionNode ans = null;

		if (expNode instanceof OperationNode)
			ans = ((OperationNode) expNode).getLeft();

		return ans;

	}

	/**
	 * gets the right expression node from the given expression node
	 * 
	 * @param expNode
	 * @return the right expression node
	 */
	private ExpressionNode getRNode(ExpressionNode expNode) {
		ExpressionNode ans = null;

		if (expNode instanceof OperationNode)
			ans = ((OperationNode) expNode).getRight();

		return ans;

	}

}
