package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a subprogram head: contains the name and an array list of the
 * arguments
 * 
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class SubProgramHeadNode extends SyntaxTreeNode {

	private String name;
	private String[] args;

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
			answer += args[i] + '\n';

		return answer;

	}

}
