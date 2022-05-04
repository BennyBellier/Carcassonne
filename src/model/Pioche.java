package model;
import java.util.*;


import global.Configuration;


public class Pioche {

    private LinkedList<Integer> pioche;

    public Pioche (int[][] tab){
        pioche = new LinkedList<Integer>();
        init_pioche(tab);
    }

    void init_pioche (int[][] tab){
        int piece;
        int allp = 0 ;
        while (allp != 71){
            Random r = new Random();
            int type_pièce = r.nextInt(24);
            if (tab[type_pièce][1]!=0){
                tab[type_pièce][1]=tab[type_pièce][1]-1;
                piece = type_pièce;
                pioche.add(allp,piece);
                allp++;
            }
        }
        Configuration.instance().logger().info("Initialisation de la pioche avec " + pioche.size() + " tuiles");
    }

	public int piochePiece (){
        if (pioche.isEmpty()) {
            Configuration.instance().logger().warning("Impossible de piocher, la pioche est vide");
            return -1;
        }
        int piece = pioche.get(0);
        pioche.remove(0);
        Configuration.instance().logger().info("Piochage de la tuile " + piece);

        return piece;
    }

}