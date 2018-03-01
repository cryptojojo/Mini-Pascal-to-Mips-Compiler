package syntaxtree;

public class ReadNode extends StatementNode {

	private VariableNode name;

	public ReadNode(VariableNode name) {
		this.name = name;
	}

	public VariableNode getName() {
		return name;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Read \n ";
		answer += name.indentedToString(level + 1);
		return answer;
	}

}
