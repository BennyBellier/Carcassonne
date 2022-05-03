package Global;
import java.io.InputStream;
import java.io.FileInputStream;
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
      System.out.println(prop);

      String home = System.getProperty("user.home");
      FileInputStream f = new FileInputStream(home + "/.carcassonne");
      prop = new Properties(prop);
      prop.load(f);
      f.close();
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

  public Logger logger() {
    Logger log = Logger.getLogger("Carcassonne.Logger");
    log.setLevel(Level.parse(lis("LogLevel")));
    return log;
  }
}
