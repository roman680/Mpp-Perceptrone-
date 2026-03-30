package roman.denysiuk;

public final class EvaluationMetrics {
    private EvaluationMetrics() {
    }

    public static double measureAccuracy(int[] realClasses, int[] predictedClasses) {
        if (realClasses == null || predictedClasses == null) {
            throw new IllegalArgumentException("Class arrays must not be null.");
        }
        if (realClasses.length != predictedClasses.length) {
            throw new IllegalArgumentException("Class arrays must have the same size.");
        }
        if (realClasses.length == 0) {
            throw new IllegalArgumentException("Class arrays must not be empty.");
        }

        int correct = 0;
        for (int i = 0; i < realClasses.length; i++) {
            if (realClasses[i] == predictedClasses[i]) {
                correct++;
            }
        }

        return (double) correct / realClasses.length;
    }
}
