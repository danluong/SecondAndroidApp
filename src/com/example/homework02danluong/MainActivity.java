package com.example.homework02danluong;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {

	long currentTimeMs;
	String cTimeString;

	Calendar c = Calendar.getInstance();
	DateFormat df = DateFormat.getTimeInstance();

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

	}



	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {
				public void run() {

					Calendar c = Calendar.getInstance();
					DateFormat df = DateFormat.getTimeInstance();
					cTimeString = df.format(c.getTime()).toString();

					TextView myTextView = (TextView) findViewById(R.id.textBox);

					myTextView.setText(cTimeString);

				}
			});

		}

	}
}
