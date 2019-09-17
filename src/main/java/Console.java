import java.util.*;
public class Console {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		homeScreen();
	}	
	
	public static void homeScreen () {
		System.out.print("Please input a command: ");
		
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
				homeScreen();
			}
	}
	
	
	public static void add() {
		System.out.print("Enter new class name: ");
		Scanner console = new Scanner (System.in);
		String newClass = console.next();
		System.out.print("Add class " + newClass + "? (y/n): ");
		String answer = console.next();
		if (answer.equals("y")) {
			//add class function
			System.out.println("Class added " + newClass + ".");
		} else {
			System.out.println("Add canceled.");
		}
		homeScreen();
	}
	
	public static void list() {
		System.out.println("list of classes");
	}
	
	public static void load() {
		System.out.print("Any unsaved work will be lost, do you want to save? (y/n): ");
		Scanner console = new Scanner (System.in);
		String answer = console.next();
		if (answer.equals("y")) {
			save();
		} else {
			System.out.print("Enter file name to load: ");
			answer = console.next();
			//load function
			System.out.println("Load complete.");
			homeScreen();
		}
	}
	
	public static void save() {
		System.out.print("Save current work? (y/n): ");
		Scanner console = new Scanner (System.in);
		String answer = console.next();
			if (answer.equals("y")) {
				System.out.print("Please enter file name: ");
				answer = console.next();
				//need if/else checking file name exists ??
				System.out.print("Use filename: " + answer + "? (y/n): ");
				answer = console.next();
				if (answer.equals("y")) {
					//save function
					System.out.println("File has been saved.");
					homeScreen();
				} else {
					System.out.println("Save canceled.");
					homeScreen();
				}
			} else {
				System.out.println("Save canceled.");
				homeScreen();
			}
			
			
	}

}
