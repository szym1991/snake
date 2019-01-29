import ai.SingleNeuralNetwork;
import general.NeuralNetworkHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Snake extends JFrame {

    private static final int VIEWS_NUMBER = 55;
    private static final int COLS_NUMBER = 11;
    private static final Integer WINDOW_SIZE = 100;
    public static final String NAME = "Snake";

    private JPanel panel;
    private List<MainPanel> games;
    private NeuralNetworkHandler neuralNetworkHandler;

    public Snake() throws HeadlessException {
        super(NAME);
        neuralNetworkHandler = new NeuralNetworkHandler();
        panel = new JPanel();
        games = new ArrayList<>();
        int rows = (int) Math.ceil(VIEWS_NUMBER / COLS_NUMBER);
        int cols = VIEWS_NUMBER > COLS_NUMBER ? COLS_NUMBER : VIEWS_NUMBER;
        panel.setLayout(new GridLayout(rows, cols));
        setContentPane(panel);
        prepareGame();
        startGame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                new Snake();
            }
        });
    }

    private void prepareGame() {
        for (int i = 0; i < VIEWS_NUMBER; i++) {
            MainPanel mainPanel = new MainPanel(WINDOW_SIZE, handleEndGame());
            games.add(mainPanel);
            panel.add(mainPanel);
        }
    }

    private void startGame() {
        for (MainPanel game : games) {
            SingleNeuralNetwork next = neuralNetworkHandler.getNext();
            if (next != null) {
                game.startGame(next);
            }
        }
    }

    private Consumer<MainPanel> handleEndGame() {
        return mainPanel -> {
            neuralNetworkHandler.handleEndGame(mainPanel.getCurrentNeuralNetwork());
            SingleNeuralNetwork next = neuralNetworkHandler.getNext();
            if (next != null) {
                mainPanel.startGame(next);
            }
            if (neuralNetworkHandler.areAllGamesFinished()) {
                handleNewGeneration();
            }
        };
    }

    private void handleNewGeneration() {
        neuralNetworkHandler.generateNextGeneration();
        startGame();
    }
}