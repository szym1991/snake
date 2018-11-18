package ai;

import lombok.Getter;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.neuroph.core.*;
import org.neuroph.core.transfer.Linear;
import org.neuroph.core.transfer.TransferFunction;
import org.neuroph.util.ConnectionFactory;
import util.GeneralUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SingleNeuralNetwork implements Comparable, Serializable {

    private static final int NUMBER_OF_INPUTS = 6;
    private static final int NUMBER_OF_OUTPUTS = 3;

    private NeuralNetwork ann;
    @Getter
    private Double score;
    private Integer moves;
    private Integer collectedFoods;

    public SingleNeuralNetwork() {
        score = 0.0;
        moves = 0;
        collectedFoods = 0;
        Layer inputLayer = new Layer();
        for (int i = 0; i < NUMBER_OF_INPUTS; i++) {
            Neuron neuron = new Neuron();
            neuron.setTransferFunction(getDefaultFunction());
            inputLayer.addNeuron(neuron);
        }

        Layer hiddenLayerOne = new Layer();
        for (int i = 0; i < NUMBER_OF_INPUTS - 1; i++) {
            Neuron neuron = new Neuron();
            neuron.setTransferFunction(getDefaultFunction());
            hiddenLayerOne.addNeuron(neuron);
        }

        Layer outputLayer = new Layer();
        for (int i = 0; i < NUMBER_OF_OUTPUTS; i++) {
            Neuron neuron = new Neuron();
            neuron.setTransferFunction(getDefaultFunction());
            outputLayer.addNeuron(neuron);
        }
        ann = new NeuralNetwork();
        ann.addLayer(0, inputLayer);
        ann.addLayer(1, hiddenLayerOne);
        ConnectionFactory.fullConnect(ann.getLayerAt(0), ann.getLayerAt(1));
        ann.addLayer(2, outputLayer);
        ConnectionFactory.fullConnect(ann.getLayerAt(1), ann.getLayerAt(2));
        ConnectionFactory.fullConnect(ann.getLayerAt(0),
                ann.getLayerAt(ann.getLayersCount() - 1), false);
        ann.setInputNeurons(inputLayer.getNeurons());
        ann.setOutputNeurons(outputLayer.getNeurons());

        setRandomInputLayerWeight();
        setRandomHiddenLayerWeight();
        setRandomOutputLayerWeight();
    }

    public SingleNeuralNetwork(String weights) {
        this();
        String[] split = weights.split(",");
        List<Connection> allConnections = getAllConnections();
        if (allConnections.size() == split.length) {
            for (int i = 0; i < split.length; i++) {
                allConnections.get(i).getWeight().setValue(Double.valueOf(split[i]));
            }
        }
    }

    private TransferFunction getDefaultFunction() {
        return new Linear();
    }

    private void setRandomInputLayerWeight() {
        setRandomWeight(0);
    }

    private void setRandomHiddenLayerWeight() {
        setRandomWeight(1);
    }

    private void setRandomOutputLayerWeight() {
        setRandomWeight(2);
    }

    private void setRandomWeight(int layerIndex) {
        Layer inputLayer = ann.getLayerAt(layerIndex);
        for (Neuron neuron : inputLayer.getNeurons()) {
            for (Connection connection : neuron.getOutConnections()) {
                connection.setWeight(new Weight(GeneralUtils.randomValue()));
            }
        }
    }

    public OutputValue calculate(IInputValue inputValue) {
        ann.setInput(inputValue.getValues());
        ann.calculate();

        double[] output = ann.getOutput();
        return new OutputValue(output);
    }

    public void addScore(double scoreToAdd) {
        score += scoreToAdd;
        moves++;
    }

    public void incrementCollectedFoods() {
        collectedFoods++;
    }

    public void clearScore() {
        score = 0.0;
        moves = 0;
        collectedFoods = 0;
    }

    public Double getFinalScore() {
        return score + moves * 10 + collectedFoods * 10000;
    }

    public SingleNeuralNetwork clone() {
        return SerializationUtils.clone(this);
    }

    public void changeInputWeight(int weightIndex, double weight) {
        int numberOfConnections = getNumberOfConnections();
        if (weightIndex >= numberOfConnections) {
            throw new IndexOutOfBoundsException();
        }
        getAllConnections().get(weightIndex).setWeight(new Weight(weight));

    }

    public List<Connection> getAllConnections() {
        List<Connection> connections = new ArrayList<>();
        Layer hiddenLayer = ann.getLayerAt(1);
        for (Neuron neuron : hiddenLayer.getNeurons()) {
            connections.addAll(Arrays.asList(neuron.getInputConnections()));
        }
        for (Neuron neuron : hiddenLayer.getNeurons()) {
            connections.addAll(Arrays.asList(neuron.getOutConnections()));
        }
        return connections;
    }

    public int getNumberOfConnections() {
        return getAllConnections().size();
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof SingleNeuralNetwork) {
            return ((SingleNeuralNetwork) o).getFinalScore().compareTo(getFinalScore());
        }
        return -1;
    }

    @Override
    public String toString() {
        List<Double> weights = getAllConnections().stream()
                .map(Connection::getWeight)
                .map(Weight::getValue)
                .collect(Collectors.toList());

        return StringUtils.join(weights, ',');
    }
}
