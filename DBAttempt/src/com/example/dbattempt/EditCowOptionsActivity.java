package com.example.dbattempt;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;

public class EditCowOptionsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_cow_options);
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
		getMenuInflater().inflate(R.menu.edit_cow_options, menu);
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
	
	public void editCows(View view) {
		Intent editIntent = new Intent(this, EditCowActivity.class);
		startActivity(editIntent);
	}
	
	public void deleteCows(View view) {
		Intent editIntent = new Intent(this, DeleteCowActivity.class);
		startActivity(editIntent);
	}
	
	public void deleteMedicalRecords(View view) {
		Intent editIntent = new Intent(this, DeleteMedicalRecordActivity.class);
		startActivity(editIntent);
	}
	
	public void deleteDehorningEvents(View view) {
		Intent editIntent = new Intent(this, DeleteDehorningEventActivity.class);
		startActivity(editIntent);
	}
	
	public void deleteAll(View view) {
		Intent editIntent = new Intent(this, DeleteAllActivity.class);
		startActivity(editIntent);
	}
}
