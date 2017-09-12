package com.example.android.sp.FragmentsScreen3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.sp.R;
import com.example.android.sp.Stock;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

/**
 * This class contains the fragment that displays the predicted values for the next 5 working days
 */
public class PredictedChartFragment extends Fragment {

    private Stock stock;
    public PredictedChartFragment() {
    }

    /*
    Create new instance of the fragment that contains information about the selected stock
     */
    public static PredictedChartFragment newInstance(Stock stock) {
        PredictedChartFragment fragment = new PredictedChartFragment();
        fragment.stock = stock;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_predictedchart, container, false);

        //get the 5 day predicted values from stock object and put them to double array
        String[] valuesString = stock.getPredicted7Days();
        double[] values = new double[valuesString.length];
        for (int i = 0; i < valuesString.length; i++) {
            values[i] = Double.parseDouble(valuesString[i]);
        }
        Log.w("stock-viewer", "values.length = " + values.length);

        //creates datapoints to display on the graph
        DataPoint[] dataPionts = new DataPoint[]{
            new DataPoint(1, values[0]),
                new DataPoint(2, values[1]),
                new DataPoint(3, values[2]),
                new DataPoint(4, values[3]),
                new DataPoint(5, values[4]),
        };

        //initialize graph view and series and set up some customised settings
        GraphView graphView = (GraphView) rootView.findViewById(R.id.chart_pred);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPionts);
        graphView.addSeries(series);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(5.5);
        double [] minMax = findMinMax(values);
        graphView.getViewport().setMinY(minMax[0]-0.3);
        graphView.getViewport().setMaxY(minMax[1]+0.3);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(R.color.backGround_dark);
        series.setSpacing(15);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint dataPoint) {
                return R.color.backGround_main;
            }
        });
        return rootView;
    }

    /*
    Additional method to find min and max in order to help to display chart in the correct borders.
     */
    public static double[] findMinMax (double[] numbers){
        double[] minMax = new double[2];
        double max = Integer.MIN_VALUE;
        double min = Integer.MAX_VALUE;
        for(int i=0; i<numbers.length; i++)
        {
            if(numbers[i] < min)
                min = numbers[i];
            if(numbers[i] > max)
                max = numbers[i];
        }
        minMax[0]=min;
        minMax[1]=max;
        return minMax;
    }

    //setter for stock object
    public void setStock(Stock stock) {
        this.stock = stock;
    }
}

