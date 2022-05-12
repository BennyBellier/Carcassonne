package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 *
 * @author ludov
 */
public class Images extends JComponent {
    
    Image img , img2;
    ArrayList<InputStream> images = new ArrayList<>();
    
    public Images() {
        InputStream in = charge("Bois.png");
        InputStream in2 = charge("background.png");
        for ( int i = 0 ; i<24 ; i++){
            InputStream in3 = charge(i+".png");
            images.add(in3);
        }
        
        
        try {
            img = ImageIO.read(in);
            img2 = ImageIO.read(in2);
        } catch (IOException e) {
                System.exit(1);
        }
        }

    public static InputStream charge(String nom) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(nom);
    }

    public Image getImage() {
        return img;
    }

    public Image getImage2() {
        return img2;
    }

    public ArrayList<InputStream> getImagesList(){
        return images;
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D drawable = (Graphics2D) g;
        int width = getSize().width;
        int height = getSize().height;
        drawable.clearRect(0, 0, width, height);
        drawable.drawImage(img2 , 0 , 0 , width , height , null);

    }
    
}
