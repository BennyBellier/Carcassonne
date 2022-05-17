package model.Projects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import global.Configuration;
import model.Meeple;
import model.Tile;
import model.Graph.*;

public abstract class Project {

  public enum Type {
    ABBEY,
    ROAD,
    CITY;
  }

  boolean finish = false;
  Type type;
  Graph<Tile> g = new Graph<>();

  abstract void evaluate(Graph<Tile> g, Tile[][] set, Tile from, int x, int y, String card);

  /**
   ** Retourne l'état du projet
   * @return boolean
   */
  public final boolean finished() {
    return finish;
  }

  /**
   ** Retourne le graph du projet
   */
  public final Graph<Tile> graph() {
    return g;
  }

  /**
   ** Retourne le type Project.Type de projet
   * @return Project.Type
   */
  public final Type type() {
    return type;
  }

  public abstract int value();

  /**
   ** Retourne le nombre de tuile du projet
   *
   * @return
   */
  public final Tile[] getListofTiles() {
    Tile[] res = new Tile[g.getListOfNode().size()];
    Iterator<Tile> it = g.getListOfNode().iterator();
    for (int i = 0; i < g.getListOfNode().size(); i++) {
      res[i] = (Tile) it.next();
    }
    return res;
  }

  public boolean equals(Project p) {
    if (type != p.type())
      return false;

    List<Tile> project1 = new ArrayList<>(Arrays.asList(getListofTiles()));
    List<Tile> project2 = new ArrayList<>(Arrays.asList(p.getListofTiles()));

    if (project1.size() != project2.size())
      return false;

    for (int i = 0; i < project1.size(); i++) {
      for (int j = 0; j < project2.size(); j++) {
        if (project1.get(i).equalsTo(project2.get(j)))
          break;
        if (j == project2.size() - 1)
          return false;
      }
    }

    return false;
  }

  /**
   ** Parcours entièrement le plateau est retourne la liste des projets finie
   *
   * @return Projects[] liste des projets finie
   */
  public static List<Project> evaluateProjects(Tile[][] t) {
    List<Project> projects = new ArrayList<>();
    List<Tile> cityVisit = new ArrayList<>();
    List<Tile> roadVisit = new ArrayList<>();
    List<Tile> abbeyVisit = new ArrayList<>();

    for (int i = 0; i < t.length; i++) {
      for (int j = 0; j < t[i].length; j++) {
        if (!roadVisit.contains(t[i][j]) && t[i][j] != null) {
          if (t[i][j].hasRoad()) {
            if (t[i][j].center() == Tile.Type.ROAD) {
              ProjectRoad p = new ProjectRoad(t, j, i, "c", roadVisit);
              if (p.finished())
                projects.add(p);
            } else {
              if (t[i][j].south() == Tile.Type.ROAD) {
                ProjectRoad p = new ProjectRoad(t, j, i, "s", roadVisit);
                if (p.finished())
                  projects.add(p);
              }
              if (t[i][j].north() == Tile.Type.ROAD) {
                ProjectRoad p = new ProjectRoad(t, j, i, "n", roadVisit);
                if (p.finished())
                  projects.add(p);
              }
              if (t[i][j].east() == Tile.Type.ROAD) {
                ProjectRoad p = new ProjectRoad(t, j, i, "e", roadVisit);
                if (p.finished())
                  projects.add(p);
              }
              if (t[i][j].west() == Tile.Type.ROAD) {
                ProjectRoad p = new ProjectRoad(t, j, i, "w", roadVisit);
                if (p.finished())
                  projects.add(p);
              }
            }
          }
        }
        if (!abbeyVisit.contains(t[i][j]) && t[i][j] != null) {
          abbeyVisit.add(t[i][j]);
          if (t[i][j].center() == Tile.Type.ABBEY) {
            ProjectAbbey p = new ProjectAbbey(t, i, j);
            if (p.finished())
              projects.add(p);
          }
        }

        if (!cityVisit.contains(t[i][j]) && t[i][j] != null) {
          if (t[i][j].hasCity()) {
            if (t[i][j].center() == Tile.Type.CITY) {
              ProjectCity p = new ProjectCity(t, j, i, "c", cityVisit);
              if (p.finished())
                projects.add(p);
              break;
            } else {
              if (t[i][j].cityEnder()) {
                if (t[i][j].south() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "s", cityVisit);
                  if (p.finished())
                    projects.add(p);
                }
                if (t[i][j].north() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "n", cityVisit);
                  if (p.finished())
                    projects.add(p);
                }
                if (t[i][j].east() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "e", cityVisit);
                  if (p.finished())
                    projects.add(p);
                }
                if (t[i][j].west() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "w", cityVisit);
                  if (p.finished())
                    projects.add(p);
                }
              } else {
                if (t[i][j].south() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "s", cityVisit);
                  if (p.finished())
                    projects.add(p);
                } else if (t[i][j].north() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "n", cityVisit);
                  if (p.finished())
                    projects.add(p);
                } else if (t[i][j].east() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "e", cityVisit);
                  if (p.finished())
                    projects.add(p);
                } else if (t[i][j].west() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "w", cityVisit);
                  if (p.finished())
                    projects.add(p);
                }
              }
            }
          }
        }
      }
    }
    Configuration.instance().logger().info("Détection de " + projects.size() + " projets terminées");
    return projects;
  }
}
