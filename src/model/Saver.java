package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

import global.Configuration;

public class Saver {

  Stack<Save> history;

  public Saver() {
    history = new Stack<>();
  }

  public void addSave(Save s) {
    history.push(s);
  }

  public Save getLastSave() {
    return history.pop();
  }

  public static String formatFileString(String s) {
    if (!s.endsWith(".dat"))
      s = s.concat(".dat");
    return Configuration.instance().getConfigFolderPath() + File.separator + "saves" + File.separator + s;
  }

  public void saveGame(String file) {
    File f = null;
    try {
      String filePath = formatFileString(file);
      int i = 1;
      while (Files.exists(Paths.get(filePath))) {
        ++i;
        filePath = formatFileString(file + i);
      }

      f = new File(filePath);

      FileOutputStream outputStream = new FileOutputStream(f);

      byte[] bytes = history.pop().toArray();

      outputStream.write(bytes, 0, bytes.length);

      outputStream.close();
    } catch (IOException e) {
      Configuration.instance().logger().severe("Erreur, impossible d'enregistrer la partie");
      e.printStackTrace();
    } catch (IndexOutOfBoundsException iOfBoundsException) {
      Configuration.instance().logger().severe("Auncune sauvegardes disponible");
      if (f != null)
        f.delete();
      iOfBoundsException.printStackTrace();
    }
  }

  public static Save load(String file) {
    try {
      Configuration.instance().logger().info("Chargement de la sauvegarde : " + file);
      FileInputStream inputStream = new FileInputStream(file);
      Save s = Save.fromFile(inputStream);
      inputStream.close();
      return s;
    } catch (FileNotFoundException fe) {
      Configuration.instance().logger().severe("Fichier de sauvegarde inexistant");
      fe.printStackTrace();
    } catch (IOException ioe) {
      Configuration.instance().logger().severe("Fichier de sauvegarde corrompu");
      ioe.printStackTrace();
    }
    return null;
  }
}
