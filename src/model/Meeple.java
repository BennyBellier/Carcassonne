package model;

import global.Configuration;

public class Meeple {

  int x, y, player;
  String card;

  /**
   ** Créer un Meeple
   * @param player int correspondant au joueur
   * @param x
   * @param y
   * @param cardinal
   */
  public Meeple(int player, int x, int y, String cardinal) {
    this.player = player;
    this.x = x;
    this.y = y;
    card = cardinal;
    Configuration.instance().logger().info("Génération d'un meeple pour le joueur " + player + ", au coordonnées (" + x + ", " + y + ") de cardinal : "+ card);
  }

  /**
   ** Retourne la coordonnée x de la tuile ou se situe le meeple
   * @return int la coordonnée x de la tuile ou se situe le meeple
   */
  public int getX() {
    return x;
  }

    /**
   ** Retourne la coordonnée y de la tuile ou se situe le meeple
   * @return int la coordonnée y de la tuile ou se situe le meeple
   */
  public int getY() {
    return y;
  }

  /**
   ** Retourne le cardinal determinant la position du Meeple sur la tuile
   * @return String type : "n", "s", "e", "o", "c"
   */
  public String getCardinal() {
    return card;
  }

  /**
   ** Retourne l'entier correspondant au joueur qui possède le Meeple
   * @return int
   */
  public int getOwner() {
    return player;
  }
}
