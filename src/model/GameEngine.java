package model;

import java.util.List;
import java.util.ArrayList;
import java.awt.Point;

import global.Configuration;
import model.Player.Type;
import model.Projects.Project;

public class GameEngine {
  private GameSet gameSet;
  private Pioche pioche;
  private List<Player> players;
  private List<Meeple> meeplesOnSet;
  private int playerTurn, nbPlayer;
  private CurrentTile currentTile;
  private int x = -1, y = -1;

  public GameEngine(Player... playersIn) {
    gameSet = new GameSet();
    pioche = new Pioche();
    piocheTuile();
    nbPlayer = playersIn.length;
    players = new ArrayList<>(nbPlayer);
    meeplesOnSet = new ArrayList<>();
    for (Player p : playersIn) {
      players.add(p);
    }
    Configuration.instance().logger().fine("Création de l'objet GameEngine avec " + playersIn.length + " joueurs");
  }

  /**
   ** Renvoie un reset de la partie en cours
   * @return GameEngine
   */
  public GameEngine reset() {
    Configuration.instance().logger().info("Remise à zéro de la partie en cours");
    return new GameEngine(players.stream().toArray(Player[]::new));
  }

  /**
   ** Retourne une copy du plateau courant
   * @return Tile[][]
   */
  public Tile[][] getSet() {
    return gameSet.cloneSet();
  }

  /**
   ** Retourne la tuile courante
   * @return CurrentTile
   */
  public CurrentTile getCurrentTile() {
    return currentTile;
  }

  /**
   ** Retourne la taille courante de la pioche
   * @return int
   */
  public int getPiocheSize() {
    return pioche.size();
  }

  /**
   ** Retourne le nombre de joueur
   * @return int
   */
  public int getNbPlayer() {
    return nbPlayer;
  }

  /**
   ** Retourne la liste des joueurs
   * @return List<Players>
   */
  public List<Player> getListPlayers() {
    return players;
  }

  /**
   ** Effectue l'action du clic en fonction de l'état courant du tour du joueur
   * @param x position x de la tuile à placer ou du meeple
   * @param y position y de la tuile à placer ou du meeple
   * @param card cardinalité du meeple à placer
   * @return boolean vraie si la pose de la tuile ou du meeple a été effectué, faux sinon
   */
  public boolean clic(int x, int y, String card) {
    if (players.get(playerTurn).type() == Type.HUMAN) {
      if (!currentTile.placed) {
        Point start = gameSet.getStartTilePoint();
        if (gameSet.addTile(currentTile.tile, x - start.x, y - start.y)) {
          currentTile.placed();
          currentTile.x = x - start.x;
          currentTile.y = y - start.y;
          return true;
        }
        return false;
      } else {
        Point start = gameSet.getStartTilePoint();
        if (placeMeeple(new Meeple(playerTurn, y - start.y, x - start.x, card))) {
          endOfTurn();
          return true;
        }
      }
    }
    return false;
  }

  /**
   ** Renvoie vraie si le joueur peut terminer son tour
   * @return boolean
   */
  public boolean canEndTurn() {
    return currentTile.placed;
  }

  /**
   ** Tourne la tuile courante d'un quart de tour vers la droite
   */
  public void turnCurrentTile() {
    currentTile.tile.turnClock();
  }

  /**
   ** Retourne le Point contenant la position de la tuile de départ
   * @return Point
   */
  public Point getStartTilePoint() {
    return gameSet.getStartTilePoint();
  }

  /**
   ** Retoune l'entier correspondant aua joueur entrain de jouer son tour
   * @return int
   */
  public int getPlayerTurn() {
    return playerTurn;
  }

  /**
   ** Récupère une tuile dans la pioche, si elle n'est pas posable alors elle est remisé est un nouvelle tuile est pioché
   */
  void piocheTuile() {
    if (!pioche.isEmpty()) {
      currentTile = new CurrentTile(pioche.piocheTuile());
      currentTile.unplaced();

      while (gameSet.tilePositionsAllowed(currentTile.tile, true).size() == 0) {
        pioche.remiserTuile(currentTile.tile);
        currentTile.tile =  pioche.piocheTuile();
      }
      Configuration.instance().logger().fine("Tuile pioché : " + currentTile.toString());
    }
  }

