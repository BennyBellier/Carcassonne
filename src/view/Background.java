package view;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;

import global.Configuration;

public class Background extends JComponent {

  Image currentbackground , menu , credits , jouer , nouvellePartie , options , regles , plateauInGame;

  public Background() {
    try {
      menu = ImageIO.read(Configuration.charge("Images/BackgroundTitle.jpg"));
      credits = ImageIO.read(Configuration.charge("Images/Credits.jpg"));
      jouer = ImageIO.read(Configuration.charge("Images/Jouer.jpg"));
      nouvellePartie = ImageIO.read(Configuration.charge("Images/NouvellePartie.jpg"));
      options = ImageIO.read(Configuration.charge("Images/Options.jpg"));
      regles = ImageIO.read(Configuration.charge("Images/Regles.jpg"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void affichageCredits() {
    currentbackground = credits;
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
    repaint();
  }

  public void affichageRegles() {
    currentbackground = regles;
    repaint();
  }

  public void affichageMenu(){
    currentbackground = menu;
    repaint();
  }

  public void desactivate() {
    currentbackground = null;
    repaint();
  }

  public void affichagePlateauInGame() {
    currentbackground = plateauInGame;
    repaint();
  }

  public void affichageMenuBoutons() {
    currentbackground = plateauInGame;
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D drawable = (Graphics2D) g;

    int width = getSize().width;
    int height = getSize().height;

    drawable.clearRect(0, 0, width, height);
    if (currentbackground != null)
      drawable.drawImage(currentbackground, 0, 0, width, height, null);
  }
}
