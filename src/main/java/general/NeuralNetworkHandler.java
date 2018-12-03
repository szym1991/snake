package general;

import ai.NeuralNetworkPopulation;
import ai.SingleNeuralNetwork;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler for population of neural networks
 */
@Slf4j
public class NeuralNetworkHandler {

    private static final int POPULATION_SIZE = 55;
    private NeuralNetworkPopulation neuralNetworkPopulation;
    private int endedGames;

    /**
     * Generates new population
     */
    public NeuralNetworkHandler() {
        this.neuralNetworkPopulation = new NeuralNetworkPopulation(POPULATION_SIZE);
        endedGames = 0;
    }

    /**
     * Gets next neural network from population
     *
     * @return neural network
     */
    public SingleNeuralNetwork getNext() {
        return neuralNetworkPopulation.getNext();
    }

    /**
     * Generates new generation of population
     */
    public void generateNextGeneration() {
        log.info("Generation: " + neuralNetworkPopulation.getGenerationNo());
        endedGames = 0;
        neuralNetworkPopulation.newGeneration();
    }

    /**
     * Handles finished games
     *
     * @param singleNeuralNetwork neural network
     */
    public void handleEndGame(SingleNeuralNetwork singleNeuralNetwork) {
        endedGames++;
        log.info("Current network score: " + singleNeuralNetwork.getFinalScore());
        if (singleNeuralNetwork.getFinalScore() > 2500) {
            log.info(singleNeuralNetwork.toString());
        }
    }

    /**
     * Checks if all games are finished
     *
     * @return true if all finished
     */
    public boolean areAllGamesFinished() {
        return endedGames == POPULATION_SIZE;
    }
}
