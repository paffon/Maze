package searches;

import grid.Grid;
import grid.Square;
import grid.SquareType;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearch extends Search{
    private Stack<Square> stack;

    public DepthFirstSearch(Grid grid, Square originSquare, Square goalSquare) {
        super(grid, originSquare, goalSquare);
        stack = new Stack<>();
        stack.add(origin);
    }


    @Override
    public void next() {
        if(!stack.isEmpty()) { // still something left to explore
            Square currentSquare = stack.pop();
            if(currentSquare.equals(goal)) {
                currentSquare.setType(SquareType.GOAL_FOUND);
                System.out.println("Found a path.");
                System.out.println("distance = " + currentSquare.distance);
                backRunner = goal;
                pathFound = true;
                return;
            }

            if(! currentSquare.isOrigin()) {
                currentSquare.setType(SquareType.VISITED);
            }

            visited.add(currentSquare);

            List<Square> neighbours = grid.getNeighbours(currentSquare);
            Collections.shuffle(neighbours);
            for(Square neighbour : neighbours) {
                if(! (neighbour.isWall() || visited.contains(neighbour) || stack.contains(neighbour) ) ) {
                    if(neighbour.type == SquareType.TO_VISIT) {
                        neighbour.distance = Math.min(neighbour.distance, currentSquare.distance + 1);
                    }
                    else {
                        neighbour.distance = currentSquare.distance + 1;
                    }
                    neighbour.setType(SquareType.TO_VISIT);
                    stack.add(neighbour);
                }
            }
        }
        else {
            System.out.println("Path doesn't exit.");
            pathMightExist = false;
        }
    }
}
