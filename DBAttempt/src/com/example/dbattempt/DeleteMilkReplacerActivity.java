package com.example.dbattempt;

import java.text.ParseException;

import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.MilkRecord;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

public class DeleteMilkReplacerActivity extends EditCowActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_milk_replacer);
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
		getMenuInflater().inflate(R.menu.delete_milk_replacer, menu);
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
		TableLayout deleteMilkRecordTable = (TableLayout) findViewById(R.id.main_table);
		createEmptyTable(deleteMilkRecordTable);
		
		// adds cow details rows
		for(MilkRecord milkRecord : db.getAllMilkReplacerRecords()) {
			addRowsToTable(milkRecord, deleteMilkRecordTable);
		}
	}
	
	@Override
	public void addColumnHeadings(TableRow tr) {
		createTextViewForCell("Purchase Date \t\t\t", tr);
		createTextViewForCell("Milk Name \t\t\t", tr);
		createTextViewForCell("Invoice No. \t", tr);
		createTextViewForCell("  ", tr);
	}

	@Override
	public void addRowsToTable(MilkRecord milkRecord, TableLayout deleteMilkRecordTable) {
		// Create a TableRow
		TableRow tr = new TableRow(this);
		tr.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
					
		// create text views for cells
		createTextViewForCell(milkRecord.getPurchaseDate() + "\t\t", tr);
		createTextViewForCell(milkRecord.getMilkName() + "\t\t", tr);
		createTextViewForCell(milkRecord.getInvoiceNo(), tr);
		createButtonForCell("Delete", tr, milkRecord.getPurchaseDate(), milkRecord.getMilkName(), milkRecord.getInvoiceNo());
		
		//Add the TableRow to the TableLayout
		deleteMilkRecordTable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(this);
		createTextViewForCell("", blankTr);
		deleteMilkRecordTable.addView(blankTr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	}
	
	public void createButtonForCell(String buttonText, TableRow tr, final String purchaseDate, final String milkName, final String invoiceNo) {
		OnClickListener onClickListener = new OnClickListener() {
		    @Override
		    public void onClick(View view) {
		    	deleteMealRecord(view, purchaseDate, milkName, invoiceNo);
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
	
	public void deleteMealRecord(View view, final String purchaseDate, final String milkName, final String invoiceNo) {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Confirm Deletion")
		.setMessage("Are you sure you want to delete this milk record?")
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
				db.deleteMilkReplacerRecord(purchaseDate, milkName, invoiceNo); 
				Toast toast = Toast.makeText(getApplicationContext(), "All Milk Records Deleted", Toast.LENGTH_SHORT);
				toast.show();
				recreate();		// recreates activity to refresh list of meal records
			}
		})
		.setNegativeButton("No", null)
		.show();
	}
}
