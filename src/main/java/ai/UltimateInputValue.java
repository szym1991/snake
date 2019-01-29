package ai;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UltimateInputValue implements IInputValue {

    private DistanceValue topLeft;
    private DistanceValue top;
    private DistanceValue topRight;
    private DistanceValue right;
    private DistanceValue bottomRight;
    private DistanceValue bottom;
    private DistanceValue bottomLeft;
    private DistanceValue left;

    public double[] getValues() {
        double[] values = new double[24];

        values[0] = topLeft.getFood();
        values[1] = topLeft.getTail();
        values[2] = topLeft.getWall();
        values[3] = top.getFood();
        values[4] = top.getTail();
        values[5] = top.getWall();
        values[6] = topRight.getFood();
        values[7] = topRight.getTail();
        values[8] = topRight.getWall();
        values[9] = right.getFood();
        values[10] = right.getTail();
        values[11] = right.getWall();
        values[12] = bottomRight.getFood();
        values[13] = bottomRight.getTail();
        values[14] = bottomRight.getWall();
        values[15] = bottom.getFood();
        values[16] = bottom.getTail();
        values[17] = bottom.getWall();
        values[18] = bottomLeft.getFood();
        values[19] = bottomLeft.getTail();
        values[20] = bottomLeft.getWall();
        values[21] = left.getFood();
        values[22] = left.getTail();
        values[23] = left.getWall();

        return values;
    }
}
