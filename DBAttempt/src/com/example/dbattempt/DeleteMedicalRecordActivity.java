package com.example.dbattempt;

import java.text.ParseException;

import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.MedicalRecord;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.support.v4.app.NavUtils;

public class DeleteMedicalRecordActivity extends EditCowActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_medical_record);
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
		getMenuInflater().inflate(R.menu.delete_medical_record, menu);
		
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
		TableLayout editMedicalRecordTable = (TableLayout) findViewById(R.id.main_table);
		createEmptyTable(editMedicalRecordTable);
		
		// adds cow details rows
		for(MedicalRecord medRecord : db.getAllMedicalRecords()) {
			addRowsToTable(medRecord, editMedicalRecordTable);
		}
	}
	
	@Override
	public void addColumnHeadings(TableRow tr) {
		createTextViewForCell("Ear Tag No. \t\t\t", tr);
		createTextViewForCell("Batch No. \t\t\t", tr);
		createTextViewForCell("Date of Use \t", tr);
		createTextViewForCell("  ", tr);
	}

	@Override
	public void addRowsToTable(MedicalRecord medRecord, TableLayout editMedicalRecordTable) {
		// Create a TableRow
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
					
		// create text views for cells
		createTextViewForCell(medRecord.getEarTagNo() + "\t\t", tr);
		createTextViewForCell(medRecord.getBatchNo() + "\t\t", tr);
		createTextViewForCell(medRecord.getDateOfUse(), tr);
		createButtonForCell("Delete", tr, medRecord.getEarTagNo(), medRecord.getBatchNo(), medRecord.getDateOfUse());
		
		//Add the TableRow to the TableLayout
		editMedicalRecordTable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(this);
		createTextViewForCell("", blankTr);
		editMedicalRecordTable.addView(blankTr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	}
	
	public void createButtonForCell(String buttonText, TableRow tr, final String earTagNo, final String batchNo, final String dateOfUse) {
		OnClickListener onClickListener = new OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	deleteMedRecord(view, earTagNo, batchNo, dateOfUse);
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
	
	public void deleteMedRecord(View view, final String earTagNo, final String batchNo, final String dateOfUse) {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Confirm Deletion")
		.setMessage("Are you sure you want to delete this medical record?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
				db.deleteMedicalRecord(earTagNo, batchNo, dateOfUse); 
				Toast toast = Toast.makeText(getApplicationContext(), "All Medical Records Deleted", Toast.LENGTH_SHORT);
				toast.show();
				recreate();		// recreates activity to refresh list of medical records
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
}
