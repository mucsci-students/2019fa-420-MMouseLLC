package data;

/**
 * 
 * @author Matt Fossett
 *
 */
public class ParentChildPair {
	private UMLItem parent;
	private UMLItem child;
	
	public ParentChildPair(UMLItem p, UMLItem c) {
		this.parent = p;
		this.child = c;
	}

	public UMLItem getParent() {
		return parent;
	}

	public void setParent(UMLItem parent) {
		this.parent = parent;
	}

	public UMLItem getChild() {
		return child;
	}

	public void setChild(UMLItem child) {
		this.child = child;
	}
	
}
