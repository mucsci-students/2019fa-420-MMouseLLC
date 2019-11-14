package data;

/**
 * Object containing UMLItem parent and UMLItem child to store the relationship between them
 * @author Matt Fossett
 * 
 */
public class ParentChildPair {
	private UMLItem parent;
	private UMLItem child;
	
	/**
	 * Construct with UMLItem parent and UMLItem child
	 * @param p UMLItem
	 * @param c UMLItem
	 */
	public ParentChildPair(UMLItem p, UMLItem c) {
		this.parent = p;
		this.child = c;
	}

	/**
	 * Get Parent UMLItem 
	 * @return UMLItem
	 */
	public UMLItem getParent() {
		return parent;
	}

	/**
	 * Set Parent UMLItem to new UMLItem
	 * @param parent
	 */
	public void setParent(UMLItem parent) {
		this.parent = parent;
	}

	/**
	 * Get Child UMLItem
	 * @return child UMLItem
	 */
	public UMLItem getChild() {
		return child;
	}

	/**
	 * Set Child UMLItem to new UMLItem
	 * @param child
	 */
	public void setChild(UMLItem child) {
		this.child = child;
	}
	
}
