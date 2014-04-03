package com.example.dbattempt;

import org.joda.time.DateTime;
import org.joda.time.Months;

import com.example.dbattempt.helper.DatabaseHelper;
import com.example.dbattempt.model.Cow;
import com.example.dbattempt.model.DehorningEvent;
import com.example.dbattempt.model.MonthlyReport;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Database Helper
    DatabaseHelper db;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = DatabaseHelper.getInstance(getApplicationContext());
        db.deleteAllMonthlyReports(); //DELETE AFTER
        createMonthlyReportIfNeeded();
    }
   
    // checks if particular date was in past month
    private static boolean inPastMonth(String date) {
    	int firstHyphenPosition = date.indexOf("-");
		int secondHyphenPosition = date.lastIndexOf("-");
		int eventYear = Integer.parseInt(date.substring(0, firstHyphenPosition));
		int eventMonth = Integer.parseInt(date.substring(firstHyphenPosition + 1, secondHyphenPosition));
		DateTime eventDate = new DateTime(eventYear, eventMonth, 1, 0, 0, 0);
		
		
		DateTime currentDate = new DateTime();
		
		int months = Months.monthsBetween(eventDate, currentDate).getMonths();
		
		if(months == 1)		// if one month apart, then event was in the past month for monthly record
			return true;
		else
			return false;
    }
    
    // checks if particular date was this month
    private boolean inThisMonth(String date) {
    	if(!(date.equals("---"))) {		// if using moved away date and hasn't yet moved away, return true
    		int firstHyphenPosition = date.indexOf("-");
    		int secondHyphenPosition = date.lastIndexOf("-");
    		int eventYear = Integer.parseInt(date.substring(0, firstHyphenPosition));
    		int eventMonth = Integer.parseInt(date.substring(firstHyphenPosition + 1, secondHyphenPosition));
		
    		DateTime todaysDate = new DateTime();	// gets today's date
    		int currentYear = todaysDate.getYear();
    		int currentMonth = todaysDate.getMonthOfYear();
		
    		if((currentYear == eventYear) && (currentMonth == eventMonth))		// if same year and same month, joined this month
    			return true;
    		else
    			return false;
    	}
    	else
    		return true;
    }
    
    // the day that the cow left in the previous month if it left mid-way through
    private static int getDaysOfMonth(String date) {
		int secondHyphenPosition = date.lastIndexOf("-");
		int daysOfMonth = Integer.parseInt(date.substring(secondHyphenPosition + 1));
	    
	    return daysOfMonth;
    }
    
    // just gets how many days were in previous month
    private int getHowManyDaysInPreviousMonth() {
		DateTime todaysDate = new DateTime();	// gets today's date
		int currentYear = todaysDate.getYear();
		int previousMonth = todaysDate.getMonthOfYear() - 1;
		DateTime previousMonthsDate = new DateTime(currentYear, previousMonth, 1, 0, 0, 0);
		int daysInPreviousMonth = previousMonthsDate.dayOfMonth().getMaximumValue();
		return daysInPreviousMonth;
	}
    
    // subtracts the number of days in current month from cow's total days to find how many days cow was on farm when month change happened
    	// can return a minus number if cow has moved but isn't important in cheap/expensive calf no. calculation because being compared to 91
    private int cowDaysAtEndOfPreviousMonth(Cow cow) {
    	DateTime todaysDate = new DateTime();	// gets today's date
    	int daysAtEndOfMonth = cow.getDaysOnFarm() - todaysDate.getDayOfMonth();
    	return daysAtEndOfMonth;
    }
    
    // subtracts the day the cow joined farm from number of days in month to find how many days it was on farm when the month change happened
    	// then adds 1 to include the day that the cow joined the farm (chargable)
    private int cowDaysAtEndOfPreviousMonthIfMoved(Cow cow) {
    	int daysAtEndOfMonth = (getHowManyDaysInPreviousMonth() - getDaysOfMonth(cow.getDateArrived()) + 1);	
    	return daysAtEndOfMonth;																			
    }
    
    //////
    
    private int calculateTotalCalves() {
    	int calfCount = 0;
    	for(Cow cow : db.getAllCows()) {
    		if(inThisMonth(cow.getDateMovedOffFarm()))		// if cow hasn't moved off farm
    			if(!(inThisMonth(cow.getDateArrived())))	// and if cow arrived this month, don't add (monthly report is for previous month)
    				calfCount++;
		}
    	return calfCount;
    }
    
    private int calculateCheapCalves() {
    	int cheapCalfCount = 0;
    	for(Cow cow : db.getAllCows()) {
    		if(inThisMonth(cow.getDateMovedOffFarm()))
    			if(!(inThisMonth(cow.getDateArrived())))	// if cow arrived this month, don't add
					if(cowDaysAtEndOfPreviousMonth(cow) < 92)		// if cow had been on farm for less than 92 days at the END OF MONTH, then add as cheap calf
						cheapCalfCount++;
		}
        return cheapCalfCount;
    }
    
    private int calculateExpensiveCalves() {
    	int expensiveCalfCount = 0;
    	for(Cow cow : db.getAllCows()) {
    		if(inThisMonth(cow.getDateMovedOffFarm()))
    			if(!(inThisMonth(cow.getDateArrived())))	// if cow arrived this month, don't add
    				if(cowDaysAtEndOfPreviousMonth(cow) > 91)		// if cow had been on farm for over 91 days at the END OF MONTH, then add as expensive calf
    					expensiveCalfCount++;
		}
        return expensiveCalfCount;
    }
    
    private int calculateNoDehorned() {
    	int dehornedCount = 0;
    	for(DehorningEvent dehornedRecord : db.getAllDehorningEvents()) {
    		if(inPastMonth(dehornedRecord.getDateDehorned()))		// if event was in the previous month, add number dehorned
    			dehornedCount+= dehornedRecord.getNoDehorned();
    	}
    	return dehornedCount;
    }

    private int calculateNoMoved() {
    	int calvesMovedCount = 0;
    	for(Cow cow : db.getAllCows()) {
    		if(!(cow.getDateMovedOffFarm().equals("---")))				// if cow has moved
    			if(inPastMonth(cow.getDateMovedOffFarm()))				// and moved in past month
    				if(!(cow.getMovedTo()).equals("Died"))				// and cow didn't die
    					calvesMovedCount++;								// then cow must have moved :-)
		}
        return calvesMovedCount;
    }
    
    private int calculateNoDied() {
    	int calvesDiedCount = 0;
    	for(Cow cow : db.getAllCows()) {
    		if(!(cow.getDateMovedOffFarm().equals("---")))				// if cow has moved
    			if(inPastMonth(cow.getDateMovedOffFarm()))				// and moved in past month
				if((cow.getMovedTo()).equals("Died"))					// and died
					calvesDiedCount++;									// add to death count :-(
		}
        return calvesDiedCount;
    }
    
    private double calculateTotalIntake() {
    	double totalIntake = 0;
    	double calvesIntake = 0;
    	double dehornedIntake = 0;
    	int lastMonthDays;						// holds the day of the month that the cow left
    	
    	// calves price calculation
    	for(Cow cow : db.getAllCows()) {
    		if(!(cow.getDateMovedOffFarm().equals("---"))) {						// if cow has moved
    			lastMonthDays = getDaysOfMonth(cow.getDateMovedOffFarm());
    			if(inPastMonth(cow.getDateMovedOffFarm())) {						// and cow moved in the previous month
        			calvesIntake += calvesIntakeCalculation(cow.getDaysOnFarm(), lastMonthDays);
    			}
    			else if(!(inThisMonth(cow.getDateArrived())))						// else if cow didn't arrive this month (monthly report is for previous month)
    				calvesIntake += calvesIntakeCalculation(cowDaysAtEndOfPreviousMonthIfMoved(cow), lastMonthDays);		// calculate for monthly report
    		}
    		else {																	// cow hasn't moved in previous month
    			if(!(inThisMonth(cow.getDateArrived())))						// and cow didn't arrive this month (monthly report is for previous month)
    				calvesIntake += calvesIntakeCalculation(cowDaysAtEndOfPreviousMonth(cow), getHowManyDaysInPreviousMonth());		// calculate for monthly report
    		}
    	}
    	
    	// dehorning price calculation
		dehornedIntake+= (calculateNoDehorned() * 2.00);				// add £2 for the number of dehornings that date
    	
    	totalIntake = (calvesIntake + dehornedIntake) * 1.2;			// calves intake + dehorned intake + 20% VAT
    	
    	return totalIntake;
    }
    
    private double calvesIntakeCalculation(int cowDaysOnFarm, int lastChargableDayInMonth) {
    	int daysAtLowerRate;				// days in last month where cow is 50p
		int daysAtHigherRate;				// days in last month where cow is 85p
		double calvesIntake = 0;
		boolean cowJoinedMidWayThroughMonth = false;
    	
    	if(cowDaysOnFarm < 92) 									// if cow hadn't been on farm for over 91 days at end of month / when cow left farm
			if(cowDaysOnFarm >= lastChargableDayInMonth)						// but cow had been on farm every day that month / until it left
				daysAtLowerRate = lastChargableDayInMonth;								// cow is at lower rate for every day of month / until it left
			else {
				daysAtLowerRate = cowDaysOnFarm;							// else cow is at lower rate for every day it had been on farm for that month
				cowJoinedMidWayThroughMonth = true;								// and cow flagged as joining somewhere during the month
			}
		else {
			daysAtLowerRate = lastChargableDayInMonth - (cowDaysOnFarm - 91);	// else find how many days in month it was at lower
			if(daysAtLowerRate < 0)		// if less than zero, then it has no days at lower rate and can be charged the higher rate for every day of the month / until it left
				daysAtLowerRate = 0;
		}
		
		if(cowJoinedMidWayThroughMonth == false)				// if cow joined midway through month, no days at higher rate (can't be >91 days on farm)
			if(daysAtLowerRate > 0)								// if there are days at lower rate, do subtraction from month days to find higher rate days
				daysAtHigherRate = lastChargableDayInMonth - daysAtLowerRate;
			else												// but if no days at lower rate, use last chargable day as higher rate days 
				daysAtHigherRate = lastChargableDayInMonth;					// (every day cow was on farm that month)
		else
			daysAtHigherRate = 0;
		
		calvesIntake+= (daysAtLowerRate * 0.5);	
		calvesIntake+= (daysAtHigherRate * 0.85);
		
		return calvesIntake;
    }
    
    private void createMonthlyReportIfNeeded() {
    	DateTime todaysDate = new DateTime();	// gets today's date
		int currentYear = todaysDate.getYear();
		int currentMonth = todaysDate.getMonthOfYear();
		
		StringBuilder builder = new StringBuilder();
		builder.append(currentYear).append("-");
		if(currentMonth < 9) 
			builder.append("0" + currentMonth).append("-");
		else 
			builder.append(currentMonth).append("-");	
		builder.append("01");

        String date = builder.toString();
        
        boolean reportExists = false;
        
        // checks if report for that month already exists
        for(MonthlyReport monthlyReport : db.getAllMonthlyReports()) {
			if(date.equals(monthlyReport.getDate())) {
				reportExists = true;
				break;
			}
		}
        
        // if report doesn't already exist, create it
        if(reportExists == false) {
        	MonthlyReport monthlyReportToAdd = new MonthlyReport(date, calculateTotalCalves(), calculateCheapCalves(), calculateExpensiveCalves(), calculateNoDehorned(), calculateNoMoved(), calculateNoDied(), calculateTotalIntake());	
        	db.createMonthlyReport(monthlyReportToAdd);
        	Toast toast = Toast.makeText(getApplicationContext(), "New Monthly Report Available", Toast.LENGTH_LONG);
    		toast.show();
        }
    }
    
    public void goToOverview(View view) {
    	Intent intent = new Intent(this, OverviewActivity.class);
		startActivity(intent);
    }
    
    public void goToMealRecord(View view) {
    	Intent intent = new Intent(this, MealRecordActivity.class);
		startActivity(intent);
    }
    
    public void goToMilkReplacer(View view) {
    	Intent intent = new Intent(this, MilkReplacerActivity.class);
		startActivity(intent);
    }
    
    public void goToMonthlyRecord(View view) {
    	Intent intent = new Intent(this, MonthlyReportActivity.class);
		startActivity(intent);
    }
}
