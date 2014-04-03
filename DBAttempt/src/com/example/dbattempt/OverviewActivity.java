package com.example.dbattempt;

import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.tabfragment.FragmentTab1;
import com.example.dbattempt.tabfragment.FragmentTab2;
import com.example.dbattempt.tabfragment.FragmentTab3;
import com.example.dbattempt.tabfragment.TabListener;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;

public class OverviewActivity extends FragmentActivity {

	protected DatabaseHelper db;
	protected ActionBar.Tab Tab1, Tab2, Tab3;
	Fragment fragmentTab1 = new FragmentTab1();
	Fragment fragmentTab2 = new FragmentTab2();
	Fragment fragmentTab3 = new FragmentTab3();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overview);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	protected void setupActionBar() {
		ActionBar actionBar = getActionBar();
 
        // Create Actionbar Tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // Set Tab Icon and Titles
        Tab1 = actionBar.newTab().setText("General");
        Tab2 = actionBar.newTab().setText("Medical");
        Tab3 = actionBar.newTab().setText("Dehorns");
 
        // Set Tab Listeners
        Tab1.setTabListener(new TabListener(fragmentTab1));
        Tab2.setTabListener(new TabListener(fragmentTab2));
        Tab3.setTabListener(new TabListener(fragmentTab3));
        
        // Add tabs to actionbar
        actionBar.addTab(Tab1);
        actionBar.addTab(Tab2);
        actionBar.addTab(Tab3);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.overview, menu);
		
		// Associate searchable configuration with the SearchView
	    SearchManager searchManager =
	           (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView =
	            (SearchView) menu.findItem(R.id.search).getActionView();
	    searchView.setSearchableInfo(
	            searchManager.getSearchableInfo(getComponentName()));

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
		case R.id.add_cow:
			// Go to Add Cow activity
			Intent addIntent = new Intent(this, AddOptionsActivity.class);
			startActivity(addIntent);
			return true;
		case R.id.action_edit:
			Intent editIntent = new Intent(this, EditCowOptionsActivity.class);
			startActivity(editIntent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
