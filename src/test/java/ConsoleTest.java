import org.junit.Before;
import org.junit.Test;

import utility.Console;

/**
 * The Class ConsoleTest.
 */
public class ConsoleTest {

  /** The console. */
  private Console console;

  /**
   * Sets up.
   */
  @Before
  public void setUp() {
    console = new Console();
  }
  
  
  /**
   * Test console.
   */
  @Test
  public void testConsole() {
    String[] help = { "help" };
    console.checkInput(help);
    
    String[] add = { "add" , "dan" };
    console.checkInput(add);
    
    String[] find = { "find" , "dan" };
    console.checkInput(find);
    
    String[] addMore = { "add" , "grant" };
    console.checkInput(addMore);
    String[] addRelationship = { "add_relationship" , "grant", "dan" };
    console.checkInput(addRelationship);
    
//    String[] edit = { "edit" , "grant" , "matt" };
//    console.checkInput(edit);
//    

    console.listRelationships();
    
    String[] list = { "list" };
    console.checkInput(list);
    
    
    
    
    String[] listCategories = { "list_categories" };
    String[] listRelationships = { "list_relationships" };
    String[] listFields = { "list_fields" };
    String[] listFunctions = { "list_functions" };
    console.checkInput(listCategories);
    console.checkInput(listRelationships);
    console.checkInput(listFields);
    console.checkInput(listFunctions);




    String[] delete = { "delete", "matt", "-f" };
    console.checkInput(delete);
  }

}
