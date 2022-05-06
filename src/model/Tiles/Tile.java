package model.Tiles;

import java.util.ArrayList;

import global.Configuration;

public class Tile {

  private TileType c, n, e, s, w;
  private boolean b, cityEnder, isStart = false;

  /**
   * Créer une tuile de type type et précisant les bords des autres tuiles
   * @param center type principale de la tuile
   * @param n type de connexion au nord
   * @param e type de connexion à l'est
   * @param s type de connexion au sud
   * @param w type de connexion à l'ouest
   * @param b vraie si la tuile as un blason
   * @param cityEnder vraie si la tuile ne contient que des partie qui finisse la ville
   */
  public Tile(
    TileType center,
    TileType north,
    TileType east,
    TileType south,
    TileType west,
    boolean blason,
    boolean cityEnder
  ) {
    c = center;
    n = north;
    e = east;
    s = south;
    w = west;
    b = blason;
    this.cityEnder = cityEnder;
    Configuration
      .instance()
      .logger()
      .info(
        "Génération d'une tuile de type : " +
        toString()
      );
  }

  /**
   * Retourne le type de la tuile
   */
  public TileType center() {
    return c;
  }

  /**
   * Retourne le type au nord de la tuile
   */
  public TileType north() {
    return n;
  }

  /**
   * Retourne le type au sud de la tuile
   */
  public TileType south() {
    return s;
  }

  /**
   * Retourne le type à l'est de la tuile
   */
  public TileType east() {
    return e;
  }

  /**
   * Retourne le type à l'ouest de la tuile
   */
  public TileType west() {
    return w;
  }

  public boolean blason() {
    return b;
  }

  public boolean cityEnder() {
    return cityEnder;
  }

  /**
   * Définie la tuile comme la tuile de départ
   */
  public void setToStart() {
    isStart = true;
  }

  /**
   * Retourne la lsite des cardinaux de la tuile ou l'on peut poser un meeple
   * @return ArrayList<String> contenant les cardinaux de la tuile ou l'on peut poser un meeple
   */
  public ArrayList<String> getMeeplesPosition() {
    ArrayList<String> pos = new ArrayList<>();
    if (c != TileType.TOWN && c != TileType.FIELD)
      pos.add("c");
    if (n != TileType.FIELD)
      pos.add("n");
    if (e != TileType.FIELD)
      pos.add("e");
    if (s != TileType.FIELD)
      pos.add("s");
    if (w != TileType.FIELD)
      pos.add("w");
    return pos;
  }

  /**
   * Retourne vraie si la tuile est celle de départ
   * @return boolean
   */
  public boolean isStartTile() {
    return isStart;
  }

  /**
   * Retourne une copie profonde de la tuile
   * @return Tile with deep copy
   */
  public Tile clone() {
    return new Tile(c, n, e, s, w, b, cityEnder);
  }

  public void turnLeft() {
    TileType tmp = n;
    n = e;
    e = s;
    s = w;
    w = tmp;
  }

  public void turnRight() {
    TileType tmp = n;
    n = w;
    w = s;
    s = e;
    e = tmp;
  }

  /**
   * Retourne un boolean si l'on peut connecter la tuile avec la tuile t
   * sur avec le bords de coté side
   * @param t la tuile avec laquelle on veut la connecter
   * @param side le coté avec laquelle la tuile va se connecter : "n", "s", "e" ou "w"
   * pour respectivement Nord, Sud, Est et Ouest
   * @return boolean vraie si la connection est possible, faux sinon
   */
  public boolean canConnect(Tile t, String side) {
    if (t == null) return true;
    switch (side) {
      case "n":
        Configuration
          .instance()
          .logger()
          .info(
            "Connexion entre la tuile : " +
            toString() +
            " et " +
            t.toString() +
            " = " +
            (n == t.south())
          );
        return n == t.south();
      case "e":
        Configuration
          .instance()
          .logger()
          .info(
            "Connexion entre la tuile : " +
            toString() +
            " et " +
            t.toString() +
            " = " +
            (e == t.west())
          );
        return e == t.west();
      case "s":
        Configuration
          .instance()
          .logger()
          .info(
            "Connexion entre la tuile : " +
            toString() +
            " et " +
            t.toString() +
            " = " +
            (s == t.north())
          );
        return s == t.north();
      case "w":
        Configuration
          .instance()
          .logger()
          .info(
            "Connexion entre la tuile : " +
            toString() +
            " et " +
            t.toString() +
            " = " +
            (w == t.east())
          );
        return w == t.east();
      default:
        return false;
    }
  }

  /**
   * Retourne une chaine de caractère décrivant la tuile
   */
  public String toString() {
    return (
      c.toString() +
        " -> north = " +
        n.toString() +
        ", south = " +
        s.toString() +
        ", east = " +
        e.toString() +
        ", ouest = " +
        w.toString() +
        ", avec blason = " +
        b
    );
  }

  /**
   * Retourne une chaine de caractère permettant d'afficher une tuile
   * @return la valeur de retour est de type :
   * +-------+
   * |   C   |
   * | R S R |
   * |   L   |
   * +-------+
   */
  public String[] toStringArray() {
    String[] str = new String[3];
    if (b)
      str[0] = "  " + n.toOneCharString() + " b";
    else
      str[0] = "  " + n.toOneCharString() + "  ";
    str[1] =
      e.toOneCharString() +
      " " +
      c.toOneCharString() +
      " " +
      w.toOneCharString();
    str[2] = "  " + s.toOneCharString() + "  ";
    return str;
  }

  /**
   * Crée et retourne la tuile de départ
   * @return Tile
   */
  public static Tile getStartTile() {
    Tile start = new Tile(
      TileType.ROAD,
      TileType.CITY,
      TileType.ROAD,
      TileType.FIELD,
      TileType.ROAD,
      false,
      true
    );
    start.setToStart();
    return start;
  }

  public boolean equalsTo(Tile t) {
    return c == t.center() && n == t.north() && e == t.east() && s == t.south() && w == t.west() && b == t.blason() && cityEnder == t.cityEnder() && isStart == t.isStartTile();
  }
}
