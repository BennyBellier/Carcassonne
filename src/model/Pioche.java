package model;

import global.Configuration;
import model.Tiles.Tile;
import model.Tiles.TileType;

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
        pioche.add(allp, getTileFromInt(typeTuile));
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
   ** Créer et retourne une tuile correspondant à son numéro
   * @param i int
   * @return Tile
   */
  Tile getTileFromInt(int i) {
    switch (i) {
      case 0:
        return new Tile(
          TileType.ABBEY,
          TileType.FIELD,
          TileType.FIELD,
          TileType.FIELD,
          TileType.FIELD,
          false,
          false
        );
      case 1:
        return new Tile(
          TileType.ABBEY,
          TileType.FIELD,
          TileType.FIELD,
          TileType.ROAD,
          TileType.FIELD,
          false,
          false
        );
      case 2:
        return new Tile(
          TileType.CITY,
          TileType.CITY,
          TileType.CITY,
          TileType.CITY,
          TileType.CITY,
          true,
          true
        );
      case 3:
        return new Tile(
          TileType.CITY,
          TileType.CITY,
          TileType.CITY,
          TileType.FIELD,
          TileType.CITY,
          false,
          true
        );
      case 4:
        return new Tile(
          TileType.CITY,
          TileType.CITY,
          TileType.CITY,
          TileType.FIELD,
          TileType.CITY,
          true,
          true
        );
      case 5:
        return new Tile(
          TileType.CITY,
          TileType.CITY,
          TileType.CITY,
          TileType.ROAD,
          TileType.CITY,
          false,
          true
        );
      case 6:
        return new Tile(
          TileType.CITY,
          TileType.CITY,
          TileType.CITY,
          TileType.FIELD,
          TileType.CITY,
          true,
          true
        );
      case 7:
        return new Tile(
          TileType.FIELD,
          TileType.CITY,
          TileType.CITY,
          TileType.FIELD,
          TileType.FIELD,
          false,
          true
        );
      case 8:
        return new Tile(
          TileType.FIELD,
          TileType.CITY,
          TileType.CITY,
          TileType.FIELD,
          TileType.FIELD,
          true,
          true
        );
      case 9:
        return new Tile(
          TileType.ROAD,
          TileType.CITY,
          TileType.CITY,
          TileType.ROAD,
          TileType.ROAD,
          false,
          true
        );
      case 10:
        return new Tile(
          TileType.ROAD,
          TileType.CITY,
          TileType.CITY,
          TileType.ROAD,
          TileType.ROAD,
          true,
          true
        );
      case 11:
        return new Tile(
          TileType.CITY,
          TileType.FIELD,
          TileType.CITY,
          TileType.FIELD,
          TileType.CITY,
          false,
          true
        );
      case 12:
        return new Tile(
          TileType.CITY,
          TileType.FIELD,
          TileType.CITY,
          TileType.FIELD,
          TileType.CITY,
          true,
          true
        );
      case 13:
        return new Tile(
          TileType.FIELD,
          TileType.CITY,
          TileType.CITY,
          TileType.FIELD,
          TileType.FIELD,
          false,
          false
        );
      case 14:
        return new Tile(
          TileType.FIELD,
          TileType.CITY,
          TileType.FIELD,
          TileType.CITY,
          TileType.FIELD,
          false,
          false
        );
      case 15:
        return new Tile(
          TileType.FIELD,
          TileType.CITY,
          TileType.FIELD,
          TileType.FIELD,
          TileType.FIELD,
          false,
          false
        );
      case 16:
        return new Tile(
          TileType.ROAD,
          TileType.CITY,
          TileType.ROAD,
          TileType.ROAD,
          TileType.FIELD,
          false,
          false
        );
      case 17:
        return new Tile(
          TileType.ROAD,
          TileType.CITY,
          TileType.FIELD,
          TileType.ROAD,
          TileType.ROAD,
          false,
          false
        );
      case 18:
        return new Tile(
          TileType.TOWN,
          TileType.CITY,
          TileType.ROAD,
          TileType.ROAD,
          TileType.ROAD,
          false,
          false
        );
      case 19:
        return new Tile(
          TileType.ROAD,
          TileType.CITY,
          TileType.ROAD,
          TileType.FIELD,
          TileType.ROAD,
          false,
          false
        );
      case 20:
        return new Tile(
          TileType.ROAD,
          TileType.ROAD,
          TileType.FIELD,
          TileType.ROAD,
          TileType.FIELD,
          false,
          false
        );
      case 21:
        return new Tile(
          TileType.ROAD,
          TileType.FIELD,
          TileType.ROAD,
          TileType.ROAD,
          TileType.FIELD,
          false,
          false
        );
      case 22:
        return new Tile(
          TileType.TOWN,
          TileType.FIELD,
          TileType.ROAD,
          TileType.ROAD,
          TileType.ROAD,
          false,
          false
        );
      case 23:
        return new Tile(
          TileType.TOWN,
          TileType.ROAD,
          TileType.ROAD,
          TileType.ROAD,
          TileType.ROAD,
          false,
          false
        );
      default:
        return null;
    }
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
}
