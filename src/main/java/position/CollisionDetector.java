package position;

import matrix.Matrix;
import matrix.MatrixObject;

public class CollisionDetector {
    public static boolean detectCollision(Matrix matrix, CurrentPosition position) {
        MatrixObject cell = matrix.getCell(position.getX(), position.getY());
        return cell.isCollide();
    }
}
