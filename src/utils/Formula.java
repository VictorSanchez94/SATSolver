package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Formula {
	
	private ArrayList<Clause> clauses;
	private Scanner scanner;
	private boolean isHornSAT = true;
	private boolean is2SAT = true;
	private boolean isNSAT = true;
	
	/**
	 * Constructor
	 */
	public Formula (String pathFile) {
		try {
			scanner = new Scanner(new File(pathFile));
			scanner.useDelimiter("([\\)\n\r]|\\(|\\)|\\+)+");
		} catch (FileNotFoundException e) {
			System.err.println("ERROR. El fichero indicado no existe.");
		}
	}
	
	public void start() {
		clauses = new ArrayList<Clause>();		//Initialize clauses vector
		ArrayList<Literal> aux = new ArrayList<Literal>();
		String s;
		while(scanner.hasNext()){
			s = scanner.next();
			System.out.println(s);
			if(s.equals("*")){
				Clause cAux = new Clause((ArrayList<Literal>) aux.clone());
				clauses.add(cAux);
				if(!cAux.isHornSAT()){
					isHornSAT = false;
				}
				if(!cAux.is2SAT()){
					is2SAT = false;
				}
				if(!cAux.isNSAT()){
					isNSAT = false;
				}
				aux.clear();
			}else{
				aux.add(new Literal(s));
			}
		}
		clauses.add(new Clause((ArrayList<Literal>) aux.clone()));
	}
	
	public String toString() {
		String buf = "";
		Iterator<Clause> it = clauses.iterator();
		if(it.hasNext()){
			buf += it.next().toString();
			while(it.hasNext()){
				buf += " * " + it.next();
			}
		}
		return buf;
	}
	
	public ArrayList<Clause> getFormula() {
		return clauses;
	}
	
	
	public static void main (String[] args) {
		Formula f = new Formula("testFiles/prueba1.cnf");
		f.start();
		System.out.println("LENGTH: " + f.getFormula().size());
		System.out.println(f.toString());
	}
	
}
