import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.UMLEnvironment;
import data.UMLItem;
import utility.Console;

/**
 * Testing delete
 *
 */
public class DeleteTest {
	/** The UMLEnvironment. */
	  private UMLEnvironment env;
	  private Console c = new Console();
	  
	/**
	 * Start the environment for test 
	 */
	@Before
	  public void setEnv() {
	    env = c.getUMLEnvironment();
	  }
	
	/**
	 * Slick method for building up list of items
	 * @return formatted list of items
	 */
	public String list(){
		  String buildUp = "[ ";
		  for (UMLItem i : env.getItems()){
			  buildUp += i.getName() + " ";
		  }
		  buildUp += "]";
		  return buildUp;
	  }

	
	/**
	 *  Test delete regularly
	 */
	@Test
	public void testDelete() {
		String[] input = {"add", "matt"};
		c.checkInput(input);  
		
		assertEquals(list(), "[ matt ]");
		
		input = new String[3];
		input[0] = "delete";
		input[1] = "-f";
		input[2] = "matt";
		c.checkInput(input);
		assertEquals(list(), "[ ]");
		
	}
	
	/**
	 * Test delete when something does not exist
	 */
	@Test
	public void testDeleteNotExist() {
		String[] input = new String[3];
		input[0] = "delete";
		input[1] = "-f";
		input[2] = "matt";
		c.checkInput(input);
		// Didn't get null ptr
		assertEquals(list(), "[ ]");
		
		input = new String[2];
		input[0] = "delete";
		
		input[1] = "matt";
		c.checkInput(input);
		assertEquals(list(), "[ ]");
		
	}

}
