package com.example.dbattempt;

import com.example.dbattempt.datepickerfragment.GeneralDatePickerFragment;
import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.MedicalRecord;

import android.os.Bundle;
import android.app.Activity;
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

public class AddMedicalRecordActivity extends Activity implements OnDateSetListener {

	DatabaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_medical_record);
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
		getMenuInflater().inflate(R.menu.add_medical_record, menu);
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
	    newFragment.show(getFragmentManager(), "dateOfUse");
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

	        ((TextView) findViewById(R.id.cow_date_of_use_show)).setText(date);
	}
	
	public void validateCreateMedicalRecordEntry(View view) {
		EditText earTagInputField = ((EditText) findViewById(R.id.cow_ear_tag_text_entry));
		EditText medicalDetailsInputField = ((EditText) findViewById(R.id.cow_medical_details_entry));
		EditText batchNoInputField = ((EditText) findViewById(R.id.cow_batch_no_text_entry));
		TextView dateOfUseInputField = ((TextView) findViewById(R.id.cow_date_of_use_show));
		EditText quantityUsedInputField = ((EditText) findViewById(R.id.cow_quantity_used_text_entry));
		EditText doctorInputField = ((EditText) findViewById(R.id.cow_doctor_text_entry));
		EditText commentsInputField = ((EditText) findViewById(R.id.cow_comments_text_entry));
		
		db = DatabaseHelper.getInstance(getApplicationContext());
		boolean valid = true;
		
		// set up like this so that only one error is produced each time (so that user isn't overwhelmed by error tags)
		valid = requiredFixedLengthViewIsValid(earTagInputField, "Medical Record needs an associated cow", "Ear Tag No. should be 11 digits long", 11);
		if(valid == true)
			valid = requiredMaxLengthViewIsValid(medicalDetailsInputField, "Needs Medical Details", "Details should be 15 characters or less", 15);
		if(valid == true)
			valid = requiredFixedLengthViewIsValid(batchNoInputField, "Needs a Batch No.", "Batch No. should be 12 characters long", 12);
		if(valid == true)
			valid = viewIsValid(dateOfUseInputField, "Needs a Date of Use");
		if(valid == true) {
			dateOfUseInputField.setError(null);		// removes error tag from date of use if previously invalid
			valid = requiredMaxLengthViewIsValid(quantityUsedInputField, "Needs a Quantity Used value", "Quantity Used should be 3 digits or less", 3);
		}
		if(valid == true)
			valid = requiredFixedLengthViewIsValid(doctorInputField, "Needs a Doctor", "Doctor should be 3 characters long", 3);
		if(valid == true)
			valid = optionalMaxLengthViewIsValid(commentsInputField, "Comments should be 50 characters or less", 50);
		
		// if valid after all of these checks, go on to add to database
		if(valid == true) {
			createNewMedicalRecord(view);
		}
	}
	
	public boolean requiredFixedLengthViewIsValid(EditText view, String presenceError, String lengthError, int fixedLength) {
		boolean valid = true;
		
		if(view.getText().toString().equals("")) {
			view.requestFocus();
			view.setError(presenceError);
			valid = false;
		}
		
		if(fixedLength != 0) {
			if(!(view.getText().toString().length() == fixedLength)) {
				view.requestFocus();
				view.setError(lengthError);
				valid = false;
			}
		}
		return valid;
	}
	
	public boolean requiredMaxLengthViewIsValid(EditText view, String presenceError, String lengthError, int maxLength) {
		boolean valid = true;
		
		if(view.getText().toString().equals("")) {
			view.requestFocus();
			view.setError(presenceError);
			valid = false;
		}
		
		if(maxLength != 0) {
			if(view.getText().toString().length() > maxLength) {
				view.requestFocus();
				view.setError(lengthError);
				valid = false;
			}
		}
		return valid;
	}
	
	public boolean optionalMaxLengthViewIsValid(EditText view, String lengthError, int maxLength) {
		boolean valid = true;
		
		if(maxLength != 0) {
			if(view.getText().toString().length() > maxLength) {
				view.requestFocus();
				view.setError(lengthError);
				valid = false;
			}
		}
		return valid;
	}
	
	// for date textfield
	public boolean viewIsValid(TextView view, String error) {
		boolean valid = true;
		
		if(view.getText().toString().equals("")) {
			view.requestFocus();
			view.setError(error);
			valid = false;
		}
		
		return valid;
	}
	
	
	public void createNewMedicalRecord(View view) {
		EditText earTagInputField = ((EditText) findViewById(R.id.cow_ear_tag_text_entry));
		EditText medicalDetailsInputField = ((EditText) findViewById(R.id.cow_medical_details_entry));
		EditText batchNoInputField = ((EditText) findViewById(R.id.cow_batch_no_text_entry));
		TextView dateOfUseInputField = ((TextView) findViewById(R.id.cow_date_of_use_show));
		EditText quantityUsedInputField = ((EditText) findViewById(R.id.cow_quantity_used_text_entry));
		EditText doctorInputField = ((EditText) findViewById(R.id.cow_doctor_text_entry));
		EditText commentsInputField = ((EditText) findViewById(R.id.cow_comments_text_entry));
		
		// setting form element properties to cow details variables
		String cowEarTag = earTagInputField.getText().toString();
		String details = medicalDetailsInputField.getText().toString();
		String batchNo = batchNoInputField.getText().toString();
		String dateOfUse = dateOfUseInputField.getText().toString();
		int quantityUsed = Integer.valueOf(quantityUsedInputField.getText().toString());
		String doctor = doctorInputField.getText().toString();
		String comments = commentsInputField.getText().toString();
		
		MedicalRecord medicalRecordToAdd = new MedicalRecord(cowEarTag, details, batchNo, dateOfUse, quantityUsed, doctor, comments);	//--- as date moved off and moved to are n/a
		
		db = DatabaseHelper.getInstance(getApplicationContext());
		
		db.createMedicalRecord(medicalRecordToAdd);
		
		Toast toast = Toast.makeText(getApplicationContext(), "Medical Record Added", Toast.LENGTH_SHORT);
		toast.show();
		
		// back to overview
		Intent intent = new Intent(this, OverviewActivity.class);
		startActivity(intent);
	}
}
