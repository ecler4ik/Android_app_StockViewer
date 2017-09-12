package com.example.android.sp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.example.android.sp.FragmentsScreen3.PredictedChartFragment;
import com.example.android.sp.FragmentsScreen3.PriceChartFragment;
import com.example.android.sp.FragmentsScreen3.PriceInfoFragment;


public class Screen3_Activity extends FragmentActivity {

    TextView textViewCompany;
    TextView textViewticker;
    TextView textViewPrice;
    TextView textViewPriceChange;
    TextView textViewDate;
    TextView textViewTime;

    ViewPager mViewPager;
    StockInfoPagerAdapter mStockInfoPagerAdapter;
    Stock stock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_stockpage);

        //TextView textView = (TextView) findViewById(R.id.textVIEW);

        Intent b = getIntent();
        stock = b.getExtras().getParcelable("k");
        if (stock == null) {
            Log.d("stock-viewer", "stock == null");
        } else {
            Log.d("stock-viewer", "stock != null " + stock.toString());
        }
        //textView.setText(stock.toString());


        //Get VIEWS
        textViewCompany = (TextView) findViewById(R.id.tvCompany);
        textViewticker = (TextView) findViewById(R.id.tvCompanyTicker);
        textViewPrice = (TextView) findViewById(R.id.tvPrice);
        textViewPriceChange = (TextView) findViewById(R.id.tvPriceChange);
        textViewDate = (TextView) findViewById(R.id.tvTradeDate);
        textViewTime = (TextView) findViewById(R.id.tvTradeTime);

        // Sets company name, price, etc
        textViewCompany.setText(stock.getName());
        textViewticker.setText(stock.getTicker());
        textViewPrice.setText(stock.getPrice());
        textViewPriceChange.setText(stock.getPriceChange());
        textViewDate.setText(stock.getTradeDate());
        textViewTime.setText("Updated: " + stock.getTradeTime());

        // If price change is positive, set TextView to green. Otherwise Red.
        if (stock.getPriceDirChanged() >= 0)
            textViewPriceChange.setTextColor(getResources().getColor(R.color.change_green));
        else
            textViewPriceChange.setTextColor(getResources().getColor(R.color.change_red));

        // Sets up the ViewPager
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new StockInfoPagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(0);          // Set default starting page to the 1st item
        mViewPager.setOffscreenPageLimit(2);   // Keep all pages loaded

    }


    public class StockInfoPagerAdapter extends FragmentPagerAdapter {


        public StockInfoPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return PriceInfoFragment.newInstance(stock);
                case 1:
                    return PriceChartFragment.newInstance(stock);
                case 2:
                    return PredictedChartFragment.newInstance(stock);

                default:
                    return PriceInfoFragment.newInstance(stock);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return getResources().getString(R.string.price_info);
                case 1:
                    return getResources().getString(R.string.charts);
                case 2:
                    return getResources().getString(R.string.prediction);

                default:
                    return "What";
            }
        }
    }

}

