package model.Projects;

import java.util.List;

import global.Configuration;
import model.Graph.*;
import model.Tiles.*;

public class ProjectRoad extends Project {

  Type type = Type.CITY;

  /**
   ** Vérifie si pour la portion de route à la case (x, y), la route qui y
   ** correspond est terminée
   *
   * @param set         Plateau de la partie courante
   * @param x           position x de l'abbeye
   * @param y           position y de l'abbeye
   * @param card        position de la portition de la ville sur la tuile
   * @param cityVisited liste des tuiles contenant une villes visitées
   */
  public ProjectRoad(Tile[][] set, int x, int y, String card, List<Tile> roadVisited) {
    super();
    Configuration
        .instance()
        .logger()
        .info(
            "Évaluation du projet route aux coordonnées (" + x + ", " + y + "), direction : " + card);
    evaluate(g, set, set[y][x], x, y, card);

    roadVisited.addAll(g.getListOfNode());

    if (g.isEmpty()) {
      finish = false;
    }

    if (isRoadFinish(g, (Tile) g.getListOfNode().toArray()[0]))
      finish = true;
    else
      finish = false;

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

  /**
   ** Détermine si le projet et fini ou non
   * <p>
   * Utilisation de la récurence pour tester si toutes les extrémités
   * du graph sont des tuiles qui finissent une route
   * <p>
   * ! Attention, si il n'y as pas de tuiles alors la route n'est pas finie
   *
   * @param g Graph contenant les noeuds du projet
   * @param t la tuile de départ
   * @return vraie si la route est complété (toutes les extrémités sont fermés)
   */
  boolean isRoadFinish(Graph<Tile> g, Tile t) {
    if (g.getVoisins(t).size() == 0 && isEnder(t)) {
      if (g.getNodeCount() == 1)
        return false;
      return true;
    }

    else if (g.getVoisins(t).size() > 0) {
      for (Tile v : g.getVoisins(t)) {
        if (isRoadFinish(g, v) == false)
          return false;
      }
    }
    return false;
  }

  /**
   ** Partie récursive de la fonction d'évaluation
   *
   * @param g    Graph contenant les noeuds compasant le projet
   * @param set  copy du plateau courant
   * @param source Tile précédente permettant de créer les connexions dans le graphs
   * @param x    position x de la tuile à tester
   * @param y    position y de la tuile à tester
   * @param card cardinalité courante sur la tuile
   */
  @Override
  void evaluate(Graph<Tile> g, Tile[][] set, Tile source, int x, int y, String card) {
    Tile t = set[y][x];
    if (t != null && !g.hasNode(t)) {
      g.addNode(t);
      if (g.getNodeCount() > 1)
        g.addEdge(source, t);
      if (isEnder(t) && g.getNodeCount() == 1) {
        switch (card) {
          case "n":
              evaluate(g, set, t, x, y - 1, "s");
          case "s":
              evaluate(g, set, t, x, y + 1, "n");
            break;
          case "e":
              evaluate(g, set, t, x + 1, y, "w");
            break;
          case "w":
              evaluate(g, set, t, x - 1, y, "e");
            break;
          default:
            break;
        }
      } else if (!isEnder(t)) {
        switch (card) {
          case "c":
            if (t.north() == Tile.Type.ROAD) {
              evaluate(g, set, t, x, y - 1, "s");
            }
            if (t.east() == Tile.Type.ROAD) {
              evaluate(g, set, t, x + 1, y, "w");
            }
            if (t.south() == Tile.Type.ROAD) {
              evaluate(g, set, t, x, y + 1, "n");
            }
            if (t.west() == Tile.Type.ROAD) {
              evaluate(g, set, t, x - 1, y, "e");
            }
            break;
          case "n":
            evaluate(g, set, t, x, y - 1, "s");
            if (t.east() == Tile.Type.ROAD) {
              evaluate(g, set, t, x + 1, y, "w");
            }
            if (t.south() == Tile.Type.ROAD) {
              evaluate(g, set, t, x, y + 1, "n");
            }
            if (t.west() == Tile.Type.ROAD) {
              evaluate(g, set, t, x - 1, y, "e");
            }
            break;
          case "s":
            evaluate(g, set, t, x, y + 1, "n");
            if (t.north() == Tile.Type.ROAD) {
              evaluate(g, set, t, x, y - 1, "s");
            }
            if (t.east() == Tile.Type.ROAD) {
              evaluate(g, set, t, x + 1, y, "w");
            }
            if (t.west() == Tile.Type.ROAD) {
              evaluate(g, set, t, x - 1, y, "e");
            }
            break;
          case "e":
            evaluate(g, set, t, x + 1, y, "w");
            if (t.north() == Tile.Type.ROAD) {
              evaluate(g, set, t, x, y - 1, "s");
            }
            if (t.south() == Tile.Type.ROAD) {
              evaluate(g, set, t, x, y + 1, "n");
            }
            if (t.west() == Tile.Type.ROAD) {
              evaluate(g, set, t, x - 1, y, "e");
            }
            break;
          case "w":
            evaluate(g, set, t, x - 1, y, "e");
            if (t.north() == Tile.Type.ROAD) {
              evaluate(g, set, t, x, y - 1, "s");
            }
            if (t.east() == Tile.Type.ROAD) {
              evaluate(g, set, t, x + 1, y, "w");
            }
            if (t.south() == Tile.Type.ROAD) {
              evaluate(g, set, t, x, y + 1, "n");
            }
            break;
          default:
            break;
        }
      }
    }
  }

  /**
   ** Retourne vraie si le centre de la tuile n'est pas un élément qui fini une
   ** route
   *
   * @param t Tile à tester
   * @return vraie si la route se termine au centre de la tuile
   */
  boolean isEnder(Tile t) {
    return (t.center() == Tile.Type.ABBEY ||
        t.center() == Tile.Type.CITY ||
        t.center() == Tile.Type.TOWN);
  }

  /**
   ** Retourne la valeur courante du projet
   * @return int
   */
  @Override
  public int value() {
    return g.getNodeCount();
  }
}
