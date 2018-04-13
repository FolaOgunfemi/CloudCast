package ogunfemi.folaranmi.cloudcast.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ogunfemi.folaranmi.cloudcast.R;

/**
 * This class retrieves the current weather conditions.
 * Uses data from website Forecast.io

 */

public class Current {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;
    private String mTimeZone;

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }



    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }


    public int getIconId(){
    //check all possible values based on forecast
    //one of the following values: clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night. (Developers should ensure that a sensible default is defined, as additional values, such as hail, thunderstorm, or tornado, may be defined in the future.)
    //calling static method from Forecast


        return Forecast.getIconId(mIcon);
        }


    public long getTime() {
        return mTime;
    }


    public String getFormattedTime(){

        SimpleDateFormat formatter = new SimpleDateFormat( "h:mm a");

        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));

        //convert seconds to milliseconds
        Date dateTime = new Date(getTime() * 1000);

        String timeString = formatter.format(dateTime);

        return timeString;
    }


    public void setTime(long time) {
        mTime = time;
    }

    public int getTemperature() {
        return (int) Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public double getPrecipChance() {

        double precipPercentage = mPrecipChance * 100;

        return (int) Math.round(precipPercentage);
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}