  /**
   ** Passe au joueur suivant
   */
  void nextPlayer() {
    playerTurn = (playerTurn + 1) % nbPlayer;
    Configuration.instance().logger().info("Au tour du joueur " + playerTurn + " : " + players.get(playerTurn));
  }

  /**
   ** Reviens au joeuer précédent (annulation de coup)
   */
  void prevPlayer() {
    playerTurn = (playerTurn - 1) % nbPlayer;
    Configuration.instance().logger().info("Au tour du joueur " + playerTurn + " : " + players.get(playerTurn));
  }

  /**
   ** Remise à zéro des valeurs, récupération d'une nouvelle tuile,
   ** et passeage au joueur suivant
   */
  public void endOfTurn() {
    x = -1;
    y = -1;
    nextPlayer();
    piocheTuile();
    Configuration.instance().logger().finer("Fin du tour du joueur " + playerTurn + " : " + players.get(playerTurn));
  }

  /**
   ** Place un meeple (si possible) du joueur dont c'est le tour
   * @param m meeple définie à placer
   * @return vraie si le placement à eu lieu
   */
  public boolean placeMeeple(Meeple m) {
    if (players.get(playerTurn).canUseMeeple() && gameSet.meeplePlacementAllowed(m)) {
      players.get(playerTurn).meepleUse();
      meeplesOnSet.add(m);
      Configuration.instance().logger().info(players.get(playerTurn).pseudo() + " à poser un meeple sur la case (" + m.getX() + ", " + m.getY() + ") " + m.getCardinal());
      return true;
    }

    return false;
  }

  /**
   ** Place une tuile sur le plateau à al position (x, y), retourne vraie si possible, faux sinon
   * @param x int position x de la tuile à placer
   * @param y int position y de la tuile à placer
   * @return vraie si la tuile a pu être poser, faux sinon
   */
  public boolean placeTile(int x, int y) {
    if (gameSet.addTile(currentTile.tile, x, y)) {
      this.x = x;
      this.y = y;
      Configuration.instance().logger().info(players.get(playerTurn) + " à poser une tuile sur la case (" + x + " ," + y + ")");
      return true;
    }
    return false;
  }

  /**
   ** Supprime du plateau la tuile que le joueur à poser
   * @return vraie si la tuile à était enlevé, faux sinon
   */
  public boolean removeTile() {
    Configuration.instance().logger()
        .info("La tuile sur la case (" + x + " ," + y + ") a était enlevé");
    if (gameSet.removeTile(x, y)) {
      currentTile.unplaced();
      return true;
    }
    return false;
  }

  /**
   ** Retourne la liste des meeples sur le plateau à l'instant T
   * @return List<Meeple>
   */
  public List<Meeple> getMeeplesOnSet() {
    return meeplesOnSet;
  }

  /*
   * Pour tout les projets finie sur le plateau, détermine le propriétaire du projet,
   * enlève les meeples du plateau et attribut les points au propriétaire
   */
  void projectsEvaluation() {
    List<Project> projects = Project.evaluateProjects(gameSet.cloneSet());

    for (Project project : projects) {
      int[] ownersValue = new int[nbPlayer];
      for (Meeple m : meeplesOnSet) {
        if (project.type() == gameSet.getProjectType(m.getX(), m.getY(), m.getCardinal())
            && project.graph().hasNode(gameSet.getTileFromCoord(m.getX(), m.getY()))) {
          ownersValue[m.player] += 1;
          meeplesOnSet.remove(m);
          players.get(m.player).meepleRecovery();
        }
      }

      int owner = 0, ownerValue = 0;

      for (int i = 0; i < ownersValue.length; i++) {
        if (ownersValue[i] > ownerValue) {
          ownerValue = ownersValue[i];
          owner = i;
        }
      }
      Configuration.instance().logger().fine(players.get(owner) + " est propiétaire du projet " + project.type() + "à la case" );
      players.get(owner).scorePlus(project.value());
    }
  }

  /**
   *$ Gestion des sauvegardes
   */
}
