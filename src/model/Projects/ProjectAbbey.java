package model.Projects;


import model.Graph.Graph;
import global.Configuration;
import model.Tiles.Tile;

public class ProjectAbbey extends Project {

  int x, y;

  /**
   ** Vérifie si pour l'abbeye à la case (x, y), est finie
   * @param set Plateau de la partie courante
   * @param x position x de l'abbeye
   * @param y position y de l'abbeye
   */
  public ProjectAbbey(Tile[][] set, int x, int y) {
    super();
    this.x = x;
    this.y = y;
    g.addNode(set[y][x]);
    Configuration.instance().logger().info("Évaluation du projet abbeye sur la case (" + x + ", " + y + ")");
    evaluate(g, set, null, x, y, "");
    Configuration
        .instance()
        .logger()
        .info(
            "Le projet de l'abbeye aux coordonnées (" +
                x +
                ", " +
                y +
                ") est fini : " +
                finished() +
                ", il compte " +
                value() +
                " points");
  }

  /**
   ** Évaluation récursive du projet de type abbeye
   */
  @Override
  void evaluate(Graph<Tile> g, Tile[][] set, Tile source, int x, int y, String card) {
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        if (set[y + i][x + j] != null && !g.hasNode(set[y + i][x + j])) {
          g.addNode(set[y + i][x + j]);
        }
      }
    }
    if (g.getNodeCount() == 9) {
      finish = true;
      Configuration.instance().logger().info("Projet Abbeye de la case (" + x + ", " + y + ")");
    }
  }

  /**
   ** Retourne la valeur courante du projet fini ou non
   */
  @Override
  public int value() {
    return g.getNodeCount();
  }
}
