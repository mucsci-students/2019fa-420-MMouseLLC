
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import data.UMLEnvironment;
import data.UMLItem;
import utility.Console;

@SuppressWarnings("javadoc")
@Deprecated
public class ChildTest {
  private UMLEnvironment env;
  private Console c = new Console();

  @Before
  public void setEnv() {
    env = c.getUMLEnvironment();
  }

  /**
   * Test add child functionality
   */
  @Test
  public void testAddChild() {
    String[] input = { "add", "Matt" };
    c.checkInput(input);
    c.list(input);
    input[1] = "Kasey";
    c.checkInput(input);
    c.list(input);
    input[1] = "Grant";
    c.checkInput(input);
    c.list(input);
    UMLItem child = env.findItem("Matt");
    UMLItem parent = env.findItem("Kasey");

    assertEquals(child.getName(), "Matt");
    assertEquals(parent.getName(), "Kasey");

    input = new String[3];
    input[0] = "add_child";
    input[1] = "Matt";
    input[2] = "Kasey";
    c.checkInput(input);
    input[1] = "Grant";
    c.checkInput(input);
    input[1] = "Kasey";
    c.checkInput(input);
    assertTrue(true);

  }

  /**
   * Test add functionality when no relationship exists
   */
  @Test
  public void testAddNonExistant() {
    String[] input = { "ADD", "DummyP" };
    c.checkInput(input);
    input[1] = "DummyC";
    c.checkInput(input);

    input = new String[3];
    input[0] = "add_child";
    input[1] = "DummyP";
    input[2] = "DummyC";
    c.checkInput(input);
    // Just don't want null ptr exception
    assertTrue(true);
  }

  /**
   * Test add functionality when no classes exists
   */
  @Test
  public void testAddNoClasses() {
    String[] input = { "add_child", "NotPar", "NotChi" };
    c.checkInput(input);
    String[] otherInput = { "add", "NoPar" };
    c.checkInput(otherInput);
    c.checkInput(input);
    // Don't want a null ptr exception
    assertTrue(true);
  }

  /**
   * Test remove child functionality
   */
  @Test
  public void testRemoveChild() {
    String[] input = { "add", "Eric" };
    c.checkInput(input);
    c.list(input);
    input[1] = "Dan";
    c.checkInput(input);
    c.list(input);
    input[1] = "Lauren";
    c.checkInput(input);
    c.list(input);
    UMLItem child = env.findItem("Eric");
    UMLItem parent = env.findItem("Dan");

    assertEquals(child.getName(), "Eric");
    assertEquals(parent.getName(), "Dan");

    input = new String[3];
    input[0] = "add_child";
    input[1] = "Eric";
    input[2] = "Dan";
    c.checkInput(input);
    input[0] = "remove_child";
    input[1] = "Lauren";
    c.checkInput(input);
    input[0] = "add_child";
    c.checkInput(input);
    input[0] = "remove_child";
    input[1] = "Lauren";
    c.checkInput(input);
    input[1] = "Eric";
    c.checkInput(input);
  }

  /**
   * Test remove functionality when no relationship exists
   */
  @Test
  public void testRemoveNonExistant() {
    String[] input = { "ADD", "DummyP" };
    c.checkInput(input);
    input[1] = "DummyC";
    c.checkInput(input);

    input = new String[3];
    input[0] = "remove_child";
    input[1] = "DummyP";
    input[2] = "DummyC";
    c.checkInput(input);

    assertTrue(true);
  }

  /**
   * Test remove functionality when no classes exists
   */
  @Test
  public void testRemoveNoClasses() {
    String[] input = { "remove_child", "NotPar", "NotChi" };
    c.checkInput(input);
    String[] otherInput = { "add", "NoPar" };
    c.checkInput(otherInput);
    c.checkInput(input);
    // Don't want a null ptr exception
    assertTrue(true);
  }

  /**
   * Test functionality of listing children classes given a parent class
   */
  @Test
  public void testListChildren() {
    String[] input = { "add", "Maggie" };
    c.checkInput(input);
    c.list(input);
    input[1] = "Oreo";
    c.checkInput(input);
    c.list(input);
    input[1] = "Goober";
    c.checkInput(input);
    c.list(input);

    input = new String[3];
    input[0] = "add_child";
    input[1] = "Oreo";
    input[2] = "Maggie";
    c.checkInput(input);
    input = new String[2];
    input[0] = "list_chidlren";
    input[1] = "Maggie";
    c.checkInput(input);
    input = new String[3];
    input[0] = "add_child";
    input[1] = "Goober";
    input[2] = "Maggie";
    c.checkInput(input);
    input = new String[2];
    input[0] = "list_chidlren";
    input[1] = "Maggie";
    c.checkInput(input);
    input[1] = "Oreo";
    c.checkInput(input);
    input = new String[1];
    input[0] = "list_children";

    assertTrue(true);
  }

  /**
   * Testing list children functionality when no classes exist
   */
  @Test
  public void testListChildrenNoClasses() {
    String[] input = { "list_children", "NoClass" };
    c.checkInput(input);
    String[] otherInput = { "add", "NoClass" };
    c.checkInput(otherInput);
    c.checkInput(input);
    assertTrue(true);
  }

  /**
   * Test functionality of listing parent classes given a child class
   */
  @Test
  public void testListParents() {
    String[] input = { "add", "Biscotti" };
    c.checkInput(input);
    
    input[1] = "Patches";
    c.checkInput(input);
    
    input[1] = "Sauron";
    c.checkInput(input);
    

    input = new String[3];
    input[0] = "add_child";
    input[1] = "Patches";
    input[2] = "Biscotti";
    c.checkInput(input);
    input = new String[2];
    input[0] = "list_parents";
    input[1] = "Patches";
    c.checkInput(input);
    input = new String[3];
    input[0] = "add_child";
    input[1] = "Patches";
    input[2] = "Sauron";
    c.checkInput(input);
    input = new String[2];
    input[0] = "list_parents";
    input[1] = "Patches";
    c.checkInput(input);
    input = new String[3];
    input[0] = "add_child";
    input[1] = "Sauron";
    input[2] = "Biscotti";
    c.checkInput(input);
    input = new String[2];
    input[0] = "list_parents";
    input[1] = "Sauron";
    c.checkInput(input);
    input[1] = "Biscotti";
    c.checkInput(input);
    input = new String[1];
    input[0] = "list_parents";

    assertTrue(true);
  }

  /**
   * Testing list parents functionality when no classes exist
   */
  @Test
  public void testListParentsNoClasses() {
    String[] input = { "list_parents", "NoClass" };
    c.checkInput(input);
    String[] otherInput = { "add", "NoClass" };
    c.checkInput(otherInput);
    c.checkInput(input);
    assertTrue(true);
  }

}
