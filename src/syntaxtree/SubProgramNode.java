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

}
