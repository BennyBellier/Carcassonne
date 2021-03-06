package view;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JLabel;

import java.awt.*;

import model.*;
import model.Tile.Type;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ludov
 */
public class AffichePlateau extends JComponent {

  GameEngine gm;
  ArrayList<ArrayList<Image>> images;
  Image blason, meeplePossibility;
  int tileSize, startX, startY;
  Random rand;
  Graphics2D drawable;
  CurrentTile currentTile;
  Images imgs;
  Font font;
  List<ScoreEntryPlayer> playersScores;
  JLabel nbTuileRestante, pioche, refaire, valider;
  AfficheCurrentTile act;
  boolean aide;
  boolean aideIA;
  Point suggestIA;

  public AffichePlateau(JLabel pioche, JLabel refaire, JLabel valider, AfficheCurrentTile act) {
    imgs = new Images();
    images = imgs.list;
    blason = imgs.blason;
    meeplePossibility = imgs.meeplePossibility;
    this.pioche = pioche;
    this.refaire = refaire;
    this.act = act;
    valider.setVisible(false);
    aide = true;
    aideIA = false;
    afficherPioche();
    repaint();
  }

  public void setGameEngine(GameEngine gameEngine) {
    gm = gameEngine;
  }

  public void setFont(Font font) {
    this.font = font;
  }

  public void setLabelScore(JLabel... labelListe) {
    playersScores = new ArrayList<>();
    int nbLabel = (labelListe.length - 1) / 2;
    if (nbLabel >= 2) {
      playersScores.add(new ScoreEntryPlayer(labelListe[0], labelListe[1]));
      playersScores.add(new ScoreEntryPlayer(labelListe[2], labelListe[3]));
    }
    if (nbLabel >= 3) {
      playersScores.add(new ScoreEntryPlayer(labelListe[4], labelListe[5]));
    }
    if (nbLabel >= 4) {
      playersScores.add(new ScoreEntryPlayer(labelListe[6], labelListe[7]));
    }
    if (nbLabel >= 5) {
      playersScores.add(new ScoreEntryPlayer(labelListe[8], labelListe[9]));
    }
    nbTuileRestante = labelListe[labelListe.length - 1];
  }

  /**
   ** Retourne la taille courante de la tuile
   *
   * @return int
   */
  public int tailleTuile() {
    return tileSize;
  }

  /**
   ** Retourne le d??calage x du plateau
   *
   * @return int
   */
  public int getOffsetX() {
    return startX;
  }

  /**
   ** Retourne le d??calage Y du plateau
   *
   * @return int
   */
  public int getOffsetY() {
    return startY;
  }

  public void afficherPioche() {
    pioche.setVisible(true);
    refaire.setVisible(false);
  }

  public void afficherRefaire() {
    refaire.setVisible(true);
    pioche.setVisible(false);
  }

  /**
   ** Active la suggestion
   */
  public void activateAideIA(Point p) {
    aideIA = true;
    suggestIA = p;
  }

  /**
   ** D??sactive la suggestion
   */
  public void desactivateAideIA() {
    aideIA = false;
  }

  public boolean getStateAideIA() {
    return aideIA;
  }

  /**
   ** D??finie la taille de la tuile
   */
  void getTileSize() {
    int nbCase = gm.getSet().length;
    int tileWidth = getSize().width / nbCase;
    int tileHeight = getSize().height / nbCase;

    if (tileWidth > tileHeight) {
      tileSize = tileHeight;
      startY = 0;
      startX = (getSize().width - (nbCase * tileSize)) / 2;
    } else {
      tileSize = tileWidth;
      startX = 0;
      startY = (getSize().height - (nbCase * tileSize)) / 2;
    }
  }

  /**
   ** Affiche les meeples sur le plateau
   */
  public void meeplePaint() {
    rand = new Random(11000);
    int meepleSize = tileSize / 5;
    int alea = rand.nextInt(meepleSize / 2);

    int meepleHeight = meepleSize * 100 / 125;
    int meepleWidth = meepleSize * 100 / 130;

    for (Meeple m : gm.meeplesDisplay()) {
      int meepleX = startX, meepleY = startY - meepleHeight / 2;
      switch (m.getCardinal()) {
        case "c":
          meepleX += ((tileSize / 2) - (meepleWidth / 2)) + alea;
          meepleY += ((tileSize / 2) - (meepleHeight / 2)) + alea;
          break;
        case "n":
          meepleY += (tileSize * 0.03) + alea;
          meepleX += ((tileSize / 2) - (meepleWidth / 2)) + alea;
          break;
        case "e":
          meepleX += tileSize - (meepleWidth + tileSize * 0.01);
          meepleY += ((tileSize / 2) - (meepleHeight / 2)) + alea;
          break;
        case "s":
          meepleX += ((tileSize / 2) - (meepleWidth / 2)) + alea;
          meepleY += tileSize - (meepleHeight + tileSize * 0.01);
          break;
        case "w":
          meepleX += tileSize * 0.03;
          meepleY += ((tileSize / 2) - (meepleHeight / 2)) + alea;
          break;
      }
      meepleX += ((m.getY() + gm.getStartTilePoint().y) * tileSize);
      meepleY += ((m.getX() + gm.getStartTilePoint().x) * tileSize);
      drawable.drawImage(imgs.Meeple(gm.getListPlayers().get(m.getOwner()).color()), meepleX, meepleY, meepleHeight,
          meepleWidth, null);
    }
  }

