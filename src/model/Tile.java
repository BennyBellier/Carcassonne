package model;

import global.Configuration;

public class Tile {

  private TileType type;
  private TileType n;
  private TileType e;
  private TileType s;
  private TileType w;
  private boolean b;

  /**
   * Créer une tuile de type type et précisant les bords des autres tuiles
   * @param type type principale de la tuile
   * @param n type de connexion au nord
   * @param e type de connexion à l'est
   * @param s type de connexion au sud
   * @param w type de connexion à l'ouest
   */
  public Tile(
    TileType type,
    TileType north,
    TileType east,
    TileType south,
    TileType west,
    boolean blason
  ) {
    this.type = type;
    n = north;
    e = east;
    s = south;
    w = west;
    b = blason;
    Configuration
      .instance()
      .logger()
      .info(
        "Génération d'une tuile de type : " +
        type.toString() +
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
   * Retourne le type de la tuile
   */
  TileType getType() {
    return type;
  }

  /**
   * Retourne le type de tuile à placer au nord
   */
  TileType north() {
    return n;
  }

  /**
   * Retourne le type de tuile à placer au sud
   */
  TileType south() {
    return s;
  }

  /**
   * Retourne le type de tuile à placer à l'est
   */
  TileType east() {
    return e;
  }

  /**
   * Retourne le type de tuile à placer à l'ouest
   */
  TileType west() {
    return w;
  }

  /**
   * Retourne vraie si la tuile est celle de départ
   * @return boolean
   */
  public boolean isStartTile() {
    return type == TileType.START;
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
            type.toString() +
            " et " +
            t.getType().toString() +
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
            type.toString() +
            " et " +
            t.getType().toString() +
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
            type.toString() +
            " et " +
            t.getType().toString() +
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
            type.toString() +
            " et " +
            t.getType().toString() +
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
      type.toString() +
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
    str[0] = "  " + n.toOneCharString() + "  ";
    str[1] =
      e.toOneCharString() +
      " " +
      type.toOneCharString() +
      " " +
      w.toOneCharString();
    str[2] = "  " + s.toOneCharString() + "  ";
    return str;
  }
}
