package edu.iastate.cs472.proj1;

import java.io.FileNotFoundException;

/**
 *
 * @author Abhay Prasanna Rao
 *
 */

public class EightPuzzle
{
	/**
	 * This static method solves an 8-puzzle with a given initial state using three heuristics. The
	 * first two, allowing single moves only, compare the board configuration with the goal configuration
	 * by the number of mismatched tiles, and by the Manhattan distance, respectively.  The third
	 * heuristic, designed by yourself, allows double moves and must be also admissible.  The goal
	 * configuration set for all puzzles is
	 *
	 * 			1 2 3
	 * 			8   4
	 * 			7 6 5
	 *
	 * @param s0
	 * @return a string specified in the javadoc below
	 */

	public static String solve8Puzzle(State s0) {
;
//		System.out.println("Checking solvability...");
//		System.out.println("Is solvable: " + s0.solvable());

		if (!s0.solvable()) {
			return "No solution exists for the following initial state:\n\n" + s0.toString();
		}

		Heuristic[] h = {Heuristic.TileMismatch, Heuristic.ManhattanDist, Heuristic.DoubleMoveHeuristic};
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < 3; i++) {
			System.out.println("Solving with heuristic: " + h[i]);
			long startTime = System.currentTimeMillis();
			String solution = AStar(s0, h[i]);
			long endTime = System.currentTimeMillis();
			System.out.println("Time taken: " + (endTime - startTime) + " ms");
			result.append(solution);
			if (i < 2) {
				result.append("\n\n");
			}
		}

		return result.toString();
	}


	/**
	 * This method implements the A* algorithm to solve the 8-puzzle with an input initial state s0.
	 * The algorithm implementation is described in Section 3 of the project description.
	 *
	 * Precondition: the puzzle is solvable with the initial state s0.
	 *
	 * @param s0  initial state
	 * @param h   heuristic
	 * @return    solution string
	 */
	public static String AStar(State s0, Heuristic h) {
		OrderedStateList OPEN = new OrderedStateList(h, true);
		OrderedStateList CLOSE = new OrderedStateList(h, false);

		State.heu = h;
		OPEN.addState(s0);
		int iterations = 0;
		while (OPEN.size() > 0) {
			iterations++;

			State s = OPEN.remove();
			CLOSE.addState(s);

			if (s.isGoalState()) {
				String solution = s.numMoves + " moves in total (heuristic: ";
				if (h == Heuristic.TileMismatch) {
					solution += "number of mismatched tiles";
				} else if (h == Heuristic.ManhattanDist) {
					solution += "the Manhattan distance";
				} else if (h == Heuristic.DoubleMoveHeuristic) {
					solution += "double moves allowed";
				}
				solution += ")\n" + solutionPath(s);
				return solution;
			}

			for (Move move : Move.values()) {
				try {
					State successor = s.successorState(move);
					if (OPEN.findState(successor) == null && CLOSE.findState(successor) == null) {
						OPEN.addState(successor);
					} else if (OPEN.findState(successor) != null) {
						State old = OPEN.findState(successor);
						if (old.cost() > successor.cost()) {
							OPEN.removeState(old);
							OPEN.addState(successor);
						}
					} else if (CLOSE.findState(successor) != null) {
						State old = CLOSE.findState(successor);
						if (successor.cost() < old.cost()) {
							CLOSE.removeState(old);
							OPEN.addState(successor);
						}
					}
				} catch (IllegalArgumentException e) {
					// Skip if invalid move
				}
			}
		}

		return "No solution found after " + iterations + " iterations";
	}



	/**
	 * From a goal state, follow the predecessor link to trace all the way back to the initial state.
	 * Meanwhile, generate a string to represent board configurations in the reverse order, with
	 * the initial configuration appearing first. Between every two consecutive configurations
	 * is the move that causes their transition. A blank line separates a move and a configuration.
	 * In the string, the sequence is preceded by the total number of moves and a blank line.
	 *
	 * See Section 6 in the projection description for an example.
	 *
	 * Call the toString() method of the State class.
	 *
	 * @param goal
	 * @return
	 */
	private static String solutionPath(State goal)
	{
		StringBuilder s = new StringBuilder();
		State state = goal;
		while (state != null) {
			if (state.move != null) {
				s.insert(0, "\n" + state.move + "\n\n" + state);
			} else {
				s.insert(0, "\n" + state);
			}
			state = state.predecessor;
		}
		return s.toString();
	}



}
