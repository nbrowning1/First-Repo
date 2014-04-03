package com.example.dbattempt;

import com.example.dbattempt.datepickerfragment.GeneralDatePickerFragment;
import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.MealRecord;

import android.os.Bundle;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class AddMealRecordActivity extends AddMedicalRecordActivity implements OnDateSetListener, OnItemSelectedListener {
	
	DatabaseHelper db;
	private String foodName;
	private String supplier;
	private String groupsFed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_meal_record);
		// Show the Up button in the action bar.
		setupActionBar();
		
		dealWithSpinner(0);
		dealWithSpinner(1);
		dealWithSpinner(2);
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
		getMenuInflater().inflate(R.menu.add_meal_record, menu);
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
	
	public void showPurchaseDateDatePickerDialog(View v) {
		GeneralDatePickerFragment newFragment = new GeneralDatePickerFragment(this);
	    newFragment.show(getFragmentManager(), "datePurchased");
	}

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

        ((TextView) findViewById(R.id.meal_record_purchase_date_show)).setText(date);
	}

	// sets up the spinners (dropdown) for form
	public void dealWithSpinner(int spinnerNo) {
		Spinner spinner;
		ArrayAdapter<CharSequence> adapter;
		switch(spinnerNo) {
		case(0):
			spinner = (Spinner) findViewById(R.id.meal_record_food_name_spinner);
			// Create an ArrayAdapter using the string array and a default spinner layout
			adapter = ArrayAdapter.createFromResource(this,
					R.array.activity_add_meal_record_food_name, android.R.layout.simple_spinner_item);
			// Specify the layout to use when the list of choices appears
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner
			spinner.setAdapter(adapter);
						
			spinner.setOnItemSelectedListener(this);
			break;
		case(1):
			spinner = (Spinner) findViewById(R.id.meal_record_supplier_spinner);
			adapter = ArrayAdapter.createFromResource(this,
					R.array.activity_add_meal_record_supplier, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
					
			spinner.setOnItemSelectedListener(this);
			break;
		case(2):
			spinner = (Spinner) findViewById(R.id.meal_record_groups_fed_spinner);
			adapter = ArrayAdapter.createFromResource(this,
					R.array.activity_add_meal_record_groups_fed, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
				
			spinner.setOnItemSelectedListener(this);
			break;
		}
	}
	
	// deals with the selected spinner's choice
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
		switch(parent.getId()){
        case R.id.meal_record_food_name_spinner:
        	switch (pos) {
    		case 0:
    			foodName = parent.getItemAtPosition(0).toString();
    			break;
    		case 1:
    			foodName = parent.getItemAtPosition(1).toString();
    			break;
    		case 2:
    			foodName = parent.getItemAtPosition(2).toString();
    			break;
    		}
            break;
        case R.id.meal_record_supplier_spinner:
        	switch (pos) {
        	case 0:
        		supplier = parent.getItemAtPosition(0).toString();
        		break;
        	case 1:
        		supplier = parent.getItemAtPosition(1).toString();
        		break;
        	}
        	break;
        case R.id.meal_record_groups_fed_spinner:
        	switch (pos) {
        	case 0:
        		groupsFed = parent.getItemAtPosition(0).toString();
        		break;
        	case 1:
        		groupsFed = parent.getItemAtPosition(1).toString();
        		break;
        	}
        	break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// just implemented, not used
	}
	
	public void validateCreateMealRecordEntry(View view) {
		TextView purchaseDateInputField = ((TextView) findViewById(R.id.meal_record_purchase_date_show));
		EditText invoiceNoInputField = ((EditText) findViewById(R.id.meal_record_invoice_entry));
		EditText quantityInputField = ((EditText) findViewById(R.id.meal_record_quantity_entry));
		
		db = DatabaseHelper.getInstance(getApplicationContext());
		boolean valid = true;
		
		valid = viewIsValid(purchaseDateInputField, "Needs a Purchase Date");
		if(valid == true)
			purchaseDateInputField.setError(null);		// removes error tag from purchase date if previously invalid
			valid = requiredFixedLengthViewIsValid(invoiceNoInputField, "Needs an Invoice No.", "Invoice No. should be 12 characters long", 12);
		if(valid == true)
			valid = requiredMaxLengthViewIsValid(quantityInputField, "Needs a quantity", "Quantity should be 3 digits or less", 3);
		
		if(valid == true) {
			createNewMealRecord(view);
		}
	}
	
	public void createNewMealRecord(View view) {
		TextView purchaseDateInputField = ((TextView) findViewById(R.id.meal_record_purchase_date_show));
		EditText invoiceNoInputField = ((EditText) findViewById(R.id.meal_record_invoice_entry));
		EditText quantityInputField = ((EditText) findViewById(R.id.meal_record_quantity_entry));
		
		// setting form element properties to cow details variables
		String purchaseDate = purchaseDateInputField.getText().toString();
		String foodName = this.foodName;
		String supplier = this.supplier;
		String invoiceNo = invoiceNoInputField.getText().toString();
		int quantity = Integer.valueOf(quantityInputField.getText().toString());
		String groupsFed = this.groupsFed;
		
		MealRecord mealRecordToAdd = new MealRecord(purchaseDate, foodName, supplier, invoiceNo, quantity, groupsFed);	//--- as date moved off and moved to are n/a
		
		db = DatabaseHelper.getInstance(getApplicationContext());
		
		db.createMealRecord(mealRecordToAdd);
		
		Toast toast = Toast.makeText(getApplicationContext(), "Meal Record Added", Toast.LENGTH_SHORT);
		toast.show();
		
		// back to overview
		Intent intent = new Intent(this, MealRecordActivity.class);
		startActivity(intent);
	}
}
