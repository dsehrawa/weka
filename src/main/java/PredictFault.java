import java.io.*;

import java.util.List;

import org.joda.time.DateTime;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.timeseries.WekaForecaster;
import weka.filters.supervised.attribute.TSLagMaker;

public class PredictFault {

    public static void main(String[] args) {
        try {
            // path to the Australian wine data included with the time series forecasting
            // package
            String pathToWineData = weka.core.WekaPackageManager.PACKAGES_DIR.toString()
                    + File.separator + "timeseriesForecasting" + File.separator + "sample-data"
                    + File.separator + "fault.arff";

            // load the wine data
            Instances wine = new Instances(new BufferedReader(new FileReader(pathToWineData)));

            // new forecaster
            WekaForecaster forecaster = new WekaForecaster();

            // set the targets we want to forecast. This method calls
            // setFieldsToLag() on the lag maker object for us
            forecaster.setFieldsToForecast("printer,vpn");

            // default underlying classifier is SMOreg (SVM) - we'll use
            // gaussian processes for regression instead
            forecaster.setBaseForecaster(new NaiveBayes());

            forecaster.getTSLagMaker().setTimeStampField("timestamp"); // date time stamp
            forecaster.getTSLagMaker().setMinLag(1);
            forecaster.getTSLagMaker().setMaxLag(12); // monthly data

            // add a month of the year indicator field
            forecaster.getTSLagMaker().setAddMonthOfYear(true);

            // add a quarter of the year indicator field
            forecaster.getTSLagMaker().setAddQuarterOfYear(true);

            // build the model
            forecaster.buildForecaster(wine, System.out);

            // prime the forecaster with enough recent historical data
            // to cover up to the maximum lag. In our case, we could just supply
            // the 12 most recent historical instances, as this covers our maximum
            // lag period
            forecaster.primeForecaster(wine);



            // forecast for 12 units (months) beyond the end of the
            // training data
            List<List<NumericPrediction>> forecast = forecaster.forecast(12, System.out);

            DateTime currentDt = getCurrentDateTime(forecaster.getTSLagMaker());

            // output the predictions. Outer list is over the steps; inner list is over
            // the targets
            for (int i = 0; i < 12; i++) {
                List<NumericPrediction> predsAtStep = forecast.get(i);
                System.out.print(currentDt + " ");
                for (int j = 0; j < 2; j++) {
                    NumericPrediction predForTarget = predsAtStep.get(j);
                    System.out.print("" + predForTarget.predicted() + " ");
                }
                System.out.println();
                currentDt = advanceTime(forecaster.getTSLagMaker(), currentDt);
            }

            // we can continue to use the trained forecaster for further forecasting
            // by priming with the most recent historical data (as it becomes available).
            // At some stage it becomes prudent to re-build the model using current
            // historical data.

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static DateTime getCurrentDateTime(TSLagMaker lm) throws Exception {
        return new DateTime((long)lm.getCurrentTimeStampValue());
    }

    private static DateTime advanceTime(TSLagMaker lm, DateTime dt) {
        return new DateTime((long)lm.advanceSuppliedTimeValue(dt.getMillis()));
    }
}
