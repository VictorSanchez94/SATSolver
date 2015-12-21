package utils;

/*
 * Stores the variables contained in each Clause of the Formula
 * To be Stored in HashObjects two ArrayLists
 */

public class Clause {
    private Literal[] variables;
    private int size,i;
    final private int length;

    public Clause(final Literal[] a) {
        this.variables = a;
        this.length = a.length;
        this.size = a.length;
    }

    public int size() {
        return size;
    }

    public int actualSize() {
        return length;
    }

    public void removeVar(final String var) {
        //remove and place a 0 in place of the variable in the array to hold position
        //also update clause size with the update size of the array
        for (i=0; i<length; i++) {
            if (variables[i].equals(var)) {
                variables[i].literal = "";
                size--;
            }
        }
    }

    public void addVar(final String var) {
        for (i=0; i<length; i++) {
            if (variables[i].equals("")) {
                variables[i].literal = var;
                size++;
                break;
            }
        }
    }

    public Literal[] getLiterals(){
    	return variables;
    }
    
    public Literal get(final int index) {
        //*ensure index is formated for array*
        return variables[index];
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
        final int varLen = variables.length;
        final StringBuilder buf = new StringBuilder();
        for (int curVar = 0; curVar < varLen; ++curVar) {
            buf.append(variables[curVar]);
            buf.append(delimiter);
        }
        return buf.toString();
    }

	public boolean is2SAT() {
		if(length ==2 && !isHornSAT()){
			return true;
		}else{
			return false;
		}
	}

	public boolean isHornSAT() {
		int numPositiveVars = 0;
		for(int i=0; i<length; i++){
			if(!variables[i].literal.contains("-")){
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
		if(length>2){
			return true;
		}else{
			return false;
		}
	}

}
