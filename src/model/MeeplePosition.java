package model;

import java.awt.Point;

public class MeeplePosition {
  int x, y;
  String card;

  public MeeplePosition(int x, int y, String cardinal) {
    this.x = x;
    this.y = y;
    card = cardinal;
  }

  public Point getPoint() {
    return new Point(x, y);
  }

  public String getCardinal() {
    return card;
  }
}
