package model;

import global.Configuration;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Tiles.*;

public class GameSet {

  private Tile[][] tiles;
  private Tile start;

  /**
   ** Initialise le plateau en y mettant la tuile de départ
   */
  public GameSet() {
    tiles = new Tile[3][3];
    start = Tile.getStartTile();
    tiles[1][1] = start;
  }

  /**
   ** Retourne les coordonnées courante de la tuile de départ dans la matrice
   * @return int
   */
  public Point getStartTilePoint() {
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        if (tiles[i][j] != null && tiles[i][j].isStartTile()) {
          Configuration
            .instance()
            .logger()
            .info(
              "Récupération des coordonnées de la tuile de départ : (" +
              i +
              ", " +
              j +
              ")"
            );
          return new Point(i, j);
        }
      }
    }
    Configuration.instance().logger().severe("Tuile de départ non trouvable");
    return null;
  }

  /**
   ** Redimensionne la matrice en ajoutant 1 colonnes de chaque côté du plateau courant
   */
  void redimTiles() {
    Tile[][] newTiles = new Tile[tiles.length + 2][tiles[0].length + 2];
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[0].length; j++) {
        newTiles[i + 1][j + 1] = tiles[i][j];
      }
    }
    Configuration
      .instance()
      .logger()
      .info(
        "Redimensionnement de la matrice : " +
        tiles.length +
        ", " +
        tiles[0].length +
        " -> " +
        newTiles.length +
        ", " +
        newTiles[0].length
      );
    tiles = newTiles;
  }

  /**
   ** Retourne vraie si il n'y a pas de tuiles sur les 4 côtés de la tuile à la position (x, y)
   * @param x
   * @param y
   * @return boolean
   */
  boolean noTilesAround(int x, int y) {
    return !(
      isTiles(y, x - 1) ||
      isTiles(y, x + 1) ||
      isTiles(y - 1, x) ||
      isTiles(y + 1, x)
    );
  }

  /**
   ** Retourne vraie si une tuile se situe au coordonnées (x, y)
   * @param x
   * @param y
   * @return boolean
   */
  boolean isTiles(int x, int y) {
    if (y >= 0 && y < tiles.length && x >= 0 && x < tiles[y].length) return (
      tiles[y][x] != null
    ); else return false;
  }

  /**
   ** Retourne une copie de la matrice contenant les tuiles
   * @return Tile[][]
   */
  public Tile[][] cloneSet() {
    Tile[][] cp = tiles.clone();
    for (int i = 0; i < cp.length; i++) cp[i] = tiles[i].clone();
    return cp;
  }

  /**
   ** Ajoute une tuile t aux coordonnées (x, y) si cela est possible
   * @param t Tiles
   * @param x int
   * @param y int
   * @return booléen
   */
  public boolean addTile(Tile t, int x, int y) {
    if (t == null) return false;

    Point start = getStartTilePoint();

    Configuration
      .instance()
      .logger()
      .info("Essaie de placement d'une tuile en (" + x + ", " + y + ")");

    x = (int) start.getX() + x;
    y = (int) start.getY() + y;

    if (y < -1 || y > tiles.length + 1 || x < -1 || x > tiles[y].length + 1) {
      Configuration
        .instance()
        .logger()
        .severe("Impossible d'ajouter une tuile en dehors du plateau");
      return false;
    }

    if (noTilesAround(x, y)) {
      Configuration
        .instance()
        .logger()
        .warning(
          "Impossible d'ajouter la tuile : " +
          t.toString() +
          " au coordonnées (" +
          x +
          ", " +
          y +
          ")" +
          ", aucune tuiles n'est à proximité"
        );
      return false;
    }

    if (y >= 0 && y < tiles.length) {
      if (x - 1 >= 0 && !t.canConnect(tiles[y][x - 1], "e")) {
        Configuration
          .instance()
          .logger()
          .warning(
            "Impossible d'ajouter la tuile : " +
            t.toString() +
            " au coordonnées (" +
            x +
            ", " +
            y +
            ")"
          );
        return false;
      }
      if (x + 1 < tiles[y].length && !t.canConnect(tiles[y][x + 1], "w")) {
        Configuration
          .instance()
          .logger()
          .warning(
            "Impossible d'ajouter la tuile : " +
            t.toString() +
            " au coordonnées (" +
            x +
            ", " +
            y +
            ")"
          );
        return false;
      }
    }
    if (x >= 0 && x < tiles[y].length) {
      if (y - 1 >= 0 && !t.canConnect(tiles[y - 1][x], "s")) {
        Configuration
          .instance()
          .logger()
          .warning(
            "Impossible d'ajouter la tuile : " +
            t.toString() +
            " au coordonnées (" +
            x +
            ", " +
            y +
            ")"
          );
        return false;
      }
      if (y + 1 < tiles.length && !t.canConnect(tiles[y + 1][x], "n")) {
        Configuration
          .instance()
          .logger()
          .warning(
            "Impossible d'ajouter la tuile : " +
            t.toString() +
            " au coordonnées (" +
            x +
            ", " +
            y +
            ")"
          );
        return false;
      }
    }

    if (x == 0 || x == tiles[y].length - 1 || y == 0 || y == tiles.length - 1) {
      redimTiles();
      x += 1;
      y += 1;
    }

    Configuration
      .instance()
      .logger()
      .info(
        "Ajout de la tuile : " +
        t.toString() +
        " au coordonnées (" +
        x +
        ", " +
        y +
        ")"
      );

    tiles[y][x] = t;
    return true;
  }

  public List<String> meeplePlacementPossible(Tile t) {
    List<String> card = new ArrayList<>();
    if (t.north() != TileType.FIELD)
      card.add("n");
    if (t.south() != TileType.FIELD)
      card.add("s");
    if (t.east() != TileType.FIELD)
      card.add("e");
    if (t.west() != TileType.FIELD)
      card.add("w");
    if (t.center() != TileType.FIELD && t.center() != TileType.TOWN)
      card.add("c");
    return card;
  }

  public Map<Integer, ArrayList<Integer>> tileAllowed(Tile t) {
    Map<Integer, ArrayList<Integer>> map = new HashMap<>();
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        if ((!map.containsKey(i) || (map.containsKey(i) && !map.get(i).contains(j))) && tiles[i][j] != null) {
          for (int r = 0; r < 3; r++) {
            if (tiles[i][j+1] == null && tiles[i][j].canConnect(t, "n")) {
              ArrayList<Integer> l;
              if (!map.containsKey(i))
                l = new ArrayList<>();
              else
                l = map.get(i);
              l.add(j+1);
              map.put(i, l);
            }
            if (tiles[i][j-1] == null && tiles[i][j].canConnect(t, "s")) {
              ArrayList<Integer> l;
              if (!map.containsKey(i))
                l = new ArrayList<>();
              else
                l = map.get(i);
              l.add(j-1);
              map.put(i, l);
            }
            if (tiles[i+1][j] == null && tiles[i][j].canConnect(t, "e")) {
              ArrayList<Integer> l;
              if (!map.containsKey(i))
                l = new ArrayList<>();
              else
                l = map.get(i);
              l.add(j);
              map.put(i, l);
            }
            if (tiles[i-1][j] == null && tiles[i][j].canConnect(t, "w")) {
              ArrayList<Integer> l;
              if (!map.containsKey(i))
                l = new ArrayList<>();
              else
                l = map.get(i);
              l.add(j+1);
              map.put(i, l);
            }
            t.turnLeft();
          }
        }
      }
    }
    return map;
  }

  /**
   ** Retourne un String du plateau de tuiles
   * @return String
   */
  public String toString() {
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < tiles.length; i++) {
      for (int j2 = 0; j2 < 3; j2++) {
        for (int j = 0; j < tiles[i].length; j++) {
          if (tiles[i][j] != null) b.append(
            tiles[i][j].toStringArray()[j2] + "|"
          ); else b.append("·····|");
        }
        b.append("\n");
      }
      b.append("\n");
    }
    return b.toString();
  }
}
