package global;

import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

import model.GameSet;
import model.Tile;

public class IA
{
    //currentConfig : Contient le plateau courant à chaque tour
     Tile [][] currentConfig;

    //stack: Le nombre des tuiles restantes pas encore jouées
    int stack;

    /*
        validPositions : L'ensemble des positions où la tuile à jouer peut être placé
        On stocke chaque coordonnée (i, j) en le mappant à un seul entier avec la fonction: e = (i * 1000) + j
        Pour récupérer i : i = e / 10000
        Pour récupérer j : j = e % 10000
    */
    ArrayList <Integer> validPositions;

    //meeples: Nombre de Meeples pas placés
    int meeples;

    //meeplePositions: Positions où notre meeples sont placés dans le plateau
    ArrayList<Integer> placedMeeples;

    IA()
    {
        currentConfig = null;
        stack = 0;
        meeples = 7;
        placedMeeples = new ArrayList<>();
        validPositions = new ArrayList <>();
    }


    /*

        encodeCoords : Prends des coordonnées sous la forme d'un HashMap et renvoi un ArrayList avec ces coordonnées
        Entrées : a le HashMap
        Sorties : Les coordonnées sous forme d'un ArrayList. Chaque pair de clé-valeur est stocké dans l'ArrayList sous la forme : key * 10000 + valeur
    */
    ArrayList<Integer> encodeCoords (Map<Integer, ArrayList<Integer>> a)
    {
        ArrayList<Integer> coords = new ArrayList<>(), keyValue;
        int key;
        //Iteration de tous les elements du Map avec la methode entrySet() de la classe Map
        for (Map.Entry<Integer,ArrayList<Integer>> set : a.entrySet())
        {
            key = set.getKey();
            keyValue = set.getValue();
            for (Integer Key : keyValue) {
                coords.add(key * 10000 + Key);
            }
        }

        return coords;
    }

    /*
        calculateRotations : Prend une tuile et renvoie les rotations avec lesquelles elle peut être placé à la position pos
        Entrées : tab le tableau du jeu pendant ce tour, t la tuile à placer, pos la position oùu on va poser la tuile sous la forme pos = (i * 10000 + j)
        Sorties: Un ArrayList qui contient les rotations valides de la tuile pour cette position
        Remarque : Le switch vérifie toutes les rotations en fonction de cases adjacentes à cette position.
    */

