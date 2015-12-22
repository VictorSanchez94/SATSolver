package utils;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * Stores the variables contained in each Clause of the Formula
 * To be Stored in HashObjects two ArrayLists
 */

public class Clause {
	
    private ArrayList<Literal> variables;
    private int size,i;

    /**
     * Constructor
     */
    public Clause(final ArrayList<Literal> a) {
        this.variables = a;
        this.size = a.size();
    }

    /**
     * Return size of the Clause
     */
    public int getSize() {
        return size;
    }

    /**
     * Pre: ---
     * Post: Remove and place an empty String in place of the variable in the array to hold position
     * 		also update clause size with the updated size of the array
     */
    public void removeVar(final String var) {
        for (i=0; i<size; i++) {
            if (variables.get(i).equals(var)) {
            	variables.get(i).literal = "";
                size--;
            }
        }
    }
    
    /**
     * Pre: ---
     * Post: Place the String 'var' in the Clause also update clause size with the updated size of the array
     */
    public void addVar(final String var) {
        for (i=0; i<size; i++) {
            if (variables.get(i).equals("")) {
            	variables.get(i).literal = var;
                size++;
                break;
            }
        }
    }

    /**
     * Return an ArrayList<Literal> with Literal objects contained in the Clause
     */
    public ArrayList<Literal> getLiterals(){
    	return variables;
    }
    
    /**
     * Return the Literal allocated in the position 'index' of the Clause.
     */
    public Literal get(final int index) {
        //*ensure index is formated for array*
        return variables.get(index);
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
		if(size <=2 && !isHornSAT()){
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
		for(int i=0; i<size; i++){
			if(!variables.get(i).isNegative){
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
		if(size>2){
			return true;
		}else{
			return false;
		}
	}
	
    @Override
    public String toString() {
        String buf = "( ";
        Iterator<Literal> it = variables.iterator();
        if(it.hasNext()){
        	buf += it.next().toString();
	        while (it.hasNext()) {
	            buf += " + " + it.next().toString();
	        }
        }
        buf += " )";
        return buf;
    }

}
