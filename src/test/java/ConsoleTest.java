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
    String[] add = { "add" , "dan" };
    String[] delete = { "delete", "dan", "-f" };
    String[] list = { "list" };
    String[] listCategories = { "list_categories" };
    String[] listRelationships = { "list_relationships" };
    String[] listFields = { "list_fields" };
    String[] listFunctions = { "list_functions" };
    console.checkInput(help);
    console.checkInput(add);
    console.checkInput(list);
    console.checkInput(listCategories);
    console.checkInput(listRelationships);
    console.checkInput(listFields);
    console.checkInput(listFunctions);
    console.checkInput(delete);
  }

}
