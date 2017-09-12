package com.example.android.sp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/*
This class specifies how the Stock object will be created, contains all instance variables, getters
and setters, also it implements Parcelable interface that is used to send Stock objects between activities.
 */
public class Stock implements Parcelable {


    private String prevClose;       //previous Close
    private String open;            //today's open
    private String oneYearTarget;   //one year targen
    private String price;           //current price from Yahoo
    private String priceChange;     //absolute and relative price change
    private String marketCap;       //market capitalization
    private String tradeDate;       //current date
    private String daysRange;       //range in prices during the day
    private String yearRange;       //range in prices during the year
    private String tradeTime;       //last updated trade time
    private String name;            //company's name
    private String ticker;          //company's ticker
    private String avgDailyVolume;  //average daily volumes of equities
    private String volume;          //volume of stocks
    private String[] prices5;       //5 last prices
    private String[] dates5;        //5 last open dates
    private String[] dates90;       //approx 90 open dates
    private String[] prices90;      //approx 90 last prices
    private String predictedValue;  //predicted values for today's close
    private String[] predicted7Days;//predicted 7 days values

    /*
    Empty constructor
     */
    public Stock() {
    }

    /*
    deserialization of the Stock object
     */
    protected Stock(Parcel in) {
        prevClose = in.readString();
        open = in.readString();
        oneYearTarget = in.readString();
        price = in.readString();
        priceChange = in.readString();
        marketCap = in.readString();
        tradeDate = in.readString();
        predictedValue = in.readString();
        predicted7Days = in.createStringArray();
        daysRange = in.readString();
        yearRange = in.readString();
        avgDailyVolume = in.readString();
        volume = in.readString();
        tradeTime = in.readString();
        name = in.readString();
        ticker = in.readString();
        prices5 = in.createStringArray();
        dates5 = in.createStringArray();
        prices90 = in.createStringArray();
        dates90 = in.createStringArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /*
    Serialize Stock object
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(prevClose);
        dest.writeString(open);
        dest.writeString(oneYearTarget);
        dest.writeString(price);
        dest.writeString(priceChange);
        dest.writeString(marketCap);
        dest.writeString(tradeDate);
        dest.writeString(predictedValue);
        dest.writeStringArray(predicted7Days);
        dest.writeString(daysRange);
        dest.writeString(yearRange);
        dest.writeString(avgDailyVolume);
        dest.writeString(volume);
        dest.writeString(tradeTime);
        dest.writeString(name);
        dest.writeString(ticker);
        dest.writeStringArray(prices5);
        dest.writeStringArray(dates5);
        dest.writeStringArray(prices90);
        dest.writeStringArray(dates90);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };

    /*
     All getters and setters
     */

    public String[] getDates5() {
        return dates5;
    }

    public void setDates5(String[] dates5) {
        this.dates5 = dates5;
    }

    public String[] getDates90() {
        return dates90;
    }

    public void setDates90(String[] dates90) {
        this.dates90 = dates90;
    }

    public String[] getPrices90() {
        return prices90;
    }

    public void setPrices90(String[] prices90) {
        this.prices90 = prices90;
    }

    public String getPredictedValue() {
        return predictedValue;
    }

    public void setPredictedValue(String predictedValue) {
        this.predictedValue = predictedValue;
    }

    public String[] getPredicted7Days() {
        return predicted7Days;
    }

    public void setPredicted7Days(String[] predicted7Days) {
        this.predicted7Days = predicted7Days;
    }

    public String getDaysRange() {
        return daysRange;
    }

    public void setDaysRange(String daysRange) {
        this.daysRange = daysRange;
    }

    public String getYearRange() {
        return yearRange;
    }

    public void setYearRange(String yearRange) {
        this.yearRange = yearRange;
    }

    public String getAvgDailyVolume() {
        return avgDailyVolume;
    }

    public void setAvgDailyVolume(String avgDailyVolume) {
        this.avgDailyVolume = avgDailyVolume;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String[] getPrices5() {
        return prices5;
    }

    public void setPrices5(String[] prices5) {
        this.prices5 = prices5;
    }

    public String getPrevClose() {
        return prevClose;
    }

    public void setPrevClose(String prevClose) {
        this.prevClose = prevClose;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getOneYearTarget() {
        return oneYearTarget;
    }

    public void setOneYearTarget(String oneYearTarget) {
        this.oneYearTarget = oneYearTarget;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(String priceChange) {
        this.priceChange = priceChange;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getPriceDirChanged() {

        if (priceChange.length() > 0) {
            String d = priceChange.substring(0, 1);
            if (d.equals("-")) {
                return -1;
            } else if (d.equals("+")) {
                return 1;
            }
        }
        return 0;
    }


    @Override
    public String toString() {
        return "Stock{" +
                "prevClose='" + prevClose + '\'' +
                ", open='" + open + '\'' +
                ", oneYearTarget='" + oneYearTarget + '\'' +
                ", price='" + price + '\'' +
                ", priceChange='" + priceChange + '\'' +
                ", marketCap='" + marketCap + '\'' +
                ", tradeDate='" + tradeDate + '\'' +
                ", daysRange='" + daysRange + '\'' +
                ", yearRange='" + yearRange + '\'' +
                ", tradeTime='" + tradeTime + '\'' +
                ", name='" + name + '\'' +
                ", ticker='" + ticker + '\'' +
                ", avgDailyVolume='" + avgDailyVolume + '\'' +
                ", volume='" + volume + '\'' +
                ", prices5=" + Arrays.toString(prices5) +
                ", dates5=" + Arrays.toString(dates5) +
                ", dates90=" + Arrays.toString(dates90) +
                ", prices90=" + Arrays.toString(prices90) +
                ", predictedValue='" + predictedValue + '\'' +
                ", predicted7Days='" + predicted7Days + '\'' +
                '}';
    }
}
