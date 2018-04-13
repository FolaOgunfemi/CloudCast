package ogunfemi.folaranmi.cloudcast.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ogunfemi.folaranmi.cloudcast.R;
import ogunfemi.folaranmi.cloudcast.weather.Day;

import static ogunfemi.folaranmi.cloudcast.R.id.imgTempCircle;

/**
 * Created by FOLARANMI on 1/29/2018.
 */

////ADAPTERS adapters take responsibility for creating Views using certain specifications


////"JUST-IN-TIME APPROACH...SMOOTH SCROLLING:
    /// Rather than creating memory space for the dozens of views that will be created, we can resuse the memory space for items that have scrolled off of the screen, for items that scroll onto it.
     ///wE CREATE ONE SET OF VIEWS FOR THE NUMBER OF VIEWS THAT FIT ON THE SCREEN (ie 4), and We reuse those containers over and over again by refilling it with new data
        ///Done using ViewHolders


public class DayAdapter extends BaseAdapter {

    // This Adapter will need to know the context and the data(days)


    private Context mContext;
    private Day[] mDays;


    public DayAdapter(Context context, Day[] days) {
        mContext = context;
        mDays = days;
    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int i) {
        return 0;  //No need to use this. Tag items for easy reference
    }

    ///Get View is called for each item in the list.
    ///ViewHolders - Lets us reuse the same objects for views
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView IS the View Object that we want to REuse. Initially, it will be null and we need code to create it. If not null, we should reUSE it
        ViewHolder holder;




        if (convertView == null) {
            //brand new convertView
            //Will use a layoutInflater....an android object that transforms xml layouts to Views. It need to be fetched from the context

            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);

            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);

            //adjustment
            holder.imgTempCircle= (ImageView) convertView.findViewById(imgTempCircle);

            holder.imgTempCircle.setImageResource(R.drawable.bg_temperature);
            //adjustment

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();


        }

        Day day = mDays[position];

        holder.iconImageView.setImageResource(day.getIconId());
        holder.temperatureLabel.setText(day.getTemperatureMax() + "");
        holder.dayLabel.setText(day.getDayOfTheWeek());


        return convertView;


    }
        private static class ViewHolder {
            ImageView iconImageView; //public by default
            TextView temperatureLabel;
            TextView dayLabel;

            //adjustment
            ImageView imgTempCircle;
            //adjustment
        }


    }


