package syntaxtree;

/**
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public class SubProgramNode extends SyntaxTreeNode {

	private SubProgramHeadNode head;
	private DeclarationsNode variables;
	private CompoundStatementNode main;
	private SubProgramDeclarationsNode functions;

	public SubProgramNode(SubProgramHeadNode head, DeclarationsNode variables, CompoundStatementNode main,
			SubProgramDeclarationsNode functions) {
		this.head = head;
		this.variables = variables;
		this.main = main;
		this.functions = functions;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += head.indentedToString(level + 1);
		answer += variables.indentedToString(level + 1);
		answer += functions.indentedToString(level + 1);
		answer += main.indentedToString(level + 1);
		return answer;
	}

}
