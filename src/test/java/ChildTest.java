import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

import data.UMLEnvironment;
import data.UMLItem;
import utility.Console;

public class ChildTest {
	private UMLEnvironment env = new UMLEnvironment();
	private Console c = new Console(env);

	public String printContainer(ArrayList<UMLItem> arr) {
		String build = "[ ";
		for (UMLItem i : arr) { // for each loop
			build += i.getName() + " ";
		}
		build += "]";
		return build;
	}

	/**
	 * Get UMLItem based on given name
	 */
	public UMLItem getItem(String name) {
		for (UMLItem i : env.getItems()) {
			if (i.getName().equals(name)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Test add child functionality
	 */
	@Test
	public void testAddChild() {
		String[] input = { "add", "Matt" };
		c.checkInput(input);
		c.list();
		input[1] = "Kasey";
		c.checkInput(input);
		c.list();
		input[1] = "Grant";
		c.checkInput(input);
		c.list();
		UMLItem child = env.findItem("Matt");
		UMLItem parent = env.findItem("Kasey");

		assertEquals(child.getName(), "Matt");
		assertEquals(parent.getName(), "Kasey");

		input = new String[3];
		input[0] = "addchild";
		input[1] = "Matt";
		input[2] = "Kasey";
		c.checkInput(input);

		assertEquals(env.findItem("Kasey").getChildren().get(0).getName(), "Matt");
		assertEquals(env.findItem("Matt").getParents().get(0).getName(), "Kasey");

		input[1] = "Grant";
		c.checkInput(input);
		assertEquals(env.findItem("Kasey").getChildren().get(0).getName(), "Matt");
		assertEquals(env.findItem("Kasey").getChildren().get(1).getName(), "Grant");

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
		input[0] = "addchild";
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
		String[] input = { "addchild", "NotPar", "NotChi" };
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
		c.list();
		input[1] = "Dan";
		c.checkInput(input);
		c.list();
		input[1] = "Lauren";
		c.checkInput(input);
		c.list();
		UMLItem child = env.findItem("Eric");
		UMLItem parent = env.findItem("Dan");

		assertEquals(child.getName(), "Eric");
		assertEquals(parent.getName(), "Dan");

		input = new String[3];
		input[0] = "addchild";
		input[1] = "Eric";
		input[2] = "Dan";
		c.checkInput(input);

		assertEquals(env.findItem("Dan").getChildren().get(0).getName(), "Eric");
		assertEquals(env.findItem("Eric").getParents().get(0).getName(), "Dan");

		input[0] = "removechild";
		input[1] = "Lauren";
		c.checkInput(input);

		assertEquals(env.findItem("Dan").getChildren().size(), 1);

		input[0] = "addchild";
		c.checkInput(input);

		assertEquals(env.findItem("Dan").getChildren().size(), 2);
		assertEquals(env.findItem("Dan").getChildren().get(1).getName(), "Lauren");

		input[0] = "removechild";
		input[1] = "Lauren";
		c.checkInput(input);

		assertEquals(env.findItem("Dan").getChildren().size(), 1);

		input[1] = "Eric";
		c.checkInput(input);

		assertEquals(env.findItem("Dan").getChildren().size(), 0);
		assertEquals(env.findItem("Eric").getParents().size(), 0);
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
		input[0] = "removechild";
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
		String[] input = { "removechild", "NotPar", "NotChi" };
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
		c.list();
		input[1] = "Oreo";
		c.checkInput(input);
		c.list();
		input[1] = "Goober";
		c.checkInput(input);
		c.list();

		input = new String[3];
		input[0] = "addchild";
		input[1] = "Oreo";
		input[2] = "Maggie";
		c.checkInput(input);

		assertEquals(env.findItem("Maggie").getChildren().get(0).getName(), "Oreo");
		assertEquals(env.findItem("Oreo").getParents().get(0).getName(), "Maggie");

		input = new String[2];
		input[0] = "listChidlren";
		input[1] = "Maggie";
		c.checkInput(input);

		assertEquals("[ Oreo ]", printContainer(getItem("Maggie").getChildren()));
		
		input = new String[3];
		input[0] = "addchild";
		input[1] = "Goober";
		input[2] = "Maggie";
		c.checkInput(input);

		assertEquals(env.findItem("Maggie").getChildren().get(1).getName(), "Goober");
		assertEquals(env.findItem("Goober").getParents().get(0).getName(), "Maggie");

		input = new String[2];
		input[0] = "listChidlren";
		input[1] = "Maggie";
		c.checkInput(input);

		assertEquals("[ Oreo Goober ]", printContainer(getItem("Maggie").getChildren()));

		input[1] = "Oreo";
		c.checkInput(input);
		
		assertEquals("[ ]", printContainer(getItem("Oreo").getChildren()));
		
		input = new String[1];
		input[0] = "listchildren";
		
		assertTrue(true);
	}
	
	/**
	 * Testing list children functionality when no classes exist
	 */
	@Test
	public void testListChildrenNoClasses() {
		String[] input = { "listchildren", "NoClass" };
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
		c.list();
		input[1] = "Patches";
		c.checkInput(input);
		c.list();
		input[1] = "Sauron";
		c.checkInput(input);
		c.list();

		input = new String[3];
		input[0] = "addchild";
		input[1] = "Patches";
		input[2] = "Biscotti";
		c.checkInput(input);

		assertEquals(env.findItem("Biscotti").getChildren().get(0).getName(), "Patches");
		assertEquals(env.findItem("Patches").getParents().get(0).getName(), "Biscotti");

		input = new String[2];
		input[0] = "listParents";
		input[1] = "Patches";
		c.checkInput(input);

		assertEquals("[ Biscotti ]", printContainer(getItem("Patches").getParents()));
		
		input = new String[3];
		input[0] = "addchild";
		input[1] = "Patches";
		input[2] = "Sauron";
		c.checkInput(input);
		
		input = new String[2];
		input[0] = "listParents";
		input[1] = "Patches";
		c.checkInput(input);

		assertEquals("[ Biscotti Sauron ]", printContainer(getItem("Patches").getParents()));
		
		input = new String[3];
		input[0] = "addchild";
		input[1] = "Sauron";
		input[2] = "Biscotti";
		c.checkInput(input);

		assertEquals(env.findItem("Biscotti").getChildren().get(1).getName(), "Sauron");
		assertEquals(env.findItem("Sauron").getParents().get(0).getName(), "Biscotti");

		input = new String[2];
		input[0] = "listParents";
		input[1] = "Sauron";
		c.checkInput(input);

		assertEquals("[ Biscotti ]", printContainer(getItem("Sauron").getParents()));

		input[1] = "Biscotti";
		c.checkInput(input);
		
		assertEquals("[ ]", printContainer(getItem("Biscotti").getParents()));
		
		input = new String[1];
		input[0] = "listparents";
		
		assertTrue(true);
	}
	
	/**
	 * Testing list parents functionality when no classes exist
	 */
	@Test
	public void testListParentsNoClasses() {
		String[] input = { "listparents", "NoClass" };
		c.checkInput(input);
		String[] otherInput = { "add", "NoClass" };
		c.checkInput(otherInput);
		c.checkInput(input);
		assertTrue(true);
	}

}
