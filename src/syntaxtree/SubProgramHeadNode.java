package syntaxtree;

import java.util.ArrayList;

public class SubProgramHeadNode extends SyntaxTreeNode {

	private final String name;
	private final String[] args;

	public SubProgramHeadNode(String name, ArrayList<String> arguments) {
		this.name = name;
		this.args = new String[arguments.size()];
		arguments.toArray(this.args);
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Function name: " + name + '\n';

		for (int i = 0; i <= args.length; i++)
			answer += args[i].indentedToString(level + 1);

		return answer;

	}

}
