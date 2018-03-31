
package syntaxtree;

import scanner.TokenType;

/**
 * Represents a variable in the syntax tree.
 * 
 * @author Erik Steinmetz, Joseph Miller <miller12 @ augsburg.edu>
 */
public class VariableNode extends ExpressionNode {

	/** The name of the variable associated with this node. */
	String name;
	TokenType type;

	/**
	 * Creates a ValueNode with the given attribute.
	 * 
	 * @param attr
	 *            The attribute for this value node.
	 */
	public VariableNode(String attr) {
		this.name = attr;
	}

	public VariableNode(String attr, TokenType type) {
		this.type = type;
		this.name = attr;
		super.setType(type);
	}

	public void setType(TokenType type) {
		this.type = type;
		super.setType(type);
	}

	/**
	 * Returns the name of the variable of this node.
	 * 
	 * @return The name of this VariableNode.
	 */
	public String getName() {
		return (this.name);
	}

	public TokenType getType() {
		return (this.type);
	}

	/**
	 * Returns the name of the variable as the description of this node.
	 * 
	 * @return The attribute String of this node.
	 */
	@Override
	public String toString() {
		return (name);
	}

	/**
	 * Creates a String representation of this variable node.
	 * 
	 * @param level
	 *            The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Name: " + this.name + " (Expression Type: " + this.type + ")\n";
		return answer;
	}

	@Override
	public boolean equals(Object o) {
		boolean answer = false;
		if (o instanceof VariableNode) {
			VariableNode other = (VariableNode) o;
			if (this.name.equals(other.name))
				answer = true;
		}
		return answer;
	}

}
