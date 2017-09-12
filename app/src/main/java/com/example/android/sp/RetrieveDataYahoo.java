package com.example.android.sp;

import java.io.IOException;
import java.net.InterfaceAddress;
import java.net.MalformedURLException;
import java.net.URL;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by valeria on 10/08/2017.
 */

public class RetrieveDataYahoo {

    // Yahoo finance symbols
    //http://www.canbike.org/information-technology/yahoo-finance-url-download-to-a-csv-file.html
    final static String price           = "l1";
    final static String change          = "c";
    final static String prevClose       = "p";
    final static String open            = "o";
    final static String oneYrTarget     = "t8";
    final static String marketCap       = "j1";
    final static String tradeDate       = "d1";
    final static String tradeTime       = "t1";
    final static String daysRange       = "m";
    final static String yearRange       = "w";
    final static String avgDailyVolume  = "a2";
    final static String volume          = "v";
    final static String name            = "n";

    /*
    This method retrieves data from Yahoo finance (all specified details)
    and returns result as Stock object
     */
    public static Stock getStockFromYahoo(String ticker){
        Stock stock = new Stock();
        String[] stockInfo;
        try {
            URL urlYahoo = new URL ("http://download.finance.yahoo.com/d/quotes.csv?s="
                    + ticker
                    + "&f="
                    + price
                    + change
                    + prevClose
                    + open
                    + oneYrTarget
                    + marketCap
                    + tradeDate
                    + tradeTime
                    + avgDailyVolume
                    + volume
                    + daysRange
                    + yearRange
                    + name);

            // Open connection
            URLConnection connection = urlYahoo.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            String str = br.readLine();
            System.out.println(str);
            stockInfo =str.split(",");
            br.close();

            stock.setPrice(stockInfo[0]);
            stock.setPrice(String.format("$%.2f", Float.parseFloat(stockInfo[0])));
            String[] priceChange = stockInfo[1].replaceAll("\"", "").split(" - "); //correct
            stock.setPriceChange(priceChange[0] + " (" + priceChange[1] + ")");//correct
            stock.setPrevClose(stockInfo[2]); //correct
            stock.setOpen(stockInfo[3]); //correct
            stock.setOneYearTarget(stockInfo[4]); //correct
            stock.setMarketCap(stockInfo[5]); //correct
            stock.setTradeDate(stockInfo[6].replaceAll("\"", ""));
            stock.setTradeTime(stockInfo[7].replaceAll("\"", ""));
            stock.setAvgDailyVolume(stockInfo[8]);
            stock.setVolume(stockInfo[9]);
            stock.setDaysRange(stockInfo[10].replaceAll("\"", ""));
            stock.setYearRange(stockInfo[11].replaceAll("\"", ""));
            stock.setTicker(ticker);
            stock.setName(stockInfo[stockInfo.length-1].replaceAll("\"", "")); //correct
        } catch (Exception e) {
            Log.d("ERROR_GETTINGDATA", e.toString());
        }
        return stock;
    }


    /*
    This method retreives 5 days' historical prices from GOOGLE. These 5 values will be used for prediction
     */
    public static String[] get5HistoricalPrices(String ticker){
        ArrayList<String> prices = new ArrayList<>();
        try{
            URL url = new URL(createUrlfor5HistPrices(ticker));
            URLConnection urlConnection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            String [] temp;
            int count = 0;
            for (int i=0; i<=5; i++){
               line=br.readLine();
                if (count!=0){
                   temp = line.split(",");
                    prices.add(temp[4]);
               }
//            while ((line=br.readLine())!=null){
//                if (count!=0){
//                    temp = line.split(",");
//                    prices.add(temp[4]);
//                }
                count++;
            }
            br.close();
        } catch (Exception e) {
            e.toString();
        }
        String[] priceArray = prices.toArray(new String[prices.size()]);
        return priceArray;
    }

