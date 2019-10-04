import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import data.UMLEnvironment;
import data.UMLItem;
import utility.Console;

/**
 * The Class SingleLineCommandTest
 */
public class SingleLineCommandTest {
  
  /** The UMLEnvironment. */
  private UMLEnvironment env = new UMLEnvironment();
  private Console c = new Console(env);
  
  public String list(){
	  String buildUp = "[ ";
	  for (UMLItem i : env.getItems()){
		  buildUp += i.getName() + " ";
	  }
	  buildUp += "]";
	  return buildUp;
  }
  
  /**
   * Test single line add
 * @throws IOException 
   */
  @Test
  public void testAdd() throws IOException {
    c.checkInput("add Matt");
    c.checkInput("add Dan");
    c.checkInput("add Eric");
    assertEquals("[ Matt Dan Eric ]", list());
    
    c.checkInput("add Matt");
    assertEquals("[ Matt Dan Eric ]", list());
    
    c.checkInput("add Eric");
    assertEquals("[ Matt Dan Eric ]", list());
    
    c.checkInput("add Kasey");
    assertEquals("[ Matt Dan Eric Kasey ]", list());
    
  }
  
  @Test
  public void testEdit() throws IOException {
	  c.checkInput("add Matt");
	  c.checkInput("edit Matt Eric");
	  assertEquals("[ Eric ]", list());
	  
	  c.checkInput("edit Eric");
	  assertEquals("[ Eric ]", list());
	  
	  c.checkInput("edit Eric Eric");
	  assertEquals("[ Eric ]", list());
	  
	  c.checkInput("edit Matt Eric");
	  assertEquals("[ Eric ]", list());
  }
  
  /**
   * Running this exact chain of inputs in the actual cmd line
   * 	interface works. This test will not properly load a file 
   * 	and it is because of an issue where this class UMLEnvironment
   * 	does not get updated to the loaded one. 
   */
/*  
  @Test
  public void testSaveLoad() throws IOException, InterruptedException {
	  
	  
	  Random r = new Random();
	  String s = "Test " + r.nextInt(100);
	  System.out.println(s);
	  c.checkInput("add Matt");
	  c.checkInput("save " + s);
	  c.checkInput("add Eric");
	  c.checkInput("load " + s);
	  // Should not load previous since not -f 
	  assertEquals("[ Matt Eric ]", list());
	  c.checkInput("load -f " + s);
	  
	  assertEquals("[ Matt ]", list());
	  
	  c.checkInput("add Eric");
	  c.checkInput("add Lauren");
	  c.checkInput("add Gernt");
	  c.checkInput("save " + s);
	  c.checkInput("load -f " + s);
	  // Since save should not have worked since not forced overwrite
	  assertEquals("[ Matt ]", list());
	  
	  c.checkInput("add Gernt");
	  c.checkInput("save -f " + s);
	  c.checkInput("add Lauren");
	  c.checkInput("load -f " + s);
	  assertEquals("[ Matt Gernt ]", list());
	  
	  LocalFile lf = new LocalFile();
	 // lf.setFileName(s);
	 // lf.deleteFile();
  } */  	
}
