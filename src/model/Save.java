package model;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import global.Configuration;

public class Save {
  int nbPlayer, playerTurn;
  List<Player> players;
  CurrentTile currentTile;
  CurrentMeeple currentMeeple;
  Tile[][] set;
  Pioche p;
  List<Meeple> meeples;

  public Save(int playerTurn, CurrentTile currentTile, CurrentMeeple currentMeeple, Tile[][] set, Pioche p,
      List<Player> players, List<Meeple> meeples) {
    this.players = players;
    this.currentTile = currentTile;
    this.currentMeeple = currentMeeple;
    this.set = set;
    this.p = p;
    this.meeples = meeples;
    this.playerTurn = playerTurn;
    nbPlayer = players.size();
  }

  public byte[] toArray() {
    List<Byte> bytes = new ArrayList<>();

    // décalage
    for (int i = 0; i < 16; i++) {
      bytes.add((byte) 0);
    }

    bytes.addAll(Arrays.asList((byte) players.size(), (byte) playerTurn, (byte) set.length, (byte) set[0].length, (byte) meeples.size(), (byte) p.size()));

    // Joueurs (6 octets par joueur)
    for (int i = 0; i < players.size(); i++) {
      bytes.addAll(players.get(i).toByteArray());
    }

    // Tuile courante (12 octets)
    if (currentTile == null)
      bytes.addAll(
          Arrays.asList((byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1,
              (byte) -1, (byte) -1, (byte) -1, (byte) -1));
    else
      bytes.addAll(currentTile.toByteArray());

    // Meeple courant (3 octets)
    if (currentMeeple == null)
      bytes.addAll(Arrays.asList((byte) -1, (byte) -1, (byte) -1));
    else
      bytes.addAll(currentMeeple.toByteArray());

    // Plateau (9 octets 1 tuile)
    for (int i = 0; i < set.length; i++) {
      for (int j = 0; j < set[i].length; j++) {
        if (set[i][j] != null) {
          bytes.addAll(set[i][j].toByteArray());
        } else {
          bytes.addAll(
              Arrays.asList((byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1, (byte) -1));
        }
      }
    }

    // Pioche (9 octets 1 tuile)
    bytes.addAll(p.toByteArray());

    // Meeples sur le palteau (4 octets 1 meeple)
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
      int nbPlayer, playerTurn, setLength, set0Length, nbMeeplesOnSet, piocheSize;

      int size = inputStream.available();

      Tile[][] set;
      Pioche p;
      List<Player> players = new ArrayList<>();
      List<Meeple> meeples = new ArrayList<>();
      CurrentTile currentTile;
      CurrentMeeple currentMeeple;

      inputStream.skip(16);

      System.out.println(size - inputStream.available());

      byte[] bytes = new byte[6];
      inputStream.read(bytes);
      nbPlayer = bytes[0];
      playerTurn = bytes[1];
      setLength = bytes[2];
      set0Length = bytes[3];
      nbMeeplesOnSet = bytes[4];
      piocheSize = bytes[5];

      System.out.println(size - inputStream.available());

      // lecture des paramètre des joueurs
      byte[][] playerByte = new byte[nbPlayer][9];
      for (int i = 0; i < nbPlayer; i++) {
        inputStream.read(playerByte[i]);
      }

      bytes = new byte[12];

      System.out.println(size - inputStream.available());

      // lecture de la tuile courante
      inputStream.read(bytes);
      if (bytes[0] == -1)
        currentTile = null;
      else
        currentTile = new CurrentTile(bytes);

      bytes = new byte[3];
      System.out.println(size - inputStream.available());

      inputStream.read(bytes);
      if (bytes[0] == -1)
        currentMeeple = null;
      else
        currentMeeple = new CurrentMeeple(bytes);

      bytes = new byte[9];
      System.out.println(size - inputStream.available());

      // lecture du plateau
      set = new Tile[setLength][set0Length];
      for (int i = 0; i < set.length; i++) {
        for (int j = 0; j < set[i].length; j++) {
          inputStream.read(bytes);
          if (bytes[0] != -1) {
            set[i][j] = new Tile(bytes);
          }
        }
      }
      System.out.println(size - inputStream.available());

      // lecture de la pioche
      byte[][] pBytes = new byte[piocheSize][9];
      for (int i = 0; i < piocheSize; i++) {
        inputStream.read(pBytes[i]);
      }
      p = new Pioche(pBytes);
      System.out.println(size - inputStream.available());

      // lecture des meeples sur le plateau
      bytes = new byte[4];
      for (int i = 0; i < nbMeeplesOnSet; i++) {
        inputStream.read(bytes);
        meeples.add(new Meeple(bytes));
      }
      System.out.println(size - inputStream.available());

      // lecture des pseudo
      bytes = new byte[1];
      char[] tmp = new char[1];
      for (int i = 0; i < nbPlayer; i++) {
        String pseudo = "";
        inputStream.read(bytes);
        while (inputStream.available() > 0 && (char) bytes[0] != '\n') {
          pseudo = pseudo.concat(new String(tmp));
          inputStream.read(bytes);
        }
        players.add(i, new Player(playerByte[i], pseudo));
        inputStream.skip(1);
      }
      System.out.println(size - inputStream.available());

      return new Save(playerTurn, currentTile, currentMeeple, set, p, players, meeples);
    } catch (

    Exception e) {
      Configuration.instance().logger().severe("Fichier de sauvegarde corrompu");
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(String.valueOf(playerTurn) + " : \n");

    for (Player p : players) {
      str.append("\t" + p.toString() + "\n");
    }

    if (currentTile != null)
      str.append(currentTile.toString() + "\n");

    if (currentMeeple != null)
      str.append(currentMeeple.toString() + "\n");

    for (Meeple meeple : meeples) {
      str.append("\t" + meeple.toString() + "\n");
    }

    str.append(new GameSet(set).toString());

    return str.toString();
  }
}
