package syntaxtree;

import java.util.ArrayList;

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

	@Override
	public boolean equals(Object o) {
		boolean answer = false;
		if (o instanceof ArrayNode) {
			ArrayNode other = (ArrayNode) o;
			if (super.name.equals(other.getName()) && (this.expNode.equals(other.getExpNode())))
				answer = true;
		}
		return answer;
	}
}