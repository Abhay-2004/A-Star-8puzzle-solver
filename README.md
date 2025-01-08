# 8-Puzzle Solver

This project implements an A* search algorithm to solve the 8-puzzle problem using three different heuristics.

## H3 Heuristic: computeNumSingleDoubleMoves

The `computeNumSingleDoubleMoves` heuristic is designed to estimate the number of moves (including both single and double moves) required to reach the goal state. This heuristic is used when double moves are allowed in addition to single moves.

### Implementation

```java
private int computeNumSingleDoubleMoves() {
    if (numSingleDoubleMoves >= 0) {
        return numSingleDoubleMoves;
    }
    int count = 0;
    for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 3; col++) {
            int value = board[row][col];
            if (value != 0) {
                int goalRow = (value - 1) / 3;
                int goalCol = (value - 1) % 3;
                int rowDiff = Math.abs(row - goalRow);
                int colDiff = Math.abs(col - goalCol);
                count += Math.max((rowDiff + 1) / 2, (colDiff + 1) / 2);
            }
        }
    }
    numSingleDoubleMoves = count;
    return count;
}
```

### Explanation

This heuristic works as follows:

1. For each tile (excluding the empty tile):
    - Calculate the difference in row and column positions between the current state and the goal state.
    - Determine the minimum number of moves (single or double) required to move the tile to its correct position.
    - Add this number to the total count.

2. The formula `Math.max((rowDiff + 1) / 2, (colDiff + 1) / 2)` is used to calculate the minimum number of moves:
    - `(rowDiff + 1) / 2` represents the number of vertical moves (including double moves).
    - `(colDiff + 1) / 2` represents the number of horizontal moves (including double moves).
    - The maximum of these two values is taken because the tile needs to be moved both vertically and horizontally, and these moves can be done in parallel.

### Admissibility

This heuristic is admissible for the following reasons:

1. It never overestimates the number of moves required to reach the goal state. In fact, it often underestimates because:
    - It doesn't account for potential conflicts between tiles.
    - It assumes that tiles can move freely, which is not always the case due to the constraints of the puzzle.

2. It considers the best-case scenario where double moves can be used optimally. In reality, double moves may not always be possible or optimal.

3. For each tile, it calculates the minimum number of moves required to reach its goal position, ignoring the positions of other tiles. This is always less than or equal to the actual number of moves needed in the presence of other tiles.

4. The heuristic is consistent: the estimated cost from any state to the goal is always less than or equal to the estimated cost from any successor state to the goal, plus the cost of reaching that successor.

By being admissible, this heuristic ensures that the A* algorithm will find the optimal solution (the path with the fewest moves) to the 8-puzzle problem when both single and double moves are allowed.