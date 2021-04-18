package grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel implements ActionListener {

    class Point {
        int x;
        int y;
        boolean marked;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
            this.marked = false;
        }
    }

    int PANEL_WIDTH;
    int PANEL_HEIGHT;
    int rows;
    int cols;
    int tileSize;
    int r = 0;
    int c = 0;
    Point[][] grid;
    Timer timer;

    public MyPanel(int rows, int cols, int tileSize) {
        this.rows = rows;
        this.cols = cols;
        this.tileSize = tileSize;

        this.PANEL_WIDTH = cols * tileSize;
        this.PANEL_HEIGHT = rows * tileSize;

        this.grid = initializeGrid(rows, cols, tileSize);

        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        timer = new Timer(50, this);
        timer.start();
    }

    private Point[][] initializeGrid(int rows, int cols, int tileSize) {
        Point[][] grid = new Point[rows][cols];

        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                grid[r][c] = new Point(c * tileSize, r * tileSize);
            }
        }

        return grid;
    }

    public void paint(Graphics g) {
        super.paint(g); // paint background

        Graphics2D g2D = (Graphics2D) g;

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                Point point = grid[row][col];

                if(row == r && col == c) {
                    g2D.setColor(Color.RED);
                    g2D.fillRect(point.x, point.y, tileSize - 2, tileSize - 2);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        grid[r][c].marked = true;

        if(r == rows - 1 && c == cols - 1) {
            grid = initializeGrid(rows, cols, tileSize);
            r = 0;
            c = 0;
        }
        else if(c == cols - 1) {
            c = 0;
            r++;
        }
        else {
            c++;
        }

        repaint();
    }
}
