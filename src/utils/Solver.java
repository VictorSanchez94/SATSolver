package utils;

import java.util.ArrayList;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Solver {

	
	
	public static void Sat2Solver (ArrayList<Clause> clauses) {
		
		DirectedGraph<String, DefaultEdge> g =
	            new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		
		
		ArrayList<String> literals = getLiterals(clauses);

		for (String v:literals) {
			g.addVertex(v);
			g.addVertex("-"+v);
		}
		
		/*<String> vertex = g.vertexSet();
		for (String s:vertex) {
			System.out.println(s);
		}*/
		
		/*for (Clause c:clauses) {
			ArrayList<Literal> cLiterals = c.getLiterals();

			ArrayList<Literal> aux = new ArrayList<Literal>();
			if (cLiterals.get(0).isNegative) {
				aux.add(new Literal(cLiterals.get(0).literal, false));
				aux.add(cLiterals.get(1));
			}
			else {
				aux.add(new Literal(cLiterals.get(0).literal, true));
				aux.add(cLiterals.get(1));
			}
			
			System.out.println("Edge: " + aux.get(0).toString() + "   " + aux.get(1).toString());

			if (!clauses.contains(new Clause(aux))) {
				g.addEdge(aux.get(0).toString(), aux.get(1).toString());
			}
			
			
			
			aux = new ArrayList<Literal>();
			if (cLiterals.get(1).isNegative) {
				aux.add(new Literal(cLiterals.get(1).literal, false));
				aux.add(cLiterals.get(0));
			}
			else {
				aux.add(new Literal(cLiterals.get(1).literal, true));
				aux.add(cLiterals.get(0));
			}
			
			System.out.println("Edge: " + aux.get(0).toString() + "   " + aux.get(1).toString());
	
			if (!clauses.contains(new Clause(aux))) {
				g.addEdge(aux.get(0).toString(), aux.get(1).toString());
			}
			
		}*/
		
		Set<String> vertex = g.vertexSet();
		Set<String> vertex2 = g.vertexSet();
		for (String v:vertex) {
			for (String v2:vertex2) {

				if (!v.equals(v2)) {
					ArrayList<Literal> auxClause = new ArrayList<Literal>();
					
					Literal ver1, ver2;
					
					if (v.startsWith("-")) {
						ver1 = new Literal(v.substring(1, v.length()),false);
					}
					else {
						ver1 = new Literal(v,true);
					}
					
					if (v2.startsWith("-")) {
						ver2 = new Literal(v2.substring(1, v2.length()),true);
					}
					else {
						ver2 = new Literal(v2,false);
					}
					
					
					auxClause.add(ver1);
					auxClause.add(ver2);
					Clause auxC = new Clause(auxClause);
					
					
//					System.out.println(v + "  " + v2 + "  --  " + ver1 + "  " + ver2);
					if (contains(clauses, auxC)) {
						
						if (!auxClause.get(0).toString().equals(auxClause.get(1).toString())) {
							System.out.println("Contiene madafaka");
							g.addEdge(v, v2);
						}
						
					}
					

				}
				
				
			}
		}
		
		
		Set<DefaultEdge> edges = g.edgeSet();
		for (DefaultEdge e:edges) {
			System.out.println(e);
		}
		
		CycleDetector<String, DefaultEdge> cycleDetector = new CycleDetector<>(g);
		if (cycleDetector.detectCycles()) {
			System.out.println("No tiene solucion niggah");
		}
		else {
			System.out.println("Tiene solución madafaka");
		}
		
	}
	
	
	private static boolean contains(ArrayList<Clause> clauses, Clause c) {
		int found = 0;
		ArrayList<Literal> cAux = c.getLiterals();


		for (Clause aux:clauses) {
			ArrayList<Literal> lAux = aux.getLiterals();

			
			for (int i=0; i<lAux.size(); i++) {
				for (int j=0; j<cAux.size(); j++) {
					
					if (lAux.get(i).isNegative == cAux.get(j).isNegative && lAux.get(i).literal.equals(cAux.get(j).literal)) {
						found ++;
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
		
		for (Clause c:clauses) {
			ArrayList<Literal> literals = c.getLiterals();
			
			for (Literal l:literals) {
				if (!list.contains(l.literal)) {
					list.add(l.literal);
				}
			}
		}
		
		return list;
	}
}
