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
  Image blason, meeplePossibility, plateauInGame, croix, menu, highlight, boisB, bleu, jaune, noir, rouge, vert, pioche,
      valider, refaire;
  Image hollowRed, hollowBlack, hollowBlue, hollowGreen, hollowYellow, meepleRed, meepleBlack, meepleBlue, meepleGreen,
      meepleYellow;
  ArrayList<ArrayList<Image>> list = new ArrayList<>();
  private final int colorBlue = new Color(7, 45, 249).getRGB();
  private final int colorRed = new Color(240, 0, 32).getRGB();
  private final int colorGreen = new Color(60, 212, 21).getRGB();
  private final int colorYellow = new Color(255, 235, 87).getRGB();
  private final int colorBlack = new Color(31, 31, 31).getRGB();

  public Images() {
    try {
      loadTiles();
      loadMeeples();
      blason = ImageIO.read(Configuration.charge("Images/blason.png"));
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

  public Image croix() {
    return croix;
  }

  public Image menu() {
    return menu;
  }

  public Image boisB() {
    return boisB;
  }

  public Image bleu() {
    return bleu;
  }

  public Image rouge() {
    return rouge;
  }

  public Image noir() {
    return noir;
  }

  public Image jaune() {
    return jaune;
  }

  public Image vert() {
    return vert;
  }

  public Image pioche() {
    return pioche;
  }

  public Image valider() {
    return valider;
  }

  public Image refaire() {
    return refaire;
  }

  void loadMeeples() {

    try {
      hollowRed = ImageIO.read(Configuration.charge("Images/meeples/hollowRouge.png"));
      hollowBlack = ImageIO.read(Configuration.charge("Images/meeples/hollowNoir.png"));
      hollowBlue = ImageIO.read(Configuration.charge("Images/meeples/hollowBleu.png"));
      hollowGreen = ImageIO.read(Configuration.charge("Images/meeples/hollowVert.png"));
      hollowYellow = ImageIO.read(Configuration.charge("Images/meeples/hollowJaune.png"));
      meepleRed = ImageIO.read(Configuration.charge("Images/meeples/meepleRouge.png"));
      meepleBlack = ImageIO.read(Configuration.charge("Images/meeples/meepleNoir.png"));
      meepleBlue = ImageIO.read(Configuration.charge("Images/meeples/meepleBleu.png"));
      meepleGreen = ImageIO.read(Configuration.charge("Images/meeples/meepleVert.png"));
      meepleYellow = ImageIO.read(Configuration.charge("Images/meeples/meepleJaune.png"));
    } catch (IOException e) {
      Configuration.instance().logger().severe("Impossible de charger les images des meeples");
      e.printStackTrace();
    }
  }

  public Image hollowMeeple(Color c) {
    if (c.getRGB() == colorBlack)
      return hollowBlack;
    else if (c.getRGB() == colorBlue)
      return hollowBlue;
    else if (c.getRGB() == colorGreen)
      return hollowGreen;
    else if (c.getRGB() == colorYellow)
      return hollowYellow;
    else if (c.getRGB() == colorRed)
      return hollowRed;

    return null;
  }

  public Image Meeple(Color c) {
    if (c.getRGB() == colorBlack)
      return meepleBlack;
    else if (c.getRGB() == colorBlue)
      return meepleBlue;
    else if (c.getRGB() == colorGreen)
      return meepleGreen;
    else if (c.getRGB() == colorYellow)
      return meepleYellow;
    else if (c.getRGB() == colorRed)
      return meepleRed;

    return null;
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
