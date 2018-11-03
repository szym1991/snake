package position;

import matrix.Matrix;
import matrix.MatrixObject;
import matrix.SnakeBody;

public class SnakeBodyDrawer {
    public static void updateSnakeBodyAndMatrix(Matrix matrix, SnakeBody snakeBody, CurrentPosition currentPosition) {
        Position prevTail = snakeBody.getTail();
        snakeBody.move(currentPosition);

        Position head = snakeBody.getHead();
        Position tail = snakeBody.getTail();
        matrix.setCell(head.getX(), head.getY(), MatrixObject.SNAKE);

        if (!prevTail.equals(tail)) {
            matrix.setCell(prevTail.getX(), prevTail.getY(), MatrixObject.EMPTY_PLACE);
        }
    }
}
