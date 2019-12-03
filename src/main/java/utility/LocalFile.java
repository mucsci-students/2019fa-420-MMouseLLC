package utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import data.GUIEnvironment;
import data.UMLEnvironment;

/**
 * The class LocalFile. Deals with everything related to the file that can be saved
 * and loaded for the user.
 */
public class LocalFile {
  
  /** The Constant logger. */
  private static final Logger logger = Logger.getLogger(LocalFile.class.getName());
  
  /** The Constant SAVE_DIR. */
  private static final String SAVE_DIR = "saved_files";
  
  /** The Constant PLACEHOLDER */
  private static final String PLACEHOLDER = "placeholder.txt";
  
  /** The UMLEnvironment. */
  UMLEnvironment env;
  
  /** The File directory. */
  File directory;
  
  /** The file name. */
  String fileName;
  
  GUIEnvironment guiEnv;
  
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
  
  public LocalFile(GUIEnvironment guiEnv) {
	  this.guiEnv = guiEnv;
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
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    try {
    objectMapper.writeValue(new File(SAVE_DIR + "/" + fileName + ".yaml"), env);
  } catch (IOException e) {
    logger.severe("LocalFile: IOException occured in saveFile: " + e.getCause());
  }
    System.out.println("File saved successfully");
  }
  
  /**
   * Loads the JSON file and transforms it into a UMLEnvironment object.
   *
   * @return the UMLEnvironment
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public UMLEnvironment loadFile() {
    long startTime = System.nanoTime();
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    System.out.println("Loading file in loadFile()");
    UMLEnvironment loadedEnv = null;
    try {
      loadedEnv = objectMapper.readValue(new File(SAVE_DIR + "/" + fileName + ".yaml"), UMLEnvironment.class);      
    } catch(IOException e) {
      logger.severe("IOException occurred in loadFile.");
    }
    long endTime = System.nanoTime();
    /** Gives the duration to execute the load in ms */
    long duration = (endTime - startTime) / 1_000_000;
    System.out.println("Load completed in: " + duration + " ms.");
    return loadedEnv;
  }
  
  /**
   * Deletes a file.
   */
  public void deleteFile() {
    File file = new File(SAVE_DIR + "/" + fileName + ".yaml");
    if(file.delete())
      System.out.println("File: " + fileName + " deleted successfully.");
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
    createPlaceholderFile();
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
   * Creates a placeholder .txt file in saved_files directory after deleting
   * all directory contents. This is created in order to keep the directory
   * in the Github repository.
   */
  public void createPlaceholderFile() {
    try(Writer writer = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream(SAVE_DIR + "/" + PLACEHOLDER), "utf-8"))) {
      writer.write("Placeholder to keep directory active for Github.");
    } catch(IOException e) { 
      logger.severe("Error. IOException occured in createPlaceholderFile()");
    }
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
   * sSets the file name
   * 
   * @param fileName
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
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
