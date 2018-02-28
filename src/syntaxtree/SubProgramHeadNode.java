package syntaxtree;

import java.util.ArrayList;

public class SubProgramHeadNode {

	private final String name;
	private final String[] args;

	public SubProgramHeadNode(String name, ArrayList<String> arguments) {
		this.name = name;
		this.args = new String[arguments.size()];
		arguments.toArray(this.args);
	}

}
