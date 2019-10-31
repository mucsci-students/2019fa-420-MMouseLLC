package utility;

import data.UMLEnvironment;

/**
 * The Interface IConsole.
 */
public interface IConsole {
	
	/**
	 * Runs the Console.
	 */
	public void run();
	
	/**
	 * Check input.
	 *
	 * @param input the input
	 */
	public void checkInput(String[] input);
	
	/**
	 * List.
	 */
	public void list();
	
	/**
	 * Gets the UML environment.
	 *
	 * @return the UML environment
	 */
	public UMLEnvironment getUMLEnvironment();

}
