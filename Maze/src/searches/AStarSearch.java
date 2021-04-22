package searches;

import grid.Grid;
import grid.Square;
import grid.SquareType;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AStarSearch extends Search {
    PriorityQueue<Square> pq;

    public AStarSearch(Grid grid, Square originSquare, Square goalSquare) {
        super(grid, originSquare, goalSquare);

        pq = new PriorityQueue<Square>(rows * cols,
                Comparator.comparing(x -> x.distance + heuristic(x)));
        pq.add(origin);
    }

    public double heuristic(Square square) {
        return Math.pow(euclideanDistance(square, goal), 2); // Squaring the heuristic to give it more weight
    }

    private double euclideanDistance(Square s1, Square s2) {
        double deltaX = Math.pow(s1.getGridX() - s2.getGridX(), 2);
        double deltaY = Math.pow(s1.getGridY() - s2.getGridY(), 2);
        return Math.sqrt(deltaX + deltaY);
    }

    @Override
    public void next() {
        if(!pq.isEmpty()) {
            // still something left to explore
            Square currentSquare = pq.poll();
            if(currentSquare.equals(goal)) {
                currentSquare.setType(SquareType.GOAL_FOUND);
                System.out.println("Found a path");
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
            for(Square neighbour : neighbours) {
                if(! (neighbour.isWall() || visited.contains(neighbour) || pq.contains(neighbour) ) ) {
                    neighbour.distance = currentSquare.distance + 1;
                    neighbour.setType(SquareType.TO_VISIT);
                    pq.add(neighbour);
                }
            }
        }
        else {
            // pq is empty
            System.out.println("Path DOESN'T EXIST");
            pathMightExist = false;
        }
    }
}