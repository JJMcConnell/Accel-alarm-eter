package com.McConnellJoseph.accel_alarm_eter;
//Joseph McConnell
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//This class runs what happens when the alarm goes off


public class Alert extends Activity {
	
	//Makes sure that shaking the device will not remove multiple entries
	boolean shakeFlag = false;
	
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
		
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//Changes the screen to the alarm
		 setContentView(R.layout.alarm_pop_up);
		 
		 
		 
		 
		 //accelerometer set up
		 mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		 mSensorListener = new ShakeEventListener(); 
	       
		 
		 //Makes a bunch of awful noise.
		 //It takes the user's current phone settings for alarm and plays it
		 //If no alarm is currently set it falls back to the ringtone and if that fails
		 //it resorts to using the notification sound
		 Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		 
	     if(alert == null){
	   
	         alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
	         
	         if(alert == null){  
	        	 
	             alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);               
	         }
	     }
	     
	     final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alert);
	     
	   
		   r.play();
	     
		   
		   
		   //Sets up the listener so that if you shake the phone the alarm will stop
		   mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

				 public void onShake() {
				    //Stops the terrible noise
				    r.stop();
				    
				    if (shakeFlag == false){
				    	
				    	if (Set_Alarm.getAlarmSentenceLength() > 0) {
				    //These remove the alarm from the current alarms list once it has gone off
				    Set_Alarm.removeAlarmSentence(0);
			           
			        Set_Alarm.removeCampers(0);
				    	}
			        
			        shakeFlag = true;
				    }
			        //Closes the activity that pops up
				    finish();
				      }
				 });
	     
	     //Code for a button to stop the alarm as well. I am leaving it without a button
		 //as a design choice but if needed I will re-implement one
		   
		/* Button buttonStop = (Button) findViewById(R.id.buttonStop);
		 
		
		buttonStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Stops the noise
				r.stop();
				
				//These remove the alarm from the current alarms list once it has gone off
			    Set_Alarm.removeAlarmSentence(0);
		           
		        Set_Alarm.removeCampers(0);
				
				finish();
				
			} 
		}); */
		
	}
	@Override
	  protected void onResume() {
	    super.onResume();
	    mSensorManager.registerListener(mSensorListener,
	        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_UI);
	  }

	  @Override
	  protected void onPause() {
	    mSensorManager.unregisterListener(mSensorListener);
	    super.onPause();
	  }
	  
	  @Override
		 public void onBackPressed(){
			 
		 }
	  
}
