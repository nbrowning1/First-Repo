package com.example.dbattempt.searchresults;

import java.text.ParseException;

import com.example.dbattempt.DeleteMedicalRecordActivity;
import com.example.dbattempt.R;
import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.MedicalRecord;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.support.v4.app.NavUtils;

public class SearchResultsDeleteMedicalActivity extends DeleteMedicalRecordActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results_delete_medical);
		// Show the Up button in the action bar.
		setupActionBar();
		
		handleIntent(getIntent());
	}

	@Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
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
		getMenuInflater().inflate(R.menu.search_results_delete_medical, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String partialEarTag = intent.getStringExtra(SearchManager.QUERY);
            
            try {
				displayChosenRecords(partialEarTag);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
    }
	
	private void displayChosenRecords(String earTag) throws ParseException {
		TableLayout medRecordTable = (TableLayout) findViewById(R.id.main_table);
		createEmptyTable(medRecordTable);
		
		DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
		
		// adds cow details rows
		for(MedicalRecord medRecord : db.getMedicalRecords(earTag)) {
			addRowsToTable(medRecord, medRecordTable);
		}
		
		db.close();
	}
}
