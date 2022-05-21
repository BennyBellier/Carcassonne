package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Point;

import global.Configuration;
import model.Player.Type;
import model.Projects.Project;
import model.Projects.ProjectRoad;

public class GameEngine {
  private GameSet gameSet;
  private Pioche pioche;
  private List<Player> players;
  private List<Meeple> meeplesOnSet;
  private List<Project> projectsEvaluate;
  private int playerTurn, nbPlayer;
  private CurrentTile currentTile;

  public GameEngine(Player... playersIn) {
    gameSet = new GameSet();
    pioche = new Pioche();
    projectsEvaluate = new ArrayList<>();
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
          currentTile.placed();
          currentTile.x = x - start.x;
          currentTile.y = y - start.y;

          if (players.get(playerTurn).nbMeeplesRestant() == 0) {
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
    if (!pioche.isEmpty()) {
      currentTile = new CurrentTile(pioche.piocheTuile());
      currentTile.unplaced();

      while (gameSet.tilePositionsAllowed(currentTile.tile, true).size() == 0) {
        pioche.remiserTuile(currentTile.tile);
        currentTile.tile = pioche.piocheTuile();
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
    projectsEvaluation();
    nextPlayer();
    piocheTuile();
    Configuration.instance().logger().finer("Fin du tour du joueur " + playerTurn + " : " + players.get(playerTurn));
  }

  Project.Type projectOf(Meeple m) {
    return gameSet.getProjectType(m.getX() + gameSet.getStartTilePoint().x, m.getY() + gameSet.getStartTilePoint().y,
        m.getCardinal());
  }

  /**
   ** Retourne vraie si et seulement si le le meeple (x, y, card) peut être posé dans la ville
   * @param x
   * @param y
   * @param card
   * @param start
   * @return
   */
  boolean allowMeepleInCity(int x, int y, String card, Point start) {
    Tile t;
    boolean allowed = true;
    for (Meeple m2 : meeplesOnSet) { // boucle sur tout les meeples sur le plateau
      if (!allowed)
        break;
      if (projectOf(m2) != Project.Type.CITY) // SI pas de type CITY alors break
        break;
      if ((t = gameSet.getTileFromCoord(m2.getX() + start.x, m2.getY() + start.y)) == null) // si tuile null alors break
        break;

      if (gameSet.getTileFromCoord(x + start.x, y + start.y).cityEnder()) {
        switch (card) {
          case "n":
            if (!(x == m2.getX()) && !(y - 1 == m2.getY()))
              break;
            if (t.cityEnder() && m2.getCardinal().equals("s"))
              allowed = false;
            else if (!t.cityEnder())
              allowed = false;
            break;
          case "s":
            if (!(x == m2.getX()) && !(y + 1 == m2.getY()))
              break;
            if (t.cityEnder() && m2.getCardinal().equals("n"))
              allowed = false;
            else if (!t.cityEnder())
              allowed = false;
            break;
          case "e":
            if (!(x + 1 == m2.getX()) && !(y == m2.getY()))
              break;
            if (t.cityEnder() && m2.getCardinal().equals("w"))
              allowed = false;
            else if (!t.cityEnder())
              allowed = false;
            break;
          case "w":
            if (!(x - 1 == m2.getX()) && !(y == m2.getY()))
              break;
            if (t.cityEnder() && m2.getCardinal().equals("e"))
              allowed = false;
            else if (!t.cityEnder())
              allowed = false;
            break;

          default:
            break;
        }
      } else {
        // test coté est
        if (x + 1 == m2.getX() && y == m2.getY()) {
          if (t.cityEnder() && m2.getCardinal().equals("w"))
            allowed = false;
          else if (!t.cityEnder())
            allowed = false;
        }
        // test côté ouest
        if (x - 1 == m2.getX() && y == m2.getY()) {
          if (t.cityEnder() && m2.getCardinal().equals("e"))
            allowed = false;
          else if (!t.cityEnder())
            allowed = false;
        }
        // test côté nord
        if (x == m2.getX() && y + 1 == m2.getY()) {
          if (t.cityEnder() && m2.getCardinal().equals("s"))
            allowed = false;
          else if (!t.cityEnder())
            allowed = false;
        }
        // test côté sud
        if (x == m2.getX() && y - 1 == m2.getY()) {
          if (t.cityEnder() && m2.getCardinal().equals("n"))
            allowed = false;
          else if (!t.cityEnder())
            allowed = false;
        }
      }
    }
    return allowed;
  }
/**
 ** Retourne vraie si il n'y as pas de meeple déjà présent sur la route (x, y, card)
 * @param x
 * @param y
 * @param card
 * @return
 */
  boolean detectMeepleOnRoad(int x, int y, String card) {
    for (Meeple m : meeplesOnSet) {
      if (x == m.getX() && y == m.getY() && Project.Type.ROAD == projectOf(m)) {
        if (ProjectRoad.isEnder(gameSet.getTileFromCoord(x, y)) && card == m.getCardinal())
          return true;
        else
          return true;
      }
    }
    return false;
  }

  /**
   ** Vérifie si sur le route il n'y as pas déjà un meeple
   * Fonction récursive
   * @param visited
   * @param x
   * @param y
   * @param card
   * @return
   */
  boolean checkMeepleOnRoad(List<Tile> visited, int x, int y, String card) {
    if (detectMeepleOnRoad(x, y, card))
      return true;
    Tile t = gameSet.getTileFromCoord(x, y);
    if (t != null && !visited.contains(t)) {
      visited.add(t);
      if (ProjectRoad.isEnder(t) && visited.size() == 1) {
        switch (card) {
          case "n":
            return checkMeepleOnRoad(visited, x, y - 1, "s");
          case "s":
            return checkMeepleOnRoad(visited, x, y + 1, "n");
          case "e":
            return checkMeepleOnRoad(visited, x + 1, y, "w");
          case "w":
            return checkMeepleOnRoad(visited, x - 1, y, "e");
        }
      } else if (!(ProjectRoad.isEnder(t))) {
        boolean res = false;
        switch (card) {
          case "c":
            if (t.north() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x, y - 1, "s");
            }
            if (t.east() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x + 1, y, "w");
            }
            if (t.south() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x, y + 1, "n");
            }
            if (t.west() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x - 1, y, "e");
            }
          case "n":
            res = checkMeepleOnRoad(visited, x, y - 1, "s");
            if (!res && t.east() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x + 1, y, "w");
            }
            if (!res && t.south() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x, y + 1, "n");
            }
            if (!res && t.west() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x - 1, y, "e");
            }
            break;
          case "s":
            res = checkMeepleOnRoad(visited, x, y + 1, "n");
            if (!res && t.north() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x, y - 1, "s");
            }
            if (!res && t.east() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x + 1, y, "w");
            }
            if (!res && t.west() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x - 1, y, "e");
            }
            break;
          case "e":
            res = checkMeepleOnRoad(visited, x + 1, y, "w");
            if (!res && t.north() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x, y - 1, "s");
            }
            if (!res && t.south() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x, y + 1, "n");
            }
            if (!res && t.west() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x - 1, y, "e");
            }
            break;
          case "w":
            res = checkMeepleOnRoad(visited, x - 1, y, "e");
            if (!res && t.north() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x, y - 1, "s");
            }
            if (!res && t.east() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x + 1, y, "w");
            }
            if (!res && t.south() == Tile.Type.ROAD) {
              res = checkMeepleOnRoad(visited, x, y + 1, "n");
            }
            break;
          default:
            break;
        }
        return res;
      }
    }
    return false;
  }

  /**
   ** Retourne vraie si le meeple peut être posé
   * @param m
   * @return
   */
  boolean meeplePlacementAllowed(Meeple m) {
    // if (meeplesOnSet.size() == 0)
    // return true;
    Point start = gameSet.getStartTilePoint();
    Project.Type p1 = gameSet.getProjectType(m.getX() + start.x, m.getY() + start.y, m.getCardinal());
    switch (p1) {
      case CITY:
        return allowMeepleInCity(m.getX(), m.getY(), m.getCardinal(), start);

      // case ROAD:
      // List<Tile> visited = new ArrayList<>();
      // return !checkMeepleOnRoad(visited, m.getX() + start.x, m.getY() + start.y,
      // m.getCardinal());

      default:
        return true;
    }
  }

  /**
   ** Retourne la liste des possitions possibles du meeple sur la tuile courante
   * @return
   */
  public List<String> getMeeplePositions() {
    List<String> res = new ArrayList<>();
    if (players.get(playerTurn).nbMeeplesRestant() > 0) { // vérification si le joueurs à des meeples disponibles

      for (String pos : currentTile.tile.getMeeplesPosition()) {
        switch (currentTile.tile.getCardinalType(pos)) {
          case CITY:
            Point start = gameSet.getStartTilePoint();
            if (allowMeepleInCity(currentTile.x, currentTile.y, pos, start))
              res.add(pos);
            break;

          case ROAD:
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
   ** Place une tuile sur le plateau à al position (x, y), retourne vraie si
   * possible, faux sinon
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

  boolean projectAlreadyEvaluate(Project p) {
    for (Project project : projectsEvaluate) {
      if (project.equals(p))
        return true;
    }
    return false;
  }

  boolean equalType(Project p, Meeple m) {
    return p.type() == gameSet.getProjectType(m.getX() + gameSet.getStartTilePoint().x,
        m.getY() + gameSet.getStartTilePoint().y, m.getCardinal());
  }

  boolean meepleOnProjectTile(Project p, Meeple m) {
    return p.graph().hasNode(
        gameSet.getTileFromCoord(m.getX() + gameSet.getStartTilePoint().x, m.getY() + gameSet.getStartTilePoint().y));
  }

  boolean meepleOnProject(Project p, Meeple m) {
    return equalType(p, m) && meepleOnProject(p, m);
  }

  /*
   * Pour tout les projets finie sur le plateau, détermine le propriétaire du
   * projet,
   * enlève les meeples du plateau et attribut les points au propriétaire
   */
  void projectsEvaluation() {
    List<Project> projects = Project.evaluateProjects(gameSet.cloneSet());

    for (Meeple m : meeplesOnSet) {
      System.out.println(m.toString());
    }

    for (Project project : projects) {
      int[] ownersValue = new int[nbPlayer];
      Iterator<Meeple> it = meeplesOnSet.iterator();
      int index = 0;
      while (it.hasNext()) {
        Meeple m = it.next();
        // if (meepleOnProject(project, m)) {
        // ownersValue[m.player] += 1;
        // players.get(m.player).meepleRecovery();
        // meeplesOnSet.remove(index);
        // ++index;
        // }
      }

      int owner = 0, ownerValue = 0;

      for (int i = 0; i < ownersValue.length; i++) {
        if (ownersValue[i] > ownerValue) {
          ownerValue = ownersValue[i];
          owner = i;
        }
      }
      Configuration.instance().logger()
          .fine(players.get(owner) + " est propiétaire du projet " + project.type() + "à la case");
      players.get(owner).scorePlus(project.value());
      projectsEvaluate.add(project);
    }
  }

  /**
   * $ Gestion des sauvegardes
   */
}
