package grid;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyFrame extends JFrame {
    MyPanel panel;

    public MyFrame() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);

        String maze = getUserInput("Which maze?", sc);
        if(maze.equals("")) maze = "DEFAULT MAZE";

        String searchType = getUserInput("Which search? (BFS, DFS, AStar)", sc);
        if(searchType.equals("")) searchType = "DEFAULT SEARCH";

        this.setTitle("Solving " + maze + " using " + searchType);

        panel = new MyPanel(maze, searchType);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private String getUserInput(String string, Scanner sc) {
        System.out.println(string);
        return sc.nextLine();
    }
}
