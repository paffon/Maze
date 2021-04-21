package searches;

import grid.Grid;
import grid.Square;
import grid.SquareType;

import java.util.*;

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
                runner = goal;
                pathFound = true;
                return;
            }

            if(! currentSquare.isAgent()) {
                currentSquare.setType(SquareType.VISITED);
            }

            visited.add(currentSquare);

            List<Square> neighbours = grid.getNeighbours(currentSquare);
            Collections.shuffle(neighbours);
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
