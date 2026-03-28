package roman.denysiuk;

import roman.denysiuk.model.DatasetSplit;
import roman.denysiuk.model.Observation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class PrepareDataset {
    private static final double TRAIN_RATIO = 0.7;

    private PrepareDataset() {
    }

    public static DatasetSplit trainTestSplit(List<Observation> dataset) {
        if (dataset == null || dataset.isEmpty()) {
            throw new IllegalArgumentException("Dataset must not be null or empty.");
        }

        List<Observation> setosa = new ArrayList<>();
        List<Observation> versicolor = new ArrayList<>();

        for (Observation observation : dataset) {
            if ("setosa".equals(observation.getLabel())) {
                setosa.add(observation);
            } else if ("versicolor".equals(observation.getLabel())) {
                versicolor.add(observation);
            }
        }

        if (setosa.isEmpty() || versicolor.isEmpty()) {
            throw new IllegalArgumentException("Dataset must include both setosa and versicolor observations.");
        }

        Random random = new Random(42);
        Collections.shuffle(setosa, random);
        Collections.shuffle(versicolor, random);

        List<Observation> train = new ArrayList<>();
        List<Observation> test = new ArrayList<>();

        splitClassSubset(setosa, train, test);
        splitClassSubset(versicolor, train, test);

        Collections.shuffle(train, random);
        Collections.shuffle(test, random);

        return new DatasetSplit(train, test);
    }

    private static void splitClassSubset(List<Observation> classSubset, List<Observation> train, List<Observation> test) {
        int trainClassSize = (int) Math.round(classSubset.size() * TRAIN_RATIO);

        for (int i = 0; i < classSubset.size(); i++) {
            if (i < trainClassSize) {
                train.add(classSubset.get(i));
            } else {
                test.add(classSubset.get(i));
            }
        }
    }
}
