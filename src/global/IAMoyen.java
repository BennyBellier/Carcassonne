package global;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.GameEngine;
import model.GameSet;
import model.Tile;
import model.Projects.Project;

public class IAMoyen extends IA
{
    private Random rand;

    ArrayList<Project> projetsIA;
    ArrayList<Integer> ptsIA;
    ArrayList<Project> projetsAdversaire;
    ArrayList<Integer> ptsAdversaire;

    public IAMoyen()
    {
        rand = new Random();
    }

    ArrayList<Integer> valeurs (ArrayList<Project> list)
    {
        ArrayList<Integer> retour = new ArrayList<>();
        for (Project element : list)
        {
            retour.add(element.value());
        }
        return retour;
    }

    int calcul(int i,int j,Tile currentTile, GameEngine ge, ArrayList<Project> projetsIA, ArrayList<Project> projetsAdversaire)
    // finir le projet de l'adversaire -1
    // complete un projet de l'adversaire 0
    // complete notre projet et celui de l'adversaire 1
    // complete notre projet ou en démarrer un nouveau 2
    // termine notre projet valeur du projet
    {
        ArrayList<Integer> ptsProjetsIA, ptsProjetsAdversaire,ptsIACour, ptsAdversaireCour;
        ptsProjetsIA = valeurs(projetsIA);
        ptsProjetsAdversaire = valeurs(projetsAdversaire);

        boolean placed = ge.placeTile(i,j);
        boolean completed = false;

        if (placed)
        {
            ptsIACour = valeurs(projetsIA);
            ptsAdversaireCour = valeurs(projetsAdversaire);

            for (int k = 0; k < projetsAdversaire.size(); k++)
            {
                if (projetsAdversaire.get(k).finished())
                {
                    return -1;
                }
                else if (ptsAdversaire.get(k) != ptsAdversaireCour.get(k))
                {
                    completed = true;
                }
            }
            for (int k = 0; k < projetsIA.size(); k++)
            {
                if (projetsIA.get(k).finished())
                {
                    return projetsIA.get(k).value();
                }
                else if (ptsProjetsIA.get(k) != ptsIACour.get(k))
                {
                    if (completed)
                    {
                        return 1;
                    }
                    else
                    {
                        return 2;
                    }
                }
                else
                {
                    if (completed)
                    {
                        return 0;
                    }
                }
            }
        }
        return 2;
    }

    public int[] placeTile(GameEngine G, GameSet gs, Tile currentTile, ArrayList<Project> IA, ArrayList<Project> adversaire)
    {
        projetsIA = IA;
        projetsAdversaire = adversaire;


        Map<Integer, ArrayList<Integer>> pos = gs.tilePositionsAllowed(currentTile, false);

        List<Integer> iList = new ArrayList<>(pos.keySet());
        int maxpts = -1;
        int x = 0;
        int y = 0;
        int m;
        for (int i = 0;i<iList.size();i++)
        {
            for(int j = 0;j<pos.get(i).size();j++)
            {
                if ((m =calcul(i,j,currentTile, G, projetsIA, projetsAdversaire)) >= maxpts)
                {
                    maxpts = m;
                    x = iList.get(i);
                    y = pos.get(i).get(j);
                }
            }
        }


        return new int[] {y, x};
    }

    public  String placeMeeple(List<String> meeplesPositions)
    {
        if (rand.nextInt(3) == 0)
        {
            return meeplesPositions.get(rand.nextInt(meeplesPositions.size()));
        }
        return null;
    }
}