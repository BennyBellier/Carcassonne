package view;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;

public class Background extends JComponent{

  Image currentbackground , menu , credits , jouer , nouvellePartie , options , regles;

  public Background() {
    try {
      //currentbackground = ImageIO.read(new File("assets/Images/background2.png"));
      menu = ImageIO.read(new File("assets/Images/background2.png"));
      credits = ImageIO.read(new File("assets/Images/Credits.jpg"));
      jouer = ImageIO.read(new File("assets/Images/Jouer.jpg"));
      nouvellePartie = ImageIO.read(new File("assets/Images/NouvellePartie.jpg"));
      options = ImageIO.read(new File("assets/Images/Options.jpg"));
      regles = ImageIO.read(new File("assets/Images/Regles.jpg"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void affichageCredits() {
    currentbackground = credits;
    System.out.println(currentbackground);
    repaint();
  }

  public void affichageJouer() {
    currentbackground = jouer;
    repaint();
  }

  public void affichageNouvellePartie() {
    currentbackground = nouvellePartie;
    repaint();
  }

  public void affichageOptions() {
    currentbackground = options;
    System.out.println(currentbackground);
    repaint();
  }

  public void affichageRegles() {
    currentbackground = regles;
    System.out.println(currentbackground);
    repaint();
  }

  public void affichageMenu(){
    currentbackground = menu;
    System.out.println(currentbackground);
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D drawable = (Graphics2D) g;
    
    int width = getSize().width;
    int height = getSize().height;
    
    drawable.clearRect(0, 0, width, height);

    drawable.drawImage(currentbackground, 0, 0, width, height, null);
  }
}
