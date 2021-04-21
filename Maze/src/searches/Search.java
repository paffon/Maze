package searches;

import grid.Grid;
import grid.Square;
import grid.SquareType;

import java.util.*;

public abstract class Search {
    protected Square agent, goal, runner;
    protected int rows, cols;
    protected Grid grid;
    protected Set<Square> visited;
    protected boolean pathMightExist;
    protected boolean pathFound;
    protected boolean pathReconstructed;

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

    public void reconstructPath() {
        if(!isSolved()) {
            // There is no path to reconstruct.
            return;
        }

        if(runner == null || runner.equals(agent)) {
            pathReconstructed = true;
        }
        else {
            if(!runner.equals(goal)) runner.setType(SquareType.BEST_PATH);
            runner = leastDistanceNeighbour(runner);
        }
    }

    protected Square leastDistanceNeighbour(Square runner) {
        List<Square> neighbours = grid.getNeighbours(runner);
        if(neighbours.isEmpty()) return null; // no neighbours found

        Square bestNeighbour = null;
        double minDistance = Double.MAX_VALUE;

        for(Square neighbour : neighbours) {
            if(neighbour.distance < minDistance && neighbour.type == SquareType.VISITED) {
                minDistance = neighbour.distance;
                bestNeighbour = neighbour;
            }
        }

        return bestNeighbour;
    }

    public abstract void next();


}
