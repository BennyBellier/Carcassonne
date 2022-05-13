package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import global.Configuration;

public class Tile {

  public enum Type {
    TOWN, // petit ville au centre d'une seul tuile
    CITY, // ville sur plusieurs tuiles
    ROAD, // route
    ABBEY, // abbey
    FIELD; // prairie

    public byte toByte() {
      switch (this) {
        case TOWN:
          return (byte) 1;
        case CITY:
          return (byte) 2;
        case ROAD:
          return (byte) 3;
        case ABBEY:
          return (byte) 4;
        case FIELD:
          return (byte) 5;

        default:
          return (byte) -1;
      }
    }

    public static Type fromByte(byte b) {
      switch (b) {
        case 1:
          return TOWN;
        case 2:
          return CITY;
        case 3:
          return ROAD;
        case 4:
          return ABBEY;
        case 5:
          return FIELD;

        default:
          return null;
      }
    }

    /**
     ** Retourne une chaine de caractère contenant qu'une lettre pour décrire la
     * tuile
     *
     * @return String
     */
    public String toOneCharString() {
      switch (this) {
        case TOWN:
          return "T";
        case CITY:
          return "C";
        case ROAD:
          return "R";
        case ABBEY:
          return "A";
        case FIELD:
          return "F";
        default:
          return "";
      }
    }

    /**
     ** Retourne une chaine de caractère retournant le type de tuile
     *
     * @return String
     */
    public String toString() {
      switch (this) {
        case TOWN:
          return "TOWN";
        case CITY:
          return "CITY";
        case ROAD:
          return "ROAD";
        case ABBEY:
          return "ABBEY";
        case FIELD:
          return "FIELD";
        default:
          return "";
      }
    }
  }

  private Type c, n, e, s, w;
  private boolean b, cityEnder, isStart = false;

