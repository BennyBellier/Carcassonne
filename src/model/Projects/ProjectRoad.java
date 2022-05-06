package model.Projects;

import global.Configuration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Set;
import model.Graph.*;
import model.Meeple;
import model.Tiles.*;

public class ProjectRoad extends Project {

  private ArrayList<Meeple> meeples = new ArrayList<>();

  /**
   ** Génère un projet de type route au coordonnées déterminé par le meeple
   * @param meeple Meeple placé sur la route
   * @param set Tile[][] clone du plateau courant
   */
  public ProjectRoad(Meeple meeple, Tile[][] set) {
    super();
    meeples.add(meeple);
    g.addNode(set[meeple.getX()][meeple.getY()]);
    Configuration
      .instance()
      .logger()
      .info(
        "Création d'un projet route sur la case (" +
        meeple.getX() +
        ", " +
        meeple.getY() +
        ")"
      );
  }

  /**
   ** Evalue la valeur et l'état du projet de type route
   * <p>
   * L'évaluation se fait récursivement
   * <p>
   * @param set Tile[][] utilise un clone du plateau pour effectuer l'évaluation
   */
  @Override
  public void evaluate(Tile[][] set) {
    int x = meeples.get(0).getX();
    int y = meeples.get(0).getY();

    Configuration
      .instance()
      .logger()
      .info(
        "Évaluation du projet route aux coordonnées (" + x + ", " + y + ")"
      );
    __evaluate(g, set, x, y, meeples.get(0).getCardinal());

    if (isRoadFinish(g, g.getFirstNode()))
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
        " points"
      );
  }

  /**
   ** Détermine si le projet et fini ou non 
   * <p>
   * Utilisation de la récurence pour tester si toutes les extrémités
   * du graph sont des tuiles qui finissent une route
   * <p>
   *! Attention, si il n'y as pas de tuiles alors la route n'est pas finie
   * @param g Graph contenant les noeuds du projet
   * @param t la tuile de départ
   * @return vraie si la route est complété (toutes les extrémités sont fermés)
   */
  boolean isRoadFinish(Graph<Tile> g, Tile t) {
    if (g.getVoisins(t).size() == 0 && isEnder(t))
      return true;
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
   * @param g Graph contenant les noeuds compasant le projet
   * @param set copy du plateau courant
   * @param x position x de la tuile à tester
   * @param y position y de la tuile à tester
   * @param card cardinalité courante sur la tuile
   */
  void __evaluate(Graph<Tile> g, Tile[][] set, int x, int y, String card) {
    Tile t = set[x][y];
    if (t != null && !g.hasNode(t)) {
      g.addNode(t);
      if (!isEnder(t)) {
        switch (card) {
          case "c":
            if (t.north() == TileType.ROAD) {
              g.addEdge(t, set[x][y + 1]);
              __evaluate(g, set, x, y + 1, "s");
            }
            if (t.east() == TileType.ROAD) {
              g.addEdge(t, set[x + 1][y]);
              __evaluate(g, set, x + 1, y, "w");
            }
            if (t.south() == TileType.ROAD) {
              g.addEdge(t, set[x][y - 1]);
              __evaluate(g, set, x, y - 1, "n");
            }
            if (t.west() == TileType.ROAD) {
              g.addEdge(t, set[x - 1][y]);
              __evaluate(g, set, x - 1, y, "e");
            }
            break;
          case "n":
            if (t.east() == TileType.ROAD) {
              g.addEdge(t, set[x + 1][y]);
              __evaluate(g, set, x + 1, y, "w");
            }
            if (t.south() == TileType.ROAD) {
              g.addEdge(t, set[x][y - 1]);
              __evaluate(g, set, x, y - 1, "n");
            }
            if (t.west() == TileType.ROAD) {
              g.addEdge(t, set[x - 1][y]);
              __evaluate(g, set, x - 1, y, "e");
            }
            break;
          case "s":
            if (t.north() == TileType.ROAD) {
              g.addEdge(t, set[x][y + 1]);
              __evaluate(g, set, x, y + 1, "s");
            }
            if (t.east() == TileType.ROAD) {
              g.addEdge(t, set[x + 1][y]);
              __evaluate(g, set, x + 1, y, "w");
            }
            if (t.west() == TileType.ROAD) {
              g.addEdge(t, set[x - 1][y]);
              __evaluate(g, set, x - 1, y, "e");
            }
            break;
          case "e":
            if (t.north() == TileType.ROAD) {
              g.addEdge(t, set[x][y + 1]);
              __evaluate(g, set, x, y + 1, "s");
            }
            if (t.south() == TileType.ROAD) {
              g.addEdge(t, set[x][y - 1]);
              __evaluate(g, set, x, y - 1, "n");
            }
            if (t.west() == TileType.ROAD) {
              g.addEdge(t, set[x - 1][y]);
              __evaluate(g, set, x - 1, y, "e");
            }
            break;
          case "w":
            if (t.north() == TileType.ROAD) {
              g.addEdge(t, set[x][y + 1]);
              __evaluate(g, set, x, y + 1, "s");
            }
            if (t.east() == TileType.ROAD) {
              g.addEdge(t, set[x + 1][y]);
              __evaluate(g, set, x + 1, y, "w");
            }
            if (t.south() == TileType.ROAD) {
              g.addEdge(t, set[x][y - 1]);
              __evaluate(g, set, x, y - 1, "n");
            }
            break;
          default:
            break;
        }
      }
    }
  }

  /**
   ** Retourne vraie si le centre de la tuile n'est pas un élément qui fini une route
   * @param t Tile à tester
   * @return vraie si la route se termine au centre de la tuile
   */
  boolean isEnder(Tile t) {
    return (
      t.center() == TileType.ABBEY ||
      t.center() == TileType.CITY ||
      t.center() == TileType.TOWN
    );
  }

    /**
   * Retourne le ou les identifiants des joueurs qui possèdent la route
   * @param nbP entier : Nombre de joueur dans la partie
   * @return int[] tableau contenant le ou les identifiants des joueurs qui possédent la route
   */
  @Override
  public int[] owner(int nbP) {
    if (meeples.size() == 1) return new int[] {
      meeples.get(0).getOwner(),
    }; else {
      Map<Integer, Integer> hm = new HashMap<>();
      int max = 0;
      for (Meeple meeple : meeples) {
        int owner = meeple.getOwner();
        hm.put(owner, hm.getOrDefault(owner, 0) + 1);
      }
      Set<Entry<Integer, Integer>> entrySet = hm.entrySet();
      for (Entry<Integer, Integer> entry : entrySet) {
        if (entry.getValue() > max) {
          max = entry.getValue();
        }
      }
      ArrayList<Integer> ownersList = new ArrayList<>();
      for (Entry<Integer, Integer> entry : entrySet) {
        if (entry.getValue() == max) {
          ownersList.add(entry.getKey());
        }
      }
      int[] owners = new int[ownersList.size()];
      for (int i = 0; i < owners.length; i++) {
        owners[i] = ownersList.get(i);
      }
      return owners;
    }
  }
}
