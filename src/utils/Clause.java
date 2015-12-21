package utils;

/*
 * Stores the variables contained in each Clause of the Formula
 * To be Stored in HashObjects two ArrayLists
 */

public class Clause {
    private String variables[];
    private int size,i;
    final private int length;

    public Clause(final String a[]) {
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
                variables[i] = "";
                size--;
            }
        }
    }

    public void addVar(final String var) {
        for (i=0; i<length; i++) {
            if (variables[i].equals("")) {
                variables[i] = var;
                size++;
                break;
            }
        }
    }

    public String get(final int index) {
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
		if(length ==2 && !isHorn()){
			return true;
		}else{
			return false;
		}
	}

	private boolean isHorn() {
		
		return false;
	}
	
	private boolean isNSAT() {
		if(length>2){
			return true;
		}else{
			return false;
		}
	}
	
	
	public String[] getVariables() {
		return variables;
	}

}
