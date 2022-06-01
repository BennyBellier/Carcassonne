package model.Projects;

import java.util.ArrayList;
import java.util.List;

import global.Configuration;
import model.Tile;

public class ProjectRoad extends Project {

  private List<TileOfProject> list;
  private boolean finish;
  /**
   ** Vérifie si pour la portion de route à la case (x, y), la route qui y
   ** correspond est terminée
   *
   * @param set         Plateau de la partie courante
   * @param x           position x de l'abbeye
   * @param y           position y de l'abbeye
   * @param card        position de la portition de la route sur la tuile
   * @param ROADVisited liste des tuiles contenant une routes visitées
   */
  public ProjectRoad(Tile[][] set, int x, int y, String card, List<Tile> roadVisited) {
    super(Type.ROAD);
    list = new ArrayList<>();
    finish = false;

    Configuration
        .instance()
        .logger()
        .info("Évaluation du projet route aux coordonnées (" + x + ", " + y + "), direction : " + card);
    finish = evaluate(list, set, x, y, card);

    roadVisited.addAll(TileOfProject.toTileList(list));

    Configuration
        .instance()
        .logger()
        .info(
            "Le projet de route aux coordonnées (" +
                x +
                ", " +
                y +
                ") est fini : " +
                finish +
                ", il compte " +
                value() +
                " points");
  }

  boolean evaluate(List<TileOfProject> visited, Tile[][] set, int x, int y, String card) {
    Tile t;
    boolean ender;
    StringBuilder str = new StringBuilder();

    str.append("Évaluation de la tuile (" + x + ", " + y + ") -> " + card + ", type = " + type.toString() + "\n");

    if ((t = set[y][x]) == null) {
      str.append("\tLa tuile est null" + "\n");
      Configuration.instance().logger().fine(str.toString());
      return false;
    }

    if (t.getCardinalType(card) != Tile.Type.ROAD) {
      str.append("\tLe cardinal ne correspond pas à un type route" + "\n");
      Configuration.instance().logger().fine(str.toString());
      return false;
    }

    ender = t.roadEnder();

    TileOfProject top = TileOfProject.fromTileCoord(t, x, y, card, ender, Tile.Type.ROAD);

    if (!TileOfProject.contains(visited, top)) {
      visited.add(top);

      if (ender) {
        str.append("\tLa tuile est une fin de route" + "\n");
        switch (card) {
          case "n":
            str.append("\tÉvaluation de la cardinalité nord" + "\n");
            if (t.north() == Tile.Type.ROAD && evaluate(visited, set, x, y - 1, "s")) {
              str.append("\tLa tuile est lié à d'autre tuile de type route" + "\n");
              Configuration.instance().logger().fine(str.toString());
              return true;
            } else {
              str.append("\tLa tuile n'est pas lié à d'autre tuie de type route" + "\n");
              Configuration.instance().logger().fine(str.toString());
              return false;
            }
          case "s":
            str.append("\tÉvaluation de la cardinalité sud" + "\n");
            if (t.south() == Tile.Type.ROAD && evaluate(visited, set, x, y + 1, "n")) {
              str.append("\tLa tuile est lié à d'autre tuile de type route" + "\n");
              Configuration.instance().logger().fine(str.toString());
              return true;
            } else {
              str.append("\tLa tuile n'est pas lié à d'autre tuie de type route" + "\n");
              Configuration.instance().logger().fine(str.toString());
              return false;
            }
          case "e":
            str.append("\tÉvaluation de la cardinalité est" + "\n");
            if (t.east() == Tile.Type.ROAD && evaluate(visited, set, x + 1, y, "w")) {
              str.append("\tLa tuile est lié à d'autre tuile de type route" + "\n");
              Configuration.instance().logger().fine(str.toString());
              return true;
            } else {
              str.append("\tLa tuile n'est pas lié à d'autre tuie de type route" + "\n");
              Configuration.instance().logger().fine(str.toString());
              return false;
            }
          case "w":
            str.append("\tÉvaluation de la cardinalité ouest" + "\n");
            if (t.west() == Tile.Type.ROAD && evaluate(visited, set, x - 1, y, "e")) {
              str.append("\tLa tuile est lié à d'autre tuile de type route" + "\n");
              Configuration.instance().logger().fine(str.toString());
              return true;
            } else {
              str.append("\tLa tuile n'est pas lié à d'autre tuie de type route" + "\n");
              Configuration.instance().logger().fine(str.toString());
              return false;
            }
        }
      } else {
        str.append("\tLa tuile n'est pas une fin de route" + "\n");
        boolean n = true, s = true, e = true, w = true;
        if (t.north() == Tile.Type.ROAD) {
          str.append("\tÉvaluation de la cardinalité nord" + "\n");
          if (t.north() == Tile.Type.ROAD)
            n = evaluate(visited, set, x, y - 1, "s");
        }
        if (t.south() == Tile.Type.ROAD) {
          str.append("\tÉvaluation de la cardinalité sud" + "\n");
          if (t.south() == Tile.Type.ROAD)
            s = evaluate(visited, set, x, y + 1, "n");
        }
        if (t.east() == Tile.Type.ROAD) {
          str.append("\tÉvaluation de la cardinalité est" + "\n");
          if (t.east() == Tile.Type.ROAD)
            e = evaluate(visited, set, x + 1, y, "w");
        }
        if (t.west() == Tile.Type.ROAD) {
          str.append("\tÉvaluation de la cardinalité ouest" + "\n");
          if (t.west() == Tile.Type.ROAD)
            w = evaluate(visited, set, x - 1, y, "e");
        }
        str.append("\tRésultat de la tuile (" + x + ", " + y + ") -> " + card + " : nord = " + n + ", sud = " + s
            + ", est = " + e + ", ouest = " + w + "\n");
        Configuration.instance().logger().fine(str.toString());
        return (n && s && e && w);
      }
    }
    Configuration.instance().logger().fine(str.toString());
    return true;
  }

  /**
   ** Retourne la valeur courante du projet
   * @return int
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
