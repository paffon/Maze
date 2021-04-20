package grid;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Which maze?");
            String maze = sc.nextLine();

            System.out.println("Which search? (BFS, DFS, AStar)");
            String searchType = sc.nextLine();

            new MyFrame(maze, searchType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}