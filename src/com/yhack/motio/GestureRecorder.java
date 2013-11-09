package com.yhack.motio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;

public class GestureRecorder implements SensorEventListener {
	private SensorManager sensorManager;
	float xAccel, yAccel, zAccel;
	float xRot, yRot, zRot;
	public boolean doneRecording = false;
	boolean newGesture;
	int recordTime = 2; //time to record in sec
	ArrayList<float[]> data = new ArrayList<float[]>();

	Activity parent;

	public GestureRecorder(Activity a, boolean newGesture) {
		this.parent = a;
		this.newGesture = newGesture;
		sensorManager= (SensorManager) this.parent.getSystemService(Context.SENSOR_SERVICE);
		// add listener. The listener will be HelloAndroid (this) class
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
				SensorManager.SENSOR_DELAY_FASTEST);


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
		String dataString = "[";
		for(int i=0; i<data.size(); i++) {
			float[] data_t = data.get(i);
			dataString+="[";
			for(int t=0; t<5; t++) 
				dataString+=data_t[t]+", ";
			dataString+=data_t[5]+"], ";
		}
		dataString+="]";
		
		if(newGesture) {
			final EditText input = new EditText(this.parent);
			new AlertDialog.Builder(this.parent)
		    .setTitle("Name your gesture: ")
		    .setView(input)
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            Editable value = input.getText(); 
		    		Log.i("Name", value.toString());
		        }
		    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            // Do nothing.
		        }
		    }).show();
			Log.i("Data", dataString);
		}
		else {
			Log.i("Data", dataString);
		}

		 // Create a new HttpClient and Post Header
//	    HttpClient httpclient = new DefaultHttpClient();
//	    HttpPost httppost = new HttpPost("http://www.yoursite.com/script.php");
//
//	    try {
//	        // Add your data
//	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//	        nameValuePairs.add(new BasicNameValuePair("name", "please"));
//	        nameValuePairs.add(new BasicNameValuePair("data", dataString));
//	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//	        // Execute HTTP Post Request
//	        HttpResponse response = httpclient.execute(httppost);
//
//	    } catch (ClientProtocolException e) {
//	    	e.printStackTrace();
//	    } catch (IOException e) {
//	    	e.printStackTrace();
//	    }
	}
//	while(!handler.doneRecording);

	public void onSensorChanged(SensorEvent event){

		// check sensor type
		if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
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