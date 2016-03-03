package com.example.shoab.myproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shoab.myproject.model.Forecast;
import java.util.List;

/**
 * Adapter class to generate the list view
 */
public class ForecastAdapter extends ArrayAdapter<Forecast> {
    private Context context;
    private List<Forecast> forecasts;
    public ForecastAdapter(Context context, int resource, List<Forecast> objects) {
        super(context, resource, objects);
        this.context = context;
        this.forecasts = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_forecast, parent, false);

        //Display forecast in the TextView widget
        Forecast forecast = forecasts.get(position);
        TextView textView1 = (TextView) view.findViewById(R.id.textView1);
        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        textView1.setText(forecast.getDay());
        if(MainActivity.toggle.isChecked()){
            textView2.setText("Temp: "+forecast.getTemp()+" " +(char) 0x00B0+ "C" );
        }else {
            textView2.setText("Temp: "+forecast.getTemp()+" " +(char) 0x00B0+ "F" );
        }

        return view;
    }
}
