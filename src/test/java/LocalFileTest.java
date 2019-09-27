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
    LocalFile file = createSaveFile();
    file.saveFile();
    File testFile = new File(SAVE_DIR + "/" + file.getFileName() + ".yaml");
    assertTrue("File not found", testFile.exists());
  }
  
  /**
   * Test loadFile.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testLoadFile() throws IOException {
    LocalFile file = createSaveFile();
    file.saveFile();
    String fileName = "test_file";
    LocalFile fileTest = new LocalFile(fileName);
    env = fileTest.loadFile();
    
    assertTrue("Number of UMLItems not correct", env.getItems().size() == 2);
  }
  
  /**
   * Test deleteFile.
   * 
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testDeleteFile() throws IOException {
    LocalFile file = createSaveFile();
    file.saveFile();
    
    String fileName = "test_file";
    LocalFile fileTest = new LocalFile(fileName);
    
    fileTest.deleteFile();
    
    assertTrue("File was not deleted", !fileTest.hasExistingFileName(fileName));
  }
  
  /**
   * Test deleteAllFiles
   */
  @Test
  public void testDeleteAllFiles() throws IOException {
    LocalFile file = createSaveFile();
    file.saveFile();
    
    String fileName = "test_file";
    LocalFile fileTest = new LocalFile(fileName);
    
    fileTest.deleteFile();
    
    /** directory size should be 1 due to .gitignore in directory */
    assertTrue("saved_file directory still has contents.", fileTest.getDirectory().list().length == 1);
  }
 
  /**
   * Creates the file to be saved in LocalFile.
   */
  public LocalFile createSaveFile() {
    createTestUMLEnvironment();
    String fileName = "test_file";
    return new LocalFile(env, fileName);
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
