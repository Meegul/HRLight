package edu.purdue.mbilstei.hrlight;

import android.text.format.Time;
import android.widget.TextView;

/**
 * Created by Michael on 1/31/16.
 */
public class HeartRateSensor {
    private double numRegistered;
    private float currentMax;
    private float currentMin;
    private Time started;
    float currentLight;
    double BPM;
    TextView lightText;
    TextView BPMText;
    HeartRateRunnable runObj;
    public HeartRateSensor(TextView lightText, TextView BPMText) {
        this.lightText = lightText;
        this.BPMText = BPMText;
        numRegistered = 0;
        currentMax = 0;
        started = new Time();
        started.setToNow();
        runObj = new HeartRateRunnable(this);
        new Thread(runObj).start();
    }

    public void calcCurrentBPM() {
        //This method is intended to run every 10 seconds.
        //It will not function properly if it is run more or less often.
        if (currentMax < 8)
            BPM =  numRegistered * 10;
        if (currentMax < 10)
            BPM = numRegistered * 8;
        else BPM = numRegistered * 6;
    }

    public void resetBPMText() {
        BPMText.setText("0");
    }

    public void setCurrentLight(float light) {
        currentLight = light;
        lightText.setText("Current Light: " + light);
    }

    public float getCurrentMax() {
        return currentMax;
    }

    public void updateBPMText() {
        BPMText.setText("" + (int) BPM);
    }

    public double getNumRegistered() {
        return numRegistered;
    }

    public boolean updateRegistered() {
        if (currentLight > currentMax)
            currentMax = currentLight;
        if (currentLight < currentMin)
            currentMin = currentLight;
        if (Math.abs(currentLight - currentMax) <= 2 && currentMax >= 2) {
            numRegistered++;
            return true;
        }
        return false;
    }

    public void resetMax() {
        started.setToNow();
        numRegistered = 0;
        currentMax = 0;
    }
}
