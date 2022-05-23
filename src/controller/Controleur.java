/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.GameEngine;
import view.AffichePlateau;

/**
 *
 * @author ludov
 */
public class Controleur implements ActionListener {

  public enum Command {
    RemoveTile;
  }

  AffichePlateau tab;
  GameEngine ge;

  public Controleur(GameEngine gameEngine) {
    ge = gameEngine;
  }

  public void setAfficheur(AffichePlateau t) {
    tab = t;
  }

  public boolean isGameRunning() {
    if (ge == null)
      return false;
    return ge.isGameRunning();
  }

  /**
   ** Retourne la cardinalité du clic sur la tuile (x, y)
   *
   * @param x position x du clic dans la fenêtre
   * @param y position y du clic dans la fenêtre
   * @return String
   */
  public String cardOfClic(int x, int y) {
    x = (x - tab.getOffsetX()) % tab.tailleTuile();
    y = (y - tab.getOffsetY()) % tab.tailleTuile();
    int tileSize = tab.tailleTuile();
    System.out.println(x + ", " + y);
    System.out.println(tileSize);
    if (x >= 0 && x < tileSize / 3) {
      if (y >= (tileSize / 3) && y < ((tileSize * 2) / 3))
        return "w";
    }
    if (x >= (tileSize / 3) && x <= ((tileSize * 2) / 3)) {
      if (y >= 0 && y < (tileSize / 3))
        return "n";
      if (y >= (tileSize / 3) && y < ((tileSize * 2) / 3))
        return "c";
      if (y >= ((tileSize * 2) / 3) && y < tileSize)
        return "s";
    }
    if (x >= ((tileSize * 2) / 3) && x < tileSize) {
      if (y >= (tileSize / 3) && y < ((tileSize * 2) / 3))
        return "e";
    }
    return "";
  }

  /**
   ** Retourne vraie si le clic en (x, y) est sur le plateau
   *
   * @param x int position x du clic
   * @param y int position y du clic
   * @return boolean vraie si le clic est sur le plateau, faux sinon
   */
  public boolean clicOnSet(int x, int y) {
    return x - tab.getOffsetX() >= 0
        && y - tab.getOffsetY() >= 0
        && x - tab.getOffsetX() <= tab.tailleTuile() * ge.getSet().length
        && y - tab.getOffsetY() <= tab.tailleTuile() * ge.getSet().length;
  }

  /**
   ** Retourne vraie si le clic en (x, y) est sur la tuile courante
   * @param x int position x du clic
   * @param y int position y du clic
   * @return boolean craie si le clic est sur la tuile courrante
   */
  public boolean clicOnCurrentTile(int x, int y) {
    return x >= tab.getWidth() - 100
        && x <= tab.getWidth() - 15
        && y >= tab.getHeight() - 100
        && y <= tab.getHeight() - 15;
  }

  /**
   ** Redirige le clic
   * @param x int position x du clic
   * @param y int position y du clic
   */
  public void clic(int x, int y) {
    if (clicOnSet(x, y)) {
      float c = (x - tab.getOffsetX()) / tab.tailleTuile();
      float l = (y - tab.getOffsetY()) / tab.tailleTuile();
      if (c >= 0 && l >= 0) {
        ge.clic((int) c, (int) l, cardOfClic(x, y));
        tab.repaint();
      }
    }
    if (clicOnCurrentTile(x, y)) {
      if (ge.canEndTurn())
        ge.endOfTurn();
      ge.turnCurrentTile();
      tab.repaint();
    }
  }

  public void command(Command cmd) {
    switch (cmd) {
      case RemoveTile:
        ge.removeTile();
        break;

      default:
        break;
    }
    tab.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.print(e.getActionCommand());
    switch (e.getActionCommand()) {

      default:
        System.out.println("Commande invalide");
        break;

    }
  }

}
