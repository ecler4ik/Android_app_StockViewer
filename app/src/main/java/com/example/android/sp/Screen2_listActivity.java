package com.example.android.sp;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;



import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * This class contains the activity for the second screen.
 */
public class Screen2_listActivity extends AppCompatActivity {

    //array of the tickers
    private final String[] stocks = {"GE", "C", "T", "MSFT", "AAPL", "FB", "INTC",
            "CSCO", "TWTR", "MS", "NVDA", "WMT", "UMC", "XOM", "HPQ", "AMD", "F", "BTI"};

    //initialized array of the stock objects. Will be filled while reading data from YAhoo and Google
    public static Stock[] mStocks = new Stock[18];

    //array with addresses of the compiled graphs. They are stored in Android/app/assets
    private static String[] model_file_array = {"file:///android_asset/optimized_ge_model.pb",
            "file:///android_asset/optimized_c_model.pb", "file:///android_asset/optimized_t_model.pb",
            "file:///android_asset/optimized_msft_model.pb", "file:///android_asset/optimized_aapl_model.pb",
            "file:///android_asset/optimized_fb_model.pb", "file:///android_asset/optimized_intc_model.pb",
            "file:///android_asset/optimized_csco_model.pb",
            "file:///android_asset/optimized_twtr_model.pb", "file:///android_asset/optimized_ms_model.pb",
            "file:///android_asset/optimized_nvda_model.pb", "file:///android_asset/optimized_wmt_model.pb",
            "file:///android_asset/optimized_umc_model.pb", "file:///android_asset/optimized_xom_model.pb",
            "file:///android_asset/optimized_hpq_model.pb", "file:///android_asset/optimized_amd_model.pb",
            "file:///android_asset/optimized_f_model.pb", "file:///android_asset/optimized_bti_model.pb"};

    private static final String INPUT_NODE = "inputN";
    private static final String OUTPUT_NODE = "output";
    private static final int[] INPUT_SIZE = {1, 5};
    //initialize the interference interface
    private TensorFlowInferenceInterface inferenceInterface;

    //import the tensorfow library
    static {
        System.loadLibrary("tensorflow_inference");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen2_list);

