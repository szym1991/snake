package ai;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Input based on distances
 */
@Builder
@AllArgsConstructor
public class InputBasedOnDistances implements IInputValue {

    private double clearAhead;
    private double clearOnLeft;
    private double clearOnRight;
    private double foodAhead;
    private double foodOnLeft;
    private double foodOnRight;

    @Override
    public double[] getValues() {
        double[] values = new double[6];
        values[0] = clearAhead;
        values[1] = clearOnLeft;
        values[2] = clearOnRight;
        values[3] = foodAhead;
        values[4] = foodOnLeft;
        values[5] = foodOnRight;
        return values;
    }
}
