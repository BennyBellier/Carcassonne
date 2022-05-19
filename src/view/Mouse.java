package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.Controleur;

public class Mouse extends MouseAdapter {

  Controleur c;

  public Mouse(AffichePlateau a, Controleur control) {
    c = control;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    c.clic(e.getX(), e.getY());
  }
}
