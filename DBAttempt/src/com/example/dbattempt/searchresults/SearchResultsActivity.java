package com.example.dbattempt.searchresults;

import com.example.dbattempt.OverviewActivity;
import com.example.dbattempt.R;
import com.example.dbattempt.tabfragment.SearchFragmentTab1;
import com.example.dbattempt.tabfragment.SearchFragmentTab2;
import com.example.dbattempt.tabfragment.TabListener;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class SearchResultsActivity extends OverviewActivity {

	Fragment fragmentTab1 = new SearchFragmentTab1();
	Fragment fragmentTab2 = new SearchFragmentTab2();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		handleIntent(getIntent());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);
		// Show the Up button in the action bar.
	}
	
	@Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_results, menu);
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
	
	@Override
	protected void setupActionBar() {
		ActionBar actionBar = getActionBar();
 
        // Create Actionbar Tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
        // Set Tab Icon and Titles
        Tab1 = actionBar.newTab().setText("General");
        Tab2 = actionBar.newTab().setText("Medical");
 
        // Set Tab Listeners
        Tab1.setTabListener(new TabListener(fragmentTab1));
        Tab2.setTabListener(new TabListener(fragmentTab2));
        
        // Add tabs to actionbar
        actionBar.addTab(Tab1);
        actionBar.addTab(Tab2);

	}
	
	private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            
            Bundle bundle=new Bundle();
            bundle.putString("search", query);
              // passes search argument in a bundle to both fragments
            fragmentTab1.setArguments(bundle);
            fragmentTab2.setArguments(bundle);
        }
    }
}
