package syntaxtree;

import parser.DataType;
import scanner.TokenType;

/**
 * Represents any operation in an expression.
 * 
 * @author Erik Steinmetz, Joseph Miller <miller12 @ augsburg.edu>
 */
public class OperationNode extends ExpressionNode {

	/** The left operator of this operation. */
	private ExpressionNode left;

	/** The right operator of this operation. */
	private ExpressionNode right;

	/** The kind of operation. */
	private TokenType opType;

	/**
	 * Creates an operation node given an operation token.
	 * 
	 * @param operation
	 *            The token representing this node's math operation.
	 */
	public OperationNode(TokenType opType) {
		this.opType = opType;
	}

	// Getters
	public ExpressionNode getLeft() {
		return (this.left);
	}

	public ExpressionNode getRight() {
		return (this.right);
	}

	public TokenType getOperation() {
		return (this.opType);
	}

	// Setters
	public void setLeft(ExpressionNode node) {
		// If we already have a left, remove it from our child list.
		this.left = node;
	}

	public void setRight(ExpressionNode node) {
		// If we already have a right, remove it from our child list.
		this.right = node;
	}

	public void setoperation(TokenType opType) {
		//super.setType(opType);
		this.opType = opType;
	}

	/**
	 * Returns the operation token as a String.
	 * 
	 * @return The String version of the operation token.
	 */
	@Override
	public String toString() {
		return opType.toString();
	}

	/**
	 * Creates a String representation of this node and its children.
	 * 
	 * @param level
	 *            The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "operation: " + this.opType + " (Expression Type: " + left.getType() + ")\n";
		answer += left.indentedToString(level + 1);
		answer += right.indentedToString(level + 1);
		return (answer);
	}

	@Override
	public boolean equals(Object o) {
		boolean answer = false;
		if (o instanceof OperationNode) {
			OperationNode other = (OperationNode) o;
			if ((this.opType == other.opType) && this.left.equals(other.left) && this.right.equals(other.right))
				answer = true;
		}
		return answer;
	}
}
