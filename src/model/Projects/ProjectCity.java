package model.Projects;

import java.util.ArrayList;
import java.util.List;

import global.Configuration;
import model.Tile;

public class ProjectCity extends Project {
	private List<TileOfProject> list;
	private boolean finish;

	/**
	 ** Vérifie si pour la portion de ville à la case (x, y), la ville qui y
	 ** correspond est terminée
	 *
	 * @param set         Plateau de la partie courante
	 * @param x           position x de l'abbeye
	 * @param y           position y de l'abbeye
	 * @param card        position de la portition de la ville sur la tuile
	 * @param cityVisited liste des tuiles contenant une villes visitées
	 */
	public ProjectCity(Tile[][] set, int x, int y, String card, List<Tile> cityVisited) {
		super(Type.CITY);
		list = new ArrayList<>();
		finish = false;

		Configuration
				.instance()
				.logger()
				.info(
						"Évaluation du projet ville aux coordonnées (" + x + ", " + y + ")");
		finish = evaluate(list, set, x, y, card);


		cityVisited.addAll(TileOfProject.toTileList(list));

		Configuration
				.instance()
				.logger()
				.info(
						"Le projet de ville aux coordonnées (" +
								x +
								", " +
								y +
								") est fini : " +
								isFinish() +
								", il compte " +
								value() +
								" points");
	}


	boolean evaluate(List<TileOfProject> visited, Tile[][] set, int x, int y, String card) {
		Tile t;
		boolean ender;
		StringBuilder str = new StringBuilder();

		str.append("Évaluation de la tuile (" + x + ", " + y + ") -> " + card + ", type = " + type.toString() + "\n");

		if ((t = set[y][x]) == null) {
			str.append("\tLa tuile est null" + "\n");
			Configuration.instance().logger().fine(str.toString());
			return false;
		}

		if (t.getCardinalType(card) != Tile.Type.CITY) {
			str.append("\tLe cardinal ne correspond pas à un type vile" + "\n");
			Configuration.instance().logger().fine(str.toString());
			return false;
		}

		ender = t.cityEnder();

		TileOfProject top = TileOfProject.fromTileCoord(t, x, y, card, ender, Tile.Type.CITY);

		if (!TileOfProject.contains(visited, top)) {
			visited.add(top);

			if (ender) {
				str.append("\tLa tuile est une fin de ville" + "\n");
				switch (card) {
					case "n":
						str.append("\tÉvaluation de la cardinalité nord" + "\n");
						if (t.north() == Tile.Type.CITY && evaluate(visited, set, x, y - 1, "s")) {
							str.append("\tLa tuile est lié à d'autre tuile de type ville" + "\n");
							Configuration.instance().logger().fine(str.toString());
							return true;
						} else {
							str.append("\tLa tuile n'est pas lié à d'autre tuie de type ville" + "\n");
							Configuration.instance().logger().fine(str.toString());
							return false;
						}
					case "s":
						str.append("\tÉvaluation de la cardinalité sud" + "\n");
						if (t.south() == Tile.Type.CITY && evaluate(visited, set, x, y + 1, "n")) {
							str.append("\tLa tuile est lié à d'autre tuile de type ville" + "\n");
							Configuration.instance().logger().fine(str.toString());
							return true;
						} else {
							str.append("\tLa tuile n'est pas lié à d'autre tuie de type ville" + "\n");
							Configuration.instance().logger().fine(str.toString());
							return false;
						}
					case "e":
						str.append("\tÉvaluation de la cardinalité est" + "\n");
						if (t.east() == Tile.Type.CITY && evaluate(visited, set, x + 1, y, "w")) {
							str.append("\tLa tuile est lié à d'autre tuile de type ville" + "\n");
							Configuration.instance().logger().fine(str.toString());
							return true;
						} else {
							str.append("\tLa tuile n'est pas lié à d'autre tuie de type ville" + "\n");
							Configuration.instance().logger().fine(str.toString());
							return false;
						}
					case "w":
						str.append("\tÉvaluation de la cardinalité ouest" + "\n");
						if (t.west() == Tile.Type.CITY && evaluate(visited, set, x - 1, y, "e")) {
							str.append("\tLa tuile est lié à d'autre tuile de type ville" + "\n");
							Configuration.instance().logger().fine(str.toString());
							return true;
						} else {
							str.append("\tLa tuile n'est pas lié à d'autre tuie de type ville" + "\n");
							Configuration.instance().logger().fine(str.toString());
							return false;
						}
				}
			} else {
				str.append("\tLa tuile n'est pas une fin de ville" + "\n");
				boolean n = true, s = true, e = true, w = true;
				if (t.north() == Tile.Type.CITY) {
					str.append("\tÉvaluation de la cardinalité nord" + "\n");
					if (t.north() == Tile.Type.CITY)
						n = evaluate(visited, set, x, y - 1, "s");
				}
				if (t.south() == Tile.Type.CITY) {
					str.append("\tÉvaluation de la cardinalité sud" + "\n");
					if (t.south() == Tile.Type.CITY)
						s = evaluate(visited, set, x, y + 1, "n");
				}
				if (t.east() == Tile.Type.CITY) {
					str.append("\tÉvaluation de la cardinalité est" + "\n");
					if (t.east() == Tile.Type.CITY)
						e = evaluate(visited, set, x + 1, y, "w");
				}
				if (t.west() == Tile.Type.CITY) {
					str.append("\tÉvaluation de la cardinalité ouest" + "\n");
					if (t.west() == Tile.Type.CITY)
						w = evaluate(visited, set, x - 1, y, "e");
				}
				str.append("\tRésultat de la tuile (" + x + ", " + y + ") -> " + card + " : nord = " + n + ", sud = " + s + ", est = " + e + ", ouest = " + w + "\n");
				Configuration.instance().logger().fine(str.toString());
				return (n && s && e && w);
			}
		}
		Configuration.instance().logger().fine(str.toString());
		return true;
	}

	/**
	 ** Retourne le nombre de blason que contient la ville
	 *
	 * @return int
	 */
	int blasonCounter() {
		int blason = 0;
		for (TileOfProject t : list) {
			if (t.t.blason())
				blason += 1;
		}
		return blason;
	}

	/**
	 ** Retourne la valeur courante du projet
	 *
	 * @return int
	 */
	@Override
	public int value() {
		if (isFinish())
			return (list.size() + blasonCounter()) * 2;
		return list.size() + blasonCounter();
	}

	@Override
	public boolean isFinish() {
		return finish;
	}

	@Override
	public List<TileOfProject> list() {
		return list;
	}
}
