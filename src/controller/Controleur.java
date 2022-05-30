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
  Animation animIA;
  Timer timer;

  JLabel lueurJ1, lueurJ2, lueurJ3, lueurJ4, lueurJ5;

  public Controleur(GameEngine gameEngine, JPanel scoreFin, JTable scoreJTable, JButton menuPlateau, JLabel tourJ1,
      JLabel tourJ2, JLabel tourJ3, JLabel tourJ4, JLabel tourJ5) {
    ge = gameEngine;
    ge.setControleur(this);
    affichageScoreFin = scoreFin;
    scoreboard = scoreJTable;
    menuBoutons = menuPlateau;
    lueurJ1 = tourJ1;
    lueurJ2 = tourJ2;
    lueurJ3 = tourJ3;
    lueurJ4 = tourJ4;
    lueurJ5 = tourJ5;
    IAPlaying = false;
    timer = new Timer(500, this);
    timer.start();
  }

  public void setAfficheur(AffichePlateau t) {
    tab = t;
  }

  public boolean isGameRunning() {
    if (ge == null)
      return false;
    return ge.isGameRunning();
  }

  public void lueur2J() {
    if (!lueurJ2.isVisible()) {
      lueurJ2.setVisible(true);
      lueurJ1.setVisible(false);
    } else {
      lueurJ2.setVisible(false);
      lueurJ1.setVisible(true);
    }
  }

  public void lueur3J() {
    if (!lueurJ2.isVisible() && !lueurJ3.isVisible()) {
      lueurJ2.setVisible(true);
      lueurJ1.setVisible(false);
      lueurJ3.setVisible(false);
    } else if (!lueurJ3.isVisible() && !lueurJ1.isVisible()) {
      lueurJ2.setVisible(false);
      lueurJ3.setVisible(true);
      lueurJ1.setVisible(false);
    } else {
      lueurJ1.setVisible(true);
      lueurJ2.setVisible(false);
      lueurJ3.setVisible(false);
    }
  }

  public void lueur4J() {
    if (!lueurJ2.isVisible() && !lueurJ3.isVisible() && !lueurJ4.isVisible()) {
      lueurJ2.setVisible(true);
      lueurJ1.setVisible(false);
      lueurJ3.setVisible(false);
      lueurJ4.setVisible(false);
    } else if (!lueurJ3.isVisible() && !lueurJ1.isVisible() && !lueurJ4.isVisible()) {
      lueurJ2.setVisible(false);
      lueurJ3.setVisible(true);
      lueurJ1.setVisible(false);
      lueurJ4.setVisible(false);
    } else if (!lueurJ4.isVisible() && !lueurJ1.isVisible() && !lueurJ2.isVisible()) {
      lueurJ1.setVisible(false);
      lueurJ2.setVisible(false);
      lueurJ3.setVisible(false);
      lueurJ4.setVisible(true);
    } else {
      lueurJ1.setVisible(true);
      lueurJ2.setVisible(false);
      lueurJ3.setVisible(false);
      lueurJ4.setVisible(false);
    }
  }

  public void lueur5J() {
    if (!lueurJ2.isVisible() && !lueurJ3.isVisible() && !lueurJ4.isVisible() && !lueurJ5.isVisible()) {
      lueurJ2.setVisible(true);
      lueurJ1.setVisible(false);
      lueurJ3.setVisible(false);
      lueurJ4.setVisible(false);
      lueurJ5.setVisible(false);
    } else if (!lueurJ3.isVisible() && !lueurJ1.isVisible() && !lueurJ4.isVisible() && !lueurJ5.isVisible()) {
      lueurJ2.setVisible(false);
      lueurJ3.setVisible(true);
      lueurJ1.setVisible(false);
      lueurJ4.setVisible(false);
      lueurJ5.setVisible(false);
    } else if (!lueurJ4.isVisible() && !lueurJ1.isVisible() && !lueurJ2.isVisible() && !lueurJ5.isVisible()) {
      lueurJ1.setVisible(false);
      lueurJ2.setVisible(false);
      lueurJ3.setVisible(false);
      lueurJ4.setVisible(true);
      lueurJ5.setVisible(false);
    } else if (!lueurJ5.isVisible() && !lueurJ1.isVisible() && !lueurJ2.isVisible() && !lueurJ3.isVisible()) {
      lueurJ1.setVisible(false);
      lueurJ2.setVisible(false);
      lueurJ3.setVisible(false);
      lueurJ4.setVisible(false);
      lueurJ5.setVisible(true);
    } else {
      lueurJ1.setVisible(true);
      lueurJ2.setVisible(false);
      lueurJ3.setVisible(false);
      lueurJ4.setVisible(false);
      lueurJ5.setVisible(false);
    }
  }

  public void switchLueur() {
    if (ge.getListPlayers().size() == 2) {
      lueur2J();
    } else if (ge.getListPlayers().size() == 3) {
      lueur3J();
    } else if (ge.getListPlayers().size() == 4) {
      lueur4J();
    } else {
      lueur5J();
    }
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
    switchLueur();
  }

  public void saveGame(String file) {
    ge.saveGame(file);
  }

  public GameEngine rewind() {
    GameEngine newGe = ge.rewind();
    ge = newGe;
    return newGe;
  }

  /**
   ** Redirige le clic
   *
   * @param x int position x du clic
   * @param y int position y du clic
   */
  public void clic(int x, int y) {
    if (ge.isGameRunning() && !ge.isIATurn()) {
       if (clicOnHand(x, y)) {
        if (ge.getCurrentTile().placed) {
          endTurn();
        } else if (!ge.getCurrentTile().placed) {
          ge.turnCurrentTile();
        }
      } else if (clicOnCancel(x, y)) {
        if (ge.getcurrentMeeple() != null) {
          undoMeeple();
        } else if (ge.getcurrentMeeple() == null && ge.getCurrentTile().placed) {
          undoTile();
        }
      } else if (clicOnSet(x, y)) {
        if (!ge.getCurrentTile().placed) {
          placeTile(x, y);
        } else {
          if (ge.getcurrentMeeple() == null) {
            placeMeeple(x, y);
          }
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
    if (ge.isGameRunning() && ge.isIATurn()) {
      IAPlaying = true;
      if (!ge.getCurrentTile().placed) {
        while (!ge.IAPlaceTile()) {
        }
        tab.repaint();
      }
      else if (ge.getCurrentTile().placed) {
        ge.IAPlaceMeeple();
        tab.repaint();
        ge.endOfTurn();
        switchLueur();
        tab.repaint();
        IAPlaying = false;
      }

    }
  }
}
