package ai;

import lombok.experimental.UtilityClass;
import org.neuroph.core.Connection;
import util.GeneralUtils;

import java.util.List;

/**
 * Mutator for neural network
 */
@UtilityClass
public class NeuralNetworkMutator {

    /**
     * Breeds neural network based on two others from previous generation
     *
     * @param first  first neural network
     * @param second second neural network
     * @return new neural network
     */
    public static SingleNeuralNetwork breed(SingleNeuralNetwork first, SingleNeuralNetwork second) {
        SingleNeuralNetwork crossover = crossover(first, second);
        return mutate(crossover);
    }

    /**
     * Mutates neural network
     * Changes 1/4th weights on network
     *
     * @param network input network
     * @return network with changes weights
     */
    public static SingleNeuralNetwork mutate(SingleNeuralNetwork network) {
        SingleNeuralNetwork ann = network.clone();
        int numberOfConnections = ann.getNumberOfConnections();
        for (int i = 0; i < numberOfConnections / 4; i++) {
            int weightIndex = GeneralUtils.randomValue(numberOfConnections);
            double weight = GeneralUtils.randomValue();
            ann.changeInputWeight(weightIndex, weight);
        }
        return ann;
    }

    /**
     * Gets neural network based on crossed two previous ones
     *
     * @param first  first neural network
     * @param second second neural network
     * @return new neural network
     */
    public static SingleNeuralNetwork crossover(SingleNeuralNetwork first, SingleNeuralNetwork second) {
        SingleNeuralNetwork clone = first.clone();
        List<Connection> secondAllConnections = second.getAllConnections();

        int numberOfConnections = first.getNumberOfConnections();
        int crossoverIndex = GeneralUtils.randomValue(numberOfConnections);
        for (int i = crossoverIndex; i < numberOfConnections; i++) {
            clone.changeInputWeight(i, secondAllConnections.get(i).getWeight().getValue());
        }
        return clone;
    }
}
