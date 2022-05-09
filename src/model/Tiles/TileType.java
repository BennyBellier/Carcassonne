package model.Tiles;

public enum TileType {
  START, // tuile de départ
  TOWN, // petit ville au centre d'une seul tuile
  CITY, // ville sur plusieurs tuiles
  ROAD, // route
  ABBEY, // abbey
  FIELD; // prairie

  /**
   ** Retourne une chaine de caractère contenant qu'une lettre pour décrire la tuile
   * @return String
   */
  public String toOneCharString() {
    switch (this) {
      case START:
        return "S";
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
   * @return String
   */
  public String toString() {
    switch (this) {
      case START:
        return "START";
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
