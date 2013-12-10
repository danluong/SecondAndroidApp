package com.example.homework02danluong;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {

	private Calendar c;
	private TimePickedListener mListener;

	public static interface TimePickedListener {
		public void onTimePicked(Calendar time);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	@Override
	public void onAttach(Activity activity) {
		// when the fragment is initially shown (i.e. attached to the activity),
		// cast the activity to the callback interface type
		super.onAttach(activity);
		try {
			mListener = (TimePickedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException();
		}
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Do something with the time chosen by the user
		c.set(Calendar.HOUR_OF_DAY, hourOfDay);
		c.set(Calendar.MINUTE, minute);
		mListener.onTimePicked(c);
	}
}