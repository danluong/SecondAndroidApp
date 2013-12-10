package com.example.homework02danluong;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homework02danluong.TimePickerFragment.TimePickedListener;

public class MainActivity extends Activity implements TimePickedListener {

	private String cTimeString;

	private Calendar c = Calendar.getInstance();
	private DateFormat df = DateFormat.getTimeInstance();

	private Calendar alarmC;
	private boolean alarmSet = false;
	private int alarmCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// set up time display textview
		TextView myTextView = (TextView) findViewById(R.id.textBox);

		Typeface typeFace = Typeface.createFromAsset(getAssets(),
				"fonts/Iceland-Regular.ttf");
		myTextView.setTypeface(typeFace);
		myTextView.setGravity(Gravity.CENTER);

		// Now we start Timer to update value in TextView
		MyTimerTask mytask;
		Timer timer;
		mytask = new MyTimerTask();
		timer = new Timer();
		timer.schedule(mytask, 0, 1000);

		// Set up alarm dialog
		findViewById(R.id.alarmImgBtn).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (alarmSet) {
							alarmSet = false;
							TextView myTextView = (TextView) findViewById(R.id.textBox);

							myTextView.setTextColor(getResources().getColor(
									android.R.color.white));
							Toast.makeText(getApplicationContext(),
									"Alarm cancelled", Toast.LENGTH_SHORT)
									.show();

						} else {
							DialogFragment newFragment = new TimePickerFragment();
							newFragment
									.show(getFragmentManager(), "timePicker");
						}
					}
				});

	}

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			// this runs every second to update the time display
			runOnUiThread(new Runnable() {
				public void run() {

					c = Calendar.getInstance();
					df = DateFormat.getTimeInstance();
					cTimeString = df.format(c.getTime()).toString();
					TextView myTextView = (TextView) findViewById(R.id.textBox);

					myTextView.setText(cTimeString);

					if (alarmSet) {
						// check if current time >= alarm time
						int result = c.compareTo(alarmC);
						if (result >= 0) {
							// reached alarm time
							myTextView.setTextColor(getResources().getColor(
									android.R.color.holo_red_light));
							alarmCount++;

							// if alarmCount > 6 (5 sec), reset text color and
							// disable alarm
							if (alarmCount >= 6) {
								myTextView.setTextColor(getResources()
										.getColor(android.R.color.white));
								alarmSet = false;
								alarmCount = 0;
							}
						}

					}
				}
			});

		}

	}

	@Override
	public void onTimePicked(Calendar returnedC) {
		if(returnedC.after(c)){
		
		// display the selected time in the TextView

		cTimeString = df.format(returnedC.getTime()).toString();
		Toast.makeText(this, "Alarm set for: " + cTimeString,
				Toast.LENGTH_SHORT).show();
		TextView myTextView = (TextView) findViewById(R.id.textBox);

		myTextView.setTextColor(getResources().getColor(
				android.R.color.holo_blue_bright));
		alarmC = returnedC;
		alarmSet = true;
		} else{
			Toast.makeText(this, "Alarm time is invalid",
					Toast.LENGTH_SHORT).show();	
		}
	}

}
