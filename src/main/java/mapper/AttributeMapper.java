package mapper;

import java.util.logging.Logger;

import data.UMLEnvironment;
import data.UMLItem;

public class AttributeMapper {
  
  Logger logger = Logger.getLogger(AttributeMapper.class.getName());
  
  UMLEnvironment env;
  
  public AttributeMapper() {
    super();
  }
  
  public AttributeMapper(UMLEnvironment env) {
    this.env = env;
  }

  /**
   * Given command list_attributes  [className], log the attributes of given class
   * @param input
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
  

}
