package roman.denysiuk;

import roman.denysiuk.model.Observation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class IrisDatasetLoader {
    private IrisDatasetLoader() {
    }

    public static List<Observation> loadSetosaVersicolor() {
        List<Observation> observations = new ArrayList<>();

        try (InputStream stream = IrisDatasetLoader.class.getResourceAsStream("/iris.csv")) {
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
}
