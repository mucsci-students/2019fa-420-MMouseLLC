package data;

import java.util.ArrayList;

/*
 * UMLItem is a class that represents a single item in the UML Environment.
 * Getters and Setters are created here to define Id, Name, Parent, Children
 * and Attributes for the UMLItem.
 * @author Eric Hinerdeer 
 * Modified by Matt Fossett
 * Date: August 29, 2019
 */
public class UMLItem {

	private int id;
	/** Unique class name **/
	private String name;
	/** List of attributes associated with this class **/
	private ArrayList<String> attributes;
	/** List of parents that this class inherits from **/
	private ArrayList<UMLItem> parents;
	/** List of children that inherit from this class **/
	private ArrayList<UMLItem> children;

	/**
	 * Default Constructor id is assigned -1, Name is empty string, lists are empty
	 */
	public UMLItem() {
		id = -1;
		name = "";
		attributes = new ArrayList<String>();
		parents = new ArrayList<>();
		children = new ArrayList<>();
	}

	/**
	 * Constructor with One parent and Child
	 * 
	 * @param Id
	 * @param Name
	 * @param Parent- Single UMLItem
	 * @param Child-  Single UMLItem
	 */
	public UMLItem(int id, String name, UMLItem parent, UMLItem child) {
		this.id = id;
		this.name = name;

		this.attributes = new ArrayList<>();
		this.parents = new ArrayList<>();
		this.children = new ArrayList<>();

		this.parents.add(parent);
		this.children.add(child);
	}

	/**
	 * Simple Constructor Only assigns ID and Name
	 * 
	 * @param Id
	 * @param name
	 */
	public UMLItem(int id, String name) {
		this.id = id;
		this.name = name;
		this.attributes = new ArrayList<>();
		this.parents = new ArrayList<>();
		this.children = new ArrayList<>();
	}

	/**
	 * Name Constructor Only assigns Name
	 * 
	 * @param name
	 */
	public UMLItem(String name) {
		this.name = name;
		this.attributes = new ArrayList<>();
		this.parents = new ArrayList<>();
		this.children = new ArrayList<>();
	}

	/**
	 * @return unique ID assigned to this UMLItem
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Set new ID
	 * 
	 * @param Id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Return name associated with this class
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Change name associated with this class
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the ArrayList of attribute Strings
	 * 
	 * @return ArrayList<String> attributes
	 */
	public ArrayList<String> getAttributes() {
		return this.attributes;
	}

	/**
	 * Add a single String attribute to the ArrayList<String> attributes
	 * 
	 * @param attr
	 */
	public void addAttribute(String attr) {
		this.attributes.add(attr);
	}

	/**
	 * Get the ArrayList of attribute Strings
	 * 
	 * @param existingAttribute
	 * @return true if attr is found in ArrayList<String> attributes, else false
	 */
	public boolean existingAttribute(String existingAttribute) {
		return this.attributes.contains(existingAttribute);
	}

	/**
	 * Edit a single String attribute to the ArrayList<String> attributes
	 * 
	 * @params oldAttr, newAttr
	 */
	public boolean editAttribute(String oldAttr, String newAttr) {
		if (!existingAttribute(oldAttr)) {
			return false;
		} else if (existingAttribute(newAttr)) {
			return false;
		}
		attributes.remove(oldAttr);
		attributes.add(newAttr);
		return true;
	}

	/**
	 * If String attr is found in the attributes list then remove
	 * 
	 * @param attr
	 * @return true if attr is found in ArrayList<String> attributes, else false
	 */
	public boolean removeAttribute(String attr) {
		return this.attributes.remove(attr);
	}

	/**
	 * Getter for parents list
	 * 
	 * @return
	 */
	public ArrayList<UMLItem> getParents() {
		return this.parents;
	}

	/**
	 * Adds UMLItem to this class list of parents
	 * 
	 * @param parent
	 */
	public void addParent(UMLItem parent) {
		parents.add(parent);
	}

	/**
	 * Set field ArrayList<UMLItem> parents to the parents parameter
	 * 
	 * @param parents
	 */
	public void setParents(ArrayList<UMLItem> parents) {
		this.parents = parents;
	}

	/**
	 * If UMLItem i is found in parent list then it will be removed
	 * 
	 * @param i
	 * @return true if item exists in list else false
	 */
	public boolean removeParent(UMLItem i) {
		return parents.remove(i);
	}

	/**
	 * Getter for the children ArrayList
	 * 
	 * @return ArrayList<UMLItem> children
	 */
	public ArrayList<UMLItem> getChildren() {
		return this.children;
	}

	/**
	 * Sets children field ArrayList<UMLItem> to c
	 * 
	 * @param c
	 */
	public void setChildren(ArrayList<UMLItem> c) {
		this.children = c;
	}

	/**
	 * Add UMLItem as a child to the children list
	 * 
	 * @param newChild
	 */
	public void addChild(UMLItem newChild) {
		this.children.add(newChild);
	}

	/**
	 * if UMLItem is found in children list then it will be removed
	 * 
	 * @param remove
	 * @return true if item found in children, else false
	 */
	public boolean removeChild(UMLItem remove) {
		return children.remove(remove);
	}

	/**
	 * Removes the child from a specified parent object.
	 * 
	 * @param parent the parent
	 * @param child  the child
	 */
	public void deleteChildFromParent(UMLItem parent, UMLItem child) {
		parent.removeChild(child);
	}

	/**
	 * Removes the child from all parent objects it is attached to.
	 * 
	 * @param child the child
	 */
	public void deleteAllChildren(UMLItem child) {
		for (UMLItem i : parents) {
			if (i.equals(child))
				i.removeChild(child);
		}
	}
}
