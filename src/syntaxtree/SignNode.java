package syntaxtree;

import scanner.TokenType;

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

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Unary Operation: " + this.oper + "\n";
		answer += exp.indentedToString(level + 1);
		return (answer);
	}

}
