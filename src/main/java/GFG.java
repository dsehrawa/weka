import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

// Main class
// BreastCancer
public class GFG {

    // Main driver method
    public static void main(String args[])
    {

        // Try block to check for exceptions
        try {

            // Create J48 classifier by
            // creating object of J48 class
            J48 j48Classifier = new J48();

            // Dataset path
            String breastCancerDataset
                    = "breast-cancer.arff";

            // Creating bufferedreader to read the dataset
            BufferedReader bufferedReader
                    = new BufferedReader(
                    new FileReader(breastCancerDataset));

            // Create dataset instances
            Instances datasetInstances
                    = new Instances(bufferedReader);

            // Set Target Class
            datasetInstances.setClassIndex(
                    datasetInstances.numAttributes() - 1);

            // Evaluating by creating object of Evaluation
            // class
            Evaluation evaluation
                    = new Evaluation(datasetInstances);
            j48Classifier.buildClassifier(datasetInstances);

            // Cross Validate Model with 10 folds
            evaluation.crossValidateModel(
                    j48Classifier, datasetInstances, 10,
                    new Random(1));

            System.out.println(evaluation.toSummaryString(
                    "\nResults", false));

            for(int i=0;i<datasetInstances.size();i++) {
                System.out.println("Actual:" + datasetInstances.get(i).classValue());
                System.out.println("eval:" + evaluation.evaluateModelOnce(j48Classifier, datasetInstances.get(i)));
                double[] attValues = datasetInstances.get(i).toDoubleArray();
                Instance dataInstance = new DenseInstance(1.0, attValues);
                dataInstance.setDataset(datasetInstances);
                System.out.println("wow eval:" + evaluation.evaluateModelOnce(j48Classifier, dataInstance));
            }
        }

        // Catch block to handle the exceptions
        catch (Exception e) {

            // Print message on the console
            System.out.println("Error Occurred!!!! \n"
                    + e.getMessage());
        }
    }
}