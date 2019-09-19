import java.util.*;
public class Console {

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		homeScreen();
	}	*/
	
	public static void homeScreen (UMLEnvironment env) {
		System.out.print("Please input a command: ");
		
		Scanner console = new Scanner (System.in);
		String input = console.next();
		
		while (console.hasNextLine()) {
			checkInput(input, env);
			input = console.next();
		}
		console.close();
	}
	public static void checkInput(String input, UMLEnvironment env) {
			if (input.toLowerCase().equals("add")) {
				add(env);
			} else if (input.toLowerCase().equals("list")) {
				list(env);
			} else if (input.toLowerCase().equals("load")) {
				load(env);
			} else if (input.toLowerCase().equals("save")) {
				save(env);
			} else if (input.toLowerCase().equals("edit")) {
				edit(env);
			} else if (input.toLowerCase().equals("find")) {
				find(env);
			}
			else {
				System.err.println("command not found - retry");
				homeScreen(env);
			}
	}
	
	
	public static void add(UMLEnvironment env) {
		System.out.print("Enter new class name: ");
		Scanner console = new Scanner (System.in);
		String newClass = console.next();
		System.out.print("Add class " + newClass + "? (y/n): ");
		String answer = console.next();
		if (answer.equals("y")) {
			boolean added = AddClass.addClass(env, newClass);
			if(added) {
				System.out.println("Class added " + newClass + ".");
				list(env);
			} else {
				System.out.println(newClass + " is already a class.");
				list(env);
			}
		} else {
			System.out.println("Add cancelled.");
		}
		homeScreen(env);
		console.close();
	}
	
	public static void list(UMLEnvironment env) {
		System.out.print("List of classes: [ ");
		for (UMLItem i : env.getItems()){
			System.out.print(i.getName() + " ");
		}
		System.out.print("]\n");
		
	}
	
	public static void load(UMLEnvironment env) {
		System.out.print("Any unsaved work will be lost, do you want to save? (y/n): ");
		Scanner console = new Scanner (System.in);
		String answer = console.next();
		if (answer.equals("y")) {
			save(env);
		} else {
			System.out.print("Enter file name to load: ");
			answer = console.next();
			//load function
			System.out.println("Load complete.");
			homeScreen(env);
		}
		console.close();
	}
	
	public static void save(UMLEnvironment env) {
		
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
					LocalFile file = new LocalFile(env);
					file.saveFile();
					System.out.println("File has been saved.");
					homeScreen(env);
				} else {
					System.out.println("Save canceled.");
					homeScreen(env);
				}
			} else {
				System.out.println("Save canceled.");
				homeScreen(env); 
			}
			console.close();
			
	}
	public static void edit(UMLEnvironment env) {
		System.out.print("Enter old class name: ");
		Scanner console = new Scanner (System.in);
		String oldClass = console.next();
		System.out.print("Enter new class name: ");
		String newClass = console.next();
		System.out.print("Change " + oldClass + " to " + newClass + "? (y/n): ");
		String answer = console.next();
		if (answer.equals("y")) {
			boolean edited = AddClass.editItem(env, oldClass, newClass);
			if(edited) {
				System.out.println("Class " + oldClass + " changed to " + newClass + ".");
				list(env);
			} else {
				list(env);
			}
		} else {
			System.out.println("Add cancelled.");
		}
		homeScreen(env);
		console.close();
	}
	
	public static void find(UMLEnvironment env) {
		System.out.print("Enter class name to find: ");
		Scanner console = new Scanner (System.in);
		String name = console.next();
		for (UMLItem i : env.getItems()){
			if(i.getName().toLowerCase().equals(name.toLowerCase())) {
				System.out.println("Class " + name + " exists.");
				homeScreen(env);
				console.close();
				return;
			}
		}
		System.out.println("Class does not exist.");
		homeScreen(env);
		console.close();
	}
}
