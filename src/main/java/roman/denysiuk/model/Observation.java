package roman.denysiuk.model;

public class Observation {
    private final double[] data;
    private final String label;

    public Observation(double[] data, String label) {
        this.data = data;
        this.label = label;
    }

    public double[] getData() {
        return data;
    }

    public String getLabel() {
        return label;
    }

    public int getBinaryLabel() {
        return "setosa".equals(label) ? 1 : 0;
    }
}
