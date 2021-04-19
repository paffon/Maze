package searches;

import grid.Grid;
import grid.Square;
import grid.SquareType;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class DepthFirstSearch {
    private Square agent, goal;
    private int rows, cols;
    private Grid grid;
    private Set<Square> visited;
    private Stack<Square> stack;
    private boolean pathMightExist;
    private boolean pathFound;

    public DepthFirstSearch(Grid grid, Square agentSquare, Square goalSquare) {
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

        stack = new Stack<>();
        visited = new HashSet<>();

        stack.add(agent);

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

    public void next() {
        if(!stack.isEmpty()) { // still something left to explore
            Square currentSquare = stack.pop();
            if(currentSquare.equals(goal)) {
                currentSquare.setType(SquareType.GOAL_FOUND);
                System.out.println("Found a path");
                pathFound = true;
                return;
            }

            if(! currentSquare.isAgent()) {
                currentSquare.setType(SquareType.VISITED);
            }

            visited.add(currentSquare);

            Set<Square> neighbours = grid.getNeighbours(currentSquare);
            for(Square neighbour : neighbours) {
                if(! (neighbour.isWall() || visited.contains(neighbour) || stack.contains(neighbour) ) ) {
                    neighbour.setType(SquareType.TO_VISIT);
                    stack.add(neighbour);
                }
            }
        }
        else {
            System.out.println("Path DOESN'T EXIST");
            pathMightExist = false;
        }
    }
}
