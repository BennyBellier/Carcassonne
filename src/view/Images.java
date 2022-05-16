package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.imageio.ImageIO;

import global.Configuration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.nio.file.Path;

/**
 *
 * @author ludov
 */
public class Images {

  Image blason;
  ArrayList<ArrayList<Image>> images = new ArrayList<>();

  public Images() {
    try {
      loadTiles();
      blason = ImageIO.read(new File("assets/Images/blason.png"));
    } catch (Exception e) {
      Configuration.instance().logger().severe("Impossible de charger les tuiles");
      e.printStackTrace();
    }
  }

  /**
   ** Charge les images des tuiles dans la liste images
   */
  void loadTiles() {
    int j;
    for (int i = 0; i <= 18; i++) {
      images.add(new ArrayList<>());
    }

    for (int i = 0; i <= 18; i++) {
      j = 1;
      try {
        images.get(i).add(ImageIO.read(new File("assets/Images/tiles/" + i + ".png")));
        while (j < 4) {
          if (Files.exists(Path.of("assets/Images/tiles/" + i + "-" + j + ".png")))
          images.get(i).add(ImageIO.read(new File("assets/Images/tiles/" + i + "-" + j + ".png")));
          ++j;
        }
      } catch (IOException e) {
        Configuration.instance().logger().severe("Impossible de charger les images " + i);
        e.printStackTrace();
      }
    }
  }

  /**
   ** Retourne l'image de blason
   * @return Image
   */
  public Image blason() {
    return blason;
  }

  /**
   ** Retourne la liste des images
   * @return ArrayList<ArrayList<Image>>
   */
  public ArrayList<ArrayList<Image>> getImagesList() {
    return images;
  }
}
