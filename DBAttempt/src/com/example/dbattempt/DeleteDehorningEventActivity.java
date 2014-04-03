package com.example.dbattempt;

import java.text.ParseException;

import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.DehorningEvent;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.support.v4.app.NavUtils;

public class DeleteDehorningEventActivity extends EditCowActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_dehorning_event);
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
		getMenuInflater().inflate(R.menu.delete_dehorning_event, menu);
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
		TableLayout editDehorningEventTable = (TableLayout) findViewById(R.id.main_table);
		createEmptyTable(editDehorningEventTable);
		
		// adds cow details rows
		for(DehorningEvent dehornEvent : db.getAllDehorningEvents()) {
			addRowsToTable(dehornEvent, editDehorningEventTable);
		}
	}
	
	@Override
	public void addColumnHeadings(TableRow tr) {
		createTextViewForCell("Date Dehorned \t\t\t", tr);
		createTextViewForCell("Group Ref. \t\t", tr);
		createTextViewForCell("No. \t\t", tr);
		createTextViewForCell("  ", tr);
	}

	@Override
	public void addRowsToTable(DehorningEvent dehornEvent, TableLayout editDehorningEventTable) {
		// Create a TableRow
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
					
		// create text views for cells
		createTextViewForCell(dehornEvent.getDateDehorned() + "\t\t", tr);
		createTextViewForCell(dehornEvent.getGroupRef() + "\t", tr);
		createTextViewForCell("" + dehornEvent.getNoDehorned() + "\t\t", tr);
		createButtonForCell("Delete", tr, dehornEvent.getDateDehorned(), dehornEvent.getGroupRef());
		
		//Add the TableRow to the TableLayout
		editDehorningEventTable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(this);
		createTextViewForCell("", blankTr);
		editDehorningEventTable.addView(blankTr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	}	
	
	public void createButtonForCell(String buttonText, TableRow tr, final String dateDehorned, final String groupRef) {
		OnClickListener onClickListener = new OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	deleteDehorningEvent(view, dateDehorned, groupRef);
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
	
	public void deleteDehorningEvent(View view, final String dateDehorned, final String groupRef) {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Confirm Deletion")
		.setMessage("Are you sure you want to delete this dehorning event?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
				db.deleteDehorningEvent(dateDehorned, groupRef); 
				Toast toast = Toast.makeText(getApplicationContext(), "All Dehorning Events Deleted", Toast.LENGTH_SHORT);
				toast.show();
				recreate();		// recreates activity to refresh list of medical records
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
}
