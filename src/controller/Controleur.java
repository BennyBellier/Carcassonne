/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import global.Configuration;
import model.GameEngine;
import view.AffichePlateau;

/**
 *
 * @author ludov
 */
public class Controleur implements ActionListener {

  public enum Command {
    RemoveTile,
    EndTurn,
    MenuInGame;
  }

  AffichePlateau tab;
  GameEngine ge;
  JPanel affichageScoreFin;
  JTable scoreboard;

  public Controleur(GameEngine gameEngine, JPanel scoreFin, JTable scoreJTable) {
    ge = gameEngine;
    ge.setControleur(this);
    affichageScoreFin = scoreFin;
    scoreboard = scoreJTable;
  }

  public void setAfficheur(AffichePlateau t) {
    tab = t;
  }

  public boolean isGameRunning() {
    if (ge == null)
      return false;
    return ge.isGameRunning();
  }

  void startGame() {
    if (ge.isIATurn())
      iaPlay();
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
  boolean clicOnSet(int x, int y) {
    return x - tab.getOffsetX() >= 0
        && y - tab.getOffsetY() >= 0
        && x - tab.getOffsetX() <= tab.tailleTuile() * ge.getSet().length
        && y - tab.getOffsetY() <= tab.tailleTuile() * ge.getSet().length;
  }

  boolean clicOnCancel(int x, int y) {
    return false;
  }

  boolean clicOnHand(int x, int y) {
    return x >= tab.getWidth() - 100
        && x <= tab.getWidth() - 15
        && y >= tab.getHeight() - 100
        && y <= tab.getHeight() - 15;
  }

  boolean clickOnCurrentTile(int x, int y) {
    Point start = ge.getStartTilePoint();
    if (!ge.getCurrentTile().placed)
      return false;
    return x - start.x == ge.getCurrentTile().x && y - start.y == ge.getCurrentTile().y;
  }

  public void iaPlay() {
    if (ge.isGameRunning()) {
      while (!ge.IAPlaceTile()) {
      }
      tab.repaint();
      ge.IAPlaceMeeple();
      tab.repaint();
    }
  }

  void placeTile(int x, int y) {
    float c = (x - tab.getOffsetX()) / tab.tailleTuile();
    float l = (y - tab.getOffsetY()) / tab.tailleTuile();
    if (c >= 0 && l >= 0) {
      ge.placeTile((int) c, (int) l);
      tab.repaint();
    }
  }

  void placeMeeple(int x, int y) {
    float c = (x - tab.getOffsetX()) / tab.tailleTuile();
    float l = (y - tab.getOffsetY()) / tab.tailleTuile();
    if (ge.getCurrentTile().placed && clickOnCurrentTile((int) c, (int) l)) {
      System.out.println("placement du meeple");
      ge.placeMeeple((int) c, (int) l, cardOfClic(x, y));
      tab.repaint();
    }
    tab.repaint();
  }

  void undoTile() {
    ge.removeTile();
    tab.repaint();
  }

  void undoMeeple() {
    ge.removeMeeple();
    tab.repaint();
  }

  void endTurn() {
    ge.endOfTurn();
  }

  /**
   ** Redirige le clic
   *
   * @param x int position x du clic
   * @param y int position y du clic
   */
  public void clic(int x, int y) {
    if (ge.isGameRunning()) {
      if (clicOnSet(x, y)) {
        if (!ge.getCurrentTile().placed) {
          placeTile(x, y);
          System.out.println("Pose tuile");
        } else {
          placeMeeple(x, y);
          System.out.println("Pose meeple");
        }
      } else if (clicOnCancel(x, y)) {
        if (ge.getcurrentMeeple() == null) {
          undoMeeple();
          System.out.println("Suppression meeple");
        } else if (ge.getcurrentMeeple() != null && ge.getCurrentTile().placed) {
          undoTile();
          System.out.println("Suppression tuile");
        }
      } else if (clicOnHand(x, y)) {
        if (ge.getCurrentTile().placed) {
          endTurn();
        } else if (!ge.getCurrentTile().placed) {
          ge.turnCurrentTile();
          System.out.println("Rotation tuile");
        }
      }
      tab.repaint();
    }
  }

  public void command(Command cmd) {
    switch (cmd) {
      case RemoveTile:
        ge.removeTile();
        break;
      case EndTurn:
        if (ge.getCurrentTile().placed)
          ge.endOfTurn();
        break;

      default:
        break;
    }
    tab.repaint();
  }

  public void finDeGame() {
    scoreboard.setModel(new javax.swing.table.DefaultTableModel( ge.playersScores(), new String[] {"Joueur", "Nombre de Projets", "Tuiles placées", "Score"}));
    affichageScoreFin.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {

      default:
        Configuration.instance().logger().severe("Commande invalide");
        break;
    }
  }

}
