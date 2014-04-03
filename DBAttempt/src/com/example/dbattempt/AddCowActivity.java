package com.example.dbattempt;

import com.example.dbattempt.datepickerfragment.AddDatePickerFragment;
import com.example.dbattempt.datepickerfragment.GeneralDatePickerFragment;
import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.Cow;

import android.os.Bundle;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.FragmentManager;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;

public class AddCowActivity extends FragmentActivity implements OnDateSetListener {

	DatabaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_cow);
		// Show the Up button in the action bar.
		setupActionBar();
		
		RadioGroup radioGroup = ((RadioGroup) findViewById(R.id.radio_group));
		radioGroup.check(R.id.radio_button_male);
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
		getMenuInflater().inflate(R.menu.add_cow, menu);
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
	
	
	// handles whichever datepicker was chosen
	public void showDateArrivedDatePickerDialog(View v) {
	    AddDatePickerFragment newFragment = new AddDatePickerFragment();
	    newFragment.show(getFragmentManager(), "dateArrived");
	}
	
	public void showVaccinationDateDatePickerDialog(View v) {
	    AddDatePickerFragment newFragment = new AddDatePickerFragment();
	    newFragment.show(getFragmentManager(), "vaccinationDate");
	}
	
	public void showBovivacBoosterDatePickerDialog(View v) {
	    GeneralDatePickerFragment newFragment = new GeneralDatePickerFragment(this);
	    newFragment.show(getFragmentManager(), "bovivacBoosterDate");
	}
	
	public void showIBRAndRSPBoosterDatePickerDialog(View v) {
		GeneralDatePickerFragment newFragment = new GeneralDatePickerFragment(this);
	    newFragment.show(getFragmentManager(), "ibrRSPBoosterDate");
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

        FragmentManager fm = getFragmentManager();
        
        if(fm.findFragmentByTag("dateArrived") != null) {
        	((TextView) findViewById(R.id.cow_date_arrived_show)).setText(date);
        }
        if(fm.findFragmentByTag("vaccinationDate") != null) {
        	((TextView) findViewById(R.id.cow_vaccination_date_show)).setText(date);
        }
        if(fm.findFragmentByTag("bovivacBoosterDate") != null) {
        	((TextView) findViewById(R.id.cow_bovivac_booster_show)).setText(date);
        }
        if(fm.findFragmentByTag("ibrRSPBoosterDate") != null) {
        	((TextView) findViewById(R.id.cow_ibr_rsp_booster_show)).setText(date);
        }
    }
	
	public void validateCreateCowFormEntry(View view) {
		EditText earTagInputField = ((EditText) findViewById(R.id.cow_ear_tag_text_entry));
		TextView dateArrivedField = ((TextView) findViewById(R.id.cow_date_arrived_show));
		TextView vaccinationDateField = ((TextView) findViewById(R.id.cow_vaccination_date_show));
		String earTagAsString = earTagInputField.getText().toString();
		
		db = DatabaseHelper.getInstance(getApplicationContext());
		boolean valid = true;
		
		if(!(earTagAsString.length() == 11)) {
			earTagInputField.requestFocus();
			earTagInputField.setError("Ear Tag No should be 11 digits long.");
			valid = false;
		}
		else {
			for(Cow cow : db.getAllCows()) {
				if(earTagAsString.equals(cow.getEarTagNo())) {
					earTagInputField.requestFocus();
					earTagInputField.setError("Cow with this Ear Tag No. already exists.");
					valid = false;
					break;
				}
			}
		}
		
		if(valid == true) {
			if(dateArrivedField.getText().toString().equals("")) {
				dateArrivedField.requestFocus();
				dateArrivedField.setError("Cow needs a 'Date Arrived'.");
				valid = false;
			}
		}
			
		if(valid == true) {
			dateArrivedField.setError(null);	// removes error from other date field (otherwise it hangs even when fixed?)
			
			if(vaccinationDateField.getText().toString().equals("")) {
				vaccinationDateField.requestFocus();
				vaccinationDateField.setError("Cow needs a 'Vaccination Date'.");
				valid = false;
			}
		}
		
		if(valid == true) {
			createNewCow(view);
		}
	}
	
	public void createNewCow(View view) {
		// declaring form elements in code
		EditText earTagInputField = ((EditText) findViewById(R.id.cow_ear_tag_text_entry));
		TextView dateArrivedField = ((TextView) findViewById(R.id.cow_date_arrived_show));
		TextView vaccinationDateField = ((TextView) findViewById(R.id.cow_vaccination_date_show));
		TextView bovivacBoosterDateField = ((TextView) findViewById(R.id.cow_bovivac_booster_show));
		TextView ibrRSPBoosterDateField = ((TextView) findViewById(R.id.cow_ibr_rsp_booster_show));
		RadioButton maleRadioButton = ((RadioButton) findViewById(R.id.radio_button_male));
		RadioButton femaleRadioButton = ((RadioButton) findViewById(R.id.radio_button_female));
		
		// setting form element properties to cow details variables
		String cowEarTag = earTagInputField.getText().toString();
		String dateArrived = dateArrivedField.getText().toString();
		String vaccinationDate = vaccinationDateField.getText().toString();
		String bovivacBoosterDate = bovivacBoosterDateField.getText().toString();
		String ibrRSPBoosterDate = ibrRSPBoosterDateField.getText().toString();
		String gender = "";
		if(maleRadioButton.isChecked()) {
			gender = "Male";
		}
		if(femaleRadioButton.isChecked()) {
			gender = "Female";
		}
		
		Cow cowToAdd = new Cow(cowEarTag, gender, dateArrived, vaccinationDate, bovivacBoosterDate, ibrRSPBoosterDate, "---", "---");	//--- as date moved off and moved to are n/a
		
		db = DatabaseHelper.getInstance(getApplicationContext());
		
		db.createCow(cowToAdd);
		
		Toast toast = Toast.makeText(getApplicationContext(), "Cow Added", Toast.LENGTH_SHORT);
		toast.show();
		
		// back to overview
		Intent intent = new Intent(this, OverviewActivity.class);
		startActivity(intent);
	}
}