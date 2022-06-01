/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
  Timer timer;
  boolean turnSaved;

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
    turnSaved = false;
    timer = new Timer(Integer.parseInt(Configuration.instance().lis("IASpeed")), this);
    timer.start();
  }

  /**
   ** Initialise l'afficheur de jeu
   * @param t
   */
  public void setAfficheur(AffichePlateau t) {
    tab = t;
  }

  /**
   ** Retourne un vraie si une partie est en cours
   */
  public boolean isGameRunning() {
    if (ge == null)
      return false;
    return ge.isGameRunning();
  }

  /**
   ** Met en pause le jeu des IA
   */
  public void pauseGame() {
    timer.stop();
  }

  /**
   ** Redémarre le jeu des IA
   */
  public void resumeGame() {
    timer.start();
  }

  /**
   ** Change la vitesse de jeu d'une IA
   */
  public void setIASpeed(int speed) {
    timer = new Timer(speed, this);
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

  /**
   ** Change l'indicateur qui permet à l'utilisateur de savoir qui doit jouer
   */
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

  /**
   ** Retourne vraie si le clic est sur le bouton cancel
   * @param x
   * @param y
   * @return
   */
  boolean clicOnCancel(int x, int y) {
    return x >= 1570
        && x <= 1700
        && y >= 770
        && y <= 910;
  }

  /**
   ** Retourne vraie si le clic est sur la tuile dans la main
   * @param x
   * @param y
   * @return
   */
  boolean clicOnHand(int x, int y) {
    return x >= 1685
        && x <= 1875
        && y >= 835
        && y <= 1035;
  }

  /**
   ** Retourne vraie si le clic est sur la tuile courante du joueur posé sur le plateau
   * @param x
   * @param y
   * @return
   */
  boolean clickOnCurrentTile(int x, int y) {
    Point start = ge.getStartTilePoint();
    if (!ge.getCurrentTile().placed)
      return false;
    return x - start.x == ge.getCurrentTile().x && y - start.y == ge.getCurrentTile().y;
  }

  /**
   ** Place la tuile
   * @param x
   * @param y
   */
  void placeTile(int x, int y) {
    float c = (x - tab.getOffsetX()) / tab.tailleTuile();
    float l = (y - tab.getOffsetY()) / tab.tailleTuile();
    if (c >= 0 && l >= 0) {
      ge.placeTile((int) c, (int) l);
      tab.repaint();
    }
  }

  /**
   ** Place le meeple
   * @param x
   * @param y
   */
  void placeMeeple(int x, int y) {
    float c = (x - tab.getOffsetX()) / tab.tailleTuile();
    float l = (y - tab.getOffsetY()) / tab.tailleTuile();
    if (ge.getCurrentTile().placed && clickOnCurrentTile((int) c, (int) l)) {
      ge.placeMeeple(cardOfClic(x, y));
      tab.repaint();
    }
    tab.repaint();
  }

  /**
   ** Annule le palcement de la tuile
   */
  void undoTile() {
    ge.removeTile();
    tab.repaint();
  }

  /**
   ** Annule le placement du meeple
   */
  void undoMeeple() {
    ge.removeMeeple();
    tab.repaint();
  }

  /**
   ** Met fin au tour d'un joueur
   */
  void endTurn() {
    ge.endOfTurn();
    ge.saveTurn();
    switchLueur();
  }

  /**
   ** Sauvegarde la partie
   * @param file
   */
  public void saveGame(String file) {
    ge.saveGame(file);
  }

  /**
   ** Annule les coups des IA
   */
  public void rewind() {
    ge = ge.rewind();
    tab.setGameEngine(ge);
  }

  /**
   ** Active la suggestion de l'IA
   */
  public void activateAideIA() {
    tab.activateAideIA(ge.IAPreferedPlay());
    tab.repaint();
  }

  /**
   ** Redirige le clic
   *
   * @param x int position x du clic
   * @param y int position y du clic
   */
  public void clic(int x, int y) {
    if (ge.isGameRunning() && !ge.isIATurn()) {

      if (!turnSaved && !ge.getCurrentTile().placed && ge.getcurrentMeeple() == null) {
        ge.saveTurn();
        turnSaved = false;
      }

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
          tab.desactivateAideIA();
        } else {
          if (ge.getcurrentMeeple() == null) {
            placeMeeple(x, y);
          }
        }
      }
      tab.repaint();
    }
  }

  /**
   ** Affiche le tableau des scores à la fin de la partie trié en fonction du score
   */
  public void finDeGame() {
    String[][] playersScores = ge.playersScores();
    scoreboard.setModel(new javax.swing.table.DefaultTableModel(playersScores,
        new String[] { "Joueurs", "Nombre de Projets", "Tuiles placées", "Score" }) {
      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
      }
    });

    TableRowSorter<TableModel> sorter = new TableRowSorter<>(scoreboard.getModel());
    scoreboard.setRowSorter(sorter);
    List<RowSorter.SortKey> sortKeys = new ArrayList<>();
    sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));
    sorter.setSortKeys(sortKeys);
    sorter.sort();

    DefaultTableCellRenderer render = new DefaultTableCellRenderer();
    render.setHorizontalAlignment(SwingConstants.CENTER);
    render.setOpaque(false);

    for (int j = 0; j < 4; j++) {
      scoreboard.getColumnModel().getColumn(j).setCellRenderer(render);
    }

    affichageScoreFin.setVisible(true);
    menuBoutons.setVisible(false);

  }

  /**
   ** Jeu automatique des IA
   */
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
        IAPlaying = false;
      }
    }
  }
}
