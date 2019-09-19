import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The class LocalFile. Deals with everything related to the file that can be saved
 * and loaded for the user.
 * 
 * @author  Daniel Hartenstine
 * @version 1.0
 * @since   2019-09-09
 */
public class LocalFile {
  
  /** The Constant logger. */
  private static final Logger logger = Logger.getLogger(LocalFile.class.getName());
  
  /** The Constant SAVE_DIR. */
  private static final String SAVE_DIR = "saved_files";
  
  /** The UMLEnvironment. */
  UMLEnvironment env;
  
  /** The File directory. */
  File directory;
  
  /** The file name. */
  String fileName;
  
  /**
   * Default Constructor.
   */
  public LocalFile() {
    this.directory = new File(SAVE_DIR);
  }
  
  /**
   * Instantiates a new LocalFile.
   *
   * @param env The UMLEnvironment
   */
  public LocalFile(UMLEnvironment env) {
    this.env = env;
    this.directory = new File(SAVE_DIR);
  }
  
  /**
   * Instantiates a new LocalFile.
   *
   * @param fileName the file name
   */
  public LocalFile(String fileName) {
    this.fileName = fileName;
    this.directory = new File(SAVE_DIR);
  }
  
  /**
   * Instantiates a new LocalFile.
   *
   * @param env the UMLEnvironment
   * @param fileName the file name
   */
  public LocalFile(UMLEnvironment env, String fileName) {
    this.env = env;
    this.fileName = fileName;
    this.directory = new File(SAVE_DIR);
  }
  
  /**
   * Writes the Object(s) to the file.
   */
  public void saveFile() {
    JsonFactory jsonFactory = new JsonFactory(); 
    FileOutputStream file = null;
    try {
      file = new FileOutputStream(new File(SAVE_DIR + "/" + fileName + ".json"));
    } catch (FileNotFoundException e) {
      logger.log(Level.SEVERE, "Error. saved_files directory not found: ", e);
    }
    try(JsonGenerator jsonGen = jsonFactory.createGenerator(file, JsonEncoding.UTF8)) {
      jsonGen.setCodec(new ObjectMapper());
      jsonGen.writeObject(env);
    } catch(IOException e) {
      logger.log(Level.SEVERE, "Error. IOException in writeToFile(): ", e);
    }
    logger.info("File saved successfully");
  }
  
  /**
   * Loads the JSON file and transforms it into a UMLEnvironment object.
   *
   * @return the UMLEnvironment
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public UMLEnvironment loadFile() throws IOException {
    byte[] saveData = Files.readAllBytes(Paths.get(SAVE_DIR + "/" + fileName + ".json"));
    ObjectMapper objectMapper = new ObjectMapper();
    logger.info("Loading file in loadFile()");
    return objectMapper.readValue(saveData, UMLEnvironment.class);
  }
  
  /**
   * Deletes a file.
   */
  public void deleteFile() {
    File file = new File(SAVE_DIR + "/" + fileName + ".json");
    if(file.delete())
      logger.info("File: " + fileName + " deleted successfully.");
    else
      logger.warning("File: " + fileName + " not deleted successfully.");
  }
  
  /**
   * Deletes all files in the saved_files directory.
   * 
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void deleteAllFiles() throws IOException {
    FileUtils.cleanDirectory(directory);
  }
  

  /**
   * Searches through the saved_files folder path as specified by SAVE_DIR and
   * checks if the file name exists.
   *
   * @param fileName the file name
   * @return boolean The result of the search for the file name.
   */
  public boolean hasExistingFileName(String fileName) {
    File[] fileList = directory.listFiles();
    for(File f : fileList) {
      String fileNameNoExt = FilenameUtils.removeExtension(f.getName());
      if(fileNameNoExt.equals(fileName)) 
        return true;
    }
    return false;
  }
  
  /**
   * Gets the file name.
   *
   * @return the file name
   */
  public String getFileName() {
    return this.fileName;
  }
  
  /**
   * Gets the directory.
   * 
   * @return the directory
   */
  public File getDirectory() {
    return this.directory;
  }
  
}