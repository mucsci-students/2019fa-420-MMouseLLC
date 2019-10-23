package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class HelpScreenConfig {

  private static final Logger logger = Logger.getLogger(HelpScreenConfig.class.getName());

  public static final String HELP_SCREEN_FILE = "helpscreen.properties";

  public static final String HELP_SCREEN_DATA = "help.message";

  public static Properties getHelpFileConfig() {
    Properties props = new Properties();
    InputStream inputStream = HelpScreenConfig.class.getClassLoader().getResourceAsStream(HELP_SCREEN_FILE);
    try {
      props.load(inputStream);
    } catch (IOException e) {
      logger.severe("HelpScreenConfig unable to read helpscreen.properties file from classpath");
    }
    return props;
  }

  public static void printHelpScreen() {
    String helpText = HelpScreenConfig.getHelpFileConfig().get(HelpScreenConfig.HELP_SCREEN_DATA).toString();
    System.out.println(helpText);
  }

}