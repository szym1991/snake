package ai;

import lombok.Getter;
import position.Direction;

/**
 * Output for neural network
 */
@Getter
public class OutputValue {

    private double goAhead;
    private double turnLeft;
    private double turnRight;

    public OutputValue(double[] outputValues) {
        if (outputValues.length == 3) {
            this.goAhead = outputValues[0];
            this.turnLeft = outputValues[1];
            this.turnRight = outputValues[2];
        }
    }

    /**
     * Gets best direction based on current direction
     *
     * @param currentDirection currentDirection
     * @return next direction
     */
    public Direction getBestDirection(Direction currentDirection) {
        double max = Math.max(Math.max(goAhead, turnLeft), turnRight);

        if (max == goAhead) {
            return currentDirection;
        }
        if (max == turnLeft) {
            return Direction.turnLeft(currentDirection);
        }

        if (max == turnRight) {
            return Direction.turnRight(currentDirection);
        }

        return currentDirection;
    }
}
