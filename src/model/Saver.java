package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import global.Configuration;

public class Saver {

  List<Save> history;

  public Saver() {
    history = new ArrayList<>();
  }

  public void addSave(Save s) {
    history.add(s);
  }

  public Save getLastSave() {
    return history.remove(history.size() - 1);
  }

  public static String formatFileString(String s) {
    if (!s.endsWith(".dat"))
      s = s.concat(".dat");
    return Configuration.instance().getConfigFolderPath() + File.separator + "saves" + File.separator + s;
  }

  public void saveGame(String file) {
    try {
      FileOutputStream outputStream = new FileOutputStream(new File(formatFileString(file)));

      byte[] bytes = history.get(history.size() - 1).toArray();

      outputStream.write(bytes, 0, bytes.length);

      outputStream.close();
    } catch (Exception e) {
      Configuration.instance().logger().severe("Erreur, impossible d'enregistrer la partie");
      e.printStackTrace();
    }
  }

  public static Save load(String file) {
    try {
      FileInputStream inputStream = new FileInputStream(formatFileString(file));
      Save s = Save.fromFile(inputStream);
      inputStream.close();
      return s;
    } catch (FileNotFoundException fe) {
      Configuration.instance().logger().severe("Ficheier de sauvegarde inexistant");
    } catch (IOException ioe) {
      Configuration.instance().logger().severe("Fichier de sauvegarde corrompu");
    }
    return null;
  }
}
