package com.example.dbattempt.tabfragment;

import java.text.ParseException;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.dbattempt.R;
import com.example.dbattempt.model.MedicalRecord;

public class SearchFragmentTab2 extends FragmentTab2 {
	protected View rootView;
	String earTagNo;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment_tab_2, container, false);
        this.rootView = rootView;
        
        // passes search arguments to fragment
        String earTagArgument = this.getArguments().getString("search");
        this.earTagNo = earTagArgument;
        
        try {
        	displayMedicalRecords();
        	db.close();
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return rootView;
    }
    
    @Override
    public void displayMedicalRecords() throws ParseException {
		TableLayout medicalRecordTable = (TableLayout) rootView.findViewById(R.id.main_table);
		createEmptyTable(medicalRecordTable);
		
		// adds medical details rows
		for(MedicalRecord medicalRecord : db.getMedicalRecords(earTagNo)) {
			addRowsToTable(medicalRecord, medicalRecordTable);
		}
		
		db.close();
	}
}
