package com.McConnellJoseph.accel_alarm_eter;
//Joseph McConnell
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MainActivity extends Activity {
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
	
		
	}

	

	public void setAlarm (View view) {
		
		//Goes to the activity to set the alarm
		
		Intent intent = new Intent(MainActivity.this, Set_Alarm.class);
		startActivity(intent);
	}
	
	//Goes to the current alarms activity
	public void currentAlarms (View view){
		Intent intent = new Intent(MainActivity.this, CurrentAlarms.class);
		startActivity(intent);
		
	}	
	
}
