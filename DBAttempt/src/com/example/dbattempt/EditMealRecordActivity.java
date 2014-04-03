package com.example.dbattempt;

import com.example.dbattempt.helper.DatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class EditMealRecordActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_meal_record);
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
		getMenuInflater().inflate(R.menu.edit_meal_record, menu);
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

	public void deleteMealRecords(View view) {
		Intent editIntent = new Intent(this, DeleteMealRecordActivity.class);
		startActivity(editIntent);
	}
	
	public void deleteAllMealRecords(View view) {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Confirm Deletion")
		.setMessage("Are you sure you want to delete all meal records?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				confirmDeletion();
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
	
	// used to 'nest' confirm dialog boxes, used in the deleteAllCows() method to ensure no accidental deletion of all
	public void confirmDeletion() {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Confirm Deletion")
		.setMessage("Confirm deletion")
		.setPositiveButton("Confirm", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
				db.deleteAllMealRecords();
				Toast toast = Toast.makeText(getApplicationContext(), "All Meal Records Deleted", Toast.LENGTH_SHORT);
				toast.show();
			}
		})
		.setNegativeButton("Cancel", null)
		.show();
	}
}
