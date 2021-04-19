package grid;

import searches.BreadthFirstSearch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Random;

public class MyPanel extends JPanel implements ActionListener {

    int PANEL_WIDTH;
    int PANEL_HEIGHT;
    Timer timer;
    Grid grid;
    BreadthFirstSearch bfs;

    public MyPanel(int rows, int cols, int squareSize) throws FileNotFoundException {
        this.PANEL_WIDTH = cols * squareSize;
        this.PANEL_HEIGHT = rows * squareSize;

        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);

        MazeConstructor mz = new MazeConstructor();
        char[][] chars = mz.construct("maze1.txt");
        grid = new Grid(chars);
        Square origin = grid.getSquare(mz.agents.get(0));
        Square goal = grid.getSquare(mz.goals.get(0));
        bfs = new BreadthFirstSearch(grid, origin, goal);



        int fps = 50;
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
