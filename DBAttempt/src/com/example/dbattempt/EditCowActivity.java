package com.example.dbattempt;

import java.text.ParseException;

import com.example.dbattempt.model.Cow;
import com.example.dbattempt.template.ScrollTableTemplate;

import android.os.Bundle;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.support.v4.app.NavUtils;

public class EditCowActivity extends ScrollTableTemplate {

	public final static String EAR_TAG_NO = "com.example.dbattempt.EARTAG";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_cow);
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
		getMenuInflater().inflate(R.menu.edit_cow, menu);
		
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
	public void displayItems() throws ParseException {
		TableLayout editCowTable = (TableLayout) findViewById(R.id.main_table);
		createEmptyTable(editCowTable);
		
		// adds cow details rows
		for(Cow cow : db.getAllCows()) {
			addRowsToTable(cow, editCowTable);
		}
	}
	
	@Override
	public void addColumnHeadings(TableRow tr) {
		createTextViewForCell("Ear Tag No. \t\t\t", tr);
		createTextViewForCell("Gender \t\t\t", tr);
		createTextViewForCell("Date Arrived \t", tr);
		createTextViewForCell("  ", tr);
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
		createTextViewForCell(cow.getDateArrived(), tr);
		createButtonForCell("Edit", tr, cow.getEarTagNo());
		
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

	public void createButtonForCell(String buttonText, TableRow tr, final String earTagNo) {
		OnClickListener onClickListener = new OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	editCow(view, earTagNo);
		    }
		};
		Button button = new Button(this);
		button.setText(buttonText);
		button.setOnClickListener(onClickListener);
		button.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		tr.addView(button);
	}
	
	// passes the ear tag of cow chosen to edit to the real edit activity
	public void editCow(View view, String earTagNo) {
		Intent intent = new Intent(this, EditCowShowActivity.class);
		intent.putExtra(EAR_TAG_NO, earTagNo);
		startActivity(intent);
	}
}
