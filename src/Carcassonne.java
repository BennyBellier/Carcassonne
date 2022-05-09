
import java.util.Map.Entry;
import java.util.ArrayList;


import global.Configuration;
import model.*;
import model.Projects.Project;
import model.Tiles.Tile;

public class Carcassonne {

  public static void main(String[] args) throws Exception {
    Configuration.instance().logger().finest("Lancement de l'application");
    GameSet gameSet = new GameSet();
    Pioche p = new Pioche();

    Configuration.instance().logger().fine("Batterie de tests sur le plateau");
    System.out.println(gameSet.toString());
    gameSet.addTile(Tile.getTileFromInt(22), -1, 0);
    Tile t = Tile.getTileFromInt(7);
    t.turnClock();
    gameSet.addTile(t, 0, -1);
    gameSet.addTile(t, 2, 0);
    gameSet.addTile(Tile.getTileFromInt(0), 1, 0);
    t = Tile.getTileFromInt(15);
    t.turnCounterClock();
    gameSet.addTile(t, 1, -1);
    t = Tile.getTileFromInt(17);
    t.turnClock();
    gameSet.addTile(t, 1, 0);
    t = Tile.getTileFromInt(1);
    t.turnClock();
    t.turnClock();
    gameSet.addTile(t, 1, 1);

    System.out.println(gameSet.toString());

    Project.evaluateProjects(gameSet.cloneSet());

    Configuration.instance().logger().fine("Batterie de tests sur la pioche");

    for (int j = 0; j < 72; j++) {
      t = p.piocheTuile();
      if (t != null) {
        System.out.println("piece n° : " + (j + 1));
        System.out.println("+-----+");
        for (int i = 0; i < 3; i++) {
          System.out.println("|" + t.toStringArray()[i] + "|");
        }
        System.out.println("+-----+");
      }
      if (t != null) {
        System.out.println(gameSet.toString());
        System.out.println("Position :");
        for (Entry<Integer, ArrayList<Integer>> pos : gameSet.tilePositionsAllowed(t, true).entrySet()) {
          for (int i = 0; i < pos.getValue().size(); i++) {
            System.out.print("(" + pos.getValue().get(i) + ", " + pos.getKey() + ") | ");
          }
        }
        System.out.println();
      }
    }
  }
}
