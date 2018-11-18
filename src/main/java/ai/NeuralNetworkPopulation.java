package ai;

import lombok.Getter;
import util.GeneralUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NeuralNetworkPopulation {

    private static final int POPULATION_SIZE = 55;

    @Getter
    private int generationNo;

    @Getter
    private Iterator<SingleNeuralNetwork> iterator;

    private List<SingleNeuralNetwork> neuralNetworks;

    public NeuralNetworkPopulation() {
        generationNo = 1;
        neuralNetworks = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            neuralNetworks.add(new SingleNeuralNetwork());
        }

        iterator = neuralNetworks.iterator();
    }

    public SingleNeuralNetwork getNext() {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    public void newGeneration() {
        List<SingleNeuralNetwork> newGen = new ArrayList<>();
        neuralNetworks.sort(SingleNeuralNetwork::compareTo);
        for (int i = 0; i < POPULATION_SIZE / 10; i++) {
            if (neuralNetworks.get(i).getFinalScore() > 0) {
                newGen.add(neuralNetworks.get(i));
            }
        }
        if (newGen.isEmpty()) {
            newGen.add(new SingleNeuralNetwork());
        }
        while (newGen.size() < POPULATION_SIZE) {
            SingleNeuralNetwork first = newGen.get(GeneralUtils.randomValue(newGen.size()));
            SingleNeuralNetwork second = newGen.get(GeneralUtils.randomValue(newGen.size()));
            newGen.add(NeuralNetworkMutator.breed(first, second));
        }

        clearScore();
        iterator = neuralNetworks.iterator();
        generationNo++;
    }

    public void clearScore() {
        for (SingleNeuralNetwork neuralNetwork : neuralNetworks) {
            neuralNetwork.clearScore();
        }
    }
}