        new GetKeyStatsAsyncTask().execute(stocks);
    }


    //Inner class that is in charge to execute some action pre, in, post
    private class GetKeyStatsAsyncTask extends AsyncTask<String, String, Stock[]> {

        LinearLayout layout_listView = (LinearLayout) findViewById(R.id.fragm_sc2_stocks);
        FrameLayout fragment_container = (FrameLayout) findViewById(R.id.fragment_container);
        LinearLayout layout_intro = (LinearLayout) findViewById(R.id.layout_intro);

        /**
         * Setting progress br visible while the data is being recieved from Yahoo and google
         */
        @Override
        protected void onPreExecute() {
            layout_intro.setVisibility(View.VISIBLE);
            layout_listView.setVisibility(View.VISIBLE);
            fragment_container.setVisibility(View.INVISIBLE);
        }


        @Override
        protected Stock[] doInBackground(String... ticker) {
            if (isNetworkAvailable()) {
                try {
                    for (int i = 0; i < ticker.length; i++) {
                        Stock currentStock = RetrieveDataYahoo.getStockFromYahoo(ticker[i]);
                        currentStock.setPrices5(RetrieveDataYahoo.get5HistoricalPrices(ticker[i]));
                        currentStock.setDates90(RetrieveDataYahoo.get90HistoricalDates(ticker[i]));
                        currentStock.setPrices90(RetrieveDataYahoo.get90HistoricalPrices(ticker[i]));
                        mStocks[i] = currentStock;
                    }

                    //using tensorflow make a prediction for today's close
                    inferenceInterface = new TensorFlowInferenceInterface();
                    for (int i = 0; i < model_file_array.length; i++) {
                        inferenceInterface.initializeTensorFlow(getAssets(), model_file_array[i]);
                        float[] inputs_realValues = convertToFloat(mStocks[i].getPrices5());
                        float[] inputs = normalize(inputs_realValues);
                        inferenceInterface.fillNodeFloat(INPUT_NODE, INPUT_SIZE, inputs);
                        inferenceInterface.runInference(new String[]{OUTPUT_NODE});
                        float[] resu = {0, 0};
                        inferenceInterface.readNodeFloat(OUTPUT_NODE, resu);
                        mStocks[i].setPredictedValue(String.format("%.2f", (convertToReal(resu[0], inputs_realValues))));
                    }

                    //using tensorflow make a prediction for 5 working days ahead
                    for (int i = 0; i < model_file_array.length; i++) {
                        String[] predicted = new String[5];
                        inferenceInterface.initializeTensorFlow(getAssets(), model_file_array[i]);
                        float[] inputs_realValues = convertToFloat(mStocks[i].getPrices5());
                        float[] temp = normalize(inputs_realValues);
                        float[] temp2 = new float[5];
                        for (int j = 0; j < 5; j++) {
                            inferenceInterface.fillNodeFloat(INPUT_NODE, INPUT_SIZE, temp);
                            inferenceInterface.runInference(new String[]{OUTPUT_NODE});
                            float[] res = {0, 0};
                            inferenceInterface.readNodeFloat(OUTPUT_NODE, res);
                            predicted[j] = (String.format("%.2f", (convertToReal(res[0], inputs_realValues))));
                            temp2 = temp;
                            for (int t = 0; t < 1; t++) {
                                temp[t] = temp2[t + 1];
                                temp[t + 1] = temp2[t + 2];
                                temp[t + 2] = temp2[t + 3];
                                temp[t + 3] = temp2[t + 4];
                                temp[t + 4] = res[0];
                            }
                        }
                        mStocks[i].setPredicted7Days(predicted);
                    }


                } catch (Exception e) {
                    Log.e("YAHOO_ERROR", e.toString());
                }
            }
            return mStocks;
        }


        //display the listview with the corresponding information o
        protected void onPostExecute(Stock[] stocks) {

            layout_intro.setVisibility(View.INVISIBLE);
            //LIST VIEW SHOULD BE HERE TO BE DISPLAYED!!!
            final ListView listView = ((ListView) findViewById(R.id.lvSn2_stocks));
            SimpleAdapter simpleAdapter = new SimpleAdapter();
            listView.setAdapter(simpleAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view,
                                        int position, long arg3) {
                    Intent intent = new Intent(Screen2_listActivity.this, Screen3_Activity.class);
                    intent.putExtra("k", mStocks[position]);
                    startActivity(intent);
                }
            });

        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    class SimpleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mStocks.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_lv, null);
            TextView ticker = (TextView) convertView.findViewById(R.id.tv_ticker);
            TextView currentPrice = (TextView) convertView.findViewById(R.id.tv_current_value_display);
            TextView predictedPrice = (TextView) convertView.findViewById(R.id.tv_predicted_value_display);

            ticker.setText(mStocks[position].getTicker());
            currentPrice.setText(mStocks[position].getPrice());
            predictedPrice.setText(mStocks[position].getPredictedValue());

            return convertView;
        }
    }

    public static float[] findMinMax(float[] numbers) {
        float[] minMax = new float[2];
        float max = Integer.MIN_VALUE;
        float min = Integer.MAX_VALUE;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] < min)
                min = numbers[i];
            if (numbers[i] > max)
                max = numbers[i];
        }
        minMax[0] = min;
        minMax[1] = max;
        return minMax;
    }

    private static float[] normalize(float[] inputs) {
        float[] normilised = new float[inputs.length];
        float[] minMax = findMinMax(inputs);
        for (int i = 0; i < inputs.length; i++) {
            normilised[i] = (inputs[i] - minMax[0]) / (minMax[1] - minMax[0]);
        }
        return normilised;
    }

    private static float convertToReal(float num, float[] inputs) {
        float[] minMax = findMinMax(inputs);
        float numReal = num * (minMax[1] - minMax[0]) + minMax[0];
        return numReal;
    }

    private float[] convertToFloat(String[] input) {
        float[] output = new float[input.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = Float.parseFloat(input[i]);
        }
        return output;
    }

}


//stastictical test to test whaether two distribution are different

