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

    public char[][] proceduralRandomMaze(int rows, int cols) {
        char[][] result = new char[rows][cols];

        for(int r=0; r<rows; r++) {
            for(int c=0; c<cols; c++) {
                result[r][c] = '#';
            }
        }

        ArrayList<Point> q = new ArrayList<>();
        q.add(new Point(new Random().nextInt(rows), new Random().nextInt(cols)));
        Set<Point> visited = new HashSet<>();

        while(!q.isEmpty()) {
            Point currentPoint = q.get(0);
            q.remove(currentPoint);
            visited.add(currentPoint);

            Set<Point> neighbours = getNeighbours(rows, cols, currentPoint);
            Collections.shuffle(q);
            for(Point neighbour: neighbours) {
                if(!visited.contains(neighbour) && !q.contains(neighbour)) {
                    if( eligible(neighbour, result) ) {
                        result[neighbour.x][neighbour.y] = ' ';
                        q.add(neighbour);
                    }
                }
            }
        }

        // Add agent and goal

        Point agentPosition = findFreePosition(result, 0, 0, 1, 1);
        result[agentPosition.x][agentPosition.y] = 'A';
        Point goalPosition  = findFreePosition(result, rows-1, cols-1, -1, -1);
        result[goalPosition.x][goalPosition.y] = 'G';

        agents.add(agentPosition);
        goals.add(goalPosition);

        return result;
    }

    // find a free position in the grid, from a starting position (x,y) and going in a direction by some deltas
    private Point findFreePosition(char[][] chars, int rStart, int cStart, int delta_r, int delta_c) {
        int rows = chars.length;
        int cols = chars[0].length;

        // set limit to the search
        int rStop, cStop;
        if(rStart == 0) rStop = rows;
        else rStop = -1;
        if(cStart == 0) cStop = cols;
        else cStop = -1;

        for(int r=rStart; r!=rStop; r+=delta_r) {
            for(int c=cStart; c!=cStop; c+=delta_c) {
                char currentChar = chars[r][c];
                if(currentChar == ' ') return new Point(r, c);
            }
        }

        return null;
    }

    private boolean eligible(Point point, char[][] chars) {
        double probability = 0.1; // higher number [0,1] --> more spaces in the maze
        int nearbySpacesCounter = countNeighboursOfType(point, ' ', 1, chars);
        int limit = 2;
        if(Math.random() > probability) limit = 1;
        return nearbySpacesCounter <= limit;
    }

    private int countNeighboursOfType(Point point, char c, int maxNumberOfNeighboursOfTypeC, char[][] chars) {
        int x = point.x;
        int y = point.y;

        Set<Point> neighbours = getNeighbours(chars.length, chars[0].length, point);

        int counter = 0;
        for(Point neighbour: neighbours) {
            char neighbouringChar = chars[neighbour.x][neighbour.y];
            if(neighbouringChar == ' ') {
                counter++;
            }
        }

        return counter;
    }

    private Set<Point> getNeighbours(int rows, int cols, Point currentPoint) {
        int x = currentPoint.x;
        int y = currentPoint.y;

        Set<Point> result = new HashSet<>();

        if(x>0) result.add(new Point(x-1, y));
        if(x<rows-1) result.add(new Point(x+1, y));
        if(y>0) result.add(new Point(x, y-1));
        if(y<cols-1) result.add(new Point(x, y+1));

        return result;
    }
}
