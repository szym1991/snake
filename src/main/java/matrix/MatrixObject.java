package matrix;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@Getter
@AllArgsConstructor
public enum MatrixObject {
    SNAKE(Color.GREEN, true, false),
    EMPTY_PLACE(Color.GRAY, false, false),
    FOOD(Color.RED, false, true),
    OBSTACLE(Color.WHITE, true, false);

    private Color color;
    private boolean isCollide;
    private boolean eatable;
}