  /**
   ** Affiche les placements possibles des meeples sur la tuile courante
   */
  void meeplePlacementPaint() {
    int meepleSize = tileSize / 2;

    int meepleHeight = meepleSize * 100 / 247;
    int meepleWidth = meepleSize * 100 / 162;

    for (String card : gm.getMeeplePositions()) {
      int meepleX = startX, meepleY = startY - meepleHeight / 2;
      switch (card) {
        case "c":
          meepleX += ((tileSize / 2) - (meepleWidth / 2));
          meepleY += ((tileSize / 2) - (meepleHeight / 2));
          break;
        case "n":
          meepleY += (tileSize * 0.03);
          meepleX += ((tileSize / 2) - (meepleWidth / 2));
          break;
        case "e":
          meepleX += tileSize - (meepleWidth + tileSize * 0.01);
          meepleY += ((tileSize / 2) - (meepleHeight / 2));
          break;
        case "s":
          meepleX += ((tileSize / 2) - (meepleWidth / 2));
          meepleY += tileSize - (meepleHeight + tileSize * 0.01);
          break;
        case "w":
          meepleX += tileSize * 0.03;
          meepleY += ((tileSize / 2) - (meepleHeight / 2));
          break;
      }
      meepleX += ((currentTile.x + gm.getStartTilePoint().y) * tileSize);
      meepleY += ((currentTile.y + gm.getStartTilePoint().x) * tileSize);
      drawable.drawImage(imgs.hollowMeeple(gm.getListPlayers().get(gm.getPlayerTurn()).color()), meepleX, meepleY,
          meepleHeight, meepleWidth, null);
    }
  }

  /**
   ** Affiche les blasons des tuiles
   *
   * @param x position x du coin sup??rieur gauche ou placer le blason
   * @param y position y du coin sup??rieur gauche ou placer le blason
   */
  public void drawBlason(int x, int y) {
    double blasonSize = (tileSize * 0.5);
    drawable.drawImage(blason, x, y, (int) (0.29 * blasonSize), (int) (0.39 * blasonSize), null);
  }

