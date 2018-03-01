package syntaxtree;

import scanner.TokenType;

/**
 * General representation of any expression.
 * 
 * @author erik
 */
public abstract class ExpressionNode extends SyntaxTreeNode {

	TokenType type;

	public ExpressionNode() {
		type = null;
	}

	public ExpressionNode(TokenType t) {
		this.type = t;
	}

	public void setType(TokenType t) {
		this.type = t;
	}

	public TokenType getType() {
		return type;
	}

}
