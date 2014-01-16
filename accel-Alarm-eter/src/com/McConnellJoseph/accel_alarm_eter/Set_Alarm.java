package com.McConnellJoseph.accel_alarm_eter;

//Joseph McConnell

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;

public class Set_Alarm extends Activity {

	
		private Spinner daynight;
		private EditText timeInput;
		String hour = " ";
		String minute = " ";
		String selection;
		 
		 
		 boolean invalidTime = false;
		 boolean AM = false;
		 boolean PM = false;
		 boolean colon = false;
		 boolean TimeSet = false;
		 
		   //Campers like to stay in state parks
		static ArrayList<Camper> statePark = new ArrayList<Camper>();
		 
		  //Holds the text that displays in the listview for currently set alarms
		static ArrayList<String> alarmSentence = new ArrayList<String>();
		 
		 
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set__alarm);
		
		addListenerOnSpinnerItemSelection();
		addListenerOnTimeBox();
		
		
	}
	
	//Adds a listener for when a selection is made in the spinner
	//The SpinnerListener class handles most of it and is able to return the selected string
	public void addListenerOnSpinnerItemSelection() {
		daynight = (Spinner) findViewById(R.id.daynight);
		daynight.setOnItemSelectedListener(new SpinnerListener());
	}
	
	
	public void addListenerOnTimeBox() {
		
		//This function detects when the user hits done after typing in the time they want
		//the alarm set for. It started out simple but I realized that the colon would make a mess
		//of the parsing and that I should have just used the default time picker widget
		//but I think it's ugly so I added a bunch of dumb conditionals in order to properly
		//parse the time that the user inputs. The true cost of form over function right here.
		
		//finds and names the button
		timeInput = (EditText) findViewById(R.id.timeInput);
		
		//attaches the listener to the button
		timeInput.setOnEditorActionListener( new OnEditorActionListener(){
			 public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				 
			        boolean handled = false;
			        
			        if (actionId == EditorInfo.IME_ACTION_DONE) {
			        	//resets hour and minute to blank strings in case they change the time inputed
			        	hour = " ";
			        	minute = " ";
			        	
			        	//loops until the end of the input
			        	for (int i=0; i < timeInput.getText().toString().length(); i++){
			        		
			        		//Checks if the current char is the ":" if it is it changes the flag	        					        		
			        		if(	(timeInput.getText().toString().charAt(i)) == ':' ){
			        			colon = true;
			        			continue;
			        		}
			        	
			        		
			        		//If the colon has not been reached yet then it is reading the hour values
			        		else if (colon == false) {
			        			hour += timeInput.getText().toString().substring(i, i+1);
			        			}
			        	
			        		
			        		//If the colon has been reached then it is reading the minute values
			        		else if (colon == true){
			        			minute += timeInput.getText().toString().substring(i, i+1);
			        			}
			        	
			        		
			        	}
			        	//resets the flag in case input gets changed
			        	colon = false;
			        	
			        	//Checks to make sure that the hour input is possible, nags the user if it isn't
			        	//The -1 is to correct for the blank space in the front of the strings
			        	if ((hour.length() - 1) > 2 || (hour.length() - 1 ) < 1){
			        		Toast.makeText(getApplicationContext(), "Please input a proper time", Toast.LENGTH_LONG).show();
			        	}
			        	
			        	//Checks to make sure that the minute input is possible nags the user if it isn't
			        	if ((minute.length() - 1) != 2){
			        		Toast.makeText(getApplicationContext(), "Please input a proper time", Toast.LENGTH_LONG).show();
			        	}
			        	
			        	
			            TimeSet = true;	
			            handled = true;
			            hideSoftKeyboard();
			            
			        }
			        return handled;
			    }
		});
	}
	
	
	//Method to determine the selection made at the spinner ie: AM or PM
	public void spinnerCheck(){
		if (SpinnerListener.getSelection().equals("AM") ) {
			
			AM = true;
			PM = false;
			selection = "AM";
			
		}
		if (SpinnerListener.getSelection().equals("PM")) {
			PM = true;
			AM = false;
			selection = "PM";
			
		}	
	}
	
	//This method is called in the addListenerOnTimeBox method
	//It makes the keyboard go away after they hit done
	private void hideSoftKeyboard(){
	    if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
	        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.hideSoftInputFromWindow(timeInput.getWindowToken(), 0);
	    }
	}
	
	
	
	//Converts the string "minute" to an integer
	public int minuteConverter(String s){
		
		
		//This acts as a check that numbers were actually entered
		//If the user had inputed " 12: " then the minute string would have a length of 1 
		//due to the space and would be caught and dealt with by this conditional
		
		if (s.length() < 2){
			invalidTime = true;
			return -1;
		}
		
		//Removes the space that is in front of the values in the string
		String minutesWithoutThatSpace = s.substring(1, s.length());
		
		//Changes the string to an integer
		int convertedMinutes = Integer.parseInt(minutesWithoutThatSpace);
		
		//This checks to make sure that the time entered is valid and flags it if it is not
		if (convertedMinutes > 59 || convertedMinutes < 0){
			invalidTime = true;
		}
	
		return convertedMinutes;
		
	}
	
	
	//Converts the string "hours" into an integer and then alters it depending on whether 
	//AM or PM was selected by the user
	public int hourConverter(String s){
		
		
		//This acts as a check that numbers were actually entered
		//If the user had inputed " :11" then the hour string would have a length of 1 due to the space
		//and would be caught and dealt with by this conditional
		
		if (s.length() < 2){
			invalidTime = true;
			return -1;
		}
		
		//Removes the space that is in front of the time in the string
		String hoursWithoutThatSpace = s.substring(1,s.length());
		
		//Changes the value from a string to an integer
		int convertedHours = Integer.parseInt(hoursWithoutThatSpace);
		
		//Checks to makes sure that the time entered is valid and flags it if it is not
		if (convertedHours > 12 || convertedHours < 1){
			invalidTime = true;
		}
		else {
			spinnerCheck();
			if( PM == true){
				convertedHours += 12;
			}
		}
		
		return convertedHours;
		
	}
	
	
	//This method is run once the button is hit to set the alarm
	public void set(View v){
		spinnerCheck();
		int tempHour = hourConverter(hour);
		int tempMinute = minuteConverter(minute);
		
		
		//Checks to see if a time was entered, if it was not it asks the user to enter one
		if (TimeSet == false || invalidTime == true){
			Toast.makeText(getApplicationContext(), "Please Input A Proper Time", Toast.LENGTH_SHORT).show();
		}
		
		
		
		else{
			//This number takes the current time, it is used when creating the pending Intent
			//in order to guarantee that the intents are different from each other 
			//If they were not unique then canceling one alarm would cancel them all
			int pendingIntentNumber = (int) System.currentTimeMillis();
		
		
		Intent alarmIntent = new Intent(getApplicationContext(), AlarmService.class );
		PendingIntent pendingAlarmIntent = PendingIntent.getService(getApplicationContext(), pendingIntentNumber, alarmIntent, 0);
		
		
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		
		//Creates a Calendar object, then takes the current time and changes it to be
		//The chosen time of the alarm
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.HOUR_OF_DAY, tempHour);
		cal.set(Calendar.MINUTE, tempMinute); 
		cal.set(Calendar.SECOND, 0);
		
		//Checks to see if the time set has already passed in the current day
		//If it has then it sets the alarm for that time the next day
		if (cal.getTimeInMillis() < System.currentTimeMillis()) {
			cal.setTimeInMillis( cal.getTimeInMillis() + 86400000);
		}
		
		
		//adds the new alarm to the list of alarms that have been set
		alarmSentence.add(Long.toString(cal.getTimeInMillis()) + "Alarm set for " + hour + ":" + minute.substring(1,minute.length()) + " " + selection );
		
		//Adds the pending intent to an array list that holds them all
		//The pending intent is needed for canceling an alarm
		statePark.add(new Camper(cal.getTimeInMillis(), pendingAlarmIntent));
		
		//Sorts both the array list of sentences and the array list of intents
		//The sorting allows me to remove the top entry from either array list when
		//An alarm goes off keeping the list of current alarms accurate
		Collections.sort(alarmSentence);
		Collections.sort(statePark, new CamperComparator());
		
		
		//The one set to rule them all. Sets the actual alarm
		alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingAlarmIntent);
		
		finish();
		
		//Prints out the time that was entered for the alarm
		Toast.makeText(getApplicationContext(), "Alarm set for " + hour.substring(1, hour.length()) + ":" + minute.substring(1, minute.length()) + " " + selection, Toast.LENGTH_SHORT).show();
		
		}
		
	}
	
	//This is the comparator class used for sorting the array list of camper objects
	//It compares their id values which are the time in milliseconds of the alarm that was set
	//That way the indexing of both of the array lists match up for deleting purposes
	class CamperComparator implements Comparator<Camper> {
		
	    public int compare(Camper camper1, Camper camper2) {
	        return (int) (camper1.getID() - camper2.getID());
	    }
	}
	
	//Get and remove methods for the two array lists 
	public static String getAlarmSentence(int x) {
			return alarmSentence.get(x);
		}
	
	public static int getAlarmSentenceLength(){
		return alarmSentence.size();
	}
	
	public static void removeAlarmSentence(int x){
		alarmSentence.remove(x);
	}
	
	public static void removeCampers(int x){
		statePark.remove(x);
	}
	
	public static PendingIntent cancelAlarm(int n){
		
		return (statePark.get(n).getPendInt());
	}
	
	
	

}
