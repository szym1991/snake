package position;

public enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;


    public static Direction goAhead(Direction currentDirection) {
        return currentDirection;
    }

    public static Direction turnLeft(Direction currentDirection) {
        switch (currentDirection) {
            case UP:
                return Direction.LEFT;
            case DOWN:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.UP;
            case LEFT:
                return Direction.DOWN;
        }
        return currentDirection;
    }

    public static Direction turnRight(Direction currentDirection) {
        switch (currentDirection) {
            case UP:
                return Direction.RIGHT;
            case DOWN:
                return Direction.LEFT;
            case RIGHT:
                return Direction.DOWN;
            case LEFT:
                return Direction.UP;
        }
        return currentDirection;
    }
}
