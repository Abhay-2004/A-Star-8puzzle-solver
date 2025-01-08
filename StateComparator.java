package edu.iastate.cs472.proj1;

import java.util.Comparator;

/**
 *
 * @author Abhay Prasanna Rao
 *
 */

/**
 * This method compares two states in the lexicographical order of the board configuration. 
 * The 3X3 array representing each board configuration is converted into a sequence of nine 
 * digits starting at the 0th row, and within each row, at the 0th column.  For example, the 
 * two states
 * <p>
 * 	   2 0 3        2 8 1 
 *     1 8 4        7 5 3 
 *     7 6 5        6 0 4 
 * <p>
 * are converted into the sequences <2,0,3,1,8,4,7,6,5>, and <2,8,1,7,5,3,6,0,4>, respectively. 
 * By definition the first state is less than the second one.  
 * <p>
 * The comparator will be used for maintaining the CLOSED list used in the A* algorithm. 
 */
public class StateComparator implements Comparator<State>
{
	@Override
	public int compare(State s1, State s2)
	{
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				int board1 = s1.board[row][col];
				int board2 = s2.board[row][col];

				if (board1 != board2) {
					return board1 - board2;
				}
			}
		}
		return 0; // Both states have the same board configuration
	}  		
}
