package utility;

import java.io.IOException;
import java.util.logging.Logger;

import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.EnumCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import config.ConsoleCommands;

public class ConsoleBuilder {
  
  Logger logger = Logger.getLogger(ConsoleBuilder.class.getName());

  public ConsoleBuilder() {
    super();
  }
  
  public LineReader buildConsole() {
    TerminalBuilder builder = TerminalBuilder.builder();
    Completer completer = new EnumCompleter(ConsoleCommands.class);
    Terminal terminal = null;

    try {
      terminal = builder.build();
    } catch (IOException e) {
      logger.severe("Error. IOException occurred in run() in Console");
    }

    LineReader reader = LineReaderBuilder.builder()
        .terminal(terminal)
        .completer(completer)
        .build();

    reader.option(LineReader.Option.COMPLETE_IN_WORD, true);
    reader.option(LineReader.Option.RECOGNIZE_EXACT, true);
    reader.option(LineReader.Option.CASE_INSENSITIVE, true);
    
    return reader;
  }
  
}
