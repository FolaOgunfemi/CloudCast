package ogunfemi.folaranmi.cloudcast.ui;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.drawable.Drawable;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;

        import butterknife.BindView;
        import butterknife.ButterKnife;

        import butterknife.OnClick;
        import ogunfemi.folaranmi.cloudcast.R;
        import ogunfemi.folaranmi.cloudcast.weather.Current;
        import ogunfemi.folaranmi.cloudcast.weather.Day;
        import ogunfemi.folaranmi.cloudcast.weather.Forecast;
        import ogunfemi.folaranmi.cloudcast.weather.Hour;
        import okhttp3.Call;
        import okhttp3.Callback;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.Response;
////ADDITIONAL FEATURES - Add additional screens that each save a particular location as a default display for weather

//


////IMPORTANT API NOTE
  ///This issue is an intermittent problem with the JSON that gets pulled back from forecast.io. I left a couple of workarounds in the post you linked - have you tried those?

/*There is a problem with forecast.io where it sometimes omits the key/value pair from the data feed. So, when you try to access a value by using its key, this fails with a JSON Exception ("ie. no value for TemperatureMax")
*/
//Perform network functions outside of the main thread in an asynchronous thread. Main thread should be reserved for UI in order to ensure that the app is responsive to input at any time, even if some networking functions are still waiting to be completed.

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    // private Response response;
    public static final String DAILY_FORECAST = "DAILY_FORECAST";

    private Forecast mForecast;

   ////DELETE COMMENT @BindView(R.id.temperatureLabel) TextView mTimeLabel;

    //Setting member variable views here equal to the resources in layouts using ButterKnife
    @BindView(R.id.timeLabel) TextView mTimeLabel;
    @BindView(R.id.temperatureLabel) TextView mTemperatureLabel;
    @BindView(R.id.humidityValue) TextView mHumidityValue;
    @BindView(R.id.precipValue) TextView mPrecipValue;
    @BindView(R.id.summaryLabel) TextView mSummaryLabel;
    @BindView(R.id.iconImageView) ImageView mIconImageView;
    @BindView(R.id.refreshImageView) ImageView mRefreshImageView; //Added OnClickListener to have this imageView function like a button
    @BindView(R.id.progressBar) ProgressBar mProgressBar;  //Will superimpose the progressbar on top of the refresh button. It will show only when we get new data


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  //attaches layout to THIS activity
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE); //Will superimpose the progressbar on top of the refresh button. It will show only when we get new data

     ////EXPANSIONS
        //Prompt User for location in the form of an address or zip code.
        // Use Google Location API to geocode the address into latitude and longitude...https://www.googleapis.com/geolocation/v1/geolocate?key=MY_API_KEY
        //set these values to the variable below
        //Allow the user to save that location and a set number of others. These will be the separate pages of the app viewable via the action bar

        //Forecast Latitude and Longitude
        final double latitude = 37.8267;
        final double longitude = -122.4233;



        mRefreshImageView.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getForecast(latitude, longitude);
            }
        }  );

