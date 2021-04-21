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

public class MyPanel extends JPanel implements ActionListener {

    int PANEL_WIDTH;
    int PANEL_HEIGHT;
    Timer timer;
    Grid grid;
    Search search;
    private final int squareSize = 3;
    private final int fps = 1000;

    public MyPanel(String mazeName, String searchKind) throws FileNotFoundException {
        // Window setup
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        this.PANEL_WIDTH = (int) (0.9 * screenWidth);
        this.PANEL_HEIGHT = (int) (0.9 * screenHeight);

        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);

        // Maze setup
        MazeConstructor mazeConstructor = new MazeConstructor();
        char[][] mazeAsGridOfChars;
        if(mazeName.equals("random") || mazeName.equals("")) {
            mazeName = "randomized";
            mazeAsGridOfChars = mazeConstructor.proceduralRandomMaze(PANEL_HEIGHT / squareSize,PANEL_WIDTH / squareSize); // Super-size version
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
        grid = new Grid(mazeAsGridOfChars, squareSize);
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

                g2D.setColor(currentSquare.getColor());

                int margin = 0; // margin between squares

                g2D.fillRect(x, y, squareSize - margin, squareSize - margin);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // the actions to be performed between frames

        if(search.isSolved() || search.solutionDoesNotExist()) {
            if (search.backtrackReconstructed()) {
                timer.stop();
            } else {
                search.reconstructPath();
            }
        }
        else {
            search.next();
        }


        repaint(); // this calls paint() for us every time.

    }
}
