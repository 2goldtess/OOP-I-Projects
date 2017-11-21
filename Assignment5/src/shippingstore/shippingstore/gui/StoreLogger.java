package shippingstore.shippingstore.gui;

import java.io.IOException;
import java.util.logging.*;

public class StoreLogger {
    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    private static final String dirname = System.getProperty("user.dir");

    static public void setup() throws IOException {

        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setUseParentHandlers(false);

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler(dirname+"/src/shippingstore/Logging.txt");

         // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);
    }
}