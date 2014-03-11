package com.McConnellJoseph.accel_alarm_eter;
//Joseph McConnell
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//This class handles the current alarms activity including list view stuff

public class CurrentAlarms extends Activity implements OnItemClickListener {
	
	//Adapter used for the list view
	ArrayAdapter<String> adapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		//Checks to see that there are actually alarms currently set
		if( Set_Alarm.getAlarmSentenceLength() == 0){
			
			Toast.makeText(getApplicationContext(), "No alarms currently set", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_alarms);
		
		
		
		//Sets the listview to the adapter
		ListView listOfAlarms = (ListView) findViewById(R.id.listOfAlarms);
		 adapter = new ArrayAdapter<String>(this, R.layout.alarm_list_entry);
		listOfAlarms.setAdapter(adapter);
		
		//Goes through the array list and adds it to the listview 
		//It parses from 13 to the end of the strings because the first 13 chars are 
		//the time in millis that the alarms were set for, that allows for proper sorting
	
		for (int i = 0; i < Set_Alarm.getAlarmSentenceLength(); i++ ){
			adapter.add( Set_Alarm.getAlarmSentence(i).substring(13, Set_Alarm.getAlarmSentence(i).length()));
		}
		
		
		listOfAlarms.setOnItemClickListener(this);
		
	}
	
	//Listener for clicking on the list view
		@Override
	    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
			
			//The position in the list view of the clicked on item
			final int pos = position;
			
			//This creates a pop-up that prompts the user if they want to delete an alarm
			new AlertDialog.Builder(this)
		    .setTitle("Cancel Alarm")
		    .setMessage("Would you like to cancel this alarm?")
		    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	
		        	//If they choose yes then it creates a matching intent as the alarm to cancel it
		        	//and removes the alarm from the list view and both array lists 
		        	
		        	PendingIntent temp = Set_Alarm.cancelAlarm(pos);
		        	AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		            alarmManager.cancel(temp);
		           
		            adapter.remove(Set_Alarm.getAlarmSentence(pos).substring(13, Set_Alarm.getAlarmSentence(pos).length()));
		            Set_Alarm.removeAlarmSentence(pos);
		           
		            Set_Alarm.removeCampers(pos);
		        	
		        	
		            
		        }
		     })
		    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        	
		        	//If they say no then the pop-up closes and nothing happens
		        }
		     })
		    //.setIcon(R.drawable.)
		     .show();
			
		}
	
	
	//A button to return to the first screen of the app
	public void backButton (View view){
		finish();
	}
	
	
	
	
	

}
