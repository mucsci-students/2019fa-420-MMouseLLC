package mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import data.UMLEnvironment;
import data.UMLItem;
import deprecated.AddClass;

/**
 * The Class FieldMapper.
 */
public class FieldMapper {

	  /** The logger. */
	  private static final Logger logger = Logger.getLogger(AttributeMapper.class.getName());
	  
	  /** The env. */
	  UMLEnvironment env;
	  
	  /**
	   * Instantiates a new attribute mapper.
	   */
	  public FieldMapper() {
	    super();
	  }
	  
	  /**
	   * Instantiates a new attribute mapper.
	   *
	   * @param env the UMLEnvironment
	   */
	  public FieldMapper(UMLEnvironment env) {
	    this.env = env;
	  }

	  /**
	   * Given command list_map  [className], print the map of the field or function
	   *
	   * @param m the map
	   * @return the string
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
	   * Edits the field type.
	   *
	   * @param className the class name
	   * @param key the key for the field var
	   * @param oldField the old field type
	   * @param newField the new field type
	   */
	  public void editFieldType(String className, String key, String oldField, String newField) {
	    UMLItem item = env.findItem(className);
	    if (item == null) {
	      logger.warning("Class name " + className + " does not exist.");
	    } else if (item.getFields().containsKey(key)) {
	      item.editField(key, newField);
	      logger.info("Type " + oldField + " changed to " + newField + ".");
	    } else {
	      logger.warning("Variable " + key + " doesn't exist.");
	    }
	  }
	  
	  /**
	   * Edits the variable of the field
	   *
	   * @param className the class name
	   * @param oldKey the old key for the field var
	   * @param newKey the new key for the field var
	   * @param oldField the old field type
	   * @param newField the new field type
	   */
	  public void editFieldVar(String className, String oldKey, String newKey, String oldField, String newField) {
	    UMLItem item = env.findItem(className);
	    if (item == null) {
	      logger.warning("Class name " + className + " does not exist.");
	    } else if (item.getFields().containsKey(newKey)) {
	 	     logger.warning("Variable " + newKey + " already exists."); 
	 	} else if (item.getFields().containsKey(oldKey)) {
	      item.editField(oldKey, newKey, newField);
	      logger.info("Variable" + oldKey + " changed to " + newKey + ".");
	      logger.info("Type" + oldField + " changed to " + newField + ".");
	    }else {
	      logger.warning("Variable " + oldKey + " doesn't exist.");
	    }
	  }
	  
	  /**
	   * Adds the field
	   *
	   * @param className the class name
	   * @param var the variable for the field
	   * @param type the type of the field
	   */
	  public void addField(String className, String type, String var) {
	    UMLItem item = env.findItem(className);
	    if (item == null) {
	      logger.warning("Class name " + className + " does not exist.");
	    } else if (item.getFields().containsKey(var)) {
	      logger.warning("Variable " + var + " already exists.");
	    } else {
	      item.addField(type, var);
	    }
	  }
	  
	  /**
	   * Delete the field. 
	   *
	   * @param className the class name
	   * @param var
	   */
	  public void deleteField(String className, String var) {
	    UMLItem item = env.findItem(className);
	    if (item == null) {
	      logger.warning("Class name " + className + " does not exist.");
	    } else if (item.getFields().containsKey(var)) {
	     item.removeField(var);
	      logger.info("Field with variable " + var + " deleted.");
	    } else {
	      logger.warning("Variable " + var + " doesn't exist.");
	    }
	  }
}
