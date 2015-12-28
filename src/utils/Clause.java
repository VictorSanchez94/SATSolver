package utils;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * Stores the variables contained in each Clause of the Formula
 */

public class Clause {

    private ArrayList<Literal> literals;

    /**
     * Constructor
     */
    public Clause(final ArrayList<Literal> a) {
        this.literals = a;
    }

    /**
     * Return size of the Clause
     */
    public int getSize() {
        return literals.size();
    }

    /**
     * Pre: ---
     * Post: Remove and place an empty String in place of the variable in the array to hold position
     * 		also update clause size with the updated size of the array
     */
    public void removeLiteral(Literal l) {
    	int i = 0;
    	while(!literals.get(i).equals(l)){
    		i++;
    	}
    	literals.remove(i);
    }
    
    /**
     * Pre: ---
     * Post: Place the String 'var' in the Clause also update clause size with the updated size of the array
     */
    public void addLiteral(final String var) {
        for (int i=0; i<literals.size(); i++) {
            if (literals.get(i).equals("")) {
            	literals.get(i).literal = var;
                break;
            }
        }
    }

    /**
     * Return an ArrayList<Literal> with Literal objects contained in the Clause
     */
    public ArrayList<Literal> getLiterals(){
    	return literals;
    }
    
    /**
     * Return the Literal allocated in the position 'index' of the Clause.
     */
    public Literal get(final int index) {
        //*ensure index is formated for array*
        return literals.get(index);
    }

    /*public int lengthOne() {
        return (size == 1) ? findOne() : 0;
    }*/

    /*private int findOne() {
        for(i = 0; i<length; i++) {
            if(variables[i] != 0) {
                break;
            }
        }
        return variables[i];
    }*/

    /**
     * Return true only if Clause is 2 SAT
     */
	public boolean is2SAT() {
		if(literals.size() <=2 && !isHornSAT()){
			return true;
		}else{
			return false;
		}
	}

	/**
     * Return true only if Clause is Horn SAT
     */
	public boolean isHornSAT() {
		int numPositiveVars = 0;
		for(int i=0; i<literals.size(); i++){
			if(!literals.get(i).isNegative){
				numPositiveVars++;
			}
		}
		if(numPositiveVars > 1){
			return false;
		}else{
			return true;
		}
	}
	
	/**
     * Return true only if Clause is N SAT
     */
	public boolean isNSAT() {
		if(literals.size()>2 && !isHornSAT()){
			return true;
		}else{
			return false;
		}
	}
	
    @Override
    public String toString() {
        String buf = "( ";
        Iterator<Literal> it = literals.iterator();
        if(it.hasNext()){
        	buf += it.next().toString();
	        while (it.hasNext()) {
	            buf += " + " + it.next().toString();
	        }
        }
        buf += " )";
        return buf;
    }

	public boolean contains(Literal literal) {
		for(Literal l: literals){
			if(l.equals(literal)){
				return true;
			}
		}
		return false;
	}

}
