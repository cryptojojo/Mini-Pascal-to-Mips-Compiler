package syntaxtree;

/**
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */
public abstract class SubProgramNode extends SyntaxTreeNode {

	private DeclarationsNode variables;
	private CompoundStatementNode main;
	private SubProgramDeclarationsNode functions;

	public SubProgramNode(DeclarationsNode variables, SubProgramDeclarationsNode functions,
			CompoundStatementNode main) {
		this.variables = variables;
		this.main = main;
		this.functions = functions;
	}

	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += variables.indentedToString(level + 1);
		answer += functions.indentedToString(level + 1);
		answer += main.indentedToString(level + 1);
		return answer;
	}

}
