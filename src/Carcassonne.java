
import global.Configuration;
import model.*;
import model.Player.Type;
import view.AffichePlateau;

import view.Frame;
import view.Mouse;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controller.Controleur;


public class Carcassonne {
  public static void window() {
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new Frame().setVisible(true);
      }
    });
  }

  static void setOfTests() {
    GameSet gameSet = new GameSet();
    Pioche p = new Pioche();

    // Saver.save("test1", new Save(0, -1, -1, Tile.getTileFromInt(5),
    //     gameSet.cloneSet(), p, Arrays.asList(new Player("joueur1", Type.HUMAN), new Player("IA facile", Type.IA_EASY)),
    //     Arrays.asList(new Meeple(0, 1, 1,
    //         "s"))));
    // Saver.load("test1");

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

    // Project.evaluateProjects(gameSet.cloneSet());

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
        // for (Entry<Integer, ArrayList<Integer>> pos : gameSet.tilePositionsAllowed(t,
        //     true).entrySet()) {
        //   for (int i = 0; i < pos.getValue().size(); i++) {
        //     System.out.print("(" + pos.getValue().get(i) + ", " + pos.getKey() + ") | ");
        //   }
        // }
        System.out.println();
      }
    }
  }

  static void startGame() {
    GameEngine gm = new GameEngine(new Player("Grizondox", Type.HUMAN, 0x00ff00), /* new Player("Wall-E", Type.IA_EASY, 0x000000), */ new Player("Benny", Type.HUMAN, 0x101eff));

    AffichePlateau affPlat = new AffichePlateau(gm);

    Controleur c = new Controleur(gm);
    c.setAfficheur(affPlat);
    affPlat.addMouseListener(new Mouse(affPlat, c));

    JFrame frame = new JFrame();
    frame.setMinimumSize(new Dimension(853, 480));
    frame.setMaximumSize(new Dimension(1920, 1080));
    frame.add(affPlat);
    frame.setSize(new Dimension(1080, 720));
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  public static void main(String[] args) throws Exception {
    Configuration.instance().logger().finest("Lancement de l'application");
    System.out.println(System.getProperty("os.name").toLowerCase());
    System.out.println(System.getProperty("user.home"));
    startGame();
  }
}
