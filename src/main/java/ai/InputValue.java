package ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InputValue implements IInputValue {
    private boolean clearAhead;
    private boolean clearOnLeft;
    private boolean clearOnRight;
    private boolean foodAhead;
    private boolean foodOnLeft;
    private boolean foodOnRight;

    public static double getDoubleValue(boolean value) {
        return value ? 1.0 : 0.0;
    }

    @Override
    public double[] getValues() {
        double[] values = new double[6];
        values[0] = getDoubleValue(clearAhead);
        values[1] = getDoubleValue(clearOnLeft);
        values[2] = getDoubleValue(clearOnRight);
        values[3] = getDoubleValue(foodAhead);
        values[4] = getDoubleValue(foodOnLeft);
        values[5] = getDoubleValue(foodOnRight);
        return values;
    }
}
