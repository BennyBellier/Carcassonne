package model;

import java.util.List;
import java.util.Map;

import controller.Controleur;

import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Point;

import controller.IA;
import global.Configuration;
import model.Projects.Project;
import model.Projects.TileOfProject;
import model.Projects.Project.Type;

public class GameEngine {
  private GameSet gameSet;
  private Pioche pioche;
  private List<Player> players;
  private List<Meeple> meeplesOnSet;
  private List<Project> projectsEvaluate;
  private int playerTurn, nbPlayer;
  private CurrentTile currentTile;
  private CurrentMeeple currentMeeple;
  private boolean gameEnded;
  private Controleur control;
  private Saver save;

  public GameEngine(Player... playersIn) {
    gameSet = new GameSet();
    pioche = new Pioche();
    projectsEvaluate = new ArrayList<>();
    piocheTuile();
    nbPlayer = playersIn.length;
    players = new ArrayList<>(nbPlayer);
    meeplesOnSet = new ArrayList<>();
    gameEnded = false;
    save = new Saver();
    for (Player p : playersIn) {
      players.add(p);
    }
    Configuration.instance().logger().fine("Création de l'objet GameEngine avec " + playersIn.length + " joueurs");
  }

  public GameEngine(Save s) {
    gameSet = new GameSet(s.set);
    pioche = s.p;
    nbPlayer = s.nbPlayer;
    players = s.players;
    playerTurn = s.playerTurn;
    meeplesOnSet = s.meeples;
    gameEnded = false;
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

  public CurrentMeeple getcurrentMeeple() {
    return currentMeeple;
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

  public boolean IAPlaceTile() {
    IA ia = players.get(playerTurn).getIA();
    Point start = gameSet.getStartTilePoint();

    // placement tuile
    int[] pos = ia.placeTile(gameSet, currentTile.tile);
    Configuration.instance().logger().info(players.get(playerTurn).pseudo() + " place la tuile en ("
        + (pos[0] - start.y) + ", " + (pos[1] - start.x) + ")");
    return placeTile(pos[0], pos[1]);
  }

  public void IAPlaceMeeple() {
    if (players.get(playerTurn).nbMeeplesRestant() == 0 || getMeeplePositions().size() == 0) {
      return;
    }

    IA ia = players.get(playerTurn).getIA();
    // placement du meeple
    String card = ia.placeMeeple(currentTile.tile.getMeeplesPosition());

    if (card != null)
      placeMeeple(card);
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
    currentTile = null;
    currentMeeple = null;

    do {
      currentTile = new CurrentTile(pioche.piocheTuile());
      currentTile.unplaced();
    } while (!pioche.isEmpty() && gameSet.tilePositionsAllowed(currentTile.tile, true).size() == 0);

    if (currentTile.tile == null && pioche.isEmpty()) {
      gameEnded = true;
      return;
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
    if (currentTile.placed) {
      projectsEvaluation();
      if (pioche.isEmpty()) {
        System.out.println("Game Engine fin de game");
        gameEnded = true;
        control.finDeGame();
      } else {
        piocheTuile();
        nextPlayer();
      }
      Configuration.instance().logger().finer("Fin du tour du joueur " + playerTurn + " : " + players.get(playerTurn));
      save.addSave(new Save(playerTurn, currentTile, currentMeeple, gameSet.cloneSet(), pioche, players, meeplesOnSet));
    }
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

      if (type == Tile.Type.CITY)
        ender = t.cityEnder();
      if (type == Tile.Type.ROAD)
        ender = t.roadEnder();

      if (isMeepleOnTile(x, y, card, ender, type, start)) {
        return false;
      }

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

    if (currentMeeple != null)
      return res;

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
  public boolean placeMeeple(String card) {
    if (currentTile.placed && players.get(playerTurn).canUseMeeple()) {
      Meeple m = new Meeple(playerTurn, currentTile.y, currentTile.x, card);

      for (Meeple meep : meeplesOnSet) {
        if (m.equal(meep)) {
          Configuration.instance().logger().info(
              "Il existe déjà un meeple à cette endroit (" + currentTile.x + ", " + currentTile.y + ", " + card + ")");
          return false;
        }
      }

      if (!meeplePlacementAllowed(m) || currentMeeple != null) {
        Configuration.instance().logger()
            .info("Impossible de poser de meeple ici :\n(" + currentTile.x + ", " + currentTile.y + ", " + card + ")");
        return false;
      }

      if (gameSet.meeplePlacementAllowed(m)) {
        players.get(playerTurn).meepleUse();
        meeplesOnSet.add(m);
        currentMeeple = new CurrentMeeple(currentTile.y, currentTile.x, card);
        Configuration.instance().logger().info(players.get(playerTurn).pseudo() + " à poser un meeple sur la case ("
            + m.getX() + ", " + m.getY() + ") " + m.getCardinal());
        return true;
      }
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
    Point start = gameSet.getStartTilePoint();
    if (!currentTile.placed && gameSet.addTile(currentTile.tile, x - start.x, y - start.y)) {
      currentTile.x = x - start.x;
      currentTile.y = y - start.y;
      currentTile.placed();
      players.get(playerTurn).TileplacedPlus();
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
    if (currentTile.placed && gameSet.removeTile(currentTile.x + gameSet.getStartTilePoint().x,
        currentTile.y + gameSet.getStartTilePoint().y)) {
      currentTile.unplaced();
      players.get(playerTurn).TilePlacedMinus();
      Configuration.instance().logger()
          .info("La tuile sur la case (" + currentTile.x + " ," + currentTile.y + ") a été enlevé");
      return true;
    }
    return false;
  }

  public boolean removeMeeple() {
    if (currentMeeple != null) {
      Meeple rm = new Meeple(0, currentMeeple.x, currentMeeple.y, currentMeeple.card);
      for (int i = 0; i < meeplesOnSet.size(); i++) {
        if (meeplesOnSet.get(i).equal(rm)) {
          meeplesOnSet.remove(i);
          players.get(playerTurn).meepleRecovery();
          Configuration.instance().logger().info(
              "Le meeple (" + currentMeeple.x + ", " + currentMeeple.y + ", " + currentMeeple.card + ") a été enlevé");
          currentMeeple = null;
          return true;
        }
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
      if (top.x - start.x == m.getY() && top.y - start.y == m.getX() && top.containsMeeple(m, start))
        return true;
    }
    return false;
  }

  boolean meepleOnAbbey(Project p, Meeple m) {
    Point start = gameSet.getStartTilePoint();
    return p.list().get(0).x - start.x == m.getY() && p.list().get(0).y - start.y == m.getX()
        && p.list().get(0).containsMeeple(m, start);
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

      System.out.println(project.type().toString());

      if (project.type() == Type.ABBEY) {
        for (Meeple m : meeplesOnSet) {
          if (meepleOnAbbey(project, m)) {
            ownersValue.add(m.player, ownersValue.get(m.player) + 1);
            meepleToRemove.add(m);
            players.get(m.player).meepleRecovery();
          }
        }

      } else {
        for (Meeple m : meeplesOnSet) {
          if (meepleOnProject(project, m)) {
            ownersValue.add(m.player, ownersValue.get(m.player) + 1);
            meepleToRemove.add(m);
            players.get(m.player).meepleRecovery();
          }
        }
      }

      meeplesOnSet.removeAll(meepleToRemove);

      int maxValue = 0;
      for (Integer ownerValue : ownersValue) {
        if (ownerValue > maxValue)
          maxValue = ownerValue;
      }

      if (maxValue > 0) {
        for (int i = 0; i < ownersValue.size(); i++) {
          if (ownersValue.get(i) == maxValue) {
            players.get(i).scorePlus(project.value());
            players.get(i).minusCurrentNumberProjects();
          }
        }
      }
      projectsEvaluate.add(project);
    }
  }

  List<Player> resGame() {
    List<Player> sort = new LinkedList<>();
    for (Player p : players) {
      if (sort.isEmpty())
        sort.add(p);
      else {
        for (int j = 0; j < players.size(); j++) {
          if (j == sort.size() - 1) {
            sort.add(p);
            break;
          }
          if (sort.get(j).score() < p.score()) {
            sort.add(j, p);
            break;
          }
        }
      }
    }
    return sort;
  }

  public String[][] playersScores() {
    List<Player> res = resGame();
    String[][] scores = new String[res.size()][4];
    for (int i = 0; i < res.size(); i++) {
      scores[i][0] = res.get(i).pseudo();
      scores[i][1] = String.valueOf(res.get(i).numberOfProjects());
      scores[i][2] = String.valueOf(res.get(i).nbTilePlaced());
      scores[i][3] = String.valueOf(res.get(i).score());
    }
    return scores;
  }

  public void saveGame(String file) {
    save.saveGame(file);
  }
}
