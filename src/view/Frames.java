package view;

import controller.Controleur;
import global.Configuration;
import view.Player.pType;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;



/**
 *
 * @author ludov
 */
public class Frames extends javax.swing.JFrame {
    Player p;
    Controleur control;
    Images imgs;
  
    Random r = new Random();
    Color c , bleu , noir , vert , rouge;
    private Color couleurBleu = new Color(7,45,249);
    private Color couleurRouge = new Color(245,31,27);
    private Color couleurNoir = new Color(31,31,31);
    private Color couleurVert = new  Color(60,212,21);
    private Color couleurJaune = new Color(255,235,87);
    private String textField;
    private String types,pseudos;
    ArrayList <Player> players = new ArrayList<>();
    
    /**
     * Creates new form Frames
     */
    public Frames(Controleur c) {
        control = c;
        imgs = new Images();
        
        initComponents();
        setupBoutons();
        setupPanel();
        control.getInterface(this);
    }
    
    private void setupBoutons() {
    //menuPanel
  
    // optionsPanel
    
    // reglesPanel
   
    // creditsPanel
    

  }
    
    private void setupPanel() {
        menuPrincipale();
        
    
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
        parties.setVisible(false);
        regles.setVisible(false);
        plateauJeu.setVisible(false);
        parametres.setVisible(false);
        quitterOptionPane.setVisible(false);
        parties.setVisible(false);
        ajouterJoueur.setEnabled(false);
        credits.setVisible(false);
    }
  
    public void boutonSupDesactiver(){
        supprimerJ1.setVisible(false);
        supprimerJ2.setVisible(false);
        supprimerJ3.setVisible(false);
        supprimerJ4.setVisible(false);
        supprimerJ5.setVisible(false);
     }
  
    public void choixNom(){
      textField = pseudo.getText();
      if (pseudo.getText().isEmpty()){
        ajouterJoueur.setEnabled(false);
      } else {
          ajouterJoueur.setEnabled(true);
      }
    
    }
 
    private void desactivation(){
        if (c.equals(couleurNoir)){
            cNoir.setEnabled(false);
        } else if (c.equals(couleurRouge)){
            cRouge.setEnabled(false);
        }else if (c.equals(couleurVert)){
            cVert.setEnabled(false);
        }else if (c.equals(couleurJaune)){
            cJaune.setEnabled(false);
        }else if(c.equals(couleurBleu)){
            cBleu.setEnabled(false);
        }
    }
  
    private void activation(){
        if (c.equals(couleurNoir)){
            cNoir.setEnabled(true);
        } else if (c.equals(couleurRouge)){
            cRouge.setEnabled(true);
        }else if (c.equals(couleurVert)){
            cVert.setEnabled(true);
        }else if (c.equals(couleurJaune)){
            cJaune.setEnabled(true);
        }else if(c.equals(couleurBleu)){
            cBleu.setEnabled(true);
        }
    }
  
    private void ajouterHumain(String text){
        if (j1.getText().isEmpty()){
            j1.setForeground(c);
            desactivation();
            j1.setText(text);
            supprimerJ1.setVisible(true);
        }else if (j2.getText().isEmpty() && (!j1.getText().isEmpty())){
            j2.setForeground(c);
            desactivation();
            j2.setText(text);
            supprimerJ2.setVisible(true);
        }else if (j3.getText().isEmpty() && (!j1.getText().isEmpty()) && (!j2.getText().isEmpty())){
            j3.setForeground(c);
            desactivation();
            j3.setText(text);
            supprimerJ3.setVisible(true);
        }else if (j4.getText().isEmpty() && (!j1.getText().isEmpty()) && (!j2.getText().isEmpty())&& (!j3.getText().isEmpty())){
            j4.setForeground(c);
            desactivation();
            j4.setText(text);
            supprimerJ4.setVisible(true);
        }else {
            j5.setForeground(c);
            desactivation();
            j5.setText(text);
            supprimerJ5.setVisible(true);
        }
        p = new Player(text,pType.HUMAN,c);
        players.add(p);
    }
  
