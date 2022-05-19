package model.Graph;

import java.util.*;
import model.Tile;

public class Graph {

  // We use Hashmap to store the edges in the graph
  private Map<Tile, List<Tile>> map = new HashMap<>();

  // This function adds a new Node to the graph
  public void addNode(Tile s) {
    map.put(s, new LinkedList<Tile>());
  }

  // This function adds the edge
  // between source to destination
  public void addEdge(Tile source, Tile destination) {

    if (!map.containsKey(source))
      addNode(source);

    if (!hasEdge(source, destination)){
      map.get(source).add(destination);
    }
  }

  // This function gives the count of vertices
  public int getNodeCount() {
    return map.keySet().size();
  }

  // This function gives the count of edges
  public void getEdgesCount() {
    int count = 0;
    for (Tile v : map.keySet()) {
      count += map.get(v).size();
    }
    count = count / 2;
  }

  // This function gives whether
  // a Node is present or not.
  public boolean hasNode(Tile s) {
    Iterator<Tile> it = map.keySet().iterator();
    while (it.hasNext()) {
      if (s.equalsTo(it.next()))
        return true;
    }
    return false;
  }

  // This function gives whether an edge is present or not.
  public boolean hasEdge(Tile s, Tile d) {
    if (map.get(s) != null && map.get(s).contains(d))
      return true;
    else
      return false;
  }

  public Set<Tile> getSetOfNode() {
    return map.keySet();
  }

  public Tile[] getListofNode() {
    Tile[] res = new Tile[map.keySet().size()];
    Iterator<Tile> it = map.keySet().iterator();
    for (int i = 0; i < map.keySet().size(); i++) {
      res[i] = (Tile) it.next();
    }
    return res;
  }

  public List<Tile> getVoisins(Tile n) {
    return map.get(n);
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }

  // Prints the adjancency list of each Node.
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    for (Tile v : map.keySet()) {
      if (v != null) {
        builder.append(v.toString() + " ==> ");
        for (Tile w : map.get(v)) {
          if (w != null)
            builder.append(w.toString() + " || ");
        }
        builder.append("\n");
      }
    }

    return (builder.toString());
  }
}