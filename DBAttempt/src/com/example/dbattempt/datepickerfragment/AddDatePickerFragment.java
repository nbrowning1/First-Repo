package com.example.dbattempt.datepickerfragment;

import java.util.Calendar;

import com.example.dbattempt.AddCowActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class AddDatePickerFragment extends DialogFragment{
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);	
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), (AddCowActivity)getActivity(), year, month, day);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        
        return datePicker;
    }
}