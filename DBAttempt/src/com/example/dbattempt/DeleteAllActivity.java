package com.example.dbattempt;

import com.example.dbattempt.helper.DatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class DeleteAllActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_all);
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
		getMenuInflater().inflate(R.menu.delete_all, menu);
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

	public void deleteAllCows(View view) {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Confirm Deletion")
		.setMessage("Are you sure you want to delete all cows?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				confirmDeletion(0);
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
	
	public void deleteAllMedicalRecords(View view) {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Confirm Deletion")
		.setMessage("Are you sure you want to delete all medical records?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				confirmDeletion(1);
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
	
	public void deleteAllDehorningEvents(View view) {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Confirm Deletion")
		.setMessage("Are you sure you want to delete all dehorning events?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				confirmDeletion(2);
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
	
	// used to 'nest' confirm dialog boxes, used in the deleteAllCows() method to ensure no accidental deletion of all
	public void confirmDeletion(final int buttonSelected) {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Confirm Deletion")
		.setMessage("Confirm deletion")
		.setPositiveButton("Confirm", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
				switch (buttonSelected) {
		        case 0:
		        	db.deleteAllCows();
		        	Toast toast = Toast.makeText(getApplicationContext(), "All Cows Deleted", Toast.LENGTH_SHORT);
					toast.show();
		        	break;
		        case 1:
		        	db.deleteAllMedicalRecords();
		        	Toast toast1 = Toast.makeText(getApplicationContext(), "All Medical Records Deleted", Toast.LENGTH_SHORT);
					toast1.show();
		        	break;
		        case 2:
		        	db.deleteAllDehorningEvents();
		        	Toast toast2 = Toast.makeText(getApplicationContext(), "All Dehorning Events Deleted", Toast.LENGTH_SHORT);
					toast2.show();
		        	break;
				}
			}
		})
		.setNegativeButton("Cancel", null)
		.show();
	}
}
