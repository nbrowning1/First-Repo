package com.example.dbattempt.template;

import java.text.ParseException;

import com.example.dbattempt.R;
import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.Cow;
import com.example.dbattempt.model.DehorningEvent;
import com.example.dbattempt.model.MealRecord;
import com.example.dbattempt.model.MedicalRecord;
import com.example.dbattempt.model.MilkRecord;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class ScrollTableTemplate extends Activity {

	protected DatabaseHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scroll_table_template);
		
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
		getMenuInflater().inflate(R.menu.scroll_table_template, menu);
		return true;
	}

	public void displayItems() throws ParseException {
		TableLayout cowTable = (TableLayout) findViewById(R.id.main_table);
		createEmptyTable(cowTable);
		
		// adds cow details rows
		for(Cow cow : db.getAllCows()) {
			addRowsToTable(cow, cowTable);
		}
	}
	
	public void createEmptyTable(TableLayout table) {
		db = DatabaseHelper.getInstance(getApplicationContext());
		TableRow initialTr = new TableRow(this);
		
		addColumnHeadings(initialTr);
		
		table.addView(initialTr, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(this);
		createTextViewForCell("", blankTr);
		table.addView(blankTr, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
	}
	
	public void addColumnHeadings(TableRow initialTr) {
		// makes column headings for table
		// MUST BE OVERRIDDEN
	}
	
	public void addRowsToTable(Cow cow, TableLayout table) {
		// add own implementation
	}
	
	public void addRowsToTable(MedicalRecord medRecord, TableLayout table) {
		// add own implementation
	}
	
	public void addRowsToTable(DehorningEvent dehornEvent, TableLayout table) {
		// add own implementation
	}
	
	public void addRowsToTable(MealRecord mealRecord, TableLayout table) {
		// add own implementation
	}
	
	public void addRowsToTable(MilkRecord milkRecord, TableLayout table) {
		// add own implementation
	}
	
	public void createTextViewForCell(String textToSet, TableRow tr) {
		TextView textview = new TextView(this);
		textview.setText(textToSet);
		textview.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		tr.addView(textview);
	}
	
	// adds leading zeroes as the database will cut them off when storing a number like 00001234567 etc.
	protected static String addLeadingZeroes(String numberToFix, int FieldLength) {
		int id_length = numberToFix.length();
		String zeroes = "";
		while(id_length < FieldLength) {
			zeroes += "0";
			id_length++;
		}
			
		String fullText = "" + zeroes + numberToFix;
		
		return fullText;
	}
}
