package edu.purdue.mbilstei.hrlight;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        Context context = getApplicationContext();
        CharSequence text = "This started!";
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
        lightData = 0;
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        resetButton = (Button) findViewById(R.id.resetButton);
        BPMText = (TextView) findViewById(R.id.BPMText);
        lightLevelText = (TextView) findViewById(R.id.lightLevelText);
        lightCounterText = (TextView) findViewById(R.id.lightCounter);
        currentMaxText = (TextView) findViewById(R.id.currentMax);
        hr = new HeartRateSensor(mSensorManager, lightLevelText, BPMText);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hr.resetMax();
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
        hr.updateRegistered();
        hr.updateBPMText();
        lightCounterText.setText("Times Registered: " + hr.getNumRegistered());
        currentMaxText.setText("Current Max: " + hr.getCurrentMax());
    }

}
