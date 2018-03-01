package syntaxtree;

public class WhileStatementNode extends StatementNode {

	private ExpressionNode test; // The test to be checked on each iteration of the while loop
	private StatementNode doW; // The statement to be executed on each iteration

	public void setTest(ExpressionNode test) {
		this.test = test;
	}

	public void setDoStatement(StatementNode doW) {
		this.doW = doW;
	}

	public ExpressionNode getTest() {
		return test;
	}

	public StatementNode getDo() {
		return doW;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "While:\n";
		answer += this.test.indentedToString(level + 1);
		answer += this.indentation(level) + "Do:\n" + this.doW.indentedToString(level + 1);
		return answer;
	}

}
