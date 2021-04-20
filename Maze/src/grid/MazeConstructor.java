package grid;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MazeConstructor {
    String path="";
    List<Point> agents;
    List<Point> goals;

    public MazeConstructor() {
        agents = new LinkedList<>();
        goals = new LinkedList<>();
    }

    public char[][] constructMazeFromTextFile(String path) throws FileNotFoundException {
        File text = new File(path);
        Scanner sc = new Scanner(text);

        List<String> lines = new LinkedList<>();
        while(sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }

        int rows = lines.size();
        int cols = lines.get(0).length();

        char[][] result = new char[rows][cols];
        Set<Character> characterSet = new HashSet<>();
        for(int r=0; r<rows; r++) {
            String line = lines.get(r);
            for(int c=0; c<line.length(); c++) {
                char ch = line.charAt(c);
                characterSet.add(ch);
                result[r][c] = ch;
                switch(ch) {
                    case 'A':
                        agents.add(new Point(r, c));
                        break;
                    case 'G':
                        goals.add(new Point(r,c));
                        break;
                    default:
                        break;
                }

            }
        }

        return result;
    }

    public char[][] randomMaze(int rows, int cols) {
        char[][] result = new char[rows][cols];

        for(int r=0; r<rows; r++) {
            for(int c=0; c<cols; c++) {
                char newChar;
                if(Math.random() < 0.65) {
                    newChar = ' ';
                }
                else {
                    newChar = '#';
                }
                result[r][c] = newChar;
            }
        }
        int agentX = new Random().nextInt(rows);
        int agentY = new Random().nextInt(cols);
        int goalX = new Random().nextInt(rows);
        int goalY = new Random().nextInt(cols);

        while(agentX == goalX && agentY == goalY) {
            goalX = new Random().nextInt(rows);
            goalY = new Random().nextInt(cols);
        }

        result[agentX][agentY] = 'A';
        result[goalX][goalY] = 'G';

        agents.add(new Point(agentX, agentY));
        goals.add(new Point(goalX, goalY));

        return result;
    }
}
