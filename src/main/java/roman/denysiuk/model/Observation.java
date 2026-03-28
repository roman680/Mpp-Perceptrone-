package roman.denysiuk.model;

public class Observation {
    private double[] data;
    private String label;

    public Observation(double[] data, String label) {
        this.data = data;
        this.label = label;
    }

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