    /*
    This method create URL to retreive data (last 5 days historical days)
     */
    public static String createUrlfor5HistPrices(String ticker){
        Calendar calendar = Calendar.getInstance();
        String dayE = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)-1);
        String monthE = toMonth(calendar.get(Calendar.MONTH) + 1);
        String yearE = Integer.toString(calendar.get(Calendar.YEAR));

        Calendar calendarPast = addWorkDays(calendar, -10);
        String dayS = Integer.toString(calendarPast.get(Calendar.DAY_OF_MONTH));
        String monthS = toMonth(calendarPast.get(Calendar.MONTH) + 1);
        String yearS = Integer.toString(calendarPast.get(Calendar.YEAR));

        String url = "http://www.google.com/finance/historical?q="+ticker+"&startdate="+monthS+"+"+dayS+"%2c+"+yearS+
                "&enddate="+monthE+"+"+dayE+"%2c+"+yearE+"&output=csv";
        return url;
    }


    /*
    This method skips Saturday and Sunday. Is used to calculate the period of time that is used in URL
     */
    public static Calendar addWorkDays(final Calendar baseDate, final int days) {
        Calendar resDate = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate.getTime());
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

        // test for SAT
        if (currentDay == Calendar.SATURDAY) {
            // move to next FRIDAY
            calendar.add(Calendar.DAY_OF_MONTH, (days < 0 ? -1 : +2));
            currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        }
        // test for SUN
        if (currentDay == Calendar.SUNDAY) {
            // move to next FRIDAY
            calendar.add(Calendar.DAY_OF_MONTH, (days < 0 ? -2 : +1));
            currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        }

        // test for the week days
        if (currentDay >= Calendar.MONDAY && currentDay <= Calendar.FRIDAY) {
            boolean inCurrentWeek = false;
            if (days > 0)
                inCurrentWeek = (currentDay + days < 7);
            else
                inCurrentWeek = (currentDay + days > 1);

            if (inCurrentWeek) {
                calendar.add(Calendar.DAY_OF_MONTH, days);
                resDate = calendar;
            } else {
                int totalDays = 0;
                int daysInCurrentWeek = 0;

                // fill up current week.
                if (days > 0) {
                    daysInCurrentWeek = Calendar.SATURDAY - currentDay;
                    totalDays = daysInCurrentWeek + 2;
                } else {
                    daysInCurrentWeek = -(currentDay - Calendar.SUNDAY);
                    totalDays = daysInCurrentWeek - 2;
                }

                int restTotalDays = days - daysInCurrentWeek;
                // next working week... add 2 days for each week.
                int x = restTotalDays / 5;
                totalDays += restTotalDays + (x * 2);

                calendar.add(Calendar.DAY_OF_MONTH, totalDays);
                resDate = calendar;
            }
        }
        return resDate;
    }

    /*
    This method converts the numeric representation of the month to String format
     */
     public static String toMonth(int monthNumber){
        String monthString;
        switch (monthNumber) {
            case 1:  monthString = "Jan";       break;
            case 2:  monthString = "Feb";      break;
            case 3:  monthString = "Mar";         break;
            case 4:  monthString = "Apr";         break;
            case 5:  monthString = "May";           break;
            case 6:  monthString = "Jun";          break;
            case 7:  monthString = "Jul";          break;
            case 8:  monthString = "Aug";        break;
            case 9:  monthString = "Sep";     break;
            case 10: monthString = "Oct";       break;
            case 11: monthString = "Nov";      break;
            case 12: monthString = "Dec";      break;
            default: monthString = "Invalid month"; break;
        }
        return monthString;
    }

    /*
    This method retrieves from Yahoo last 90 days close values. They will be plotted on the graph
     */
    public static String[] get90HistoricalPrices(String ticker){
        ArrayList<String> prices = new ArrayList<>();
        try{
            URL url = new URL(buildUrlFor90HistDays(ticker));
            URLConnection urlConnection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            String [] temp;
            int count = 0;
            while ((line=br.readLine())!=null){
                if (count!=0){
                    temp = line.split(",");
                    prices.add(temp[4]);
                }
                count++;
            }

            br.close();
        } catch (Exception e) {
            e.toString();
        }
        String[] priceArray = prices.toArray(new String[prices.size()]);
        return priceArray;
    }

    /*
    This method retrieves last 90 days' dates from GOOGLE finance
     */
    public static String[] get90HistoricalDates(String ticker){
        ArrayList<String> dates = new ArrayList<>();
        try{
            URL url = new URL(buildUrlFor90HistDays(ticker));
            URLConnection urlConnection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            String [] temp;
            int count = 0;
            while ((line=br.readLine())!=null){
                if (count!=0){
                    temp = line.split(",");
                    dates.add(temp[0]);
                }
                count++;
            }
            br.close();
        } catch (Exception e) {
            Log.e("1", "get90HistoricalDates() error", e);
        }
        String[] datesArray = dates.toArray(new String[dates.size()]);
        return datesArray;
    }

    /*
    Additional method that helps to build the url to retrieve last 90 days' historical values from Google finance
     */
    public static String buildUrlFor90HistDays(String ticker){

        Calendar calendar = Calendar.getInstance();
        String dayE = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)-1);
        String monthE = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        String yearE = Integer.toString(calendar.get(Calendar.YEAR));

        Calendar calendarPast = addWorkDays(calendar, -90);
        String dayS = Integer.toString(calendarPast.get(Calendar.DAY_OF_MONTH));
        String monthS =  toMonth(calendarPast.get(Calendar.MONTH) + 1);
        String yearS = Integer.toString(calendarPast.get(Calendar.YEAR));
        String url = "http://www.google.com/finance/historical?q="+ticker+"&startdate="+monthS+"+"+dayS+"%2c+"+yearS+
                "&enddate="+monthE+"+"+dayE+"%2c+"+yearE+"&output=csv";
        return url;
    }




}
