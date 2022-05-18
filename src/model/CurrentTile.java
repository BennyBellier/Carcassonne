package model;

public class CurrentTile {
  public Tile tile;
  public boolean placed;
  public int x, y;

  public CurrentTile(Tile t) {
    tile = t;
    placed = false;
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
}
