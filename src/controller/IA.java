package controller;

import java.util.List;

import model.GameSet;
import model.Meeple;
import model.Tile;

public interface IA {
  public int[] placeTile(int id, GameSet gs, Tile currentTile, List<Meeple> meeples);
  public String placeMeeple(List<String> meeplesPositions);
}
