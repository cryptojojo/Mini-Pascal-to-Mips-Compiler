package syntaxtree;

import java.util.ArrayList;

public class ProcedureNode extends StatementNode {

	private String name;
	private ArrayList<ExpressionNode> args = new ArrayList<>();

	public ProcedureNode(String name) {
		this.name = name;
	}

	public void addAllExpressions(ArrayList<ExpressionNode> input) {
		args.addAll(input);
	}

	public void addArg(ExpressionNode exp) {
		args.add(exp);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Procedure: ";
		answer += this.name + "\n";
		for (ExpressionNode exp : args) {
			answer += exp.indentedToString(level + 1);
		}
		return answer.toString();
	}

}
