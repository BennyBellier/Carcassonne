package model;

import java.util.Arrays;
import java.util.List;

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

  public Meeple(byte[] b) {
    player = b[0];
    x = b[1];
    y = b[2];
    card = String.valueOf((char) b[3]);
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
   * @return String type : "n", "s", "e", "w", "c"
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

  public boolean equal(Meeple m) {
    return x == m.getX() && y == m.getY() && card.equals(m.getCardinal());
  }

  /**
   ** Retourne la définition binaire d'un meeple
   * @return List<Byte>
   */
  public List<Byte> toByteArray() {
    return Arrays.asList((byte) player, (byte) x, (byte) y, (byte) card.charAt(0));
  }

  @Override
  public String toString() {
    return player + " (" + x + ", " + y + ") " + card;
  }

  @Override
  public Meeple clone() {
    return new Meeple(player, x, y, card);
  }
}
