package model.Projects;

import java.util.ArrayList;
import java.util.List;

import global.Configuration;
import model.Tile;

public abstract class Project {

  public enum Type {
    ABBEY,
    ROAD,
    CITY;
  }

  Type type;

  public Project(Type type) {
    this.type = type;
  }

  public Type type() {
    return type;
  }

  public abstract boolean isFinish();

  abstract boolean evaluate(List<TileOfProject> tile, Tile[][] set, int x, int y, String card);

  public abstract int value();

  public abstract List<TileOfProject> list();

  public boolean equals(Project p) {
    if (type != p.type())
      return false;

    List<TileOfProject> project1 = new ArrayList<>(this.list());
    List<TileOfProject> project2 = new ArrayList<>(p.list());

    if (project1.size() != project2.size())
      return false;

    for (int i = 0; i < project1.size(); i++) {
      for (int j = 0; j < project2.size(); j++) {
        if (project1.get(i).equals(project2.get(j)))
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
  public static List<Project> evaluateProjects(Tile[][] t, boolean endGame) {
    List<Project> projects = new ArrayList<>();
    List<Tile> cityVisit = new ArrayList<>();
    List<Tile> roadVisit = new ArrayList<>();
    List<Tile> abbeyVisit = new ArrayList<>();

    for (int i = 0; i < t.length; i++) {
      for (int j = 0; j < t[i].length; j++) {
        if (!roadVisit.contains(t[i][j]) && t[i][j] != null) {
          if (t[i][j].hasRoad()) {
            if (t[i][j].center() == Tile.Type.ROAD || t[i][j].center() == Tile.Type.CITY
                || t[i][j].center() == Tile.Type.ABBEY) {
              ProjectRoad p = new ProjectRoad(t, j, i, "c", roadVisit);
              if (p.isFinish() || endGame)
                projects.add(p);
            } else {
              if (t[i][j].south() == Tile.Type.ROAD) {
                ProjectRoad p = new ProjectRoad(t, j, i, "s", roadVisit);
                if (p.isFinish() || endGame)
                  projects.add(p);
              }
              if (t[i][j].north() == Tile.Type.ROAD) {
                ProjectRoad p = new ProjectRoad(t, j, i, "n", roadVisit);
                if (p.isFinish() || endGame)
                  projects.add(p);
              }
              if (t[i][j].east() == Tile.Type.ROAD) {
                ProjectRoad p = new ProjectRoad(t, j, i, "e", roadVisit);
                if (p.isFinish() || endGame)
                  projects.add(p);
              }
              if (t[i][j].west() == Tile.Type.ROAD) {
                ProjectRoad p = new ProjectRoad(t, j, i, "w", roadVisit);
                if (p.isFinish() || endGame)
                  projects.add(p);
              }
            }
          }
        }
        if (!abbeyVisit.contains(t[i][j]) && t[i][j] != null) {
          abbeyVisit.add(t[i][j]);
          if (t[i][j].center() == Tile.Type.ABBEY) {
            ProjectAbbey p = new ProjectAbbey(t, j, i);
            if (p.isFinish() || endGame)
              projects.add(p);
          }
        }

        if (!cityVisit.contains(t[i][j]) && t[i][j] != null) {
          if (t[i][j].hasCity()) {
            if (t[i][j].center() == Tile.Type.CITY) {
              ProjectCity p = new ProjectCity(t, j, i, "c", cityVisit);
              if (p.isFinish() || endGame)
                projects.add(p);
              break;
            } else {
              if (t[i][j].cityEnder()) {
                if (t[i][j].south() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "s", cityVisit);
                  if (p.isFinish() || endGame)
                    projects.add(p);
                }
                if (t[i][j].north() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "n", cityVisit);
                  if (p.isFinish() || endGame)
                    projects.add(p);
                }
                if (t[i][j].east() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "e", cityVisit);
                  if (p.isFinish() || endGame)
                    projects.add(p);
                }
                if (t[i][j].west() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "w", cityVisit);
                  if (p.isFinish() || endGame)
                    projects.add(p);
                }
              } else {
                if (t[i][j].south() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "s", cityVisit);
                  if (p.isFinish() || endGame)
                    projects.add(p);
                } else if (t[i][j].north() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "n", cityVisit);
                  if (p.isFinish() || endGame)
                    projects.add(p);
                } else if (t[i][j].east() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "e", cityVisit);
                  if (p.isFinish() || endGame)
                    projects.add(p);
                } else if (t[i][j].west() == Tile.Type.CITY) {
                  ProjectCity p = new ProjectCity(t, j, i, "w", cityVisit);
                  if (p.isFinish() || endGame)
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
