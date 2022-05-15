package view;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;

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
  Image blason;
  int tileSize, startX, startY;
  Random rand;
  Graphics2D drawable;

  public AffichePlateau(GameEngine gameEngine) {
    gm = gameEngine;
    Images imgs = new Images();
    images = imgs.getImagesList();
    blason = imgs.blason();
  }

  public int tailleTuile() {
    return tileSize;
  }

  public int getOffsetX() {
    return startX;
  }

  public int getOffsetY() {
    return startY;
  }

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

  public void meeplePaint() {
    rand = new Random(11000);
    int meepleX = startX, meepleY = startY + gm.getStartTilePoint().y;
    int meepleSize = tileSize / 8;
    int alea = rand.nextInt(meepleSize) - (meepleSize / 2);
    for (Meeple m : gm.getMeeplesOnSet()) {
      switch (m.getCardinal()) {
        case "c":
          meepleX += (tileSize / 2 - meepleSize / 2) + alea;
          meepleY += (tileSize / 2 - meepleSize / 2) + alea;
          break;
        case "n":
          meepleY += (tileSize * 0.03) + alea;
          meepleX += (tileSize / 2 - meepleSize / 2) + alea;
          break;
        case "e":
          meepleX += tileSize - (meepleSize + tileSize * 0.01);
          meepleY += (tileSize / 2 - meepleSize / 2) + alea;
          break;
        case "s":
          meepleX += (tileSize / 2 - meepleSize / 2) + alea;
          meepleY += tileSize - (meepleSize + tileSize * 0.01);
          break;
        case "w":
          meepleX += tileSize * 0.03;
          meepleY += (tileSize / 2 - meepleSize / 2) + alea;
          break;
      }
      drawable.fillOval(meepleX + (m.getY()
          + gm.getStartTilePoint().y) * tileSize, meepleY + (m.getX()
              + gm.getStartTilePoint().x) * tileSize, meepleSize, meepleSize);
    }
  }

  public void drawBlason(int x, int y) {
    double blasonSize = (tileSize * 0.5);
    drawable.drawImage(blason, x, y, (int) (0.29 * blasonSize), (int) (0.39 * blasonSize), null);
  }

  @Override
  public void paintComponent(Graphics g) {
    drawable = (Graphics2D) g;
    drawable.clearRect(0, 0, getSize().width, getSize().height);
    Tile[][] plateau = gm.getSet();
    Tile currentTile = gm.getCurrentTile();
    getTileSize();

    for (int i = 0; i < plateau.length; i++) {
      for (int j = 0; j < plateau[i].length; j++) {
        if (plateau[i][j] != null) {
          drawable.drawImage(getImage(plateau[i][j]), startX + j * tileSize, startY + i * tileSize, tileSize,
              tileSize, null);
          if (plateau[i][j].blason())
            drawBlason((int) (startX + tileSize*0.15  + j * tileSize), (int) (startY + tileSize * 0.15 + i * tileSize));
        }
      }
    }

    List<Player> players = gm.getListPlayers();
    for (int i = 0; i < gm.getNbPlayer(); i++) {
      if (gm.getPlayerTurn() == i)
        g.drawString(">", 0, (int)  (15 + i * (getSize().height * 0.10)));
      g.drawString(players.get(i).pseudo(), 10, (int)  (15 + i * (getSize().height * 0.10)));
      drawable.drawString(String.valueOf(players.get(i).score()), 100, (int)  (15 + i * (getSize().height * 0.10)));
      drawable.drawString(String.valueOf(players.get(i).nbMeeplesRestant()), 80, (int)  (15 + i * (getSize().height * 0.10)));
    }

    g.drawString(gm.getPiocheSize() + " / 71", getSize().width - 80, getSize().height - 120);

    if (currentTile != null) {
      drawable.drawImage(getImage(currentTile), getSize().width - 100, getSize().height - 100, 85, 85, null);
      if (currentTile.blason()) {
        drawable.drawImage(blason, getSize().width - 90, getSize().height - 90, 20, 26, null);
      }
    }
    meeplePaint();
  }

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
