package com.yhack.motio;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class GestureActivity extends Activity implements SensorEventListener{
	private SensorManager manager;
	float accelThresh = 8.0f;
	float rotThresh = 8.0f;
	float xAccel, yAccel, zAccel;
	float xRot, yRot, zRot;
	int restElapsed = 0;
	int restThresh = 500;
	boolean happening = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture);
		
		manager= (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
		// add listener. The listener will be HelloAndroid (this) class
		manager.registerListener(this,
				manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
				SensorManager.SENSOR_DELAY_NORMAL);
		
		manager.registerListener(this,
				manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
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
		
		if((Math.abs(xRot) > rotThresh || Math.abs(yRot) > rotThresh || Math.abs(zRot) > rotThresh) && 
				(Math.abs(xAccel) > accelThresh || Math.abs(yAccel) > accelThresh || Math.abs(zAccel) > accelThresh)) {
			if(!happening)
				Log.i("YES", "It's happening");
			happening = true;
		}
		else {
			if(happening)
				restElapsed++;
			if(restElapsed>restThresh) {
				Log.i("NO", "it stopped happening");
				happening = false;
				restElapsed = 0;
			}
		}
	}
	
}
