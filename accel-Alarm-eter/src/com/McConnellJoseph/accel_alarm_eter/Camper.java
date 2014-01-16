package com.McConnellJoseph.accel_alarm_eter;

import android.app.PendingIntent;

/*This is the class for the Camper object
  The camper object stores intents...  
  It is used to keep track of the alarms that have been set so that they can 
  be canceled. The id is used for sorting purposes */

public class Camper {
	
	public long id;
	public PendingIntent pendingInt;
	
	public Camper(long a, PendingIntent b){
		id = a;
		pendingInt = b;
		
	}
	
	public long getID(){
		return id;
		
	}
	
	public PendingIntent getPendInt(){
		return pendingInt;
		
	}
	
}
