package model;


public class Player {

  public enum Type {
    HUMAN,
    IA;

    public String toString() {
      switch (this) {
        case HUMAN:
          return "Humain";
        case IA:
          return "IA";

        default:
          return "";
      }
    }
  }

  private String pseudo;
  private Type type;
  private int meeplesNumber, score, nbTilePlaced, curNumberOfProject, numberOfProjects;

  public Player(String name, Type type) {
    pseudo = name;
    this.type = type;
    meeplesNumber = 7;
    score = 0;
    nbTilePlaced = 0;
    curNumberOfProject = 0;
    numberOfProjects = 0;
  }

  /**
   ** Retourne le pseudo du joueur
   * @return String
   */
  public String Pseudo() {
    return pseudo;
  }

  /**
   ** Retourne le type de joueur humain ou ia
   * @return Type
   */
  public Type type() {
    return type;
  }

  /**
   ** Retourne le nombre de meeples restant au joueur
   * @return int
   */
  public int nbMeeplesRestant() {
    return meeplesNumber;
  }

  /**
   ** Retourne le score courant du joueur
   * @return int
   */
  public int score() {
    return score;
  }

  /**
   ** Ajout value au score du joueur
   * @param value int
   */
  public void scorePlus(int value) {
    score += value;
  }

  /**
   ** Retire value au score du joueur
   * @param value int
   */
  public void scoreMinus(int value) {
    score -= value;
  }

  /**
   ** Retourne le nombre de tuiles placés par le joueur durant partie
   * @return
   */
  public int nbTilePlaced() {
    return nbTilePlaced;
  }

  /**
   ** Ajoute 1 au nombre de tuiles placés par le joueurs durant la partie
   */
  public void TileplacedPlus() {
    nbTilePlaced += 1;
  }

  /**
   ** Retire 1 au nombre de tuiles placés par le joueuer durant la partie
   */
  public void TilePlacedMinus() {
    nbTilePlaced -= 1;
  }

  /**
   ** Retourne le nombre courant de projets du joueur
   * @return int
   */
  public int currentNbProject() {
    return curNumberOfProject;
  }

  /**
   ** Retourne le nombre de projets que le joueur à finie ou non durant la partie
   * @return int
   */
  public int numberOfProjects() {
    return numberOfProjects;
  }

  /**
   ** Utilise un meeple du joueur et retourne vraie si c'est possible, faux sinon
   * <p>
   * Réduit de 1 le nombre de meeple du joueur<br/>
   * Augmente de 1 le nombre de projet courant et global du joueur
   * @return booléen vraie si le joueur peut utiliser un meeple, faux sinon
   */
  public boolean meepleUse() {
    if (meeplesNumber > 0) {
      meeplesNumber -= 1;
      curNumberOfProject += 1;
      numberOfProjects += 1;
      return true;
    }
    return false;
  }

  /**
   ** Le joueur récupère un meeple, le nombre de projet courant est réduit de 1 puisque le projet est finie
   */
  public void meepleRecovery() {
    meeplesNumber += 1;
    curNumberOfProject -= 1;
  }
}
