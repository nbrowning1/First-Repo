package com.example.dbattempt.tabfragment;

import java.text.ParseException;

import com.example.dbattempt.R;
import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.DehorningEvent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.app.Fragment;
 
public class FragmentTab3 extends Fragment {
	DatabaseHelper db;
	View rootView;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_3, container, false);
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
		TableLayout dehornTable = (TableLayout) rootView.findViewById(R.id.main_table);
		createEmptyTable(dehornTable);
		
		// adds cow details rows
		for(DehorningEvent dehornEvent : db.getAllDehorningEvents()) {
			addRowsToTable(dehornEvent, dehornTable);
		}
	}
	
	public void createEmptyTable(TableLayout dehornTable) {
		db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
//		db = new DatabaseHelper(getActivity().getApplicationContext());
		TableRow initialTr = new TableRow(getActivity());
		
		// makes column headings for table
		createTextViewForCell("Date Dehorned \t\t\t", initialTr);
		createTextViewForCell("Group Ref. \t\t\t", initialTr);
		createTextViewForCell("No. Dehorned \t\t\t", initialTr);
		createTextViewForCell("D-Caine \t\t\t", initialTr);
		
		dehornTable.addView(initialTr, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(getActivity());
		createTextViewForCell("", blankTr);
		dehornTable.addView(blankTr, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
	}
	
	public void addRowsToTable(DehorningEvent dehornEvent, TableLayout dehornTable) {
		// Create a TableRow
		TableRow tr = new TableRow(getActivity());
		tr.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
					
		// create text views for cells
		createTextViewForCell(dehornEvent.getDateDehorned() + "\t\t", tr);
		createTextViewForCell(dehornEvent.getGroupRef() + "\t\t", tr);
		createTextViewForCell("" + dehornEvent.getNoDehorned(), tr);
		createTextViewForCell(dehornEvent.getDCaine() + "\t\t", tr);
		
		//Add the TableRow to the TableLayout
		dehornTable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(getActivity());
		createTextViewForCell("", blankTr);
		dehornTable.addView(blankTr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
	}
	
	public void createTextViewForCell(String textToSet, TableRow tr) {
		TextView textview = new TextView(getActivity());
		textview.setText(textToSet);
		textview.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		tr.addView(textview);
	}
}