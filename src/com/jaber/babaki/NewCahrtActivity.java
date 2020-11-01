package com.jaber.babaki;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;


public class NewCahrtActivity extends Activity {

    /** Called when the activity is first created. */
    private SensorManager senSensorManager;
    private Sensor        senAccelerometer;
    static Vibrator       vibe;
    static Vibrator       half;
    private long          backPressTime = 0L;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ChartView chView = (ChartView) findViewById(R.id.chartView1);
        // chView.generateNumber(date);
        // v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibre mShaker = new vibre(this);
        Log.i("LOG", "SHAKE LISTENER CALLED");
        mShaker.setOnShakeListener(new vibre.OnShakeListener()
        {

            public void onShake()
            {
                vibe.vibrate(100);
                Log.i("LOG", "SHAKE LISTENER CALLED");
                Log.i("LOG", "jaber  ==  " + vibre.mLastX);
                // noofShakes++;
            }
        });
        // mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }


    public void onSensorChanged(SensorEvent event) {

    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onBackPressed() {

        Toast toast = Toast.makeText(this, "برای خروج یکبار دیگر ضربه بزنید",
                Toast.LENGTH_SHORT);
        if (backPressTime >= System.currentTimeMillis() - 2000L) {

            if (toast != null)
                toast.cancel();
            super.onBackPressed();
            ChartView.data.retainAll(null);
            finish();
        } else {
            toast.show();
            backPressTime = System.currentTimeMillis();
        }
    }
}