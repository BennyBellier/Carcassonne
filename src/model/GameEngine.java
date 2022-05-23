package model;

import java.util.List;
import java.util.Map;

import controller.Controleur;

import java.util.ArrayList;
import java.util.Collections;
import java.awt.Point;

import controller.IA;
import global.Configuration;
import model.Player.Type;
import model.Projects.Project;
import model.Projects.TileOfProject;

public class GameEngine {
  private GameSet gameSet;
  private Pioche pioche;
  private List<Player> players;
  private List<Meeple> meeplesOnSet;
  private List<Project> projectsEvaluate;
  private int playerTurn, nbPlayer;
  private CurrentTile currentTile;
  private boolean gameEnded;
  private Controleur control;

  public GameEngine(Player... playersIn) {
    gameSet = new GameSet();
    pioche = new Pioche();
    projectsEvaluate = new ArrayList<>();
    piocheTuile();
    nbPlayer = playersIn.length;
    players = new ArrayList<>(nbPlayer);
    meeplesOnSet = new ArrayList<>();
    gameEnded = false;
    for (Player p : playersIn) {
      players.add(p);
    }
    Configuration.instance().logger().fine("Création de l'objet GameEngine avec " + playersIn.length + " joueurs");
  }

  public void setControleur(Controleur c) {
    control = c;
  }

  public boolean isGameRunning() {
    return !gameEnded;
  }

  /**
   ** Renvoie un reset de la partie en cours
   *
   * @return GameEngine
   */
  public GameEngine reset() {
    Configuration.instance().logger().info("Remise à zéro de la partie en cours");
    return new GameEngine(players.stream().toArray(Player[]::new));
  }

  /**
   ** Retourne une copy du plateau courant
   *
   * @return Tile[][]
   */
  public Tile[][] getSet() {
    return gameSet.cloneSet();
  }

  /**
   ** Retourne la tuile courante
   *
   * @return CurrentTile
   */
  public CurrentTile getCurrentTile() {
    return currentTile;
  }

  /**
   ** Retourne la taille courante de la pioche
   *
   * @return int
   */
  public int getPiocheSize() {
    return pioche.size();
  }

  /**
   ** Retourne le nombre de joueur
   *
   * @return int
   */
  public int getNbPlayer() {
    return nbPlayer;
  }

  /**
   ** Retourne la liste des joueurs
   *
   * @return List<Players>
   */
  public List<Player> getListPlayers() {
    return players;
  }

  public GameSet getGameSet() {
    return gameSet;
  }

