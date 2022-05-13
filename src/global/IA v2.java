import java.util.Random;
import java.util.ArrayList;

public class playAgainstAI
{
    //currentConfig: Contient le plateau courant à chaque tour
    Tile [][] currentConfig;

    //currentTile: La tuile à jouer
    Tile currentTile;

    //stack: L'état courant de notre pioche (Les tuiles restantes pas encore jouées)
    Pioche stack;

    /*
        validPositions: L'ensemble des positions où currentTile peut être placé
        On stocke chaque coordonnée (i,j) en le mappant à un seul entier avec la fonction: e = (i * 1000) + j
        Pour récupérer i : i = e / 10000
        Pour récupérer j : j = e % 10000
    */
    ArrayList <Integer> validPositions;
    /*
        rotations: Chaque element de cet ArrayList correspond à un element du validPositions
        Exemple rotations.get(0) retourne un tableau de booléens qui indique si la tuile currentTile peut être placée avec chaque rotation à l'indice validPositions.get(0)
    */
    ArrayList<boolean []> rotations;

    //meeples: Nombre de Meeples pas placés
    int meeples;

    //meeplePositions: Positions où notre meeples sont placés dans le plateau
    ArrayList<Integer> placedMeeples;

    playAgainstAI()
    {
        currentConfig = null;
        currentTile = null;
        stack = null;
        meeples = 7;
        placedMeeples = new ArrayList<>();
        validPositions = new ArrayList <>();
        rotations = new ArrayList<>();
    }


