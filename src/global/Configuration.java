package global;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.*;

public class Configuration {
  private static Configuration instance = null;
  Properties prop;
  Path logsFolder;
  Path configFolder;

  public static InputStream charge(String name) {
    return ClassLoader.getSystemClassLoader().getResourceAsStream(name);
  }

  private Path setConfigFolder() {
    Path path = null;
    switch (OSInfo.getOs()) {
      case MAC:
        configFolder = Path.of(System.getProperty("user.home") + File.separator + "Library" + File.separator
            + "Application Support" + File.separator + ".carcassonne");
        break;
      case WINDOWS:
        configFolder = Path.of(System.getProperty("user.home") + File.separator
            + "Local Settings" + File.separator + "ApplicationData" + File.separator + ".carcassonne");
        break;
      case UNIX:
        configFolder = Path.of(System.getProperty("user.home") + File.separator + ".carcassonne");
        break;

      default:
        logger().severe("Votre système d'exploitation n'est pas supporté");
        System.exit(0);
        break;
    }
    return path;
  }

  private void generateConfigFolder(Path configPath) {
    try {
      Files.createDirectories(configPath);
      Files.createDirectories(Path.of(configPath.toString() + File.separator + "logs"));
      Files.createDirectories(Path.of(configPath.toString() + File.separator + "saves"));
      Files.copy(Path.of("assets/default.cfg"), Path.of(configPath + "/config.cfg"),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      System.err.println("Impossible de créer le dossier de configuration");
    }
  }

  public String getConfigFolderPath() {
    return configFolder.toString();
  }

  private Configuration() {
    prop = new Properties();
    setConfigFolder();
    // Génération du dossier si il n'existe pas
    if (!Files.exists(configFolder))
      generateConfigFolder(configFolder);

    logsFolder = Path.of(configFolder.toString() + File.separator + "logs");

    // Utilisation du fichier de configuration contenue dans le dossier applications
    try {
      if (!Files.exists(Path.of(configFolder.toFile() + File.separator + "config.cfg")))
        Files.copy(Path.of("assets/default.cfg"), Path.of(configFolder + "/config.cfg"),
            StandardCopyOption.REPLACE_EXISTING);
      FileInputStream f = new FileInputStream(configFolder.toFile() + File.separator + "config.cfg");
      prop = new Properties(prop);
      prop.load(f);
      f.close();
    } catch (Exception e) {
      // Utilisation du fichier de configuration par défaut
      try {
        prop = new Properties(prop);
        FileInputStream f = new FileInputStream("assets/default.cfg");
        prop.load(f);
        f.close();
        Files.copy(Path.of("default.cfg"), Path.of(configFolder + "/config.cfg"), StandardCopyOption.REPLACE_EXISTING);
      } catch (Exception e2) {
        System.err.println("Impossible de charger le fichier de configuration");
      }
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
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
    Date date = new Date();
    return formatter.format(date) + ".log";
  }

  public Logger logger() {
    Logger log = Logger.getLogger("Carcassonne.Logger");
    log.setLevel(Level.parse(lis("LogLevel")));

    if (log.getHandlers().length == 0) {
      try {
        FileHandler fh = new FileHandler(logsFolder.toString() + File.separator + getFileLoggerName());
        FileHandler fhLatest = new FileHandler(logsFolder.toString() + File.separator + "latest.log");
        log.addHandler(fh);
        log.addHandler(fhLatest);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        fhLatest.setFormatter(formatter);
      } catch (SecurityException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return log;
  }
}
