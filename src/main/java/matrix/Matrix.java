package matrix;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private int matrixSize;
    private boolean withBorder;
    private List<List<MatrixObject>> matrix;

    public Matrix(int matrixSize) {
        this(matrixSize, false);
    }

    public Matrix(int matrixSize, boolean withBorder) {
        this.matrixSize = matrixSize;
        this.withBorder = withBorder;
        this.matrix = new ArrayList<>();
        fillEmptyMatrix();
    }

    private void fillEmptyMatrix() {
        for (int i = 0; i < matrixSize; i++) {
            List<MatrixObject> emptyPlaces = new ArrayList<>();
            for (int j = 0; j < matrixSize; j++) {
                emptyPlaces.add(MatrixObject.EMPTY_PLACE);
            }
            matrix.add(emptyPlaces);
        }

        if (withBorder) {
            makeBorder();
        }
    }

    private void makeBorder() {
        for (int i = 0; i < matrixSize; i++) {
            if (i == 0 || i == matrixSize - 1) {
                List<MatrixObject> borders = new ArrayList<>();
                for (int j = 0; j < matrixSize; j++) {
                    borders.add(MatrixObject.OBSTACLE);
                }
                matrix.add(i, borders);
            }
            matrix.get(i).add(0, MatrixObject.OBSTACLE);
            matrix.get(i).add(matrixSize - 1, MatrixObject.OBSTACLE);
        }
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

    public int getSize() {
        return matrixSize;
    }
}
