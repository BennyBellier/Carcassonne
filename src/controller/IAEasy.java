package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.GameSet;
import model.Meeple;
import model.Tile;

public class IAEasy implements IA {
  private Random rand;

  public IAEasy() {
    rand = new Random();
  }

  public int[] placeTile(int id, GameSet gs, Tile currentTile, List<Meeple> meeples) {
    int rota = rand.nextInt(3);

    for (int i = 0; i < rota; i++) {
      currentTile.turnClock();
    }

    while (gs.tilePositionsAllowed(currentTile, false).keySet().size() == 0) {
      currentTile.turnClock();
    }

    Map<Integer, ArrayList<Integer>> pos = gs.tilePositionsAllowed(currentTile, false);

    List<Integer> iList = new ArrayList<>(pos.keySet());
    int i = iList.get(rand.nextInt(iList.size()));

    List<Integer> jList = pos.get(i);
    int j = jList.get(rand.nextInt(jList.size()));

    return new int[] { j, i };
  }

  public String placeMeeple(List<String> meeplesPositions) {
    if (rand.nextInt(100) < 95) {
      return meeplesPositions.get(rand.nextInt(meeplesPositions.size()));
    }
    return null;
  }
}