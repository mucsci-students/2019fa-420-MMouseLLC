package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * UMLEnvironment is an object meant to keep track of all the UMLItems that 
 * exist at any given point in the running of the program. The purpose of this 
 * class will be to help define what can and cannot be used during the duration
 * of use of the program. 
 * @author Eric Hinerdeer
 * Date: August 29, 2019
 */
public class UMLEnvironment {

  private static final Logger logger = Logger.getLogger(UMLEnvironment.class.getName());

  ArrayList<UMLItem> items;
  ArrayList<Relationship> relationships;
  ArrayList<Category> categories;

  /**
   * Construct a new UMLEnvironment
   */
  public UMLEnvironment() {
    this.items = new ArrayList<>();
    this.relationships = new ArrayList<>();
    this.categories =  new ArrayList<>();
    
  }

  /**
   * if a UMLItem with name itemName exists in this environment then returns
   * that item else return null
   * 
   * @param itemName
   * @return The UMLItem
   */
  public UMLItem findItem(String itemName) {
    for (UMLItem i : items) {
      if (i.getName().equals(itemName)) { 
        return i;
      }
    }
    return null;
  }

  /**
   * Adds a UMLItem to the environment if it is not already in it.
   * 
   * @param item The UMLItem
   */
  public void addItem(UMLItem item) {
    if (item == null || !itemExists(item)) {
      this.items.add(item);
      System.out.println("Class successfully added: " + item.getName());
    } else {
      logger.warning(item.getName() + " is already a class.");
    }
  }

  /**
   * Deletes a UMLItem from the environment if it exists in it.
   * 
   * @param item The UMLItem
   */
  public void removeItem(UMLItem item) {
    if (item != null && itemExists(item)) {
      System.out.println("Class " + item.getName() + " being removed.");   
      this.items.remove(item);
    } else {
      logger.warning("Class does not exist.");
    }
  }

  /**
   * Checks if a class exists in the environment, and if so edits the name
   * of the class to the new one
   * 
   * @param oldClass The old class name
   * @param newClass The new class name
   * @param item the UMLItem
   */
  public void editItem(String oldClass, String newClass, UMLItem item) {
    if (item == null || !itemExists(item)) {
      logger.warning("Class " + oldClass + " does not exist. Edit cancelled.");
    } else {
      item.setName(newClass);
      System.out.println("Class " + oldClass + " changed to " + newClass + ".");
    }
  }

  /**
   * Gets all classes in the environment.
   * 
   * @return builder the String of all classes
   */
  public String listClasses() {
    StringBuilder builder = new StringBuilder();
    builder.append("List of classes: [ ");
    for (UMLItem i : items) {
      builder.append("{" + i.getName() + "} ");
    }
    builder.append("]");
    return builder.toString();
  }
  
  /**
   * Gets all classes in the environment.
   * 
   * @return builder the String of all classes
   */
  public String listRelationships() {
    StringBuilder builder = new StringBuilder();
    builder.append("List of relationships: [ ");
    for (Relationship i : relationships) {
      builder.append("{" + i.getParent().getName() + "--[" + i.getQuantifierName() + "]->" + i.getChild().getName() + "} ");
    }
    builder.append("]");
    return builder.toString();
  }
  
  
  /**
   * Gets all categories in the environment.
   * 
   * @return builder the String of all classes
   */
  public String listCategories() {
    StringBuilder builder = new StringBuilder();
    builder.append("List of categories: [ ");
    for (Category i : categories) {
      builder.append("{" + i.getParent().getName() + "--[" + i.getCategoryType() + "]->" + i.getChild().getName() + "} ");
    }
    builder.append("]");
    return builder.toString();
  }
  
  /**
   * Gets all classes in the environment.
   * 
   * @return builder the String of all classes
   */
  public String listRelationships(ArrayList<Relationship> r) {
    StringBuilder builder = new StringBuilder();
    builder.append("[ ");
    for (Relationship i : r) {
      builder.append("{" + i.getParent().getName() + "--[" + i.getQuantifierName() + "]->" + i.getChild().getName() + "} ");
    }
    builder.append("]");
    return builder.toString();
  }
  
  /**
   * Add a new relationship to the environment
   * @param parent
   * @param child
   */
  public void addRelationship(Relationship r) {
	  if (findRelationship(r) == null) {
		  relationships.add(r);
	  }
	  
  }
  
  /**
   * Add a new category to the environment
   * @param parent
   * @param child
   */
  public void addCategory(Category c) {
	  if (findCategory(c) == null) {
		  categories.add(c);
	  }
	  
  }
  
  
  /**
   * Try to remove a relationship from the environment
   * 
   * @param parent
   * @param child
   * @return true if found, else false
   */
  public boolean removeRelationship(Relationship r) {
	  Relationship toRemove = findRelationship(r);
	  if (toRemove == null) {
		  return false;
	  }
	  
	  relationships.remove(toRemove);
	  
	  return true;
  }
  /**
   * Try to remove a Category from the environment
   * 
   * @param parent
   * @param child
   * @return true if found, else false
   */
  public boolean removeCategory(Category c) {
	  Category toRemove = findCategory(c);
	  if (toRemove == null) {
		  return false;
	  }
	  
	  categories.remove(toRemove);
	  
	  return true;
  }
  
