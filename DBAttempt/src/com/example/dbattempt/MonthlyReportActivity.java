package com.example.dbattempt;

import java.text.DecimalFormat;

import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.MonthlyReport;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MonthlyReportActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	DatabaseHelper db;
	DecimalFormat df = new DecimalFormat("0.00");  // keeps price to 2 decimal places

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monthly_report);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		db = DatabaseHelper.getInstance(getApplicationContext());
		
		int noOfItems = 0;
		
		for(MonthlyReport monthlyReport : db.getAllMonthlyReports()) {
			noOfItems++;
		}
		
		mViewPager.setCurrentItem(noOfItems - 1);		// sets default tab to right-most tab
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.monthly_report, menu);
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

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			MonthlyReport monthlyReport = db.getAllMonthlyReports().get(position);
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			// passes each attribute of the cow with a tag to use it later
			args.putInt("total calves", monthlyReport.getTotalCalves());
			args.putInt("cheap calves", monthlyReport.getCheapCalves());
			args.putInt("expensive calves", monthlyReport.getExpensiveCalves());
			args.putInt("total dehorned", monthlyReport.getNoDehorned());
			args.putInt("total moved", monthlyReport.getNoMoved());
			args.putInt("total died", monthlyReport.getNoDied());
			args.putString("total income", "£" + df.format(monthlyReport.getTotalIntake()));
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// number of pages (number of monthly reports)
			DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
			int count = 0;
			
			for(MonthlyReport monthlyReport : db.getAllMonthlyReports()) {	// counts monthly reports
				count++;
	    	}
			return count;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// sets up titles of tabs
			MonthlyReport monthlyReport = db.getAllMonthlyReports().get(position);
			
			return monthlyReport.getDate();
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_monthly_report_dummy, container, false);
			TextView totalCalves = (TextView) rootView.findViewById(R.id.total_calves_show);
			TextView cheapCalves = (TextView) rootView.findViewById(R.id.cheap_calves_show);
			TextView expensiveCalves = (TextView) rootView.findViewById(R.id.expensive_calves_show);
			TextView totalDehorned = (TextView) rootView.findViewById(R.id.no_dehorned_show);
			TextView totalMoved = (TextView) rootView.findViewById(R.id.no_moved_show);
			TextView totalDied = (TextView) rootView.findViewById(R.id.no_died_show);
			TextView totalIntake = (TextView) rootView.findViewById(R.id.total_intake_show);
			
			totalCalves.setText(Integer.toString(getArguments().getInt("total calves")));
			cheapCalves.setText(Integer.toString(getArguments().getInt("cheap calves")));
			expensiveCalves.setText(Integer.toString(getArguments().getInt("expensive calves")));
			totalDehorned.setText(Integer.toString(getArguments().getInt("total dehorned")));
			totalMoved.setText(Integer.toString(getArguments().getInt("total moved")));
			totalDied.setText(Integer.toString(getArguments().getInt("total died")));
			totalIntake.setText(getArguments().getString("total income"));
			return rootView;
		}
	}

}
