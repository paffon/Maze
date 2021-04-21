package grid;

import java.util.*;

public class Grid {
    private Square[][] squares;

    private int rows, cols;

    private int squareSize; // Default square size

    public Grid(int rows, int cols, int squareSize) {
        this.rows = rows;
        this.cols = cols;
        this.squareSize = squareSize;

        initEmptyGrid();
    }

    public Grid(char[][] chars, int squareSize) {
        this.squareSize = squareSize;

        Map<Character, SquareType> types = new HashMap<>();
        types.put('#', SquareType.WALL);
        types.put(' ', SquareType.FREE);
        types.put('A', SquareType.AGENT);
        types.put('G', SquareType.GOAL);

        this.rows = chars.length;
        this.cols = chars[0].length;

        squares = new Square[rows][cols];
        for(int r=0; r<rows; r++) {
            for(int c=0; c<cols; c++) {
                Point newScreenLocation = new Point(c*squareSize, r*squareSize);
                Point newGridLocation = new Point(r, c);
                SquareType newType = types.get(chars[r][c]);
                Square newSquare = new Square(newType, newScreenLocation);
                newSquare.setGridCoordinates(newGridLocation);
                squares[r][c] = newSquare;
            }
        }
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
    public Square getSquare(Point point) {
        return getSquare(point.x, point.y);
    }

    private boolean withinBounds(int n, int min, int max) {
        return (n >= min && n <= max);
    }

    private void initEmptyGrid() {
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

    public List<Square> getNeighbours(int r, int c) {
        Square north = getSquare(r, c-1);
        Square south = getSquare(r, c+1);
        Square east = getSquare(r+1, c);
        Square west = getSquare(r-1, c);

        List<Square> result = new ArrayList<>();
        if(north != null) result.add(north);
        if(south != null) result.add(south);
        if(east != null) result.add(east);
        if(west != null) result.add(west);

        return result;
    }

    public List<Square> getNeighbours(Point p) {
        return getNeighbours(p.x, p.y);
    }
    public List<Square> getNeighbours(Square s) {
        return getNeighbours(s.getGridCoordinates());
    }
}
