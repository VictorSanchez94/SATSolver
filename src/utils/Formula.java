package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Formula {
	
	private Clause[] clauses;
	private Scanner scanner;
	private boolean isHornSAT;
	private boolean is2SAT;
	private boolean isNSAT;
	
	/**
	 * Constructor
	 */
	public Formula (String pathFile) {
		try {
			scanner = new Scanner(new File(pathFile));
			scanner.useDelimiter("[\\(\\)\\+\\ *\\n]");
		} catch (FileNotFoundException e) {
			System.err.println("ERROR. El fichero indicado no existe.");
		}
	}
	
	public Clause[] start() {
		int i = 0;
		int numClauses = scanner.nextInt();
		clauses = new Clause[numClauses];		//Initialize clauses vector
		ArrayList<String> aux = new ArrayList<String>();
		String s;
		while(scanner.hasNext()){
			s = scanner.next();
			if(s.equals("*")){
				clauses[i] = new Clause((String[])aux.toArray());
				if(!clauses[i].isHornSAT()){
					isHornSAT = false;
				}
				if(!clauses[i].is2SAT()){
					is2SAT = false;
				}
				if(!clauses[i].isNSAT()){
					isNSAT = false;
				}
				i++; aux.clear();
			}else{
				aux.add(scanner.next());
			}
		}
		
		
		return clauses;
	}
	
	
	public static void main (String[] args) {
		Formula f = new Formula("testFiles/prueba1.cnf");
		Clause[] c = f.start();
		for(int i=0; i<c.length; i++){
			System.out.println(c[i].toString());
		}
	}
	
}
