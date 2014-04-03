package com.example.dbattempt.tabfragment;

import java.text.ParseException;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.dbattempt.R;
import com.example.dbattempt.model.Cow;

public class SearchFragmentTab1 extends FragmentTab1 {
	
	protected View rootView;
	String earTagNo;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment_tab_1, container, false);
        this.rootView = rootView;
        
        // passes search arguments to fragment
        String earTagArgument = this.getArguments().getString("search");
        this.earTagNo = earTagArgument;
        
        try {
			displayCows();
			db.close();
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return rootView;
    }
    
    @Override
    public void displayCows() throws ParseException {
		TableLayout cowTable = (TableLayout) rootView.findViewById(R.id.main_table);
		createEmptyTable(cowTable);
		
		// adds cow details rows for searched partial ear tag
		for(Cow cow : db.getCows(earTagNo)) {
			addRowsToTable(cow, cowTable);
		}
		
		db.close();
	}
}
