package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a function
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class FunctionNode extends VariableNode {

	private ArrayList<ExpressionNode> expNode;

	public FunctionNode(String attr) {
		super(attr);
		expNode = null;
		this.type = null;
	}

	public ArrayList<ExpressionNode> getExpNode() {
		return this.expNode;
	}

	public void setExpNode(ArrayList<ExpressionNode> input) {
		this.expNode = input;
	}

	public void addExpNode(ExpressionNode input) {
		expNode.add(input);
	}

	public void addAll(ArrayList<ExpressionNode> input) {
		expNode.addAll(input);
	}

	public String getName() {
		return (super.name);
	}

	@Override
	public String toString() {
		return ("VariableNode: " + super.name + "ExpressionNode: " + expNode);
	}

	/**
	 * Creates a String representation of this a function node and its children.
	 * 
	 * @param level
	 *            The tree level at which this node resides.
	 * @return A String representing this node.
	 */

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Name: " + super.name + ", Type: " + super.type + "\n";
		answer += this.indentation(level);
		answer += "Arguments: \n";
		for (ExpressionNode expression : expNode) {
			answer += expression.indentedToString(level + 1);
		}
		return answer;
	}

}