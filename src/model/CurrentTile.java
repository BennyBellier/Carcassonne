package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CurrentTile {
  public Tile tile;
  public boolean placed;
  public int x, y;

  public CurrentTile(Tile t) {
    tile = t;
    placed = false;
  }

  /**
   ** Génére un objet tuile depuis un tableau de bytes
   * @param b
   */
  public CurrentTile(byte[] b) {
    if (b[0] == 0)
      placed = false;
    else
      placed = true;

    byte[] tb = new byte[9];
    for (int i = 1; i < 10; i++) {
      tb[i-1] = b[i];
    }
    tile = new Tile(tb);

    x = b[10];
    y = b[11];
  }

  /**
   ** Met le boolean placed à vraie
   */
  public void placed() {
    placed = true;
  }

  /**
   ** Met le boolean de placed à faux
   */
  public void unplaced() {
    placed = false;
  }

  /**
   ** Retourne l'égalité entre 2 tuiles
   */
  public boolean equalsTo(CurrentTile t) {
    return tile.equalsTo(t.tile);
  }

  /**
   ** Retourne le tableau de byte pour la sauvegarde
   * @return
   */
  public List<Byte> toByteArray() {
    List<Byte> l = new ArrayList<>();
    Byte p = (placed) ? (byte) 1 : (byte) 0;
    l.add(p);
    l.addAll(tile.toByteArray());
    l.addAll(Arrays.asList((byte) x, (byte) y));
    return l;
  }

  @Override
  public CurrentTile clone() {
    CurrentTile c = new CurrentTile(tile.clone());
    c.placed = placed;
    c.x = x;
    c.y = y;
    return c;
  }
}
