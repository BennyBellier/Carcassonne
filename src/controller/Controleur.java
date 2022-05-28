/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

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
  JButton menuBoutons;
  boolean IAPlaying;
  //JLabel lueurJ1 , lueurJ2 , lueurJ3 , lueurJ4 , lueurJ5;  

  public Controleur(GameEngine gameEngine, JPanel scoreFin, JTable scoreJTable, JButton menuPlateau  /*JLabel tourJ1 ,JLabel tourJ2 ,JLabel tourJ3 ,JLabel tourJ4 ,JLabel tourJ5*/) {
    ge = gameEngine;
    ge.setControleur(this);
    affichageScoreFin = scoreFin;
    scoreboard = scoreJTable;
    menuBoutons = menuPlateau;
    /*tourJ1 = lueurJ1;
    tourJ2 = lueurJ2;
    tourJ3 = lueurJ3;
    tourJ4 = lueurJ4;
    tourJ5 = lueurJ5;*/
    IAPlaying = false;
  }

  public void setAfficheur(AffichePlateau t) {
    tab = t;
  }

  public boolean isGameRunning() {
    if (ge == null)
      return false;
    return ge.isGameRunning();
  }

  public void startGame() {
    if (ge.isIATurn())
      iaPlay();
  }

  /*public void lueur2J(){
    if (!lueurJ2.isVisible()){
      lueurJ2.setVisible(true);
      lueurJ1.setVisible(false);
    } else {
      lueurJ2.setVisible(false);
      lueurJ1.setVisible(true);
    }
  }*/

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
    return x >= 1570
        && x <= 1700
        && y >= 770
        && y <= 910;
  }

  boolean clicOnHand(int x, int y) {
    return x >= 1685
        && x <= 1875
        && y >= 835
        && y <= 1035;
  }

  boolean clickOnCurrentTile(int x, int y) {
    Point start = ge.getStartTilePoint();
    if (!ge.getCurrentTile().placed)
      return false;
    return x - start.x == ge.getCurrentTile().x && y - start.y == ge.getCurrentTile().y;
  }

  public void iaPlay() {
    if (ge.isGameRunning()) {
      IAPlaying = true;
      while (!ge.IAPlaceTile()) {
      }
      tab.repaint();
      ge.IAPlaceMeeple();
      tab.repaint();
      ge.endOfTurn();
      tab.repaint();
      IAPlaying = false;

      if (ge.isIATurn())
        iaPlay();
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
      ge.placeMeeple(cardOfClic(x, y));
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
    if (ge.isGameRunning() && !IAPlaying) {
      if (clicOnSet(x, y)) {
        if (!ge.getCurrentTile().placed) {
          placeTile(x, y);
          if (ge.getCurrentTile().placed)
            tab.afficherRefaire();
        } else {
          if (ge.getcurrentMeeple() == null) {
            placeMeeple(x, y);
            tab.afficherRefaire();
          }
        }
      } else if (clicOnHand(x, y)) {
        if (ge.getCurrentTile().placed) {
          endTurn();
          tab.afficherPioche();
          if (ge.isIATurn())
            iaPlay();
        } else if (!ge.getCurrentTile().placed) {
          ge.turnCurrentTile();
          tab.afficherPioche();
        }
      } else if (clicOnCancel(x, y)) {
        if (ge.getcurrentMeeple() != null) {
          undoMeeple();
          tab.afficherRefaire();
        } else if (ge.getcurrentMeeple() == null && ge.getCurrentTile().placed) {
          undoTile();
          tab.afficherPioche();
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
    String[][] playersScores = ge.playersScores();
    scoreboard.setModel(new javax.swing.table.DefaultTableModel(playersScores,
        new String[] { "Joueurs", "Nombre de Projets", "Tuiles placées", "Score" }) {
      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
      }
    });

    DefaultTableCellRenderer render = new DefaultTableCellRenderer();
    render.setHorizontalAlignment(SwingConstants.CENTER);
    render.setOpaque(false);

    for (int j = 0; j < 4; j++) {
      scoreboard.getColumnModel().getColumn(j).setCellRenderer(render);
    }

    affichageScoreFin.setVisible(true);
    menuBoutons.setVisible(false);

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