/*
        //This BoilerPlate action below can also be performed by ButterKnife bind statements as seen above
        private TextView mTemperatureLabel;
        mTemperatureLabel = (TextView)findViewById(R.id.temperatureLabel);*/


        //Below...Check to see that a network connection is available before making a network call
        //If true, run http request
        //If false, notify user via a Toast

        getForecast(latitude, longitude);

        Log.d(TAG, "Main UI Code is Running!");
    }



    private void getForecast( double latitude,double longitude) {
        String apiKey = "f5d3eda07bc28ebd39b109bd0a11d91b";

        String forecastUrl = "https://api.darksky.net/forecast/" + apiKey+ "/" + latitude + "," + longitude; // Full Sample URL for Alcatraz Weather. We have taken out the API Key and latitude longitude and set them to variables..."https://api.darksky.net/forecast/f5d3eda07bc28ebd39b109bd0a11d91b/37.8267,-122.4233"

        //Check to see that a network connection is available before making a network call
        if (isNetworkAvailable()) {

            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();

            Call call = client.newCall(request);
            //Use callback as the main line of communication between the main thread and the background worker thread(asynchronous thread).
            // After the worker has performed, If there is an issue, the onFailure method will be called and if the callback is valid then the onResponse will be called

            call.enqueue(new Callback() {

////LOGIC SUBSTITUTION: OnFailure and OnResponse should be capable of holding Request and "Call, Response" respectively. They ask for Calls however which I have included.

                @Override
                public void onFailure(Call call,IOException e) {

                    //Refresh the data...Must be run on UI thread.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }

                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //Needed to request internet connection permission in the Android Manifest

                    //Refresh the data...Must be run on UI thread.
///Beginning of adjusted OnResponse
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
/// Ending of adjusted OnResponse

////Previous way to run on UI-Thread  while using "call" as a parameter in the onResponse method
                    /*                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();

                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);


                        if (response.isSuccessful()) {
                            //   Log.v(TAG, response.body().string());
                            mForecast = parseForecastDetails(jsonData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });*/

                            /*updating display would ordinarily be sufficient, but in this case we must use runOnUiThread, bc updateDisplay would be a background process and only the main UI thread can update.     updateDisplay();  */

                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);

                    }

                }
            }); //end of callback


        } //end of network check conditional
        else {
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show(); //Test network issues with airplane mode in emulator
        }
    }

    private void toggleRefresh() { //called for each onFailure, OnResponse and networkIsAvailable
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE); //Will superimpose the progressbar on top of the refresh button. It will show only when we get new data
            mRefreshImageView.setVisibility(View.INVISIBLE); //Refresh will be invisible when progress is visible
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    //NOTE: ONLY MAIN THREAD IS ALLOWED TO UPDATE USER INTERFACE...
    //We use ACTIVITY.runOnUIThread( runnable object) to tell the main UI thread that we have code ready for it.

    private void updateDisplay() {
        Current current = mForecast.getCurrent();

        mTemperatureLabel.setText(current.getTemperature() + "");
        mTimeLabel.setText("At " + current.getFormattedTime() + " it will be");
        mHumidityValue.setText(current.getHumidity() + "");
        mPrecipValue.setText(current.getPrecipChance() + "%");
        mSummaryLabel.setText(current.getSummary());


/*       //getResources is deprecated.
    Drawable drawable = getResources().getDrawable(current.getIconId());
        mIconImageView.setImageDrawable(drawable);*/

/*        Drawable drawable = ResourcesCompat.getDrawable(getResources(), mCurrent.getIconId(), null);
        mIconImageView.setImageDrawable(drawable);*/

        Drawable drawable = ContextCompat.getDrawable(this, current.getIconId());
        mIconImageView.setImageDrawable(drawable);
    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException {

        Forecast forecast = new Forecast();

        forecast.setCurrent(getCurrentDetails(jsonData)); //Throws JsonException which is handled earlier
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));

        return forecast;

    }

    private Day[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("hourly");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for(int i = 0; i < data.length(); i++){
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();

            day.setSummary(jsonDay.getString("summary"));
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(timezone);

            days[i] = day;
        }
        return days;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];

            for(int i = 0; i < data.length(); i++){
                JSONObject jsonHour = data.getJSONObject(i);
                Hour hour = new Hour();

                hour.setSummary(jsonHour.getString("summary"));
                hour.setTemperature(jsonHour.getDouble("temperature"));
                hour.setIcon(jsonHour.getString("icon"));
                hour.setTime(jsonHour.getLong("time"));
                hour.setTimezone(timezone);

                hours[i] = hour;
            }
            return hours;
    }

    private Current getCurrentDetails(String jsonData)  throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON:" + timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();

        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(timezone);

        //display the formatted time in the blog, as it should appear in the app
        Log.d(TAG, current.getFormattedTime());
        return current;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE); //parameter is the name of service we want in either string format or referenced through the context CLASS as seen.

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //this also needs permission in the Android Manifest

        boolean isAvailable = false;
        if (networkInfo !=null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();

        dialog.show(getFragmentManager(), "error_dialog");  //this will show dialog and assigning a string tag to it
    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity (View view) {

        Intent intent = new Intent(this, DailyForecastActivity.class);
        //adding extra data to intent
        intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
        startActivity(intent);


        startActivity(intent);


    }
}
