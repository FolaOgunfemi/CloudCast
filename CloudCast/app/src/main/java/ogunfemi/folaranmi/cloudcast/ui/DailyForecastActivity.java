package ogunfemi.folaranmi.cloudcast.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.Arrays;

import ogunfemi.folaranmi.cloudcast.R;
import ogunfemi.folaranmi.cloudcast.adapters.DayAdapter;
import ogunfemi.folaranmi.cloudcast.weather.Day;

public class DailyForecastActivity extends ListActivity {
    //ListActivity provides additional functionality including display options for when fields in the list are empty

    private Day[] mDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

            Intent intent = getIntent();
            Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
            mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
            ////PARCELABLE IS INTERFACE THAT MAKES ANDROID DATA EASY TO TRANSFER FROM ONE THING TO ANOTHER (USUALLY ACTIVITIES). b/c of the way that activities are typically created and destroyed, simply passing an object between activities without implementing parcelable can lead to errors
                //The strategy is to serialize the data into common format that can be deserialized on the receiving end.


        //Array Adapter functions as a controller(MVC) that will take data and filter it for the View(MVC). It uses Generic types<>
            // parameters are ArrayAdapter<String>(context, oneOfMany PossibleListViewsDefinedInTheClass,arrayToFilter)
       DayAdapter adapter = new DayAdapter(this, mDays);
        setListAdapter(adapter);
        }

}
