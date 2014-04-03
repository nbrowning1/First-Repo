package com.example.dbattempt;

import com.example.dbattempt.datepickerfragment.GeneralDatePickerFragment;
import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.DehorningEvent;

import android.os.Bundle;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class AddDehorningEventActivity extends AddMedicalRecordActivity implements OnDateSetListener {

	DatabaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_dehorning_event);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_dehorning_event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure.
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void showDateOfUseDatePickerDialog(View v) {
	    GeneralDatePickerFragment newFragment = new GeneralDatePickerFragment(this);
	    newFragment.show(getFragmentManager(), "dateDehorned");
	}
	
	// handles the date returned by datepicker
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		StringBuilder builder = new StringBuilder();
		builder.append(year).append("-");
		if(month < 9) 
			builder.append("0" + (month + 1)).append("-");	//month + 1 because Jan = 0 and Dec = 11 by default
		else 
			builder.append(month + 1).append("-");	//month + 1 because Jan = 0 and Dec = 11 by default
		
		if(day < 10)
			builder.append("0" + day);
		else
			builder.append(day);

	        String date = builder.toString();

	        ((TextView) findViewById(R.id.date_dehorned_show)).setText(date);
	}
	
	public void validateCreateDehorningEventEntry(View view) {
		TextView dateDehornedField = ((TextView) findViewById(R.id.date_dehorned_show));
		EditText groupRefField  = ((EditText) findViewById(R.id.group_ref_entry));
		EditText noDehornedField  = ((EditText) findViewById(R.id.number_dehorned_entry));
		EditText dCaineField  = ((EditText) findViewById(R.id.d_caine_entry));
		db = DatabaseHelper.getInstance(getApplicationContext());
		boolean valid = true;
		
		valid = viewIsValid(dateDehornedField, "Needs Date Dehorned");
		// set up like this so that only one error is produced each time (so that user isn't overwhelmed by error tags)
		if(valid == true)
			valid = requiredFixedLengthViewIsValid(groupRefField, "Needs Group Ref.", "Group Ref. should be 6 characters long", 6);
		if(valid == true)
			valid = viewIsValid(noDehornedField, "Needs No. Dehorned");
		if(valid == true)
			valid = requiredFixedLengthViewIsValid(dCaineField, "Needs D-Caine.", "D-Caine should be 8 characters long", 8);
		
		if(valid == true) {
			createNewDehorningEvent(view);
		}
	}
	
	public void createNewDehorningEvent(View view) {
		// declaring form elements in code
		TextView dateDehornedField = ((TextView) findViewById(R.id.date_dehorned_show));
		EditText groupRefField = ((EditText) findViewById(R.id.group_ref_entry));
		EditText noDehornedField = ((EditText) findViewById(R.id.number_dehorned_entry));
		EditText dCaineField = ((EditText) findViewById(R.id.d_caine_entry));
		
		String dateDehorned = dateDehornedField.getText().toString();
		String groupRef = groupRefField.getText().toString();
		int noDehorned = Integer.valueOf(noDehornedField.getText().toString());
		String dCaine = dCaineField.getText().toString();
		
		DehorningEvent dehorningEventToAdd = new DehorningEvent(dateDehorned, groupRef, noDehorned, dCaine);	
		
		db = DatabaseHelper.getInstance(getApplicationContext());
		
		db.createDehorningEvent(dehorningEventToAdd);
		
		Toast toast = Toast.makeText(getApplicationContext(), "Dehorning Event Added", Toast.LENGTH_SHORT);
		toast.show();
		
		// back to overview
		Intent intent = new Intent(this, OverviewActivity.class);
		startActivity(intent);
	}
}