    /*
        verifyConnection: Prend une tuile et verifie si elle peut être placée dans le plateau,à l'indice (i,j)
        Entrées: t la tuile, tab le plateau, i et j les indices de la position testée
        Sorties: Un tableaude booléens à 4 cases. Chaque case verifie si la tuile peut être placé avec une rotation specifique:
            rotations[0]: Pas de rotation
            rotations[1]: Rotation à 90 degrés à droite
            rotations[2]: Rotation à 180 degrés à droite
            rotations[3]: Rotation à 270 degrés à droite
        Remarque: Le switch verifie toutes les rotations en fonction de cases adjacentes à cette position.
    */
    public boolean [] verifyConnection(Tile t, Tile[][] tab, int i, int j)
    {
		/*
			adjacents[0] == true -> Il existe une tuile en dessus de (i,j)
			adjacents[1] == true -> Il existe une tuile en dessous de (i,j)
			adjacents[2] == true -> Il existe une tuile à droite de (i,j)
			adjacents[3] == true -> Il existe une tuile à gauche de (i,j)
		*/
        boolean [] adjacents = new boolean[4];
        boolean [] rotations = new boolean [4];
        int nbAdjacents = 0;

        for (int k = 0; k < 3; k++)
        {
            adjacents[k] = false;
        }

        if ((i == 0) || (tab[i -1][j] == null))
        {
            adjacents[0] = false;
        }
        else
        {
            adjacents[0] = true;
            nbAdjacents++;
        }
        if ((j == 0) || (tab[i][j -1] == null))
        {
            adjacents[3] = false;
        }
        else
        {
            adjacents[3] = true;
            nbAdjacents++;
        }
        if ((i == tab.length - 1) || (tab[i + 1][j] == null))
        {
            adjacents[1] = false;
        }
        else
        {
            adjacents[1] = true;
            nbAdjacents++;
        }
        if ((j == tab[0].length - 1) || (tab[i][j + 1] == null))
        {
            adjacents[2] = false;
        }
        else
        {
            adjacents[2] = true;
            nbAdjacents++;
        }
        switch(nbAdjacents)
        {
            case 1:
                if (adjacents[0])
                {
                    if (t.canConnect(tab[i -1][j], "n"))
                    {
                        rotations[0] = true;
                    }
					else if (t.canConnect(tab[i -1][j], "e"))
                    {
                        rotations[1] = true;
                    }
					else if (t.canConnect(tab[i -1][j], "s"))
                    {
                        rotations[2] = true;
                    }
					else if (t.canConnect(tab[i -1][j], "w"))
                    {
                        rotations[3] = true;
                    }
                }
                else if (adjacents[1])
                {
                    if (t.canConnect(tab[i -1][j], "s"))
                    {
                        rotations[0] = true;
                    }
					else if (t.canConnect(tab[i -1][j], "w"))
                    {
                        rotations[1] = true;
                    }
					else if (t.canConnect(tab[i -1][j], "n"))
                    {
                        rotations[2] = true;
                    }
					else if (t.canConnect(tab[i -1][j], "e"))
                    {
                        rotations[3] = true;
                    }
                }
                else if (adjacents[2])
                {
                    if (t.canConnect(tab[i -1][j], "e"))
                    {
                        rotations[0] = true;
                    }
					else if (t.canConnect(tab[i -1][j], "n"))
                    {
                        rotations[1] = true;
                    }
					else if (t.canConnect(tab[i -1][j], "w"))
                    {
                        rotations[2] = true;
                    }
					else if (t.canConnect(tab[i -1][j], "s"))
                    {
                        rotations[3] = true;
                    }
                }
                else if (adjacents[3])
                {
                    if (t.canConnect(tab[i -1][j], "w"))
                    {
                        rotations[0] = true;
                    }
					else if (t.canConnect(tab[i -1][j], "s"))
                    {
                        rotations[1] = true;
                    }
					else if (t.canConnect(tab[i -1][j], "e"))
                    {
                        rotations[2] = true;
                    }
					else if (t.canConnect(tab[i -1][j], "n"))
                    {
                        rotations[3] = true;
                    }
                }
            case 2:
                if (adjacents[0])
                {
                    if (adjacents[1])
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
                    if (adjacents[2])
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
                    if (adjacents[3])
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
                else if (adjacents[1] && adjacents[2])
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
                else if (adjacents[2] && adjacents[3])
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
                else if (adjacents[1] && adjacents[3])
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
            case 3:
                if (adjacents[0] && adjacents[1] && adjacents[2])
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
                else if (adjacents[0] && adjacents[3] && adjacents[2])
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
                else if (adjacents[0] && adjacents[1] && adjacents[3])
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
                else if (adjacents[3] && adjacents[1] && adjacents[2])
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
            default:
                return rotations;
        }
    }

    public int playEasy(Tile [][] tab, Pioche p,Tile t)
    {
        currentConfig = tab.clone();

        stack = p.getPioche();

        currentTile = t.clone();

        int nbPositions, randomPosition, posx, posy, randomRotation;

        int nbRotations = 0;

        boolean [] canPlace;

        Random obj = new Random();

        //On suppose que la tuile t est déjà enlevé de la pioche p
        for (int i = 0; i < currentConfig.length; i++)
        {
            for (int j = 0; j < currentConfig[0].length; j++)
            {
                canPlace = verifyConnection(currentTile, currentConfig, i, j);
                for (int k = 0; k < 4; k++)
                {
                    if (canPlace[k])
                    {
                        validPositions.add(i * 10000 + j);
                        rotations.add(canPlace);
                        break;
                    }
                }
            }
        }
        nbPositions = validPositions.size();
        if (nbPositions == 1)
        {
            posx = validPositions.get(0) / 10000;
            posy = validPositions.get(0) % 10000;
            Configuration.instance().logger().info("Tuile " + t.toString() + " placée aux coordonnées (" + posx + "," + posy + ").");
            for (int i = 0; i < 4; i++)
            {
                if (rotations.get(0)[i])
                {
                    nbRotations++;
                }
            }
            if (nbRotations == 1)
            {
                Configuration.instance().logger().info("Tuile " + t.toString() + " tournée à " + rot + " degrés");
            }
        }
        if (nbPositions != 0)
        {
            randomPosition = obj.nextInt(nbPositions);
        }
    }
}
