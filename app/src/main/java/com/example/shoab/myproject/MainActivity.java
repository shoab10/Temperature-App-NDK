package com.example.shoab.myproject;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.shoab.myproject.model.Forecast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    //List of forecast of each day
    private List<Forecast> forecasts;

    //UI Elements
    ListView listView;
    TextView sensorLabel,sensorReading;
    public static ToggleButton toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize UI Elements
        listView = (ListView) findViewById(R.id.listView);
        sensorReading = (TextView) findViewById(R.id.tempReading);

        //Initialize forecasts list
        forecasts = new ArrayList<>();
        forecasts.add(new Forecast("Monday", randInRangeInc(0,40 )));
        forecasts.add(new Forecast("Tuesday", randInRangeInc(0,40 )));
        forecasts.add(new Forecast("Wednesday", randInRangeInc(0,40 )));
        forecasts.add(new Forecast("Thursday", randInRangeInc(0,40 )));
        forecasts.add(new Forecast("Friday", randInRangeInc(0,40 )));
        
        //Ambient SensorManager
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor ambientTemperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(ambientTemperatureSensor != null) {
            sensorManager.registerListener(AmbientTemperaturSensorListener,
                                            ambientTemperatureSensor,
                                            sensorManager.SENSOR_DELAY_NORMAL);
        } else {
            sensorReading.setText("Ambient Sensor Not Available");
        }

        //Toggle Button listener
        toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    forecasts=farToCel(forecasts);
                    updateDisplay();
                } else {
                    forecasts=celToFar(forecasts);
                    updateDisplay();
                }
            }
        });

        //Updating the list view
        updateDisplay();
    }

    //Method to update the list view
    protected void updateDisplay() {
        ForecastAdapter adapter = new ForecastAdapter(this, R.layout.item_forecast, forecasts);
        listView.setAdapter(adapter);
    }

    //Sensor listener
    private final SensorEventListener AmbientTemperaturSensorListener
            = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                if (!toggle.isChecked()) {
                    sensorReading.setText("Ambient Temperature:" + cToF(event.values[0])+" " +(char) 0x00B0+ "F");
                }else{
                    sensorReading.setText("Ambient Temperature:" + event.values[0]+" " +(char) 0x00B0+ "C");
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            String accuracyMsg = "";
            switch (accuracy) {
                case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                    accuracyMsg = "Sensor has high accurary";
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                    accuracyMsg = "Sensor has medium accurary";
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                    accuracyMsg = "Sensor has low accurary";
                    break;
                case SensorManager.SENSOR_STATUS_UNRELIABLE:
                    accuracyMsg = "Sensor has unrealiable accurary";
                    break;
                default:
                    break;
            }
            Toast accuracyToast = Toast.makeText(getApplicationContext(),accuracyMsg,Toast.LENGTH_SHORT);
            accuracyToast.show();
        }
    };

    // JNI native methods to convert C to F and vice versa
    static {
        System.loadLibrary("MyLibrary");
    }
    public native List<Forecast> celToFar(List<Forecast> forecasts);
    public native List<Forecast> farToCel(List<Forecast> forecasts);
    public native float cToF(float c);
    public native float fToc(float f);

    //Method to get random number in range
    public static float randInRangeInc(int min, int max) {
        return min + (int) (Math.random() * ((1 + max) - min));
    }
}