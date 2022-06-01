package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.GameSet;
import model.Meeple;
import model.Tile;
import model.Projects.Project;
import model.Projects.TileOfProject;
import model.Projects.Project.Type;

public class IAMoyen implements IA {
  private Random rand;

  List<Project> projetsIA;
  List<Project> projetsAdversaire;
  List<Meeple> meeples;
  int id;

  public IAMoyen() {
    rand = new Random();
  }

  List<Integer> valeurs(List<Project> list) {
    List<Integer> retour = new ArrayList<>();
    if (list.size() == 0) {
      retour.add(0);
    } else {
      for (Project element : list) {
        retour.add(element.value());
      }
    }
    return retour;
  }

  int calcul(GameSet gs)
  //
  // finir le projet de l'adversaire -1
  // complete un projet de l'adversaire 0
  // complete notre projet et celui de l'adversaire 1
  // complete notre projet ou en démarrer un nouveau 2
  // termine notre projet valeur du projet
  {

    List<Integer> ptsProjetsIA = new ArrayList<>(), ptsProjetsAdversaire = new ArrayList<>(), ptsIACour,
        ptsAdversaireCour;
    if (projetsIA.size() > 0)
      ptsProjetsIA = valeurs(projetsIA);
    if (projetsAdversaire.size() > 0)
      ptsProjetsAdversaire = valeurs(projetsAdversaire);

    boolean completed = false;
    assignProject(gs);

    ptsIACour = valeurs(projetsIA);
    ptsAdversaireCour = valeurs(projetsAdversaire);

    if (ptsAdversaireCour.size() > 0) {
      for (Project elt : projetsAdversaire) {
        int a = projetsAdversaire.indexOf(elt);
        if (elt.isFinish()) {
          return -1;
        } else if (ptsProjetsAdversaire.size() < ptsAdversaireCour.size()) {
          completed = true;
        } else if (ptsProjetsAdversaire.get(a) != ptsAdversaireCour.get(a)) {
          completed = true;
        }
      }

    } else {
      if (ptsIACour.size() == 0) {
        return 2;
      } else {
        for (Project elt : projetsIA) {
          if (elt.isFinish()) {
            return elt.value();
          } else if (ptsProjetsIA.get(projetsIA.indexOf(elt)) != ptsIACour.get(projetsIA.indexOf(elt))) {

            if (completed) {
              return 1;
            } else {
              return 2;
            }
          } else {
            if (completed) {
              return 0;
            }
          }
        }
      }
    }
    return -1;
  }

  boolean meepleOnAbbey(GameSet gameSet, Project p, Meeple m) {
    Point start = gameSet.getStartTilePoint();
    return p.list().get(0).x - start.x == m.getY() && p.list().get(0).y - start.y == m.getX()
        && p.list().get(0).containsMeeple(m, start);
  }

  boolean meepleOnProject(GameSet gameSet, Project p, Meeple m) {
    Point start = gameSet.getStartTilePoint();
    for (TileOfProject top : p.list()) {
      if (top.x - start.x == m.getY() && top.y - start.y == m.getX() && top.containsMeeple(m, start))
        return true;
    }
    return false;
  }

  void assignProject(GameSet gs) {
    List<Project> all = Project.evaluateProjects(gs.cloneSet(), true);
    for (Project project : all) {
      List<Integer> ownersValue = new ArrayList<>();
      for (int i = 0; i < 2; i++) {
        ownersValue.add(0);
      }

      if (project.type() == Type.ABBEY) {
        for (Meeple m : meeples) {
          if (meepleOnAbbey(gs, project, m)) {
            if (m.getOwner() == id)
              ownersValue.add(0, ownersValue.get(0) + 1);
            else
              ownersValue.add(1, ownersValue.get(1) + 1);
          }
        }

      } else {
        for (Meeple m : meeples) {
          if (meepleOnProject(gs, project, m)) {
            if (m.getOwner() == id)
              ownersValue.add(0, ownersValue.get(0) + 1);
            else
              ownersValue.add(1, ownersValue.get(1) + 1);
          }
        }
      }

      int maxValue = 0;
      for (Integer ownerValue : ownersValue) {
        if (ownerValue > maxValue)
          maxValue = ownerValue;
      }

      if (maxValue > 0) {
        for (int i = 0; i < ownersValue.size(); i++) {
          if (ownersValue.get(i) == maxValue) {
            if (i == 0)
              projetsIA.add(project);
            else
              projetsAdversaire.add(project);
          }
        }
      }
    }
  }

  Tile rotateTileFromCoord(Tile t, GameSet gs, int i, int j) {
    Map<Integer, ArrayList<Integer>> pos;
    boolean correctRota = false;

    while (!correctRota) {
      pos = gs.tilePositionsAllowed(t, false);
      if (pos.size() == 0)
        t.turnClock();
      else if (!pos.containsKey(i))
        t.turnClock();
      else if (!pos.get(i).contains(j))
        t.turnClock();
      else
        correctRota = true;
    }
    return t;
  }

  public int[] placeTile(int id, GameSet gs, Tile currentTile, List<Meeple> meeples) {
    this.meeples = meeples;
    this.id = id;

    projetsIA = new ArrayList<>();
    projetsAdversaire = new ArrayList<>();

    // Liste des mauvais placements
    List<Point> badPlacement = new ArrayList<>();

    Map<Integer, ArrayList<Integer>> pos = gs.tilePositionsAllowed(currentTile, true);
    Tile testTile;
    GameSet testGameSet;
    Point start = gs.getStartTilePoint();

    int maxpts = -1;
    int x = 0;
    int y = 0;
    int m;
    for (int i : pos.keySet()) {
      for (int j : pos.get(i)) {
        testGameSet = gs.clone();
        testTile = currentTile.clone();
        testTile = rotateTileFromCoord(testTile, testGameSet, i, j);
        if (testGameSet.addTile(testTile, j - start.x, i - start.y) && (m = calcul(testGameSet)) >= maxpts) {
          maxpts = m;
          x = j;
          y = i;
          if (maxpts == -1) {
            badPlacement.add(new Point(x, y));
          }
        }
      }
    }

    if (maxpts == -1) {
      Point placement = badPlacement.get(rand.nextInt(badPlacement.size()));
      x = placement.x;
      y = placement.y;
    }

    rotateTileFromCoord(currentTile, gs, y, x);
    return new int[] { x, y };
  }

  public String placeMeeple(List<String> meeplesPositions) {
    return meeplesPositions.get(rand.nextInt(meeplesPositions.size()));
  }
}