# Mpp-Perceptrone-

Perceptron implementation (Delta Rule) using only Java base libraries.

## Implemented
- `Perceptron` class with `dimension`, `weights`, `threshold`, `alpha`, `beta`
- `predict(inputs)` and `train(inputs, labels)` methods
- learning rate passed as an argument during training
- epoch counting and epoch-by-epoch accuracy reporting
- stratified `70/30` train/test split in `PrepareDataset.trainTestSplit(dataset)`
- `EvaluationMetrics.measureAccuracy(realClasses, predictedClasses)`
- 2D console plot (ASCII) for decision boundary and test observations (petal length/width)
- console menu with options:
  1) Launch prediction
  2) Make new prediction on user data

## Design notes
- No inner classes are used.
- Responsibilities are split across dedicated classes (`IrisDatasetLoader`, `PrepareDataset`,
  `EvaluationMetrics`, `ConsolePlotter`, `Perceptron`).

## Run

```bash
javac -d /tmp/mpp-out $(find src/main/java -name '*.java')
java -cp /tmp/mpp-out:src/main/resources roman.denysiuk.Main
```
