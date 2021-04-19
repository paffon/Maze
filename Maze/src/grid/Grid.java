package grid;

import java.util.HashSet;
import java.util.Set;

public class Grid {
    private Square[][] squares;

    private int rows, cols;

    private int squareSize;

    public Grid(int rows, int cols, int squareSize) {
        this.rows = rows;
        this.cols = cols;
        this.squareSize = squareSize;

        initSquares();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getSquareSize() {
        return squareSize;
    }

    public Square getSquare(int r, int c) {
        if(withinBounds(r, 0, rows-1) && withinBounds(c, 0, cols-1))
            return squares[r][c];
        else
            return null;
    }

    private boolean withinBounds(int n, int min, int max) {
        return (n >= min && n <= max);
    }

    private void initSquares() {
        squares = new Square[rows][cols];
        for(int r=0; r<rows; r++) {
            for(int c=0; c<cols; c++) {
                Point newScreenLocation = new Point(c*squareSize, r*squareSize);
                Point newGridLocation = new Point(r, c);
                Square newSquare = new Square(SquareType.FREE, newScreenLocation);
                newSquare.setGridCoordinates(newGridLocation);
                squares[r][c] = newSquare;
            }
        }
    }

    public Set<Square> getNeighbours(int r, int c) {
        Square north = getSquare(r, c-1);
        Square south = getSquare(r, c+1);
        Square east = getSquare(r+1, c);
        Square west = getSquare(r-1, c);

        Set<Square> result = new HashSet<>();
        if(north != null) result.add(north);
        if(south != null) result.add(south);
        if(east != null) result.add(east);
        if(west != null) result.add(west);

//        System.out.println("Returning " + result.size() + " neighbours");
        return result;
    }

    public Set<Square> getNeighbours(Point p) {
        return getNeighbours(p.x, p.y);
    }
    public Set<Square> getNeighbours(Square s) {
        return getNeighbours(s.getGridCoordinates());
    }
}
