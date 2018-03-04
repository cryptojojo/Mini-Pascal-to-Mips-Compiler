package syntaxtree;

import scanner.TokenType;

/**
 * Represents an single array
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
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

	/**
	 * Creates a String representation of this array node and its children.
	 * 
	 * @param level
	 *            The tree level at which this node resides.
	 * @return A String representing this node.
	 */
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
