import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import org.junit.Test;

import data.UMLEnvironment;
import data.UMLItem;
import utility.LocalFile;

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
    
    assertTrue("Number of UMLItems not correct", env.getItems().size() == 1);
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
    
    fileTest.deleteAllFiles();
    
    /** directory size should be 1 due to .gitignore in directory */
    assertTrue("saved_file directory still has contents. Size is: " + fileTest.getDirectory().list().length, fileTest.getDirectory().list().length == 0);
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
    UMLItem item = new UMLItem();
    UMLItem itemTwo = new UMLItem();
    
    item.addAttribute("Dan");
    itemTwo.addAttribute("Matt");
    
    env.addItem(item);
    env.addItem(itemTwo);
  }

}
