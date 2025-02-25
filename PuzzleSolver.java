package edu.iastate.cs472.proj1;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  
 * @author Abhay Prasanna Rao
 *
 */

public class PuzzleSolver 
{
	/**
	 *  Read an initial state from the input file.  Solve the eight puzzle three times:
	 *  
	 *      1) The first solution allows single moves only and uses the heuristic based on the number
	 *         of mismatched tiles. 
	 *         
	 *      2) The second solution also allows single moves only but uses the heuristic based on the 
	 *         Manhattan distance. 
	 *         
	 *      3) The third solution allows single and double moves and uses the admissible heuristic 
	 *         designed by yourself.  
	 *         
	 *  Each solution is printed out as a sequence of states, generated by single/double moves, from the 
	 *  initial state to the goal state. If no solution exists, report it.  
	 * 
	 * @param args
	 * @throws FileNotFoundException if the input file does not exist 
	 * @throws IllegalArgumentException if the initial state from the file is not in the correct format
	 */
	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException
	{	
		// Read an initial board configuration from a file.  

		// Call EightPuzzle.solve8puzzle() to solve the puzzle. 
		
		// You may make it interactive by repeatedly accepting puzzle files and print out 
		// solutions.  (No extra credit but good for debugging and for the user.)
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Enter the puzzle file name (or 'quit' to exit): ");
			String filename = scanner.nextLine().trim();

			if (filename.equalsIgnoreCase("quit")) {
				break;
			}

			try {
				System.out.println("Attempting to read file: " + filename);
				State initialState = new State(filename);
				System.out.println("\nSolving puzzle from file: " + filename);
				System.out.println("Initial Board Configuration:");
				System.out.println(initialState);

				System.out.println("Checking solvability...");
				boolean isSolvable = initialState.solvable();
				System.out.println("Is solvable: " + isSolvable);

				if (isSolvable) {
					String solution = EightPuzzle.solve8Puzzle(initialState);
					System.out.println(solution);
				} else {
					System.out.println("No solution exists for this initial state.");
				}
			} catch (FileNotFoundException e) {
				System.err.println("Error: The file " + filename + " was not found.");
			} catch (IllegalArgumentException e) {
				System.err.println("Error: " + e.getMessage());
			} catch (Exception e) {
				System.err.println("An unexpected error occurred while processing the file:");
				e.printStackTrace();
			}

			System.out.println("\n----------------------------------------\n");
		}

		scanner.close();
		System.out.println("Have a Nice Day! Thank You for using my program!");
	}


}
