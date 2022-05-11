package model;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import global.Configuration;

public class Save {
  int nbPlayer, playerTurn, x, y;
  List<Player> players;
  Tile currentTile;
  Tile[][] set;
  Pioche p;
  List<Meeple> meeples;

  public Save(int playerTurn, int currentTileX, int currentTileY, Tile currentTile, Tile[][] set,
      Pioche p, List<Player> players, List<Meeple> meeples) {
    this.players = players;
    this.currentTile = currentTile;
    this.set = set;
    this.p = p;
    this.meeples = meeples;
    this.playerTurn = playerTurn;
    x = currentTileX;
    y = currentTileY;
  }

  public Byte[] toArray() {
    List<Byte> bytes = new ArrayList<>();

    bytes.addAll(Arrays.asList((byte) players.size(),(byte) playerTurn,(byte)  set.length,(byte) set[0].length,(byte) meeples.size(),(byte) p.size(),(byte) x,(byte) y));

    bytes.add(Byte.parseByte("\n"));

    // Joueurs
    for (int i = 0; i < players.size(); i++) {
      bytes.add((byte) i);
      bytes.addAll(players.get(i).toByteArray());
      bytes.add(Byte.parseByte("\n"));
    }

    // Tuile courante
    if (currentTile == null)
      bytes.add((byte)-1);
    else
      bytes.addAll(currentTile.toByteArray());

    bytes.add(Byte.parseByte("\n"));

    for (int i = 0; i < set.length; i++) {
      for (int j = 0; j < set[i].length; j++) {
        if (set[i][j] != null) {
          bytes.addAll(Arrays.asList((byte) i, (byte) j));
          bytes.addAll(set[i][j].toByteArray());
          bytes.add(Byte.parseByte("\n"));
        }
      }
    }

    // Pioche
    bytes.addAll(p.toByteArray());
    bytes.add(Byte.parseByte("\n"));

    // Meeples
    for (Meeple m : meeples) {
      bytes.addAll(m.toByteArray());
    }

    return (Byte[]) bytes.toArray();
  }

  public static String formatFileString(String s) {
    if (!s.contains(".dat"))
      s.concat(".dat");
    return Configuration.instance().getConfigFolderPath() + File.separator + "saves" + File.separator + s;
  }

  public static void save(String file, Save s) {
    try {
      FileOutputStream outputStream = new FileOutputStream(new File(formatFileString(file)));

      Byte[] Bytes = s.toArray();
      byte[] bytes = new byte[Bytes.length];

      for (int i = 0; i < Bytes.length; i++)
        bytes[i] = Bytes[i];

      outputStream.write(bytes, 0, bytes.length);

      outputStream.close();
    } catch (Exception e) {
      Configuration.instance().logger().severe("Erreur, impossible d'enregistrer la partie");
    }
  }

  public static Save load(String file) {
    return null;
  }
}
