package com.example.android.sp.FragmentsScreen3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.sp.R;
import com.example.android.sp.Stock;

/*
This class contains the fragment that displays the statistical information about selected stock
 */
public class PriceInfoFragment extends Fragment {

    TextView textViewPreviousClose;     //previous close
    TextView textViewOpen;              //open price
    TextView textViewVolume;            //volume of the equities on the market
    TextView textViewAverageDVolume;    //average daily volume of the equities
    TextView textViewDaysRange;         //range in price within the day
    TextView textViewYearRange;         //range in price within the year
    TextView textViewMCapitaliz;        //market capitalization
    TextView textViewOneYearTarget;     //one year target
    Stock stock;

    public PriceInfoFragment() {
    }

    //creates instance of the fragment with the data of the selected stock
    public static PriceInfoFragment newInstance(Stock stock) {
        PriceInfoFragment fragment = new PriceInfoFragment();
        fragment.stock = stock;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_priceinfo, container, false);
        //initialize the text views
        textViewPreviousClose = (TextView) rootView.findViewById(R.id.tvPrevClose);
        textViewOpen = (TextView) rootView.findViewById(R.id.tvOpen);
        textViewAverageDVolume = (TextView) rootView.findViewById(R.id.tvAvgDailyVolume);
        textViewVolume = (TextView) rootView.findViewById(R.id.tvVolume);
        textViewDaysRange = (TextView) rootView.findViewById(R.id.tvDaysRange);
        textViewYearRange = (TextView) rootView.findViewById(R.id.tvYearRange);
        textViewMCapitaliz = (TextView) rootView.findViewById(R.id.tvMarketCap);
        textViewOneYearTarget = (TextView) rootView.findViewById(R.id.tvOneYearTarget);

        //set the data to the text view
        textViewPreviousClose.setText(stock.getPrevClose());
        textViewOpen.setText(stock.getOpen());
        textViewAverageDVolume.setText(stock.getAvgDailyVolume());
        textViewVolume.setText(stock.getVolume());
        textViewDaysRange.setText(stock.getDaysRange());
        textViewYearRange.setText(stock.getYearRange());
        textViewMCapitaliz.setText(stock.getMarketCap());
        textViewOneYearTarget.setText(stock.getOneYearTarget());
        return rootView;
    }

}