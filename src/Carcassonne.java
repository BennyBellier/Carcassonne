import global.Configuration;
import model.*;
import model.Tiles.Tile;
import model.Tiles.TileType;

public class Carcassonne {

  public static void main(String[] args) throws Exception {
    Configuration.instance().logger().finest("Lancement de l'application");
    GameSet gameSet = new GameSet();
    Pioche p = new Pioche();

    Configuration.instance().logger().fine("Batterie de tests sur le plateau");
    System.out.println(gameSet.toString());
    gameSet.addTile(
      new Tile(
        TileType.TOWN,
        TileType.FIELD,
        TileType.ROAD,
        TileType.ROAD,
        TileType.ROAD,
        false,
        false
      ),
      -1,
      0
    );
    gameSet.addTile(
      new Tile(
        TileType.CITY,
        TileType.FIELD,
        TileType.FIELD,
        TileType.CITY,
        TileType.CITY,
        false,
        true
      ),
      0,
      -1
    );
    gameSet.addTile(
      new Tile(
        TileType.CITY,
        TileType.FIELD,
        TileType.FIELD,
        TileType.CITY,
        TileType.CITY,
        false,
        true
      ),
      2,
      0
    );
    gameSet.addTile(
      new Tile(
        TileType.TOWN,
        TileType.FIELD,
        TileType.FIELD,
        TileType.FIELD,
        TileType.FIELD,
        true,
        false
      ),
      1,
      0
    );
    gameSet.addTile(
      new Tile(
        TileType.FIELD,
        TileType.FIELD,
        TileType.CITY,
        TileType.FIELD,
        TileType.FIELD,
        true,
        true
      ),
      1,
      -1
    );
    System.out.println(gameSet.toString());

    Configuration.instance().logger().fine("Batterie de tests sur la pioche");

    for (int j = 0; j < 72; j++) {
      Tile t = p.piocheTuile();
      if (t != null) {
        System.out.println("piece n° : " + (j + 1));
        System.out.println("+-----+");
        for (int i = 0; i < 3; i++) {
          System.out.println("|" + t.toStringArray()[i] + "|");
        }
        System.out.println("+-----+");
      }
    }
  }
}
