package com.McConnellJoseph.accel_alarm_eter;

//Joseph McConnell

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;


//Creates and runs an intent to shift over to the service class 
//when the alarm time is reached


public class AlarmService extends Service{

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	   public void onStart(Intent intent, int startId) {
	      
		
		
		
	      Intent alertIntent = new Intent();
	      alertIntent.setClass( this , Alert.class );
	      alertIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	      startActivity( alertIntent );
	   }
	

}
