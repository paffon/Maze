package grid;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Square {

    private Point screenCoordinates;
    private Point gridCoordinates;
    private Color color;
    public SquareType type;
    public double distance = 0;

    public Square(SquareType type, Point screenCoordinates) {
        setType(type);
        this.screenCoordinates = screenCoordinates;
    }

    public void setType(SquareType newType) {
        this.type = newType;
        this.color = colorFromType(newType);
    }

    private Color colorFromType(SquareType type) {
        switch (type) {
            case FREE:
                return new Color(220,220,220);
            case WALL:
                return new Color(20,50,50);
            case AGENT:
                return new Color(200,50,50);
            case GOAL:
                return new Color(0,100,200);
            case GOAL_FOUND:
                return new Color(50,150,250);
            case TO_VISIT:
                return new Color(60,210,150);
            case VISITED:
                return new Color(50,200,100);
            default:
                System.out.println("Color undefined for type " + type + ". Setting to default color.");
                return Color.white;
        }
    }

    public int getScreenX() {
        return screenCoordinates.x;
    }
    public int getScreenY() {
        return screenCoordinates.y;
    }

    public int getGridX() {
        return gridCoordinates.x;
    }
    public int getGridY() {
        return gridCoordinates.y;
    }

    public Point getGridCoordinates() {
        return gridCoordinates;
    }

    public void setGridCoordinates(Point gridCoordinates) {
        this.gridCoordinates = gridCoordinates;
    }

    public Point getScreenCoordinates() {
        return screenCoordinates;
    }

    public Color getColor() {
        return color;
    }

    public boolean isWall() {
        return this.type == SquareType.WALL;
    }

    public boolean isAgent() {
        return this.type == SquareType.AGENT;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Square)) return false;
        Square square = (Square) o;
        return Objects.equals(getGridCoordinates(), square.getGridCoordinates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGridCoordinates());
    }
}
