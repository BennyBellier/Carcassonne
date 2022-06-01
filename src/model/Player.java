package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.*;

import java.awt.Color;

public class Player {

  public enum Type {
    HUMAN,
    IA_EASY,
    IA_MEDIUM,
    IA_HARD;

    public byte toByte() {
      switch (this) {
        case HUMAN:
          return (byte)0;
        case IA_EASY:
          return (byte)1;
        case IA_MEDIUM:
          return (byte)2;
        case IA_HARD:
          return (byte)3;

          default:
          return (byte)-1;
      }
    }

    public static Type fromByte(byte b) {
      switch (b) {
        case 0:
          return HUMAN;
        case 1:
          return IA_EASY;
        case 2:
          return IA_MEDIUM;
        case 3:
          return IA_HARD;


        default:
          return null;
      }
    }

    public String toString() {
      switch (this) {
        case HUMAN:
          return "Humain";
        case IA_EASY:
          return "IA EASY";
        case IA_MEDIUM:
          return "IA MEDIUM";
        case IA_HARD:
          return "IA HARD";

        default:
          return "";
      }
    }
  }

  private String pseudo;
  private Type type;
  private int meeplesNumber, score, nbTilePlaced, curNumberOfProject, numberOfProjects;
  private Color color;
  private IA ia;


  public Player(String name, Type type, Color color) {
    pseudo = name;
    this.type = type;
    meeplesNumber = 7;
    score = 0;
    nbTilePlaced = 0;
    curNumberOfProject = 0;
    numberOfProjects = 0;
    this.color = color;
    if (type == Type.IA_EASY)
      ia = new IAEasy();
    else if (type == Type.IA_MEDIUM)
      ia = new IAMoyen();
  }

  public Player(byte[] b, String pseudo) {
    this.pseudo = pseudo;
    type = Type.fromByte(b[0]);
    meeplesNumber = b[1];
    score = b[2];
    curNumberOfProject = b[3];
    numberOfProjects = b[4];
    nbTilePlaced = b[5];
    color = new Color((int) b[6] & 0xFF, (int) b[7] & 0xFF, (int) b[8] & 0xFF);
    if (type == Type.IA_EASY)
      ia = new IAEasy();
    else if (type == Type.IA_MEDIUM)
      ia = new IAMoyen();
  }

  @Override
  public Player clone() {
    Player nwPlayer = new Player(pseudo, type, new Color(color.getRGB()));
    nwPlayer.setCurNbProject(curNumberOfProject);
    nwPlayer.setMeeplesNumber(meeplesNumber);
    nwPlayer.setNbProject(numberOfProjects);
    nwPlayer.setNbTilePlaced(nbTilePlaced);
    nwPlayer.setScore(score);
    return nwPlayer;
  }

  @Override
  public String toString() {
    return type.toString();
  }

  /**
   ** Retourne l'object IA du Player
   * @return
   */
  public IA getIA() {
    return ia;
  }

  /**
   ** Retourne le pseudo du joueur
   * @return String
   */
  public String pseudo() {
    return pseudo;
  }

  /**
   ** Retourne la couleur du player
   * @return
   */
  public Color color() {
    return color;
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
   ** Retourne vraie si le player peut utiliser une IA
   * @return
   */
  public boolean canUseMeeple() {
    return meeplesNumber > 0;
  }

  /**
   ** Retourne vraie si le Player est une IA
   * @return
   */
  public boolean isIA() {
    return type != Type.HUMAN;
  }

  /**
   ** Utilise un meeple du joueur et retourne vraie si c'est possible, faux sinon
   * <p>
   * Réduit de 1 le nombre de meeple du joueur<br/>
   * Augmente de 1 le nombre de projet courant et global du joueur
   * @return booléen vraie si le joueur peut utiliser un meeple, faux sinon
   */
  public void meepleUse() {
    if (meeplesNumber > 0) {
      meeplesNumber -= 1;
      curNumberOfProject += 1;
      numberOfProjects += 1;
    }
  }

  /**
   ** Met à jour le nombre de meeple disponible du Player
   * @param nb
   */
  public void setMeeplesNumber(int nb) {
    meeplesNumber = nb;
  }

  /**
   ** Met à jour le score disponible du Player
   * @param nb
   */
  public void setScore(int score) {
    this.score = score;
  }

  /**
   ** Met à jour le nombre courant de projet disponible du Player
   * @param nb
   */
  public void setCurNbProject(int nb) {
    curNumberOfProject = nb;
  }

  /**
   ** Met à jour le nombre de projet total disponible du Player
   * @param nb
   */
  public void setNbProject(int nb) {
    numberOfProjects = nb;
  }

  /**
   ** Met à jour le nombre de tuiles placé disponible du Player
   * @param nb
   */
  public void setNbTilePlaced(int nb) {
    nbTilePlaced = nb;
  }

  /**
   ** Le joueur récupère un meeple
   */
  public void meepleRecovery() {
    meeplesNumber += 1;
  }

  /**
   ** Le nombre courant de projet du joueur est réduit de 1
   */
  public void minusCurrentNumberProjects() {
    curNumberOfProject -= 1;
  }

  /**
   ** Retourne la définition binaire du joueur pour une sauvegarde
   * @return List<Byte>
   */
  public List<Byte> toByteArray() {
    return new ArrayList<>(Arrays.asList(type.toByte(), (byte) meeplesNumber, (byte) score, (byte) curNumberOfProject, (byte) numberOfProjects, (byte) nbTilePlaced, (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue()));
  }
}
