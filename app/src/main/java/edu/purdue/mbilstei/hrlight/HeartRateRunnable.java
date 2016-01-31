package edu.purdue.mbilstei.hrlight;

/**
 * Created by Michael on 1/31/16.
 */
public class HeartRateRunnable implements Runnable {


    HeartRateSensor heartRateSensor;
    public HeartRateRunnable(HeartRateSensor hrs){
        super();
        heartRateSensor = hrs;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {

            }
            heartRateSensor.calcCurrentBPM();
            heartRateSensor.resetMax();
        }
    }
}
