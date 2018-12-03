package ai.input;

import ai.IInputValue;
import ai.InputBasedOnDistances;
import matrix.Matrix;
import position.CurrentPosition;
import position.Direction;
import position.Position;

public class DistancesInputValueGenerator {

    public static IInputValue getInputBasedOnDistances(Matrix matrix, CurrentPosition position, Direction currentDirection) {
        int size = matrix.getSize();
        Position food = matrix.findFood();

        double foodAheadDistance;
        double foodOnLeftDistance;
        double foodOnRightDistance;
        if (food == null) {
            foodAheadDistance = 100;
            foodOnLeftDistance = 100;
            foodOnRightDistance = 100;

        } else {
            foodAheadDistance = getAheadDistance(position, food, currentDirection);
            foodOnLeftDistance = getLeftDistance(position, food, currentDirection);
            foodOnRightDistance = getRightDistance(position, food, currentDirection);
        }

        double wallAheadDistance = getAheadWallDistance(matrix, position, currentDirection);
        double wallOnLeftDistance = getLeftWallDistance(matrix, position, currentDirection);
        double wallOnRightDistance = getRightWallDistance(matrix, position, currentDirection);

//        return InputBasedOnDistances.builder()
//                .clearAhead(wallAheadDistance)
//                .clearOnLeft(wallOnLeftDistance)
//                .clearOnRight(wallOnRightDistance)
//                .foodAhead(foodAheadDistance)
//                .foodOnLeft(foodOnLeftDistance)
//                .foodOnRight(foodOnRightDistance)
//                .build();
        return InputBasedOnDistances.builder()
//                .clearAhead(wallAheadDistance / size * 2)
                .clearAhead(wallAheadDistance * 2)
//                .clearOnLeft(wallOnLeftDistance / size * 2)
                .clearOnLeft(wallOnLeftDistance * 2)
//                .clearOnRight(wallOnRightDistance / size * 2)
                .clearOnRight(wallOnRightDistance * 2)
                .foodAhead(getNormalizedDistanceValue(foodAheadDistance, size) * 2)
                .foodOnLeft(getNormalizedDistanceValue(foodOnLeftDistance, size) * 2)
                .foodOnRight(getNormalizedDistanceValue(foodOnRightDistance, size) * 2)
                .build();
    }

    private static double getNormalizedDistanceValue(double distance, int matrixSize) {
        if (distance < 0) {
            return -matrixSize;
        }
        if (distance == 0) {
            return 0;
        }

//        return 1 - (distance / matrixSize);
        return matrixSize - distance;
    }

    private static double getAheadWallDistance(Matrix matrix, Position position, Direction currentDirection) {
        Position topWall = getTopObstaclePosition(matrix, position);
        Position bottomWall = getBottomObstaclePosition(matrix, position);
        Position leftWall = getLeftObstaclePosition(matrix, position);
        Position rightWall = getRightObstaclePosition(matrix, position);

        switch (currentDirection) {
            case UP:
                return getAheadDistance(position, topWall, currentDirection);
            case DOWN:
                return getAheadDistance(position, bottomWall, currentDirection);
            case RIGHT:
                return getAheadDistance(position, rightWall, currentDirection);
            case LEFT:
                return getAheadDistance(position, leftWall, currentDirection);
        }
        return 0;
    }

    private static double getLeftWallDistance(Matrix matrix, Position position, Direction currentDirection) {
        Position topWall = getTopObstaclePosition(matrix, position);
        Position bottomWall = getBottomObstaclePosition(matrix, position);
        Position leftWall = getLeftObstaclePosition(matrix, position);
        Position rightWall = getRightObstaclePosition(matrix, position);

        switch (currentDirection) {
            case UP:
                return getLeftDistance(position, leftWall, currentDirection);
            case DOWN:
                return getLeftDistance(position, rightWall, currentDirection);
            case RIGHT:
                return getLeftDistance(position, topWall, currentDirection);
            case LEFT:
                return getLeftDistance(position, bottomWall, currentDirection);
        }
        return 0;
    }

    private static double getRightWallDistance(Matrix matrix, Position position, Direction currentDirection) {
        Position topWall = getTopObstaclePosition(matrix, position);
        Position bottomWall = getBottomObstaclePosition(matrix, position);
        Position leftWall = getLeftObstaclePosition(matrix, position);
        Position rightWall = getRightObstaclePosition(matrix, position);

        switch (currentDirection) {
            case UP:
                return getRightDistance(position, rightWall, currentDirection);
            case DOWN:
                return getRightDistance(position, leftWall, currentDirection);
            case RIGHT:
                return getRightDistance(position, bottomWall, currentDirection);
            case LEFT:
                return getRightDistance(position, topWall, currentDirection);
        }
        return 0;
    }

    private static double getAheadDistance(Position p1, Position p2, Direction direction) {
        switch (direction) {
            case UP:
                return p1.getX() - p2.getX();
            case LEFT:
                return p1.getY() - p2.getY();
            case RIGHT:
                return p2.getY() - p1.getY();
            case DOWN:
                return p2.getX() - p1.getX();
        }
        return 0;
    }

    private static double getLeftDistance(Position p1, Position p2, Direction direction) {
        switch (direction) {
            case UP:
                return p1.getY() - p2.getY();
            case LEFT:
                return p2.getX() - p1.getX();
            case RIGHT:
                return p1.getX() - p2.getX();
            case DOWN:
                return p2.getY() - p1.getY();
        }
        return 0;
    }

    private static double getRightDistance(Position p1, Position p2, Direction direction) {
        switch (direction) {
            case UP:
                return p2.getY() - p1.getY();
            case LEFT:
                return p1.getX() - p2.getX();
            case RIGHT:
                return p2.getX() - p1.getX();
            case DOWN:
                return p1.getY() - p2.getY();
        }
        return 0;
    }


    private static Position getTopObstaclePosition(Matrix matrix, Position position) {
        Position obstacle = new Position(position.getX() - 1, position.getY());
        while (!matrix.getCell(obstacle).isCollide() && obstacle.getX() != 0) {
            obstacle.setX(obstacle.getX() - 1);
        }
        return obstacle;
    }

    private static Position getBottomObstaclePosition(Matrix matrix, Position position) {
        Position obstacle = new Position(position.getX() + 1, position.getY());
        while (!matrix.getCell(obstacle).isCollide() && obstacle.getX() != matrix.getSize() - 1) {
            obstacle.setX(obstacle.getX() + 1);
        }
        return obstacle;
    }

    private static Position getLeftObstaclePosition(Matrix matrix, Position position) {
        Position obstacle = new Position(position.getX(), position.getY() - 1);
        while (!matrix.getCell(obstacle).isCollide() && obstacle.getY() != 0) {
            obstacle.setY(obstacle.getY() - 1);
        }
        return obstacle;
    }

    private static Position getRightObstaclePosition(Matrix matrix, Position position) {
        Position obstacle = new Position(position.getX(), position.getY() + 1);
        while (!matrix.getCell(obstacle).isCollide() && obstacle.getY() != matrix.getSize() - 1) {
            obstacle.setY(obstacle.getY() + 1);
        }
        return obstacle;
    }
}
