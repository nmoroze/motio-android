package com.yhack.motio;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity  {

	TextView tv;
	Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getActionBar().setDisplayHomeAsUpEnabled(true);
     }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void record(View v) {
    	GestureRecorder recorder = new GestureRecorder(this, true);
    }
    
    public void runGestures(View v) {
    	Intent i = new Intent(this, GestureActivity.class);
    	startActivity(i); 
    }
}
