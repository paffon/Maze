package grid;

import searches.AStarSearch;
import searches.BreadthFirstSearch;
import searches.DepthFirstSearch;
import searches.Search;

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
    Search search;
    private final int squareSize = 20;

    public MyPanel(String mazeName, String searchKind) throws FileNotFoundException {
        MazeConstructor mazeConstructor = new MazeConstructor();
        char[][] mazeAsGridOfChars;
        if(mazeName.equals("random")) {
            mazeAsGridOfChars = mazeConstructor.randomMaze(25,25);
        }
        else {
            try {
                mazeAsGridOfChars = mazeConstructor.constructMazeFromTextFile(mazeName + ".txt");
            } catch (FileNotFoundException e) {
                System.out.println("Maze name <<<" + mazeName + ">>> Not found.");
                mazeName = "maze4";
                mazeAsGridOfChars = mazeConstructor.constructMazeFromTextFile(mazeName+".txt");
            }
        }
        grid = new Grid(mazeAsGridOfChars);
        Square origin = grid.getSquare(mazeConstructor.agents.get(0));
        Square goal = grid.getSquare(mazeConstructor.goals.get(0));

        switch (searchKind) {
            case "BFS":
                search = new BreadthFirstSearch(grid, origin, goal);
                break;
            case "DFS":
                search = new DepthFirstSearch(grid, origin, goal);
                break;
            default:
                search = new AStarSearch(grid, origin, goal);
                searchKind = "AStar";
                break;
        }

        System.out.println("Solving maze '"+mazeName+"' with '" + searchKind + "' search...");

        int rows = grid.getRows();
        int cols = grid.getCols();

        this.PANEL_WIDTH = cols * squareSize;
        this.PANEL_HEIGHT = rows * squareSize;

        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);

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

                int margin = 0; // margin between squares

                g2D.fillRect(x, y, squareSize - margin, squareSize - margin);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // the actions to be performed between frames

        if(search.foundSolution() || search.solutionDoesNotExist())
            timer.stop();
        else {
            search.next();
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
