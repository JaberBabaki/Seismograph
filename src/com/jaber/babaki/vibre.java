package com.jaber.babaki;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;


public class vibre implements SensorEventListener
{

    private static final int FORCE_THRESHOLD = 300;
    private static final int TIME_THRESHOLD  = 100;
    private static final int SHAKE_TIMEOUT   = 500;
    private static final int SHAKE_DURATION  = 1000;
    private static final int SHAKE_COUNT     = 3;

    private SensorManager    mSensorMgr;
    public static float      mLastX          = -1.0f, mLastY = -1.0f, mLastZ = -1.0f;
    private long             mLastTime;
    private OnShakeListener  mShakeListener;
    private Context          mContext;
    private int              mShakeCount     = 0;
    private long             mLastShake;
    private long             mLastForce;
    public static float      speed;


    public interface OnShakeListener
    {

        public void onShake();
    }


    public vibre(Context context)
    {
        mContext = context;
        resume();
    }


    public void setOnShakeListener(OnShakeListener listener)
    {
        mShakeListener = listener;
    }


    public void resume()
    {
        mSensorMgr = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorMgr == null)
        {
            throw new UnsupportedOperationException("Sensors not supported");
        }

        boolean supported = false;

        try
        {
            supported = mSensorMgr.registerListener(this, mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        }
        catch (Exception e)
        {
            Toast.makeText(mContext, "Shaking not supported", Toast.LENGTH_LONG).show();
        }

        if (( !supported) && (mSensorMgr != null))
            mSensorMgr.unregisterListener(this);
    }


    public void pause()
    {
        if (mSensorMgr != null)
        {
            mSensorMgr.unregisterListener(this);
            mSensorMgr = null;
        }
    }


    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }


    public void onSensorChanged(final SensorEvent event)
    {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        final long now = System.currentTimeMillis();

        if ((now - mLastForce) > SHAKE_TIMEOUT)
        {
            mShakeCount = 0;
        }

        if ((now - mLastTime) > TIME_THRESHOLD)
        {

            long diff = now - mLastTime;
            speed = Math.abs(event.values[SensorManager.DATA_X] + event.values[SensorManager.DATA_Y] + event.values[SensorManager.DATA_Z] - mLastX - mLastY - mLastZ) / diff * 10000;
            ChartView.generateNumber(speed);// System.out.println("SPEED OF THE VIBRATION " + speed);
            if (speed > FORCE_THRESHOLD)
            {
                if ((++mShakeCount >= SHAKE_COUNT) && (now - mLastShake > SHAKE_DURATION))
                {
                    mLastShake = now;
                    mShakeCount = 0;
                    if (mShakeListener != null)
                    {
                        mShakeListener.onShake();
                    }
                }
                mLastForce = now;
            }
            mLastTime = now;
            mLastX = event.values[SensorManager.DATA_X];
            mLastY = event.values[SensorManager.DATA_Y];
            mLastZ = event.values[SensorManager.DATA_Z];
            /* Thread n = new Thread(new Runnable() {

                 @Override
                 public void run() {
                     try {
                         Thread.sleep(10);
                         ChartView.generateNumber(speed);
                         if (ChartView.half == 1) {
                             //ChartView.data.remove(1);
                             // ChartView.data.remove(2);
                         }

                     }
                     catch (InterruptedException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                     }

                 }
             });
             n.start();*/
        }

    }
}