  /**
   ** Créer une tuile de type type et précisant les bords des autres tuiles
   *
   * @param center    type principale de la tuile
   * @param n         type de connexion au nord
   * @param e         type de connexion à l'est
   * @param s         type de connexion au sud
   * @param w         type de connexion à l'ouest
   * @param b         vraie si la tuile as un blason
   * @param cityEnder vraie si la tuile ne contient que des partie qui finisse la
   *                  ville
   */
  public Tile(
      Type center,
      Type north,
      Type east,
      Type south,
      Type west,
      boolean blason,
      boolean cityEnder) {
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
                toString());
  }

  public Tile(byte[] b) {
    c = Type.fromByte(b[0]);
    n = Type.fromByte(b[1]);
    e = Type.fromByte(b[2]);
    s = Type.fromByte(b[3]);
    w =  Type.fromByte(b[4]);
    this.b =  (b[5] == 1 ? true : false);
    cityEnder = (b[6] == 1 ? true : false);

    if (b[7] == 1)
      isStart = true;
  }

  /**
   ** Retourne le type de la tuile
   */
  public Type center() {
    return c;
  }

  /**
   ** Retourne le type au nord de la tuile
   */
  public Type north() {
    return n;
  }

  /**
   ** Retourne le type au sud de la tuile
   */
  public Type south() {
    return s;
  }

  /**
   ** Retourne le type à l'est de la tuile
   */
  public Type east() {
    return e;
  }

  /**
   ** Retourne le type à l'ouest de la tuile
   */
  public Type west() {
    return w;
  }

  /**
   ** Retourne vraie si la tuile a un blason, faux sinon
   *
   * @return boolean
   */
  public boolean blason() {
    return b;
  }

  /**
   ** Retourne vraie si la tuile ne contient qu'un ou plusieurs côté non relié
   * fermant la ville
   *
   * @return boolean
   */
  public boolean cityEnder() {
    return cityEnder;
  }

  /**
   ** Définie la tuile comme la tuile de départ
   */
  public void setToStart() {
    isStart = true;
  }

  /**
   ** Retourne la lsite des cardinaux de la tuile ou l'on peut poser un meeple
   *
   * @return ArrayList<String> contenant les cardinaux de la tuile ou l'on peut
   *         poser un meeple
   */
  public ArrayList<String> getMeeplesPosition() {
    ArrayList<String> pos = new ArrayList<>();
    if (c != Type.TOWN && c != Type.FIELD)
      pos.add("c");
    if (n != Type.FIELD)
      pos.add("n");
    if (e != Type.FIELD)
      pos.add("e");
    if (s != Type.FIELD)
      pos.add("s");
    if (w != Type.FIELD)
      pos.add("w");
    return pos;
  }

  /**
   ** Retourne vraie si la tuile est celle de départ
   *
   * @return boolean
   */
  public boolean isStartTile() {
    return isStart;
  }

  /**
   ** Retourne une copie profonde de la tuile
   *
   * @return Tile with deep copy
   */
  public Tile clone() {
    return new Tile(c, n, e, s, w, b, cityEnder);
  }

  /**
   ** Effectue une rotation de la tuile, dans le sens contraire des aiguilles d'une
   * montre
   */
  public void turnCounterClock() {
    Type tmp = n;
    n = e;
    e = s;
    s = w;
    w = tmp;
  }

  /**
   ** Effectue une rotation de la tuile, dans le sens des aiguilles d'une montre
   */
  public void turnClock() {
    Type tmp = n;
    n = w;
    w = s;
    s = e;
    e = tmp;
  }

  /**
   ** Retourne vraie si la tuile possède une ou plusieurs routes
   *
   * @return
   */
  public boolean hasRoad() {
    return n == Type.ROAD || w == Type.ROAD || e == Type.ROAD || s == Type.ROAD;
  }

  /**
   ** Retourne vraie si la tuile possède une ou plusieurs partie(s) de villes
   *
   * @return
   */
  public boolean hasCity() {
    return n == Type.CITY || w == Type.CITY || e == Type.CITY || s == Type.CITY || c == Type.CITY;
  }

  /**
   ** Retourne un boolean si l'on peut connecter la tuile avec la tuile t
   ** sur avec le bords de coté side
   *
   * @param t    la tuile avec laquelle on veut la connecter
   * @param side le coté avec laquelle la tuile va se connecter : "n", "s", "e" ou
   *             "w"
   *             pour respectivement Nord, Sud, Est et Ouest
   * @return boolean vraie si la connection est possible, faux sinon
   */
  public boolean canConnect(Tile t, String side) {
    if (t == null)
      return true;
    switch (side) {
      case "n":
        Configuration
            .instance()
            .logger()
            .info(
                "Connexion north entre la tuile : " +
                    toString() +
                    " et " +
                    t.toString() +
                    " == " +
                    (n == t.south()));
        return n == t.south();
      case "e":
        Configuration
            .instance()
            .logger()
            .info(
                "Connexion east entre la tuile : " +
                    toString() +
                    " et " +
                    t.toString() +
                    " == " +
                    (e == t.west()));
        return e == t.west();
      case "s":
        Configuration
            .instance()
            .logger()
            .info(
                "Connexion south entre la tuile : " +
                    toString() +
                    " et " +
                    t.toString() +
                    " == " +
                    (s == t.north()));
        return s == t.north();
      case "w":
        Configuration
            .instance()
            .logger()
            .info(
                "Connexion west entre la tuile : " +
                    toString() +
                    " et " +
                    t.toString() +
                    " == " +
                    (w == t.east()));
        return w == t.east();
      default:
        return false;
    }
  }

  /**
   ** Retourne une chaine de caractère décrivant la tuile
   */
  public String toString() {
    return (c.toString() +
        " -> north = " +
        n.toString() +
        ", south = " +
        s.toString() +
        ", east = " +
        e.toString() +
        ", ouest = " +
        w.toString() +
        ", avec blason = " +
        b +
        ", ender = " +
        cityEnder);
  }

  /**
   ** Retourne une chaine de caractère permettant d'afficher une tuile
   *
   * @return la valeur de retour est de type :
   *         $ +-------+
   *         $ | C |
   *         $ | R S R |
   *         $ | L |
   *         $ +-------+
   */
  public String[] toStringArray() {
    String[] str = new String[3];
    StringBuilder sb = new StringBuilder();
    if (cityEnder)
      sb.append("e ");
    else
      sb.append("  ");

    sb.append(n.toOneCharString());

    if (b)
      str[0] = sb.append(" b").toString();
    else
      str[0] = sb.append("  ").toString();

    str[1] = w.toOneCharString() +
        " " +
        c.toOneCharString() +
        " " +
        e.toOneCharString();
    str[2] = "  " + s.toOneCharString() + "  ";
    return str;
  }

  /**
   ** Retourne un booléen vraie si les deux tuiles sont identiques
   *
   * @param t Tile avec laquelle tester l'égalité
   * @return vraie si la tuile est égale à la tuile t
   */
  public boolean equalsTo(Tile t) {
    return c == t.center() && n == t.north() && e == t.east() && s == t.south() && w == t.west() && b == t.blason()
        && cityEnder == t.cityEnder() && isStart == t.isStartTile();
  }

  /**
   ** Crée et retourne la tuile de départ
   *
   * @return Tile
   */
  public static Tile getStartTile() {
    Tile start = new Tile(
        Type.ROAD,
        Type.CITY,
        Type.ROAD,
        Type.FIELD,
        Type.ROAD,
        false,
        true);
    start.setToStart();
    return start;
  }

  /**
   ** Retourne la défintion binaire de la tuile pour une sauvegarde
   *
   * @return List<Byte>
   */
  public List<Byte> toByteArray() {
    return Arrays.asList(c.toByte(), n.toByte(), e.toByte(), s.toByte(), w.toByte(),
        (byte) (b ? 1 : 0), (byte) (cityEnder ? 1 : 0), (byte) (isStart ? 1 : 0));
  }

  /**
   ** Créer et retourne une tuile correspondant à son numéro
   *
   * @param i int
   * @return Tile
   */
  public static Tile getTileFromInt(int i) {
    switch (i) {
      case 0:
        return new Tile(
            Type.ABBEY,
            Type.FIELD,
            Type.FIELD,
            Type.FIELD,
            Type.FIELD,
            false,
            false);
      case 1:
        return new Tile(
            Type.ABBEY,
            Type.FIELD,
            Type.FIELD,
            Type.ROAD,
            Type.FIELD,
            false,
            false);
      case 2:
        return new Tile(
            Type.CITY,
            Type.CITY,
            Type.CITY,
            Type.CITY,
            Type.CITY,
            true,
            false);
      case 3:
        return new Tile(
            Type.CITY,
            Type.CITY,
            Type.CITY,
            Type.FIELD,
            Type.CITY,
            false,
            false);
      case 4:
        return new Tile(
            Type.CITY,
            Type.CITY,
            Type.CITY,
            Type.FIELD,
            Type.CITY,
            true,
            false);
      case 5:
        return new Tile(
            Type.CITY,
            Type.CITY,
            Type.CITY,
            Type.ROAD,
            Type.CITY,
            false,
            false);
      case 6:
        return new Tile(
            Type.CITY,
            Type.CITY,
            Type.CITY,
            Type.FIELD,
            Type.CITY,
            true,
            false);
      case 7:
        return new Tile(
            Type.FIELD,
            Type.CITY,
            Type.CITY,
            Type.FIELD,
            Type.FIELD,
            false,
            false);
      case 8:
        return new Tile(
            Type.FIELD,
            Type.CITY,
            Type.CITY,
            Type.FIELD,
            Type.FIELD,
            true,
            false);
      case 9:
        return new Tile(
            Type.ROAD,
            Type.CITY,
            Type.CITY,
            Type.ROAD,
            Type.ROAD,
            false,
            false);
      case 10:
        return new Tile(
            Type.ROAD,
            Type.CITY,
            Type.CITY,
            Type.ROAD,
            Type.ROAD,
            true,
            false);
      case 11:
        return new Tile(
            Type.CITY,
            Type.FIELD,
            Type.CITY,
            Type.FIELD,
            Type.CITY,
            false,
            false);
      case 12:
        return new Tile(
            Type.CITY,
            Type.FIELD,
            Type.CITY,
            Type.FIELD,
            Type.CITY,
            true,
            false);
      case 13:
        return new Tile(
            Type.FIELD,
            Type.CITY,
            Type.CITY,
            Type.FIELD,
            Type.FIELD,
            false,
            true);
      case 14:
        return new Tile(
            Type.FIELD,
            Type.CITY,
            Type.FIELD,
            Type.CITY,
            Type.FIELD,
            false,
            true);
      case 15:
        return new Tile(
            Type.FIELD,
            Type.CITY,
            Type.FIELD,
            Type.FIELD,
            Type.FIELD,
            false,
            true);
      case 16:
        return new Tile(
            Type.ROAD,
            Type.CITY,
            Type.ROAD,
            Type.ROAD,
            Type.FIELD,
            false,
            true);
      case 17:
        return new Tile(
            Type.ROAD,
            Type.CITY,
            Type.ROAD,
            Type.ROAD,
            Type.FIELD,
            false,
            true);
      case 18:
        return new Tile(
            Type.TOWN,
            Type.CITY,
            Type.ROAD,
            Type.ROAD,
            Type.ROAD,
            false,
            true);
      case 19:
        return new Tile(
            Type.ROAD,
            Type.CITY,
            Type.ROAD,
            Type.FIELD,
            Type.ROAD,
            false,
            true);
      case 20:
        return new Tile(
            Type.ROAD,
            Type.ROAD,
            Type.FIELD,
            Type.ROAD,
            Type.FIELD,
            false,
            false);
      case 21:
        return new Tile(
            Type.ROAD,
            Type.FIELD,
            Type.ROAD,
            Type.ROAD,
            Type.FIELD,
            false,
            false);
      case 22:
        return new Tile(
            Type.TOWN,
            Type.FIELD,
            Type.ROAD,
            Type.ROAD,
            Type.ROAD,
            false,
            false);
      case 23:
        return new Tile(
            Type.TOWN,
            Type.ROAD,
            Type.ROAD,
            Type.ROAD,
            Type.ROAD,
            false,
            false);
      default:
        return null;
    }
  }
}
