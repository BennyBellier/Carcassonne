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

  /**
   ** Crée un meeple à partir de la sauvegarde
   * @param b
   */
  public CurrentMeeple(byte[] b) {
    x = b[0];
    y = b[1];
    card = String.valueOf(b[2]);
  }

  /**
   ** Retourne le tableau de byte permettant de sauvegarder un meeple
   * @return
   */
  public List<Byte> toByteArray() {
    return Arrays.asList((byte) x, (byte) y, (byte) card.charAt(0));
  }

  @Override
  public CurrentMeeple clone() {
    return new CurrentMeeple(x, y, card);
  }
}
