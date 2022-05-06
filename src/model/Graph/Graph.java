package model.Graph;

// Java program to implement Graph
// with the help of Generics

import java.util.*;

public class Graph<T> {

  // We use Hashmap to store the edges in the graph
  private Map<T, List<T>> map = new HashMap<>();

  // This function adds a new Node to the graph
  public void addNode(T s) {
    map.put(s, new LinkedList<T>());
  }

  // This function adds the edge
  // between source to destination
  public void addEdge(T source, T destination) {

    if (!map.containsKey(source))
      addNode(source);

    if (!hasEdge(source, destination)){
      map.get(source).add(destination);
    }
  }

  @SuppressWarnings("unchecked")
  public T getFirstNode() {
    return (T)map.entrySet().toArray()[0];
  }

  // This function gives the count of vertices
  public int getNodeCount() {
    return map.keySet().size();
  }

  // This function gives the count of edges
  public void getEdgesCount() {
    int count = 0;
    for (T v : map.keySet()) {
      count += map.get(v).size();
    }
    count = count / 2;
  }

  // This function gives whether
  // a Node is present or not.
  public boolean hasNode(T s) {
    if (map.containsKey(s))
      return true;
    else
      return false;
  }

  // This function gives whether an edge is present or not.
  public boolean hasEdge(T s, T d) {
    if (map.get(s) != null && map.get(s).contains(d))
      return true;
    else
      return false;
  }

  public Set<T> getListOfNode() {
    return map.keySet();
  }

  public List<T> getVoisins(T n) {
    return map.get(n);
  }

  // Prints the adjancency list of each Node.
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    for (T v : map.keySet()) {
      if (v != null) {
        builder.append(v.toString() + " -> ");
        for (T w : map.get(v)) {
          if (w != null)
            builder.append(w.toString() + " | ");
        }
        builder.append("\n");
      }
    }

    return (builder.toString());
  }
}