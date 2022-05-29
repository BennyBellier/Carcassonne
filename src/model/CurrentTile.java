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

  public void placed() {
    placed = true;
  }

  public void unplaced() {
    placed = false;
  }

  public boolean equalsTo(CurrentTile t) {
    return tile.equalsTo(t.tile);
  }

  public List<Byte> toByteArray() {
    List<Byte> l = new ArrayList<>();
    Byte p = (placed) ? (byte) 1 : (byte) 0;
    l.add(p);
    l.addAll(tile.toByteArray());
    l.addAll(Arrays.asList((byte) x, (byte) y));
    return l;
  }
}
