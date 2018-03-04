package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a procedure
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class ProcedureNode extends StatementNode {

	private VariableNode variable = null;
	private ArrayList<ExpressionNode> expNode = new ArrayList();

	public void addExpNode(ExpressionNode input) {
		expNode.add(input);
	}

	public void addAllExpressions(ArrayList<ExpressionNode> input) {
		expNode.addAll(input);
	}

	public void setVariable(VariableNode input) {
		this.variable = input;
	}

	public void setExpNode(ArrayList<ExpressionNode> input) {
		this.expNode = input;
	}

	public ArrayList<ExpressionNode> getExpNode() {
		return expNode;
	}

	public VariableNode getVariable() {
		return this.variable;
	}

	/**
	 * Creates a String representation of this procedure node and its children.
	 * 
	 * @param level
	 *            The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Procedure: ";
		answer += this.variable + "\n";
		for (ExpressionNode exp : expNode) {
			answer += exp.indentedToString(level + 1);
		}
		return answer;
	}
}
