package searches;

import grid.Grid;
import grid.Point;
import grid.Square;

class SquareCannotBeWallException extends Exception {
    public SquareCannotBeWallException(String errorMessage) {
        super(errorMessage);
    }
}

public class BreadthFirstSearch {
    private Square agent, goal;
    private int rows, cols;
    private Grid grid;

    public BreadthFirstSearch(Grid grid, Square agent, Square goal) {
        try {
            assertPointNotIsNotWall(agent, "origin");
            assertPointNotIsNotWall(goal, "goal");
        } catch (SquareCannotBeWallException e) {
            e.printStackTrace();
        }

        this.agent = agent;
        this.goal = goal;

        this.rows = grid.getRows();
        this.cols = grid.getCols();
        this.grid = grid;
    }

    private void assertPointNotIsNotWall(Square square, String squareName) throws SquareCannotBeWallException {
        if(square.isWall()) {
            String errorMessage = "The square [" +squareName+
                    "] in location "+square.getLocation()+
                    " cannot be a wall.";
            throw new SquareCannotBeWallException(errorMessage);
        }
    }
}
