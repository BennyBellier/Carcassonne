package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.imageio.ImageIO;

import global.Configuration;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author ludov
 */
public class Images {

  Image blason, meeplePossibility;
  ArrayList<ArrayList<Image>> list = new ArrayList<>();

  public Images() {
    try {
      loadTiles();
      blason = ImageIO.read(Configuration.charge("Images/blason.png"));
      meeplePossibility = ImageIO.read(
          Configuration.charge("Images/meeple.png"));
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
      list.add(new ArrayList<>());
    }

    for (int i = 0; i <= 18; i++) {
      j = 1;
      try {
        list.get(i).add(ImageIO.read(Configuration.charge("Images/tiles/" + i + ".png")));
        while (j < 4) {
          InputStream in;
          if ((in = Configuration.charge("Images/tiles/" + i + "-" + j + ".png")) != null)
            list.get(i).add(ImageIO.read(in));
          ++j;
        }
      } catch (IOException e) {
        Configuration.instance().logger().severe("Impossible de charger les images " + i);
        e.printStackTrace();
      }
    }
  }
}
