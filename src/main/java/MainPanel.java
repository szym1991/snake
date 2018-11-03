import matrix.Matrix;
import matrix.MatrixObject;
import matrix.SnakeBody;
import position.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainPanel extends JPanel implements ActionListener {

    private static final boolean WITH_BORDER = false;
    private static final int DELAY = 50;
    private static final int MATRIX_SIZE = 40;

    private static Timer timer;

    private Matrix matrix;
    private int cellSize;
    private Integer windowSize;
    private Integer points;

    private Position startPosition;
    private CurrentPosition position;
    private PositionHandler positionHandler;
    private Turn currentTurn;
    private SnakeBody snakeBody;

    public MainPanel(int windowSize) {
        this.windowSize = windowSize;
        this.cellSize = windowSize / MATRIX_SIZE;

        addKeyListener(new Adapter());
        setFocusable(true);

        timer = new Timer(DELAY, this);
        timer.start();
        int pos = MATRIX_SIZE / 2;
        this.startPosition = new Position(pos - 1, pos - 1);
        this.positionHandler = new PositionHandler(MATRIX_SIZE);

        startGame();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Integer yLoc = 0;
        Integer xLoc;
        for (int i = 0; i < matrix.getSize(); i++) {
            xLoc = 0;
            for (int j = 0; j < matrix.getSize(); j++) {
                MatrixObject matrixObject = matrix.getCell(i, j);
                g.setColor(matrixObject.getColor());
                g.fillRect(xLoc, yLoc, cellSize, cellSize);
                xLoc += cellSize;
            }
            yLoc += cellSize;
        }
    }

    private void startGame() {
        this.points = 0;
        this.matrix = new Matrix(MATRIX_SIZE, WITH_BORDER);
        this.position = new CurrentPosition(startPosition.getX(), startPosition.getY());
        this.snakeBody = new SnakeBody(position);
        this.currentTurn = Turn.RIGHT;
        Position foodPosition = FoodGenerator.getNextPosition(matrix);
        matrix.setCell(foodPosition.getX(), foodPosition.getY(), MatrixObject.FOOD);
        updateScore();
        timer.start();
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        CurrentPosition currentPosition = positionHandler.changePosition(position, currentTurn);
        boolean collision = CollisionDetector.detectCollision(matrix, currentPosition);
        if (collision) {
            timer.stop();
            return;
        }
        if (matrix.getCell(currentPosition.getX(), currentPosition.getY()).isEatable()) {
            snakeBody.makeBigger();
            points++;
            updateScore();
            Position foodPosition = FoodGenerator.getNextPosition(matrix);
            matrix.setCell(foodPosition.getX(), foodPosition.getY(), MatrixObject.FOOD);
        }
        SnakeBodyDrawer.updateSnakeBodyAndMatrix(matrix, snakeBody, currentPosition);
        this.repaint();
    }

    private void updateScore() {
        Snake snake = (Snake) SwingUtilities.windowForComponent(this);
        if (snake != null) {
            snake.setTitle(Snake.NAME + " - Score: " + points);
        }
    }

    private class Adapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && currentTurn != Turn.RIGHT) {
                currentTurn = Turn.LEFT;
            }

            if (key == KeyEvent.VK_RIGHT && currentTurn != Turn.LEFT) {
                currentTurn = Turn.RIGHT;
            }

            if (key == KeyEvent.VK_UP && currentTurn != Turn.DOWN) {
                currentTurn = Turn.UP;
            }

            if (key == KeyEvent.VK_DOWN && currentTurn != Turn.UP) {
                currentTurn = Turn.DOWN;
            }

            if (key == KeyEvent.VK_SPACE) {
                startGame();
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(windowSize, windowSize);
    }
}
