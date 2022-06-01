package global;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.*;

public class Configuration {
  private static Configuration instance = null;
  Properties prop;
  Path configFolder = Paths.get(System.getProperty("user.home") + File.separator + ".carcassonne");
  Path logsFolder = Paths.get(configFolder.toString() + File.separator + "logs");

  public static InputStream charge(String name) {
    return ClassLoader.getSystemClassLoader().getResourceAsStream(name);
  }

  /**
   * Genere le fichiers de configuration du jeu
   * @param configPath
   */
  private void generateConfigFolder(Path configPath) {
    try {
      Files.createDirectories(configPath);
      Files.createDirectories(Paths.get(configPath.toString() + File.separator + "logs"));
      Files.createDirectories(Paths.get(configPath.toString() + File.separator + "saves"));
      Files.copy(Configuration.charge("default.cfg"), Paths.get(configPath.toString() + File.separator + "config.cfg"),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      System.err.println("Impossible de créer le dossier de configuration");
    }
  }

  /**
   ** Retourne la chaine de caractère du chemin vers le fichier de configuration
   * @return String
   */
  public String getConfigFolderPath() {
    return configFolder.toString();
  }

  private Configuration() {
    prop = new Properties();
    // Génération du dossier si il n'existe pas
    if (!Files.exists(configFolder))
      generateConfigFolder(configFolder);

    // Utilisation du fichier de configuration contenue dans le dossier applications
    try {
      if (!Files.exists(Paths.get(configFolder.toString() + File.separator + "config.cfg")))
        Files.copy(Configuration.charge("default.cfg"), Paths.get(configFolder + "/config.cfg"),
            StandardCopyOption.REPLACE_EXISTING);
      FileInputStream f = new FileInputStream(configFolder.toFile() + File.separator + "config.cfg");
      prop = new Properties(prop);
      prop.load(f);
      f.close();
    } catch (Exception e) {
      // Utilisation du fichier de configuration par défaut
      try {
        prop = new Properties(prop);
        InputStream f = Configuration.charge("default.cfg");
        prop.load(f);
        f.close();
        Files.copy(Configuration.charge("default.cfg"), Paths.get(configFolder.toString() + "/config.cfg"), StandardCopyOption.REPLACE_EXISTING);
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

  /**
   ** CHange le fichier de property
   */
  public void setProperty(String key, String value) {
    prop.setProperty(key, value);
    try {
      if (configFolder != null) {
        prop.store(new FileOutputStream(configFolder.toString() + File.separator + "config.cfg"), null);
      }
    } catch (IOException e) {
      logger().severe("Le fichier de configuration n'existe pas");
    }
  }

  /**
   ** Génére le nom de fichier pour les logs
   * @return
   */
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
