/*
 * UMLItem is a class that represents a single item in the UML Environment.
 * Getters and Setters are created here to define Id, Name, Parent, Children
 * and Attributes for the UMLItem.
 * @author Eric Hinerdeer 
 * Modified by Matt Fossett
 * Date: August 29, 2019
 */

import java.util.ArrayList;

public class UMLItem {
    private int id;
    private String name;
    private ArrayList<String> attributes;
    private ArrayList<UMLItem> parents;
    private ArrayList<UMLItem> children;
    
    /**
     * Default Constructor
     * id is assigned -1, Name is empty string, lists are empty
     */
    public UMLItem() {
    		id = -1;
    		name = "";
    		attributes = new ArrayList<String>();
    		parents    = new ArrayList<UMLItem>();
    		children   = new ArrayList<UMLItem>();
    }
    
    /**
     * Constructor with One parent and Child
     * @param Id
     * @param Name
     * @param Parent- Single UMLItem 
     * @param Child- Single UMLItem
     */
    public UMLItem(int id, String name, UMLItem parent, UMLItem child) {
        this.id = id;
        this.name = name;
        
        this.attributes = new ArrayList<String>();
		this.parents    = new ArrayList<UMLItem>();
		this.children   = new ArrayList<UMLItem>();
		
		this.parents.add(parent);
		this.children.add(child);
    }
    
    /**
     * Verbose Constructor, every field is added
     * @param Id
     * @param Name
     * @param p
     * @param c
     * @param attr
     */
    public UMLItem (int id, String name, ArrayList<UMLItem> p, 
    		ArrayList<UMLItem> c, ArrayList<String> attr){
    		this.id = id;
    		this.name = name;
    		this.parents = p;
    		this.children = c;
    		this.attributes = attr;
    }
    
    /**
     * Simple Constructor
     * Only assigns ID and Name
     * @param Id
     * @param name
     */
    public UMLItem(int id, String name){
	    	this.id = id;
	    	this.name = name;
	    	
    		attributes = new ArrayList<String>();
    		parents    = new ArrayList<UMLItem>();
    		children   = new ArrayList<UMLItem>();
    }
    
    /**
     * @return unique ID assigned to this UMLItem
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * Set new ID
     * @param Id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Get the ArrayList of attribute Strings
     * @return ArrayList<String> attributes
     */
    public ArrayList<String> getAttributes() {
        return this.attributes;
    }
    
    /**
     * Get the ArrayList of attribute Strings
     * @param existingAttribute
     * @return true if attr is found in ArrayList<String> attributes, else false
     */
    public boolean existingAttribute(String existingAttribute) {
    	return this.attributes.contains(existingAttribute);
    }
    
    /**
     * Add a single String attribute to the ArrayList<String> attributes
     * @param attr
     */
    public void addAttribute(String attr) {
        this.attributes.add(attr);
    }
    
    
    public boolean editAttribute(String oldAttr, String newAttr) {
    	if (!existingAttribute(oldAttr))
    	{
    		return false;
    	}
    	else if (existingAttribute(newAttr))
    	{
    		return false;
    	}
    	attributes.remove(oldAttr);
    	attributes.add(newAttr);
		return true;
    }
    /**
     * If String attr is found in the attributes list then remove
     * @param attr
     * @return true if attr is found in ArrayList<String> attributes, else false
     */
    public boolean removeAttribute(String attr){
    		return this.attributes.remove(attr);
    }
    
    /**
     * Getter 
     * @return
     */
    public ArrayList<UMLItem> getParents() {
        return this.parents;
    }
    
    /**
     * Adds UMLItem to this class list of parents
     * @param parent
     */
    public void addParent(UMLItem parent) {
    		parents.add(parent);
    }
    
    /**
     * Set field ArrayList<UMLItem> parents to the parents parameter
     * @param parents
     */
    public void setParents(ArrayList<UMLItem> parents){
    		this.parents = parents;
    }
    
    /**
     * If UMLItem i is found in parent list then it will be removed
     * @param i
     * @return true if item exists in list else false
     */
    boolean removeParent(UMLItem i){
    		return parents.remove(i);
    }
    
    /**
     * Getter for the children ArrayList
     * @return ArrayList<UMLItem> children
     */
    public ArrayList<UMLItem> getChildren() {
        return this.children;
    }
    
    /**
     * Sets children field ArrayList<UMLItem> to c
     * @param c
     */
    public void setChildren(ArrayList<UMLItem> c){
    		this.children = c;
    }
    
    /**
     * Add UMLItem as a child to the children list 
     * @param newChild
     */
    public void addChild(UMLItem newChild) {
        this.children.add(newChild);
    }
    
    /**
     * if UMLItem is found in children list then it will be removed 
     * @param remove
     * @return true if item found in children, else false
     */
    public boolean removeChild(UMLItem remove) {
        return children.remove(remove);
    }
}
