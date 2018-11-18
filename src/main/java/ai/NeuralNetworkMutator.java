package ai;

import lombok.experimental.UtilityClass;
import org.neuroph.core.Connection;
import util.GeneralUtils;

import java.util.List;

@UtilityClass
public class NeuralNetworkMutator {

    public static SingleNeuralNetwork breed(SingleNeuralNetwork first, SingleNeuralNetwork second) {
        SingleNeuralNetwork crossover = crossover(first, second);
        return mutate(crossover);
    }

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