    ArrayList<Integer> calculateRotations(Tile [][] tab, Tile t, int pos)
    {
        /*
            adjacents[0] == true -> Il existe une tuile en dessus de (pos / 10000, pos % 10000)
            adjacents[1] == true -> Il existe une tuile en dessous de (pos / 10000, pos % 10000)
            adjacents[2] == true -> Il existe une tuile à droite de (pos / 10000, pos % 10000)
            adjacents[3] == true -> Il existe une tuile à gauche de (pos / 10000, pos % 10000)
        */
        boolean [] voisins = new boolean[4];
        boolean [] rotations = new boolean[4];
        int nbVoisins = 0;
        int i = pos / 10000;
        int j = pos % 10000;
        ArrayList<Integer> valid = new ArrayList<>();

        if (i > 0 && tab[i - 1][j] != null)
        {
            voisins[0] = true;
            nbVoisins++;
        }
        if (j < tab[i].length - 1 && tab[i][j + 1] != null)
        {
            voisins[2] = true;
            nbVoisins++;
        }
        if (i < tab.length - 1 && tab[i + 1][j] != null)
        {
            voisins[1] = true;
            nbVoisins++;
        }
        if (j > 0 && tab[i][j - 1] != null)
        {
            voisins[3] = true;
            nbVoisins++;
        }

        switch(nbVoisins)
        {
            case 1:
                if (voisins[0])
                {
                    if (t.canConnect(tab[i -1][j], "n"))
                    {
                        rotations[0] = true;
                    }
                    else if (t.canConnect(tab[i -1][j], "w"))
                    {
                        rotations[1] = true;
                    }
                    else if (t.canConnect(tab[i -1][j], "s"))
                    {
                        rotations[2] = true;
                    }
                    else if (t.canConnect(tab[i -1][j], "e"))
                    {
                        rotations[3] = true;
                    }
                }
                else if (voisins[1])
                {
                    if (t.canConnect(tab[i + 1][j], "s"))
                    {
                        rotations[0] = true;
                    }
                    else if (t.canConnect(tab[i + 1][j], "e"))
                    {
                        rotations[1] = true;
                    }
                    else if (t.canConnect(tab[i  + 1][j], "n"))
                    {
                        rotations[2] = true;
                    }
                    else if (t.canConnect(tab[i  + 1][j], "w"))
                    {
                        rotations[3] = true;
                    }
                }
                else if (voisins[2])
                {
                    if (t.canConnect(tab[i][j + 1], "e"))
                    {
                        rotations[0] = true;
                    }
                    else if (t.canConnect(tab[i][j + 1], "n"))
                    {
                        rotations[1] = true;
                    }
                    else if (t.canConnect(tab[i][j + 1], "w"))
                    {
                        rotations[2] = true;
                    }
                    else if (t.canConnect(tab[i][j + 1], "s"))
                    {
                        rotations[3] = true;
                    }
                }
                else {
                    if (t.canConnect(tab[i][j - 1], "w"))
                    {
                        rotations[0] = true;
                    }
                    else if (t.canConnect(tab[i][j - 1], "s"))
                    {
                        rotations[1] = true;
                    }
                    else if (t.canConnect(tab[i][j - 1], "e"))
                    {
                        rotations[2] = true;
                    }
                    else if (t.canConnect(tab[i][j - 1], "n"))
                    {
                        rotations[3] = true;
                    }
                }
                break;
            case 2:
                if (voisins[0])
                {
                    if (voisins[1])
                    {
                        if ((t.canConnect(tab[i - 1][j], "n") && t.canConnect(tab[i + 1][j], "s")))
                        {
                            rotations[0] = true;
                        }
                        else if ((t.canConnect(tab[i - 1][j], "w") && t.canConnect(tab[i + 1][j], "e")))
                        {
                            rotations[1] = true;
                        }
                        else if ((t.canConnect(tab[i - 1][j], "s") && t.canConnect(tab[i + 1][j], "n")))
                        {
                            rotations[2] = true;
                        }
                        else if ((t.canConnect(tab[i - 1][j], "e") && t.canConnect(tab[i + 1][j], "w")))
                        {
                            rotations[3] = true;
                        }
                    }
                    if (voisins[2])
                    {
                        if ((t.canConnect(tab[i - 1][j], "n") && t.canConnect(tab[i][j + 1], "e")))
                        {
                            rotations[0] = true;
                        }
                        else if ((t.canConnect(tab[i - 1][j], "w") && t.canConnect(tab[i][j + 1], "n")))
                        {
                            rotations[1] = true;
                        }
                        else if ((t.canConnect(tab[i - 1][j], "s") && t.canConnect(tab[i][j + 1], "w")))
                        {
                            rotations[2] = true;
                        }
                        else if ((t.canConnect(tab[i - 1][j], "e") && t.canConnect(tab[i][j + 1], "s")))
                        {
                            rotations[3] = true;
                        }
                    }
                    if (voisins[3])
                    {
                        if ((t.canConnect(tab[i - 1][j], "n") && t.canConnect(tab[i][j - 1], "w")))
                        {
                            rotations[0] = true;
                        }
                        else if ((t.canConnect(tab[i - 1][j], "w") && t.canConnect(tab[i][j - 1], "s")))
                        {
                            rotations[1] = true;
                        }
                        else if ((t.canConnect(tab[i - 1][j], "s") && t.canConnect(tab[i][j - 1], "e")))
                        {
                            rotations[2] = true;
                        }
                        else if ((t.canConnect(tab[i - 1][j], "e") && t.canConnect(tab[i][j - 1], "n")))
                        {
                            rotations[3] = true;
                        }
                    }
                }
                else if (voisins[1] && voisins[2])
                {
                    if ((t.canConnect(tab[i + 1][j], "s") && t.canConnect(tab[i][j + 1], "e")))
                    {
                        rotations[0] = true;
                    }
                    else if ((t.canConnect(tab[i + 1][j], "e") && t.canConnect(tab[i][j + 1], "n")))
                    {
                        rotations[1] = true;
                    }
                    else if ((t.canConnect(tab[i + 1][j], "n") && t.canConnect(tab[i][j + 1], "w")))
                    {
                        rotations[2] = true;
                    }
                    else if ((t.canConnect(tab[i + 1][j], "w") && t.canConnect(tab[i][j + 1], "s")))
                    {
                        rotations[3] = true;
                    }
                }
                else if (voisins[2])
                {
                    if ((t.canConnect(tab[i][j - 1], "w") && t.canConnect(tab[i][j + 1], "e")))
                    {
                        rotations[0] = true;
                    }
                    else if ((t.canConnect(tab[i][j - 1], "s") && t.canConnect(tab[i][j + 1], "n")))
                    {
                        rotations[1] = true;
                    }
                    else if ((t.canConnect(tab[i][j - 1], "e") && t.canConnect(tab[i][j + 1], "w")))
                    {
                        rotations[2] = true;
                    }
                    else if ((t.canConnect(tab[i][j - 1], "n") && t.canConnect(tab[i][j + 1], "s")))
                    {
                        rotations[3] = true;
                    }
                }
                else
                {
                    if ((t.canConnect(tab[i + 1][j], "s") && t.canConnect(tab[i][j - 1], "w")))
                    {
                        rotations[0] = true;
                    }
                    else if ((t.canConnect(tab[i + 1][j], "e") && t.canConnect(tab[i][j - 1], "s")))
                    {
                        rotations[1] = true;
                    }
                    else if ((t.canConnect(tab[i + 1][j], "n") && t.canConnect(tab[i][j - 1], "e")))
                    {
                        rotations[2] = true;
                    }
                    else if ((t.canConnect(tab[i + 1][j], "w") && t.canConnect(tab[i][j - 1], "n")))
                    {
                        rotations[3] = true;
                    }
                }
                break;
            case 3:
                if (!voisins[3])
                {
                    if ((t.canConnect(tab[i - 1][j], "n") && t.canConnect(tab[i + 1][j], "s") && t.canConnect(tab[i][j + 1], "e")))
                    {
                        rotations[0] = true;
                    }
                    else if ((t.canConnect(tab[i - 1][j], "w") && t.canConnect(tab[i + 1][j], "e") && t.canConnect(tab[i][j + 1], "n")))
                    {
                        rotations[1] = true;
                    }
                    else if ((t.canConnect(tab[i - 1][j], "s") && t.canConnect(tab[i + 1][j], "n") && t.canConnect(tab[i][j + 1], "w")))
                    {
                        rotations[2] = true;
                    }
                    else if ((t.canConnect(tab[i - 1][j], "e") && t.canConnect(tab[i + 1][j], "w") && t.canConnect(tab[i][j + 1], "s")))
                    {
                        rotations[3] = true;
                    }
                }
                else if (!voisins[1])
                {
                    if ((t.canConnect(tab[i - 1][j], "n") && t.canConnect(tab[i][j - 1], "w") && t.canConnect(tab[i][j + 1], "e")))
                    {
                        rotations[0] = true;
                    }
                    else if ((t.canConnect(tab[i - 1][j], "w") && t.canConnect(tab[i][j - 1], "s") && t.canConnect(tab[i][j + 1], "n")))
                    {
                        rotations[1] = true;
                    }
                    else if ((t.canConnect(tab[i - 1][j], "s") && t.canConnect(tab[i][j - 1], "e") && t.canConnect(tab[i][j + 1], "w")))
                    {
                        rotations[2] = true;
                    }
                    else if ((t.canConnect(tab[i - 1][j], "e") && t.canConnect(tab[i][j - 1], "n") && t.canConnect(tab[i][j + 1], "s")))
                    {
                        rotations[3] = true;
                    }
                }
                else if (!voisins[2])
                {
                    if ((t.canConnect(tab[i - 1][j], "n") && t.canConnect(tab[i + 1][j], "s") && t.canConnect(tab[i][j - 1], "w")))
                    {
                        rotations[0] = true;
                    }
                    else if ((t.canConnect(tab[i - 1][j], "w") && t.canConnect(tab[i + 1][j], "e") && t.canConnect(tab[i][j - 1], "s")))
                    {
                        rotations[1] = true;
                    }
                    else if ((t.canConnect(tab[i - 1][j], "s") && t.canConnect(tab[i + 1][j], "n") && t.canConnect(tab[i][j - 1], "e")))
                    {
                        rotations[2] = true;
                    }
                    else if ((t.canConnect(tab[i - 1][j], "e") && t.canConnect(tab[i + 1][j], "w") && t.canConnect(tab[i][j - 1], "n")))
                    {
                        rotations[3] = true;
                    }
                }
                else
                {
                    if ((t.canConnect(tab[i][j - 1], "w") && t.canConnect(tab[i + 1][j], "s") && t.canConnect(tab[i][j + 1], "e")))
                    {
                        rotations[0] = true;
                    }
                    else if ((t.canConnect(tab[i][j - 1], "s") && t.canConnect(tab[i + 1][j], "e") && t.canConnect(tab[i][j + 1], "n")))
                    {
                        rotations[1] = true;
                    }
                    else if ((t.canConnect(tab[i][j - 1], "e") && t.canConnect(tab[i + 1][j], "n") && t.canConnect(tab[i][j + 1], "w")))
                    {
                        rotations[2] = true;
                    }
                    else if ((t.canConnect(tab[i][j - 1], "n") && t.canConnect(tab[i + 1][j], "w") && t.canConnect(tab[i][j + 1], "s")))
                    {
                        rotations[3] = true;
                    }
                }
                break;
            case 4:
                if ((t.canConnect(tab[i - 1][j], "n") && (t.canConnect(tab[i + 1][j], "s")) && (t.canConnect(tab[i][j + 1], "e")) && (t.canConnect(tab[i][j - 1], "w"))))
                {
                    rotations[0] = true;
                }
                else if ((t.canConnect(tab[i - 1][j], "w") && (t.canConnect(tab[i + 1][j], "e")) && (t.canConnect(tab[i][j + 1], "n")) && (t.canConnect(tab[i][j - 1], "s"))))
                {
                    rotations[1] = true;
                }
                else if ((t.canConnect(tab[i - 1][j], "s") && (t.canConnect(tab[i + 1][j], "n")) && (t.canConnect(tab[i][j + 1], "w")) && (t.canConnect(tab[i][j - 1], "e"))))
                {
                    rotations[2] = true;
                }
                else if ((t.canConnect(tab[i - 1][j], "e") && (t.canConnect(tab[i + 1][j], "w")) && (t.canConnect(tab[i][j + 1], "s")) && (t.canConnect(tab[i][j - 1], "n"))))
                {
                    rotations[3] = true;
                }
                break;
            default:
                break;
        }
        if (rotations[0])
        {
            valid.add(0);
        }
        if (rotations[1])
        {
            valid.add(90);
        }
        if (rotations[2])
        {
            valid.add(180);
        }
        if (rotations[3])
        {
            valid.add(270);
        }
        return valid;
    }

