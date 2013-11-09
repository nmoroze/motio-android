package com.yhack.motio;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;


public class SensorService extends Service implements SensorEventListener {
	float dist;
	Context context;
	SensorManager manager;
	Sensor acceleration;
	Sensor gyro;
	float x, y, z;
	float thresh = 0.5f;
	
	 @Override
	   public IBinder onBind(Intent intent) {
	      return null;
	   }

	   @Override
	   public void onCreate() {
		   context=getApplicationContext();
		   manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		   acceleration = manager.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION).get(0);
		manager.registerListener(this,acceleration,SensorManager.SENSOR_DELAY_NORMAL);

		   Toast.makeText(context, "Activated sensor service!", Toast.LENGTH_SHORT).show();
	   }

	   @Override
	   public void onDestroy() {
		   //stop listening!
		   manager.unregisterListener(this);
	   }

	   @Override
	   public void onStart(Intent intent, int startid) {
		   
	   }
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//IDGAF
	}
	
	public void onSensorChanged(SensorEvent event) {
		Sensor s = event.sensor;
		int type = s.getType();
		if(type==Sensor.TYPE_LINEAR_ACCELERATION) {
			float xAccel = event.values[0];
			float yAccel = event.values[1];
			float zAccel = event.values[2];
		}
		if(x>thresh || y>thresh || z>thresh) {
			
		}
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
}