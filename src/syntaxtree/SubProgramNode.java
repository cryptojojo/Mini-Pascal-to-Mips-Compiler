package syntaxtree;

public class SubProgramNode extends SyntaxTreeNode {

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);

		answer = this.indentation(level);
		answer += "SubProgramDeclarations\n";

		return answer;
	}

}
