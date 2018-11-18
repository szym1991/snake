package position;

public class PositionHandler {
    private int matrixSize;

    public PositionHandler(int matrixSize) {
        this.matrixSize = matrixSize;
    }

    public CurrentPosition changePosition(CurrentPosition currentPosition, Direction direction) {
        CurrentPosition position = new CurrentPosition(currentPosition.getX(), currentPosition.getY());
        int x;
        int y;
        switch (direction) {
            case UP:
                x = position.getX() - 1;
                if (x < 0) {
                    x = matrixSize - 1;
                }
                position.setX(x);
                break;
            case DOWN:
                x = position.getX() + 1;
                if (x > matrixSize - 1) {
                    x = 0;
                }
                position.setX(x);
                break;
            case LEFT:
                y = position.getY() - 1;
                if (y < 0) {
                    y = matrixSize - 1;
                }
                position.setY(y);
                break;
            case RIGHT:
                y = position.getY() + 1;
                if (y > matrixSize - 1) {
                    y = 0;
                }
                position.setY(y);
                break;
        }
        return position;
    }
}
