package position;

import matrix.Matrix;

import java.util.Random;

public class FoodGenerator {
    private static final Random RANDOM = new Random();

    public static Position getNextPosition(Matrix matrix) {
        int size = matrix.isWithBorder() ? matrix.getSize() - 1 : matrix.getSize();
        Position position = getRandomPosition(size);
        while (matrix.getCell(position.getX(), position.getY()).isCollide()) {
            position = getRandomPosition(size);
        }
        return position;
    }

    private static Position getRandomPosition(int bound) {
        int x = RANDOM.nextInt(bound - 1);
        int y = RANDOM.nextInt(bound - 1);
        return new Position(x, y);
    }
}
