package shippingstore.shippingstore.gui;

import java.io.IOException;
import java.util.logging.*;

public class StoreLogger {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static private FileHandler fileHTML;
    static private Formatter formatterHTML;
    private static final String dirname = System.getProperty("user.dir");

    static public void setup() throws IOException {

        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        // suppress the logging output to the console
       // Logger rootLogger = Logger.*getLogger*("");
       // Handler[] handlers = rootLogger.getHandlers();
      //  if (handlers[0] instanceof ConsoleHandler) {
      //      rootLogger.removeHandler(handlers[0]);
      //  }

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler(dirname+"/src/shippingstore/Logging.txt");

         // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

    }
}