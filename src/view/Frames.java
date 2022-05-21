package view;

import controller.Controleur;
import global.Configuration;
import model.GameEngine;
import model.Player;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

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
  Color c, bleu, noir, vert, rouge;
  private Color couleurBleu = new Color(7, 45, 249);
  private Color couleurRouge = new Color(245, 31, 27);
  private Color couleurNoir = new Color(31, 31, 31);
  private Color couleurVert = new Color(60, 212, 21);
  private Color couleurJaune = new Color(255, 235, 87);
  private String textField;
  ArrayList<Player> players = new ArrayList<>();
  Keybord keyboard = new Keybord();
  Font uniFont;

  /**
   * Creates new form Frames
   */
  public Frames() {
    setIcon();
    loadFont();
    imgs = new Images();
    basculeEnPleineEcran();
    initComponents();
    setupBoutons();
    setupPanel();
    addKeyListener(keyboard);
  }

  void setIcon() {
    try {
      setIconImage(ImageIO.read(Configuration.charge("Images/logo.png")));
    } catch(IOException e) {
      Configuration.instance().logger().severe("Impossible de charger l'icon");
    }
  }

  private void setupBoutons() {
    // menuPanel
    // optionsPanel
    // reglesPanel
    // creditsPanel
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
    ajouterIA.setEnabled(false);

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

  public void display() {
    System.out.println("display");
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
    ajouterIA.setEnabled(false);
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
    jouer = new javax.swing.JButton();
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
    plateauJeu = new AffichePlateau();
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

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Carcassonne");
    setMinimumSize(new java.awt.Dimension(1280, 720));

    background.setMinimumSize(null);
    background.setPreferredSize(new java.awt.Dimension(1980, 1080));

    menuPrincipale.setOpaque(false);
    menuPrincipale.setPreferredSize(new java.awt.Dimension(1920, 1080));

    jouer.setFont(uniFont.deriveFont((float) 34)); // NOI18N
    jouer.setText("Jouer");
    jouer.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jouerActionPerformed(evt);
      }
    });

    version.setFont(uniFont.deriveFont((float) 16)); // NOI18N
    version.setForeground(new java.awt.Color(0xffffff));
    version.setText(Configuration.instance().lis("Version"));

    quitter.setFont(uniFont.deriveFont((float) 26)); // NOI18N
    quitter.setText("Quitter");
    quitter.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        quitterActionPerformed(evt);
      }
    });

    menuCredits.setFont(uniFont.deriveFont((float) 28)); // NOI18N
    menuCredits.setText("Crédits");
    menuCredits.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuCreditsActionPerformed(evt);
      }
    });

    menuOptions.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    menuOptions.setText("Options");
    menuOptions.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuOptionsActionPerformed(evt);
      }
    });

    menuRegles.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    menuRegles.setText("Règles");
    menuRegles.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuReglesActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout menuPrincipaleLayout = new javax.swing.GroupLayout(menuPrincipale);
    menuPrincipale.setLayout(menuPrincipaleLayout);
    menuPrincipaleLayout.setHorizontalGroup(
        menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPrincipaleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(menuPrincipaleLayout.createSequentialGroup()
                        .addComponent(menuCredits, javax.swing.GroupLayout.PREFERRED_SIZE, 104,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(quitter, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
                            javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(menuRegles, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jouer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE)
                    .addComponent(menuOptions, javax.swing.GroupLayout.Alignment.TRAILING,
                        javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPrincipaleLayout.createSequentialGroup()
                .addContainerGap(908, Short.MAX_VALUE)
                .addComponent(version, javax.swing.GroupLayout.PREFERRED_SIZE, 131,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(881, Short.MAX_VALUE)));
    menuPrincipaleLayout.setVerticalGroup(
        menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, menuPrincipaleLayout.createSequentialGroup()
                .addGap(353, 353, 353)
                .addComponent(jouer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(menuRegles, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(menuOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(menuCredits, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quitter, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                        javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(version, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(343, Short.MAX_VALUE)));

    jouerPanel.setMinimumSize(null);
    jouerPanel.setOpaque(false);
    jouerPanel.setPreferredSize(new java.awt.Dimension(1920, 1080));

    jeuEnReseaux.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    jeuEnReseaux.setText("Jeu en réseaux");

    nouvellePartie.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    nouvellePartie.setText("Nouvelle Partie");
    nouvellePartie.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        nouvellePartieActionPerformed(evt);
      }
    });

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

    retourParties.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    retourParties.setText("Retour");
    retourParties.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourPartiesActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jouerPanelLayout = new javax.swing.GroupLayout(jouerPanel);
    jouerPanel.setLayout(jouerPanelLayout);
    jouerPanelLayout.setHorizontalGroup(
        jouerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jouerPanelLayout.createSequentialGroup()
                .addContainerGap(421, Short.MAX_VALUE)
                .addComponent(sauvegardeScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 617,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(191, 191, 191)
                .addGroup(jouerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nouvellePartie, javax.swing.GroupLayout.PREFERRED_SIZE, 250,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jeuEnReseaux, javax.swing.GroupLayout.PREFERRED_SIZE, 250,
                        javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(441, Short.MAX_VALUE))
            .addGroup(jouerPanelLayout.createSequentialGroup()
                .addGap(193, 193, 193)
                .addComponent(retourParties, javax.swing.GroupLayout.PREFERRED_SIZE, 107,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    jouerPanelLayout.setVerticalGroup(
        jouerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jouerPanelLayout.createSequentialGroup()
                .addContainerGap(405, Short.MAX_VALUE)
                .addGroup(jouerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jouerPanelLayout.createSequentialGroup()
                        .addComponent(sauvegardeScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 337,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(191, 191, 191)
                        .addComponent(retourParties, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jouerPanelLayout.createSequentialGroup()
                        .addComponent(nouvellePartie, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(128, 128, 128)
                        .addComponent(jeuEnReseaux, javax.swing.GroupLayout.PREFERRED_SIZE, 60,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(373, 373, 373)))));

    options.setMinimumSize(null);
    options.setOpaque(false);
    options.setPreferredSize(new java.awt.Dimension(1920, 1080));

    retourOptions.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    retourOptions.setText("Retour");
    retourOptions.setAlignmentX(0.5F);
    retourOptions.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourOptionsActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout optionsLayout = new javax.swing.GroupLayout(options);
    options.setLayout(optionsLayout);
    optionsLayout.setHorizontalGroup(
        optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(retourOptions)
                .addContainerGap(1704, Short.MAX_VALUE)));
    optionsLayout.setVerticalGroup(
        optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsLayout.createSequentialGroup()
                .addContainerGap(933, Short.MAX_VALUE)
                .addComponent(retourOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap()));

    regles.setOpaque(false);
    regles.setPreferredSize(new java.awt.Dimension(1920, 1080));

    retourRegles.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    retourRegles.setText("Retour");
    retourRegles.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourReglesActionPerformed(evt);
      }
    });

    reglesScrollPane.setBackground(new Color(0, 0, 0, 0));
    reglesScrollPane.setBorder(null);
    reglesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    reglesPane.setEditable(false);
    reglesPane.setBackground(new Color(0, 0, 0, 100));
    reglesPane.setBorder(null);
    reglesPane.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    reglesPane.setText(
        "But :\n\nLes joueurs posent des tuiles tour après tour afin de créer un paysage formé de routes, de villes, d’abbayes. \nVous placerez vos meeples(pions) sur ces tuiles comme voleurs, chevaliers, moines ou paysans afin de marquer des points. \nLe joueur qui aura le plus de points après le décompte final sera déclaré vainqueur.  \n\nDéroulement : \n\tLe joueur dont c’est le tour réalise les actions suivantes dans l’ordre :\n \n\t- Placement d’une tuile : Le joueur doit piocher une tuile Terrain et la placer face visible afin de continuer le paysage. \n\t   La tuile piocher sera visible en bas à droite de l’écran.\n \n\t- Pose d’un meeple : Le joueur peut poser un meeple de sa réserve sur la tuile qu’il vient de placer. \n\t   Ce n’est pas obligatoire. On ne peut pas placer 2 meeples sur 2 cases adjacentes. \n\nLes points : \t\n\tLes routes \t\n\t\tAprès avoir placé la tuile, vous pouvez placer un meeple comme voleur sur une des\n\t sections de route de cette tuile. Attention, cela n’est possible que s’il n’y a pas déjà un voleur sur cette route. \n\tVotre adversaire pioche alors une tuile qu’il place pour continuer le paysage. Pour qu’une route soit complétée\n\tet rapporte des points, ses deux extrémités doivent être reliées à un village, une ville ou une abbaye, ou entre elles\n\ten formant une boucle. Même si c’est l’un de vos adversaires qui a placé la tuile, cela complète quand même votre route. \n\tLorsque qu’une route est complète chaque tuile de cette dernière vous rapporte 1 point \n\n\tLes villes \n\t\tAprès avoir placé la tuile, vous pouvez placer un meeple comme chevalier sur une des sections de ville\n\t de cette tuile. Attention, cela n’est possible que s’il n’y a pas déjà un chevalier dans cette ville. Votre adversaire pioche alors \n\tune tuile qu’il place pour continuer le paysage. Pour qu’une ville soit complétée et rapporte des points, elle doit être entourée\n\tde murs sans trou à l’intérieur. Même si c’est l’un de vos adversaires qui a placé la tuile, cela complète quand même votre ville. \n\tLorsque qu’une ville est complète chaque tuile de cette dernière vous rapporte 2 points, de plus, chaque blason dans une ville \n\tcomplétée rapporte 2 points de plus.  \n\n\tLes abbayes \n\t\tAprès avoir placé la tuile, vous pouvez placer un meeple comme moine sur une abbayes. \n\tUne abbaye est complétée lorsqu’elle est complètement entourée de tuiles, une abbaye complétée rapporte \n\t1 point par tuile la complétant (incluant celle de l’abbaye).");
    reglesPane.setFocusable(false);
    reglesPane.setMargin(new java.awt.Insets(100, 100, 100, 100));
    reglesScrollPane.setViewportView(reglesPane);

    javax.swing.GroupLayout reglesLayout = new javax.swing.GroupLayout(regles);
    regles.setLayout(reglesLayout);
    reglesLayout.setHorizontalGroup(
        reglesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reglesLayout.createSequentialGroup()
                .addGroup(reglesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reglesLayout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(retourRegles))
                    .addGroup(reglesLayout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addComponent(reglesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1285,
                            javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    reglesLayout.setVerticalGroup(
        reglesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reglesLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(reglesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 612,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(retourRegles, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)));

    credits.setOpaque(false);
    credits.setPreferredSize(new java.awt.Dimension(1920, 1080));

    retourCredits.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    retourCredits.setText("Retour");
    retourCredits.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourCreditsActionPerformed(evt);
      }
    });

    creditsTextArea.setEditable(false);
    creditsTextArea.setBackground(new Color(0, 0, 0, 0));
    creditsTextArea.setColumns(20);
    creditsTextArea.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
    creditsTextArea.setRows(5);
    creditsTextArea.setText(
        "\t\n\n\t                       \n\n                                                  \n                                                \n                                            \n\n\t                    BELLIER Benjamin\n                                              BERENGUIER Lucas\n                                              BERTRAMOND Camille\n\t                    FERREIRA Alexis\n\t                    KETTENIS Soteris\n                                              LOUBEAU Ludovic");
    creditsTextArea.setBorder(null);
    creditsTextArea.setFocusable(false);

    javax.swing.GroupLayout creditsLayout = new javax.swing.GroupLayout(credits);
    credits.setLayout(creditsLayout);
    creditsLayout.setHorizontalGroup(
        creditsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creditsLayout.createSequentialGroup()
                .addGroup(creditsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(creditsLayout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(creditsTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 1260,
                            javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(creditsLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(retourCredits)))
                .addContainerGap(410, Short.MAX_VALUE)));
    creditsLayout.setVerticalGroup(
        creditsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creditsLayout.createSequentialGroup()
                .addContainerGap(177, Short.MAX_VALUE)
                .addComponent(creditsTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 720,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(retourCredits, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)));

    plateauJeu.setOpaque(true);
    plateauJeu.setPreferredSize(new java.awt.Dimension(1920, 1080));

    newGame.setMinimumSize(null);
    newGame.setOpaque(false);
    newGame.setPreferredSize(new java.awt.Dimension(1920, 1080));

    retourParametre.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    retourParametre.setText("Retour");
    retourParametre.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourParametreActionPerformed(evt);
      }
    });

    ajouterJoueur.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    ajouterJoueur.setText("Ajouter un joueur");
    ajouterJoueur.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ajouterJoueurActionPerformed(evt);
      }
    });

    ajouterIA.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    ajouterIA.setText("Ajouter IA");
    ajouterIA.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ajouterIAActionPerformed(evt);
      }
    });

    lancerLaPartie.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    lancerLaPartie.setText("Lancer la partie");
    lancerLaPartie.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        lancerLaPartieActionPerformed(evt);
      }
    });

    difficulterBox.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    difficulterBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Facile", "Moyen", "Terminator" }));

    cBleu.setBackground(new java.awt.Color(7, 45, 249));
    cBleu.setBorder(null);
    cBleu.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cBleuActionPerformed(evt);
      }
    });

    cRouge.setBackground(new java.awt.Color(245, 31, 27));
    cRouge.setBorder(null);
    cRouge.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cRougeActionPerformed(evt);
      }
    });

    cVert.setBackground(new java.awt.Color(60, 212, 21));
    cVert.setBorder(null);
    cVert.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cVertActionPerformed(evt);
      }
    });

    cJaune.setBackground(new java.awt.Color(255, 235, 87));
    cJaune.setBorder(null);
    cJaune.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cJauneActionPerformed(evt);
      }
    });

    cNoir.setBackground(new java.awt.Color(31, 31, 31));
    cNoir.setBorder(null);
    cNoir.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cNoirActionPerformed(evt);
      }
    });

    joueurs.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    joueurs.setForeground(new java.awt.Color(0xffffff));
    joueurs.setText("Joueurs :");

    j1.setFont(uniFont.deriveFont((float) 24)); // NOI18N

    pseudoLabel.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    pseudoLabel.setForeground(new java.awt.Color(0xffffff));
    pseudoLabel.setText("Pseudo:");

    j2.setFont(uniFont.deriveFont((float) 24)); // NOI18N

    j3.setFont(uniFont.deriveFont((float) 24)); // NOI18N

    j4.setFont(uniFont.deriveFont((float) 24)); // NOI18N

    j5.setFont(uniFont.deriveFont((float) 24)); // NOI18N

    pseudo.setFont(uniFont.deriveFont((float) 24)); // NOI18N

    supprimerJ1.setBorder(null);
    supprimerJ1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        supprimerJ1ActionPerformed(evt);
      }
    });

    supprimerJ2.setAlignmentX(0.5F);
    supprimerJ2.setBorder(null);
    supprimerJ2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        supprimerJ2ActionPerformed(evt);
      }
    });

    supprimerJ3.setBorder(null);
    supprimerJ3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        supprimerJ3ActionPerformed(evt);
      }
    });

    supprimerJ4.setBorder(null);
    supprimerJ4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        supprimerJ4ActionPerformed(evt);
      }
    });

    supprimerJ5.setBorder(null);
    supprimerJ5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        supprimerJ5ActionPerformed(evt);
      }
    });

    cJoueurLabel.setFont(uniFont.deriveFont((float) 30)); // NOI18N
    cJoueurLabel.setForeground(new java.awt.Color(0xffffff));
    cJoueurLabel.setText("Couleur du Joueur:");

    javax.swing.GroupLayout newGameLayout = new javax.swing.GroupLayout(newGame);
    newGame.setLayout(newGameLayout);
    newGameLayout.setHorizontalGroup(
        newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newGameLayout.createSequentialGroup()
                .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newGameLayout.createSequentialGroup()
                        .addGap(341, 341, 341)
                        .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 194,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pseudoLabel)
                            .addComponent(difficulterBox, javax.swing.GroupLayout.PREFERRED_SIZE, 170,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(80, 80, 80)
                        .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cJoueurLabel)
                            .addGroup(newGameLayout.createSequentialGroup()
                                .addComponent(cBleu, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(cRouge, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(cVert, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(cJaune, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(cNoir, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addGroup(
                                    newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(ajouterIA, javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ajouterJoueur, javax.swing.GroupLayout.DEFAULT_SIZE,
                                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(newGameLayout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(retourParametre)))
                .addGap(113, 113, 113)
                .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(joueurs)
                    .addGroup(newGameLayout.createSequentialGroup()
                        .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(j4, javax.swing.GroupLayout.PREFERRED_SIZE, 225,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(j5, javax.swing.GroupLayout.PREFERRED_SIZE, 225,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(j3, javax.swing.GroupLayout.PREFERRED_SIZE, 225,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(j2, javax.swing.GroupLayout.PREFERRED_SIZE, 225,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(j1, javax.swing.GroupLayout.PREFERRED_SIZE, 225,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lancerLaPartie, javax.swing.GroupLayout.PREFERRED_SIZE, 250,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(supprimerJ2, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(supprimerJ1, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(supprimerJ3, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(supprimerJ4, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(supprimerJ5, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(143, Short.MAX_VALUE)));
    newGameLayout.setVerticalGroup(
        newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newGameLayout.createSequentialGroup()
                .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newGameLayout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pseudoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cJoueurLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(joueurs, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(newGameLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cRouge, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cVert, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cJaune, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cNoir, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cBleu, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(newGameLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(j1, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(newGameLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(ajouterJoueur, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                    javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newGameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(supprimerJ1, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                            javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50)
                .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(newGameLayout.createSequentialGroup()
                        .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(newGameLayout.createSequentialGroup()
                                .addComponent(j2, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(j3, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(newGameLayout.createSequentialGroup()
                                .addComponent(supprimerJ2, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(supprimerJ3, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50)
                        .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(j4, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(supprimerJ4, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(j5, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(supprimerJ5, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ajouterIA, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                            javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(difficulterBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                            javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(170, 170, 170)
                .addGroup(newGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lancerLaPartie, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(retourParametre, javax.swing.GroupLayout.PREFERRED_SIZE, 45,
                        javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(272, Short.MAX_VALUE)));

    javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
    background.setLayout(backgroundLayout);
    backgroundLayout.setHorizontalGroup(
        backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jouerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1829, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(options, javax.swing.GroupLayout.DEFAULT_SIZE, 1829, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 169, Short.MAX_VALUE)
                    .addComponent(regles, javax.swing.GroupLayout.DEFAULT_SIZE, 1641, Short.MAX_VALUE)
                    .addGap(0, 170, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 339, Short.MAX_VALUE)
                    .addComponent(credits, javax.swing.GroupLayout.DEFAULT_SIZE, 1302, Short.MAX_VALUE)
                    .addGap(0, 339, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(menuPrincipale, javax.swing.GroupLayout.DEFAULT_SIZE, 1968, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addComponent(plateauJeu, javax.swing.GroupLayout.DEFAULT_SIZE, 1829, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(newGame, javax.swing.GroupLayout.DEFAULT_SIZE, 1829, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE))));
    backgroundLayout.setVerticalGroup(
        backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jouerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(options, javax.swing.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 174, Short.MAX_VALUE)
                    .addComponent(regles, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE)
                    .addGap(0, 74, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 182, Short.MAX_VALUE)
                    .addComponent(credits, javax.swing.GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
                    .addGap(0, 181, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(menuPrincipale, javax.swing.GroupLayout.DEFAULT_SIZE, 1068, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addComponent(plateauJeu, javax.swing.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 138, Short.MAX_VALUE)
                    .addComponent(newGame, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
                    .addGap(0, 137, Short.MAX_VALUE))));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()

                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)));
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()

                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)));

    pack();
  }// </editor-fold>

  private void retourOptionsActionPerformed(java.awt.event.ActionEvent evt) {
    menuPrincipale();
  }

  private void jouerActionPerformed(java.awt.event.ActionEvent evt) {
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
    // String[] options = { "Oui", "Non" };
    // int reply = quitterOptionPane.showOptionDialog(null, "Êtes-vous sûr.e de
    // vouloir quitter le jeu ?", "Quitter le jeu ?" ,
    // quitterOptionPane.YES_NO_OPTION, quitterOptionPane.QUESTION_MESSAGE, null,
    // options, null);
    // if (reply == quitterOptionPane.YES_OPTION) {
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

  Player[] playersToArray() {
    Player[] list = new Player[players.size()];
    for (int i = 0; i < players.size(); i++) {
      list[i] = players.get(i);
    }
    return list;
  }

  private void lancerLaPartieActionPerformed(java.awt.event.ActionEvent evt) {
    newGame.setVisible(false);
    plateauJeu.setVisible(true);

    GameEngine gm = new GameEngine(playersToArray());
    plateauJeu.setGameEngine(gm);
    Controleur c = new Controleur(gm);
    keyboard.setControleur(c);
    plateauJeu.addMouseListener(new Mouse(plateauJeu, c));
    c.setAfficheur(plateauJeu);

  }

  private void ajouterIAActionPerformed(java.awt.event.ActionEvent evt) {
    switch (difficulterBox.getSelectedIndex()) {
      case 0:
        setColor();
        ajouterIA("IA Facile");
        players.add(new Player("IA Facile", Player.Type.IA_EASY, c));
        break;
      case 1:
        setColor();
        ajouterIA("IA Moyen");
        players.add(new Player("IA Moyen", Player.Type.IA_MEDIUM, c));
        break;
      case 2:
        setColor();
        ajouterIA("Terminator");
        players.add(new Player("Terminator", Player.Type.IA_HARD, c));
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
  private javax.swing.JButton jouer;
  public javax.swing.JPanel jouerPanel;
  private javax.swing.JLabel joueurs;
  private javax.swing.JButton lancerLaPartie;
  private javax.swing.JButton menuCredits;
  private javax.swing.JButton menuOptions;
  public javax.swing.JPanel menuPrincipale;
  private javax.swing.JButton menuRegles;
  private javax.swing.JPanel newGame;
  private javax.swing.JButton nouvellePartie;
  private AffichePlateau plateauJeu;
  public javax.swing.JPanel options;
  private javax.swing.JTextField pseudo;
  private javax.swing.JLabel pseudoLabel;
  private javax.swing.JButton quitter;
  public javax.swing.JOptionPane quitterOptionPane;
  public javax.swing.JPanel regles;
  private javax.swing.JTextPane reglesPane;
  private javax.swing.JScrollPane reglesScrollPane;
  private javax.swing.JButton retourCredits;
  private javax.swing.JButton retourOptions;
  private javax.swing.JButton retourParametre;
  private javax.swing.JButton retourParties;
  private javax.swing.JButton retourRegles;
  private javax.swing.JScrollPane sauvegardeScroll;
  private javax.swing.JTable sauvegardeTable;
  private javax.swing.JButton supprimerJ1;
  private javax.swing.JButton supprimerJ2;
  private javax.swing.JButton supprimerJ3;
  private javax.swing.JButton supprimerJ4;
  private javax.swing.JButton supprimerJ5;
  private javax.swing.JLabel version;
  // End of variables declaration
}