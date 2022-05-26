package view;

import controller.Controleur;
import controller.Controleur.Command;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keybord extends KeyAdapter {
  Controleur c;

  public Keybord() {}

  public void setControleur(Controleur controleur) {
    c = controleur;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (c != null) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_DELETE:
          c.command(Command.RemoveTile);
          break;
        case KeyEvent.VK_SPACE:
          c.command(Command.EndTurn);
          break;
        case KeyEvent.VK_ESCAPE:
          c.command(Command.MenuInGame);
          break;

        default:
          break;
      }
    }
  }
}
