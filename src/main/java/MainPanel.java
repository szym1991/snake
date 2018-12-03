import ai.IInputValue;
import ai.SingleNeuralNetwork;
import ai.input.InputValueFactory;
import ai.input.InputValueGenerator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import matrix.Matrix;
import matrix.MatrixObject;
import matrix.SnakeBody;
import position.*;
import util.GeneralUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

@Slf4j
public class MainPanel extends JPanel implements ActionListener {

    private static final boolean WITH_BORDER = true;
    private static final boolean PLAY_HUMAN = false;
    private static final int DELAY = 50;
    private static final int MATRIX_SIZE = 40;
    private static final double MIN_SCORE = -50.0;

    private Timer timer;

    private Matrix matrix;
    private int cellSize;
    private Integer windowSize;
    private Integer points;

    private Position startPosition;
    private CurrentPosition position;
    private PositionHandler positionHandler;
    private Direction currentDirection;
    private SnakeBody snakeBody;

    @Getter
    private SingleNeuralNetwork currentNeuralNetwork;
    Consumer<MainPanel> endGame;

    public MainPanel(int windowSize, Consumer<MainPanel> endGame) {
        this.windowSize = windowSize;
        this.cellSize = windowSize / MATRIX_SIZE;
        this.endGame = endGame;

        addKeyListener(new Adapter());
        setFocusable(true);

        timer = new Timer(DELAY, this);
        int pos = MATRIX_SIZE / 2;
        this.startPosition = new Position(pos - 1, pos - 1);
        this.positionHandler = new PositionHandler(MATRIX_SIZE);


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

    public void startGame(SingleNeuralNetwork singleNeuralNetwork) {
        this.points = 0;
        this.currentNeuralNetwork = singleNeuralNetwork;
        if (currentNeuralNetwork == null) {
            return;
        }
        this.matrix = new Matrix(MATRIX_SIZE, WITH_BORDER);
        this.position = new CurrentPosition(startPosition.getX(), startPosition.getY());
        this.snakeBody = new SnakeBody(position);
        this.currentDirection = Direction.UP;
        Position foodPosition = FoodGenerator.getNextPosition(matrix);
        matrix.setCell(foodPosition.getX(), foodPosition.getY(), MatrixObject.FOOD);
        updateScore();
        timer.start();
        repaint();

//        while (true) {
//            actionPerformed(null);
//        }
    }

    public void actionPerformed(ActionEvent e) {
        if (matrix == null) {
            return;
        }
        double prevDistance = calculateDistance();
        if (!PLAY_HUMAN) {

            if (currentNeuralNetwork.getScore() < MIN_SCORE) {
                timer.stop();
                endGame.accept(this);
            }
            currentDirection = currentNeuralNetwork.calculate(prepareInput()).getBestDirection(currentDirection);
        }
        position = positionHandler.changePosition(position, currentDirection);
        boolean collision = CollisionDetector.detectCollision(matrix, position);
        if (collision) {
            timer.stop();
            endGame.accept(this);
            return;
        }

        double currentDistance = calculateDistance();
        if (prevDistance - currentDistance > 0) {
            currentNeuralNetwork.addScore(1);
        } else {
            currentNeuralNetwork.addScore(-1.5);
        }

        if (matrix.getCell(position.getX(), position.getY()).isEatable()) {
            snakeBody.makeBigger();
            currentNeuralNetwork.addScore(10);
            currentNeuralNetwork.incrementCollectedFoods();
            points++;
            Position foodPosition = FoodGenerator.getNextPosition(matrix);
            matrix.setCell(foodPosition.getX(), foodPosition.getY(), MatrixObject.FOOD);
        }
        SnakeBodyDrawer.updateSnakeBodyAndMatrix(matrix, snakeBody, position);
        updateScore();
        this.repaint();
    }

    private IInputValue prepareInput() {
        return InputValueFactory.generateInputValues(InputValueGenerator.INPUT_VALUE_BASED_ON_DISTANCES,
                matrix, positionHandler, currentDirection, position);
    }

    private double calculateDistance() {
        Position food = matrix.findFood();
        if (food == null) {
            return 100;
        }
        return GeneralUtils.calculateDistance(position, food);
    }

    private void updateScore() {
        Snake snake = (Snake) SwingUtilities.windowForComponent(this);
        if (snake != null) {
            snake.setTitle(Snake.NAME + " - Score: " + points + " network score: " + currentNeuralNetwork.getScore());
        }
    }

    private class Adapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && currentDirection != Direction.RIGHT) {
                currentDirection = Direction.LEFT;
            }

            if (key == KeyEvent.VK_RIGHT && currentDirection != Direction.LEFT) {
                currentDirection = Direction.RIGHT;
            }

            if (key == KeyEvent.VK_UP && currentDirection != Direction.DOWN) {
                currentDirection = Direction.UP;
            }

            if (key == KeyEvent.VK_DOWN && currentDirection != Direction.UP) {
                currentDirection = Direction.DOWN;
            }

            if (key == KeyEvent.VK_SPACE) {
                startGame(currentNeuralNetwork);
            }

            if (key == KeyEvent.VK_P) {
                pauseGame();
            }
        }
    }

    private void pauseGame() {
        if (timer.isRunning()) {
            timer.stop();
        } else {
            timer.start();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(windowSize, windowSize);
    }
}
