package org.mmouse.utility;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.mmouse.data.UMLEnvironment;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * The class LocalFile. Deals with everything related to the file that can be saved
 * and loaded for the user.
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
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    try {
    objectMapper.writeValue(new File(SAVE_DIR + "/" + fileName + ".yaml"), env);
  } catch (IOException e) {
    logger.severe("LocalFile: IOException occured in saveFile: " + e.getCause());
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
  long startTime = System.nanoTime();
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    logger.info("Loading file in loadFile()");
    long endTime = System.nanoTime();
    /** Gives the duration to execute the load in ms */
    long duration = (endTime - startTime) / 1000000;
    logger.info("Load completed in: " + duration + " ms.");
    return objectMapper.readValue(new File(SAVE_DIR + "/" + fileName + ".yaml"), UMLEnvironment.class);
  }
  
  /**
   * Deletes a file.
   */
  public void deleteFile() {
    File file = new File(SAVE_DIR + "/" + fileName + ".yaml");
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

