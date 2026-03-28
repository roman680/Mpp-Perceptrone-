package roman.denysiuk;

public final class ConsolePlotter {
    private static final int WIDTH = 70;
    private static final int HEIGHT = 20;

    private ConsolePlotter() {
    }

    public static void plot(double[][] testInputs, int[] testLabels, Perceptron perceptron) {
        if (testInputs.length == 0) {
            System.out.println("No test points available for plotting.");
            return;
        }

        double minX = min(testInputs, 0);
        double maxX = max(testInputs, 0);
        double minY = min(testInputs, 1);
        double maxY = max(testInputs, 1);

        char[][] grid = new char[HEIGHT][WIDTH];
        fillGrid(grid, '.');

        drawDecisionBoundary(grid, minX, maxX, minY, maxY, perceptron);
        drawPoints(grid, testInputs, testLabels, minX, maxX, minY, maxY);

        System.out.println("\nASCII plot (x=petal length, y=petal width)");
        for (char[] row : grid) {
            System.out.println(new String(row));
        }
        System.out.println("Legend: S=setosa, V=versicolor, *=decision boundary");
    }

    private static void drawDecisionBoundary(char[][] grid, double minX, double maxX, double minY,
                                             double maxY, Perceptron perceptron) {
        double[] w = perceptron.getWeights();
        if (w.length < 2 || Math.abs(w[1]) < 1.0e-9) {
            return;
        }

        for (int col = 0; col < WIDTH; col++) {
            double x = denormalize(col, 0, WIDTH - 1, minX, maxX);
            double y = (perceptron.getThreshold() - w[0] * x) / w[1];
            int row = mapToRow(y, minY, maxY);

            if (row >= 0 && row < HEIGHT) {
                grid[row][col] = '*';
            }
        }
    }

    private static void drawPoints(char[][] grid, double[][] testInputs, int[] testLabels,
                                   double minX, double maxX, double minY, double maxY) {
        for (int i = 0; i < testInputs.length; i++) {
            int col = mapToColumn(testInputs[i][0], minX, maxX);
            int row = mapToRow(testInputs[i][1], minY, maxY);

            if (row >= 0 && row < HEIGHT && col >= 0 && col < WIDTH) {
                grid[row][col] = testLabels[i] == 1 ? 'S' : 'V';
            }
        }
    }

    private static int mapToColumn(double x, double minX, double maxX) {
        if (Math.abs(maxX - minX) < 1.0e-9) {
            return 0;
        }
        return (int) Math.round(((x - minX) / (maxX - minX)) * (WIDTH - 1));
    }

    private static int mapToRow(double y, double minY, double maxY) {
        if (Math.abs(maxY - minY) < 1.0e-9) {
            return HEIGHT - 1;
        }
        double normalized = (y - minY) / (maxY - minY);
        return HEIGHT - 1 - (int) Math.round(normalized * (HEIGHT - 1));
    }

    private static double denormalize(double value, double sourceMin, double sourceMax,
                                      double targetMin, double targetMax) {
        if (Math.abs(sourceMax - sourceMin) < 1.0e-9) {
            return targetMin;
        }
        double ratio = (value - sourceMin) / (sourceMax - sourceMin);
        return targetMin + ratio * (targetMax - targetMin);
    }

    private static void fillGrid(char[][] grid, char value) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col] = value;
            }
        }
    }

    private static double min(double[][] array, int index) {
        double min = Double.POSITIVE_INFINITY;
        for (double[] values : array) {
            min = Math.min(min, values[index]);
        }
        return min;
    }

    private static double max(double[][] array, int index) {
        double max = Double.NEGATIVE_INFINITY;
        for (double[] values : array) {
            max = Math.max(max, values[index]);
        }
        return max;
    }
}
