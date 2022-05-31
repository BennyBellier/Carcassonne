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
    }
    else {
      for (Project element : list) {
        retour.add(element.value());
      }
    }
    return retour;
  }

  int calcul(int i, int j, Tile currentTile, GameSet gs)
  // finir le projet de l'adversaire -1
  // complete un projet de l'adversaire 0
  // complete notre projet et celui de l'adversaire 1
  // complete notre projet ou en démarrer un nouveau 2
  // termine notre projet valeur du projet
  {

    List<Integer> ptsProjetsIA = new ArrayList<>(), ptsProjetsAdversaire = new ArrayList<>(), ptsIACour, ptsAdversaireCour;
    if (projetsIA.size() > 0)
      ptsProjetsIA = valeurs(projetsIA);
    if (projetsAdversaire.size() > 0)
      ptsProjetsAdversaire = valeurs(projetsAdversaire);

    boolean placed = gs.addTile(currentTile,j - (int) gs.getStartTilePoint().getX(), i - (int) gs.getStartTilePoint().getY());
    boolean completed = false;
    assignProject(gs);

    
    if (placed) {
      ptsIACour = valeurs(projetsIA);
      ptsAdversaireCour = valeurs(projetsAdversaire);


      if (ptsAdversaireCour.size() >0)
      {
      for (Project elt : projetsAdversaire)
      {
        int a = projetsAdversaire.indexOf(elt);
        if (elt.isFinish()) {
          return -1;
        } else if (ptsProjetsAdversaire.size() < ptsAdversaireCour.size())
        { 
          completed = true;
        }else if (ptsProjetsAdversaire.get(a) != ptsAdversaireCour.get(a))
        {
          completed = true;
        }
      }
      
      }
      else
      {
      if (ptsIACour.size()==0)
      {
        return 2;
      }
      else
      {
      for (Project elt : projetsIA)
      {
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
  }
    return 2;
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

  public int[] placeTile(int id, GameSet gs, Tile currentTile, List<Meeple> meeples) {
    this.meeples = meeples;
    this.id = id;

    projetsIA = new ArrayList<>();
    projetsAdversaire = new ArrayList<>();

    Map<Integer, ArrayList<Integer>> pos = gs.tilePositionsAllowed(currentTile, true);
    System.out.println("pos with rota : "+pos);
    Map<Integer, ArrayList<Integer>> pos2 = gs.tilePositionsAllowed(currentTile, false);
    System.out.println("pos sans rota : "+pos2);

    List<Integer> iList = new ArrayList<>(pos.keySet());
    int maxpts = -1;
    int x = 0;
    int y = 0;
    int m;
    for (int i : iList) {
      for (int j : pos.get(i)) {
        if ((m = calcul(i, j, currentTile, gs)) >= maxpts) {
          maxpts = m;
          x = j;
          y = i;
        }
      }
    }
    // faire la rotation

    boolean rotaok = false;
    while(! rotaok){
      System.out.println("___");
      System.out.println("pos with rota : "+pos);
      Map<Integer, ArrayList<Integer>> pos2 = gs.tilePositionsAllowed(currentTile, false);
      System.out.println("pos sans rota : "+pos2);
      iList = new ArrayList<>(pos2.keySet());

      for (int i : iList){
        for (int j : pos2.get(i)){

          System.out.println("coordonné a jouer :"+y +","+x);
          System.out.println("i,j = "+i +","+ j);
          if ((y == i)&& (x == j) )
          {
            rotaok = true;
          }
          else{
            if (!rotaok)
            {
              currentTile.turnClock();
            }
          }
        }
      }
    System.out.println(rotaok);
    }
    
    return new int[] { y, x };
  }

  public String placeMeeple(List<String> meeplesPositions) {
    return meeplesPositions.get(rand.nextInt(meeplesPositions.size()));
  }
}