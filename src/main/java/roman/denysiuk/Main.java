package roman.denysiuk;

import roman.denysiuk.model.DatasetSplit;
import roman.denysiuk.model.Observation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int FEATURE_X = 2; // petal length
    private static final int FEATURE_Y = 3; // petal width

    public static void main(String[] args) {
        TrainedSession session = prepareSession();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Launch prediction");
                System.out.println("2. Make new prediction on user data");
                System.out.println("0. Exit");
                System.out.print("Your choice: ");

                String choice = scanner.nextLine().trim();

                if ("1".equals(choice)) {
                    launchPrediction(session);
                } else if ("2".equals(choice)) {
                    makePredictionOnUserData(scanner, session.perceptron);
                } else if ("0".equals(choice)) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Unknown option. Please choose 1, 2, or 0.");
                }
            }
        }
    }

    private static TrainedSession prepareSession() {
        List<Observation> observations = loadIrisSubset();
        DatasetSplit split = PrepareDataset.trainTestSplit(observations);

        double[][] trainInputs = selectFeatures(split.getTrainDataset());
        int[] trainLabels = toLabels(split.getTrainDataset());

        double[][] testInputs = selectFeatures(split.getTestDataset());
        int[] testLabels = toLabels(split.getTestDataset());

        Perceptron perceptron = new Perceptron(2, 0.0, 0.1, 1000);
        perceptron.train(trainInputs, trainLabels, 0.1, testInputs, testLabels);

        return new TrainedSession(perceptron, trainInputs, testInputs, testLabels);
    }

    private static void launchPrediction(TrainedSession session) {
        int[] predictions = session.perceptron.predictAll(session.testInputs);
        double testAccuracy = EvaluationMetrics.measureAccuracy(session.testLabels, predictions);

        System.out.println("Train samples: " + session.trainInputs.length);
        System.out.println("Test samples: " + session.testInputs.length);
        System.out.println("Epochs: " + session.perceptron.getEpochs());

        for (int i = 0; i < session.perceptron.getAccuracyByEpoch().size(); i++) {
            System.out.printf("Epoch %d accuracy: %.4f%n", i + 1, session.perceptron.getAccuracyByEpoch().get(i));
        }

        System.out.printf("Final test accuracy: %.4f%n", testAccuracy);
        printDecisionBoundary(session.perceptron);
    }

    private static void printDecisionBoundary(Perceptron perceptron) {
        double[] w = perceptron.getWeights();
        if (w.length < 2 || Math.abs(w[1]) < 1.0e-9) {
            System.out.println("Decision boundary: cannot be represented as y = ax + b (w2 is zero).");
            return;
        }

        double a = -w[0] / w[1];
        double b = perceptron.getThreshold() / w[1];
        System.out.printf("Decision boundary (petal_width = a * petal_length + b): y = %.4fx + %.4f%n", a, b);
    }

    private static void makePredictionOnUserData(Scanner scanner, Perceptron perceptron) {
        System.out.println("\nEnter a new observation to predict class.");
        System.out.println("Use two attributes: petal length and petal width.");

        try {
            System.out.print("Petal length: ");
            double petalLength = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Petal width: ");
            double petalWidth = Double.parseDouble(scanner.nextLine().trim());

            int prediction = perceptron.predict(new double[]{petalLength, petalWidth});
            String predictedLabel = prediction == 1 ? "setosa" : "versicolor";

            System.out.println("Predicted class: " + predictedLabel + " (" + prediction + ")");
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input. Please try again.");
        }
    }

    private static double[][] selectFeatures(List<Observation> dataset) {
        double[][] inputs = new double[dataset.size()][2];

        for (int i = 0; i < dataset.size(); i++) {
            double[] data = dataset.get(i).getData();
            inputs[i][0] = data[FEATURE_X];
            inputs[i][1] = data[FEATURE_Y];
        }

        return inputs;
    }

    private static int[] toLabels(List<Observation> dataset) {
        int[] labels = new int[dataset.size()];

        for (int i = 0; i < dataset.size(); i++) {
            labels[i] = dataset.get(i).getBinaryLabel();
        }

        return labels;
    }

    private static List<Observation> loadIrisSubset() {
        List<Observation> observations = new ArrayList<>();

        try (InputStream stream = Main.class.getResourceAsStream("/iris.csv")) {
            if (stream == null) {
                throw new IllegalStateException("Could not load iris.csv from resources.");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                String line;
                boolean isHeader = true;

                while ((line = reader.readLine()) != null) {
                    if (isHeader) {
                        isHeader = false;
                        continue;
                    }

                    String[] parts = line.split(",");
                    String species = parts[4].trim();

                    if (!"setosa".equals(species) && !"versicolor".equals(species)) {
                        continue;
                    }

                    double[] features = new double[]{
                            Double.parseDouble(parts[0]),
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])
                    };

                    observations.add(new Observation(features, species));
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read iris.csv.", e);
        }

        return observations;
    }

    private static final class TrainedSession {
        private final Perceptron perceptron;
        private final double[][] trainInputs;
        private final double[][] testInputs;
        private final int[] testLabels;

        private TrainedSession(Perceptron perceptron, double[][] trainInputs, double[][] testInputs, int[] testLabels) {
            this.perceptron = perceptron;
            this.trainInputs = trainInputs;
            this.testInputs = testInputs;
            this.testLabels = testLabels;
        }
    }
}
