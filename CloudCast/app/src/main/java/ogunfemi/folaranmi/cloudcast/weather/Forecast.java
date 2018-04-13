package ogunfemi.folaranmi.cloudcast.weather;

import ogunfemi.folaranmi.cloudcast.R;

/**
 * Created by FOLARANMI on 1/16/2018.
 * Uses data from website Forecast.io
 */

public class Forecast {
    private Current mCurrent;

    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }

    public Hour[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public void setHourlyForecast(Hour[] hourlyForecast) {
        mHourlyForecast = hourlyForecast;
    }

    public Day[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(Day[] dailyForecast) {
        mDailyForecast = dailyForecast;
    }

    private Hour[] mHourlyForecast;
    private Day[] mDailyForecast;


    public static int getIconId(String iconString) {
        //check all possible values based on forecast
        //one of the following values: clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night. (Developers should ensure that a sensible default is defined, as additional values, such as hail, thunderstorm, or tornado, may be defined in the future.)


            int iconId = R.drawable.clear_day;
            //default

            if (iconString.equals("clear-day")) {
                iconId = R.drawable.clear_day;
            }
            else if (iconString.equals("clear-night")) {
                iconId = R.drawable.clear_night;
            }
            else if (iconString.equals("rain")) {
                iconId = R.drawable.rain;
            }
            else if (iconString.equals("snow")) {
                iconId = R.drawable.snow;
            }
            else if (iconString.equals("sleet")) {
                iconId = R.drawable.sleet;
            }
            else if (iconString.equals("wind")) {
                iconId = R.drawable.wind;
            }
            else if (iconString.equals("fog")) {
                iconId = R.drawable.fog;
            }
            else if (iconString.equals("cloudy")) {
                iconId = R.drawable.cloudy;
            }
            else if (iconString.equals("partly-cloudy-day")) {
                iconId = R.drawable.partly_cloudy;
            }
            else if (iconString.equals("partly-cloudy-night")) {
                iconId = R.drawable.cloudy_night;
            }

            return iconId;


    }
}
