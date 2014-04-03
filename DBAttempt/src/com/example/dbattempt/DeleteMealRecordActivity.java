package com.example.dbattempt;

import java.text.ParseException;

import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.MealRecord;

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

public class DeleteMealRecordActivity extends EditCowActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_meal_record);
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
		getMenuInflater().inflate(R.menu.delete_meal_record, menu);
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
		TableLayout deleteMealRecordTable = (TableLayout) findViewById(R.id.main_table);
		createEmptyTable(deleteMealRecordTable);
		
		// adds cow details rows
		for(MealRecord mealRecord : db.getAllMealRecords()) {
			addRowsToTable(mealRecord, deleteMealRecordTable);
		}
	}
	
	@Override
	public void addColumnHeadings(TableRow tr) {
		createTextViewForCell("Purchase Date \t\t\t", tr);
		createTextViewForCell("Food Name \t\t\t", tr);
		createTextViewForCell("Groups Fed \t", tr);
		createTextViewForCell("  ", tr);
	}

	@Override
	public void addRowsToTable(MealRecord mealRecord, TableLayout deleteMealRecordTable) {
		// Create a TableRow
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
					
		// create text views for cells
		createTextViewForCell(mealRecord.getPurchaseDate() + "\t\t", tr);
		createTextViewForCell(mealRecord.getFoodName() + "\t\t", tr);
		createTextViewForCell(mealRecord.getGroupsFed(), tr);
		createButtonForCell("Delete", tr, mealRecord.getPurchaseDate(), mealRecord.getFoodName(), mealRecord.getGroupsFed());
		
		//Add the TableRow to the TableLayout
		deleteMealRecordTable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(this);
		createTextViewForCell("", blankTr);
		deleteMealRecordTable.addView(blankTr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	}
	
	public void createButtonForCell(String buttonText, TableRow tr, final String purchaseDate, final String foodName, final String groupsFed) {
		OnClickListener onClickListener = new OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	deleteMealRecord(view, purchaseDate, foodName, groupsFed);
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
	
	public void deleteMealRecord(View view, final String purchaseDate, final String foodName, final String groupsFed) {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Confirm Deletion")
		.setMessage("Are you sure you want to delete this meal record?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
				db.deleteMealRecord(purchaseDate, foodName, groupsFed); 
				Toast toast = Toast.makeText(getApplicationContext(), "All Meal Records Deleted", Toast.LENGTH_SHORT);
				toast.show();
				recreate();		// recreates activity to refresh list of meal records
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
}
