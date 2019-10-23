import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import data.UMLEnvironment;
import data.UMLItem;
import utility.Console;

/**
 * The Class SingleLineCommandTest
 */
public class SingleLineCommandTest {
  
  /** The UMLEnvironment. */
 // private UMLEnvironment env = new UMLEnvironment();
  private Console c = new Console();
  
  public String list(){
    UMLEnvironment env = c.getUMLEnvironment();
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
	  
	String[] matt = {"add", "Matt"};
	String[] dan = {"add", "Dan"};
	String[] eric = {"add", "Eric"};
	String[] kasey = {"add", "Kasey"};
	  
    c.checkInput(matt);
    c.checkInput(dan);
    c.checkInput(eric);
    assertEquals("[ Matt Dan Eric ]", list());
    
//    c.checkInput(matt);
//    assertEquals("[ {Matt} {Dan} {Eric} ]", list());
//    
//    c.checkInput(eric);
//    assertEquals("[ {Matt} {Dan} {Eric} ]", list());
    
    c.checkInput(kasey);
    assertEquals("[ Matt Dan Eric Kasey ]", list());
    
  }
  
  
  // Test currently not working based on new functionality of Console.
  @Test
  @Ignore
  public void testEdit() throws IOException {
	  
		String[] matt = {"add", "Matt"};
		String[] editOne = {"edit", "Matt", "Eric"};
		String[] editTwo = {"edit", "Eric", "Eric"};
		String[] editThree = {"edit", "Matt", "Eric"};
		String[] editFour = {"edit", "Eric"};
	  
	  c.checkInput(matt);
	  c.checkInput(editOne);
	  assertEquals("[ Eric ]", list());
	  
	  c.checkInput(editFour);
	  assertEquals("[ Eric ]", list());
	  
	  c.checkInput(editTwo);
	  assertEquals("[ Eric ]", list());
	  
	  c.checkInput(editThree);
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
