package com.example.dbattempt;

import java.text.ParseException;

import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.Cow;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class DeleteCowActivity extends EditCowActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_cow);
		// Show the Up button in the action bar.
		setupActionBar();
		try {
			displayItems();
			db.close();
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
		getMenuInflater().inflate(R.menu.delete_cow, menu);
		
		SearchManager searchManager =
				(SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView =
				(SearchView) menu.findItem(R.id.search).getActionView();
		searchView.setSearchableInfo(
				searchManager.getSearchableInfo(getComponentName()));
		
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
	
	@Override
	public void addRowsToTable(Cow cow, TableLayout editCowTable) {
		// Create a TableRow
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
					
		// create text views for cells
		createTextViewForCell(cow.getEarTagNo() + "\t\t", tr);
		createTextViewForCell(cow.getGender() + "\t\t", tr);
		createTextViewForCell(cow.getDateArrived() + "\t", tr);
		createButtonForCell("Delete", tr, cow.getEarTagNo());
		
		//Add the TableRow to the TableLayout
		editCowTable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(this);
		createTextViewForCell("", blankTr);
		editCowTable.addView(blankTr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	}

	@Override
	public void editCow(View view, final String earTagNo) {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Confirm Deletion")
		.setMessage("Are you sure you want to delete this cow?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
				db.deleteCow(earTagNo); 
				Toast toast = Toast.makeText(getApplicationContext(), "Cow Deleted", Toast.LENGTH_SHORT);
				toast.show();
				recreate();		// recreates activity to refresh list of cows
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
}
