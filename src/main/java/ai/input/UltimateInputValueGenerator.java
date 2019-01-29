package ai.input;

import ai.DistanceValue;
import ai.IInputValue;
import ai.UltimateInputValue;
import matrix.Matrix;
import matrix.MatrixObject;
import position.CurrentPosition;
import position.Position;
import position.PositionHandler;
import util.GeneralUtils;

public class UltimateInputValueGenerator {
    public static IInputValue getInputValue(Matrix matrix, PositionHandler positionHandler,
                                            CurrentPosition position) {


        Position topLeft = new Position(-1, -1);
        Position top = new Position(-1, 0);
        Position topRight = new Position(-1, 1);
        Position right = new Position(0, 1);
        Position bottomRight = new Position(1, 1);
        Position bottom = new Position(1, 0);
        Position bottomLeft = new Position(1, -1);
        Position left = new Position(0, -1);


        UltimateInputValue inputValue = new UltimateInputValue();
        inputValue.setTopLeft(findDistances(matrix, position, topLeft));
        inputValue.setTop(findDistances(matrix, position, top));
        inputValue.setTopRight(findDistances(matrix, position, topRight));
        inputValue.setRight(findDistances(matrix, position, right));
        inputValue.setBottomRight(findDistances(matrix, position, bottomRight));
        inputValue.setBottom(findDistances(matrix, position, bottom));
        inputValue.setBottomLeft(findDistances(matrix, position, bottomLeft));
        inputValue.setLeft(findDistances(matrix, position, left));

        return inputValue;
    }

    private static DistanceValue findDistances(Matrix matrix, CurrentPosition position, Position changed) {
        Position current = new Position(position.getX(), position.getY());
        DistanceValue distanceValue = new DistanceValue();
        while (!distanceValue.isFill()) {
            current.setX(current.getX() + changed.getX());
            current.setY(current.getY() + changed.getY());

            if (PositionHandler.isOutsideMatrix(current, matrix)) {
                break;
            }

            MatrixObject cell = matrix.getCell(current);
            if (cell.isEatable() && distanceValue.getFood() != null) {
                distanceValue.setFood(GeneralUtils.calculateDistance(position, current));
            }
            if (cell.equals(MatrixObject.SNAKE) && distanceValue.getTail() != null) {
                distanceValue.setTail(GeneralUtils.calculateDistance(position, current));
            }
            if (cell.equals(MatrixObject.OBSTACLE) && distanceValue.getWall() != null) {
                distanceValue.setWall(GeneralUtils.calculateDistance(position, current));
            }
        }

        return distanceValue;
    }
}
