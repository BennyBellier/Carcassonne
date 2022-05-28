package view;

import controller.Controleur;
import global.Audio;
import global.Configuration;
import model.GameEngine;
import model.Player;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ludov
 */
public class Frames extends javax.swing.JFrame {
  Player p;
  Controleur control;
  Images imgs;
  boolean maximized;
  Random r = new Random();
  Color c, bleu, noir, vert, violet;
  private Color couleurBleu = new Color(7, 45, 249);
  private Color couleurRouge = new Color(240, 0, 32);
  private Color couleurNoir = new Color(31, 31, 31);
  private Color couleurVert = new Color(60, 212, 21);
  private Color couleurJaune = new Color(255, 235, 87);
  private String textField;
  ArrayList<Player> players = new ArrayList<>();
  Audio audioPlayer;
  Keybord keyboard = new Keybord();
  Font uniFont;

  /**
   * Creates new form Frames
   */
  public Frames() {
    setIcon();
    loadFont();
    imgs = new Images();
    audioPlayer = new Audio();
    initComponents();
    setupPanel();
    addKeyListener(keyboard);
    basculeEnPleineEcran();
    if (Boolean.parseBoolean(Configuration.instance().lis("MusicState"))){
      audioPlayer.music.play();
    }
    
  }

  void setIcon() {
    try {
      setIconImage(ImageIO.read(Configuration.charge("Images/logo.png")));
    } catch (IOException e) {
      Configuration.instance().logger().severe("Impossible de charger l'icon");
    }
  }

  void loadFont() {
    try {
      InputStream is = Configuration.charge("OldLondon.ttf");
      uniFont = Font.createFont(Font.TRUETYPE_FONT, is);
    } catch (Exception e) {
      Configuration.instance().logger().severe("Impossible de charger la police de caractères");
    }
  }

  private void setupPanel() {
    menuPrincipale();
    jeuEnReseaux.setEnabled(false);
    lancerLaPartie.setEnabled(false);
    ajouterIA.setEnabled(true);
    sauvegarderInGame.setEnabled(false);
    tourJ2.setVisible(false);
    tourJ3.setVisible(false);
    tourJ4.setVisible(false);
    tourJ5.setVisible(false);
  }

  public void basculeEnPleineEcran() {
    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice device = env.getDefaultScreenDevice();

    if (maximized) {
      device.setFullScreenWindow(null);
      maximized = false;
    } else {
      device.setFullScreenWindow(this);
      maximized = true;
    }
  }

  public void donnerAction(JButton b, String actionCommand) {
    b.setActionCommand(actionCommand);
    b.addActionListener(control);
  }

  public void menuPrincipale() {
    background.affichageMenu();
    menuPrincipale.setVisible(true);
    options.setVisible(false);
    jouerPanel.setVisible(false);
    regles.setVisible(false);
    newGame.setVisible(false);
    quitterOptionPane.setVisible(false);
    ajouterJoueur.setEnabled(false);
    credits.setVisible(false);
    plateauJeu.setVisible(false);
    menuInGame.setVisible(false);
    regles2.setVisible(false);
    layoutJeu.setVisible(false);
    scoreFin.setVisible(false);
  }

  public void boutonSupDesactiver() {
    supprimerJ1.setVisible(false);
    supprimerJ2.setVisible(false);
    supprimerJ3.setVisible(false);
    supprimerJ4.setVisible(false);
    supprimerJ5.setVisible(false);
  }

  public void choixNom() {
    textField = pseudo.getText();
    if (pseudo.getText().isEmpty()) {
      ajouterJoueur.setEnabled(false);
    } else {
      ajouterJoueur.setEnabled(true);
    }
  }

  private void desactivation() {
    if (c.equals(couleurNoir)) {
      cNoir.setEnabled(false);
    } else if (c.equals(couleurRouge)) {
      cRouge.setEnabled(false);
    } else if (c.equals(couleurVert)) {
      cVert.setEnabled(false);
    } else if (c.equals(couleurJaune)) {
      cJaune.setEnabled(false);
    } else if (c.equals(couleurBleu)) {
      cBleu.setEnabled(false);
    }
  }

  private void activation() {
    if (c.equals(couleurNoir)) {
      cNoir.setEnabled(true);
    } else if (c.equals(couleurRouge)) {
      cRouge.setEnabled(true);
    } else if (c.equals(couleurVert)) {
      cVert.setEnabled(true);
    } else if (c.equals(couleurJaune)) {
      cJaune.setEnabled(true);
    } else if (c.equals(couleurBleu)) {
      cBleu.setEnabled(true);
    }
  }

  private void ajouterHumain(String text) {
    if (j1.getText().isEmpty()) {
      j1.setForeground(c);
      desactivation();
      j1.setText(text);
      supprimerJ1.setVisible(true);
    } else if (j2.getText().isEmpty() && (!j1.getText().isEmpty())) {
      j2.setForeground(c);
      desactivation();
      j2.setText(text);
      supprimerJ2.setVisible(true);
      lancerLaPartie.setEnabled(true);
    } else if (j3.getText().isEmpty() && (!j1.getText().isEmpty()) && (!j2.getText().isEmpty())) {
      j3.setForeground(c);
      desactivation();
      j3.setText(text);
      supprimerJ3.setVisible(true);
    } else if (j4.getText().isEmpty() && (!j1.getText().isEmpty()) && (!j2.getText().isEmpty())
        && (!j3.getText().isEmpty())) {
      j4.setForeground(c);
      desactivation();
      j4.setText(text);
      supprimerJ4.setVisible(true);
    } else {
      j5.setForeground(c);
      desactivation();
      j5.setText(text);
      supprimerJ5.setVisible(true);
      ajouterIA.setEnabled(false);
      ajouterJoueur.setEnabled(false);
    }
    p = new Player(text, Player.Type.HUMAN, c);
    players.add(p);
  }

  private void ajouterIA(String text) {
    if (j1.getText().isEmpty()) {
      j1.setForeground(c);
      desactivation();
      j1.setText(text);
      supprimerJ1.setVisible(true);
    } else if (j2.getText().isEmpty() && (!j1.getText().isEmpty())) {
      j2.setForeground(c);
      desactivation();
      j2.setText(text);
      supprimerJ2.setVisible(true);
      lancerLaPartie.setEnabled(true);
    } else if (j3.getText().isEmpty() && (!j1.getText().isEmpty()) && (!j2.getText().isEmpty())) {
      j3.setForeground(c);
      desactivation();
      j3.setText(text);
      supprimerJ3.setVisible(true);
    } else if (j4.getText().isEmpty() && (!j1.getText().isEmpty()) && (!j2.getText().isEmpty())
        && (!j3.getText().isEmpty())) {
      j4.setForeground(c);
      desactivation();
      j4.setText(text);
      supprimerJ4.setVisible(true);
    } else {
      j5.setForeground(c);
      desactivation();
      j5.setText(text);
      supprimerJ5.setVisible(true);
      ajouterIA.setEnabled(false);
      ajouterJoueur.setEnabled(false);
    }

  }

  public void setColor() {
    int cRandom = r.nextInt(5);
    switch (cRandom) {
      case 0:
        if (!cBleu.isEnabled()) {
          if (cRouge.isEnabled() || cVert.isEnabled() || cNoir.isEnabled() || cJaune.isEnabled())
            setColor();
        } else {
          c = couleurBleu;
          desactivation();
        }
        break;
      case 1:
        if (!cRouge.isEnabled()) {
          if (cBleu.isEnabled() || cVert.isEnabled() || cNoir.isEnabled() || cJaune.isEnabled())
            setColor();
        } else {
          c = couleurRouge;
          desactivation();
        }
        break;
      case 2:
        if (!cVert.isEnabled()) {
          if (cRouge.isEnabled() || cBleu.isEnabled() || cNoir.isEnabled() || cJaune.isEnabled())
            setColor();
        } else {
          c = couleurVert;
          desactivation();
        }
        break;
      case 3:
        if (!cNoir.isEnabled()) {
          if (cRouge.isEnabled() || cVert.isEnabled() || cBleu.isEnabled() || cJaune.isEnabled())
            setColor();
        } else {
          c = couleurNoir;
          desactivation();
        }
        break;
      case 4:
        if (!cJaune.isEnabled()) {
          if (cRouge.isEnabled() || cVert.isEnabled() || cNoir.isEnabled() || cBleu.isEnabled())
            setColor();
        } else {
          c = couleurJaune;
          desactivation();
        }
        break;
      default:
        break;
    }
  }

