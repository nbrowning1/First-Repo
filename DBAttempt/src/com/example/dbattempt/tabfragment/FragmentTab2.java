package com.example.dbattempt.tabfragment;

import java.text.ParseException;

import com.example.dbattempt.R;
import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.MedicalRecord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.app.Fragment;
 
public class FragmentTab2 extends Fragment {
	protected DatabaseHelper db;
	protected View rootView;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_2, container, false);
        this.rootView = rootView;
        try {
        	displayMedicalRecords();
        	db.close();
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return rootView;
    }
 
    public void displayMedicalRecords() throws ParseException {
		TableLayout medicalRecordTable = (TableLayout) rootView.findViewById(R.id.main_table);
		createEmptyTable(medicalRecordTable);
		
		// adds medical details rows
		for(MedicalRecord medicalRecord : db.getAllMedicalRecords()) {
			addRowsToTable(medicalRecord, medicalRecordTable);
		}
	}
	
	public void createEmptyTable(TableLayout medicalRecordTable) {
		db = DatabaseHelper.getInstance(getActivity().getApplicationContext());
		TableRow initialTr = new TableRow(getActivity());
		
		// makes column headings for table
		createTextViewForCell("Ear Tag No. \t\t\t", initialTr);
		createTextViewForCell("Details Of Medication \t\t\t", initialTr);
		createTextViewForCell("Batch No. \t\t\t", initialTr);
		createTextViewForCell("Date of Use \t\t\t", initialTr);
		createTextViewForCell("Quantity used \t\t\t", initialTr);
		createTextViewForCell("Who Administered Medication\t\t\t", initialTr);
		createTextViewForCell("Reason/Comments \t\t\t", initialTr);
		
		medicalRecordTable.addView(initialTr, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(getActivity());
		createTextViewForCell("", blankTr);
		medicalRecordTable.addView(blankTr, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
	}
	
	public void addRowsToTable(MedicalRecord medicalRecord, TableLayout medicalRecordTable) {
		// Create a TableRow
		TableRow tr = new TableRow(getActivity());
		tr.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
					
		// LEADING ZEROES NOT NECESSARY?
		// create text views for cells
		createTextViewForCell(medicalRecord.getEarTagNo() + "\t\t", tr);
		createTextViewForCell(medicalRecord.getDetailsOfMedication() + "\t\t", tr);
		createTextViewForCell(addLeadingZeroes(medicalRecord.getBatchNo(), 12) + "\t\t", tr);
		createTextViewForCell(medicalRecord.getDateOfUse() + "\t\t", tr);
		createTextViewForCell(addLeadingZeroes(Integer.toString(medicalRecord.getQuantityUsed()), 3) + "\t\t", tr);
		createTextViewForCell(addLeadingZeroes(medicalRecord.getDoctor(), 3) + "\t\t", tr);
		createTextViewForCell(medicalRecord.getComments() + "\t\t", tr);
		
		//Add the TableRow to the TableLayout
		medicalRecordTable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		
		// creates empty row for formatting
		TableRow blankTr = new TableRow(getActivity());
		createTextViewForCell("", blankTr);
		medicalRecordTable.addView(blankTr, new TableLayout.LayoutParams(
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
	
	// adds leading zeroes as the database will cut them off when storing a number like 00001234567 etc.
	protected static String addLeadingZeroes(String id, int FieldLength) {
		int id_length = id.length();
		String zeroes = "";
		while(id_length < FieldLength) {
			zeroes += "0";
			id_length++;
		}
			
		String fullText = "" + zeroes + id;
		
		return fullText;
	}
}