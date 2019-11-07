package mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import data.UMLEnvironment;
import data.UMLItem;

/**
 * The Class FieldMapper.
 */
public class FunctionMapper {

	  /** The logger. */
	  private static final Logger logger = Logger.getLogger(FunctionMapper.class.getName());
	  
	  /** The env. */
	  UMLEnvironment env;
	  
	  /**
	   * Instantiates a new attribute mapper.
	   */
	  public FunctionMapper() {
	    super();
	  }
	  
	  /**
	   * Instantiates a new attribute mapper.
	   *
	   * @param env the UMLEnvironment
	   */
	  public FunctionMapper(UMLEnvironment env) {
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
	   * Edits the function type.
	   *
	   * @param className the class name
	   * @param key the key for the function var
	   * @param oldFunction the old function type
	   * @param newFunction the new function type
	   */
	  public void editFunctionType(String className, String key, String oldFunction, String newFunction) {
	    UMLItem item = env.findItem(className);
	    if (item == null) {
	      logger.warning("Class name " + className + " does not exist.");
	    } else if (item.getFunctions().containsKey(key)) {
	      item.editFunction(key, newFunction);
	      logger.info("Type " + oldFunction + " changed to " + newFunction + ".");
	    } else {
	      logger.warning("Variable " + key + " doesn't exist.");
	    }
	  }
	  
	  /**
	   * Edits the variable of the Function
	   *
	   * @param className the class name
	   * @param oldKey the old key for the Function var
	   * @param newKey the new key for the Function var
	   * @param oldFunction the old Function type
	   * @param newFunction the new Functiontype
	   */
	  public void editFunctionVar(String className, String oldKey, String newKey, String oldFunction, String newFunction) {
	    UMLItem item = env.findItem(className);
	    if (item == null) {
	      logger.warning("Class name " + className + " does not exist.");
	    } else if (item.getFunctions().containsKey(newKey)) {
	 	     logger.warning("Variable " + newKey + " already exists."); 
	 	} else if (item.getFunctions().containsKey(oldKey)) {
	      item.editFunction(oldKey, newKey, newFunction);
	      logger.info("Variable" + oldKey + " changed to " + newKey + ".");
	      logger.info("Type" + oldFunction + " changed to " + newFunction + ".");
	    }else {
	      logger.warning("Variable " + oldKey + " doesn't exist.");
	    }
	  }
	  
	  /**
	   * Adds the Function
	   *
	   * @param className the class name
	   * @param var the variable for the Function
	   * @param type the type of the Function
	   */
	  public void addFunction(String className, String type, String var) {
	    UMLItem item = env.findItem(className);
	    if (item == null) {
	      logger.warning("Class name " + className + " does not exist.");
	    } else if (item.getFunctions().containsKey(var)) {
	      logger.warning("Variable " + var + " already exists.");
	    } else {
	      item.addFunction(type, var);
	    }
	  }
	  
	  /**
	   * Delete the Function 
	   *
	   * @param className the class name
	   * @param var
	   */
	  public void deleteFunction(String className, String var) {
	    UMLItem item = env.findItem(className);
	    if (item == null) {
	      logger.warning("Class name " + className + " does not exist.");
	    } else if (item.getFunctions().containsKey(var)) {
	     item.removeFunction(var);
	      logger.info("Function with variable " + var + " deleted.");
	    } else {
	      logger.warning("Variable " + var + " doesn't exist.");
	    }
	  }
}
