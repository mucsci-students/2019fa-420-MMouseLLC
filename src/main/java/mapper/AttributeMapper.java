package mapper;

import java.util.logging.Logger;

import data.UMLEnvironment;
import data.UMLItem;
import deprecated.AddClass;

/**
 * The Class AttributeMapper.
 */
public class AttributeMapper {
  
  /** The logger. */
  private static final Logger logger = Logger.getLogger(AttributeMapper.class.getName());
  
  /** The env. */
  UMLEnvironment env;
  
  /**
   * Instantiates a new attribute mapper.
   */
  public AttributeMapper() {
    super();
  }
  
  /**
   * Instantiates a new attribute mapper.
   *
   * @param env the UMLEnvironment
   */
  public AttributeMapper(UMLEnvironment env) {
    this.env = env;
  }

  /**
   * Given command list_attributes  [className], log the attributes of given class.
   *
   * @param itemName the item name
   * @return the string
   */
  public String listAttributes(String itemName) {
    UMLItem item = env.findItem(itemName);
    if (item == null) {
      logger.warning("Class " + itemName + " does not exist.");
      return null;
    }
    StringBuilder str = new StringBuilder();
    str.append("[ ");
    for (String attr : item.getAttributes()) {
      str.append("{" + attr + "} ");
    }
    str.append("]");
    return str.toString();
  }
  
  /**
   * Edits the attribute.
   *
   * @param className the class name
   * @param oldAttr the old attribute
   * @param newAttr the new attribute
   */
  public void editAttribute(String className, String oldAttr, String newAttr) {
    UMLItem item = env.findItem(className);
    if (item == null) {
      logger.warning("Class name " + className + " does not exist.");
    } else if (item.getAttributes().contains(newAttr)) {
      logger.warning("Attribute " + newAttr + " already exists.");
    } else if (item.getAttributes().contains(oldAttr)) {
      item.editAttribute(oldAttr, newAttr);
      logger.info("Attribute " + oldAttr + " changed to " + newAttr + ".");
    } else {
      logger.warning("Attribute " + oldAttr + " doesn't exist.");
    }
  }
  
  /**
   * Adds the attribute.
   *
   * @param className the class name
   * @param attributeName the attribute name
   */
  public void addAttribute(String className, String attributeName) {
    UMLItem item = env.findItem(className);
    if (item == null) {
      logger.warning("Class name " + className + " does not exist.");
    } else if (item.getAttributes().contains(attributeName)) {
      logger.warning("Attribute " + attributeName + " already exists.");
    } else {
      item.addAttribute(attributeName);
    }
  }
  
  /**
   * Delete attribute.
   *
   * @param className the class name
   * @param attributeName the attribute name
   */
  public void deleteAttribute(String className, String attributeName) {
    UMLItem deleteAttr = env.findItem(className);
    if (deleteAttr == null) {
      logger.warning("Class name " + className + " does not exist.");
    } else if (deleteAttr.getAttributes().contains(attributeName)) {
      deleteAttr.removeAttribute(attributeName);
      logger.info("Attribute " + attributeName + " deleted.");
    } else {
      logger.warning("Attribute " + attributeName + " doesn't exist.");
    }
  }

}
