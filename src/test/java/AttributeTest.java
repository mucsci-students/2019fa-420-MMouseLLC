import static org.junit.Assert.assertEquals;

import java.io.IOException;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import data.UMLEnvironment;
import data.UMLItem;
import utility.Console;

/**
 * The Class SingleLineCommandTest
 */
public class AttributeTest {

	/** The UMLEnvironment. */
	private UMLEnvironment env = new UMLEnvironment();
	private Console c = new Console();

	public String list() {
		String buildUp = "[ ";
		for (UMLItem i : env.getItems()) {
			buildUp += i.getName() + " ";
		}
		buildUp += "]";
		return buildUp;
	}

	public String printContainer(ArrayList<UMLItem> arr) {
		String build = "[ ";
		for (UMLItem i : arr) { // for each loop
			build += i.getName() + " ";
		}
		build += "]";
		return build;
	}

	public UMLItem getItem(String name) {
		for (UMLItem i : env.getItems()) {
			if (i.getName().equals(name)) {
				return i;
			}
		}
		return null;
	}

	/**
	 * gives a list of the attributes currently in the environment
	 *
	 */
	public String listAttributes(UMLItem item) {
		StringBuilder builder = new StringBuilder();
		// builder.append("List of attributes in " + item.getName());
		for (UMLItem i : env.getItems()) {
			if (i.getName().equals(item.getName())) {
				builder.append("{");
				for (String s : i.getAttributes()) {
					builder.append("[" + s + "]");
					if (i.getAttributes().indexOf(s) != i.getAttributes().size() - 1)
						builder.append(" ");
				}
				builder.append("}");
				break;
			}
		}
		return builder.toString();
		// To get previous print statement
		// logger.info("List of attributes in " + item.getName() + builder.toString());
		// logger.info(builder.toString());
	}

	/**
	 * Test single line add
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAddAttribute() throws IOException {
		String[] matt = { "add", "Matt" };
		c.checkInput(matt);
		assertEquals("[ Matt ]", list());
		String[] input = { "add_attribute", "Matt", "test" };
		c.checkInput(input);
		assertEquals(env.findItem("Matt").getAttributes().get(0), "test");

		String[] Kasey = { "add", "Kasey" };
		c.checkInput(Kasey);
		assertEquals("[ Matt Kasey ]", list());
		String[] input2 = { "add_attribute", "Kasey", "test" };
		c.checkInput(input2);
		assertEquals(env.findItem("Kasey").getAttributes().get(0), "test");

		input2[2] = "test2";
		c.checkInput(input2);
		assertEquals(env.findItem("Kasey").getAttributes().get(1), "test2");

	}
	/**
	 * Test to see if attributes can be edited
	 */
	@Test
	public void editAttributeTest() {
		String[] input = { "add", "Kasey" };
		c.checkInput(input);

		input = new String[3];
		input[0] = "add_attribute";
		input[1] = "Kasey";
		input[2] = "test";
		c.checkInput(input);

		input[2] = "test2";
		c.checkInput(input);

		input = new String[4];
		input[0] = "edit_attribute";
		input[1] = "Kasey";
		input[2] = "test2";
		input[3] = "newtest";

		c.checkInput(input);
		assertEquals(env.findItem("Kasey").getAttributes().get(1), "newtest");
		
		input[2] = "test";
		input[3] = "test3";
		c.checkInput(input);
		assertEquals(env.findItem("Kasey").getAttributes().get(1), "test3");
	}
	/**
	 * Test to see if attributes can be deleted
	 */
	@Test
	public void removeAttributeTest() {
		String[] input = { "add", "Kasey" };
		c.checkInput(input);

		input = new String[3];
		input[0] = "add_attribute";
		input[1] = "Kasey";
		input[2] = "test";
		c.checkInput(input);
		
		assertEquals(env.findItem("Kasey").getAttributes().size(), 1);
		
		input[0] = "delete_attribute";
		c.checkInput(input);
		
		assertEquals(env.findItem("Kasey").getAttributes().size(), 0);
		
	
		input[0] = "add_attribute";
		input[1] = "Kasey";
		input[2] = "test2";
		c.checkInput(input);
		
		assertEquals(env.findItem("Kasey").getAttributes().size(), 1);
		
		input[2] = "test3";
		c.checkInput(input);
		assertEquals(env.findItem("Kasey").getAttributes().size(), 2);
		
		input[0] = "delete_attribute";
		c.checkInput(input);
		
		assertEquals(env.findItem("Kasey").getAttributes().size(), 1);
	}

}
