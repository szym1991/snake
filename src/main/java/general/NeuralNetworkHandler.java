package general;

import ai.NeuralNetworkPopulation;
import ai.SingleNeuralNetwork;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NeuralNetworkHandler {

    private static final int POPULATION_SIZE = 55;
    private NeuralNetworkPopulation neuralNetworkPopulation;
    private int endedGames;

    public NeuralNetworkHandler() {
        this.neuralNetworkPopulation = new NeuralNetworkPopulation(POPULATION_SIZE);
        endedGames = 0;
    }

    public SingleNeuralNetwork getNext() {
        return neuralNetworkPopulation.getNext();
    }

    public void generateNextGeneration() {
        log.info("Generation: " + neuralNetworkPopulation.getGenerationNo());
        endedGames = 0;
        neuralNetworkPopulation.newGeneration();
    }

    public void handleEndGame(SingleNeuralNetwork singleNeuralNetwork) {
        endedGames++;
        log.info("Current network score: " + singleNeuralNetwork.getFinalScore());
        if (singleNeuralNetwork.getFinalScore() > 2500) {
            log.info(singleNeuralNetwork.toString());
        }
    }

    public boolean areAllGamesFinished() {
        return endedGames == POPULATION_SIZE;
    }
}
