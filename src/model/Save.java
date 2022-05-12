package model;

import java.io.FileInputStream;
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

  public byte[] toArray() {
    List<Byte> bytes = new ArrayList<>();

    // décalage
    for (int i = 0; i < 16; i++) {
      bytes.add((byte) 0);
    }

    bytes.addAll(Arrays.asList((byte) players.size(), (byte) playerTurn, (byte) set.length, (byte) set[0].length,
        (byte) meeples.size(), (byte) p.size(), (byte) x, (byte) y));

    // Joueurs (7 octets par joueur + 1 octet pour sa position dans la liste)
    for (int i = 0; i < players.size(); i++) {
      bytes.add((byte) i);
      bytes.addAll(players.get(i).toByteArray());
    }

    // Tuile courante (8 octets)
    if (currentTile == null)
      bytes.addAll(
          Arrays.asList((byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1));
    else
      bytes.addAll(currentTile.toByteArray());

    // Plateau (8 octets 1 tuile)
    for (int i = 0; i < set.length; i++) {
      for (int j = 0; j < set[i].length; j++) {
        if (set[i][j] != null) {
          bytes.addAll(Arrays.asList((byte) 0, (byte) 0, (byte) i, (byte) j));
          bytes.addAll(set[i][j].toByteArray());
        } else {
          bytes.addAll(Arrays.asList((byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0));
        }
      }
    }

    // Pioche (8 octets 1 tuile)
    bytes.addAll(p.toByteArray());

    for (int i = 0; i < 16 - (p.size() % 16); i++) {
      bytes.add((byte) 0);
    }

    // Meeples (4 octets 1 meeple)
    for (Meeple m : meeples) {
      bytes.addAll(m.toByteArray());
    }

    // pseudos des joueurs
    for (int i = 0; i < players.size(); i++) {
      for (int j = 0; j < players.get(i).pseudo().length(); j++) {
        bytes.add((byte) players.get(i).pseudo().charAt(j));
      }
      // séparateur de pseudo
      bytes.add((byte) '\n');
    }

    byte[] res = new byte[bytes.size()];
    for (int i = 0; i < bytes.size(); i++)
      res[i] = bytes.get(i);

    return res;
  }

  public static Save fromFile(FileInputStream inputStream) {
    try {
      int nbPlayer, playerTurn, setLength, set0Length, currentTileX, currentTileY, nbMeeplesOnSet, piocheSize;

      Tile[][] set;
      Pioche p;
      List<Player> players = new ArrayList<>();
      List<Meeple> meeples = new ArrayList<>();
      Tile currentTile;

      byte[] bytes = new byte[8];

      inputStream.read(bytes, 16, 8);
      nbPlayer = bytes[0];
      playerTurn = bytes[1];
      setLength = bytes[2];
      set0Length = bytes[3];
      nbMeeplesOnSet = bytes[4];
      piocheSize = bytes[5];
      currentTileX = bytes[6];
      currentTileY = bytes[7];

      // lecture des paramètre des joueur
      byte[][] playerByte = new byte[nbPlayer][8];
      for (int i = 0; i < nbPlayer; i++) {
        inputStream.read(playerByte[i], 0, 8);
      }

      // lecture de la tuile courante
      inputStream.read(bytes, 0, 8);
      if (bytes[0] == -1)
        currentTile = null;
      else
        currentTile = new Tile(bytes);

      // lecture du plateau
      set = new Tile[setLength][set0Length];
      for (int i = 0; i < set.length; i++) {
        for (int j = 0; j < set[i].length; j++) {
          inputStream.read(bytes, 0, 8);
          if (bytes[0] == -1)
            set[i][j] = null;
          else
            set[i][j] = new Tile(bytes);
        }
      }

      // lecture de la pioche
      byte[][] pBytes = new byte[piocheSize][8];
      for (int i = 0; i < piocheSize; i++) {
        inputStream.read(pBytes[i], 0, 8);
      }
      p = new Pioche(pBytes);

      // lecture des meeples sur le plateau
      bytes = new byte[4];
      for (int i = 0; i < nbMeeplesOnSet; i++) {
        inputStream.read(bytes, 0, 4);
        meeples.add(new Meeple(bytes));
      }

      // lecture des pseudo
      char tmp;
      for (int i = 0; i < nbPlayer; i++) {
        String pseudo = "";
        tmp = (char) inputStream.read();
        while (inputStream.available() != 0 && tmp != '\n') {
          pseudo = pseudo.concat(Character.toString(tmp));
          tmp = (char) inputStream.read();
        }
        players.add(i, new Player(playerByte[i], pseudo));
        inputStream.skip(1);
      }

      return new Save(playerTurn, currentTileX, currentTileY, currentTile, set, p, players, meeples);
    } catch (Exception e) {
      Configuration.instance().logger().severe("Fichier de sauvegarde corrompu");
    }
    return null;
  }
}
