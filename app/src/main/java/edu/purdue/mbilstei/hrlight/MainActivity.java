package edu.purdue.mbilstei.hrlight;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor lightSensor;
    public float lightData;
    Button resetButton;
    TextView lightLevelText;
    TextView lightCounterText;
    TextView currentMaxText;
    TextView BPMText;
    HeartRateSensor hr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lightData = 0;
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        resetButton = (Button) findViewById(R.id.resetButton);
        BPMText = (TextView) findViewById(R.id.BPMText);
        lightLevelText = (TextView) findViewById(R.id.lightLevelText);
        lightCounterText = (TextView) findViewById(R.id.lightCounter);
        currentMaxText = (TextView) findViewById(R.id.currentMax);
        hr = new HeartRateSensor(lightLevelText, BPMText);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hr.resetMax();
                //Intent intent = new Intent(v.getContext(), ZoomActivity.class);
                //startActivity(intent);
            }
        });

    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        hr.setCurrentLight(event.values[0]);
        if (hr.updateRegistered())
        //    beatHeart(findViewById(R.id.heartView));
        hr.updateBPMText();
        lightCounterText.setText("Times Registered: " + hr.getNumRegistered());
        currentMaxText.setText("Current Max: " + hr.getCurrentMax());

    }

    public void beatHeart(View v) {
        Intent intent = new Intent(v.getContext(), ZoomActivity.class);
        startActivity(intent);
    }

}
