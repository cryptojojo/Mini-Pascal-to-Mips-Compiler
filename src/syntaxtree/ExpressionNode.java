package syntaxtree;

import scanner.TokenType;

/**
 * General representation of any expression.
 * 
 * @author Erik Steinmetz, Joseph Miller <miller12 @ augsburg.edu>
 */
public abstract class ExpressionNode extends SyntaxTreeNode {

	TokenType type;

	public ExpressionNode() {
		type = null;
	}

	public ExpressionNode(TokenType type) {
		this.type = type;
	}

	public void setType(TokenType type) {
		this.type = type;
	}

	public TokenType getType() {
		return type;
	}

}
