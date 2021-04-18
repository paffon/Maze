package grid;

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
        return squares[r][c];
    }

    private void initSquares() {
        squares = new Square[rows][cols];
        for(int r=0; r<rows; r++) {
            for(int c=0; c<cols; c++) {
                Point newLocation = new Point(c*squareSize, r*squareSize);
                Square newSquare = new Square(SquareType.FREE, newLocation);
                squares[r][c] = newSquare;
            }
        }
    }
}
