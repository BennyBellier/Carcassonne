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
      
      default:
        System.out.println("Commande invalide");
        break;

    }
  }

}
