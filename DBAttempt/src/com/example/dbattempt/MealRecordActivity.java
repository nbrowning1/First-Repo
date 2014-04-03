package com.example.dbattempt;

import com.example.dbattempt.model.MealRecord;
import com.example.dbattempt.template.ScrollTableTemplate;

import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.support.v4.app.NavUtils;

public class MealRecordActivity extends ScrollTableTemplate {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal_record);
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
		getMenuInflater().inflate(R.menu.meal_record, menu);
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
		case R.id.add_meal_record:
			Intent addIntent = new Intent(this, AddMealRecordActivity.class);
			startActivity(addIntent);
			return true;
		case R.id.action_edit:
			Intent editIntent = new Intent(this, EditMealRecordActivity.class);
			startActivity(editIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void displayItems() throws ParseException {
		TableLayout mealTable = (TableLayout) findViewById(R.id.main_table);
		createEmptyTable(mealTable);
		
		// adds cow details rows
		for(MealRecord mealRecord : db.getAllMealRecords()) {
			addRowsToTable(mealRecord, mealTable);
		}
	}
	
	@Override
	public void addColumnHeadings(TableRow tr) {
		// makes column headings for table
		createTextViewForCell("Purchase Date \t\t\t", tr);
		createTextViewForCell("Food name \t\t\t", tr);
		createTextViewForCell("Supplier \t\t\t", tr);
		createTextViewForCell("Invoice No. \t\t\t", tr);
		createTextViewForCell("Quantity \t\t\t", tr);
		createTextViewForCell("Groups fed \t\t\t", tr);
	}
	
	public void addRowsToTable(MealRecord mealRecord, TableLayout mealTable) {
		// Create a TableRow
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
							
		// create text views for cells
		createTextViewForCell(mealRecord.getPurchaseDate() + "\t\t", tr);
		createTextViewForCell(mealRecord.getFoodName() + "\t\t", tr);
		createTextViewForCell(mealRecord.getSupplier() + "\t\t", tr);
		createTextViewForCell(mealRecord.getInvoiceNo() + "\t\t", tr);
		createTextViewForCell(addLeadingZeroes(mealRecord.getQuantity() + "\t\t", 3), tr);
		createTextViewForCell(mealRecord.getGroupsFed() + "\t\t", tr);
		
		
		//Add the TableRow to the TableLayout
		mealTable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(this);
		createTextViewForCell("", blankTr);
		mealTable.addView(blankTr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	}
}
