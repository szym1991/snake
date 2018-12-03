package ai;

import lombok.Getter;
import util.GeneralUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Population of neural network used in genetic algorithm
 */
public class NeuralNetworkPopulation {

    private int populationSize;

    @Getter
    private int generationNo;

    @Getter
    private Iterator<SingleNeuralNetwork> iterator;

    private List<SingleNeuralNetwork> neuralNetworks;

    /**
     * Creates population based on {@link SingleNeuralNetwork}
     *
     * @param populationSize size of population
     */
    public NeuralNetworkPopulation(int populationSize) {
        this.populationSize = populationSize;
        generationNo = 1;
        neuralNetworks = new ArrayList<>();
        for (int i = 0; i < this.populationSize; i++) {
            neuralNetworks.add(new SingleNeuralNetwork());
        }

        iterator = neuralNetworks.iterator();
    }

    /**
     * Gets next not used neural network in population
     *
     * @return neural network
     */
    public SingleNeuralNetwork getNext() {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    /**
     * Creates new generation.
     * Gets 1/10th best neural networks and adds to new generation.
     * Fills the rest of generation by mutate best neural network using {@link NeuralNetworkMutator}
     */
    public void newGeneration() {
        List<SingleNeuralNetwork> newGen = new ArrayList<>();
        neuralNetworks.sort(SingleNeuralNetwork::compareTo);
        for (int i = 0; i < populationSize / 10; i++) {
            if (neuralNetworks.get(i).getFinalScore() > 0) {
                newGen.add(neuralNetworks.get(i));
            }
        }
        if (newGen.isEmpty()) {
            newGen.add(new SingleNeuralNetwork());
        }
        while (newGen.size() < populationSize) {
            SingleNeuralNetwork first = newGen.get(GeneralUtils.randomValue(newGen.size()));
            SingleNeuralNetwork second = newGen.get(GeneralUtils.randomValue(newGen.size()));
            newGen.add(NeuralNetworkMutator.breed(first, second));
        }

        clearScore();
        iterator = neuralNetworks.iterator();
        generationNo++;
    }

    /**
     * Clears score in all neural network in population
     */
    public void clearScore() {
        for (SingleNeuralNetwork neuralNetwork : neuralNetworks) {
            neuralNetwork.clearScore();
        }
    }
}
