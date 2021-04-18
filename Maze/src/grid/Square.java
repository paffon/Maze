package grid;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Square {


    private Point location;
    private Color color;
    public SquareType type;

    public Square(SquareType type, Point location) {
        setType(type);
        this.location = location;
    }

    public void setType(SquareType newType) {
        this.type = newType;
        this.color = colorFromType(newType);
    }

    private Color colorFromType(SquareType type) {
        switch (type) {
            case FREE:
                return Color.black;
            case WALL:
                return Color.gray;
            case AGENT:
                return Color.red;
            case GOAL:
                return Color.orange;
            case TO_VISIT:
                return Color.cyan;
            case VISITED:
                return Color.green;
            case GOAL_FOUND:
                return Color.blue;
            default:
                System.out.println("Color undefined for type " + type + ". Setting to default color.");
                return Color.white;
        }
    }

    public int getX() {
        return location.x;
    }
    public int getY() {
        return location.y;
    }

    public Point getLocation() {
        return location;
    }

    public Color getColor() {
        return color;
    }

    public boolean isWall() {
        return this.type == SquareType.WALL;
    }



    public void setRandomType() {
        SquareType[] types = SquareType.values();
        int size = types.length;

        int randomIndex = new Random().nextInt(size);

        SquareType randomType = types[randomIndex];

        setType(randomType);
    }

    public void setNextType() {
        ArrayList<SquareType> types = new ArrayList<>();
        types.addAll(Arrays.asList(SquareType.values()));
        int size = types.size();

        int currentTypeIndex = types.indexOf(this.type);
        int nextTypeIndex = (currentTypeIndex + 1) % size;

        SquareType randomType = types.get(nextTypeIndex);

        setType(randomType);
    }
}
