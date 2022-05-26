package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.GameSet;
import model.Tile;

public class IAEasy extends IA {

  private Random rand;

  public IAEasy(int nbMeeples) {
    rand = new Random();
  }

  public String placeMeeple(List<String> meeplesPositions) {
    if (rand.nextInt(100) < 95) {
      return meeplesPositions.get(rand.nextInt(meeplesPositions.size()));
    }
    return null;
  }

  public int[] placeTile(GameSet gs, Tile currentTile) {
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

    return new int[] {j, i};
  }
}
