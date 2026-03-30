package roman.denysiuk.model;

import roman.denysiuk.Perceptron;

public class TrainingSession {
    private final Perceptron perceptron;
    private final double[][] trainInputs;
    private final int[] trainLabels;
    private final double[][] testInputs;
    private final int[] testLabels;

    public TrainingSession(Perceptron perceptron, double[][] trainInputs, int[] trainLabels,
                           double[][] testInputs, int[] testLabels) {
        this.perceptron = perceptron;
        this.trainInputs = trainInputs;
        this.trainLabels = trainLabels;
        this.testInputs = testInputs;
        this.testLabels = testLabels;
    }

    public Perceptron getPerceptron() {
        return perceptron;
    }

    public double[][] getTrainInputs() {
        return trainInputs;
    }

    public int[] getTrainLabels() {
        return trainLabels;
    }

    public double[][] getTestInputs() {
        return testInputs;
    }

    public int[] getTestLabels() {
        return testLabels;
    }
}