    /*
        meepleEasy: Decide aléatoirement si l'IA va placer un meeple sur la tuile à la position (i, j)
        Entrées : Gameset g pour récupérer le plateau, i et j les indices de la tuile qu'on vient de placer
        Sorties : Un caractère représentant la position du meeple sur la tuile :
            'H' → On le place en haut sur la tuile
            'B' → On le place en bas de la tuile
            'D' → On le place à droite sur la tuile
            'G' → On le place à gauche sur la tuile
            'M' → On le place au milieu de la tuile
        Si l'IA decide de ne pas placer un meeple, ou si elle ne peut pas placer un meeple on retourne null
    */
    public String meepleEasy (Tile [][] board, int i, int j)
    {
        Random obj = new Random();
        int randomPlacement = obj.nextInt(2);

        if (randomPlacement == 0)
        {
            return null;
        }

        ArrayList<String> validPositions = board[i][j].getMeeplesPosition();

        randomPlacement = obj.nextInt(validPositions.size());

        return validPositions.get(randomPlacement);
    }

    public int [] playEasy(Tile [][] currentConfig, Map<Integer, ArrayList<Integer>> Pos, Tile currentTile)
    {

        int randomPosition, randomRotation, posx, posy;

        ArrayList<ArrayList<Integer>> validRotations = new ArrayList<>();
        ArrayList<Integer> Rotations;

        Random obj = new Random();

        validPositions =  encodeCoords(Pos);

        for (Integer validPosition : validPositions)
        {
            validRotations.add(calculateRotations(currentConfig, currentTile, validPosition));
        }

        randomPosition = obj.nextInt(validPositions.size());

        Rotations = validRotations.get(randomPosition);
        randomRotation = obj.nextInt(Rotations.size());

        posx = randomPosition / 10000;
        posy = randomPosition % 10000;

        Configuration.instance().logger().info("Tuile " + currentTile + " placée aux coordonnées (" + posx + "," + posy + ") tournée de " + randomRotation + " degrés à droite.\n");

        return new int [] {posx, posy, randomRotation};
    }
}
