package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/*
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

  public UMLEnvironment() {
    this.items = new ArrayList<>();
    this.relationships = new ArrayList<>();
    
  }

  public UMLEnvironment(UMLItem item) {
    this.items = new ArrayList<>();
    this.items.add(item);
    this.relationships = new ArrayList<>();
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
        //System.out.println("Class " + itemName + " found.");
        return i;
      }
    }
    //logger.warning("Class " + itemName + " not found.");
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
   * Adds a child to a parent in the environment. Checks if the child/parent
   * exists, and if the child is not already linked to the parent. If so add
   * the child to the parent.
   * 
   * @param childName The child name
   * @param parentName The parent name
   * @param childItem The child UMLItem
   * @param parentItem The parent UMLItem
   */
  @Deprecated
  public void addChild(String childName, String parentName, UMLItem childItem, UMLItem parentItem) {
    if (childItem == null || !itemExists(childItem)) {
      logger.warning("Child class " + childName + " does not exist. Add child cancelled.");
    } else if (parentItem == null || !itemExists(parentItem)) {
      logger.warning("Parent class " + parentName + " does not exist. Add child cancelled.");
    } else if (childItem == parentItem) {
      logger.warning("Child name cannot be the same as parent: " + parentName + ". Add child cancelled.");
    } else if (childItem.getParents().contains(parentItem) && parentItem.getChildren().contains(childItem)) {
      logger.warning("Child " + childName + " already linked to parent " + parentName + ". Add child cancelled.");
    } else {
      parentItem.addChild(childItem);
      childItem.addParent(parentItem);
      System.out.println("Child successfully added.");
    }
  }

  /**
   * Removes a child from a parent in the environment.Checks if the child/parent
   * exists, and if the child is not already linked to the parent. If so remove
   * the child to the parent.
   * 
   * @param childName The child name
   * @param parentName The parent name
   * @param childItem The child UMLItem
   * @param parentItem The parent UMLItem
   */
  @Deprecated
  public void removeChild(String childName, String parentName, UMLItem childItem, UMLItem parentItem) {
    if (childItem == null || !itemExists(childItem)) {
      logger.warning("Child class " + childName + " does not exist. Remove child cancelled.");
    } else if (parentItem == null || !itemExists(parentItem)) {
      logger.warning("Parent class " + parentName + " does not exist. Remove child cancelled.");
    } else if (!childItem.getParents().contains(parentItem) && !parentItem.getChildren().contains(childItem)) {
      logger.warning("Child " + childName + " not linked to parent " + parentName + ". Remove child cancelled.");
    } else {
      parentItem.removeChild(childItem);
      childItem.removeParent(parentItem);
      System.out.println("Child successfully removed.");
    }
  }

  /**
   * Lists all children from a parent UMLItem
   * 
   * @param parentName The parent name
   * @return builder The string of all children
   */
  @Deprecated
  public String listChildren(String parentName) {
    UMLItem parentItem = findItem(parentName);
    StringBuilder builder = new StringBuilder();
    if (parentItem == null || !itemExists(parentItem)) {
      logger.warning("Parent class " + parentName + " does not exist. List children cancelled.");
    } else {
      builder.append("List of children [ ");
      for (UMLItem i : parentItem.getChildren()) {
        builder.append("{" + i.getName() + "} ");
      }
      builder.append("]");      
    }
    return builder.toString();
  }

  /**
   * Lists all parents from a child UMLItem
   * 
   * @param childName The child name
   * @return builder The string of all parents
   */
  @Deprecated
  public String listParents(String childName) {
    StringBuilder builder = new StringBuilder();
    UMLItem childItem = findItem(childName);
    if (childItem == null || !itemExists(childItem)) {
      logger.warning("Child class " + childName + " does not exist. List parents cancelled.");
    } else {
      builder.append("List of parents [ ");
      for (UMLItem i : childItem.getParents()) {
        builder.append("{" + i.getName() + "} ");
      }
      builder.append("]");      
    }
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
   * Find and return Relationship r in list of relationships
   *   else return null
   * @param r
   * @return
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
   * Find and return a Relationship 
   * @param parent
   * @param child
   * @return
   */
  public Relationship findRelationship(UMLItem parent, UMLItem child) {
	  for (Relationship i : relationships) {
		  if (i.getParent().equals(parent) && i.getChild().equals(child)) {
			  return i;
		  }
	  }
	  return null;
  }
  
  public ArrayList<Relationship> getRelationships(){
	  return relationships;
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
   * @return
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
   * @return
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