  /**
   ** Met ?? jour le score des joueurs sur l'overlay
   */
  void updateScoreBoard() {
    List<Player> players = gm.getListPlayers();
    for (int i = 0; i < playersScores.size(); i++) {
      playersScores.get(i).score.setText(String.valueOf(players.get(i).score()));
      playersScores.get(i).nbMeeple.setText(String.valueOf(players.get(i).nbMeeplesRestant()));
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    updateScoreBoard();
    nbTuileRestante.setText(String.valueOf(gm.getPiocheSize()));
    act.repaint();
    if (gm != null) {
      drawable = (Graphics2D) g;
      drawable.clearRect(0, 0, getSize().width, getSize().height);
      drawable.drawImage(imgs.plateauInGame, 0, 0, getSize().width, getSize().height, null);
      Tile[][] plateau = gm.getSet();
      currentTile = gm.getCurrentTile();
      getTileSize();
      afficherPioche();

      for (int i = 0; i < plateau.length; i++) {
        for (int j = 0; j < plateau[i].length; j++) {
          if (plateau[i][j] != null) {
            drawable.drawImage(getImage(plateau[i][j]), startX + j * tileSize, startY + i * tileSize, tileSize,
                tileSize, null);
            if (plateau[i][j].blason())
              drawBlason((int) (startX + tileSize * 0.15 + j * tileSize),
                  (int) (startY + tileSize * 0.15 + i * tileSize));
          }
        }
      }
      if (font != null)
        drawable.setFont(font.deriveFont((float) 40));

      if (gm.isIATurn() || currentTile.tile == null) {
        act.setVisible(false);
      } else {
        if (!currentTile.placed) {
          act.setVisible(true);
          act.img = getImage(currentTile.tile);
          if (currentTile.tile.blason()) {
            act.blason = blason;
          }
          act.repaint();
          int resize = tileSize / 15;
          if (aideIA) {
            drawable.drawImage(imgs.highlight, (startX + suggestIA.y * tileSize) + (resize),
                (startY + suggestIA.x * tileSize) + (resize), (tileSize) - (resize * 2), (tileSize) - (resize * 2),
                null);
          } else if (aide) {
            Map<Integer, ArrayList<Integer>> allPossiblePlacement = gm.getGameSet().tilePositionsAllowed(
                currentTile.tile,
                true);
            Map<Integer, ArrayList<Integer>> possiblePlacement = gm.getCurrentTilePositions();

            for (Integer i : allPossiblePlacement.keySet()) {
              for (Integer j : allPossiblePlacement.get(i)) {
                if (possiblePlacement.containsKey(i) && possiblePlacement.get(i).contains(j))
                  drawable.drawImage(imgs.highlight, (startX + j * tileSize) + (resize),
                      (startY + i * tileSize) + (resize), (tileSize) - (resize * 2), (tileSize) - (resize * 2), null);
              }
            }
          }
        } else {
          afficherRefaire();
          act.setVisible(false);
          meeplePlacementPaint();
        }
      }
      meeplePaint();
    }
  }

  /**
   ** Retourne l'image correspondant ?? la tuile t
   *
   * @param t Tile
   * @return Image
   */
  public Image getImage(Tile t) {
    if (t.center() == Type.ABBEY && t.north() == Type.FIELD && t.east() == Type.FIELD && t.south() == Type.FIELD
        && t.west() == Type.FIELD)
      return images.get(0).get(0);
    else if (t.center() == Type.ABBEY
        && (t.north() == Type.ROAD || t.east() == Type.ROAD || t.south() == Type.ROAD || t.west() == Type.ROAD)) {
      if (t.south() == Type.ROAD)
        return images.get(1).get(0);
      else if (t.west() == Type.ROAD)
        return images.get(1).get(1);
      else if (t.east() == Type.ROAD)
        return images.get(1).get(3);
      else
        return images.get(1).get(2);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.CITY)
      return images.get(2).get(0);
    else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.FIELD
        && t.west() == Type.CITY) {
      return images.get(3).get(0);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.FIELD && t.south() == Type.CITY
        && t.west() == Type.CITY) {
      return images.get(3).get(3);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.FIELD) {
      return images.get(3).get(1);
    } else if (t.center() == Type.CITY && t.north() == Type.FIELD && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.CITY) {
      return images.get(3).get(2);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.ROAD
        && t.west() == Type.CITY) {
      return images.get(4).get(0);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.ROAD && t.south() == Type.CITY
        && t.west() == Type.CITY) {
      return images.get(4).get(3);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.ROAD) {
      return images.get(4).get(1);
    } else if (t.center() == Type.CITY && t.north() == Type.ROAD && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.CITY) {
      return images.get(4).get(2);
    } else if (t.center() == Type.FIELD && t.north() == Type.CITY && t.east() == Type.FIELD && t.south() == Type.FIELD
        && t.west() == Type.CITY) {
      if (t.cityEnder())
        return images.get(8).get(0);
      return images.get(5).get(0);
    } else if (t.center() == Type.FIELD && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.FIELD
        && t.west() == Type.FIELD) {
      if (t.cityEnder())
        return images.get(8).get(1);
      return images.get(5).get(1);
    } else if (t.center() == Type.FIELD && t.north() == Type.FIELD && t.east() == Type.FIELD && t.south() == Type.CITY
        && t.west() == Type.CITY) {
      if (t.cityEnder())
        return images.get(8).get(3);
      return images.get(5).get(3);
    } else if (t.center() == Type.FIELD && t.north() == Type.FIELD && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.FIELD) {
      if (t.cityEnder())
        return images.get(8).get(2);
      return images.get(5).get(2);
    } else if (t.center() == Type.ROAD && t.north() == Type.CITY && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.CITY) {
      return images.get(6).get(0);
    } else if (t.center() == Type.ROAD && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return images.get(6).get(1);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.CITY
        && t.west() == Type.CITY) {
      return images.get(6).get(3);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.ROAD) {
      return images.get(6).get(2);
    } else if (t.center() == Type.CITY && t.north() == Type.FIELD && t.east() == Type.CITY && t.south() == Type.FIELD
        && t.west() == Type.CITY) {
      return images.get(7).get(0);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.FIELD && t.south() == Type.CITY
        && t.west() == Type.FIELD) {
      return images.get(7).get(1);
    } else if (t.center() == Type.FIELD && t.north() == Type.CITY && t.east() == Type.FIELD && t.south() == Type.CITY
        && t.west() == Type.FIELD) {
      return images.get(9).get(0);
    } else if (t.center() == Type.FIELD && t.north() == Type.FIELD && t.east() == Type.CITY && t.south() == Type.FIELD
        && t.west() == Type.CITY) {
      return images.get(9).get(1);
    } else if (t.center() == Type.FIELD && t.north() == Type.CITY && t.east() == Type.FIELD && t.south() == Type.FIELD
        && t.west() == Type.FIELD) {
      return images.get(10).get(0);
    } else if (t.center() == Type.FIELD && t.north() == Type.FIELD && t.east() == Type.CITY && t.south() == Type.FIELD
        && t.west() == Type.FIELD) {
      return images.get(10).get(1);
    } else if (t.center() == Type.FIELD && t.north() == Type.FIELD && t.east() == Type.FIELD && t.south() == Type.CITY
        && t.west() == Type.FIELD) {
      return images.get(10).get(2);
    } else if (t.center() == Type.FIELD && t.north() == Type.FIELD && t.east() == Type.FIELD && t.south() == Type.FIELD
        && t.west() == Type.CITY) {
      return images.get(10).get(3);
    } else if (t.center() == Type.ROAD && t.north() == Type.CITY && t.east() == Type.FIELD && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return images.get(11).get(0);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.CITY && t.south() == Type.FIELD
        && t.west() == Type.ROAD) {
      return images.get(11).get(1);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.CITY
        && t.west() == Type.FIELD) {
      return images.get(11).get(2);
    } else if (t.center() == Type.ROAD && t.north() == Type.FIELD && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.CITY) {
      return images.get(11).get(3);
    } else if (t.center() == Type.ROAD && t.north() == Type.CITY && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.FIELD) {
      return images.get(12).get(0);
    } else if (t.center() == Type.ROAD && t.north() == Type.FIELD && t.east() == Type.CITY && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return images.get(12).get(1);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.FIELD && t.south() == Type.CITY
        && t.west() == Type.ROAD) {
      return images.get(12).get(2);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.FIELD
        && t.west() == Type.CITY) {
      return images.get(12).get(3);
    } else if (t.center() == Type.TOWN && t.north() == Type.CITY && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return images.get(13).get(0);
    } else if (t.center() == Type.TOWN && t.north() == Type.ROAD && t.east() == Type.CITY && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return images.get(13).get(1);
    } else if (t.center() == Type.TOWN && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.CITY
        && t.west() == Type.ROAD) {
      return images.get(13).get(2);
    } else if (t.center() == Type.TOWN && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.CITY) {
      return images.get(13).get(3);
    } else if (t.center() == Type.ROAD && t.north() == Type.CITY && t.east() == Type.ROAD && t.south() == Type.FIELD
        && t.west() == Type.ROAD) {
      return images.get(14).get(0);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.CITY && t.south() == Type.ROAD
        && t.west() == Type.FIELD) {
      return images.get(14).get(1);
    } else if (t.center() == Type.ROAD && t.north() == Type.FIELD && t.east() == Type.ROAD && t.south() == Type.CITY
        && t.west() == Type.ROAD) {
      return images.get(14).get(2);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.FIELD && t.south() == Type.ROAD
        && t.west() == Type.CITY) {
      return images.get(14).get(3);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.FIELD && t.south() == Type.ROAD
        && t.west() == Type.FIELD) {
      return images.get(15).get(0);
    } else if (t.center() == Type.ROAD && t.north() == Type.FIELD && t.east() == Type.ROAD && t.south() == Type.FIELD
        && t.west() == Type.ROAD) {
      return images.get(15).get(1);
    } else if (t.center() == Type.ROAD && t.north() == Type.FIELD && t.east() == Type.FIELD && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return images.get(16).get(0);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.FIELD && t.south() == Type.FIELD
        && t.west() == Type.ROAD) {
      return images.get(16).get(1);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.FIELD
        && t.west() == Type.FIELD) {
      return images.get(16).get(2);
    } else if (t.center() == Type.ROAD && t.north() == Type.FIELD && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.FIELD) {
      return images.get(16).get(3);
    } else if (t.center() == Type.TOWN && t.north() == Type.FIELD && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return images.get(17).get(0);
    } else if (t.center() == Type.TOWN && t.north() == Type.ROAD && t.east() == Type.FIELD && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return images.get(17).get(1);
    } else if (t.center() == Type.TOWN && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.FIELD
        && t.west() == Type.ROAD) {
      return images.get(17).get(2);
    } else if (t.center() == Type.TOWN && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.FIELD) {
      return images.get(17).get(3);
    } else if (t.center() == Type.TOWN && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return images.get(18).get(0);
    }
    return null;
  }
}
