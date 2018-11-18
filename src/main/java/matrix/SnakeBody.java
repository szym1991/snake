package matrix;

import position.CurrentPosition;
import position.Position;

import java.util.ArrayList;
import java.util.List;

public class SnakeBody {
    private final static Integer START_LENGTH = 5;

    private List<Position> snakeParts;

    private Integer size;

    public SnakeBody(Position startPosition) {
        size = START_LENGTH;
        snakeParts = new ArrayList<>();
        snakeParts.add(startPosition);
    }

    public void move(CurrentPosition position) {
        snakeParts.add(new Position(position.getX(), position.getY()));
        if (snakeParts.size() > size) {
            snakeParts.remove(0);
        }
    }

    public void makeBigger() {
        size++;
    }

    public Position getHead() {
        if (snakeParts.size() != 0) {
            return snakeParts.get(snakeParts.size() - 1);
        }
        return null;
    }

    public Position getTail() {
        if (snakeParts.size() != 0) {
            return snakeParts.get(0);
        }
        return null;
    }
}