  /**
   * Find and return Relationship r in list of relationships
   *   else return null
   * @param r
   * @return Relationship or null if not found
   */
  public Relationship findRelationship(Relationship r) {
	  for (Relationship i : relationships) {
		  if (i.getParent().equals(r.getParent()) && i.getChild().equals(r.getChild() )) {
			  return i;
		  }
	  }
	  return null;
  }
  
  /**
   * Find and return Category c in list of category
   *   else return null
   * @param c
   * @return Category or null if not found
   */
  public Category findCategory(Category c) {
	  for (Category i : categories) {
		  if (i.getParent().equals(c.getParent()) && i.getChild().equals(c.getChild() )) {
			  return i;
		  }
	  }
	  return null;
  }
  
  /**
   * Find and return a Relationship 
   * @param parent
   * @param child
   * @return Relationship or null if not found
   */
  public Relationship findRelationship(UMLItem parent, UMLItem child) {
	  for (Relationship i : relationships) {
		  if (i.getParent().equals(parent) && i.getChild().equals(child)) {
			  return i;
		  }
	  }
	  return null;
  }
  
  /**
   * Find and return a Category
   * @param parent
   * @param child
   * @return Category or null if not found
   */
  public Category findCategoryp(UMLItem parent, UMLItem child) {
	  for (Category i : categories) {
		  if (i.getParent().equals(parent) && i.getChild().equals(child)) {
			  return i;
		  }
	  }
	  return null;
  }
  
  /**
   * 
   * @return ArrayList of running relationships
   */
  public ArrayList<Relationship> getRelationships(){
	  return relationships;
  }
  
  /**
   * 
   * @return ArrayList of running categories
   */
  public ArrayList<Category> getCategories(){
	  return categories;
  }
  
  /**
   * Returns a list containing relationships where UMLItem i is the parent
   * @param i
   * @return ArrayList
   */
  public ArrayList<Relationship> relationshipsWithParent(UMLItem i ){
	  ArrayList<Relationship> arr = new ArrayList<>();
	  for (Relationship r : relationships) {
		  if (r.getParent().equals(i)) {
			  arr.add(r);
		  }
	  }
	  return arr;
  }

  
  /**
   * Returns a list containing relationships where UMLItem i is the child
   * @param i
   * @return ArrayList
   */
  public ArrayList<Relationship> relationshipsWithChild(UMLItem i ){
	  ArrayList<Relationship> arr = new ArrayList<>();
	  for (Relationship r : relationships) {
		  if (r.getChild().equals(i)) {
			  arr.add(r);
		  }
	  }
	  return arr;
  }
  
  /**
   * Given a map String=>String, return a String in Array style showing all members
   * Ex: [ { height: int } { Matt: String } ] 
   * @param m
   * @return Human Readable String of Map
   */
  public String listMap(HashMap<String, String> m) {
		StringBuilder s = new StringBuilder();
		s.append("[ ");
		
		for (Map.Entry<String, String> i : m.entrySet()) {
			s.append("{ " + i.getKey() + ": " +  i.getValue() + " } " );
		}
		s.append("]");
		return s.toString();
	}
  
  /**
   * List a UMLItem by every data member associated with it 
   * Returns a string giving information on its Fields, Functions, Relationships as parent and child
   * @param i UMLItem
   * @return String 
   */
  public String listClass(UMLItem i) {
	  StringBuilder s = new StringBuilder();
	  if (i == null) {
		  return "";
	  }
	  s.append("\n" + i.getName() + "\n");
	  s.append("Fields: ");
	  s.append(listMap(i.getFields()) + "\nFunctions: ");
	  s.append(listMap(i.getFunctions()) + "\nRelations as Parent: ");
	  s.append(this.listRelationships(this.relationshipsWithParent(i)) + "\nRelations as Child: ");
	  s.append(this.listRelationships(this.relationshipsWithChild(i)));
	  
	  
	  return s.toString();
	  
  }
  
  /**
   * Builds string containing verbose info on every class in environment
   *   Calls listClass
   * @return Human readable String
   */
  public String listClassesVerbose() {
	  StringBuilder s = new StringBuilder();
	  for (UMLItem i : items) {
		  s.append(listClass(i));
		  s.append("\n");
	  }
	  return s.toString();
  }

  ////////////////////////////////////
  ////////// HELPER METHODS //////////
  ////////////////////////////////////

  /**
   * Checks if the UMLItem exists in the environment
   * 
   * @param item the UMLItem
   * @return boolean If the item exists
   */
  public boolean itemExists(UMLItem item) {
    if (!this.items.isEmpty()) {
      for (int i = 0; i < items.size(); i++) {
        if (item.getName().equals(this.items.get(i).getName())) {
          return true;
        }
      }
    }
    return false;
  }
 

  /**
   * Gets all list of all UMLItems
   * 
   * @return items the list of UMLItem
   */
  public ArrayList<UMLItem> getItems() {
    return this.items;
  }

}
