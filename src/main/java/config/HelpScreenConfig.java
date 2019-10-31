package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * The Class HelpScreenConfig.
 */
public class HelpScreenConfig {

  /** The Constant logger. */
  private static final Logger logger = Logger.getLogger(HelpScreenConfig.class.getName());

  /** The Constant HELP_SCREEN_FILE. */
  private static final String HELP_SCREEN_FILE = "helpscreen.properties";

  /** The Constant HELP_SCREEN_DATA. */
  private static final String HELP_SCREEN_DATA = "help.message";

  /**
   * Gets the helpscreen properties file and loads it as a Properties object.
   *
   * @return props The helpscreen properties file
   */
  private static Properties getHelpFileConfig() {
    Properties props = new Properties();
    InputStream inputStream = HelpScreenConfig.class.getClassLoader().getResourceAsStream(HELP_SCREEN_FILE);
    try {
      props.load(inputStream);
    } catch (IOException e) {
      logger.severe("HelpScreenConfig unable to read helpscreen.properties file from classpath: " + e.getMessage());
    }
    return props;
  }

  /**
   * Prints the help screen.
   */
  public static void printHelpScreen() {
    String helpText = HelpScreenConfig.getHelpFileConfig().get(HelpScreenConfig.HELP_SCREEN_DATA).toString();
    System.out.println(helpText);
  }

}