  /**
   ** Effectue l'action du clic en fonction de l'état courant du tour du joueur
   *
   * @param x    position x de la tuile à placer ou du meeple
   * @param y    position y de la tuile à placer ou du meeple
   * @param card cardinalité du meeple à placer
   * @return boolean vraie si la pose de la tuile ou du meeple a été effectué,
   *         faux sinon
   */
  public boolean clic(int x, int y, String card) {
    if (players.get(playerTurn).type() == Type.HUMAN) {
      if (!currentTile.placed) {
        Point start = gameSet.getStartTilePoint();
        if (gameSet.addTile(currentTile.tile, x - start.x, y - start.y)) {
          currentTile.x = x - start.x;
          currentTile.y = y - start.y;
          currentTile.placed();

          if (players.get(playerTurn).nbMeeplesRestant() == 0 || getMeeplePositions().size() == 0) {
            endOfTurn();
          }
          return true;
        }
        return false;
      } else {
        Point start = gameSet.getStartTilePoint();
        if (x - start.x == currentTile.x && y - start.y == currentTile.y) {
          if (placeMeeple(new Meeple(playerTurn, y - start.y, x - start.x, card))) {
            endOfTurn();
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean IAPlaceTile() {
    IA ia = players.get(playerTurn).getIA();
    Point start = gameSet.getStartTilePoint();

    // placement tuile
    int[] pos = ia.placeTile(gameSet, currentTile.tile);
    Configuration.instance().logger().info(players.get(playerTurn).pseudo() + " place la tuile en (" + (pos[0] - start.y) + ", " + (pos[1] - start.x) + ")");
    if (gameSet.addTile(currentTile.tile, pos[0] - start.x, pos[1] - start.y)) {
      currentTile.x = pos[0] - start.x;
      currentTile.y = pos[1] - start.y;
      currentTile.placed();
      return true;
    }
    return false;
  }

  public void IAPlaceMeeple() {
    if (players.get(playerTurn).nbMeeplesRestant() == 0 || getMeeplePositions().size() == 0) {
      endOfTurn();
      return;
    }

    IA ia = players.get(playerTurn).getIA();
    // placement du meeple
    String card = ia.placeMeeple(currentTile.tile.getMeeplesPosition());

    if (card == null)
      endOfTurn();
    else if (placeMeeple(new Meeple(playerTurn, currentTile.y, currentTile.x, card)))
      endOfTurn();
  }

  /**
   ** Renvoie vraie si le joueur peut terminer son tour
   *
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
   *
   * @return Point
   */
  public Point getStartTilePoint() {
    return gameSet.getStartTilePoint();
  }

  /**
   ** Retoune l'entier correspondant aua joueur entrain de jouer son tour
   *
   * @return int
   */
  public int getPlayerTurn() {
    return playerTurn;
  }

  /**
   ** Récupère une tuile dans la pioche, si elle n'est pas posable alors elle est
   * remisé est un nouvelle tuile est pioché
   */
  void piocheTuile() {
    if (pioche.isEmpty()) {
      currentTile = null;
      gameEnded = true;
      control.finDeGame();
    }

    while (!pioche.isEmpty()) {
      currentTile = new CurrentTile(pioche.piocheTuile());
      currentTile.unplaced();

      if (gameSet.tilePositionsAllowed(currentTile.tile, true).size() == 0)
        currentTile = null;
      else
        break;
    }

    if (currentTile == null) {
      gameEnded = true;
      control.finDeGame();
    }

    Configuration.instance().logger().fine("Tuile pioché : " + currentTile.toString());
  }

  /**
   ** Passe au joueur suivant
   */
  void nextPlayer() {
    playerTurn = (playerTurn + 1) % nbPlayer;
    Configuration.instance().logger().fine("Au tour du joueur " + playerTurn + " : " + players.get(playerTurn));
  }

  /**
   ** Reviens au joeuer précédent (annulation de coup)
   */
  void prevPlayer() {
    playerTurn = (playerTurn - 1) % nbPlayer;
    Configuration.instance().logger().fine("Au tour du joueur " + playerTurn + " : " + players.get(playerTurn));
  }

  public boolean isIATurn() {
    return players.get(playerTurn).isIA();
  }

  public IA getIATurn() {
    return players.get(playerTurn).getIA();
  }

  /**
   ** Remise à zéro des valeurs, récupération d'une nouvelle tuile,
   ** et passeage au joueur suivant
   */
  public void endOfTurn() {
    projectsEvaluation();
    nextPlayer();
    piocheTuile();
    Configuration.instance().logger().finer("Fin du tour du joueur " + playerTurn + " : " + players.get(playerTurn));
  }

  public Map<Integer, ArrayList<Integer>> getCurrentTilePositions() {
    return gameSet.tilePositionsAllowed(currentTile.tile, false);
  }

  /**
   ** Retourne le Tile.Type du meeple m
   *
   * @param m Meeple
   * @return Tile.Type
   */
  Tile.Type projectOf(Meeple m) {
    return gameSet.getCardType(m.getX() + gameSet.getStartTilePoint().x, m.getY() + gameSet.getStartTilePoint().y,
        m.getCardinal());
  }

  /**
   ** Retourne vraie si le meeple (x, y, card) peut être placé en respectant les
   ** règles du jeu
   *
   * @param visited
   * @param x
   * @param y
   * @param card
   * @param type
   * @param start
   * @return
   */
  boolean allowMeeple(List<Tile> visited, int x, int y, String card, Tile.Type type, Point start) {
    Tile t;
    boolean ender = false;

    if ((t = gameSet.getTileFromCoord(y + start.y, x + start.x)) == null) {
      return true;
    }

    if (t.getCardinalType(card) != type)
      return true;

    if (!visited.contains(t)) {
      visited.add(t);

      if (isMeepleOnTile(x, y, card, ender, type, start)) {
        return false;
      }

      if (type == Tile.Type.CITY)
        ender = t.cityEnder();
      if (type == Tile.Type.ROAD)
        ender = t.roadEnder();

      if (ender) {
        switch (card) {
          case "n":
            if (t.north() == type && allowMeeple(visited, x, y - 1, "s", type, start)) {
              return true;
            } else {
              return false;
            }
          case "s":
            if (t.south() == type && allowMeeple(visited, x, y + 1, "n", type, start)) {
              return true;
            } else {
              return false;
            }
          case "e":
            if (t.east() == type && allowMeeple(visited, x + 1, y, "w", type, start)) {
              return true;
            } else {
              return false;
            }
          case "w":
            if (t.west() == type && allowMeeple(visited, x - 1, y, "e", type, start)) {
              return true;
            } else {
              return false;
            }
        }
      } else {
        boolean n = true, s = true, e = true, w = true;
        if (t.north() == type) {
          if (t.north() == type)
            n = allowMeeple(visited, x, y - 1, "s", type, start);
        }
        if (t.south() == type) {
          if (t.south() == type)
            s = allowMeeple(visited, x, y + 1, "n", type, start);
        }
        if (t.east() == type) {
          if (t.east() == type)
            e = allowMeeple(visited, x + 1, y, "w", type, start);
        }
        if (t.west() == type) {
          if (t.west() == type)
            w = allowMeeple(visited, x - 1, y, "e", type, start);
        }
        return (n && s && e && w);
      }
    }
    return !isMeepleOnTile(x, y, card, ender, type, start);
  }

  /**
   ** Retourne vraie si il y a un meeple qui est placé n'importe ou sur la tuile
   ** (x, y) ou si il est placé sur la cardinalité de la tuile (x, y, card) si
   ** celle-ci est une fin de route ou de ville
   *
   * @param x
   * @param y
   * @param card
   * @param ender si la tuile est de type fin de route ou ville
   * @param type  type ville ou route
   * @param start emplacement de la tuile de départ sur le plateau
   * @return vraie si il y a un meeple, faus sinon
   */
  boolean isMeepleOnTile(int x, int y, String card, boolean ender, Tile.Type type, Point start) {
    if (gameSet.getTileFromCoord(y + start.y, x + start.x) == null)
      return true;

    for (Meeple m : meeplesOnSet) {
      if (x == m.getY() && y == m.getX()) {
        if (!ender) {
          if (projectOf(m) == type)
            return true;
          else
            return false;
        } else if (m.getCardinal() == card)
          return true;
      }
    }
    return false;
  }

  /**
   ** Retourne vraie si le meeple peut être posé
   *
   * @param m
   * @return
   */
  boolean meeplePlacementAllowed(Meeple m) {
    if (meeplesOnSet.size() == 0)
      return true;

    Point start = gameSet.getStartTilePoint();
    Project.Type p1 = gameSet.getProjectType(m.getX() + start.x, m.getY() + start.y, m.getCardinal());
    switch (p1) {
      case CITY:
        return allowMeeple(new ArrayList<>(), m.getY(), m.getX(), m.getCardinal(), Tile.Type.CITY, start);

      case ROAD:
        return allowMeeple(new ArrayList<>(), m.getY(), m.getX(), m.getCardinal(), Tile.Type.ROAD, start);

      default:
        return true;
    }
  }

  /**
   ** Retourne la liste des positions possibles du meeple sur la tuile courante
   *
   * @return List<String>
   */
  public List<String> getMeeplePositions() {
    List<String> res = new ArrayList<>();
    if (meeplesOnSet.size() == 0) {
      res.addAll(currentTile.tile.getMeeplesPosition());
      return res;
    }
    if (players.get(playerTurn).nbMeeplesRestant() > 0) { // vérification si le joueurs à des meeples disponibles

      Point start = gameSet.getStartTilePoint();

      for (String pos : currentTile.tile.getMeeplesPosition()) {
        switch (currentTile.tile.getCardinalType(pos)) {
          case CITY:
            if (allowMeeple(new ArrayList<>(), currentTile.x, currentTile.y, pos, Tile.Type.CITY, start))
              res.add(pos);
            break;

          case ROAD:
            if (allowMeeple(new ArrayList<>(), currentTile.x, currentTile.y, pos, Tile.Type.ROAD, start))
              res.add(pos);
            break;

          default:
            res.add(pos);
            break;
        }
      }
    }
    return res;
  }

  /**
   ** Place un meeple (si possible) du joueur dont c'est le tour
   *
   * @param m meeple définie à placer
   * @return vraie si le placement à eu lieu
   */
  boolean placeMeeple(Meeple m) {
    for (Meeple meep : meeplesOnSet) {
      if (m.equal(meep))
        return false;
    }

    if (!meeplePlacementAllowed(m)) {
      return false;
    }

    if (players.get(playerTurn).canUseMeeple() && gameSet.meeplePlacementAllowed(m)) {
      players.get(playerTurn).meepleUse();
      meeplesOnSet.add(m);
      Configuration.instance().logger().info(players.get(playerTurn).pseudo() + " à poser un meeple sur la case ("
          + m.getX() + ", " + m.getY() + ") " + m.getCardinal());
      return true;
    }

    return false;
  }

  /**
   ** Place une tuile sur le plateau à la position (x, y), retourne vraie si
   ** possible, faux sinon
   *
   * @param x int position x de la tuile à placer
   * @param y int position y de la tuile à placer
   * @return vraie si la tuile a pu être poser, faux sinon
   */
  public boolean placeTile(int x, int y) {
    if (gameSet.addTile(currentTile.tile, x, y)) {
      Configuration.instance().logger()
          .info(players.get(playerTurn) + " à poser une tuile sur la case (" + x + " ," + y + ")");
      return true;
    }
    return false;
  }

  /**
   ** Supprime du plateau la tuile que le joueur à poser
   *
   * @return vraie si la tuile à était enlevé, faux sinon
   */
  public boolean removeTile() {
    if (currentTile.placed) {
      Configuration.instance().logger()
          .info("La tuile sur la case (" + currentTile.x + " ," + currentTile.y + ") a était enlevé");
      if (gameSet.removeTile(currentTile.x + gameSet.getStartTilePoint().x,
          currentTile.y + gameSet.getStartTilePoint().y)) {
        currentTile.unplaced();
        return true;
      }
    }
    return false;
  }

  /**
   ** Retourne la liste des meeples sur le plateau à l'instant T
   *
   * @return List<Meeple>
   */
  public List<Meeple> getMeeplesOnSet() {
    return meeplesOnSet;
  }

  /**
   ** Retourne vraie si le projet à déjà était évalué
   *
   * @param p
   * @return
   */
  boolean projectAlreadyEvaluate(Project p) {
    for (Project project : projectsEvaluate) {
      if (project.equals(p))
        return true;
    }
    return false;
  }

  /**
   ** Retourne vraie si le meeple est sur le projet
   *
   * @param p
   * @param m
   * @return
   */
  boolean meepleOnProject(Project p, Meeple m) {
    Point start = gameSet.getStartTilePoint();
    for (TileOfProject top : p.list()) {
      if (top.x - start.x == m.getY() && top.y - start.y == m.getX() && top.cards.contains(m.getCardinal()))
        return true;
    }
    return false;
  }

  /*
   * Pour tout les projets finie sur le plateau, détermine le propriétaire du
   * projet,
   * enlève les meeples du plateau et attribut les points au propriétaire
   */
  public void projectsEvaluation() {
    List<Project> projects = Project.evaluateProjects(gameSet.cloneSet(), gameEnded);

    for (Project project : projects) {
      List<Integer> ownersValue = new ArrayList<>();
      for (int i = 0; i < players.size(); i++) {
        ownersValue.add(0);
      }
      List<Meeple> meepleToRemove = new ArrayList<>();

      for (Meeple m : meeplesOnSet) {
        if (meepleOnProject(project, m)) {
          ownersValue.add(m.player, ownersValue.get(m.player) + 1);
          meepleToRemove.add(m);
          players.get(m.player).meepleRecovery();
        }
      }

      meeplesOnSet.removeAll(meepleToRemove);

      int i = 1;
      Collections.sort(ownersValue, Collections.reverseOrder());
      if (ownersValue.get(0) != 0) {
        players.get(0).scorePlus(project.value());
        while (ownersValue.get(i) == ownersValue.get(0)) {
          players.get(i).scorePlus(project.value());
          ++i;
        }
      }
      projectsEvaluate.add(project);
    }
  }

  /**
   * $ Gestion des sauvegardes
   */
}
