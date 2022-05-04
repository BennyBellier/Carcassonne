package model;

public enum TileType {
  START,
  TOWN,
  CITY_OPEN,
  ROAD,
  ABBEY,
  LAND;

  /**
   * Retourne une chaine de caractère contenant qu'une lettre pour décrire la tuile
   * @return String
   */
  public String toOneCharString() {
    switch (this) {
      case START:
        return "S";
      case TOWN:
        return "T";
      case CITY_OPEN:
        return "C";
      case ROAD:
        return "R";
      case ABBEY:
        return "A";
      case LAND:
        return "L";
      default:
        return "";
    }
  }

  /**
   * Retourne une chaine de caractère retournant le type de tuile
   */
  public String toString() {
    switch (this) {
      case START:
        return "START";
      case TOWN:
        return "TOWN";
      case CITY_OPEN:
        return "CITY_OPEN";
      case ROAD:
        return "ROAD";
      case ABBEY:
        return "ABBEY";
      case LAND:
        return "LAND";
      default:
        return "";
    }
  }
}
