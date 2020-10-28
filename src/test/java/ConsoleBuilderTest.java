import static org.junit.Assert.assertTrue;

import org.jline.reader.LineReader;
import org.junit.Test;

import utility.ConsoleBuilder;

/**
 * The Class ConsoleBuilderTest.
 */
public class ConsoleBuilderTest {

  /**
   * Test console builder.
   */
  @Test
  public void testConsoleBuilder() {
    ConsoleBuilder builder = new ConsoleBuilder();
    LineReader reader = builder.buildConsole();
    
    assertTrue("Reader is null", reader != null);
  }

}
