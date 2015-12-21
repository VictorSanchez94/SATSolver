package utils;

import java.util.ArrayList;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Solver {

	
	
	public void SatSolver (Clause[] clauses) {
		
		DirectedGraph<String, DefaultEdge> g =
	            new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		
		
		

		
		String v1 = "v1";
	    String v2 = "v2";
	    String v3 = "v3";
	    String v4 = "v4";

	    // add the vertices
	    g.addVertex(v1);
	    g.addVertex(v2);
	    g.addVertex(v3);
	    g.addVertex(v4);

	    // add edges to create a circuit
	    g.addEdge(v1, v2);
	    g.addEdge(v2, v3);
	    g.addEdge(v3, v4);
	    g.addEdge(v4, v1);
		    
	}
	
	
	public ArrayList<String> getLiterals(Clause[] clauses) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		for (Clause c:clauses) {
			String[] literals = c.getVariables();
			
			for (String s:literals) {
				if (!s.contains)
			}
		}
	}
}
