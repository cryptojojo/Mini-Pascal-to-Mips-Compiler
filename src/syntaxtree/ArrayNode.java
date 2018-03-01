package syntaxtree;

import scanner.TokenType;

public class ArrayNode extends VariableNode {

	private ExpressionNode exp;
	private TokenType type = null;

	public ArrayNode(String attr, TokenType type) {
		super(attr);
		this.type = type;
	}

	public ArrayNode(String attr) {
		super(attr);
	}

	public String getName() {
		return (super.getName());
	}

	public ExpressionNode getExpNode() {
		return this.exp;
	}

	public void setExpNode(ExpressionNode input) {
		this.exp = input;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Array: " + super.getName() + ", Type: " + type + "\n";
		if (exp != null) {
			answer += this.exp.indentedToString(level + 1);
		}
		return answer;
	}

}
