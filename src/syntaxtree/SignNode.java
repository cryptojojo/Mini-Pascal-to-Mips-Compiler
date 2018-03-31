package syntaxtree;

import scanner.TokenType;

/**
 * Represents a sign, an expression and an operation
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class SignNode extends ExpressionNode {

	private ExpressionNode exp;
	private TokenType oper;

	public SignNode(TokenType op) {
		this.oper = op;
	}

	public ExpressionNode getExpression() {
		return (this.exp);
	}

	public void setExpression(ExpressionNode node) {
		this.exp = node;
	}

	public void setOperation(TokenType op) {
		super.setType(oper);
		this.oper = op;
	}

	public TokenType getOperation() {
		return (this.oper);
	}

	@Override
	public boolean equals(Object o) {
		boolean answer = false;
		if (o instanceof syntaxtree.OperationNode) {
			syntaxtree.SignNode other = (syntaxtree.SignNode) o;
			if ((this.oper == other.oper) && (this.exp.equals(other.exp)))
				answer = true;
		}
		return answer;
	}

	@Override
	public String toString() {
		return oper.toString();
	}

	/**
	 * Creates a String representation of this sign node and its children.
	 * 
	 * @param level
	 *            The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Sign Node: " + this.oper + "\n" + " (Expression Type: " + exp.getType() + ") ";
		answer += exp.indentedToString(level + 1);
		return (answer);
	}

}
