package com.example.dbattempt.datepickerfragment;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.example.dbattempt.EditCowShowActivity;
import com.example.dbattempt.model.Cow;

public class EditDatePickerFragment extends DialogFragment {
	
	private Cow currentCow;
	
	// sets up datepicker. returns datepicker to editCowShowActivity's onDateSet metho
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);	
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), (EditCowShowActivity)getActivity(), year, month, day);
        
        currentCow.setDateArrivedInMillis();
        
        FragmentManager fm = getFragmentManager();
        
        // if date left datepicker, set datepicker's minimum date to date arrived (can't leave before it arrived on farm)
        if(fm.findFragmentByTag("dateLeft") != null) {
        	datePicker.getDatePicker().setMinDate(currentCow.getDateArrivedInMillis());
        }
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        
        return datePicker;
    }
	
	public void getCurrentCow(Cow currentCow) {
		this.currentCow = currentCow;	
	}
}
