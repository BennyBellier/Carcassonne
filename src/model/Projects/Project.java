package model.Projects;

import model.Graph.*;
import model.Tiles.*;

public abstract class Project {
  int owner;
  boolean finish;
  Graph<Tile> g;

  public Project() {
    finish = false;
    g = new Graph<>();
  }

  public void evaluate(Tile[][] set) {
  }

  public boolean finished() {
    return finish;
  }

  public int value() {
    return g.getNodeCount();
  }

  public int[] owner(int nbP) {
    return new int[] {owner};
  }

  public Tile[] getListofTiles() {
    return (Tile[])g.getListOfNode().toArray();
  }
}
