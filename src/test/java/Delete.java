import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import data.UMLEnvironment;
import data.UMLItem;
import utility.Console;
import utility.IConsole;

public class Delete {
	/** The UMLEnvironment. */
	  private UMLEnvironment env;
	  private IConsole c = new Console();
	  
	  @Before
	  public void setEnv() {
	    env = c.getUMLEnvironment();
	  }
	
	  public String list(){
		  String buildUp = "[ ";
		  for (UMLItem i : env.getItems()){
			  buildUp += i.getName() + " ";
		  }
		  buildUp += "]";
		  return buildUp;
	  }

	
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
