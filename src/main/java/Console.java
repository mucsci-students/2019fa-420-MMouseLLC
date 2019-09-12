import java.util.*;
public class Console {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		homeScreen();
	}	
	
	public static void homeScreen () {
		System.out.println("input command");
		
		Scanner console = new Scanner (System.in);
		String input = console.next();
		
		while (console.hasNextLine()) {
			checkInput(input);
			input = console.next();
		}
	}
	public static void checkInput(String input) {
			if (input.equals("add")) {
				add();
			} else if (input.equals("list")) {
				list();
			} else if (input.equals("load")) {
				load();
			} else if (input.equals("save")) {
				save();
			} else {
				System.out.println("command not found - retry");
			}
	}
	
	
	public static void add() {
		System.out.println("element added");
	}
	
	public static void list() {
		System.out.println("list of classes");
	}
	
	public static void load() {
		System.out.println("load file");
	}
	
	public static void save() {
		System.out.println("Save current work? (y/n)");
		Scanner console = new Scanner (System.in);
		String answer = console.next();
			if (answer.equals("y")) {
				System.out.println("Please enter file name");
				answer = console.next();
				//need if/else checking file name exists
				System.out.println("Use filename: " + answer + "? (y/n)");
				answer = console.next();
				if (answer.equals("y")) {
					//call save function
					System.out.println("File has been saved");
					homeScreen();
				} else {
					System.out.println("Save canceled");
					homeScreen();
				}
			} else {
				System.out.println("Save canceled");
				homeScreen();
			}
			
			
	}

}
