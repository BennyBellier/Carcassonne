import global.Configuration;
import model.*;
public class Carcassonne {
  public static void main(String[] args) {
    int[][] tab = new int[24][2];

    for (int i = 0 ; i!=24;i++){
        switch(i){
        
          case 0,19,22:
                tab[i][0]=i;
                tab[i][1]=4;
                break;

            case 1,6,8,10,12,13:
                tab[i][0]=i;
                tab[i][1]=2;
                break;

            case 2:
                tab[i][0]=2;
                tab[i][1]=0;
                break;

            case 3,7,9,14,16,17,18:
                tab[i][0]=i;
                tab[i][1]=3;
                break;

            case 4,5,11,23:
                tab[i][0]=i;
                tab[i][1]=1;
                break;

            case 15:
                tab[i][0]=i;
                tab[i][1]=5;
                break;
        
            case 20:
                tab[i][0]=i;
                tab[i][1]=8;
                break;

            case 21:
                tab[i][0]=i;
                tab[i][1]=9;
                break;
        }
    
    }

    Pioche p = new Pioche(tab);
    for(int j = 0 ; j!=71;j++){
      int piece = p.piochePiece();
      System.out.println("piece n° : " + (j + 1));
      System.out.println("piece " + piece);
      }
  }
}
