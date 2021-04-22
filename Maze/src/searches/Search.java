package searches;

import grid.Grid;
import grid.Square;
import grid.SquareType;

import java.util.*;

public abstract class Search {
    protected Square origin, goal, backRunner;
    protected int rows, cols;
    protected Grid grid;
    protected Set<Square> visited;
    protected boolean pathMightExist;
    protected boolean pathFound;
    protected boolean pathReconstructed;

    public Search(Grid grid, Square originSquare, Square goalSquare) {
        try {
            assertPointNotIsNotWall(originSquare, "originSquare");
            assertPointNotIsNotWall(goalSquare, "goalSquare");
        } catch (SquareIsWallException | SquareDoesNotExistsException e) {
            e.printStackTrace();
        }

        this.origin = originSquare;
        this.goal = goalSquare;

        this.origin.setType(SquareType.ORIGIN);
        this.goal.setType(SquareType.GOAL);

        this.rows = grid.getRows();
        this.cols = grid.getCols();
        this.grid = grid;

        visited = new HashSet<>();

        pathMightExist = true;
        pathFound = false;
        pathReconstructed = false;
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

    public boolean isSolved() {
        return pathFound;
    }

    public boolean solutionDoesNotExist() {
        return !pathMightExist;
    }

    public boolean backtrackReconstructed() {
        return pathReconstructed;
    }

    // Once a path is found,
    // this will backtrack and find the shortest (known) path back to the origin
    public void reconstructPath() {
        if(!isSolved()) {
            // There is no path to reconstruct.
            return;
        }

        if(backRunner == null || backRunner.equals(origin)) {
            pathReconstructed = true;
        }
        else {
            if(!backRunner.equals(goal)) backRunner.setType(SquareType.BEST_PATH);
            backRunner = minDistanceNeighbour(backRunner);
        }
    }

    protected Square minDistanceNeighbour(Square runner) {
        List<Square> neighbours = grid.getNeighbours(runner);
        if(neighbours.isEmpty()) return null; // no neighbours found

        Square minDistanceNeighbour = null;
        double minDistance = Double.MAX_VALUE;

        for(Square neighbour : neighbours) {
            if(neighbour.distance < minDistance && neighbour.type == SquareType.VISITED) {
                minDistance = neighbour.distance;
                minDistanceNeighbour = neighbour;
            }
            // if one of the neighbours is the origin, no need to continue
            if(neighbour.type == SquareType.ORIGIN) return null;
        }

        return minDistanceNeighbour;
    }

    // Every extension must include a search method
    public abstract void next();


}
