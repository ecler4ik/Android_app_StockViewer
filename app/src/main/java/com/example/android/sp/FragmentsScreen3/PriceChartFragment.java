package com.example.android.sp.FragmentsScreen3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.android.sp.R;
import com.example.android.sp.Stock;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This fragment  displays the price chart for the current stock from Yahoo
 */

public class PriceChartFragment extends Fragment {

    private Stock stock;
    private GraphicalView mGraphicalView;

    public PriceChartFragment() {
    }
    //Create new instanse of the stock to display the historical prices
    public static PriceChartFragment newInstance(Stock stock) {
        PriceChartFragment fragment = new PriceChartFragment();
        fragment.setStock(stock);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pricechart, container, false);
        LinearLayout chartContainer = (LinearLayout) rootView.findViewById(R.id.chart_hist);

        //getting the last 90 historical prices from google and convert them to double
        String[] valuesString = stock.getPrices90();
        Double[] values = new Double[valuesString.length];
        for (int i = 0; i < valuesString.length; i++) {
            values[i] = Double.parseDouble(valuesString[i]);
        }
        Log.w("stock-viewer", "values.length = " + values.length);

        //getting the last 90 dates when there were trades on the current stock.
        // The length og the dates is equal to the length of the prices
        String[] ar = stock.getDates90();
        Date[] dates = convertStringToDate(ar);
        Log.w("stock-viewer", "dates.length = " + dates.length);

        //create Time series that contains the date and the related price
        TimeSeries timeSeries = new TimeSeries("TimeSeries");
        for (int i = 0; i < dates.length-1; i++) {
            timeSeries.add(dates[i], values[i]);
        }

        //create the graph and put series in it. Set up some customised settings
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(timeSeries);
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setDisplayChartValues(true);
        renderer.setColor(Color.BLACK);
        renderer.setPointStyle(PointStyle.SQUARE);
        renderer.setFillPoints(true);
        renderer.setDisplayChartValues(false);
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setZoomButtonsVisible(true);
        multiRenderer.addSeriesRenderer(renderer);
        mGraphicalView = ChartFactory.getTimeChartView(getActivity(), dataset, multiRenderer, "LINE");
        chartContainer.addView(mGraphicalView);
        return rootView;
    }

    /*
    This method converts the String array of dates to the specified format dd-MMM-yy
     */
    private static Date[] convertStringToDate(String[] datesString) {
        Date[] arrayOfDates = new Date[datesString.length];
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
        try {
            for (int i = 0; i < datesString.length; i++) {
                arrayOfDates[i] = formatter.parse(datesString[i]);
            }
        } catch (ParseException e) {
            Log.e("stock-viewer", "PriceChartFragment", e);
        }
        return arrayOfDates;
    }

    //setter for the stock object
    public void setStock(Stock stock) {
        this.stock = stock;
    }
}