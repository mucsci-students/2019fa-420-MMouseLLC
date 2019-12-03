package data;

import java.util.ArrayList;
import java.util.HashMap;

/**
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
	@Deprecated
	private ArrayList<String> attributes;
	/**
	 * Map of fields associated with this class Map of variable names to their type
	 **/
	private HashMap<String, String> fields;
	/** Map of functions associated with this class **/
	private HashMap<String, String> functions;
	/** List of parents that this class inherits from **/

	/**
	 * Default Constructor id is assigned -1, Name is empty string, lists are empty
	 */
	public UMLItem() {
		id = -1;
		name = "";
		attributes = new ArrayList<String>();
		this.fields = new HashMap<>();
		this.functions = new HashMap<>();
	}

	/**
	 * Constructor with One parent and Child
	 * 
	 * @param Id
	 * @param Name
	 * @param Parent Single UMLItem
	 * @param Child  Single UMLItem
	 */
	public UMLItem(int id, String name, UMLItem parent, UMLItem child) {
		this.id = id;
		this.name = name;

		this.attributes = new ArrayList<>();
		this.fields = new HashMap<>();
		this.functions = new HashMap<>();
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
		this.fields = new HashMap<>();
		this.functions = new HashMap<>();
	}

	/**
	 * Name Constructor Only assigns Name
	 * 
	 * @param name
	 */
	public UMLItem(String name) {
		this.name = name;
		this.attributes = new ArrayList<>();
		this.fields = new HashMap<>();
		this.functions = new HashMap<>();
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
	 * @return String name
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
	 * Get the HashMap of the field
	 * 
	 * Returns the field
	 */
	public HashMap<String, String> getFields() {
		return this.fields;
	}

	/**
	 * Get the HashMap of the function
	 * 
	 * Returns the function
	 */
	public HashMap<String, String> getFunctions() {
		return this.functions;
	}

	/**
	 * Add a variable and type to the HashMap<String, String> fields if var is
	 * unique
	 * 
	 * @param type
	 * @param var
	 * 
	 * @return true if var is unique
	 */
	public boolean addField(String type, String var) {
		if (!existingField(var)) {
			fields.put(var, type);
			return true;
		}
		return false;
	}

	/**
	 * Add a variable and type to the HashMap<String, String> function if var is
	 * unique
	 * 
	 * @param type
	 * @param var
	 * 
	 * @return true if var is unique
	 */
	public boolean addFunction(String type, String var) {
		if (!existingFunction(var)) {
			functions.put(var, type);
			return true;
		}
		return false;
	}

	/**
	 * Check if field is unique
	 * 
	 * @param var
	 * 
	 * @return true if var exists
	 */
	public boolean existingField(String var) {
		return this.fields.containsKey(var);
	}

	/**
	 * Check if function is unique
	 * 
	 * @param var
	 * 
	 * @return true if var exists
	 */
	public boolean existingFunction(String var) {
		return this.functions.containsKey(var);
	}

	/**
	 * Edit the type associated with var in fields
	 * 
	 * @params var
	 * @params newType
	 * 
	 * @return true if var was found and type was edited, false if not found
	 */
	public boolean editField(String var, String newType) {
		return fields.replace(var, newType) != null;
	}

	/**
	 * Edit the var and type associated with the old var in fields
	 * 
	 * @params oldVar
	 * @params newVar
	 * @params newType
	 * 
	 * @return true if var was found and type and var are both edited, false if var
	 *         not found
	 */
	public boolean editField(String oldVar, String newVar, String newType) {
		if (fields.remove(oldVar) == null) {
			return false;
		}
		fields.put(newVar, newType);
		return true;
	}

	/**
	 * Edit the type associated with var in functions
	 * 
	 * @params var
	 * @params newType
	 * 
	 * @return true if var was found and type was edited, false if not found
	 */
	public boolean editFunction(String var, String newType) {
		return functions.replace(var, newType) != null;
	}

	/**
	 * Edit the var and type associated with the old var in functions
	 * 
	 * @params oldVar
	 * @params newVar
	 * @params newType
	 * 
	 * @return true if var was found and type and var are both edited, false if var
	 *         not found
	 */
	public boolean editFunction(String oldVar, String newVar, String newType) {
		if (functions.remove(oldVar) == null) {
			return false;
		}
		functions.put(newVar, newType);
		return true;
	}

	/**
	 * If String var is found as a key then remove the pair
	 * 
	 * @param var
	 * 
	 * @return true if the field pair was removed, false if not found
	 */
	public boolean removeField(String var) {
		return this.fields.remove(var) != null;
	}

	/**
	 * If String var is found as a key then remove the pair
	 * 
	 * @param var
	 * 
	 * @return true if the functions pair was removed, false if not found
	 */
	public boolean removeFunction(String var) {
		return this.functions.remove(var) != null;
	}

	/**
	 * Get the ArrayList of attribute Strings
	 * 
	 * @return ArrayList<String> attributes
	 */
	@Deprecated
	public ArrayList<String> getAttributes() {
		return this.attributes;
	}

	/**
	 * Add a single String attribute to the ArrayList<String> attributes
	 * 
	 * @param attr
	 */
	@Deprecated
	public void addAttribute(String attr) {
		this.attributes.add(attr);
	}

	/**
	 * Get the ArrayList of attribute Strings
	 * 
	 * @param existingAttribute
	 * @return true if attr is found in ArrayList<String> attributes, else false
	 */
	@Deprecated
	public boolean existingAttribute(String existingAttribute) {
		return this.attributes.contains(existingAttribute);
	}

	/**
	 * Edit a single String attribute to the ArrayList<String> attributes
	 * 
	 * @params oldAttr, newAttr
	 */
	@Deprecated
	public void editAttribute(String oldAttr, String newAttr) {
		attributes.remove(oldAttr);
		attributes.add(newAttr);
	}

	/**
	 * If String attr is found in the attributes list then remove
	 * 
	 * @param attr
	 * @return true if attr is found in ArrayList<String> attributes, else false
	 */
	@Deprecated
	public boolean removeAttribute(String attr) {
		return this.attributes.remove(attr);
	}


}
