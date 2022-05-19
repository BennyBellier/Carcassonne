package model.Projects;

import java.util.List;

import global.Configuration;
import model.Tile;
import model.Graph.*;

public class ProjectCity extends Project {

  private Graph g;
  private boolean finish;

  /**
   ** Vérifie si pour la portion de ville à la case (x, y), la ville qui y
   ** correspond est terminée
   *
   * @param set         Plateau de la partie courante
   * @param x           position x de l'abbeye
   * @param y           position y de l'abbeye
   * @param card        position de la portition de la ville sur la tuile
   * @param cityVisited liste des tuiles contenant une villes visitées
   */
  public ProjectCity(Tile[][] set, int x, int y, String card, List<Tile> cityVisited) {
    super(Type.CITY);
    g = new Graph();
    finish = false;

    Configuration
        .instance()
        .logger()
        .info(
            "Évaluation du projet ville aux coordonnées (" + x + ", " + y + ")");
    evaluate(g, set, set[y][x], x, y, card);

    cityVisited.addAll(g.getSetOfNode());

    System.out.println(g.toString());

    if (g.isEmpty()) {
      finish = false;
    }
    else {
      if (isCityFinish(g, (Tile) g.getListofNode()[0]))
        finish = true;
      else
        finish = false;
    }


    Configuration
        .instance()
        .logger()
        .info(
            "Le projet de ville aux coordonnées (" +
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
   ** Détermine si le projet et fini ou non
   * <p>
   * Utilisation de la récurence pour tester si toutes les extrémités
   * du graph sont des tuiles qui ferme une ville
   * <p>
   * ! Attention, si il n'y as pas de tuiles alors la ville n'est pas fermé
   *
   * @param g Graph contenant les noeuds du projet
   * @param t la tuile de départ
   * @return vraie si la ville est complété (toutes les extrémités sont fermés)
   */
  boolean isCityFinish(Graph g, Tile t) {
    if (g.getVoisins(t).size() == 0 && t.cityEnder()) {
      if (g.getNodeCount() == 1)
        return false;
      return true;
    }
    else if (g.getVoisins(t).size() > 0) {
      for (Tile v : g.getVoisins(t)) {
        if (isCityFinish(g, v) == false)
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
   * @param source Tile tuile précédente permettant de créer les connexions dans le graphs
   * @param x    position x de la tuile à tester
   * @param y    position y de la tuile à tester
   * @param card cardinalité courante sur la tuile
   */
  // @Override
  void evaluate(Graph g, Tile[][] set, Tile source, int x, int y, String card) {
    Tile t = set[y][x];
    if (t != null && !g.hasNode(t)) {
      g.addNode(t);
      if (g.getNodeCount() > 1 && !t.equalsTo(source))
        g.addEdge(source, t);
      if (t.cityEnder() && g.getNodeCount() == 1) {
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
      } else if (!t.cityEnder()) {
        switch (card) {
          case "c":
            if (t.north() == Tile.Type.CITY) {
              evaluate(g, set, t, x, y - 1, "s");
            }
            if (t.east() == Tile.Type.CITY) {
              evaluate(g, set, t, x + 1, y, "w");
            }
            if (t.south() == Tile.Type.CITY) {
              evaluate(g, set, t, x, y + 1, "n");
            }
            if (t.west() == Tile.Type.CITY) {
              evaluate(g, set, t, x - 1, y, "e");
            }
            break;
          case "n":
            evaluate(g, set, t, x, y - 1, "s");
            if (t.east() == Tile.Type.CITY) {
              evaluate(g, set, t, x + 1, y, "w");
            }
            if (t.south() == Tile.Type.CITY) {
              evaluate(g, set, t, x, y + 1, "n");
            }
            if (t.west() == Tile.Type.CITY) {
              evaluate(g, set, t, x - 1, y, "e");
            }
            break;
          case "s":
            evaluate(g, set, t, x, y + 1, "n");
            if (t.north() == Tile.Type.CITY) {
              evaluate(g, set, t, x, y - 1, "s");
            }
            if (t.east() == Tile.Type.CITY) {
              evaluate(g, set, t, x + 1, y, "w");
            }
            if (t.west() == Tile.Type.CITY) {
              evaluate(g, set, t, x - 1, y, "e");
            }
            break;
          case "e":
            evaluate(g, set, t, x + 1, y, "w");
            if (t.north() == Tile.Type.CITY) {
              evaluate(g, set, t, x, y - 1, "s");
            }
            if (t.south() == Tile.Type.CITY) {
              evaluate(g, set, t, x, y + 1, "n");
            }
            if (t.west() == Tile.Type.CITY) {
              evaluate(g, set, t, x - 1, y, "e");
            }
            break;
          case "w":
            evaluate(g, set, t, x - 1, y, "e");
            if (t.north() == Tile.Type.CITY) {
              evaluate(g, set, t, x, y - 1, "s");
            }
            if (t.east() == Tile.Type.CITY) {
              evaluate(g, set, t, x + 1, y, "w");
            }
            if (t.south() == Tile.Type.CITY) {
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
   ** Retourne le nombre de blason que contient la ville
   * @return int
   */
  int blasonCounter() {
    int blason = 0;
    for (Tile t : g.getListofNode()) {
      if (t.blason())
        blason += 1;
    }
    return blason;
  }

  /**
   ** Retourne la valeur courante du projet
   * @return int
   */
  @Override
  public int value() {
    if (isFinish())
      return (g.getNodeCount() + blasonCounter()) * 2;
    return g.getNodeCount() + blasonCounter();
  }

  @Override
  public boolean isFinish() {
    return finish;
  }

  @Override
  public Graph graph() {
    return g;
  }
}
