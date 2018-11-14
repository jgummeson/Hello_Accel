package com.example.gummeson.myapplication;

import android.content.Intent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView textView;
    private Switch accelSwitch;
    private Intent serviceIntent;

    private TextView xView;
    private TextView yView;
    private TextView zView;

    SensorManager sensorManager;
    Sensor accelerometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.title_TextView);
        textView.setText(getString(R.string.app_text));

        xView = (TextView) findViewById(R.id.xval);
        yView = (TextView) findViewById(R.id.yval);
        zView = (TextView) findViewById(R.id.zval);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else {
            // fail! we dont have an accelerometer!
        }


        accelSwitch = (Switch) findViewById(R.id.accel_switch);
        accelSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean enabled) {
                if (enabled) {
                    textView.setText("Accelerometer started!");
                    sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    textView.setText("Accelerometer stopped!");
                    sensorManager.unregisterListener(MainActivity.this, accelerometer);
                }
            }
        });
    }


    @Override
    public void onAccuracyChanged(Sensor accelerometer, int accuracy) {
        //shouldn't happen in practice but needs to be handled.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        xView.setText(Float.toString(x));
        yView.setText(Float.toString(y));
        zView.setText(Float.toString(z));
    }
}