package view;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;

public class Background extends JComponent{

  Image background;

  public Background() {
    try {
      background = ImageIO.read(new File("assets/Images/background.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D drawable = (Graphics2D) g;
    int width = getSize().width;
    int height = getSize().height;
    drawable.drawImage(background, 0, 0, width, height, null);
  }
}
