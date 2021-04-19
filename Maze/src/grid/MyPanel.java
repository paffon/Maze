package grid;

import searches.BreadthFirstSearch;

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
    boolean pathFound;
    BreadthFirstSearch bfs;

    public MyPanel(int rows, int cols, int squareSize) {
        this.PANEL_WIDTH = cols * squareSize;
        this.PANEL_HEIGHT = rows * squareSize;

        grid = new Grid(rows, cols, squareSize);
        Square origin = grid.getSquare(0, 0);
        Square goal = grid.getSquare(rows-1, cols-1);
        //Adding a wall in the middle of the grid
        for(int i=0; i<rows; i++) {
            Square s = grid.getSquare(i, cols/2);
            s.setType(SquareType.WALL);
        }
        grid.getSquare(rows/2, cols/2).setType(SquareType.FREE);
        bfs = new BreadthFirstSearch(grid, origin, goal);

        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);

        int fps = 300;
        timer = new Timer(1000/fps, this);
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
                int x = currentSquare.getScreenX();
                int y = currentSquare.getScreenY();
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

        if(bfs.foundSolution() || bfs.solutionDoesNotExist())
            timer.stop();
        else {
            bfs.next();
            repaint(); // this calls paint() for us every time.
        }
    }

    private void changeRandomSquareRandomly() {
        int rows = grid.getRows();
        int cols = grid.getCols();

        int randomRow = new Random().nextInt(rows); // random in [0,rows)
        int randomCol = new Random().nextInt(cols); // random in [0,cols)

        Square randomSquare = grid.getSquare(randomRow, randomCol);

        randomSquare.setNextType();
    }
}
