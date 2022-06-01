package view;

import javax.swing.*;

public class ScoreEntryPlayer {
  public JLabel score;
  public JLabel nbMeeple;

  public ScoreEntryPlayer(JLabel score, JLabel nbMeeple) {
    this.score = score;
    this.nbMeeple = nbMeeple;
  }
}