package view;

import javax.swing.*;
import java.awt.*;


public class AfficheCurrentTile extends JComponent {
  public Image img, blason;

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D drawable = (Graphics2D) g;
    drawable.clearRect(0, 0, getSize().width, getSize().height);
    if (img != null) {
      drawable.drawImage(img, 0, 0, getSize().width, getSize().height, null);
    }
    if (blason != null)  {
      drawable.drawImage(blason, 30, 30, 31, 40, null);
    }
    img = null;
    blason = null;
  }
}
