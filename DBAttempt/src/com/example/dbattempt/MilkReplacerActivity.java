package com.example.dbattempt;

import com.example.dbattempt.model.MilkRecord;
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

public class MilkReplacerActivity extends ScrollTableTemplate {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_milk_replacer);
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
		getMenuInflater().inflate(R.menu.milk_replacer, menu);
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
		case R.id.add_milk_record:
			Intent addIntent = new Intent(this, AddMilkReplacerRecordActivity.class);
			startActivity(addIntent);
			return true;
		case R.id.action_edit:
			Intent editIntent = new Intent(this, EditMilkReplacerActivity.class);
			startActivity(editIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void displayItems() throws ParseException {
		TableLayout milkTable = (TableLayout) findViewById(R.id.main_table);
		createEmptyTable(milkTable);
		
		// adds cow details rows
		for(MilkRecord milkRecord : db.getAllMilkReplacerRecords()) {
			addRowsToTable(milkRecord, milkTable);
		}
	}
	
	@Override
	public void addColumnHeadings(TableRow tr) {
		createTextViewForCell("Purchase Date \t\t\t", tr);
		createTextViewForCell("Milk name \t\t\t", tr);
		createTextViewForCell("Manufacturer \t\t\t", tr);
		createTextViewForCell("Supplier \t\t\t", tr);
		createTextViewForCell("Invoice No. \t\t\t", tr);
		createTextViewForCell("Quantity \t\t\t", tr);
	}
	
	public void addRowsToTable(MilkRecord milkRecord, TableLayout milkTable) {
		// Create a TableRow
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
							
		// create text views for cells
		createTextViewForCell(milkRecord.getPurchaseDate() + "\t\t", tr);
		createTextViewForCell(milkRecord.getMilkName() + "\t\t", tr);
		createTextViewForCell(milkRecord.getManufacturer() + "\t\t", tr);
		createTextViewForCell(milkRecord.getSupplier() + "\t\t", tr);
		createTextViewForCell(milkRecord.getInvoiceNo() + "\t\t", tr);
		createTextViewForCell(addLeadingZeroes(milkRecord.getQuantity() + "\t\t", 3), tr);
		
		//Add the TableRow to the TableLayout
		milkTable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(this);
		createTextViewForCell("", blankTr);
		milkTable.addView(blankTr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	}
}
