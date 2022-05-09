package model.Projects;

import java.util.ArrayList;
import java.util.List;

import global.Configuration;
import model.Graph.*;
import model.Tiles.*;

public abstract class Project {
  boolean finish = false;
  Graph<Tile> g = new Graph<>();

  abstract void evaluate(Graph<Tile> g, Tile[][] set, Tile from, int x, int y, String card);

  /**
   ** Retourne l'état du projet
   * @return
   */
  public final boolean finished() {
    return finish;
  }

  public abstract int value();

  /**
   ** Retourne le nombre de tuile du projet
   * @return
   */
  public final Tile[] getListofTiles() {
    return (Tile[]) g.getListOfNode().toArray();
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
            if (t[i][j].center() == TileType.ROAD) {
              ProjectRoad p = new ProjectRoad(t, j, i, "c", roadVisit);
              if (p.finished())
                projects.add(p);
            } else {
              if (t[i][j].south() == TileType.ROAD) {
                ProjectRoad p = new ProjectRoad(t, j, i, "s", roadVisit);
                if (p.finished())
                  projects.add(p);
              }
              if (t[i][j].north() == TileType.ROAD) {
                ProjectRoad p = new ProjectRoad(t, j, i, "n", roadVisit);
                if (p.finished())
                  projects.add(p);
              }
              if (t[i][j].east() == TileType.ROAD) {
                ProjectRoad p = new ProjectRoad(t, j, i, "e", roadVisit);
                if (p.finished())
                  projects.add(p);
              }
              if (t[i][j].west() == TileType.ROAD) {
                ProjectRoad p = new ProjectRoad(t, j, i, "w", roadVisit);
                if (p.finished())
                  projects.add(p);
              }
            }
          }
        }
        if (!abbeyVisit.contains(t[i][j]) && t[i][j] != null) {
          abbeyVisit.add(t[i][j]);
          if (t[i][j].center() == TileType.ABBEY) {
            ProjectAbbey p = new ProjectAbbey(t, i, j);
            if (p.finished())
              projects.add(p);
          }
        }

        if (!cityVisit.contains(t[i][j]) && t[i][j] != null) {
          if (t[i][j].hasCity()) {
            if (t[i][j].center() == TileType.CITY) {
              ProjectCity p = new ProjectCity(t, j, i, "c", cityVisit);
              if (p.finished())
                projects.add(p);
              break;
            } else {
              if (t[i][j].cityEnder()) {
                if (t[i][j].south() == TileType.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "s", cityVisit);
                  if (p.finished())
                    projects.add(p);
                }
                if (t[i][j].north() == TileType.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "n", cityVisit);
                  if (p.finished())
                    projects.add(p);
                }
                if (t[i][j].east() == TileType.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "e", cityVisit);
                  if (p.finished())
                    projects.add(p);
                }
                if (t[i][j].west() == TileType.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "w", cityVisit);
                  if (p.finished())
                    projects.add(p);
                }
              }
              else {
                if (t[i][j].south() == TileType.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "s", cityVisit);
                  if (p.finished())
                    projects.add(p);
                }
                else if (t[i][j].north() == TileType.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "n", cityVisit);
                  if (p.finished())
                    projects.add(p);
                }
                else if (t[i][j].east() == TileType.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "e", cityVisit);
                  if (p.finished())
                    projects.add(p);
                }
                else if (t[i][j].west() == TileType.CITY) {
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
