package model.Projects;


import model.Tile;

import java.util.ArrayList;
import java.util.List;

import global.Configuration;

public class ProjectAbbey extends Project {

  private List<TileOfProject> list;
  private boolean finish;
  /**
   ** Vérifie si pour l'abbeye à la case (x, y), est finie
   * @param set Plateau de la partie courante
   * @param x position x de l'abbeye
   * @param y position y de l'abbeye
   */
  public ProjectAbbey(Tile[][] set, int x, int y) {
    super(Type.ABBEY);
    list = new ArrayList<>();
    finish = false;

    list.add(new TileOfProject(set[y][x], x, y, "c"));
    Configuration.instance().logger().info("Évaluation du projet abbeye sur la case (" + x + ", " + y + ")");
    finish = evaluate(list, set, x, y, "");
    Configuration
        .instance()
        .logger()
        .info(
            "Le projet de l'abbeye aux coordonnées (" +
                x +
                ", " +
                y +
                ") est fini : " +
                isFinish() +
                ", il compte " +
                value() +
                " points");
  }

  /**
   ** Évaluation récursive du projet de type abbeye
   */
  @Override
  boolean evaluate(List<TileOfProject> list, Tile[][] set, int x, int y, String card) {
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        TileOfProject tmp = new TileOfProject(set[y + i][x + j], x + j, y + i, "c");
        if (set[y + i][x + j] != null && !TileOfProject.contains(list, tmp)) {
          list.add(tmp);
        }
      }
    }
    return list.size() == 9;
  }

  /**
   ** Retourne la valeur courante du projet fini ou non
   */
  @Override
  public int value() {
    return list.size();
  }

  @Override
  public boolean isFinish() {
    return finish;
  }

  @Override
  public List<TileOfProject> list() {
    return list;
  }
}
