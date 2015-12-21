package probes;

import java.util.Scanner;

public class Probes {
	public static void main (String[] args) {
		String in = "2\n(x+y)*(y+-x)\n*(z+z)";
		Scanner s = new Scanner(in);
		System.out.println(in+"\n");
		System.out.println(s.delimiter());
		s.useDelimiter("\\(\\)\\+\\n"+s.delimiter());
		System.out.println(s.delimiter());
		System.out.println(s.next());
		System.out.println(s.next());
		System.out.println(s.next());
		System.out.println(s.next());
		System.out.println(s.next());
		System.out.println(s.next());
	}
}
