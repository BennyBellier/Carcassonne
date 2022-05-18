/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import global.Configuration;

/**
 *
 * @author benny
 */
public class Frame extends javax.swing.JFrame {

  /**
   * Creates new form Frame
   */
  public Frame() {
    initComponents();
    choixDeuxJoueurs.setVisible(false);
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated
  // Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    background = new Background();
    menuPrincipale = new javax.swing.JPanel();
    continuer = new javax.swing.JButton();
    charger = new javax.swing.JButton();
    humainMachine = new javax.swing.JButton();
    humainHumain = new javax.swing.JButton();
    classement = new javax.swing.JButton();
    version = new javax.swing.JLabel();
    choixDeuxJoueurs = new javax.swing.JPanel();
    retour = new javax.swing.JButton();
    local = new javax.swing.JButton();
    reseau = new javax.swing.JButton();
    modeDeJeu = new javax.swing.JLabel();
    regles = new javax.swing.JButton();
    credits = new javax.swing.JButton();
    options = new javax.swing.JButton();
    quitter = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    menuPrincipale.setOpaque(false);

    continuer.setText("Continuer");

    charger.setText("Charger");

    humainMachine.setText("1 VS IA");

    humainHumain.setText("1 VS 1");

    classement.setText("Classement");

    version.setText(Configuration.instance().lis("Version"));

    try {
      setIconImage(ImageIO.read(new File("assets/Images/logo.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }

    javax.swing.GroupLayout menuPrincipaleLayout = new javax.swing.GroupLayout(menuPrincipale);
    menuPrincipale.setLayout(menuPrincipaleLayout);
    menuPrincipaleLayout.setHorizontalGroup(
        menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPrincipaleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuPrincipaleLayout.createSequentialGroup()
                        .addGroup(
                            menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(humainMachine, javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(humainHumain, javax.swing.GroupLayout.PREFERRED_SIZE, 139,
                                    javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(menuPrincipaleLayout.createSequentialGroup()
                        .addGroup(menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(continuer, javax.swing.GroupLayout.PREFERRED_SIZE, 139,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(charger, javax.swing.GroupLayout.PREFERRED_SIZE, 139,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(204, 684, Short.MAX_VALUE))))
            .addGroup(menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(menuPrincipaleLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                            menuPrincipaleLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(classement, javax.swing.GroupLayout.PREFERRED_SIZE, 167,
                                    javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(menuPrincipaleLayout.createSequentialGroup()
                            .addComponent(version)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())));
    menuPrincipaleLayout.setVerticalGroup(
        menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPrincipaleLayout.createSequentialGroup()
                .addContainerGap(400, Short.MAX_VALUE)
                .addComponent(continuer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(charger)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(humainMachine)
                .addGap(13, 13, 13)
                .addComponent(humainHumain)
                .addGap(38, 38, 38))
            .addGroup(menuPrincipaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(menuPrincipaleLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(classement)
                    .addGap(47, 47, 47)
                    .addComponent(version)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));

    choixDeuxJoueurs.setEnabled(false);
    choixDeuxJoueurs.setOpaque(false);

    retour.setText("Retour");
    retour.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        retourActionPerformed(evt);
      }
    });

    local.setText("Local");

    reseau.setText("Réseau");

    modeDeJeu.setText("Mode de Jeu");

    javax.swing.GroupLayout choixDeuxJoueursLayout = new javax.swing.GroupLayout(choixDeuxJoueurs);
    choixDeuxJoueurs.setLayout(choixDeuxJoueursLayout);
    choixDeuxJoueursLayout.setHorizontalGroup(
        choixDeuxJoueursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(choixDeuxJoueursLayout.createSequentialGroup()
                .addGroup(choixDeuxJoueursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(choixDeuxJoueursLayout.createSequentialGroup()
                        .addGroup(choixDeuxJoueursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(choixDeuxJoueursLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(modeDeJeu))
                            .addGroup(choixDeuxJoueursLayout.createSequentialGroup()
                                .addGap(292, 292, 292)
                                .addGroup(choixDeuxJoueursLayout
                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(reseau, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(local, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(318, 318, 318))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, choixDeuxJoueursLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(retour, javax.swing.GroupLayout.PREFERRED_SIZE, 83,
                            javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap()));
    choixDeuxJoueursLayout.setVerticalGroup(
        choixDeuxJoueursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, choixDeuxJoueursLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(modeDeJeu)
                .addGap(99, 99, 99)
                .addComponent(local, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
                .addGap(54, 54, 54)
                .addComponent(reseau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
                .addGap(114, 114, 114)
                .addComponent(retour)
                .addContainerGap()));

    regles.setText("Règles");
    regles.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        reglesActionPerformed(evt);
      }
    });

    credits.setText("Credits");
    credits.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        creditsActionPerformed(evt);
      }
    });

    options.setText("Options");

    quitter.setText("Quitter");

    javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
    background.setLayout(backgroundLayout);
    backgroundLayout.setHorizontalGroup(
        backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addContainerGap(757, Short.MAX_VALUE)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(quitter, javax.swing.GroupLayout.Alignment.TRAILING,
                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(options, javax.swing.GroupLayout.Alignment.TRAILING,
                            javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(credits, javax.swing.GroupLayout.Alignment.TRAILING,
                            javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(regles, javax.swing.GroupLayout.Alignment.TRAILING,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(choixDeuxJoueurs, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(menuPrincipale, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())));
    backgroundLayout.setVerticalGroup(
        backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addContainerGap(445, Short.MAX_VALUE)
                .addComponent(regles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(credits)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(options)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(quitter)
                .addContainerGap())
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(choixDeuxJoueurs, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(backgroundLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(menuPrincipale, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                Short.MAX_VALUE));
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                Short.MAX_VALUE));

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void creditsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_creditsActionPerformed
    // TODO add your handling code here:
  }// GEN-LAST:event_creditsActionPerformed

  private void reglesActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_reglesActionPerformed
    // TODO add your handling code here:
  }// GEN-LAST:event_reglesActionPerformed

  private void retourActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_retourActionPerformed
    // TODO add your handling code here:
  }// GEN-LAST:event_retourActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
    // (optional) ">
    /*
     * If Nimbus (introduced in Java SE 6) is not available, stay with the default
     * look and feel.
     * For details see
     * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    // </editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new Frame().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel Background;
  private javax.swing.JButton charger;
  private javax.swing.JPanel choixDeuxJoueurs;
  private javax.swing.JButton classement;
  private javax.swing.JButton continuer;
  private javax.swing.JButton credits;
  private javax.swing.JButton humainHumain;
  private javax.swing.JButton humainMachine;
  private Background background;
  private javax.swing.JButton local;
  private javax.swing.JPanel menuPrincipale;
  private javax.swing.JLabel modeDeJeu;
  private javax.swing.JButton options;
  private javax.swing.JButton quitter;
  private javax.swing.JButton regles;
  private javax.swing.JButton reseau;
  private javax.swing.JButton retour;
  private javax.swing.JLabel version;
  // End of variables declaration//GEN-END:variables
}
