package com.McConnellJoseph.accel_alarm_eter;
//Joseph McConnell
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class SpinnerListener implements OnItemSelectedListener {
	
	//This class handles the selection of an item from a spinner
	//In particular the spinner used for the selection of AM or PM when creating an alarm
	
	static String choice ;
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		
		((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
		
		//This should set choice equal to the selection made at the spinner
		choice = parent.getItemAtPosition(pos).toString();
		
	}
	
	public void onNothingSelected(AdapterView<?> parent){
		choice = "None";
	}

	//So you can retrieve the selection made
	public static String getSelection() {
		return choice;
	}
	
	
	
}
