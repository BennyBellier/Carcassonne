package view;

import controller.Controleur;
import controller.Controleur.Command;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keybord extends KeyAdapter {
  Controleur c;

  public Keybord(Controleur controleur) {
    c = controleur;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_DELETE:
        c.command(Command.RemoveTile);
        break;

      default:
        break;
    }
  }
}
