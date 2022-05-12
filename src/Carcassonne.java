
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Arrays;

import global.Configuration;
import model.*;
import model.Player.Type;
import model.Projects.Project;
import view.Audio;

public class Carcassonne {

  static void setOfTests() {
    GameSet gameSet = new GameSet();
    Pioche p = new Pioche();

    // Saver.save("test1", new Save(0, -1, -1, Tile.getTileFromInt(5), gameSet.cloneSet(), p, Arrays.asList(new Player("joueur1", Type.HUMAN), new Player("IA facile", Type.IA_EASY)), Arrays.asList(new Meeple(0, 1, 1, "s"))));
    // Saver.load("test1");

    // Configuration.instance().logger().fine("Batterie de tests sur le plateau");
    // System.out.println(gameSet.toString());
    // gameSet.addTile(Tile.getTileFromInt(22), -1, 0);
    // Tile t = Tile.getTileFromInt(7);
    // t.turnClock();
    // gameSet.addTile(t, 0, -1);
    // gameSet.addTile(t, 2, 0);
    // gameSet.addTile(Tile.getTileFromInt(0), 1, 0);
    // t = Tile.getTileFromInt(15);
    // t.turnCounterClock();
    // gameSet.addTile(t, 1, -1);
    // t = Tile.getTileFromInt(17);
    // t.turnClock();
    // gameSet.addTile(t, 1, 0);
    // t = Tile.getTileFromInt(1);
    // t.turnClock();
    // t.turnClock();
    // gameSet.addTile(t, 1, 1);

    System.out.println(gameSet.toString());

    // Project.evaluateProjects(gameSet.cloneSet());

    // Configuration.instance().logger().fine("Batterie de tests sur la pioche");

    // for (int j = 0; j < 72; j++) {
    //   t = p.piocheTuile();
    //   if (t != null) {
    //     System.out.println("piece n° : " + (j + 1));
    //     System.out.println("+-----+");
    //     for (int i = 0; i < 3; i++) {
    //       System.out.println("|" + t.toStringArray()[i] + "|");
    //     }
    //     System.out.println("+-----+");
    //   }
    //   if (t != null) {
    //     System.out.println(gameSet.toString());
    //     System.out.println("Position :");
    //     for (Entry<Integer, ArrayList<Integer>> pos : gameSet.tilePositionsAllowed(t, true).entrySet()) {
    //       for (int i = 0; i < pos.getValue().size(); i++) {
    //         System.out.print("(" + pos.getValue().get(i) + ", " + pos.getKey() + ") | ");
    //       }
    //     }
    //     System.out.println();
    //   }
    // }
  }
  public static void main(String[] args) throws Exception {
    Configuration.instance().logger().finest("Lancement de l'application");
    System.out.println(System.getProperty("os.name").toLowerCase());
    System.out.println(System.getProperty("user.home"));
    Audio audioPlayer = new Audio();

    audioPlayer.music.play();
    Thread.sleep(3000);
    audioPlayer.music.stop();
    while (true) {}

    // setOfTests();
  }
}
