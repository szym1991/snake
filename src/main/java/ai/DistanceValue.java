package ai;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class DistanceValue {
    private Double food;
    private Double tail;
    private Double wall;

    public Double getFood() {
        return food != null ? food : 40;
    }

    public Double getTail() {
        return tail != null ? tail : 40;
    }

    public Double getWall() {
        return wall != null ? wall : 40;
    }

    public boolean isFill() {
        return food != null && tail != null && wall != null;
    }
}
