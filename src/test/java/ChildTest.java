import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

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
	
	public UMLItem getItem(String name) {
		for (UMLItem i : env.getItems()) {
			if (i.getName().equals(name)){
				return i;
			}
		} 
		return null;
	}
	
	@Test
	public void testAddChild() throws IOException {
		// to add a command like any regular command, use the c.checkInput()
		// printContainer takes an ArrayList<UMLItem> and will print it
		//    this is useful for getItem("className").getChildren() or .getParent()
		c.checkInput("add matt");
		c.checkInput("add lauren");
		c.checkInput("add eric");
		
		c.checkInput("addchild lauren");
		assertEquals("[ ]", printContainer(getItem("matt").getChildren()));
		
		c.checkInput("addchild grant matt");
		assertEquals("[ ]", printContainer(getItem("matt").getChildren()));
		
		c.checkInput("addchild matt matt");
		assertEquals("[ ]", printContainer(getItem("matt").getChildren()));
		
		c.checkInput("addchild lauren matt");
		assertEquals("[ lauren ]", printContainer(getItem("matt").getChildren()));
		
		c.checkInput("addchild eric matt");
		assertEquals("[ lauren eric ]", printContainer(getItem("matt").getChildren()));
	
		
		
	}
	
	@Test
	public void testRemoveChild() throws IOException {
		c.checkInput("add matt");
		c.checkInput("add lauren");
		c.checkInput("add kasey");
		c.checkInput("add dan");
		c.checkInput("addchild lauren matt");
		c.checkInput("addchild kasey matt");
		
		c.checkInput("removechild dan matt");
		assertEquals("[ lauren kasey ]", printContainer(getItem("matt").getChildren()));
		
		c.checkInput("removecild grant matt");
		assertEquals("[ lauren kasey ]", printContainer(getItem("matt").getChildren()));
		
		c.checkInput("removechild lauren");
		assertEquals("[ lauren kasey ]", printContainer(getItem("matt").getChildren()));
		
		c.checkInput("removechild lauren matt");
		assertEquals("[ kasey ]", printContainer(getItem("matt").getChildren()));
		
		c.checkInput("removechild kasey matt");
		assertEquals("[ ]", printContainer(getItem("matt").getChildren()));
	}
	
	@Test
	public void testListChildren() throws IOException {
		c.checkInput("add matt");
		c.checkInput("add lauren");
		c.checkInput("addchild lauren matt");
		
		c.checkInput("listchildren matt");
		assertEquals("[ lauren ]", printContainer(getItem("matt").getChildren()));
		
		c.checkInput("listchildren lauren");
		assertEquals("[ ]", printContainer(getItem("lauren").getChildren()));
		
		c.checkInput("listchildren grant");
		assertEquals(null, getItem("grant"));
	}
	
	@Test
	public void testListParents() throws IOException {
		c.checkInput("add matt");
		c.checkInput("add lauren");
		c.checkInput("addchild lauren matt");
		
		c.checkInput("listparents lauren");
		assertEquals("[ matt ]", printContainer(getItem("lauren").getParents()));
		
		c.checkInput("listparents matt");
		assertEquals("[ ]", printContainer(getItem("matt").getParents()));
		
		c.checkInput("listparents grant");
		assertEquals(null, getItem("grant"));
	}

}
