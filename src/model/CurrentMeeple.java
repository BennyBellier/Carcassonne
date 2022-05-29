package model;

import java.util.Arrays;
import java.util.List;

public class CurrentMeeple {
  public int x, y;
  public String card;

  public CurrentMeeple(int x, int y, String card) {
    this.x = x;
    this.y = y;
    this.card = card;
  }

  public CurrentMeeple(byte[] b) {
    x = b[0];
    y = b[1];
    card = String.valueOf(b[2]);
  }

  public List<Byte> toByteArray() {
    return Arrays.asList((byte) x, (byte) y, (byte) card.charAt(0));
  }
}
