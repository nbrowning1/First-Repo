package com.example.dbattempt;

import com.example.dbattempt.datepickerfragment.EditDatePickerFragment;
import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.Cow;

import android.os.Bundle;
import android.app.FragmentManager;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;

public class EditCowShowActivity extends FragmentActivity implements OnDateSetListener, OnItemSelectedListener {

	private String location;
	private Cow currentCow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_cow_show);
		// Show the Up button in the action bar.
		setupActionBar();
		
		showDetails();
		dealWithSpinner();
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
		getMenuInflater().inflate(R.menu.edit_cow_show, menu);
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

	// these methods called when the corresponding view date button is shown
	// call upon editDatePickerFragment's onCreateDialog
	public void showVaccinationDatePickerDialog(View v) {
	    EditDatePickerFragment newFragment = new EditDatePickerFragment();
	    newFragment.getCurrentCow(currentCow);		// passes cow to date fragment
	    newFragment.show(getFragmentManager(), "vaccinationDate");
	}
	
	public void showDateLeftDatePickerDialog(View v) {
	    EditDatePickerFragment newFragment = new EditDatePickerFragment();
	    newFragment.getCurrentCow(currentCow);		// passes cow to date fragment
	    newFragment.show(getFragmentManager(), "dateLeft");
	}
	
	public void showBovivacBoosterDatePickerDialog(View v) {
	    EditDatePickerFragment newFragment = new EditDatePickerFragment();
	    newFragment.getCurrentCow(currentCow);
	    newFragment.show(getFragmentManager(), "bovivacBooster");
	}
	
	public void showIBRRSPBoosterDatePickerDialog(View v) {
	    EditDatePickerFragment newFragment = new EditDatePickerFragment();
	    newFragment.getCurrentCow(currentCow);
	    newFragment.show(getFragmentManager(), "ibrRSPBooster");
	}
	
	//////
	
	// final stage of the datepicker, sets show labels to whatever chosen by the datepicker
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

        FragmentManager fm = getFragmentManager();
        
        if(fm.findFragmentByTag("vaccinationDate") != null) {
        	((TextView) findViewById(R.id.cow_vaccination_date_show)).setText(date);
        }
        if(fm.findFragmentByTag("dateLeft") != null) {
        	((TextView) findViewById(R.id.cow_date_moved_off_show)).setText(date);
        }
        if(fm.findFragmentByTag("bovivacBooster") != null) {
        	((TextView) findViewById(R.id.cow_bovivac_booster_show)).setText(date);
        }
        if(fm.findFragmentByTag("ibrRSPBooster") != null) {
        	((TextView) findViewById(R.id.cow_ibr_rsp_booster_show)).setText(date);
        }
    }
	
	// initialises values in fields to show some detail about the cow we're editing
	public void showDetails() {
		Intent intent = getIntent();
		String earTagNo = intent.getStringExtra(EditCowActivity.EAR_TAG_NO);
		
		DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
		Cow thisCow = db.getCow(earTagNo);
		currentCow = thisCow;
		
		TextView earTagLabel = ((TextView) findViewById(R.id.cow_ear_tag_show));
		TextView genderLabel = ((TextView) findViewById(R.id.cow_gender_show));
		TextView dateArrivedLabel = ((TextView) findViewById(R.id.cow_date_arrived_show));
		TextView vaccinationDateLabel = ((TextView) findViewById(R.id.cow_vaccination_date_show));
		TextView dateLeftLabel = ((TextView) findViewById(R.id.cow_date_moved_off_show));
		TextView bovivacBoosterLabel = ((TextView) findViewById(R.id.cow_bovivac_booster_show));
		TextView ibrRSPBoosterLabel = ((TextView) findViewById(R.id.cow_ibr_rsp_booster_show));
		
		earTagLabel.setText(thisCow.getEarTagNo());
		genderLabel.setText(thisCow.getGender());
		dateArrivedLabel.setText(thisCow.getDateArrived());
		vaccinationDateLabel.setText(thisCow.getDateVaccinated());
		dateLeftLabel.setText(thisCow.getDateMovedOffFarm());
		bovivacBoosterLabel.setText(thisCow.getBovivacBoosterDate());
		ibrRSPBoosterLabel.setText(thisCow.getIBRRSPBoosterDate());
	}
	
	// sets up the spinner (dropdown) for locations
	public void dealWithSpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.cow_moved_to_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.activity_edit_cow_moved_to_locations, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
		
		// deals with whatever value was chosen on the spinner
		switch (pos) {
        case 0:
        	location = parent.getItemAtPosition(0).toString();
        	break;
        case 1:
        	location = parent.getItemAtPosition(1).toString();
        	break;
        case 2:
        	location = parent.getItemAtPosition(2).toString();
        	break;
        case 3:
        	location = parent.getItemAtPosition(3).toString();
        	break;
		}
    }

	@Override
    public void onNothingSelected(AdapterView<?> parent) {
        // no code needed (nothing selected isn't a choice) but needs implemented anyway
    }
	
	public void editCow(View view) {
		// declaring form elements in code
		TextView vaccinationDateField = ((TextView) findViewById(R.id.cow_vaccination_date_show));
		TextView bovivacBoosterField = ((TextView) findViewById(R.id.cow_bovivac_booster_show));
		TextView ibrRSPBoosterField = ((TextView) findViewById(R.id.cow_ibr_rsp_booster_show));
		TextView earTagField = ((TextView) findViewById(R.id.cow_ear_tag_show));
		TextView dateMovedField = ((TextView) findViewById(R.id.cow_date_moved_off_show));
		
		// setting form element properties to cow details variables (ear tag is used to edit the right cow)
		String earTagNo = earTagField.getText().toString();
		String vaccinationDate = vaccinationDateField.getText().toString();
		String bovivacBoosterDate = bovivacBoosterField.getText().toString();
		String ibrRspBoosterDate = ibrRSPBoosterField.getText().toString();
		String dateMoved = dateMovedField.getText().toString();
		String movedTo = this.location;
		
		DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
		db.editCow(earTagNo, vaccinationDate, bovivacBoosterDate, ibrRspBoosterDate, dateMoved, movedTo);
		
		Toast toast = Toast.makeText(getApplicationContext(), "Cow Edited", Toast.LENGTH_SHORT);
		toast.show();
		
		// back to edit overview
		Intent intent = new Intent(this, EditCowActivity.class);
		startActivity(intent);
	}
	
	public String getLocation() {
		return this.location;
	}
}
