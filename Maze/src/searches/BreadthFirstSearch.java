package searches;

import grid.Grid;
import grid.Point;
import grid.Square;
import grid.SquareType;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSearch {
    private Square agent, goal;
    private int rows, cols;
    private Grid grid;
    private Set<Square> visited;
    private Queue<Square> q;
    private boolean pathMightExist;
    private boolean pathFound;

    public BreadthFirstSearch(Grid grid, Square agentSquare, Square goalSquare) {
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

        q = new LinkedList<>();
        visited = new HashSet<>();

        q.add(agent);

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

    public void next() {
        if(!q.isEmpty()) { // still something left to explore
            Square currentSquare = q.poll();
            if(currentSquare.equals(goal)) {
                currentSquare.setType(SquareType.GOAL_FOUND);
                System.out.println("Found a path");
                pathFound = true;
            }

            currentSquare.setType(SquareType.VISITED);
            visited.add(currentSquare);

            Set<Square> neighbours = grid.getNeighbours(currentSquare);
            for(Square neighbour : neighbours) {
                if(! (neighbour.isWall() || visited.contains(neighbour) || q.contains(neighbour) ) ) {
                    System.out.println("adding neighbour " + neighbour.getGridCoordinates() + " ("+neighbour.type+") by " + currentSquare.getGridCoordinates() + "("+currentSquare.type+")");
                    neighbour.setType(SquareType.TO_VISIT);
                    q.add(neighbour);
                }
            }
        }
        else {
            System.out.println("Path DOESN'T EXIST");
            pathMightExist = false;
        }
    }

    public boolean foundSolution() {
        return pathFound;
    }

    public boolean solutionDoesNotExist() {
        return !pathMightExist;
    }
}
