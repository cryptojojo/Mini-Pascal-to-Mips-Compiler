
package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a collection of subprogram declarations.
 * 
 * @author Erik Steinmetz
 */
public class SubProgramDeclarationsNode extends SyntaxTreeNode {

	private ArrayList<SubProgramNode> subPList = new ArrayList<>();

	public void addSubProgramDeclaration(SubProgramNode aSubProgram) {
		subPList.add(aSubProgram);
	}

	public void addall(ArrayList<SubProgramNode> aSubProgram) {
		subPList.addAll(aSubProgram);
	}

	public ArrayList<SubProgramNode> getProcs() {
		return subPList;
	}

	public String indentedToString(int level) {
		String answer = this.indentation(level);
		answer += "SubProgramDeclarations\n";
		for (SubProgramNode subProg : subPList) {
			answer += subProg.indentedToString(level + 1);
		}
		return answer;
	}

}
