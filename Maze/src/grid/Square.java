package grid;

import java.awt.*;

public class Square {
    private Point location;
    private Color color;
    public SquareType type;

    public Square(SquareType type, Point location) {
        this.type = type;
        this.location = location;
        this.color = colorFromType(type);
    }

    public void setNextType() {
        switch (this.type) {
            case FREE:
                this.type = SquareType.WALL;
                break;
            case WALL:
                this.type = SquareType.AGENT;
                break;
            case AGENT:
                this.type = SquareType.GOAL;
                break;
            case GOAL:
                this.type = SquareType.TOVISIT;
                break;
            case TOVISIT:
                this.type = SquareType.VISITED;
                break;
            case VISITED:
                this.type = SquareType.FREE;
                break;
            default:
                this.type = SquareType.FREE;
                break;
        }
        this.color = colorFromType(this.type);
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
            case TOVISIT:
                return Color.cyan;
            case VISITED:
                return Color.green;
            default:
                return Color.white;
        }
    }

    public int getX() {
        return location.x;
    }
    public int getY() {
        return location.y;
    }

    public Color getColor() {
        return color;
    }

}
