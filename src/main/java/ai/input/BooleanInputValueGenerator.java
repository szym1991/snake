package ai.input;

import ai.IInputValue;
import ai.InputValue;
import matrix.Matrix;
import position.CurrentPosition;
import position.Direction;
import position.PositionHandler;

public class BooleanInputValueGenerator {


    public static IInputValue getBooleanInputValue(Matrix matrix, PositionHandler positionHandler,
                                                   Direction currentDirection, CurrentPosition position) {
        CurrentPosition ahead = positionHandler.changePosition(position, Direction.goAhead(currentDirection));
        CurrentPosition turnLeft = positionHandler.changePosition(position, Direction.turnLeft(currentDirection));
        CurrentPosition turnRight = positionHandler.changePosition(position, Direction.turnRight(currentDirection));

        return InputValue.builder()
                .clearAhead(!matrix.getCell(ahead).isCollide())
                .clearOnLeft(!matrix.getCell(turnLeft).isCollide())
                .clearOnRight(!matrix.getCell(turnRight).isCollide())
                .foodAhead(matrix.getCell(ahead).isEatable())
                .foodOnLeft(matrix.getCell(turnLeft).isEatable())
                .foodOnRight(matrix.getCell(turnRight).isEatable())
                .build();
    }
}
