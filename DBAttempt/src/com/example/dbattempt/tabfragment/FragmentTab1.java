package com.example.dbattempt.tabfragment;

import java.text.DecimalFormat;
import java.text.ParseException;

import com.example.dbattempt.R;
import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.Cow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.app.Fragment;
import android.graphics.Typeface;
 
public class FragmentTab1 extends Fragment {
	
	protected DatabaseHelper db;
	protected View rootView;
	DecimalFormat df = new DecimalFormat("0.00");  // keeps price to 2 decimal places
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_1, container, false);
        this.rootView = rootView;
        try {
			displayCows();
			db.close();
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return rootView;
    }
 
    public void displayCows() throws ParseException {
		TableLayout cowTable = (TableLayout) rootView.findViewById(R.id.main_table);
		createEmptyTable(cowTable);
		
		// adds cow details rows
		for(Cow cow : db.getAllCows()) {
			addRowsToTable(cow, cowTable);
		}
	}
	
	public void createEmptyTable(TableLayout cowTable) {
		db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
		TableRow initialTr = new TableRow(getActivity());
		
		// makes column headings for table
		createTextViewForCell("Ear Tag No. \t\t\t", initialTr, false);
		createTextViewForCell("Gender \t\t\t", initialTr, false);
		createTextViewForCell("Date Arrived \t\t\t", initialTr, false);
		createTextViewForCell("Days on Farm \t\t\t", initialTr, false);
		createTextViewForCell("Price \t\t\t", initialTr, false);
		createTextViewForCell("Vaccination Date \t\t\t", initialTr, false);
		createTextViewForCell("Bovivac Booster Date \t\t\t", initialTr, false);
		createTextViewForCell("IBR / RSP Booster Date \t\t\t", initialTr, false);
		createTextViewForCell("Date Moved Off Farm \t\t\t", initialTr, false);
		createTextViewForCell("Moved to \t\t\t", initialTr, false);
		
		cowTable.addView(initialTr, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(getActivity());
		createTextViewForCell("", blankTr, false);
		cowTable.addView(blankTr, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
	}
	
	public void addRowsToTable(Cow cow, TableLayout cowTable) {
		// Create a TableRow
		TableRow tr = new TableRow(getActivity());
		tr.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		boolean italicText = false;
		if(!(cow.getMovedTo().equals("---")))		// if cow has moved or died
			italicText = true;							// just indicate with italic row
					
		// create text views for cells
		createTextViewForCell(cow.getEarTagNo() + "\t\t", tr, italicText);
		createTextViewForCell(cow.getGender() + "\t\t", tr, italicText);
		createTextViewForCell(cow.getDateArrived() + "\t\t", tr, italicText);
		createTextViewForCell("" + cow.getDaysOnFarm() + "\t\t", tr, italicText);
		createTextViewForCell("£" + df.format(cow.getPrice()) + "\t\t", tr, italicText);
		createTextViewForCell(cow.getDateVaccinated() + "\t\t", tr, italicText);
		createTextViewForCell(cow.getBovivacBoosterDate() + "\t\t", tr, italicText);
		createTextViewForCell(cow.getIBRRSPBoosterDate() + "\t\t", tr, italicText);
		createTextViewForCell(cow.getDateMovedOffFarm() + "\t\t", tr, italicText);
		createTextViewForCell(cow.getMovedTo() + "\t\t", tr, italicText);
		
		
		//Add the TableRow to the TableLayout
		cowTable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(getActivity());
		createTextViewForCell("", blankTr, false);
		cowTable.addView(blankTr, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
	}
	
	public void createTextViewForCell(String textToSet, TableRow tr, boolean italicText) {
		TextView textview = new TextView(getActivity());
		if(italicText == true) {
			textview.setTypeface(null, Typeface.ITALIC);
			textview.setTextColor(getResources().getColor(R.color.cow_moved_color));
		}
		textview.setText(textToSet);
		textview.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		tr.addView(textview);
	}
}