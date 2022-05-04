import global.Configuration;
import model.*;

public class Carcassonne {

  public static void main(String[] args) throws Exception {
    Configuration.instance().logger().finest("Lancement de l'application");
    GameSet gameSet = new GameSet();
    int[][] tab = new int[24][2];

    Configuration.instance().logger().fine("Batterie de tests sur le plateau");
    System.out.println(gameSet.toString());
    gameSet.addTile(
      new Tile(
        TileType.TOWN,
        TileType.LAND,
        TileType.ROAD,
        TileType.ROAD,
        TileType.ROAD,
        false
      ),
      -1,
      0
    );
    gameSet.addTile(
      new Tile(
        TileType.CITY_OPEN,
        TileType.LAND,
        TileType.LAND,
        TileType.CITY_OPEN,
        TileType.CITY_OPEN,
        false
      ),
      0,
      -1
    );
    gameSet.addTile(
      new Tile(
        TileType.CITY_OPEN,
        TileType.LAND,
        TileType.LAND,
        TileType.CITY_OPEN,
        TileType.CITY_OPEN,
        false
      ),
      2,
      0
    );
    gameSet.addTile(
      new Tile(
        TileType.TOWN,
        TileType.LAND,
        TileType.LAND,
        TileType.LAND,
        TileType.LAND,
        true
      ),
      1,
      0
    );
    System.out.println(gameSet.toString());

    Configuration.instance().logger().fine("Batterie de tests sur la pioche");
    for (int i = 0; i != 24; i++) {
      switch (i) {
        case 0:
        case 19:
        case 22:
          tab[i][0] = i;
          tab[i][1] = 4;
          break;
        case 1:
        case 6:
        case 8:
        case 10:
        case 12:
        case 13:
          tab[i][0] = i;
          tab[i][1] = 2;
          break;
        case 2:
          tab[i][0] = 2;
          tab[i][1] = 0;
          break;
        case 3:
        case 7:
        case 9:
        case 14:
        case 16:
        case 17:
        case 18:
          tab[i][0] = i;
          tab[i][1] = 3;
          break;
        case 4:
        case 5:
        case 11:
        case 23:
          tab[i][0] = i;
          tab[i][1] = 1;
          break;
        case 15:
          tab[i][0] = i;
          tab[i][1] = 5;
          break;
        case 20:
          tab[i][0] = i;
          tab[i][1] = 8;
          break;
        case 21:
          tab[i][0] = i;
          tab[i][1] = 9;
          break;
      }
    }

    Pioche p = new Pioche(tab);
    for (int j = 0; j != 71; j++) {
      int piece = p.piochePiece();
      System.out.println("piece n° : " + (j + 1));
      System.out.println("piece " + piece);
    }
  }
}
