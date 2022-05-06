import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

public class playAgainstAI
{
	//currentConfig: Contient le plateau courrant à chaque tour
	Tile [][] currentConfig;
	
	//currentTile: La tuile a jouer
	Tile currentTile;
	
	//stack: L'etat courant de notre pioche (Les tuiles restantes pas encore jouées)
	Pioche stack;
	
	//availableSpace: L'ensemble des positions où currentTile peut être placé (Le premier Integer correspond à l'indice où on peut placer la tuile, le deuxieme correspond à combien degres il faut le tourner à droite pour le placer)
	Map<Integer, Integer> availableSpace;
	
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
		placedMeeples = new ArrayList<Integer>;
		availableSpace = new Map<Integer, Integer>;
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
		boolean rotations = {false,false,false,false}
		int nbAdjacents = 0;
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
					if t.canConnect(tab[i -1][j], "n")
					{
						rotations[0] = true;
					}
					else if t.canConnect(tab[i -1][j], "e")
					{
						rotations[1] = true;
					}
					else if t.canConnect(tab[i -1][j], "s")
					{
						rotations[2] = true;
					}
					else if t.canConnect(tab[i -1][j], "w")
					{
						rotations[3] = true;
					}
				}
				else if (adjacents[1])
				{
					if t.canConnect(tab[i -1][j], "s")
					{
						rotations[0] = true;
					}
					else if t.canConnect(tab[i -1][j], "w")
					{
						rotations[1] = true;
					}
					else if t.canConnect(tab[i -1][j], "n")
					{
						rotations[2] = true;
					}
					else if t.canConnect(tab[i -1][j], "e")
					{
						rotations[3] = true;
					}
				}
				else if (adjacents[2])
				{
					if t.canConnect(tab[i -1][j], "e")
					{
						rotations[0] = true;
					}
					else if t.canConnect(tab[i -1][j], "n")
					{
						rotations[1] = true;
					}
					else if t.canConnect(tab[i -1][j], "w")
					{
						rotations[2] = true;
					}
					else if t.canConnect(tab[i -1][j], "s")
					{
						rotations[3] = true;
					}
				}
				else if (adjacents[3])
				{
					if t.canConnect(tab[i -1][j], "w")
					{
						rotations[0] = true;
					}
					else if t.canConnect(tab[i -1][j], "s")
					{
						rotations[1] = true;
					}
					else if t.canConnect(tab[i -1][j], "e")
					{
						rotations[2] = true;
					}
					else if t.canConnect(tab[i -1][j], "n")
					{
						rotations[3] = true;
					}
				}
			case 2:
				if (adjacent[0])
				{
					if (adjacent[1])
					{
						if ((t.canConnect(tab[i - 1][j], "n") && t.canConnect(tab[i + 1][j], "s"))
						{
							rotations[0] = true;
						}
						else if ((t.canConnect(tab[i - 1][j], "w") && t.canConnect(tab[i + 1][j], "e"))
						{
							rotations[1] = true;
						}
						else if ((t.canConnect(tab[i - 1][j], "s") && t.canConnect(tab[i + 1][j], "n"))
						{
							rotations[2] = true;
						}
						else if ((t.canConnect(tab[i - 1][j], "e") && t.canConnect(tab[i + 1][j], "w"))
						{
							rotations[3] = true;
						}
					}
					if (adjacent[2])
					{
						if ((t.canConnect(tab[i - 1][j], "n") && t.canConnect(tab[i][j + 1], "e"))
						{
							rotations[0] = true;
						}
						else if ((t.canConnect(tab[i - 1][j], "w") && t.canConnect(tab[i][j + 1], "n"))
						{
							rotations[1] = true;
						}
						else if ((t.canConnect(tab[i - 1][j], "s") && t.canConnect(tab[i][j + 1], "w"))
						{
							rotations[2] = true;
						}
						else if ((t.canConnect(tab[i - 1][j], "e") && t.canConnect(tab[i][j + 1], "s"))
						{
							rotations[3] = true;
						}
					}
					if (adjacent[3])
					{
						if ((t.canConnect(tab[i - 1][j], "n") && t.canConnect(tab[i][j - 1], "w"))
						{
							rotations[0] = true;
						}
						else if ((t.canConnect(tab[i - 1][j], "w") && t.canConnect(tab[i][j - 1], "s"))
						{
							rotations[1] = true;
						}
						else if ((t.canConnect(tab[i - 1][j], "s") && t.canConnect(tab[i][j - 1], "e"))
						{
							rotations[2] = true;
						}
						else if ((t.canConnect(tab[i - 1][j], "e") && t.canConnect(tab[i][j - 1], "n"))
						{
							rotations[3] = true;
						}
					}
				}
				else if (adjacent[1] && adjacent[2])
				{
					if ((t.canConnect(tab[i + 1][j], "s") && t.canConnect(tab[i][j + 1], "e"))
					{
						rotations[0] = true;
					}
					else if ((t.canConnect(tab[i + 1][j], "e") && t.canConnect(tab[i][j + 1], "n"))
					{
						rotations[1] = true;
					}
					else if ((t.canConnect(tab[i + 1][j], "n") && t.canConnect(tab[i][j + 1], "w"))
					{
						rotations[2] = true;
					}
					else if ((t.canConnect(tab[i + 1][j], "w") && t.canConnect(tab[i][j + 1], "s"))
					{
						rotations[3] = true;
					}
				}
				else if (adjacent[2] && adjacent[3])
				{
					if ((t.canConnect(tab[i][j - 1], "w") && t.canConnect(tab[i][j + 1], "e"))
					{
						rotations[0] = true;
					}
					else if ((t.canConnect(tab[i][j - 1], "s") && t.canConnect(tab[i][j + 1], "n"))
					{
						rotations[1] = true;
					}
					else if ((t.canConnect(tab[i][j - 1], "e") && t.canConnect(tab[i][j + 1], "w"))
					{
						rotations[2] = true;
					}
					else if ((t.canConnect(tab[i][j - 1], "n") && t.canConnect(tab[i][j + 1], "s"))
					{
						rotations[3] = true;
					}
				}
				else if (adjacent[1] && adjacent[3])
				{
					if ((t.canConnect(tab[i + 1][j], "s") && t.canConnect(tab[i][j - 1], "w"))
					{
						rotations[0] = true;
					}
					else if ((t.canConnect(tab[i + 1][j], "e") && t.canConnect(tab[i][j - 1], "s"))
					{
						rotations[1] = true;
					}
					else if ((t.canConnect(tab[i + 1][j], "n") && t.canConnect(tab[i][j - 1], "e"))
					{
						rotations[2] = true;
					}
					else if ((t.canConnect(tab[i + 1][j], "w") && t.canConnect(tab[i][j - 1], "n"))
					{
						rotations[3] = true;
					}
				}
			case 3:
				if (adjacent[0] && adjacent[1] && adjacent[2])
				{
					if ((t.canConnect(tab[i - 1][j], "n") && t.canConnect(tab[i + 1][j], "s") && t.canConnect(tab[i][j + 1], "e"))
					{
						rotations[0] = true;
					}
					else if ((t.canConnect(tab[i - 1][j], "w") && t.canConnect(tab[i + 1][j], "e") && t.canConnect(tab[i][j + 1], "n"))
					{
						rotations[1] = true;
					}
					else if ((t.canConnect(tab[i - 1][j], "s") && t.canConnect(tab[i + 1][j], "n") && t.canConnect(tab[i][j + 1], "w"))
					{
						rotations[2] = true;
					}
					else if ((t.canConnect(tab[i - 1][j], "e") && t.canConnect(tab[i + 1][j], "w") && t.canConnect(tab[i][j + 1], "s"))
					{
						rotations[3] = true;
					}
				}
				else if (adjacent[0] && adjacent[3] && adjacent[2])
				{
					if ((t.canConnect(tab[i - 1][j], "n") && t.canConnect(tab[i][j - 1], "w") && t.canConnect(tab[i][j + 1], "e"))
					{
						rotations[0] = true;
					}
					else if ((t.canConnect(tab[i - 1][j], "w") && t.canConnect(tab[i][j - 1], "s") && t.canConnect(tab[i][j + 1], "n"))
					{
						rotations[1] = true;
					}
					else if ((t.canConnect(tab[i - 1][j], "s") && t.canConnect(tab[i][j - 1], "e") && t.canConnect(tab[i][j + 1], "w"))
					{
						rotations[2] = true;
					}
					else if ((t.canConnect(tab[i - 1][j], "e") && t.canConnect(tab[i][j - 1], "n") && t.canConnect(tab[i][j + 1], "s"))
					{
						rotations[3] = true;
					}
				}
				else if (adjacent[0] && adjacent[1] && adjacent[3])
				{
					if ((t.canConnect(tab[i - 1][j], "n") && t.canConnect(tab[i + 1][j], "s") && t.canConnect(tab[i][j - 1], "w"))
					{
						rotations[0] = true;
					}
					else if ((t.canConnect(tab[i - 1][j], "w") && t.canConnect(tab[i + 1][j], "e") && t.canConnect(tab[i][j - 1], "s"))
					{
						rotations[1] = true;
					}
					else if ((t.canConnect(tab[i - 1][j], "s") && t.canConnect(tab[i + 1][j], "n") && t.canConnect(tab[i][j - 1], "e"))
					{
						rotations[2] = true;
					}
					else if ((t.canConnect(tab[i - 1][j], "e") && t.canConnect(tab[i + 1][j], "w") && t.canConnect(tab[i][j - 1], "n"))
					{
						rotations[3] = true;
					}
				}
				else if (adjacent[3] && adjacent[1] && adjacent[2])
				{
					if ((t.canConnect(tab[i][j - 1], "w") && t.canConnect(tab[i + 1][j], "s") && t.canConnect(tab[i][j + 1], "e"))
					{
						rotations[0] = true;
					}
					else if ((t.canConnect(tab[i][j - 1], "s") && t.canConnect(tab[i + 1][j], "e") && t.canConnect(tab[i][j + 1], "n"))
					{
						rotations[1] = true;
					}
					else if ((t.canConnect(tab[i][j - 1], "e") && t.canConnect(tab[i + 1][j], "n") && t.canConnect(tab[i][j + 1], "w"))
					{
						rotations[2] = true;
					}
					else if ((t.canConnect(tab[i][j - 1], "n") && t.canConnect(tab[i + 1][j], "w") && t.canConnect(tab[i][j + 1], "s"))
					{
						rotations[3] = true;
					}
				}
			case 4:
				if ((t.canConnect(tab[i - 1][j], "n") && (t.canConnect(tab[i + 1][j], "s")) && (t.canConnect(tab[i][j + 1], "e")) && (t.canConnect(tab[i][j - 1], "w")))
				{
					rotations[0] = true;
				}
				else if ((t.canConnect(tab[i - 1][j], "w") && (t.canConnect(tab[i + 1][j], "e")) && (t.canConnect(tab[i][j + 1], "n")) && (t.canConnect(tab[i][j - 1], "s")))
				{
					rotations[1] = true;
				}
				else if ((t.canConnect(tab[i - 1][j], "s") && (t.canConnect(tab[i + 1][j], "n")) && (t.canConnect(tab[i][j + 1], "w")) && (t.canConnect(tab[i][j - 1], "e")))
				{
					rotations[2] = true;
				}
				else if ((t.canConnect(tab[i - 1][j], "e") && (t.canConnect(tab[i + 1][j], "w")) && (t.canConnect(tab[i][j + 1], "s")) && (t.canConnect(tab[i][j - 1], "n")))
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
		
		currentTile = t.clone;
		
		int canPlace, nbPositions, randomPosition, posx, posy, rotation;
		
		Random obj = new Random();
		
		//On suppose que la tuile t est déjà enlevé de la pioche p
		for (int i = 0; i < currentConfig.length; i++)
		{
			for (int j = 0; j < currentConfig[0].length; j++)
			{
				canPlace = verifyConnection(currentTile, currentConfig, i, j)
				if (canPlace != -1)
				{
					availableSpace.put(i * 10000 + j, canPlace)
				}
			}
		}
		nbPositions = availableSpace.size;
		if (nbPositions == 1)
		{
			posx = 
			Configuration.instance().logger().info("Tuile " + t.toString() + " placée aux coordonnées (" + i + "," + j + ").");
			
		}
		if (nbPositions != 0)
		{
			randomPosition = obj.nextInt(nbPositions);
		}
	}
}
