package model.Projects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Point;

import model.Meeple;
import model.Tile;

public class TileOfProject {
  public Tile t;
  public int x, y;
  public List<String> cards;

  public TileOfProject(Tile t, int x, int y, String... cards) {
    this.t = t;
    this.x = x;
    this.y = y;
    this.cards = new ArrayList<>(Arrays.asList(cards));
  }

  public boolean equals(TileOfProject top) {
    // test sur les valeurs brute et le nombre de cardinalité
    if (t != top.t || x != top.x || y != top.y || cards.size() != top.cards.size())
      return false;

    for (String string : cards) {
      // Si top ne contient pas uen des cardinalités alors les tuiles sont différentes
      if (!top.cards.contains(string))
        return false;
    }

    return true;
  }

  public boolean containsMeeple(Meeple m, Point start) {
    if (x - start.x == m.getY() && y - start.y == m.getX()) {
      for (String string : cards) {
        if (string == m.getCardinal())
          return true;
      }
    }
    return false;
  }

  /**
   ** Retourne vraie si list contient t2
   *
   * @param list
   * @param t2
   * @return
   */
  public static boolean contains(List<TileOfProject> list, TileOfProject t2) {
    for (TileOfProject t1 : list) {
      if (t1.equals(t2))
        return true;
    }
    return false;
  }

  public static List<Tile> toTileList(List<TileOfProject> l) {
    List<Tile> list = new ArrayList<>();
    for (TileOfProject t : l) {
      list.add(t.t);
    }
    return list;
  }

  public static TileOfProject fromTileCoord(Tile t, int x, int y, String card, boolean ender, Tile.Type type) {
    if (ender)
      return new TileOfProject(t, x, y, card);

    List<String> cards = new ArrayList<>();
    if (t.center() == type)
      cards.add("c");
    if (t.north() == type)
      cards.add("n");
    if (t.south() == type)
      cards.add("s");
    if (t.east() == type)
      cards.add("e");
    if (t.west() == type)
      cards.add("w");
    return new TileOfProject(t, x, y, cards.stream().toArray(String[]::new));
  }
}
