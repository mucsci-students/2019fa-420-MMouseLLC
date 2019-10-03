package org.mmouse.utility;

import java.util.Scanner;

public class ReplScanner {
	private static Scanner repl = null;
	
	public static void initialize() {
		if (repl == null) {
			repl = new Scanner(System.in);
		} else {
			throw new RuntimeException("This can only be initialized once");
		}
	}
	
	public static Scanner getInstance() {
		return repl;
	}
}
