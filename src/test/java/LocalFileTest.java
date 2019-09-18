import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

/**
 * The Class LocalFileTest.
 */
public class LocalFileTest {
  
  /** The Constant SAVE_DIR. */
  private static final String SAVE_DIR = "saved_files";
  
  /** The UMLEnvironment. */
  private UMLEnvironment env = new UMLEnvironment();

  /**
   * Test saveFile.
   */
  @Test
  public void testSaveFile() {
    createTestUMLEnvironment();
    String fileName = "test_file";
    LocalFile file = new LocalFile(env, fileName);
    file.saveFile();
    
    File testFile = new File(SAVE_DIR + "/" + file.getFileName() + ".json");
    assertTrue("File not found", testFile.exists());
  }
  
  /**
   * Test loadFile.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testLoadFile() throws IOException {
    String fileName = "test_file";
    LocalFile file = new LocalFile(fileName);
    env = file.loadFile();
    
    assertTrue("Number of UMLItems not correct", env.getItems().size() == 2);
  }
  
  /**
   * Creates the test data for the UMLEnvironment
   */
  public void createTestUMLEnvironment() {
    ArrayList<String> arr = new ArrayList<>();
    arr.add("Eric");
    arr.add("Matt");
    arr.add("Lauren");
    arr.add("Grant");
    
    UMLItem item = new UMLItem(1, "Dan", 2, arr);
    UMLItem itemTwo = new UMLItem(2, "Captain Planet", 3, arr);
    env.addItem(item);
    env.addItem(itemTwo);
  }

}
