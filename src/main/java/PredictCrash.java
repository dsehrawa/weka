import java.io.*;

import java.util.List;

import org.joda.time.DateTime;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.timeseries.WekaForecaster;
import weka.filters.supervised.attribute.TSLagMaker;

public class PredictCrash {

    public static final int NUMBER_OF_ITERATIONS = 24;
    public static final int MIN_LAG = 12;
    public static final int MAX_LAG = 36;

    public static void main(String[] args) {
        try {
            // path to the Australian serviceToMonitor data included with the time series forecasting
            // package

            InputStream isa = PredictCrash.class.getClassLoader().getResourceAsStream("sample-data/formatted_data_30-days_wfshell.arff");

            // load the serviceToMonitor data
            Instances serviceToMonitor = new Instances(new BufferedReader(new InputStreamReader(isa)));

            // new forecaster
            WekaForecaster forecaster = new WekaForecaster();

            // set the targets we want to forecast. This method calls
            // setFieldsToLag() on the lag maker object for us
            forecaster.setFieldsToForecast("crashcount");

            // default underlying classifier is SMOreg (SVM) - we'll use
            // gaussian processes for regression instead
            forecaster.setBaseForecaster(new LinearRegression());

            forecaster.getTSLagMaker().setTimeStampField("crashtime"); // date time stamp
            forecaster.getTSLagMaker().setPeriodicity(TSLagMaker.Periodicity.HOURLY);
            forecaster.getTSLagMaker().setMinLag(MIN_LAG);
            forecaster.getTSLagMaker().setMaxLag(MAX_LAG); // monthly data


            // add a month of the year indicator field
            forecaster.getTSLagMaker().setAddMonthOfYear(true);

            // add a quarter of the year indicator field
            forecaster.getTSLagMaker().setAddQuarterOfYear(true);

            // build the model
            forecaster.buildForecaster(serviceToMonitor, System.out);

            // prime the forecaster with enough recent historical data
            // to cover up to the maximum lag. In our case, we could just supply
            // the 12 most recent historical instances, as this covers our maximum
            // lag period
            forecaster.primeForecaster(serviceToMonitor);


            // forecast for 12 units (months) beyond the end of the
            // training data
            DateTime currentDt = getCurrentDateTime(forecaster.getTSLagMaker());
            List<List<NumericPrediction>> forecast = forecaster.forecast(NUMBER_OF_ITERATIONS, System.out);

            // output the predictions. Outer list is over the steps; inner list is over
            // the targets
            for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
                currentDt = advanceTime(forecaster.getTSLagMaker(), currentDt);
                List<NumericPrediction> predsAtStep = forecast.get(i);
                System.out.print(currentDt + " ");
                for (int j = 0; j < 1; j++) {
                    NumericPrediction predForTarget = predsAtStep.get(j);
                    System.out.print("" + predForTarget.predicted() + " ");
                }
                System.out.println();
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
        return new DateTime((long) lm.getCurrentTimeStampValue());
    }

    private static DateTime advanceTime(TSLagMaker lm, DateTime dt) {
        return new DateTime((long) lm.advanceSuppliedTimeValue(dt.getMillis()));
    }
}
