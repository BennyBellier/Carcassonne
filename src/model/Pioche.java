package model;

import global.Configuration;
import model.Tiles.*;

import java.util.*;

public class Pioche {

  private int[][] numberPerTile = {
    { 0, 4 },
    { 1, 2 },
    { 2, 0 },
    { 3, 3 },
    { 4, 1 },
    { 5, 1 },
    { 6, 2 },
    { 7, 3 },
    { 8, 2 },
    { 9, 3 },
    { 10, 2 },
    { 11, 1 },
    { 12, 2 },
    { 13, 2 },
    { 14, 3 },
    { 15, 5 },
    { 16, 3 },
    { 17, 3 },
    { 18, 3 },
    { 19, 4 },
    { 20, 8 },
    { 21, 9 },
    { 22, 4 },
    { 23, 1 },
  };
  private LinkedList<Tile> pioche;
  private Random r;

  public Pioche() {
    pioche = new LinkedList<Tile>();
    r = new Random();
    initPioche();
  }

  /**
   ** Remplie la pioche de tuiles au début de la partie
   */
  void initPioche() {
    int allp = 0;
    while (allp != 71) {
      int typeTuile = r.nextInt(24);
      if (numberPerTile[typeTuile][1] != 0) {
        numberPerTile[typeTuile][1] -= 1;
        pioche.add(allp, Tile.getTileFromInt(typeTuile));
        ++allp;
      }
    }
    Configuration
      .instance()
      .logger()
      .info("Initialisation de la pioche avec " + pioche.size() + " tuiles");
  }

  /**
   ** Retourne une copie de la pioche {@code LikedList<Tile>}.
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
   * @return vraie si la pioche est vide
   */
  public boolean isEmpty() {
    return pioche.isEmpty();
  }

  /**
   ** Retourne une tuile dans la pioche
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

  public void remiserTuile(Tile t) {
    if (t != null) {
      pioche.add(r.nextInt(pioche.size()), t);
      Configuration.instance().logger().info("Tuile : " + t.toString() + " remiser");
    }
    else {
      Configuration.instance().logger().severe("Impossible de remiser une tuile inexistante");
    }
  }
}
