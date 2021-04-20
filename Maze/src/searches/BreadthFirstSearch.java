package searches;

import grid.Grid;
import grid.Square;
import grid.SquareType;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSearch extends Search{
    private Queue<Square> q;

    public BreadthFirstSearch(Grid grid, Square agentSquare, Square goalSquare) {
        super(grid, agentSquare, goalSquare);

        q = new LinkedList<>();
        q.add(agent);
    }

    @Override
    public void next() {
        if(!q.isEmpty()) { // still something left to explore
            Square currentSquare = q.poll();
            if(currentSquare.equals(goal)) {
                currentSquare.setType(SquareType.GOAL_FOUND);
                System.out.println("Found a path");
                System.out.println("distance = " + currentSquare.distance);
                pathFound = true;
                return;
            }

            if(! currentSquare.isAgent()) {
                currentSquare.setType(SquareType.VISITED);
            }

            visited.add(currentSquare);

            Set<Square> neighbours = grid.getNeighbours(currentSquare);
            for(Square neighbour : neighbours) {
                if(! (neighbour.isWall() || visited.contains(neighbour) || q.contains(neighbour) ) ) {
                    neighbour.distance = currentSquare.distance + 1;
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
}
