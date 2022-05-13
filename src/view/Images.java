package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.imageio.ImageIO;

import global.Configuration;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author ludov
 */
public class Images {

  Image img2;
  ArrayList<Image> images = new ArrayList<>(24);

  public Images() {
    try {
      for (int i = 0; i < 24; i++) {
        Image tuileTmp = ImageIO.read(new File("assets/Images/tiles/" + i + ".png"));
        images.add(tuileTmp);
      }
    } catch (Exception e) {
      Configuration.instance().logger().severe("Impossible de charger les tuiles");
      e.printStackTrace();
    }
  }

  public Image getImage2() {
    return img2;
  }

  public ArrayList<Image> getImagesList() {
    return images;
  }



}
