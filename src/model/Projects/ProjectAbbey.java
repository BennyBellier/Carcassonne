package model.Projects;

import global.Configuration;
import model.Meeple;
import model.Tiles.Tile;

public class ProjectAbbey extends Project {

  Meeple meeple;

  /**
   ** Génère un projet de type Abbeye au coordonnées déterminé par le meeple
   * @param meeple meeple placé sur l'abbeye
   * @param set clone du plateau courant
   */
  public ProjectAbbey(Meeple meeple, Tile[][] set) {
    super();
    this.meeple = meeple;
    owner = meeple.getOwner();
    g.addNode(set[meeple.getX()][meeple.getY()]);
    Configuration.instance().logger().info("Création d'un projet abbeye sur la case (" + meeple.getX() + ", " + meeple.getY() + ")");
  }

  /**
   ** Évalue la valeur et l'état du projet de type Abbeye
   * @param set Tile[][] utilise un clone du plateau pour effectuer l'évaluation
   */
  public void evaluate(Tile[][] set) {
    int x = meeple.getX();
    int y = meeple.getY();
    Configuration.instance().logger().info("Évaluation du projet abbeye sur la case (" + x + ", " + y +")");
    for (int i = -1; i < 2; i++) {
      for (int j = -1; j < 2; j++) {
        if (set[x+i][y+j] != null && !g.hasNode(set[x+i][y+j]))
          g.addNode(set[x+i][y+j]);
      }
    }
    if (g.getNodeCount() == 9) {
      finish = true;
      Configuration.instance().logger().info("Projet Abbeye de la case (" + x + ", " + y + ")");
    }
  }
}
