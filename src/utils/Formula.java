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
	private boolean dontChangeNSAT = false;
	
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
	
	/**
	 * Pre: ---
	 * Post: Parse the input and add the SAT Formula to the ArrayList
	 */
	@SuppressWarnings("unchecked")			//ArrayList<Literal> cast conversion is 100% true
	public void start() {
		clauses = new ArrayList<Clause>();		//Initialize clauses vector
		ArrayList<Literal> aux = new ArrayList<Literal>();
		String s;
		while(scanner.hasNext()){
			s = scanner.next();
			System.out.println(s);
			if(s.equals("*")){				//End of a clause detection
				Clause cAux = new Clause((ArrayList<Literal>) aux.clone());
				clauses.add(cAux);
				//SAT type detection
				if(!cAux.isHornSAT()){
					isHornSAT = false;
				}
				if(!cAux.is2SAT()){
					is2SAT = false;
				}
				if(!dontChangeNSAT){
					if(!cAux.isNSAT()){
						isNSAT = false;
					}else{
						isNSAT = true;
						dontChangeNSAT = true;
					}
				}
				aux.clear();
			}else{
				aux.add(new Literal(s));
			}
		}
		clauses.add(new Clause((ArrayList<Literal>) aux.clone()));
		scanner.close();
	}
	
	@Override
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
	
	/**
	 * Returns an ArrayList<Clause> with the formula
	 * @return
	 */
	public ArrayList<Clause> getFormula() {
		return clauses;
	}
	
	/**
	 * Returns true if Formula is Horn SAT
	 */
	public boolean isHornSAT() {
		return isHornSAT;
	}
	
	/**
	 * Returns true if Formula is 2 SAT
	 */
	public boolean is2SAT() {
		return is2SAT;
	}
	
	/**
	 * Returns true if Formula is N SAT
	 */
	public boolean isNSAT() {
		return isNSAT;
	}
	
	
	
	public static void main (String[] args) {
		Formula f = new Formula("testFiles/prueba4.cnf");
		f.start();
		System.out.println("LENGTH: " + f.getFormula().size());
		System.out.println(f.toString());
		
		Solver.Sat2Solver(f.getFormula());
		
		System.out.println("IS HORN SAT? => " + f.isHornSAT());
		System.out.println("IS 2 SAT? => " + f.is2SAT());
		System.out.println("IS N SAT? => " + f.isNSAT());
	}
	
}
