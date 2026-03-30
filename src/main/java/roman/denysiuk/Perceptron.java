package roman.denysiuk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Perceptron {
    private final int dimension;
    private final double[] weights;
    private double threshold;
    private double alpha;
    private int beta;
    private int epochs;
    private final List<Double> accuracyByEpoch;

    public Perceptron(int dimension, double threshold, double alpha, int beta) {
        this.dimension = dimension;
        this.weights = new double[dimension];
        this.threshold = threshold;
        this.alpha = alpha;
        this.beta = beta;
        this.epochs = 0;
        this.accuracyByEpoch = new ArrayList<>();
    }

    public int predict(double[] inputs) {
        validateInputVector(inputs);

        double weightedSum = 0.0;
        for (int i = 0; i < dimension; i++) {
            weightedSum += weights[i] * inputs[i];
        }

        return weightedSum >= threshold ? 1 : 0;
    }

    public void train(double[][] inputs, int[] labels) {
        train(inputs, labels, alpha, inputs, labels);
    }

    public void train(double[][] inputs, int[] labels, double learningRate) {
        train(inputs, labels, learningRate, inputs, labels);
    }

    public void train(double[][] inputs, int[] labels, double learningRate, double[][] evalInputs, int[] evalLabels) {
        validateTrainingData(inputs, labels);
        validateTrainingData(evalInputs, evalLabels);

        epochs = 0;
        accuracyByEpoch.clear();
        boolean hasError;

        do {
            hasError = false;

            for (int i = 0; i < inputs.length; i++) {
                int prediction = predict(inputs[i]);
                int error = labels[i] - prediction;

                if (error != 0) {
                    hasError = true;
                }

                for (int j = 0; j < dimension; j++) {
                    weights[j] += learningRate * error * inputs[i][j];
                }
                threshold -= learningRate * error;
            }

            epochs++;
            int[] predicted = predictAll(evalInputs);
            accuracyByEpoch.add(EvaluationMetrics.measureAccuracy(evalLabels, predicted));
        } while (hasError && epochs < beta);

        alpha = learningRate;
    }

    public int[] predictAll(double[][] inputs) {
        if (inputs == null) {
            throw new IllegalArgumentException("Inputs must not be null.");
        }

        int[] predictions = new int[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            predictions[i] = predict(inputs[i]);
        }

        return predictions;
    }

    private void validateTrainingData(double[][] inputs, int[] labels) {
        if (inputs == null || labels == null) {
            throw new IllegalArgumentException("Inputs and labels must not be null.");
        }
        if (inputs.length != labels.length) {
            throw new IllegalArgumentException("Inputs and labels size mismatch.");
        }

        for (double[] sample : inputs) {
            validateInputVector(sample);
        }
    }

    private void validateInputVector(double[] inputs) {
        if (inputs == null || inputs.length != dimension) {
            throw new IllegalArgumentException("Each input vector must have dimension " + dimension + '.');
        }
    }

    public int getDimension() {
        return dimension;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getThreshold() {
        return threshold;
    }

    public double getAlpha() {
        return alpha;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public int getEpochs() {
        return epochs;
    }

    public List<Double> getAccuracyByEpoch() {
        return Collections.unmodifiableList(accuracyByEpoch);
    }
}
