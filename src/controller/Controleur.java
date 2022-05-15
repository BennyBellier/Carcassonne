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

  AffichePlateau tab;
  GameEngine ge;

  public Controleur(GameEngine gameEngine) {
    ge = gameEngine;
  }

  public void getAfficheur(AffichePlateau t) {
    tab = t;
  }

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

  public void clic(int x, int y) {
    if (x - tab.getOffsetX() >= 0 && y - tab.getOffsetY() >= 0) {
      if (x >= tab.getWidth() - 100 && x <= tab.getWidth() - 15 && y >= tab.getHeight() - 100
          && y <= tab.getHeight() - 15) {
            if (ge.canEndTurn())
              ge.endOfTurn();
        ge.turnCurrentTile();
      } else {
        float c = (x - tab.getOffsetX()) / tab.tailleTuile();
        float l = (y - tab.getOffsetY()) / tab.tailleTuile();
        if (c >= 0 && l >= 0) {
          ge.clic((int) c, (int) l, cardOfClic(x, y));
        }
      }
      tab.repaint();
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.print(e.getActionCommand());
    switch (e.getActionCommand()) {
      case "continuer":

        break;
      case "charger":
        break;
      case "classement":

        break;
      case "credits":
        // f.menuPanel.setVisible(false);
        // f.creditsPanel.setVisible(true);
        break;
      case "options":
        // f.menuPanel.setVisible(false);
        // f.optionsPanel.setVisible(true);
        break;
      case "quitter":
        // String[] options = { "Oui", "Non" };
        // int reply = f.quitterOptionPane.showOptionDialog(null, "Êtes-vous sûr.e de
        // vouloir quitter le jeu ?",
        // "Quitter le jeu ?",
        // f.quitterOptionPane.YES_NO_OPTION, f.quitterOptionPane.QUESTION_MESSAGE,
        // null, options, null);
        // if (reply == f.quitterOptionPane.YES_OPTION) {
        // System.exit(0);
        // }
        break;
      case "vsHumain":
        break;
      case "vsBot":
        break;
      case "regles":
        // f.menuPanel.setVisible(false);
        // f.reglesPanel.setVisible(true);
        break;
      case "retourMP":
        // f.afficherMP();
        break;
      default:
        System.out.println("Commande invalide");
        break;

    }
  }

}