  public void affListe() {
    for (int i = 0; i < players.size(); i++) {
      System.out.print(players.get(i).pseudo());
      System.out.print(players.get(i).color());
      System.out.println(players.get(i).type());
    }
  }

  private void remplace4() {
    if (!j5.getText().isEmpty()) {
      j4.setText(j5.getText());
      j4.setForeground(j5.getForeground());
      j5.setText("");
      supprimerJ5.setVisible(false);
    } else {
      supprimerJ4.setVisible(false);
    }
  }

  private void remplace3() {
    if (!j4.getText().isEmpty()) {
      j3.setText(j4.getText());
      j3.setForeground(j4.getForeground());
      j4.setText("");
    } else {
      supprimerJ3.setVisible(false);
    }
    remplace4();
  }

  private void remplace2() {
    if (!j3.getText().isEmpty()) {
      j2.setText(j3.getText());
      j2.setForeground(j3.getForeground());
      j3.setText("");
    } else {
      supprimerJ2.setVisible(false);
    }
    remplace3();
  }

  private void remplace1() {
    if (!j2.getText().isEmpty()) {
      j1.setText(j2.getText());
      j1.setForeground(j2.getForeground());
      j2.setText("");
    } else {
      supprimerJ1.setVisible(false);
    }
    remplace2();
  }

  public void reinitialiserParametre() {
    ajouterJoueur.setEnabled(false);
    ajouterIA.setEnabled(true);
    j1.setText("");
    j2.setText("");
    j3.setText("");
    j4.setText("");
    j5.setText("");
    pseudo.setText("");
    cBleu.setEnabled(true);
    cRouge.setEnabled(true);
    cVert.setEnabled(true);
    cJaune.setEnabled(true);
    cNoir.setEnabled(true);
    boutonSupDesactiver();
    players.clear();

  }

  void resetInGameLabel() {
    imageBleu.setIcon(null);
    imageJaune.setIcon(null);
    imageRouge.setIcon(null);
    imageNoir.setIcon(null);
    imageVert.setIcon(null);
    ptsBleu.setText("");
    ptsRouge.setText("");
    ptsNoire.setText("");
    ptsVert.setText("");
    ptsJaune.setText("");
    cmpMeepleBleu.setText("");
    cmpMeepleRouge.setText("");
    cmpMeepleNoire.setText("");
    cmpMeepleVert.setText("");
    cmpMeepleJaune.setText("");
  }

  public void cadre() {
    resetInGameLabel();
    for (int i = 0; i < players.size(); i++) {
      ImageIcon imageIcon = null;
      if (players.get(i).color().equals(couleurRouge)) {
        imageIcon = new ImageIcon(imgs.rouge());
      } else if (players.get(i).color().equals(couleurVert)) {
        imageIcon = new ImageIcon(imgs.vert());
      } else if (players.get(i).color().equals(couleurNoir)) {
        imageIcon = new ImageIcon(imgs.noir());
      } else if (players.get(i).color().equals(couleurJaune)) {
        imageIcon = new ImageIcon(imgs.jaune());
      } else if (players.get(i).color().equals(couleurBleu)) {
        imageIcon = new ImageIcon(imgs.bleu());
      }

      if (imageBleu.getIcon() == null)
        imageBleu.setIcon(imageIcon);
      else if (imageJaune.getIcon() == null)
        imageJaune.setIcon(imageIcon);
      else if (imageNoir.getIcon() == null)
        imageNoir.setIcon(imageIcon);
      else if (imageRouge.getIcon() == null)
        imageRouge.setIcon(imageIcon);
      else if (imageVert.getIcon() == null)
        imageVert.setIcon(imageIcon);
    }
  }

  void sendLabel() {
    List<JLabel> l = new ArrayList<>();
    if (imageBleu.getIcon() != null) {
      l.add(ptsBleu);
      l.add(cmpMeepleBleu);
    }
    if (imageRouge.getIcon() != null) {
      l.add(ptsRouge);
      l.add(cmpMeepleRouge);
    }
    if (imageVert.getIcon() != null) {
      l.add(ptsVert);
      l.add(cmpMeepleVert);
    }
    if (imageJaune.getIcon() != null) {
      l.add(ptsJaune);
      l.add(cmpMeepleJaune);
    }
    if (imageNoir.getIcon() != null) {
      l.add(ptsNoire);
      l.add(cmpMeepleNoire);
    }
    l.add(nbTuileRestante);
    plateauJeu.setLabelScore(l.stream().toArray(JLabel[]::new));
  }

 
  public void lueur2J(){
    if (!tourJ2.isVisible()){
        tourJ2.setVisible(true);
        tourJ1.setVisible(false);
    } else {
        tourJ2.setVisible(false);
        tourJ1.setVisible(true);
    }
  }

  public void lueur3J(){
    if (!tourJ2.isVisible() && !tourJ3.isVisible()){
        tourJ2.setVisible(true);
        tourJ1.setVisible(false);
        tourJ3.setVisible(false);
    } else if (!tourJ3.isVisible() && !tourJ1.isVisible()){
        tourJ2.setVisible(false);
        tourJ3.setVisible(true);
        tourJ1.setVisible(false);
    } else {
        tourJ1.setVisible(true);
        tourJ2.setVisible(false);
        tourJ3.setVisible(false);
    }
  }

  public void lueur4J(){
    if (!tourJ2.isVisible() && !tourJ3.isVisible() && !tourJ4.isVisible()){
        tourJ2.setVisible(true);
        tourJ1.setVisible(false);
        tourJ3.setVisible(false);
        tourJ4.setVisible(false);
    } else if (!tourJ3.isVisible() && !tourJ1.isVisible() && !tourJ4.isVisible()){
        tourJ2.setVisible(false);
        tourJ3.setVisible(true);
        tourJ1.setVisible(false);
        tourJ4.setVisible(false);
    } else if (!tourJ4.isVisible() && !tourJ1.isVisible() && !tourJ2.isVisible()){
        tourJ1.setVisible(false);
        tourJ2.setVisible(false);
        tourJ3.setVisible(false);
        tourJ4.setVisible(true);
    } else {
        tourJ1.setVisible(true);
        tourJ2.setVisible(false);
        tourJ3.setVisible(false);
        tourJ4.setVisible(false);
    }
  }

