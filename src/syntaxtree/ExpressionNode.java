package syntaxtree;

import parser.DataType;
import scanner.TokenType;

/**
 * General representation of any expression.
 * 
 * @author Erik Steinmetz, Joseph Miller <miller12 @ augsburg.edu>
 */
public abstract class ExpressionNode extends SyntaxTreeNode {

	DataType type;

	public ExpressionNode() {
		type = null;
	}

	public ExpressionNode(DataType type) {
		this.type = type;
	}

	public void setType(DataType dataType) {
		this.type = dataType;
	}

	public DataType getType() {
		return type;
	}

}
