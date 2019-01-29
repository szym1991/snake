package matrix;

import lombok.Getter;
import position.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * ---------> Y
 * |
 * |
 * |
 * |
 * \/
 * X
 */
public class Matrix {
    @Getter
    private int size;
    @Getter
    private boolean withBorder;
    private List<List<MatrixObject>> matrix;

    public Matrix(int size) {
        this(size, false);
    }

    public Matrix(int size, boolean withBorder) {
        this.size = size;
        this.withBorder = withBorder;
        this.matrix = new ArrayList<>();
        fillEmptyMatrix();
    }

    private void fillEmptyMatrix() {
        for (int i = 0; i < size; i++) {
            List<MatrixObject> emptyPlaces = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                emptyPlaces.add(MatrixObject.EMPTY_PLACE);
            }
            matrix.add(emptyPlaces);
        }

        if (withBorder) {
            makeBorder();
        }
    }

    private void makeBorder() {
        for (int i = 0; i < size; i++) {
            if (i == 0 || i == size - 1) {
                List<MatrixObject> borders = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    borders.add(MatrixObject.OBSTACLE);
                }
                matrix.add(i, borders);
            }
            matrix.get(i).add(0, MatrixObject.OBSTACLE);
            matrix.get(i).add(size - 1, MatrixObject.OBSTACLE);
        }
    }

    public MatrixObject getCell(Position position) {
        return matrix.get(position.getX()).get(position.getY());
    }

    public MatrixObject getCell(int x, int y) {
        return matrix.get(x).get(y);
    }

    public void clearCell(int x, int y) {
        matrix.get(x).set(y, MatrixObject.EMPTY_PLACE);
    }

    public void setCell(int x, int y, MatrixObject object) {
        matrix.get(x).set(y, object);
    }

    public Position findFood() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix.get(i).get(j).isEatable()) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }
}