    private void ajouterIA(String text){
        
        if (j1.getText().isEmpty()){
            j1.setForeground(c);
            desactivation();
            j1.setText(text);
            supprimerJ1.setVisible(true);
        }else if (j2.getText().isEmpty() && (!j1.getText().isEmpty())){
            j2.setForeground(c);
            desactivation();
            j2.setText(text);
            supprimerJ2.setVisible(true);
        }else if (j3.getText().isEmpty() && (!j1.getText().isEmpty()) && (!j2.getText().isEmpty())){
            j3.setForeground(c);
            desactivation();
            j3.setText(text);
            supprimerJ3.setVisible(true);
        }else if (j4.getText().isEmpty() && (!j1.getText().isEmpty()) && (!j2.getText().isEmpty())&& (!j3.getText().isEmpty())){
            j4.setForeground(c);
            desactivation();
            j4.setText(text);
            supprimerJ4.setVisible(true);
        }else {
            j5.setForeground(c);
            desactivation();
            j5.setText(text);
            supprimerJ5.setVisible(true);
        }
    }
  
    public void setColor(){
        int cRandom = r.nextInt(5);
        switch(cRandom){
            case 0:
                if(!cBleu.isEnabled()){
                    if(cRouge.isEnabled() ||  cVert.isEnabled() || cNoir.isEnabled() || cJaune.isEnabled())
                        setColor();
                }else {
                    c = couleurBleu;
                    desactivation();
                }
                break;
            case 1:
                if(!cRouge.isEnabled()){
                    if(cBleu.isEnabled() ||  cVert.isEnabled() || cNoir.isEnabled() || cJaune.isEnabled())
                        setColor();
                }else {
                    c = couleurRouge;
                    desactivation();
                }
                break;
            case 2:
                if(!cVert.isEnabled()){
                    if(cRouge.isEnabled() ||  cBleu.isEnabled() || cNoir.isEnabled() || cJaune.isEnabled())
                        setColor();
                }else {
                    c = couleurVert;
                    desactivation();
                }
                break;
            case 3:
                if(!cNoir.isEnabled()){
                    if(cRouge.isEnabled() ||  cVert.isEnabled() || cBleu.isEnabled() || cJaune.isEnabled())
                        setColor();
                }else {
                    c = couleurNoir;
                    desactivation();
                }
                break;
            case 4:
                if(!cJaune.isEnabled()){
                    if(cRouge.isEnabled() ||  cVert.isEnabled() || cNoir.isEnabled() || cBleu.isEnabled())
                        setColor();
                }else {
                    c = couleurJaune;
                    desactivation();
                }
                break;
            default:
                break;
        }
    }

    public void affListe(){
        for (int i = 0 ; i<players.size() ; i++){
            System.out.print(players.get(i).pseudo());
            System.out.print(players.get(i).color());
            System.out.println(players.get(i).type());
        }
    }

