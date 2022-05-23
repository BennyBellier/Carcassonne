package controller;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import model.GameSet;
import model.Tile;

public abstract class IA {

  public abstract int[] placeTile(GameSet gs, Tile currentTile);

  public abstract String placeMeeple(List<String> meeplesPositions);

  /*
   * encodeCoords : Prends des coordonnées sous la forme d'un HashMap et renvoi un
   * ArrayList avec ces coordonnées
   * Entrées : a le HashMap
   * Sorties : Les coordonnées sous forme d'un ArrayList. Chaque pair de
   * clé-valeur est stocké dans l'ArrayList sous la forme : key * 10000 + valeur
   */
  ArrayList<Integer> encodeCoords(Map<Integer, ArrayList<Integer>> a) {
    ArrayList<Integer> coords = new ArrayList<>(), keyValue;
    int key;
    // Iteration de tous les elements du Map avec la methode entrySet() de la classe
    // Map
    for (Map.Entry<Integer, ArrayList<Integer>> set : a.entrySet()) {
      key = set.getKey();
      keyValue = set.getValue();
      for (Integer Key : keyValue) {
        coords.add(key * 10000 + Key);
      }
    }

    return coords;
  }

  /*
   * calculateRotations : Prend une tuile et renvoie les rotations avec lesquelles
   * elle peut être placé à la position pos
   * Entrées : tab le tableau du jeu pendant ce tour, t la tuile à placer, pos la
   * position oùu on va poser la tuile sous la forme pos = (i * 10000 + j)
   * Sorties: Un ArrayList qui contient les rotations valides de la tuile pour
   * cette position
   * Remarque : Le switch vérifie toutes les rotations en fonction de cases
   * adjacentes à cette position.
   */
}
