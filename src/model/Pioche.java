package model;

import global.Configuration;
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
    init_pioche();
  }

  void init_pioche() {
    int allp = 0;
    while (allp != 71) {
      int typeTuile = r.nextInt(24);
      if (numberPerTile[typeTuile][1] != 0) {
        numberPerTile[typeTuile][1] = numberPerTile[typeTuile][1] - 1;
        pioche.add(allp, getTileFromInt(typeTuile));
        allp++;
      }
    }
    Configuration
      .instance()
      .logger()
      .info("Initialisation de la pioche avec " + pioche.size() + " tuiles");
  }

  Tile getTileFromInt(int i) {
    switch (i) {
      case 0:
        return new Tile(TileType.ABBEY, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, false);
      case 1:
        return new Tile(TileType.ABBEY, TileType.LAND, TileType.LAND, TileType.ROAD, TileType.LAND, false);
      case 2:
        return new Tile(TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.CITY_OPEN, true);
      case 3:
        return new Tile(TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.LAND, TileType.CITY_OPEN, false);
      case 4:
        return new Tile(TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.LAND, TileType.CITY_OPEN, true);
      case 5:
        return new Tile(TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.ROAD, TileType.CITY_OPEN, false);
      case 6:
        return new Tile(TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.LAND, TileType.CITY_OPEN, true);
      case 7:
        return new Tile(TileType.LAND, TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.LAND, TileType.LAND, false);
      case 8:
        return new Tile(TileType.LAND, TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.LAND, TileType.LAND, true);
      case 9:
        return new Tile(TileType.ROAD, TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.ROAD, TileType.ROAD, false);
      case 10:
        return new Tile(TileType.ROAD, TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.ROAD, TileType.ROAD, true);
      case 11:
        return new Tile(TileType.CITY_OPEN, TileType.LAND, TileType.CITY_OPEN, TileType.LAND, TileType.CITY_OPEN, false);
      case 12:
        return new Tile(TileType.CITY_OPEN, TileType.LAND, TileType.CITY_OPEN, TileType.LAND, TileType.CITY_OPEN, true);
      case 13:
        return new Tile(TileType.LAND, TileType.CITY_OPEN, TileType.CITY_OPEN, TileType.LAND, TileType.LAND, false);
      case 14:
        return new Tile(TileType.LAND, TileType.CITY_OPEN, TileType.LAND, TileType.CITY_OPEN, TileType.LAND, false);
      case 15:
        return new Tile(TileType.LAND, TileType.CITY_OPEN, TileType.LAND, TileType.LAND, TileType.LAND, false);
      case 16:
        return new Tile(TileType.ROAD, TileType.CITY_OPEN, TileType.ROAD, TileType.ROAD, TileType.LAND, false);
      case 17:
        return new Tile(TileType.ROAD, TileType.CITY_OPEN, TileType.LAND, TileType.ROAD, TileType.ROAD, false);
      case 18:
        return new Tile(TileType.TOWN, TileType.CITY_OPEN, TileType.ROAD, TileType.ROAD, TileType.ROAD, false);
      case 19:
        return new Tile(TileType.ROAD, TileType.CITY_OPEN, TileType.ROAD, TileType.LAND, TileType.ROAD, false);
      case 20:
        return new Tile(TileType.ROAD, TileType.ROAD, TileType.LAND, TileType.ROAD, TileType.LAND, false);
      case 21:
        return new Tile(TileType.ROAD, TileType.LAND, TileType.ROAD, TileType.ROAD, TileType.LAND, false);
      case 22:
        return new Tile(TileType.TOWN, TileType.LAND, TileType.ROAD, TileType.ROAD, TileType.ROAD, false);
      case 23:
        return new Tile(TileType.TOWN, TileType.ROAD, TileType.ROAD, TileType.ROAD, TileType.ROAD, false);
      default:
        return null;
    }
  }

  public Tile piocheTuile() {
    if (pioche.isEmpty()) {
      Configuration
        .instance()
        .logger()
        .warning("Impossible de piocher, la pioche est vide");
      return null;
    }
    Tile t = pioche.get(0);
    pioche.remove(0);
    Configuration.instance().logger().info("Piochage de la tuile " + t.toString());
    return t;
  }
}
