/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author ludov
 */
public class Controleur implements ActionListener {
    
    Frames f;
    
    public Controleur(){
       
    }
    
    public void getInterface(Frames f){
        this.f =f ;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "continuer":
                break;  
            case "charger":
                break;
            case "classement":
                
                break;
            case "credits":
                f.menuPanel.setVisible(false);
                f.creditsPanel.setVisible(true);
                break;
            case "options":
                f.menuPanel.setVisible(false);
                f.optionsPanel.setVisible(true);
                break;
            case "quitter":
                String[] options = {"Oui", "Non"};
                int reply = f.quitterOptionPane.showOptionDialog(null, "Êtes-vous sûr.e de vouloir quitter le jeu ?", "Quitter le jeu ?",
                f.quitterOptionPane.YES_NO_OPTION, f.quitterOptionPane.QUESTION_MESSAGE, null, options, null);
                if (reply == f.quitterOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                break;
            case "vsHumain":
                break;
            case "vsBot":
                break;
            case "regles":
                f.menuPanel.setVisible(false);
                f.reglesPanel.setVisible(true);
                break;
            case "rOptions":
                f.menuPanel.setVisible(true);
                f.optionsPanel.setVisible(false);
                break;
            case "rRegles":
                f.menuPanel.setVisible(true);
                f.reglesPanel.setVisible(false);
                break;
            case "rCredits":
                f.menuPanel.setVisible(true);
                f.creditsPanel.setVisible(false);
                break;
            default:
                System.out.println("Commande invalide");
                break;
        
        }
    }
    
}
