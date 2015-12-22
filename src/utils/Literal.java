package utils;

import java.io.Serializable;

public class Literal implements Serializable{

	public String literal;
	public boolean isNegative;


	public Literal (String var) {
		if(var.contains("-")){
			this.isNegative = true;
			this.literal = var.substring(1,var.length());
		}else{
			this.isNegative = false;
			this.literal = var;
		}
	}
	
	public Literal (String literal, boolean isNegative) {
		this.literal = literal;
		this.isNegative = isNegative;
	}
	
	public String toString() {
		if(isNegative){
			return "-" + literal;
		}else{
			return "" + literal;
		}
	}

}
