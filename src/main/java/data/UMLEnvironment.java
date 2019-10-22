package data;

import java.util.ArrayList;
import java.util.logging.Logger;

import utility.AddClass;

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
  int size;

  public int getSize() {
    return items.size();
  }

  public UMLEnvironment() {
    this.size = 0;
    this.items = new ArrayList<>();
  }

  public UMLEnvironment(UMLItem item) {
    this.items = new ArrayList<>();
    this.items.add(item);
    this.size++;
  }

  public ArrayList<UMLItem> getItems() {
    return this.items;
  }

  /**
   * if a UMLItem with name itemName exists in this environment then returns
   * that item else return null
   * 
   * @param itemName
   * @return
   */
  public UMLItem findItem(String itemName) {
    for (UMLItem i : items) {
      if (i.getName().equals(itemName)) {
        return i;
      }
    }
    return null;
  }

  public void addItem(UMLItem item) {
    if (!itemExists(item)) {
      this.items.add(item);
      this.size++;
    }
  }

  public void removeItem(UMLItem item) {
    if (!itemExists(item)) {
      this.items.remove(item);
      this.size--;
    }
  }

  public void editItem(String oldClass, String newClass, UMLItem item) {
    if (item == null || !itemExists(item)) {
      logger.warning("Class " + oldClass + " does not exist. Edit cancelled.");
    } else {
      item.setName(newClass);
      logger.info("Class " + oldClass + " changed to " + newClass + ".");
    }
  }

  /**
   * gives a list of the classes currently in the environment.
   */
  public String listClasses() {
    StringBuilder builder = new StringBuilder();
    builder.append("List of classes: [ ");
    for (UMLItem i : items) {
      builder.append("{" + i.getName() + "} ");
    }
    builder.append("]\n");
    return builder.toString();
  }

  public void addChild(String childName, String parentName, UMLItem childItem, UMLItem parentItem) {
    if (childItem == null || itemExists(childItem)) {
      logger.warning("Child class " + childName + " does not exist. Add child cancelled.");
    } else if (parentItem == null || itemExists(parentItem)) {
      logger.warning("Parent class " + parentName + " does not exist. Add child cancelled.");
    } else if (childItem == parentItem) {
      logger.warning("Child name cannot be the same as parent: " + parentName + ". Add child cancelled.");
    } else if (childItem.getParents().contains(parentItem) && parentItem.getChildren().contains(childItem)) {
      logger.warning("Child " + childName + " already linked to parent " + parentName + ". Add child cancelled.");
    } else {
      parentItem.addChild(childItem);
      childItem.addParent(parentItem);
      logger.info("Child successfully added.");
    }
  }

  public void removeChild(String childName, String parentName, UMLItem childItem, UMLItem parentItem) {
    if (childItem == null || itemExists(childItem)) {
      logger.warning("Child class " + childName + " does not exist. Remove child cancelled.");
    } else if (parentItem == null || itemExists(parentItem)) {
      logger.warning("Parent class " + parentName + " does not exist. Remove child cancelled.");
    } else if (!childItem.getParents().contains(parentItem) && !parentItem.getChildren().contains(childItem)) {
      logger.warning("Child " + childName + " not linked to parent " + parentName + ". Remove child cancelled.");
    } else {
      parentItem.removeChild(childItem);
      childItem.removeParent(parentItem);
      logger.info("Child successfully removed.");
    }
  }

  public String listChildren(String parentName, UMLItem parentItem) {
    StringBuilder builder = new StringBuilder();
    builder.append("List of children [ ");
    for (UMLItem i : parentItem.getChildren()) {
      builder.append("{" + i.getName() + "} ");
    }
    builder.append("]");
    return builder.toString();
  }

  public String listParents(String childName, UMLItem childItem) {
    StringBuilder builder = new StringBuilder();
    builder.append("List of parents [ ");
    for (UMLItem i : childItem.getParents()) {
      builder.append("{" + i.getName() + "} ");
    }
    builder.append("]");
    return builder.toString();
  }

  ////////////////////////////////////
  ////////// HELPER METHODS //////////
  ////////////////////////////////////

  public boolean itemExists(UMLItem item) {
    if (!this.items.isEmpty()) {
      for (int i = 0; i < this.items.size(); i++) {
        if (item.getName().equals(this.items.get(i).getName())) {
          return true;
        }
      }
    }
    return false;
  }

}
