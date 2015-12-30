package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.StrongConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedSubgraph;

import sun.security.provider.certpath.Vertex;

public class Solver {

	/**
	 * References: http://www.cs.umd.edu/~gasarch/TOPICS/sat/2SAT.ppt
	 * https://kartikkukreja.wordpress.com/2013/05/16/solving-2-sat-in-linear-
	 * time/
	 */
	public static boolean Sat2Solver(ArrayList<Clause> clauses) {

		DirectedGraph<String, DefaultEdge> g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		ArrayList<String> literals = getLiterals(clauses);

		for (String v : literals) {
			g.addVertex(v);
			g.addVertex("-" + v);
		}

		Set<String> vertex = g.vertexSet();
		Set<String> vertex2 = g.vertexSet();
		
		// Fill the directed graph
		for (String v : vertex) {
			for (String v2 : vertex2) {

				if (!v.equals(v2)) {
					ArrayList<Literal> auxClause = new ArrayList<Literal>();

					Literal ver1, ver2;

					if (v.startsWith("-")) {
						ver1 = new Literal(v.substring(1, v.length()), false);
					} else {
						ver1 = new Literal(v, true);
					}

					if (v2.startsWith("-")) {
						ver2 = new Literal(v2.substring(1, v2.length()), true);
					} else {
						ver2 = new Literal(v2, false);
					}

					auxClause.add(ver1);
					auxClause.add(ver2);
					Clause auxC = new Clause(auxClause);

					if (contains(clauses, auxC)) {

						if (!auxClause.get(0).toString().equals(auxClause.get(1).toString())) {
							g.addEdge(v, v2);
						}

					}

				}

			}
		}

		// Gets the strongly connected components
		StrongConnectivityInspector<String, DefaultEdge> sci = new StrongConnectivityInspector<String, DefaultEdge>(g);
		List<DirectedSubgraph<String, DefaultEdge>> subgraphs = sci.stronglyConnectedSubgraphs();

//		System.out.println(subgraphs);

		if (subgraphs.size() % 2 == 0) {
			int numSetsNegated = 0;

			// Checks if for each subgraph there is his opposite subgraph
			for (int i = 0; i < subgraphs.size(); i++) {
				for (int j = i + 1; j < subgraphs.size(); j++) {
					if (i != j && checkNegative(subgraphs.get(i).vertexSet(), subgraphs.get(j).vertexSet())) {
						numSetsNegated++;
					}
				}
			}

			if (numSetsNegated == subgraphs.size() / 2) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * Return true if set == -set2
	 */
	private static boolean checkNegative(Set<String> set, Set<String> set2) {

		if (set.size() == set2.size()) {

			int vertexChecked = 0;
			for (String v : set) {
				if (v.startsWith("-") && set2.contains(v.substring(1, v.length()))) {
					vertexChecked++;
				} else if (set2.contains("-" + v)) {
					vertexChecked++;
				}
			}

			if (vertexChecked == set.size()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	private static boolean contains(ArrayList<Clause> clauses, Clause c) {
		int found = 0;
		ArrayList<Literal> cAux = c.getLiterals();

		for (Clause aux : clauses) {
			ArrayList<Literal> lAux = aux.getLiterals();

			for (int i = 0; i < lAux.size(); i++) {
				for (int j = 0; j < cAux.size(); j++) {

					if (lAux.get(i).isNegative == cAux.get(j).isNegative
							&& lAux.get(i).literal.equals(cAux.get(j).literal)) {
						found++;
					}

				}
			}

			if (found == c.getLiterals().size()) {
				return true;
			}

			found = 0;

		}

		return false;
	}

	private static ArrayList<String> getLiterals(ArrayList<Clause> clauses) {

		ArrayList<String> list = new ArrayList<String>();

		for (Clause c : clauses) {
			ArrayList<Literal> literals = c.getLiterals();

			for (Literal l : literals) {
				if (!list.contains(l.literal)) {
					list.add(l.literal);
				}
			}
		}

		return list;
	}

	/* HORN-SAT SOLVER METHODS */

	/**
	 * Pre: formula must be Horn-SAT Post: Return true if this formula is
	 * satisfiable
	 */
	public static boolean hornSATSolver(Formula formula) {
		Clause clause = findUnitaryClause(formula);
		if (clause == null) {
			return true;
		} else {
			Literal l = clause.get(0);
			Literal lNegate = new Literal(l.getLiteral(), !l.isNegative());
			formula.removeClause(clause);
			@SuppressWarnings("unchecked") // cast conversion 100% granted
			ArrayList<Clause> list = (ArrayList<Clause>) formula.getFormula().clone();
			for (Clause c : list) {
				if (c.contains(l)) {
					formula.removeClause(c);
				}
				if (c.contains(lNegate)) {
					formula.removeClause(c);
					c.removeLiteral(lNegate);
					if (c.getSize() == 0) {
						return false;
					}
					formula.addClause(c);
				}
			}
			return hornSATSolver(formula);
		}
	}

	/**
	 * Pre: --- Post: Return the first Clause with only 1 element of the
	 * Formula. If Formula does not have unitaries Clauses, return null.
	 */
	private static Clause findUnitaryClause(Formula formula) {
		for (Clause c : formula.getFormula()) {
			if (c.getSize() == 1) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Pre: formula must be NSAT Post: Return true if this formula is
	 * satisfiable
	 */
	public static boolean nSATSolver(Formula formula) {
		ArrayList<Literal> vars = formula.getVariables();
		// vars.add(new Literal("x")); vars.add(new Literal("y")); vars.add(new
		// Literal("z"));
		boolean satisfiable = formula.isSatisfiable(formula, vars);
		return satisfiable;
	}

	public static void main(String[] args) {
		// Formula f = new Formula("testFiles/pruebaNSAT.cnf",true);
		if (args.length == 0) {
			System.err.println("Ejecute el programa, pasando una CNF como argumento");
			return;
		}

		Formula f;
		if (args[0].equals("-f")) {
			f = new Formula(args[1], true);
		} else {
			f = new Formula(args[0], false);
		}

		f.start();
		System.out.println("FORMULA: " + f.toString() + "\n");

		if (f.isHornSAT()) {
			System.out.println("Es una formula de tipo Horn-SAT");
			System.out.println("Comprobando si es satisfactible...");
			if (hornSATSolver(f)) {
				System.out.println("Es satisfactible.");
			} else {
				System.out.println("No es satisfactible.");
			}
		} else if (f.is2SAT()) {
			System.out.println("Es una formula de tipo 2-SAT");
			System.out.println("Comprobando si es satisfactible...");
			if (Sat2Solver(f.getFormula())) {
				System.out.println("Es satisfactible.");
			} else {
				System.out.println("No es satisfactible.");
			}
		} else if (f.isNSAT()) {
			System.out.println("Es una formula de tipo N-SAT");
			System.out.println("Comprobando si es satisfactible...");
			if (nSATSolver(f)) {
				System.out.println("Es satisfactible.");
			} else {
				System.out.println("No es satisfactible.");
			}
		} else {
			System.out.println("ERROR. No se trata de una funcion cnf.");
		}
	}

}
