package ai;

import position.Direction;

public interface IOutputValue {
    void generate(double[] outputValues);

    Direction getBestDirection(Direction currentDirection);
}
