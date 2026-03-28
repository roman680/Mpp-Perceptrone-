# Mpp-Perceptrone-

Perceptron implementation (Delta Rule) using only Java base libraries.

## Implemented
- `Perceptron` class with `dimension`, `weights`, `threshold`, `alpha`, `beta`
- `predict(inputs)` and `train(inputs, labels)` methods
- learning rate passed as an argument during training
- epoch counting
- stratified `70/30` train/test split in `PrepareDataset.trainTestSplit(dataset)`
- `EvaluationMetrics.measureAccuracy(realClasses, predictedClasses)`
- epoch-by-epoch accuracy printing
- console output of decision-boundary equation (petal length/width)
- console menu with options:
  1) Launch prediction
  2) Make new prediction on user data

## Run

```bash
javac -d /tmp/mpp-out $(find src/main/java -name '*.java')
java -cp /tmp/mpp-out:src/main/resources roman.denysiuk.Main
```
