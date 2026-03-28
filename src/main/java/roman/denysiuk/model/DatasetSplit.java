package roman.denysiuk.model;

import java.util.List;

public class DatasetSplit {
    private final List<Observation> trainDataset;
    private final List<Observation> testDataset;

    public DatasetSplit(List<Observation> trainDataset, List<Observation> testDataset) {
        this.trainDataset = trainDataset;
        this.testDataset = testDataset;
    }

    public List<Observation> getTrainDataset() {
        return trainDataset;
    }

    public List<Observation> getTestDataset() {
        return testDataset;
    }
}
