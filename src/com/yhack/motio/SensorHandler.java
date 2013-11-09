package com.yhack.motio;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

public class SensorHandler implements SensorEventListener {
	private SensorManager sensorManager;
	float xAccel, yAccel, zAccel;
	float xRot, yRot, zRot;
	public boolean doneRecording = false;
	int recordTime = 2; //time to record in sec
	ArrayList<float[]> data = new ArrayList<float[]>();

	Activity parent;

	public SensorHandler(Activity a) {
		this.parent = a;
		sensorManager= (SensorManager) this.parent.getSystemService(Context.SENSOR_SERVICE);
		// add listener. The listener will be HelloAndroid (this) class
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
				SensorManager.SENSOR_DELAY_NORMAL);
		
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
				SensorManager.SENSOR_DELAY_NORMAL);


		/*	More sensor speeds (taken from api docs)
		    SENSOR_DELAY_FASTEST get sensor data as fast as possible
		    SENSOR_DELAY_GAME	rate suitable for games
		 	SENSOR_DELAY_NORMAL	rate (default) suitable for screen orientation changes
		*/
	    final SensorEventListener listener = this;
	    new Handler().postDelayed(new Runnable() {
	        @Override
	        public void run() {
	            sensorManager.unregisterListener(listener);
	            postData();
	    	    doneRecording = true;
	        }
	    }, recordTime*1000);
	}

	public void onAccuracyChanged(Sensor sensor,int accuracy){

	}

	private void postData() {
		Log.i("uh oh", ""+data.size());
		for(int i=0; i<data.size(); i++) {
			float[] data_t = data.get(i);
			Log.i("Sensor data", "xAccel: " + data_t[0]+
					" yAccel: " + data_t[1] +
					"zAccel: " + data_t[2] +
					"xRot: " + data_t[3] +
					"yRot: " + data_t[4] + 
					"zRot: " + data_t[5]);
		}
	}
//	while(!handler.doneRecording);

	public void onSensorChanged(SensorEvent event){

		// check sensor type
		if(event.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION){
			 xAccel = event.values[0];
			 yAccel = event.values[1];
			 zAccel = event.values[2];
		}
		else if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE) {
			xRot = event.values[0];
			yRot = event.values[1];
			zRot = event.values[2];
		}
		
		data.add(new float[]{xAccel, yAccel, zAccel, xRot, yRot, zRot});
	}
	
	public float[] getAccel()
	{
		return new float[]{xAccel, yAccel, zAccel};
	}
	
	public float[] getRot()
	{
		return new float[]{xAccel, yAccel, zAccel};
	}
	
	public ArrayList<float[]> getData() {
		return data;
	}
}