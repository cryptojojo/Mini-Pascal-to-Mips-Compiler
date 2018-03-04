package syntaxtree;

/**
 * Represents a write command
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class WriteNode extends StatementNode {

	private ExpressionNode content;

	public WriteNode(ExpressionNode content) {
		this.content = content;
	}

	public ExpressionNode getContent() {
		return content;
	}

	/**
	 * Creates a String representation of this write node and its children.
	 * 
	 * @param level
	 *            The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Write\n";
		answer += this.content.indentedToString(level + 1);
		return answer;
	}
}
