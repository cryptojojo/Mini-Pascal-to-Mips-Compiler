package syntaxtree;

public class WriteNode extends StatementNode {

	private ExpressionNode content;

	public WriteNode(ExpressionNode content) {
		this.content = content;
	}

	public ExpressionNode getContent() {
		return content;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Write\n";
		answer += this.content.indentedToString(level + 1);
		return answer;
	}
}
