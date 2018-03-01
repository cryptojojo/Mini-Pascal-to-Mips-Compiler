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
		// TODO Auto-generated method stub
		return null;
	}

}
