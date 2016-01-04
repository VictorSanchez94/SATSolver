package satSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @authors Alberto Sabater Bailon, 546297
 * 			Victor Sanchez Ballabriga, 602665
 */
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
	public Formula (String input, boolean isFile) {
		try {
			if(isFile){
				scanner = new Scanner(new File(input));
			}else{
				scanner = new Scanner(input);
			}
			scanner.useDelimiter("([\\)\n\r]|\\(|\\)|\\+)+");
		} catch (FileNotFoundException e) {
			System.err.println("ERROR. El fichero indicado no existe.");
		}
	}
	
	@SuppressWarnings("unchecked")
	public Formula (Formula f) {
		this.clauses = (ArrayList<Clause>) f.getFormula().clone();
		this.isHornSAT = f.isHornSAT();
		this.is2SAT = f.is2SAT();
		this.isNSAT = f.isNSAT();
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
			s = scanner.next().trim();
//			System.out.println(s);
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
	
	public int size() {
		return clauses.size();
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
	
	/**
	 * Remove Clause c of the Formula formula 
	 */
	public void removeClause (Clause c) {
		clauses.remove(c);
	}

	public void addClause(Clause c) {
		clauses.add(c);
	}
	
	public ArrayList<Literal> getVariables() {
		Clause list = new Clause(new ArrayList<Literal>());
		for(Clause cIt : clauses){
			for (Literal lIt : cIt.getLiterals()){
				if(!list.contains(new Literal(lIt.getLiteral()))){
					list.addLiteral(lIt.getLiteral());
				}
			}
		}
		return list.getLiterals();
	}

	public boolean isSatisfiable (Formula f, ArrayList<Literal> vars) {
		Literal l1 = vars.remove(0);
		return isSatisfiable(f, l1, vars);
	}
	
	@SuppressWarnings({ "unchecked"})
	private boolean isSatisfiable (Formula formula, Literal currentVar, ArrayList<Literal> unusedVars) {
		if(formula.size() == 0){
			return true;
		}else if(unusedVars.size() == 0){
			Formula formula2 = new Formula(formula);
			Formula f1 = checkVar(formula, currentVar, true);
			Formula f2 = checkVar(formula2, currentVar, false);
			if(f1.getFormula().isEmpty() || f2.getFormula().isEmpty()){
				return true;
			}else{
				return false;
			}
		}else{
			ArrayList<Literal> unusedVars2 = (ArrayList<Literal>)unusedVars.clone();
			Formula formula2 = new Formula(formula);
			return isSatisfiable(checkVar(formula,currentVar,true), unusedVars.remove(0), unusedVars) || isSatisfiable(checkVar(formula2,currentVar,false), unusedVars2.remove(0), unusedVars2);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Formula checkVar (Formula formula, Literal var, boolean value) {
		if(!value){
			var.setNegative(true);
		}
//		System.out.println(formula.toString() + "  |||  " + var.toString());
		ArrayList<Clause> aux = (ArrayList<Clause>) formula.getFormula().clone();
		for(Clause c : aux){
			if(c.contains(var)){
				formula.removeClause(c);
			}
		}
		return formula;
	}
	
//	public static void main (String[] args) {
//		Formula f = new Formula("testFiles/prueba1.cnf", true);
//		f.start();
////		System.out.println("LENGTH: " + f.getFormula().size());
//		System.out.println(f.toString());
//		System.out.println("VARS: " + f.getVariables());
//		System.out.println(f.toString());
//		
////		boolean satisfactible = Solver.Sat2Solver(f.getFormula());
////		boolean satisf = Solver.hornSATSolver(f);
////		
////		if (satisfactible) {
////			System.out.println("Tiene soluci�n");
////		}
////		else {
////			System.out.println("No tiene soluci�n");
////		}
////		if(satisf){
////			System.out.println("Tiene solucion segun Horn-SAT");
////		}
////		
////		System.out.println("IS HORN SAT? => " + f.isHornSAT());
////		System.out.println("IS 2 SAT? => " + f.is2SAT());
////		System.out.println("IS N SAT? => " + f.isNSAT());
//	}
	
}
