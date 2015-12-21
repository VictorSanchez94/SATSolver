package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Formula {
	
	private ArrayList<Clause> clauses;
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
			scanner.useDelimiter("[\\(\\)\\+]");
		} catch (FileNotFoundException e) {
			System.err.println("ERROR. El fichero indicado no existe.");
		}
	}
	
	public ArrayList<Clause> start() {
		int i = 0;
		clauses = new ArrayList<Clause>();		//Initialize clauses vector
		ArrayList<Literal> aux = new ArrayList<Literal>();
		String s;
		while(scanner.hasNext()){
			s = scanner.next();
			System.out.println(s);
			if(s.equals("*")){
				clauses.add(new Clause(aux));
				if(!clauses.get(i).isHornSAT()){
					isHornSAT = false;
				}
				if(!clauses.get(i).is2SAT()){
					is2SAT = false;
				}
				if(!clauses.get(i).isNSAT()){
					isNSAT = false;
				}
				i++; aux.clear();
			}else{
				aux.add(new Literal(s));
			}
		}
		
		return clauses;
	}
	
	
	public static void main (String[] args) {
		Formula f = new Formula("testFiles/prueba1.cnf");
		ArrayList<Clause> c = f.start();
		for(int i=0; i<c.size(); i++){
			System.out.println(c.get(i).toString());
		}
	}
	
}
