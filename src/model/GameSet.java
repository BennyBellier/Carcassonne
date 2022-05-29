package model;

import global.Configuration;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Projects.Project;

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

  public GameSet (Tile[][] set) {
    tiles = set;
  }

  /**
   ** Retourne les coordonnées courante de la tuile de départ dans la matrice
   *
   * @return int
   */
  public Point getStartTilePoint() {
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        if (tiles[i][j] != null && tiles[i][j].isStartTile()) {
          Configuration
              .instance()
              .logger()
              .finer(
                  "Récupération des coordonnées de la tuile de départ : (" +
                      i +
                      ", " +
                      j +
                      ")");
          return new Point(i, j);
        }
      }
    }
    Configuration.instance().logger().severe("Tuile de départ non trouvable");
    return null;
  }

  /**
   ** Redimensionne la matrice en ajoutant 1 colonnes de chaque côté du plateau
   * courant
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
        .fine(
            "Redimensionnement de la matrice : " +
                tiles.length +
                ", " +
                tiles[0].length +
                " -> " +
                newTiles.length +
                ", " +
                newTiles[0].length);
    tiles = newTiles;
  }

  /**
   ** Retourne vraie si il n'y a pas de tuiles sur les 4 côtés de la tuile à la
   * position (x, y)
   *
   * @param x
   * @param y
   * @return boolean
   */
  boolean noTilesAround(int x, int y) {
    return !(isTiles(x - 1, y) ||
        isTiles(x + 1, y) ||
        isTiles(x, y - 1) ||
        isTiles(x, y + 1));
  }

  /**
   ** Retourne vraie si une tuile se situe au coordonnées (x, y)
   *
   * @param x
   * @param y
   * @return boolean
   */
  boolean isTiles(int x, int y) {
    if (y >= 0 && y < tiles.length && x >= 0 && x < tiles[y].length)
      return (tiles[y][x] != null);
    else
      return false;
  }

  /**
   ** Retourne une copie de la matrice contenant les tuiles
   *
   * @return Tile[][]
   */
  public Tile[][] cloneSet() {
    Tile[][] cp = tiles.clone();
    for (int i = 0; i < cp.length; i++)
      cp[i] = tiles[i].clone();
    return cp;
  }

  /**
   ** Retourne vraie si la tuile de position (x, y) du plateau a été enlevé
   *
   * @param x int position x de la tuile
   * @param y int position y de la tuile
   * @return boolean vraie si la tuile a été enlevé, faux sinon
   */
  public boolean removeTile(int x, int y) {
    if (tiles[y][x] != null)
      tiles[y][x] = null;
    return true;
  }

  /**
   ** Ajoute une tuile t aux coordonnées (x, y) si cela est possible et retourne
   * vraie si la tuile à pu être posé, faux sinon
   *
   * @param t Tile à poser
   * @param x int position x sur laquelle posé la tuile
   * @param y int position y sur laquelle posé la tuile
   * @return booléen vraie si la tuile a été posé, faux sinon
   */
  public boolean addTile(Tile t, int x, int y) {
    if (t == null)
      return false;

    Point start = getStartTilePoint();

    Configuration
        .instance()
        .logger()
        .fine("Essaie de placement d'une tuile en (" + x + ", " + y + ")");

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
                  ", aucune tuiles n'est à proximité");
      return false;
    }

    if (tiles[y][x] != null)
      return false;

    if (y >= 0 && y < tiles.length) {
      if (x - 1 >= 0 && !t.canConnect(tiles[y][x - 1], "w")) {
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
                    ")");
        return false;
      }
      if (x + 1 < tiles[y].length && !t.canConnect(tiles[y][x + 1], "e")) {
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
                    ")");
        return false;
      }
    }
    if (x >= 0 && x < tiles[y].length) {
      if (y - 1 >= 0 && !t.canConnect(tiles[y - 1][x], "n")) {
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
                    ")");
        return false;
      }
      if (y + 1 < tiles.length && !t.canConnect(tiles[y + 1][x], "s")) {
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
                    ")");
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
                ")");

    tiles[y][x] = t;
    return true;
  }

  /**
   ** Retourne vraie si toutes les côtés de la tuiles peuvent être connectés au
   * tuiles environnantes
   *
   * @param x int position x de la tuile
   * @param y int position y de la tuile
   * @param t Tile à tester
   * @return
   */
  boolean checkAllTileConnection(int x, int y, Tile t) {
    boolean n = false, s = false, e = false, w = false;

    if (y + 1 >= tiles.length)
      s = true;
    else {
      if (t.canConnect(tiles[y + 1][x], "s"))
        s = true;
    }
    if (y - 1 < 0)
      n = true;
    else {
      if (t.canConnect(tiles[y - 1][x], "n"))
        n = true;
    }
    if (x + 1 >= tiles[y].length)
      e = true;
    else {
      if (t.canConnect(tiles[y][x + 1], "e"))
        e = true;
    }
    if (x - 1 < 0)
      w = true;
    else {
      if (t.canConnect(tiles[y][x - 1], "w"))
        w = true;
    }
    return n && s && e && w;
  }

  public GameSet clone() {
    GameSet res = new GameSet();
    res.tiles = cloneSet();
    return res;
  }

  /**
   ** Retourne la liste de tous les emplacements possibles pour la tuile t (avec
   ** les rotations ou non)
   *
   * @param t        Tile
   * @param withRota booléen si vraie alors calcul avec les rotations possibles,
   *                 sinon calcul sans les rotations
   * @return Map<Integer, ArrayList<Integer>>
   */
  public Map<Integer, ArrayList<Integer>> tilePositionsAllowed(Tile t, boolean withRota) {
    Map<Integer, ArrayList<Integer>> map = new HashMap<>();
    int r = 0;
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        if (!(map.containsKey(j) && map.get(j).contains(i) || (tiles[j][i] != null) || noTilesAround(i, j))) {
          do {
            if (checkAllTileConnection(i, j, t)) {
              ArrayList<Integer> l;
              if (!map.containsKey(j))
                l = new ArrayList<>();
              else
                l = map.get(j);
              l.add(i);
              map.put(j, l);
              break;
            }
            if (withRota)
              t.turnClock();
            else
              break;
            ++r;
          } while (r < 3 && withRota);
        }
      }
    }
    return map;
  }

  /**
   ** Retourne vraie si le meeple définie peut être placé
   *
   * @param m meeple définie avec la position (x, y) et sa cardinalité
   * @return vraie si le meeple peut être placé, faux sinon
   */
  public boolean meeplePlacementAllowed(Meeple m) {
    Tile t = getTileFromCoord(m.getX() + getStartTilePoint().x, m.getY() + getStartTilePoint().y);
    if (t != null) {
      System.out.println(t.toString());
      switch (m.getCardinal()) {
        case "c":
          return typeWhereMeepleAllow(t.center());
        case "n":
          return typeWhereMeepleAllow(t.north());
        case "s":
          return typeWhereMeepleAllow(t.south());
        case "e":
          return typeWhereMeepleAllow(t.east());
        case "w":
          return typeWhereMeepleAllow(t.west());
      }
    }
    return false;
  }

  /**
   ** Retourne vraie si le Type sur lequel on veut poser le meeple est autorisé
   *
   * @param t Type.Tile sur lequel on souhaite placer le meeple
   * @return vraie si le meeple peut y être placé
   */
  boolean typeWhereMeepleAllow(Tile.Type t) {
    return t != Tile.Type.FIELD && t != Tile.Type.TOWN;
  }

  /**
   ** retourne la tuile sur le plateau au coordonnées (x, y)
   *
   * @param x int position x de la tuile
   * @param y int position y de la tuile
   * @return Tile
   */
  public Tile getTileFromCoord(int x, int y) {
    return tiles[x][y];
  }

  /**
   ** Retourne le type à la position (x, y card)
   *
   * @param x
   * @param y
   * @param card
   * @return Tile.Type
   */
  public Tile.Type getCardType(int x, int y, String card) {
    Tile t = getTileFromCoord(x, y);
    return t.getCardinalType(card);
  }

  /**
   ** Retourne le type du projet pour la cardinalité 'card' de la tuile (x, y) sur
   * le plateau
   *
   * @param x    int position x de la tuile sur le plateau
   * @param y    int position y de la tuile sur le plateau
   * @param card String cardinalité recherché
   * @return Projet.Type corrsepondant au type de la cardinalité de la tuile
   */
  public Project.Type getProjectType(int x, int y, String card) {
    Tile t = getTileFromCoord(x, y);
    switch (card) {
      case "n":
        return tileTypeToProjectType(t.north());
      case "s":
        return tileTypeToProjectType(t.south());
      case "e":
        return tileTypeToProjectType(t.east());
      case "w":
        return tileTypeToProjectType(t.west());
      case "c":
        return tileTypeToProjectType(t.center());

      default:
        return null;
    }
  }

  /**
   ** Retourne le type de projet en fonction du type de la tuile
   *
   * @param tt Tile.Type
   * @return Project.Type correspondant au Tile.Type
   */
  public Project.Type tileTypeToProjectType(Tile.Type tt) {
    switch (tt) {
      case CITY:
        return Project.Type.CITY;
      case ROAD:
        return Project.Type.ROAD;
      case ABBEY:
        return Project.Type.ABBEY;
      default:
        return null;
    }
  }

  /**
   ** Retourne un String du plateau de tuiles
   *
   * @return String
   */
  public String toString() {
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < tiles.length; i++) {
      for (int j2 = 0; j2 < 3; j2++) {
        for (int j = 0; j < tiles[i].length; j++) {
          if (tiles[i][j] != null)
            b.append(
                tiles[i][j].toStringArray()[j2] + "|");
          else
            b.append("·····|");
        }
        b.append("\n");
      }
      b.append("\n");
    }
    return b.toString();
  }
}
