package ai.input;


import ai.IInputValue;
import matrix.Matrix;
import position.CurrentPosition;
import position.Direction;
import position.Position;
import position.PositionHandler;
import util.GeneralUtils;

/**
 * Factory for generate inputs based on different approach
 */
public class InputValueFactory {

    public static IInputValue generateInputValues(InputValueGenerator inputValueGenerator, Matrix matrix,
                                                  PositionHandler positionHandler, Direction currentDirection,
                                                  CurrentPosition position) {
        switch (inputValueGenerator) {
            case BOOLEAN_INPUT_VALUE:
                return BooleanInputValueGenerator.getBooleanInputValue(matrix, positionHandler, currentDirection,
                        position);
            case INPUT_VALUE_BASED_ON_DISTANCES:
                return DistancesInputValueGenerator.getInputBasedOnDistances(matrix, position, currentDirection);
            case ULTIMATE_INPUT_VALUE:
                return UltimateInputValueGenerator.getInputValue(matrix, positionHandler, position);
        }
        return null;
    }

    private static double calculateDistance(Matrix matrix, CurrentPosition position) {
        Position food = matrix.findFood();
        if (food == null) {
            return 100;
        }
        return GeneralUtils.calculateDistance(position, food);
    }
}
