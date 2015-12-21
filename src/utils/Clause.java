package utils;

import java.util.ArrayList;

/*
 * Stores the variables contained in each Clause of the Formula
 * To be Stored in HashObjects two ArrayLists
 */

public class Clause {
    private ArrayList<Literal> variables;
    private int size,i;

    public Clause(final ArrayList<Literal> a) {
        this.variables = a;
        this.size = a.size();
    }

    public int size() {
        return size;
    }

    public void removeVar(final String var) {
        //remove and place a 0 in place of the variable in the array to hold position
        //also update clause size with the update size of the array
        for (i=0; i<size; i++) {
            if (variables.get(i).equals(var)) {
            	variables.get(i).literal = "";
                size--;
            }
        }
    }

    public void addVar(final String var) {
        for (i=0; i<size; i++) {
            if (variables.get(i).equals("")) {
            	variables.get(i).literal = var;
                size++;
                break;
            }
        }
    }

    public ArrayList<Literal> getLiterals(){
    	return variables;
    }
    
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

    @Override
    public String toString() {
        final String delimiter = " ";
        final int varLen = variables.size();
        final StringBuilder buf = new StringBuilder();
        for (int curVar = 0; curVar < varLen; ++curVar) {
            buf.append(variables.get(curVar));
            buf.append(delimiter);
        }
        return buf.toString();
    }

	public boolean is2SAT() {
		if(size ==2 && !isHornSAT()){
			return true;
		}else{
			return false;
		}
	}

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
	
	public boolean isNSAT() {
		if(size>2){
			return true;
		}else{
			return false;
		}
	}

}
