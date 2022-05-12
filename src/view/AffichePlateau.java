package view;

import java.awt.Graphics2D;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

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

  GameSet gs = new GameSet();
  Images img = new Images();
  private Tile[][] plateau = gs.cloneSet();
  int taille = plateau.length;
  int tailleImg = 100;
  ArrayList<Image> images = img.getImagesList();

  public AffichePlateau() {
    repaint();
  }

  public void draw(Graphics2D g, int taille, int rota, int tailleImg) {
    for (int i = 0; i < taille; i++) {
      for (int j = 0; j < taille; j++) {
        if (plateau[i][j] != null) {
          Map.Entry<Image, Integer> m = getImageAndRotation(plateau[i][j]);
          System.out.println("test");
          g.drawImage(m.getKey(), i * 20, j * 20, 20, 20, null);
        }
      }
    }

  }

  public Map.Entry<Image, Integer> getImageAndRotation(Tile t) {
    if (t.center() == Type.ABBEY && t.north() == Type.FIELD && t.east() == Type.FIELD && t.south() == Type.FIELD
        && t.west() == Type.FIELD)
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(0), 0);
    else if (t.center() == Type.ABBEY
        && (t.north() == Type.ROAD || t.east() == Type.ROAD || t.south() == Type.ROAD || t.west() == Type.ROAD)) {
      if (t.south() == Type.ROAD)
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(1), 0);
      else if (t.west() == Type.ROAD)
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(1), 90);
      else if (t.east() == Type.ROAD)
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(1), -90);
      else
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(1), 180);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.CITY)
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(2), 0);
    else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.FIELD
        && t.west() == Type.CITY) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(4), 0);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(3), 0);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.FIELD && t.south() == Type.CITY
        && t.west() == Type.CITY) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(4), -90);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(3), -90);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.FIELD) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(4), 90);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(3), 90);
    } else if (t.center() == Type.CITY && t.north() == Type.FIELD && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.CITY) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(4), 180);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(3), 180);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.FIELD
        && t.west() == Type.CITY) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(6), 0);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(5), 0);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.FIELD && t.south() == Type.CITY
        && t.west() == Type.CITY) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(6), -90);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(5), -90);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.FIELD) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(6), 90);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(5), 90);
    } else if (t.center() == Type.CITY && t.north() == Type.FIELD && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.CITY) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(6), 180);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(5), 180);
    } else if (t.center() == Type.FIELD && t.north() == Type.CITY && t.east() == Type.FIELD && t.south() == Type.FIELD
        && t.west() == Type.CITY) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(8), 0);
      if (t.cityEnder())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(13), 0);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(7), 0);
    } else if (t.center() == Type.FIELD && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.FIELD
        && t.west() == Type.FIELD) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(8), 90);
      if (t.cityEnder())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(13), 90);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(7), 90);
    } else if (t.center() == Type.FIELD && t.north() == Type.FIELD && t.east() == Type.FIELD && t.south() == Type.CITY
        && t.west() == Type.CITY) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(8), -90);
      if (t.cityEnder())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(13), -90);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(7), -90);
    } else if (t.center() == Type.FIELD && t.north() == Type.FIELD && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.FIELD) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(8), 180);
      if (t.cityEnder())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(13), 180);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(7), 180);
    } else if (t.center() == Type.ROAD && t.north() == Type.CITY && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.CITY) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(10), 0);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(9), 0);
    } else if (t.center() == Type.ROAD && t.north() == Type.CITY && t.east() == Type.CITY && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(10), 90);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(9), 90);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.CITY
        && t.west() == Type.CITY) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(10), -90);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(9), -90);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.CITY && t.south() == Type.CITY
        && t.west() == Type.ROAD) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(10), 180);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(9), 180);
    } else if (t.center() == Type.CITY && t.north() == Type.FIELD && t.east() == Type.CITY && t.south() == Type.FIELD
        && t.west() == Type.CITY) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(12), 0);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(11), 0);
    } else if (t.center() == Type.CITY && t.north() == Type.CITY && t.east() == Type.FIELD && t.south() == Type.CITY
        && t.west() == Type.FIELD) {
      if (t.blason())
        return new AbstractMap.SimpleEntry<Image, Integer>(images.get(12), 90);
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(11), 90);
    } else if (t.center() == Type.FIELD && t.north() == Type.CITY && t.east() == Type.FIELD && t.south() == Type.CITY
        && t.west() == Type.FIELD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(14), 0);
    } else if (t.center() == Type.FIELD && t.north() == Type.FIELD && t.east() == Type.CITY && t.south() == Type.FIELD
        && t.west() == Type.CITY) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(14), 90);
    } else if (t.center() == Type.FIELD && t.north() == Type.CITY && t.east() == Type.FIELD && t.south() == Type.FIELD
        && t.west() == Type.FIELD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(15), 0);
    } else if (t.center() == Type.FIELD && t.north() == Type.FIELD && t.east() == Type.CITY && t.south() == Type.FIELD
        && t.west() == Type.FIELD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(15), 90);
    } else if (t.center() == Type.FIELD && t.north() == Type.FIELD && t.east() == Type.FIELD && t.south() == Type.CITY
        && t.west() == Type.FIELD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(15), 180);
    } else if (t.center() == Type.FIELD && t.north() == Type.FIELD && t.east() == Type.FIELD && t.south() == Type.FIELD
        && t.west() == Type.CITY) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(15), -90);
    } else if (t.center() == Type.ROAD && t.north() == Type.CITY && t.east() == Type.FIELD && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(16), 0);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.CITY && t.south() == Type.FIELD
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(16), 90);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.CITY
        && t.west() == Type.FIELD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(16), 180);
    } else if (t.center() == Type.ROAD && t.north() == Type.FIELD && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.CITY) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(16), -90);
    } else if (t.center() == Type.ROAD && t.north() == Type.CITY && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.FIELD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(17), 0);
    } else if (t.center() == Type.ROAD && t.north() == Type.FIELD && t.east() == Type.CITY && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(17), 90);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.FIELD && t.south() == Type.CITY
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(17), 180);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.FIELD
        && t.west() == Type.CITY) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(17), -90);
    } else if (t.center() == Type.ROAD && t.north() == Type.CITY && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(18), 0);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.CITY && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(18), 90);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.CITY
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(18), 180);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.CITY) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(18), -90);
    } else if (t.center() == Type.ROAD && t.north() == Type.CITY && t.east() == Type.ROAD && t.south() == Type.FIELD
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(19), 0);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.CITY && t.south() == Type.ROAD
        && t.west() == Type.FIELD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(19), 90);
    } else if (t.center() == Type.ROAD && t.north() == Type.FIELD && t.east() == Type.ROAD && t.south() == Type.CITY
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(19), 180);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.FIELD && t.south() == Type.ROAD
        && t.west() == Type.CITY) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(19), -90);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.FIELD && t.south() == Type.ROAD
        && t.west() == Type.FIELD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(20), 0);
    } else if (t.center() == Type.ROAD && t.north() == Type.FIELD && t.east() == Type.ROAD && t.south() == Type.FIELD
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(20), 90);
    } else if (t.center() == Type.ROAD && t.north() == Type.FIELD && t.east() == Type.FIELD && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(21), 0);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.FIELD && t.south() == Type.FIELD
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(21), 90);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.FIELD
        && t.west() == Type.FIELD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(21), 180);
    } else if (t.center() == Type.ROAD && t.north() == Type.FIELD && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.FIELD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(21), -90);
    } else if (t.center() == Type.ROAD && t.north() == Type.FIELD && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(22), 0);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.FIELD && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(22), 90);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.FIELD
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(22), 180);
    } else if (t.center() == Type.ROAD && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.FIELD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(22), -90);
    } else if (t.center() == Type.TOWN && t.north() == Type.ROAD && t.east() == Type.ROAD && t.south() == Type.ROAD
        && t.west() == Type.ROAD) {
      return new AbstractMap.SimpleEntry<Image, Integer>(images.get(23), 0);
    }
    return null;
  }
}
