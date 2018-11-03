package position;

public class PositionHandler {
    private int matrixSize;

    public PositionHandler(int matrixSize) {
        this.matrixSize = matrixSize;
    }

    public CurrentPosition changePosition(CurrentPosition currentPosition, Turn turn) {
        int x;
        int y;
        switch (turn) {
            case UP:
                x = currentPosition.getX() - 1;
                if (x < 0) {
                    x = matrixSize - 1;
                }
                currentPosition.setX(x);
                break;
            case DOWN:
                x = currentPosition.getX() + 1;
                if (x > matrixSize - 1) {
                    x = 0;
                }
                currentPosition.setX(x);
                break;
            case LEFT:
                y = currentPosition.getY() - 1;
                if (y < 0) {
                    y = matrixSize - 1;
                }
                currentPosition.setY(y);
                break;
            case RIGHT:
                y = currentPosition.getY() + 1;
                if (y > matrixSize - 1) {
                    y = 0;
                }
                currentPosition.setY(y);
                break;
        }
        return currentPosition;
    }
}
