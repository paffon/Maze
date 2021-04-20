package searches;

import grid.Grid;
import grid.Square;
import grid.SquareType;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public abstract class Search {
    protected Square agent, goal;
    protected int rows, cols;
    protected Grid grid;
    protected Set<Square> visited;
    protected boolean pathMightExist;
    protected boolean pathFound;

    public Search(Grid grid, Square agentSquare, Square goalSquare) {
        try {
            assertPointNotIsNotWall(agentSquare, "agentSquare");
            assertPointNotIsNotWall(goalSquare, "goalSquare");
        } catch (SquareIsWallException | SquareDoesNotExistsException e) {
            e.printStackTrace();
        }

        this.agent = agentSquare;
        this.goal = goalSquare;

        this.agent.setType(SquareType.AGENT);
        this.goal.setType(SquareType.GOAL);

        this.rows = grid.getRows();
        this.cols = grid.getCols();
        this.grid = grid;

        visited = new HashSet<>();

        pathMightExist = true;
        pathFound = false;
    }

    private void assertPointNotIsNotWall(Square square, String name) throws
            SquareIsWallException, SquareDoesNotExistsException {
        if(square == null) {
            String errorMessage = "The square [" +name+
                    "] doesn't exist in the grid.";
            throw new SquareDoesNotExistsException(errorMessage);
        }
        if(square.isWall()) {
            String errorMessage = "The square [" +name+
                    "] in location "+square.getScreenCoordinates()+
                    " cannot be a wall.";
            throw new SquareIsWallException(errorMessage);
        }
    }

    public boolean foundSolution() {
        return pathFound;
    }

    public boolean solutionDoesNotExist() {
        return !pathMightExist;
    }

    public abstract void next();
}
