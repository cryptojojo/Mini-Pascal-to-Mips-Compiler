/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

import parser.DataType;
import scanner.TokenType;

/**
 * Represents a value or number in an expression.
 * 
 * @author Erik Steinmetz, Joseph Miller <miller12 @ augsburg.edu>
 */
public class ValueNode extends ExpressionNode {

	/** The attribute associated with this node. */
	String attribute;
	DataType t;

	/**
	 * Creates a ValueNode with the given attribute.
	 * 
	 * @param attr
	 *            The attribute for this value node.
	 */
	public ValueNode(String attr, DataType type) {
		this.t = type;
		this.attribute = attr;
	}

	/**
	 * Returns the attribute of this node.
	 * 
	 * @return The attribute of this ValueNode.
	 */
	public String getAttribute() {
		return (this.attribute);
	}

	public void setType(DataType type) {
		super.setType(type);
		this.t = type;
	}

	public DataType getType() {
		return this.t;
	}

	/**
	 * Returns the attribute as the description of this node.
	 * 
	 * @return The attribute String of this node.
	 */
	@Override
	public String toString() {
		return (attribute);
	}

	/**
	 * Creates a String representation of this value node.
	 * 
	 * @param level
	 *            The tree level at which this node resides.
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "Value: " + this.attribute + " (Expression Type: " + t + ")\n";
		return answer;
	}

	@Override
	public boolean equals(Object o) {
		boolean answer = false;
		if (o instanceof ValueNode) {
			ValueNode other = (ValueNode) o;
			if (this.attribute.equals(other.attribute))
				answer = true;
		}
		return answer;
	}

}