  public void lueur5J(){
    if (!tourJ2.isVisible() && !tourJ3.isVisible() && !tourJ4.isVisible() && !tourJ5.isVisible()){
        tourJ2.setVisible(true);
        tourJ1.setVisible(false);
        tourJ3.setVisible(false);
        tourJ4.setVisible(false);
        tourJ5.setVisible(false);
    } else if (!tourJ3.isVisible() && !tourJ1.isVisible() && !tourJ4.isVisible() && !tourJ5.isVisible()){
        tourJ2.setVisible(false);
        tourJ3.setVisible(true);
        tourJ1.setVisible(false);
        tourJ4.setVisible(false);
        tourJ5.setVisible(false);
    } else if (!tourJ4.isVisible() && !tourJ1.isVisible() && !tourJ2.isVisible() && !tourJ5.isVisible()){
        tourJ1.setVisible(false);
        tourJ2.setVisible(false);
        tourJ3.setVisible(false);
        tourJ4.setVisible(true);
        tourJ5.setVisible(false);
    } else if (!tourJ5.isVisible() && !tourJ1.isVisible() && !tourJ2.isVisible() && !tourJ3.isVisible()){
        tourJ1.setVisible(false);
        tourJ2.setVisible(false);
        tourJ3.setVisible(false);
        tourJ4.setVisible(false);
        tourJ5.setVisible(true);
    } else {
        tourJ1.setVisible(true);
        tourJ2.setVisible(false);
        tourJ3.setVisible(false);
        tourJ4.setVisible(false);
        tourJ5.setVisible(false);
    }
  }



  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">
  private void initComponents() {

    quitterOptionPane = new javax.swing.JOptionPane();
    background = new Background();
    menuPrincipale = new javax.swing.JPanel();
    jouerB = new javax.swing.JButton();
    version = new javax.swing.JLabel();
    quitter = new javax.swing.JButton();
    menuCredits = new javax.swing.JButton();
    menuOptions = new javax.swing.JButton();
    menuRegles = new javax.swing.JButton();
    jouerPanel = new javax.swing.JPanel();
    jeuEnReseaux = new javax.swing.JButton();
    nouvellePartie = new javax.swing.JButton();
    sauvegardeScroll = new javax.swing.JScrollPane();
    sauvegardeTable = new javax.swing.JTable();
    retourParties = new javax.swing.JButton();
    options = new javax.swing.JPanel();
    retourOptions = new javax.swing.JButton();
    regles = new javax.swing.JPanel();
    retourRegles = new javax.swing.JButton();
    reglesScrollPane = new javax.swing.JScrollPane();
    reglesPane = new javax.swing.JTextPane();
    credits = new javax.swing.JPanel();
    retourCredits = new javax.swing.JButton();
    creditsTextArea = new javax.swing.JTextArea();
    menuPlateau = new javax.swing.JButton();
    newGame = new javax.swing.JPanel();
    retourParametre = new javax.swing.JButton();
    ajouterJoueur = new javax.swing.JButton();
    ajouterIA = new javax.swing.JButton();
    lancerLaPartie = new javax.swing.JButton();
    difficulterBox = new javax.swing.JComboBox<>();
    cBleu = new javax.swing.JButton();
    cRouge = new javax.swing.JButton();
    cVert = new javax.swing.JButton();
    cJaune = new javax.swing.JButton();
    cNoir = new javax.swing.JButton();
    joueurs = new javax.swing.JLabel();
    j1 = new javax.swing.JLabel();
    pseudoLabel = new javax.swing.JLabel();
    j2 = new javax.swing.JLabel();
    j3 = new javax.swing.JLabel();
    j4 = new javax.swing.JLabel();
    j5 = new javax.swing.JLabel();
    pseudo = new javax.swing.JTextField();
    supprimerJ1 = new javax.swing.JButton();
    supprimerJ2 = new javax.swing.JButton();
    supprimerJ3 = new javax.swing.JButton();
    supprimerJ4 = new javax.swing.JButton();
    supprimerJ5 = new javax.swing.JButton();
    cJoueurLabel = new javax.swing.JLabel();
    menuInGame = new javax.swing.JPanel();
    sauvegarderInGame = new javax.swing.JButton();
    reglesInGame = new javax.swing.JButton();
    retourInGame = new javax.swing.JButton();
    retourMenuInGame = new javax.swing.JButton();
    menuBoutons = new javax.swing.JPanel();
    regles2 = new javax.swing.JPanel();
    retourRegles2 = new javax.swing.JButton();
    reglesScrollPane2 = new javax.swing.JScrollPane();
    reglesPane1 = new javax.swing.JTextPane();
    jouerL = new javax.swing.JLabel();
    layoutJeu = new javax.swing.JPanel();
    cmpMeepleBleu = new javax.swing.JLabel();
    ptsBleu = new javax.swing.JLabel();
    imageBleu = new javax.swing.JLabel();
    cmpMeepleJaune = new javax.swing.JLabel();
    ptsJaune = new javax.swing.JLabel();
    imageJaune = new javax.swing.JLabel();
    cmpMeepleNoire = new javax.swing.JLabel();
    ptsNoire = new javax.swing.JLabel();
    imageNoir = new javax.swing.JLabel();
    cmpMeepleRouge = new javax.swing.JLabel();
    ptsRouge = new javax.swing.JLabel();
    imageRouge = new javax.swing.JLabel();
    cmpMeepleVert = new javax.swing.JLabel();
    ptsVert = new javax.swing.JLabel();
    imageVert = new javax.swing.JLabel();
    valider = new javax.swing.JLabel();
    refaire = new javax.swing.JLabel();
    pioche = new javax.swing.JLabel();
    slash = new javax.swing.JLabel();
    nbTuileRestante = new javax.swing.JLabel();
    nbTuileTotal = new javax.swing.JLabel();
    scoreFin = new javax.swing.JPanel();
    panelTable = new javax.swing.JPanel();
    finScrollPane = new javax.swing.JScrollPane();
    scoreTable = new javax.swing.JTable();
    scoreContinuer = new javax.swing.JButton();
    hand = new AfficheCurrentTile();
    tourJ1 = new javax.swing.JLabel();
    tourJ2 = new javax.swing.JLabel();
    tourJ3 = new javax.swing.JLabel();
    tourJ4 = new javax.swing.JLabel();
    tourJ5 = new javax.swing.JLabel();
    hint = new javax.swing.JButton();
    aideCheck = new javax.swing.JCheckBox();
    titreVolume = new javax.swing.JLabel();
    acVolume = new javax.swing.JLabel();
    vitesseIA = new javax.swing.JLabel();
    sliderIA = new javax.swing.JSlider();
    aideOption = new javax.swing.JLabel();
    acAide = new javax.swing.JLabel();
    
    plateauJeu = new AffichePlateau(pioche, refaire, valider, hand);
    
    if (Boolean.parseBoolean(Configuration.instance().lis("MusicState"))){
      volumeCheck = new javax.swing.JCheckBox("", true);
    } else {
      volumeCheck = new javax.swing.JCheckBox("", false);
    }

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Carcassonne");
    setMinimumSize(new java.awt.Dimension(1920, 1080));

    background.setMaximumSize(new java.awt.Dimension(1920, 1080));
    background.setMinimumSize(new java.awt.Dimension(1920, 1080));
    background.setPreferredSize(new java.awt.Dimension(1920, 1080));

    background.setMaximumSize(new java.awt.Dimension(1920, 1080));
    background.setMinimumSize(new java.awt.Dimension(1920, 1080));
    background.setPreferredSize(new java.awt.Dimension(1920, 1080));

    menuPrincipale.setMaximumSize(new java.awt.Dimension(1920, 1080));
    menuPrincipale.setMinimumSize(new java.awt.Dimension(1920, 1080));
    menuPrincipale.setOpaque(false);
    menuPrincipale.setPreferredSize(new java.awt.Dimension(1920, 1080));
    menuPrincipale.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jouerL.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    jouerL.setText("Jouer");
    menuPrincipale.add(jouerL, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 450, 70, 35));

