package global;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.*;

public class Configuration {
  private static Configuration instance = null;
  Properties prop;

  public static InputStream charge(String name) {
    return ClassLoader.getSystemClassLoader().getResourceAsStream(name);
  }

  private Configuration() {
    prop = new Properties();
    try {
      InputStream propIn = charge("default.cfg");
      prop.load(propIn);
      System.out.println("Default in assets " + prop);

      if (prop == null) {
        String home = System.getProperty("user.home");
        FileInputStream f = new FileInputStream(home + "/.carcassonne");
        prop = new Properties(prop);
        prop.load(f);
        f.close();
      }
    } catch (Exception e) {
      System.err.println("Impossible de charger la configuration !");
    }
    System.out.println(prop);
  }

  public static Configuration instance() {
    if (instance == null)
      instance = new Configuration();
    return instance;
  }

  public String lis(String key) {
    String res = prop.getProperty(key);
    if (res == null)
      throw new NoSuchElementException("Propriété " + key + " non définie");
    return res;
  }

  String getFileLoggerName() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    Date date = new Date();
    return formatter.format(date) + ".log";
  }

  public Logger logger() {
    Logger log = Logger.getLogger("Carcassonne.Logger");
    log.setLevel(Level.parse(lis("LogLevel")));

    if (log.getHandlers().length == 0) {
      try {
        FileHandler fh = new FileHandler("logs/" + getFileLoggerName());
        FileHandler fhLatest = new FileHandler("logs/latest.log");
        log.addHandler(fh);
        log.addHandler(fhLatest);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        fhLatest.setFormatter(formatter);
      }
      catch (SecurityException e) {
        e.printStackTrace();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    return log;
  }
}
