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

  Image blason, meeplePossibility , plateauInGame , croix , menu, highlight , boisB , bleu , jaune ,noir , rouge , vert , pioche , valider , refaire;
  ArrayList<ArrayList<Image>> list = new ArrayList<>();

  public Images() {
    try {
      loadTiles();
      blason = ImageIO.read(Configuration.charge("Images/blason.png"));
      meeplePossibility = ImageIO.read(Configuration.charge("Images/meeple.png"));
      croix = ImageIO.read(Configuration.charge("Images/skip.png"));
      menu = ImageIO.read(Configuration.charge("Images/icon_menu.png"));
      plateauInGame = ImageIO.read(Configuration.charge("Images/bois.jpg"));
      highlight = ImageIO.read(Configuration.charge("Images/highlight.png"));
      boisB = ImageIO.read(Configuration.charge("Images/boisBoutons.png"));
      bleu = ImageIO.read(Configuration.charge("Images/Bleu.png"));
      rouge = ImageIO.read(Configuration.charge("Images/Rouge.png"));
      noir = ImageIO.read(Configuration.charge("Images/Noir.png"));
      vert = ImageIO.read(Configuration.charge("Images/Vert.png"));
      jaune = ImageIO.read(Configuration.charge("Images/Jaune.png"));
      pioche = ImageIO.read(Configuration.charge("Images/Pioche.png"));
      valider = ImageIO.read(Configuration.charge("Images/validate.png"));
      refaire = ImageIO.read(Configuration.charge("Images/validate_undo.png"));
    } catch (Exception e) {
      Configuration.instance().logger().severe("Impossible de charger les tuiles");
      e.printStackTrace();
    }
  }

  public Image croix(){
    return croix;
  }

  public Image menu(){
    return menu;
  }

  public Image boisB(){
    return boisB;
  }

  public Image bleu(){
    return bleu;
  }
  
  public Image rouge(){
    return rouge;
  }
  
  public Image noir(){
    return noir;
  }
  
  public Image jaune(){
    return jaune;
  }
  
  public Image vert(){
    return vert;
  }
  
  public Image pioche(){
    return pioche;
  }
  
  public Image valider(){
    return valider;
  }

  public Image refaire(){
    return refaire;
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