    jouerB.setBackground(new Color(0, 0, 0, 0));
    jouerB.setIcon(new ImageIcon(imgs.boisB()));
    jouerB.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
    jouerB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jouerBActionPerformed(evt);
      }
    });
    menuPrincipale.add(jouerB, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 420, 370, 100));

    version.setFont(uniFont.deriveFont((float) 16)); // NOI18N
    version.setForeground(new java.awt.Color(0xffffff));
    version.setText(Configuration.instance().lis("Version"));
    version.setForeground(new java.awt.Color(255, 255, 255));
    menuPrincipale.add(version, new org.netbeans.lib.awtextra.AbsoluteConstraints(1761, 1040, 150, 30));

    quitter.setFont(uniFont.deriveFont((float) 26)); // NOI18N
    quitter.setText("Quitter");
    quitter.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        quitterActionPerformed(evt);
      }
    });
    menuPrincipale.add(quitter, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 750, 100, 35));

    menuCredits.setFont(uniFont.deriveFont((float) 28)); // NOI18N
    menuCredits.setText("Crédits");
    menuCredits.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuCreditsActionPerformed(evt);
      }
    });
    menuPrincipale.add(menuCredits, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 750, 104, 35));

    menuOptions.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    menuOptions.setText("Options");
    menuOptions.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuOptionsActionPerformed(evt);
      }
    });
    menuPrincipale.add(menuOptions, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 650, 300, 50));

    menuRegles.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    menuRegles.setText("Règles");
    menuRegles.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuReglesActionPerformed(evt);
      }
    });
    menuPrincipale.add(menuRegles, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 550, 300, 50));

    jouerPanel.setMaximumSize(new java.awt.Dimension(1920, 1080));
    jouerPanel.setMinimumSize(new java.awt.Dimension(1920, 1080));
    jouerPanel.setOpaque(false);
    jouerPanel.setPreferredSize(new java.awt.Dimension(1920, 1080));
    jouerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jeuEnReseaux.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    jeuEnReseaux.setText("Jeu en réseaux");
    jouerPanel.add(jeuEnReseaux, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 590, 250, 60));

    nouvellePartie.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    nouvellePartie.setText("Nouvelle Partie");
    nouvellePartie.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        nouvellePartieActionPerformed(evt);
      }
    });
    jouerPanel.add(nouvellePartie, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 420, 250, 60));

    sauvegardeTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][] {
            { null, null, null, null },
            { null, null, null, null },
            { null, null, null, null },
            { null, null, null, null }
        },
        new String[] {
            "Title 1", "Title 2", "Title 3", "Title 4"
        }));
    sauvegardeScroll.setViewportView(sauvegardeTable);

    jouerPanel.add(sauvegardeScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 360, 617, 337));

    retourParties.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    retourParties.setText("Retour");
    retourParties.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourPartiesActionPerformed(evt);
      }
    });
    jouerPanel.add(retourParties, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 910, 110, 45));

    options.setMaximumSize(new java.awt.Dimension(1920, 1080));
    options.setMinimumSize(new java.awt.Dimension(1920, 1080));
    options.setName(""); // NOI18N
    options.setOpaque(false);
    options.setPreferredSize(new java.awt.Dimension(1920, 1080));
    options.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    retourOptions.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    retourOptions.setText("Retour");
    retourOptions.setAlignmentX(0.5F);
    retourOptions.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourOptionsActionPerformed(evt);
      }
    });
    options.add(retourOptions, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 920, 110, 45));

    aideCheck.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
          aideCheckActionPerformed(evt);
      }
    });
    options.add(aideCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 666, -1, 20));
    
    titreVolume.setFont(uniFont.deriveFont((float) 35)); // NOI18N
    titreVolume.setForeground(Color.WHITE);
    titreVolume.setText("Musique :");
    options.add(titreVolume, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 250, 150, 40));

    acVolume.setFont(uniFont.deriveFont((float) 28)); // NOI18N
    acVolume.setForeground(Color.WHITE);
    acVolume.setText("Activé ");
    options.add(acVolume, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 310, 150, 30));

    vitesseIA.setFont(uniFont.deriveFont((float) 35)); // NOI18N
    vitesseIA.setForeground(Color.WHITE);
    vitesseIA.setText("Vitesse IA :");
    options.add(vitesseIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 440, 200, 45));
    options.add(sliderIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 500, -1, -1));

    aideOption.setFont(uniFont.deriveFont((float) 35)); // NOI18N
    aideOption.setForeground(Color.WHITE);
    aideOption.setText("Aide :");
    options.add(aideOption, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 600, 200, 45));

    acAide.setFont(uniFont.deriveFont((float) 28)); // NOI18N
    acAide.setForeground(Color.WHITE);
    acAide.setText("Activé ");
    options.add(acAide, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 650, 160, 40));
    volumeCheck.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            volumeCheckActionPerformed(evt);
        }
    });
    options.add(volumeCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 322, -1, 20));

    regles.setMaximumSize(new java.awt.Dimension(1920, 1080));
    regles.setMinimumSize(new java.awt.Dimension(1920, 1080));
    regles.setOpaque(false);
    regles.setPreferredSize(new java.awt.Dimension(1920, 1080));
    regles.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    retourRegles.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    retourRegles.setText("Retour");
    retourRegles.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourReglesActionPerformed(evt);
      }
    });
    regles.add(retourRegles, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 910, 110, 45));

    reglesScrollPane.setBackground(new Color(0, 0, 0, 0));
    reglesScrollPane.setBorder(null);
    reglesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    reglesPane.setEditable(false);
    reglesPane.setBackground(new java.awt.Color(201, 152, 104));
    reglesPane.setBorder(null);
    reglesPane.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    reglesPane.setText(
        "But :\n\nLes joueurs posent des tuiles tour après tour afin de créer un paysage formé de routes, de villes, d’abbayes. \nVous placerez vos meeples(pions) sur ces tuiles comme voleurs, chevaliers, moines ou paysans afin de marquer des points. \nLe joueur qui aura le plus de points après le décompte final sera déclaré vainqueur.  \n\nDéroulement : \n\tLe joueur dont c’est le tour réalise les actions suivantes dans l’ordre :\n \n\t- Placement d’une tuile : Le joueur doit piocher une tuile Terrain et la placer face visible afin de continuer le paysage. \n\t   La tuile piocher sera visible en bas à droite de l’écran.\n \n\t- Pose d’un meeple : Le joueur peut poser un meeple de sa réserve sur la tuile qu’il vient de placer. \n\t   Ce n’est pas obligatoire. On ne peut pas placer 2 meeples sur 2 cases adjacentes. \n\nLes points : \t\n\tLes routes \t\n\t\tAprès avoir placé la tuile, vous pouvez placer un meeple comme voleur sur une des\n\t sections de route de cette tuile. Attention, cela n’est possible que s’il n’y a pas déjà un voleur sur cette route. \n\tVotre adversaire pioche alors une tuile qu’il place pour continuer le paysage. Pour qu’une route soit complétée\n\tet rapporte des points, ses deux extrémités doivent être reliées à un village, une ville ou une abbaye, ou entre elles\n\ten formant une boucle. Même si c’est l’un de vos adversaires qui a placé la tuile, cela complète quand même votre route. \n\tLorsque qu’une route est complète chaque tuile de cette dernière vous rapporte 1 point \n\n\tLes villes \n\t\tAprès avoir placé la tuile, vous pouvez placer un meeple comme chevalier sur une des sections de ville\n\t de cette tuile. Attention, cela n’est possible que s’il n’y a pas déjà un chevalier dans cette ville. Votre adversaire pioche alors \n\tune tuile qu’il place pour continuer le paysage. Pour qu’une ville soit complétée et rapporte des points, elle doit être entourée\n\tde murs sans trou à l’intérieur. Même si c’est l’un de vos adversaires qui a placé la tuile, cela complète quand même votre ville. \n\tLorsque qu’une ville est complète chaque tuile de cette dernière vous rapporte 2 points, de plus, chaque blason dans une ville \n\tcomplétée rapporte 2 points de plus.  \n\n\tLes abbayes \n\t\tAprès avoir placé la tuile, vous pouvez placer un meeple comme moine sur une abbayes. \n\tUne abbaye est complétée lorsqu’elle est complètement entourée de tuiles, une abbaye complétée rapporte \n\t1 point par tuile la complétant (incluant celle de l’abbaye).");
    reglesPane.setFocusable(false);
    reglesPane.setMargin(new java.awt.Insets(100, 100, 100, 100));
    reglesPane.setCaretPosition(0);
    reglesScrollPane.setViewportView(reglesPane);

    regles.add(reglesScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 220, 1285, 640));

    regles.add(reglesScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 220, 1285, 640));

    credits.setMaximumSize(new java.awt.Dimension(1920, 1080));
    credits.setMinimumSize(new java.awt.Dimension(1920, 1080));
    credits.setOpaque(false);
    credits.setPreferredSize(new java.awt.Dimension(1920, 1080));
    credits.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    retourCredits.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    retourCredits.setText("Retour");
    retourCredits.setAlignmentX(1.0F);
    retourCredits.setAlignmentY(1.0F);
    retourCredits.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourCreditsActionPerformed(evt);
      }
    });
    credits.add(retourCredits, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 910, 110, 45));

    creditsTextArea.setEditable(false);
    creditsTextArea.setBackground(new Color(0, 0, 0, 0));
    creditsTextArea.setColumns(20);
    creditsTextArea.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    creditsTextArea.setRows(5);
    creditsTextArea.setText(
        "\t\n\n\t                       \n\n                                                  \n                                                \n                                            \n\n\t                    BELLIER Benjamin\n                                              BERENGUIER Lucas\n                                              BERTRAMOND Camille\n\t                    FERREIRA Alexis\n\t                    KETTENIS Soteris\n                                              LOUBEAU Ludovic");
    creditsTextArea.setBorder(null);
    creditsTextArea.setFocusable(false);
    creditsTextArea.setMinimumSize(null);
    creditsTextArea.setPreferredSize(null);
    credits.add(creditsTextArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 1260, 720));

    plateauJeu.setMaximumSize(new java.awt.Dimension(1920, 1080));
    plateauJeu.setMinimumSize(new java.awt.Dimension(1920, 1080));
    plateauJeu.setPreferredSize(new java.awt.Dimension(1920, 1080));
    plateauJeu.setOpaque(true);
    plateauJeu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());


    scoreFin.setBackground(new Color(0, 0, 0, 50));
    scoreFin.setOpaque(false);
    scoreFin.setMaximumSize(new java.awt.Dimension(1920, 1080));
    scoreFin.setMinimumSize(new java.awt.Dimension(1920, 1080));
    scoreFin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());


    panelTable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    panelTable.setMaximumSize(new java.awt.Dimension(1920, 1080));
    panelTable.setMinimumSize(new java.awt.Dimension(1920, 1080));
    panelTable.setPreferredSize(new java.awt.Dimension(1920, 1080));
    panelTable.setBackground(new Color(0,0,0,50));
    panelTable.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    scoreTable.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    scoreTable.setOpaque(false);
    DefaultTableCellRenderer hRenderer = (DefaultTableCellRenderer) scoreTable.getTableHeader().getDefaultRenderer();
    hRenderer.setHorizontalAlignment(0);
    hRenderer.setOpaque(false);
    scoreTable.getTableHeader().setPreferredSize(new Dimension(300, 80));
    scoreTable.getTableHeader().setDefaultRenderer(hRenderer);
    scoreTable.getTableHeader().setFont(uniFont.deriveFont((float) 40));
    scoreTable.setForeground(new Color(255, 255, 255));
    scoreTable.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {
            {null, null},
            {null, null},
            {null, null},
            {null, null},
            {null, null}
        },
        new String [] {
            "Joueurs", "Points"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    scoreTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
    scoreTable.setAutoscrolls(false);
    scoreTable.setFocusable(false);
    scoreTable.setRowHeight(75);
    scoreTable.setShowVerticalLines(false);
    scoreTable.getTableHeader().setReorderingAllowed(false);
    finScrollPane.setOpaque(false);
    finScrollPane.setBackground(new Color(0, 0, 0, (float) 0.25));
    finScrollPane.getViewport().setOpaque(false);
    finScrollPane.setViewportView(scoreTable);
    finScrollPane.setFocusable(false);


    panelTable.add(finScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(317, 316, 1285, 465));
    panelTable.setBackground(new Color(0, 0, 0, (float) 0.50));

    scoreContinuer.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    scoreContinuer.setText("->");
    scoreContinuer.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            scoreContinuerActionPerformed(evt);
        }
    });
    panelTable.add(scoreContinuer, new org.netbeans.lib.awtextra.AbsoluteConstraints(1560, 790, 110, 45));

    scoreFin.add(panelTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

    plateauJeu.add(scoreFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

    menuInGame.setBackground(new Color(0, 0, 0, 50));
    menuInGame.setMaximumSize(new java.awt.Dimension(1920, 1080));
    menuInGame.setMinimumSize(new java.awt.Dimension(1920, 1080));
    menuInGame.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    menuInGame.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(java.awt.event.MouseEvent evt) {
        menuInGameMousePressed(evt);
      }
    });

    menuBoutons.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    menuBoutons.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    sauvegarderInGame.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    sauvegarderInGame.setText("Sauvegarder");
    sauvegarderInGame.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sauvegarderInGameActionPerformed(evt);
      }
    });
    menuBoutons.add(sauvegarderInGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 36, 200, 45));

    reglesInGame.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    reglesInGame.setText("Règles");
    reglesInGame.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        reglesInGameActionPerformed(evt);
      }
    });
    menuBoutons.add(reglesInGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 117, 200, 45));

    retourInGame.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    retourInGame.setText("Retour au Jeu");
    retourInGame.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourInGameActionPerformed(evt);
      }
    });
    menuBoutons.add(retourInGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 198, 200, 45));

    retourMenuInGame.setFont(uniFont.deriveFont((float) 26)); // NOI18N
    retourMenuInGame.setText("Menu Principal");
    retourMenuInGame.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourMenuInGameActionPerformed(evt);
      }
    });
    menuBoutons.add(retourMenuInGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 279, 200, 45));

    menuInGame.add(menuBoutons, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 360, 240, 360));

    plateauJeu.add(menuInGame, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

    newGame.setMaximumSize(new java.awt.Dimension(1920, 1080));
    newGame.setMinimumSize(new java.awt.Dimension(1920, 1080));
    newGame.setOpaque(false);
    newGame.setPreferredSize(new java.awt.Dimension(1920, 1080));
    newGame.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    retourParametre.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    retourParametre.setText("Retour");
    retourParametre.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourParametreActionPerformed(evt);
      }
    });
    newGame.add(retourParametre, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 910, 110, 45));

    ajouterJoueur.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    ajouterJoueur.setText("Ajouter un joueur");
    ajouterJoueur.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ajouterJoueurActionPerformed(evt);
      }
    });
    newGame.add(ajouterJoueur, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 330, 250, 50));

    ajouterIA.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    ajouterIA.setText("Ajouter IA");
    ajouterIA.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ajouterIAActionPerformed(evt);
      }
    });
    newGame.add(ajouterIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 670, 250, 50));

    lancerLaPartie.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    lancerLaPartie.setText("Lancer la partie");
    lancerLaPartie.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        lancerLaPartieActionPerformed(evt);
      }
    });
    newGame.add(lancerLaPartie, new org.netbeans.lib.awtextra.AbsoluteConstraints(1500, 910, 250, 50));

    difficulterBox.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    difficulterBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Facile"/* , "Moyen", "Terminator" */ }));
    newGame.add(difficulterBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 670, 200, 50));

    cBleu.setBackground(new java.awt.Color(7, 45, 249));
    cBleu.setBorder(null);
    cBleu.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cBleuActionPerformed(evt);
      }
    });
    newGame.add(cBleu, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 340, 30, 30));

    cRouge.setBackground(new java.awt.Color(240, 0, 32));
    cRouge.setBorder(null);
    cRouge.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cRougeActionPerformed(evt);
      }
    });
    newGame.add(cRouge, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 340, 30, 30));

    cVert.setBackground(new java.awt.Color(60, 212, 21));
    cVert.setBorder(null);
    cVert.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cVertActionPerformed(evt);
      }
    });
    newGame.add(cVert, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 340, 30, 30));

    cJaune.setBackground(new java.awt.Color(255, 235, 87));
    cJaune.setBorder(null);
    cJaune.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cJauneActionPerformed(evt);
      }
    });
    newGame.add(cJaune, new org.netbeans.lib.awtextra.AbsoluteConstraints(785, 340, 30, 30));

    cNoir.setBackground(new java.awt.Color(31, 31, 31));
    cNoir.setBorder(null);
    cNoir.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cNoirActionPerformed(evt);
      }
    });
    newGame.add(cNoir, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 340, 30, 30));

    joueurs.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    joueurs.setForeground(new java.awt.Color(255, 255, 255));
    joueurs.setText("Joueurs :");
    newGame.add(joueurs, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 265, -1, 36));

    j1.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    newGame.add(j1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 340, 225, 30));

    pseudoLabel.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    pseudoLabel.setForeground(new java.awt.Color(255, 255, 255));
    pseudoLabel.setText("Pseudo:");
    newGame.add(pseudoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 265, -1, 44));

    j2.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    newGame.add(j2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 440, 225, 30));

    j3.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    newGame.add(j3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 540, 225, 30));

    j4.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    newGame.add(j4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 640, 225, 30));

    j5.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    newGame.add(j5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 740, 225, 30));

    pseudo.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    newGame.add(pseudo, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 335, 225, 40));

    supprimerJ1.setBorder(null);
    supprimerJ1.setBackground(new Color(0, 0, 0, 0));
    supprimerJ1.setIcon(new ImageIcon(imgs.croix()));
    supprimerJ1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        supprimerJ1ActionPerformed(evt);
      }
    });
    newGame.add(supprimerJ1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 340, 30, 30));

    supprimerJ2.setAlignmentX(0.5F);
    supprimerJ2.setBackground(new Color(0, 0, 0, 0));
    supprimerJ2.setIcon(new ImageIcon(imgs.croix()));
    supprimerJ2.setBorder(null);
    supprimerJ2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        supprimerJ2ActionPerformed(evt);
      }
    });
    newGame.add(supprimerJ2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 440, 30, 30));

    supprimerJ3.setBorder(null);
    supprimerJ3.setBackground(new Color(0, 0, 0, 0));
    supprimerJ3.setIcon(new ImageIcon(imgs.croix()));
    supprimerJ3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        supprimerJ3ActionPerformed(evt);
      }
    });
    newGame.add(supprimerJ3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 540, 30, 30));

    supprimerJ4.setBorder(null);
    supprimerJ4.setBackground(new Color(0, 0, 0, 0));
    supprimerJ4.setIcon(new ImageIcon(imgs.croix()));
    supprimerJ4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        supprimerJ4ActionPerformed(evt);
      }
    });
    newGame.add(supprimerJ4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 640, 30, 30));

    supprimerJ5.setBorder(null);
    supprimerJ5.setBackground(new Color(0, 0, 0, 0));
    supprimerJ5.setIcon(new ImageIcon(imgs.croix()));
    supprimerJ5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        supprimerJ5ActionPerformed(evt);
      }
    });
    newGame.add(supprimerJ5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 740, 30, 30));

    cJoueurLabel.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    cJoueurLabel.setForeground(new java.awt.Color(255, 255, 255));
    cJoueurLabel.setText("Couleur du Joueur:");
    newGame.add(cJoueurLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 265, -1, 34));

    regles2.setMaximumSize(new java.awt.Dimension(1920, 1080));
    regles2.setMinimumSize(new java.awt.Dimension(1920, 1080));
    regles2.setOpaque(false);
    regles2.setPreferredSize(new java.awt.Dimension(1920, 1080));
    regles2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    retourRegles2.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    retourRegles2.setText("Retour Au Jeu");
    retourRegles2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourRegles2ActionPerformed(evt);
      }
    });
    regles2.add(retourRegles2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 910, 200, 45));

    reglesScrollPane2.setBackground(new Color(0, 0, 0, 0));
    reglesScrollPane2.setBorder(null);
    reglesScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    reglesPane1.setEditable(false);
    reglesPane1.setBackground(new java.awt.Color(201, 152, 104));
    reglesPane1.setBorder(null);
    reglesPane1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    reglesPane1.setText(
        "But :\n\nLes joueurs posent des tuiles tour après tour afin de créer un paysage formé de routes, de villes, d’abbayes. \nVous placerez vos meeples(pions) sur ces tuiles comme voleurs, chevaliers, moines ou paysans afin de marquer des points. \nLe joueur qui aura le plus de points après le décompte final sera déclaré vainqueur.  \n\nDéroulement : \n\tLe joueur dont c’est le tour réalise les actions suivantes dans l’ordre :\n \n\t- Placement d’une tuile : Le joueur doit piocher une tuile Terrain et la placer face visible afin de continuer le paysage. \n\t   La tuile piocher sera visible en bas à droite de l’écran.\n \n\t- Pose d’un meeple : Le joueur peut poser un meeple de sa réserve sur la tuile qu’il vient de placer. \n\t   Ce n’est pas obligatoire. On ne peut pas placer 2 meeples sur 2 cases adjacentes. \n\nLes points : \t\n\tLes routes \t\n\t\tAprès avoir placé la tuile, vous pouvez placer un meeple comme voleur sur une des\n\t sections de route de cette tuile. Attention, cela n’est possible que s’il n’y a pas déjà un voleur sur cette route. \n\tVotre adversaire pioche alors une tuile qu’il place pour continuer le paysage. Pour qu’une route soit complétée\n\tet rapporte des points, ses deux extrémités doivent être reliées à un village, une ville ou une abbaye, ou entre elles\n\ten formant une boucle. Même si c’est l’un de vos adversaires qui a placé la tuile, cela complète quand même votre route. \n\tLorsque qu’une route est complète chaque tuile de cette dernière vous rapporte 1 point \n\n\tLes villes \n\t\tAprès avoir placé la tuile, vous pouvez placer un meeple comme chevalier sur une des sections de ville\n\t de cette tuile. Attention, cela n’est possible que s’il n’y a pas déjà un chevalier dans cette ville. Votre adversaire pioche alors \n\tune tuile qu’il place pour continuer le paysage. Pour qu’une ville soit complétée et rapporte des points, elle doit être entourée\n\tde murs sans trou à l’intérieur. Même si c’est l’un de vos adversaires qui a placé la tuile, cela complète quand même votre ville. \n\tLorsque qu’une ville est complète chaque tuile de cette dernière vous rapporte 2 points, de plus, chaque blason dans une ville \n\tcomplétée rapporte 2 points de plus.  \n\n\tLes abbayes \n\t\tAprès avoir placé la tuile, vous pouvez placer un meeple comme moine sur une abbayes. \n\tUne abbaye est complétée lorsqu’elle est complètement entourée de tuiles, une abbaye complétée rapporte \n\t1 point par tuile la complétant (incluant celle de l’abbaye).");
    reglesPane1.setFocusable(false);
    reglesPane1.setCaretPosition(0);
    reglesPane1.setMargin(new java.awt.Insets(100, 100, 100, 100));
    reglesScrollPane2.setViewportView(reglesPane1);

    regles2.add(reglesScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 220, 1285, 640));

    layoutJeu.setBackground(new Color(0, 0, 0, 0));
    layoutJeu.setMaximumSize(new java.awt.Dimension(1920, 1080));
    layoutJeu.setMinimumSize(new java.awt.Dimension(1920, 1080));
    layoutJeu.setPreferredSize(new java.awt.Dimension(1920, 1080));
    layoutJeu.setOpaque(false);
    layoutJeu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    hand.setVisible(true);
    hand.setFocusable(false);
    layoutJeu.add(hand, new org.netbeans.lib.awtextra.AbsoluteConstraints(1710, 870, 145, 145));
    
    tourJ1.setIcon(new ImageIcon(imgs.lueur()));
    layoutJeu.add(tourJ1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 41, 310, 179));

    tourJ2.setIcon(new ImageIcon(imgs.lueur()));
    layoutJeu.add(tourJ2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 241, 310, 190));

    tourJ3.setIcon(new ImageIcon(imgs.lueur()));
    layoutJeu.add(tourJ3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 441, 310, 190));

    tourJ4.setIcon(new ImageIcon(imgs.lueur()));
    layoutJeu.add(tourJ4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 641, 310, 200));

    tourJ5.setIcon(new ImageIcon(imgs.lueur()));
    layoutJeu.add(tourJ5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 841, 310, 200));

    cmpMeepleBleu.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    cmpMeepleBleu.setForeground(new java.awt.Color(255, 255, 255));
    layoutJeu.add(cmpMeepleBleu, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 82, 40, 30));
    ptsBleu.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    ptsBleu.setForeground(new java.awt.Color(255, 255, 255));
    layoutJeu.add(ptsBleu, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 125, -1, -1));
    layoutJeu.add(imageBleu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 260, 150));

    cmpMeepleJaune.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    cmpMeepleJaune.setForeground(new java.awt.Color(255, 255, 255));
    layoutJeu.add(cmpMeepleJaune, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 282, 40, -1));
    ptsJaune.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    ptsJaune.setForeground(new java.awt.Color(255, 255, 255));
    layoutJeu.add(ptsJaune, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 331, 40, 40));
    layoutJeu.add(imageJaune, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 260, 150));

    cmpMeepleRouge.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    cmpMeepleRouge.setForeground(new java.awt.Color(255, 255, 255));
    layoutJeu.add(cmpMeepleRouge, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 688, 30, -1));
    ptsRouge.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    ptsRouge.setForeground(new java.awt.Color(255, 255, 255));
    layoutJeu.add(ptsRouge, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 735, -1, -1));
    layoutJeu.add(imageRouge, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 670, 260, 140));

    cmpMeepleNoire.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    cmpMeepleNoire.setForeground(new java.awt.Color(255, 255, 255));
    layoutJeu.add(cmpMeepleNoire, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 488, 30, 40));
    ptsNoire.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    ptsNoire.setForeground(new java.awt.Color(255, 255, 255));
    layoutJeu.add(ptsNoire, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 537, 40, 40));
    layoutJeu.add(imageNoir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 465, 260, 150));

    cmpMeepleVert.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    cmpMeepleVert.setForeground(new java.awt.Color(255, 255, 255));
    layoutJeu.add(cmpMeepleVert, new org.netbeans.lib.awtextra.AbsoluteConstraints(198, 892, 30, -1));
    ptsVert.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    ptsVert.setForeground(new java.awt.Color(255, 255, 255));
    layoutJeu.add(ptsVert, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 940, 40, -1));
    layoutJeu.add(imageVert, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 875, 260, 140));

    slash.setFont(uniFont.deriveFont((float) 55)); // NOI18N
    slash.setForeground(new java.awt.Color(255, 255, 255));
    slash.setText("/");
    layoutJeu.add(slash, new org.netbeans.lib.awtextra.AbsoluteConstraints(1600, 960, 30, 50));

    nbTuileRestante.setFont(uniFont.deriveFont((float) 55)); // NOI18N
    nbTuileRestante.setForeground(new java.awt.Color(255, 255, 255));
    layoutJeu.add(nbTuileRestante, new org.netbeans.lib.awtextra.AbsoluteConstraints(1550, 940, 50, 50));

    nbTuileTotal.setFont(uniFont.deriveFont((float) 55)); // NOI18N
    nbTuileTotal.setForeground(new java.awt.Color(255, 255, 255));
    nbTuileTotal.setText("72");
    layoutJeu.add(nbTuileTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1620, 980, 70, 50));

    valider.setIcon(new ImageIcon(imgs.valider()));
    layoutJeu.add(valider, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 760, -1, -1));

    refaire.setIcon(new ImageIcon(imgs.refaire()));
    layoutJeu.add(refaire, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 760, -1, -1));

    pioche.setIcon(new ImageIcon(imgs.pioche()));
    pioche.setPreferredSize(new java.awt.Dimension(422, 309));
    layoutJeu.add(pioche, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 783, -1, -1));

    plateauJeu.add(layoutJeu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

    
    hint.setBorder(null);
    hint.setBackground(new Color(0,0,0,0));
    hint.setIcon(new ImageIcon(imgs.hint()));
    hint.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
          hintActionPerformed(evt);
      }
    });
    layoutJeu.add(hint, new org.netbeans.lib.awtextra.AbsoluteConstraints(1770, 670, 150, 130));

    menuPlateau.setBackground(new Color(0,0,0,0));
    menuPlateau.setIcon(new ImageIcon(imgs.menu()));
    menuPlateau.setBorder(null);
    menuPlateau.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            menuPlateauActionPerformed(evt);
        }
    });
    layoutJeu.add(menuPlateau, new org.netbeans.lib.awtextra.AbsoluteConstraints(1800, 10, 110, 120));

    javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
    background.setLayout(backgroundLayout);
    backgroundLayout.setHorizontalGroup(
        backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(regles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(options, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(regles2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jouerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(menuPrincipale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(newGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(plateauJeu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(credits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE));
    backgroundLayout.setVerticalGroup(
        backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(regles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(options, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(regles2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jouerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(menuPrincipale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(newGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(plateauJeu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(credits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)));
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)));
    pack();
  }// </editor-fold>

  private void retourOptionsActionPerformed(java.awt.event.ActionEvent evt) {
    menuPrincipale();
  }

  private void jouerBActionPerformed(java.awt.event.ActionEvent evt) {
    jouerPanel.setVisible(true);
    menuPrincipale.setVisible(false);
    boutonSupDesactiver();
    background.affichageJouer();
  }

  private void nouvellePartieActionPerformed(java.awt.event.ActionEvent evt) {
    jouerPanel.setVisible(false);
    newGame.setVisible(true);
    background.affichageNouvellePartie();
    reinitialiserParametre();
  }

  private void retourPartiesActionPerformed(java.awt.event.ActionEvent evt) {
    menuPrincipale();
  }

  private void menuCreditsActionPerformed(java.awt.event.ActionEvent evt) {
    credits.setVisible(true);
    menuPrincipale.setVisible(false);
    background.affichageCredits();
  }

  private void quitterActionPerformed(java.awt.event.ActionEvent evt) {
    /*
     * String[] options = { "Oui", "Non" };
     * int reply = quitterOptionPane.showOptionDialog(null,
     * "Êtes-vous sûr.e de vouloir quitter le jeu ?", "Quitter le jeu ?" ,
     * quitterOptionPane.YES_NO_OPTION, quitterOptionPane.QUESTION_MESSAGE, null,
     * options, null);
     * if (reply == quitterOptionPane.YES_OPTION) {
     */
    System.exit(0);
    // }
  }

  private void menuReglesActionPerformed(java.awt.event.ActionEvent evt) {
    regles.setVisible(true);
    menuPrincipale.setVisible(false);
    background.affichageRegles();
  }

  private void menuOptionsActionPerformed(java.awt.event.ActionEvent evt) {
    options.setVisible(true);
    menuPrincipale.setVisible(false);
    background.affichageOptions();
  }

  private void retourReglesActionPerformed(java.awt.event.ActionEvent evt) {
    menuPrincipale();
  }

  private void supprimerJ5ActionPerformed(java.awt.event.ActionEvent evt) {
    ajouterJoueur.setEnabled(true);
    j5.setText("");
    c = j5.getForeground();
    activation();
    supprimerJ5.setVisible(false);
    players.remove(4);
    ajouterIA.setEnabled(true);
  }

  private void supprimerJ4ActionPerformed(java.awt.event.ActionEvent evt) {
    ajouterJoueur.setEnabled(true);
    j4.setText("");
    c = j4.getForeground();
    activation();
    players.remove(3);
    remplace4();
    ajouterIA.setEnabled(true);
  }

  private void supprimerJ3ActionPerformed(java.awt.event.ActionEvent evt) {
    ajouterJoueur.setEnabled(true);
    j3.setText("");
    c = j3.getForeground();
    activation();
    players.remove(2);
    remplace3();
    ajouterIA.setEnabled(true);
  }

  private void supprimerJ2ActionPerformed(java.awt.event.ActionEvent evt) {
    ajouterJoueur.setEnabled(true);
    j2.setText("");
    c = j2.getForeground();
    activation();
    players.remove(1);
    remplace2();
    ajouterIA.setEnabled(true);
    if (j2.getText().isEmpty()) {
      lancerLaPartie.setEnabled(false);
    }
  }

  private void supprimerJ1ActionPerformed(java.awt.event.ActionEvent evt) {
    ajouterJoueur.setEnabled(true);
    j1.setText("");
    c = j1.getForeground();
    activation();
    players.remove(0);
    remplace1();
    ajouterIA.setEnabled(true);
    if (j2.getText().isEmpty()) {
      lancerLaPartie.setEnabled(false);
    }
  }

  private void cNoirActionPerformed(java.awt.event.ActionEvent evt) {
    c = couleurNoir;
    choixNom();
  }

  private void cJauneActionPerformed(java.awt.event.ActionEvent evt) {
    c = couleurJaune;
    choixNom();
  }

  private void cVertActionPerformed(java.awt.event.ActionEvent evt) {
    c = couleurVert;
    choixNom();
  }

  private void cRougeActionPerformed(java.awt.event.ActionEvent evt) {
    c = couleurRouge;
    choixNom();
  }

  private void cBleuActionPerformed(java.awt.event.ActionEvent evt) {
    c = couleurBleu;
    choixNom();
  }

  private void lancerLaPartieActionPerformed(java.awt.event.ActionEvent evt) {
    newGame.setVisible(false);
    plateauJeu.setVisible(true);
    layoutJeu.setVisible(true);
    GameEngine gm = new GameEngine(players.stream().toArray(Player[]::new));
    plateauJeu.setFont(uniFont);
    plateauJeu.setGameEngine(gm);
    control = new Controleur(gm, scoreFin, scoreTable , menuPlateau );
    keyboard.setControleur(control);
    this.setFocusable(true);
    plateauJeu.addMouseListener(new Mouse(plateauJeu, control));
    control.setAfficheur(plateauJeu);
    cadre();
    sendLabel();
    plateauJeu.afficherPioche();
    control.startGame();
  }

  private void ajouterIAActionPerformed(java.awt.event.ActionEvent evt) {
    switch (difficulterBox.getSelectedIndex()) {
      case 0:
        setColor();
        ajouterIA("IA Facile");
        players.add(new Player("IA Facile", Player.Type.IA_EASY, c));
        ajouterJoueur.setEnabled(false);
        break;
      case 1:
        setColor();
        ajouterIA("IA Moyen");
        players.add(new Player("IA Moyen", Player.Type.IA_MEDIUM, c));
        ajouterJoueur.setEnabled(false);
        break;
      case 2:
        setColor();
        ajouterIA("Terminator");
        players.add(new Player("Terminator", Player.Type.IA_HARD, c));
        ajouterJoueur.setEnabled(false);
        break;
    }

  }

  private void ajouterJoueurActionPerformed(java.awt.event.ActionEvent evt) {
    ajouterHumain(textField);
    pseudo.setText("");
    ajouterJoueur.setEnabled(false);
  }

  private void retourCreditsActionPerformed(java.awt.event.ActionEvent evt) {
    menuPrincipale();
  }

  private void retourParametreActionPerformed(java.awt.event.ActionEvent evt) {
    newGame.setVisible(false);
    jouerPanel.setVisible(true);
    background.affichageJouer();
  }

  private void menuPlateauActionPerformed(java.awt.event.ActionEvent evt) {
    menuInGame.setVisible(true);
    menuBoutons.setVisible(true);
    menuPlateau.setVisible(true);
    menuInGame.setFocusable(true);
  }

  private void sauvegarderInGameActionPerformed(java.awt.event.ActionEvent evt) {

  }

  private void reglesInGameActionPerformed(java.awt.event.ActionEvent evt) {
    menuInGame.setVisible(false);
    plateauJeu.setVisible(false);
    regles2.setVisible(true);
    menuPlateau.setVisible(true);
    background.affichageRegles();
  }

  private void retourInGameActionPerformed(java.awt.event.ActionEvent evt) {
    menuInGame.setVisible(false);
    plateauJeu.setFocusable(true);
    menuPlateau.setVisible(true);
  }

  private void retourMenuInGameActionPerformed(java.awt.event.ActionEvent evt) {
    menuPrincipale();
    menuPlateau.setVisible(true);
  }

  private void menuInGameMousePressed(java.awt.event.MouseEvent evt) {
    menuInGame.setBackground(new Color(0, 0, 0, 50));
  }

  private void retourRegles2ActionPerformed(java.awt.event.ActionEvent evt) {
    plateauJeu.setVisible(true);
    regles2.setVisible(false);
    menuPlateau.setVisible(true);
  }

  private void scoreContinuerActionPerformed(java.awt.event.ActionEvent evt) {
      menuPrincipale();
      menuPlateau.setVisible(true);
  }

  private void hintActionPerformed(java.awt.event.ActionEvent evt) {                                     
    if (players.size() == 2){
      lueur2J();
    } else if (players.size() == 3) {
      lueur3J(); 
    } else if (players.size() == 4) {
      lueur4J(); 
    } else { 
      lueur5J();
    }
  }  

  private void volumeCheckActionPerformed(java.awt.event.ActionEvent evt) {                                            
      if (!volumeCheck.isSelected()){
        System.out.println("Music stop");
        audioPlayer.music.stop();
        Configuration.instance().setProperty("MusicState", "false");
      } else {
        System.out.println("Music start");
        audioPlayer.music.play();
        Configuration.instance().setProperty("MusicState", "true");
      }
  }

  private void aideCheckActionPerformed(java.awt.event.ActionEvent evt) {                                          
    // TODO add your handling code here:
  }              

  // Variables declaration - do not modify
  private javax.swing.JButton ajouterIA;
  private javax.swing.JButton ajouterJoueur;
  public Background background;
  private javax.swing.JButton cBleu;
  private javax.swing.JButton cJaune;
  private javax.swing.JLabel cJoueurLabel;
  private javax.swing.JButton cNoir;
  private javax.swing.JButton cRouge;
  private javax.swing.JButton cVert;
  public javax.swing.JPanel credits;
  private javax.swing.JTextArea creditsTextArea;
  private javax.swing.JComboBox<String> difficulterBox;
  private javax.swing.JLabel j1;
  private javax.swing.JLabel j2;
  private javax.swing.JLabel j3;
  private javax.swing.JLabel j4;
  private javax.swing.JLabel j5;
  private javax.swing.JButton jeuEnReseaux;
  public javax.swing.JPanel jouerPanel;
  private javax.swing.JLabel joueurs;
  private javax.swing.JButton lancerLaPartie;
  private javax.swing.JButton menuCredits;
  private javax.swing.JPanel menuInGame;
  private javax.swing.JButton menuOptions;
  private javax.swing.JButton menuPlateau;
  public javax.swing.JPanel menuPrincipale;
  private javax.swing.JButton menuRegles;
  private javax.swing.JPanel newGame;
  private javax.swing.JButton nouvellePartie;
  public javax.swing.JPanel options;
  public AffichePlateau plateauJeu;
  private javax.swing.JTextField pseudo;
  private javax.swing.JLabel pseudoLabel;
  private javax.swing.JButton quitter;
  public javax.swing.JOptionPane quitterOptionPane;
  public javax.swing.JPanel regles;
  private javax.swing.JButton reglesInGame;
  private javax.swing.JTextPane reglesPane;
  private javax.swing.JScrollPane reglesScrollPane;
  private javax.swing.JButton retourCredits;
  private javax.swing.JButton retourInGame;
  private javax.swing.JButton retourMenuInGame;
  private javax.swing.JButton retourOptions;
  private javax.swing.JButton retourParametre;
  private javax.swing.JButton retourParties;
  private javax.swing.JButton retourRegles;
  private javax.swing.JScrollPane sauvegardeScroll;
  private javax.swing.JTable sauvegardeTable;
  private javax.swing.JButton sauvegarderInGame;
  private javax.swing.JButton supprimerJ1;
  private javax.swing.JButton supprimerJ2;
  private javax.swing.JButton supprimerJ3;
  private javax.swing.JButton supprimerJ4;
  private javax.swing.JButton supprimerJ5;
  private javax.swing.JLabel version;
  private javax.swing.JPanel menuBoutons;
  public javax.swing.JPanel regles2;
  private javax.swing.JScrollPane reglesScrollPane2;
  private javax.swing.JTextPane reglesPane1;
  private javax.swing.JButton retourRegles2;
  private javax.swing.JButton jouerB;
  private javax.swing.JLabel jouerL;
  private javax.swing.JPanel layoutJeu;
  private javax.swing.JLabel valider;
  private javax.swing.JLabel ptsBleu;
  private javax.swing.JLabel ptsJaune;
  private javax.swing.JLabel ptsNoire;
  private javax.swing.JLabel ptsRouge;
  private javax.swing.JLabel ptsVert;
  private javax.swing.JLabel refaire;
  private javax.swing.JLabel pioche;
  private javax.swing.JLabel imageBleu;
  private javax.swing.JLabel imageJaune;
  private javax.swing.JLabel imageNoir;
  private javax.swing.JLabel imageRouge;
  private javax.swing.JLabel imageVert;
  private javax.swing.JLabel cmpMeepleBleu;
  private javax.swing.JLabel cmpMeepleJaune;
  private javax.swing.JLabel cmpMeepleNoire;
  private javax.swing.JLabel cmpMeepleRouge;
  private javax.swing.JLabel cmpMeepleVert;
  private javax.swing.JLabel nbTuileRestante;
  private javax.swing.JLabel nbTuileTotal;
  private javax.swing.JLabel slash;
  private javax.swing.JPanel scoreFin;
  private javax.swing.JTable scoreTable;
  private javax.swing.JPanel panelTable;
  private javax.swing.JScrollPane finScrollPane;
  private AfficheCurrentTile hand;
  private javax.swing.JButton scoreContinuer;
  private javax.swing.JLabel tourJ1;
  private javax.swing.JLabel tourJ2;
  private javax.swing.JLabel tourJ3;
  private javax.swing.JLabel tourJ4;
  private javax.swing.JLabel tourJ5;
  private javax.swing.JButton hint;
  private javax.swing.JLabel acAide;
  private javax.swing.JLabel acVolume;
  private javax.swing.JCheckBox aideCheck;
  private javax.swing.JLabel aideOption;
  private javax.swing.JLabel titreVolume;
  private javax.swing.JSlider sliderIA;
  private javax.swing.JLabel vitesseIA;
  private javax.swing.JCheckBox volumeCheck;
  // End of variables declaration
}