    private void remplace4(){
        if (!j5.getText().isEmpty()){
            j4.setText( j5.getText());
            j4.setForeground(j5.getForeground());
            j5.setText("");
            supprimerJ5.setVisible(false);
        }else{
            supprimerJ4.setVisible(false);
        }
    }
    private void remplace3(){
        if (!j4.getText().isEmpty()){
            j3.setText( j4.getText());
            j3.setForeground(j4.getForeground());
            j4.setText("");
        }else{
            supprimerJ3.setVisible(false);
        }
        remplace4();
    }
    private void remplace2(){
        if (!j3.getText().isEmpty()){
            j2.setText( j3.getText());
            j2.setForeground(j3.getForeground());
            j3.setText("");
        }else{
            supprimerJ2.setVisible(false);
        }
        remplace3();
    }
    private void remplace1(){
        if (!j2.getText().isEmpty()){
            j1.setText( j2.getText());
            j1.setForeground(j2.getForeground());
            j2.setText("");
        }else{
            supprimerJ1.setVisible(false);
        }
        remplace2();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
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
        parties = new javax.swing.JPanel();
        jeuEnReseaux = new javax.swing.JButton();
        partiesLabel = new javax.swing.JLabel();
        nouvellePartie = new javax.swing.JButton();
        sauvegardeScroll = new javax.swing.JScrollPane();
        sauvegardeTable = new javax.swing.JTable();
        retourParties = new javax.swing.JButton();
        options = new javax.swing.JPanel();
        optionsLabel = new javax.swing.JLabel();
        retourOptions = new javax.swing.JButton();
        regles = new javax.swing.JPanel();
        reglesLabel = new javax.swing.JLabel();
        retourRegles = new javax.swing.JButton();
        credits = new javax.swing.JPanel();
        retourCredits = new javax.swing.JButton();
        creditsTextArea = new javax.swing.JTextArea();
        creditsLabel = new javax.swing.JLabel();
        plateauJeu = new javax.swing.JPanel();
        JeuScrollPane = new javax.swing.JScrollPane();
        parametres = new javax.swing.JPanel();
        parametre = new javax.swing.JLabel();
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
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Carcassonne");
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setPreferredSize(new java.awt.Dimension(1280, 720));

        background.setMinimumSize(new java.awt.Dimension(1280, 720));
        background.setPreferredSize(new java.awt.Dimension(1280, 720));

        menuPrincipale.setMinimumSize(new java.awt.Dimension(1280, 720));
        menuPrincipale.setOpaque(false);
        menuPrincipale.setPreferredSize(new java.awt.Dimension(1280, 720));

        jouer.setText("Jouer");
        jouer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jouerActionPerformed(evt);
            }
        });

        version.setText(Configuration.instance().lis("Version"));

        quitter.setText("Quitter");
        quitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitterActionPerformed(evt);
            }
        });

        menuCredits.setText("Crédits");
        menuCredits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCreditsActionPerformed(evt);
            }
        });

        menuOptions.setText("Options");
        menuOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOptionsActionPerformed(evt);
            }
        });

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
                .addContainerGap(352, Short.MAX_VALUE)
                .addGroup(menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(menuPrincipaleLayout.createSequentialGroup()
                        .addComponent(menuCredits, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(quitter, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(menuRegles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jouer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuOptions, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addContainerGap(352, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPrincipaleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(version, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuPrincipaleLayout.setVerticalGroup(
            menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(menuPrincipaleLayout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addComponent(jouer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(menuRegles, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(menuOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(menuCredits, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quitter, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(version)
                .addContainerGap())
        );

        parties.setMinimumSize(new java.awt.Dimension(1280, 720));
        parties.setOpaque(false);
        parties.setPreferredSize(new java.awt.Dimension(1280, 720));

        jeuEnReseaux.setText("Jeu en réseaux");

        partiesLabel.setText("Parties");

        nouvellePartie.setText("Nouvelle Partie");
        nouvellePartie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nouvellePartieActionPerformed(evt);
            }
        });

        sauvegardeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        sauvegardeScroll.setViewportView(sauvegardeTable);

        retourParties.setText("<-");
        retourParties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retourPartiesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout partiesLayout = new javax.swing.GroupLayout(parties);
        parties.setLayout(partiesLayout);
        partiesLayout.setHorizontalGroup(
            partiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(partiesLayout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addComponent(sauvegardeScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(partiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nouvellePartie, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jeuEnReseaux, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
            .addGroup(partiesLayout.createSequentialGroup()
                .addGroup(partiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(partiesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(partiesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(retourParties, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        partiesLayout.setVerticalGroup(
            partiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(partiesLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(partiesLabel)
                .addGroup(partiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(partiesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sauvegardeScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(retourParties, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(partiesLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(nouvellePartie, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(jeuEnReseaux, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        options.setMinimumSize(new java.awt.Dimension(1280, 720));
        options.setOpaque(false);
        options.setPreferredSize(new java.awt.Dimension(1280, 720));

        retourOptions.setText("Retour");
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
                .addComponent(optionsLabel)
                .addContainerGap(980, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(retourOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        optionsLayout.setVerticalGroup(
            optionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(optionsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 382, Short.MAX_VALUE)
                .addComponent(retourOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        regles.setMinimumSize(new java.awt.Dimension(1280, 720));
        regles.setOpaque(false);
        regles.setPreferredSize(new java.awt.Dimension(1280, 720));

        retourRegles.setText("Retour");
        retourRegles.setAlignmentY(0.0F);
        retourRegles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retourReglesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout reglesLayout = new javax.swing.GroupLayout(regles);
        regles.setLayout(reglesLayout);
        reglesLayout.setHorizontalGroup(
            reglesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reglesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(reglesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(reglesLayout.createSequentialGroup()
                        .addComponent(reglesLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reglesLayout.createSequentialGroup()
                        .addGap(0, 1807, Short.MAX_VALUE)
                        .addComponent(retourRegles, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))))
        );
        reglesLayout.setVerticalGroup(
            reglesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reglesLayout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(reglesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 955, Short.MAX_VALUE)
                .addComponent(retourRegles, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        credits.setMinimumSize(new java.awt.Dimension(1280, 720));
        credits.setOpaque(false);
        credits.setPreferredSize(new java.awt.Dimension(1280, 720));
        retourCredits.setText("Retour");
        retourCredits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retourCreditsActionPerformed(evt);
            }
        });

        creditsTextArea.setEditable(false);
        creditsTextArea.setBackground(new java.awt.Color(240, 240, 240));
        creditsTextArea.setColumns(20);
        creditsTextArea.setRows(5);
        creditsTextArea.setText("\n\t\n\tLOUBEAU Ludovic\n\tBERTRAMOND Camille\n\tKETTENIS Soteris\n\tFERREIRA Alexis\n\tBERENGUIER Lucas\n\tBELLIER Benjamin");
        creditsTextArea.setFocusable(false);

        creditsLabel.setText("Crédits");

        javax.swing.GroupLayout creditsLayout = new javax.swing.GroupLayout(credits);
        credits.setLayout(creditsLayout);
        creditsLayout.setHorizontalGroup(
            creditsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creditsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(creditsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(creditsLayout.createSequentialGroup()
                        .addComponent(creditsLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, creditsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(retourCredits, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))))
            .addGroup(creditsLayout.createSequentialGroup()
                .addContainerGap(368, Short.MAX_VALUE)
                .addComponent(creditsTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 376, Short.MAX_VALUE))
        );
        creditsLayout.setVerticalGroup(
            creditsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, creditsLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(creditsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(creditsTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addComponent(retourCredits, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        plateauJeu.setMinimumSize(new java.awt.Dimension(1280, 720));
        plateauJeu.setOpaque(false);
        plateauJeu.setPreferredSize(new java.awt.Dimension(1280, 720));

        JeuScrollPane.setMinimumSize(new java.awt.Dimension(1280, 720));
        JeuScrollPane.setPreferredSize(new java.awt.Dimension(1280, 720));

        javax.swing.GroupLayout plateauJeuLayout = new javax.swing.GroupLayout(plateauJeu);
        plateauJeu.setLayout(plateauJeuLayout);
        plateauJeuLayout.setHorizontalGroup(
            plateauJeuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JeuScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
        );
        plateauJeuLayout.setVerticalGroup(
            plateauJeuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JeuScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
        );

        parametres.setMinimumSize(new java.awt.Dimension(1280, 720));
        parametres.setOpaque(false);
        parametres.setPreferredSize(new java.awt.Dimension(1280, 720));
        parametre.setText("Parametres ");

        retourParametre.setText("<-");
        retourParametre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retourParametreActionPerformed(evt);
            }
        });

        ajouterJoueur.setText("Ajouter un joueur");
        ajouterJoueur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajouterJoueurActionPerformed(evt);
            }
        });

        ajouterIA.setText("Ajouter IA");
        ajouterIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajouterIAActionPerformed(evt);
            }
        });

        lancerLaPartie.setText("Lancer la partie");
        lancerLaPartie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lancerLaPartieActionPerformed(evt);
            }
        });

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

        joueurs.setText("Joueurs :");

        pseudoLabel.setText("Pseudo:");

        try {
            supprimerJ1.setIcon(new javax.swing.ImageIcon(ImageIO.read(new File("assets/Images/skip.png")))); // NOI18N
        } catch (Exception e) {
            e.printStackTrace();
        }
        supprimerJ1.setBorder(null);
        supprimerJ1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerJ1ActionPerformed(evt);
            }
        });

        try {
            supprimerJ2.setIcon(new javax.swing.ImageIcon(ImageIO.read(new File("assets/Images/skip.png")))); // NOI18N
        } catch (Exception e) {
            e.printStackTrace();
        }
        supprimerJ2.setAlignmentX(0.5F);
        supprimerJ2.setBorder(null);
        supprimerJ2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerJ2ActionPerformed(evt);
            }
        });

        try {
            supprimerJ3.setIcon(new javax.swing.ImageIcon(ImageIO.read(new File("assets/Images/skip.png")))); // NOI18N
        } catch (Exception e) {
            e.printStackTrace();
        }
        supprimerJ3.setBorder(null);
        supprimerJ3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerJ3ActionPerformed(evt);
            }
        });

        try {
            supprimerJ4.setIcon(new javax.swing.ImageIcon(ImageIO.read(new File("assets/Images/skip.png")))); // NOI18N
        } catch (Exception e) {
            e.printStackTrace();
        }
        supprimerJ4.setBorder(null);
        supprimerJ4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerJ4ActionPerformed(evt);
            }
        });

        try {
            supprimerJ5.setIcon(new javax.swing.ImageIcon(ImageIO.read(new File("assets/Images/skip.png")))); // NOI18N
        } catch (Exception e) {
            e.printStackTrace();
        }
        supprimerJ5.setBorder(null);
        supprimerJ5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerJ5ActionPerformed(evt);
            }
        });

        jLabel1.setText("Couleur du Joueur:");

        javax.swing.GroupLayout parametresLayout = new javax.swing.GroupLayout(parametres);
        parametres.setLayout(parametresLayout);
        parametresLayout.setHorizontalGroup(
            parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parametresLayout.createSequentialGroup()
                .addContainerGap(266, Short.MAX_VALUE)
                .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(parametresLayout.createSequentialGroup()
                        .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pseudoLabel)
                            .addComponent(pseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addGroup(parametresLayout.createSequentialGroup()
                                .addComponent(cBleu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cRouge, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cVert, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cJaune, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cNoir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ajouterJoueur, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(parametresLayout.createSequentialGroup()
                        .addComponent(difficulterBox, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(261, 261, 261)
                        .addComponent(ajouterIA, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(parametresLayout.createSequentialGroup()
                        .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(parametresLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(j4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(supprimerJ4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(parametresLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, parametresLayout.createSequentialGroup()
                                        .addComponent(joueurs)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(parametresLayout.createSequentialGroup()
                                        .addComponent(j3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(supprimerJ3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(parametresLayout.createSequentialGroup()
                                        .addComponent(j1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(supprimerJ1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(parametresLayout.createSequentialGroup()
                                        .addComponent(j2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(supprimerJ2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 157, Short.MAX_VALUE))
                    .addGroup(parametresLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(j5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(supprimerJ5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(157, Short.MAX_VALUE))))
            .addGroup(parametresLayout.createSequentialGroup()
                .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(parametresLayout.createSequentialGroup()
                        .addComponent(parametre)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(parametresLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(retourParametre, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lancerLaPartie, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        parametresLayout.setVerticalGroup(
            parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, parametresLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(parametre)
                .addGap(18, 98, Short.MAX_VALUE)
                .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(parametresLayout.createSequentialGroup()
                        .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(parametresLayout.createSequentialGroup()
                                .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(parametresLayout.createSequentialGroup()
                                        .addComponent(pseudoLabel)
                                        .addGap(0, 114, Short.MAX_VALUE))
                                    .addGroup(parametresLayout.createSequentialGroup()
                                        .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(parametresLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                                .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(j1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(supprimerJ1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(35, 35, 35))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, parametresLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(ajouterJoueur, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(4, 4, 4)))
                                        .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(j2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(supprimerJ2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(1, 1, 1))
                            .addGroup(parametresLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel1)
                                .addGap(20, 20, 20)
                                .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cRouge, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cBleu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cVert, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cJaune, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cNoir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, parametresLayout.createSequentialGroup()
                                .addGap(0, 149, Short.MAX_VALUE)
                                .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(difficulterBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ajouterIA, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(parametresLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(supprimerJ3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(j3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(j4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(supprimerJ4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(supprimerJ5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(j5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0))))
                    .addComponent(joueurs))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                .addGroup(parametresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lancerLaPartie, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(retourParametre, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1104, Short.MAX_VALUE)
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(parties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(options, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(regles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(credits, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(menuPrincipale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(plateauJeu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(parametres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(parties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(options, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(regles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(credits, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(menuPrincipale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(plateauJeu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 29, Short.MAX_VALUE)
                    .addComponent(parametres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 30, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                
                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                
                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );

        pack();
    }// </editor-fold>                                                 
                          

    private void retourOptionsActionPerformed(java.awt.event.ActionEvent evt) {                                              
        menuPrincipale();
    }                                             

    private void jouerActionPerformed(java.awt.event.ActionEvent evt) {                                      
        parties.setVisible(true);
        menuPrincipale.setVisible(false);
        boutonSupDesactiver();
        background.affichageJouer();
    }                                     

    private void nouvellePartieActionPerformed(java.awt.event.ActionEvent evt) {                                               
        parties.setVisible(false);
        parametres.setVisible(true);
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
        String[] options = { "Oui", "Non" };
        int reply = quitterOptionPane.showOptionDialog(null, "Êtes-vous sûr.e de vouloir quitter le jeu ?", "Quitter le jeu ?" , quitterOptionPane.YES_NO_OPTION, quitterOptionPane.QUESTION_MESSAGE, null, options, null);
        if (reply == quitterOptionPane.YES_OPTION) {
            System.exit(0);
        }
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

    private void retourCreditsActionPerformed(java.awt.event.ActionEvent evt) {                                              
       menuPrincipale();
    }                                             

    private void supprimerJ5ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        ajouterJoueur.setEnabled(true);
        j5.setText("");
        c = j5.getForeground();
        activation();
        supprimerJ5.setVisible(false);
        players.remove(4);
    }                                           

    private void supprimerJ4ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        ajouterJoueur.setEnabled(true);
        j4.setText("");
        c = j4.getForeground();
        activation();
        players.remove(3);
        remplace4();
    }                                           

    private void supprimerJ3ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        ajouterJoueur.setEnabled(true);
        j3.setText("");
        c = j3.getForeground();
        activation();
        players.remove(2);
        remplace3();
    }                                           

    private void supprimerJ2ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        ajouterJoueur.setEnabled(true);
        j2.setText("");
        c = j2.getForeground();
        activation();
        players.remove(1);
        remplace2();
    }                                           

    private void supprimerJ1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        ajouterJoueur.setEnabled(true);
        j1.setText("");
        c = j1.getForeground();
        activation();
        players.remove(0);
        remplace1();
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
        parametres.setVisible(false);
        plateauJeu.setVisible(true);
        affListe();
    }                                              

    private void ajouterIAActionPerformed(java.awt.event.ActionEvent evt) {                                          
        switch(difficulterBox.getSelectedIndex()){
            case 0:
                setColor();
                ajouterIA("IA Facile");
                players.add(new Player("IA Facile",pType.IA_EASY,c));
                break;
            case 1:
                setColor();
                ajouterIA("IA Moyen");
                players.add(new Player("IA Moyen",pType.IA_MEDIUM,c));
                break;
            case 2:
                setColor();
                ajouterIA("Terminator");
                players.add(new Player("Terminator",pType.IA_HARD,c));
                break;
        }
       
    }                                         

    private void ajouterJoueurActionPerformed(java.awt.event.ActionEvent evt) {                                              
        ajouterHumain(textField);
        pseudo.setText("");
        ajouterJoueur.setEnabled(false);
        
    }                                             

    private void retourParametreActionPerformed(java.awt.event.ActionEvent evt) {                                                
        parametres.setVisible(false);
        parties.setVisible(true);
    }                                               


    // Variables declaration - do not modify                     
    private javax.swing.JScrollPane JeuScrollPane;
    private javax.swing.JButton ajouterIA;
    private javax.swing.JButton ajouterJoueur;
    public Background background;
    private javax.swing.JButton cBleu;
    private javax.swing.JButton cJaune;
    private javax.swing.JButton cNoir;
    private javax.swing.JButton cRouge;
    private javax.swing.JButton cVert;
    public javax.swing.JPanel credits;
    private javax.swing.JLabel creditsLabel;
    private javax.swing.JTextArea creditsTextArea;
    private javax.swing.JComboBox<String> difficulterBox;
    private javax.swing.JLabel j1;
    private javax.swing.JLabel j2;
    private javax.swing.JLabel j3;
    private javax.swing.JLabel j4;
    private javax.swing.JLabel j5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jeuEnReseaux;
    private javax.swing.JButton jouer;
    private javax.swing.JLabel joueurs;
    private javax.swing.JButton lancerLaPartie;
    private javax.swing.JButton menuCredits;
    private javax.swing.JButton menuOptions;
    public javax.swing.JPanel menuPrincipale;
    private javax.swing.JButton menuRegles;
    private javax.swing.JButton nouvellePartie;
    public javax.swing.JPanel options;
    private javax.swing.JLabel optionsLabel;
    private javax.swing.JLabel parametre;
    private javax.swing.JPanel parametres;
    public javax.swing.JPanel parties;
    private javax.swing.JLabel partiesLabel;
    public javax.swing.JPanel plateauJeu;
    private javax.swing.JTextField pseudo;
    private javax.swing.JLabel pseudoLabel;
    private javax.swing.JButton quitter;
    public javax.swing.JOptionPane quitterOptionPane;
    public javax.swing.JPanel regles;
    private javax.swing.JLabel reglesLabel;
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
