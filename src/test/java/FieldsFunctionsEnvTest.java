import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import data.UMLItem;

public class FieldsFunctionsEnvTest {

	public String printMap(HashMap<String, String> m) {
		StringBuilder s = new StringBuilder();
		s.append("[ ");
		
		for (Map.Entry<String, String> i : m.entrySet()) {
			s.append("{ " + i.getKey() + ": " +  i.getValue() + " } " );
		}
		s.append("]");
		return s.toString();
	}
	
	@Test
	public void testFields() {
		UMLItem i = new UMLItem();
		i.setName("Kasey");
		i.addField("int", "steps");
		i.addField("String", "Letters");
		
		assertEquals(printMap(i.getFields()), "[ { Letters: String } { steps: int } ]");
		
		i.removeField("Letters");
		assertEquals(i.getFields().size(), 1);
		
		i.editField("steps", "float");
		
		assertEquals(printMap(i.getFields()) , "[ { steps: float } ]");
		
		i.editField("steps", "whatevs", "float");
		
		assertEquals(printMap(i.getFields()) , "[ { whatevs: float } ]");
		
		i.editField("whatevs", "whatevs2", "bool");
		
		assertEquals(printMap(i.getFields()) , "[ { whatevs2: bool } ]");
	}
	
	@Test
	public void testFunctions() {
		UMLItem i = new UMLItem();
		i.setName("Kasey");
		i.addFunction("int", "steps");
		i.addFunction("String", "Letters");
		
		assertEquals(printMap(i.getFunctions()), "[ { Letters: String } { steps: int } ]");
		
		i.removeFunction("Letters");
		assertEquals(i.getFunctions().size(), 1);
		
		i.editFunction("steps", "float");
		
		assertEquals(printMap(i.getFunctions()) , "[ { steps: float } ]");
		
		i.editFunction("steps", "whatevs", "float");
		
		assertEquals(printMap(i.getFunctions()) , "[ { whatevs: float } ]");
		
		i.editFunction("whatevs", "whatevs2", "bool");
		
		assertEquals(printMap(i.getFunctions()) , "[ { whatevs2: bool } ]");
	}
}
