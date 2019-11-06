import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import data.*;

import org.junit.Test;

public class RelationshipTest {
	
	UMLEnvironment env = new UMLEnvironment();
	
	@Test
	public void testRelationship() {
		env.addItem(new UMLItem("Matt"));
		env.addItem(new UMLItem("Eric"));
		
		Relationship r = new Relationship(env.findItem("Matt"), env.findItem("Eric"));
		
		env.addRelationship(r);
		
		assertTrue(env.findRelationship(r) != null);
		
		env.addRelationship(r);
		
		assertEquals(1, env.getRelationships().size());
		
		env.addItem(new UMLItem("Lauren"));
		
		r = new Relationship(env.findItem("Eric"), env.findItem("Lauren"));
		env.addRelationship(r);
		
		r = new Relationship(env.findItem("Eric"), null);
		assertTrue(env.findRelationship(r) == null);
		
		r = new Relationship(env.findItem("Eric"), env.findItem("Lauren"));
		
		
		r = new Relationship(env.findItem("Eric"), null);
		assertTrue( !env.removeRelationship(r) );
		r = new Relationship(env.findItem("Eric"), env.findItem("Lauren"));
		
		assertTrue( env.removeRelationship(r) );
		
		
		
	}
}
