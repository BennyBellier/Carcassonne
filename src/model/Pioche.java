package model;

import global.Configuration;

import java.util.*;

public class Pioche {

  // Un numéro à était attribué pour chacunes des tuiles uniques
  // ci-dessous le tableau stock pour un type de tuiles (index) son nombre d'occurences dans le jeu
  private int[] numberPerTile = { 4, 2, 1, 3, 1, 1, 2, 3, 2, 3, 2, 1, 2, 2, 3, 5, 3, 3, 3, 3, 8, 9, 4, 1 };
  private LinkedList<Tile> pioche;
  private Random r;

  public Pioche() {
    pioche = new LinkedList<Tile>();
    r = new Random();
    initPioche();
  }

  public Pioche(byte[][] b) {
    pioche = new LinkedList<>();
    for (int i = 0; i < b.length; i++) {
      pioche.add(i, new Tile(b[i]));
    }
    r = new Random(5);
  }

  /**
   ** Remplie la pioche de tuiles au début de la partie
   */
  void initPioche() {
    int allp = 0;
    while (allp != 12) {
      int typeTuile = r.nextInt(24);
      if (numberPerTile[typeTuile] != 0) {
        numberPerTile[typeTuile] -= 1;
        pioche.add(allp, Tile.getTileFromInt(0));
        ++allp;
      }
    }
    Configuration
        .instance()
        .logger()
        .info("Initialisation de la pioche avec " + pioche.size() + " tuiles");
  }

  /**
   ** Retourne la taille de la pioche
   * @return int
   */
  public int size() {
    return pioche.size();
  }

  /**
   ** Retourne une copie de la pioche {@code LikedList<Tile>}.
   *
   * @return LinkedList<Tile>
   */
  public LinkedList<Tile> getPioche() {
    LinkedList<Tile> cp = new LinkedList<>();
    for (int i = 0; i < pioche.size(); i++) {
      cp.add(i, pioche.get(i).clone());
    }
    return cp;
  }

  /**
   ** Retourne l'état de la pioche
   *
   * @return vraie si la pioche est vide
   */
  public boolean isEmpty() {
    return pioche.isEmpty();
  }

  /**
   ** Retourne une tuile dans la pioche
   *
   * @return Tile
   */
  public Tile piocheTuile() {
    if (pioche.isEmpty()) {
      Configuration.instance().logger().warning("Impossible de piocher, la pioche est vide");
      return null;
    }
    Tile t = pioche.get(0);
    pioche.remove(0);
    Configuration.instance().logger().info("Piochage de la tuile " + t.toString());
    return t;
  }

  /**
   ** Remet une tuile dans la pioche
   * @param t Tile à remiser
   */
  public void remiserTuile(Tile t) {
    if (t != null) {
      pioche.add(r.nextInt(pioche.size()), t);
      Configuration.instance().logger().info("Tuile : " + t.toString() + " remiser");
    } else {
      Configuration.instance().logger().severe("Impossible de remiser une tuile inexistante");
    }
  }

  /**
   ** Retourne la définition binaire de la pioche
   * @return List<Byte>
   */
  public List<Byte> toByteArray() {
    List<Byte> bytes = new ArrayList<>();
    for (int i = 0; i < pioche.size(); i++) {
      bytes.addAll(pioche.get(i).toByteArray());
    }
    return bytes;
  }
}
