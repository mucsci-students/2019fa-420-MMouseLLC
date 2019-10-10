import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.junit.Test;

/**
 * The Class SingleLineCommandTest
 */
public class AttributeTest {
  
  /** The UMLEnvironment. */
  private UMLEnvironment env = new UMLEnvironment();
  private Console c = new Console(env);
  
  public String list(){
	  String buildUp = "[ ";
	  for (UMLItem i : env.Items){
		  buildUp += i.getName() + " ";
	  }
	  buildUp += "]";
	  return buildUp;
  }
  
  public String listAttributes(String umlName) {
	  UMLItem i = null;
	  for (UMLItem x : env.Items) {
		  if (x.getName().contentEquals(umlName)) {
			  i = x;
		  }
	  }
	  if (i == null) {
		  return "[ ]";
	  }
	  String buildUp = "[ ";
	  for (String j : i.getAttributes()) {
		  buildUp += j + " ";
	  }
	  buildUp += "]";
	  return buildUp;
  }
  
  @Test
  public void testAdd() throws IOException {
    c.checkInput("add matt \n");
    c.checkInput("addattribute \n matt \n test \n y \n");
    //c.checkInput("matt");
    //c.checkInput("test");
    //c.checkInput("y");
    
    listAttributes("matt");
    assertEquals("[ matt ]", list());
    assertEquals("[ test ]", listAttributes("matt"));
    
    //assertEquals("[ Matt Dan Eric Kasey ]", list());
    
  }
}
  
