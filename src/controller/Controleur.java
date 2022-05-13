/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Frames;

/**
 *
 * @author ludov
 */
public class Controleur implements ActionListener {

  Frames f;

  public Controleur() {

  }

  public void getInterface(Frames f) {
    this.f = f;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "continuer":

        break;
      case "charger":
        break;
      case "classement":

        break;
      case "credits":
         f.menuPrincipale.setVisible(false);
         f.menuCredits.setVisible(true);
        break;
      case "options":
         f.menuPrincipale.setVisible(false);
         f.menuOptions.setVisible(true);
        break;
      case "quitter":
        String[] options = { "Oui", "Non" };
        int reply = f.quitterOptionPane.showOptionDialog(null, "Êtes-vous sûr.e de vouloir quitter le jeu ?", "Quitter le jeu ?",f.quitterOptionPane.YES_NO_OPTION, f.quitterOptionPane.QUESTION_MESSAGE, null, options, null);
        if (reply == f.quitterOptionPane.YES_OPTION) {
            System.exit(0);
        }
        break;
      case "vsHumain":
        break;
      case "vsBot":
        break;
      case "regles":
         f.menuPrincipale.setVisible(false);
         f.reglesPanel.setVisible(true);
        break;
      case "retourMP":
        f.menuPrincipale();
        break;
      default:
        System.out.println("Commande invalide");
        break;

    }
  }

}
