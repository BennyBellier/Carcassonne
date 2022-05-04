import global.Configuration;
import model.*;

public class Carcassonne {

  public static void main(String[] args) throws Exception {
    Configuration.instance().logger().finest("Lancement de l'application");
    GameSet gameSet = new GameSet();

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
    Pioche p = new Pioche();
    for (int j = 0; j < 72; j++) {
      Tile t = p.piocheTuile();
      if (t != null) {
        System.out.println("piece n° : " + (j + 1));
        System.out.println(t.toString() + "\n");
      }
    }
  }
}
