package ai;

import lombok.NoArgsConstructor;
import position.Direction;

@NoArgsConstructor
public class DimensionsOutputValue implements IOutputValue {

    private double top;
    private double right;
    private double bottom;
    private double left;


    @Override
    public void generate(double[] outputValues) {
        if (outputValues.length == 4) {
            this.top = outputValues[0];
            this.right = outputValues[1];
            this.bottom = outputValues[2];
            this.left = outputValues[3];
        }
    }

    @Override
    public Direction getBestDirection(Direction currentDirection) {
        double max = Math.max(Math.max(Math.max(top, right), bottom), left);

        if (max == top) {
            return Direction.UP;
        }

        if (max == left) {
            return Direction.LEFT;
        }

        if (max == bottom) {
            return Direction.DOWN;
        }

        if (max == right) {
            return Direction.RIGHT;
        }

        return currentDirection;
    }
}
