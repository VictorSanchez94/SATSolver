package probes;

import java.util.Scanner;

public class Probes {
	public static void main (String[] args) {
		Scanner s = new Scanner("(x+y)*(y+-x)\n*(z+z)");
		s.useDelimiter("[\\(\\)\\+]");
		System.out.println(s.next());
		System.out.println(s.next());
		System.out.println(s.next());
		System.out.println(s.next());
		System.out.println(s.next());
	}
}
