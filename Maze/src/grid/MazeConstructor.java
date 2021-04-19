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

    public char[][] construct(String path) throws FileNotFoundException {
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
}
