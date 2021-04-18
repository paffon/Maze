package grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MyPanel extends JPanel implements ActionListener {

    int PANEL_WIDTH;
    int PANEL_HEIGHT;
    Timer timer;
    Grid grid;

    public MyPanel(int rows, int cols, int squareSize) {
        this.PANEL_WIDTH = cols * squareSize;
        this.PANEL_HEIGHT = rows * squareSize;

        grid = new Grid(rows, cols, squareSize);

        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);

        timer = new Timer(100, this);
        timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g); // paint background

        Graphics2D g2D = (Graphics2D) g; // for painting shapes etc.

        int rows = grid.getRows();
        int cols = grid.getCols();
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                Square currentSquare = grid.getSquare(r, c);
                int x = currentSquare.getX();
                int y = currentSquare.getY();
                int squareSize = grid.getSquareSize();

                g2D.setColor(currentSquare.getColor());

                int margin = 2; // margin between squares

                g2D.fillRect(x, y, squareSize - margin, squareSize - margin);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // the actions to be performed between frames

        int rows = grid.getRows();
        int cols = grid.getCols();

        int randomRow = new Random().nextInt(rows); // random in [0,rows)
        int randomCol = new Random().nextInt(cols); // random in [0,cols)

        Square randomSquare = grid.getSquare(randomRow, randomCol);

        randomSquare.setNextType();

        repaint(); // this calls paint() for us every time.
    }

//    int PANEL_WIDTH;
//    int PANEL_HEIGHT;
//    Timer timer;
//    int rows;
//    int cols;
//    int tileSize;
//    int r = 0;
//    int c = 0;
//    Point[][] grid;
//
//    public MyPanel(int rows, int cols, int tileSize) {
//        this.PANEL_WIDTH = cols * tileSize;
//        this.PANEL_HEIGHT = rows * tileSize;
//
//        this.rows = rows;
//        this.cols = cols;
//        this.tileSize = tileSize;
//
//        grid = initializeGrid(rows, cols, tileSize);
//
//        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
//        this.setBackground(Color.BLACK);
//
//        timer = new Timer(50, this);
//        timer.start();
//    }
//
//    private Point[][] initializeGrid(int rows, int cols, int tileSize) {
//        Point[][] grid = new Point[rows][cols];
//
//        for(int r = 0; r < rows; r++) {
//            for(int c = 0; c < cols; c++) {
//                grid[r][c] = new Point(c * tileSize, r * tileSize);
//            }
//        }
//
//        return grid;
//    }
//
//    public void paint(Graphics g) {
//        super.paint(g); // paint background
//
//        Graphics2D g2D = (Graphics2D) g;
//
//        for(int row = 0; row < rows; row++) {
//            for(int col = 0; col < cols; col++) {
//                Point point = grid[row][col];
//
//                if(row == r && col == c) {
//                    g2D.setColor(Color.RED);
//                    g2D.fillRect(point.x, point.y, tileSize - 2, tileSize - 2);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        grid[r][c].marked = true;
//
//        if(r == rows - 1 && c == cols - 1) {
//            grid = initializeGrid(rows, cols, tileSize);
//            r = 0;
//            c = 0;
//        }
//        else if(c == cols - 1) {
//            c = 0;
//            r++;
//        }
//        else {
//            c++;
//        }
//
//        repaint();
//    }
}
