package com.example.dbattempt.model;

import android.app.Activity;

public class MonthlyReport extends Activity {
	String date;
    int totalCalves;
    int cheapCalves;
    int expensiveCalves;
    int noDehorned;
    int noMoved;
    int noDied;
    double totalIntake;
    
    
    
    // constructors
    public MonthlyReport() {
    }
 
    public MonthlyReport(String date, int totalCalves, int cheapCalves, int expensiveCalves, int noDehorned, int noMoved,
    						int noDied, double totalIntake) {
        this.date = date;
        this.totalCalves = totalCalves;
        this.cheapCalves = cheapCalves;
        this.expensiveCalves = expensiveCalves;
        this.noDehorned = noDehorned;
        this.noMoved = noMoved;
        this.noDied = noDied;
        this.totalIntake = totalIntake;
    }
    
    // setters
    public void setDate(String date) {
        this.date = date;
    }
 
    public void setTotalCalves(int totalCalves) {
        this.totalCalves = totalCalves;
    }
    
    public void setCheapCalves(int cheapCalves) {
        this.cheapCalves = cheapCalves;
    }
    
    public void setExpensiveCalves(int expensiveCalves) {
        this.expensiveCalves = expensiveCalves;
    }
    
    public void setNoDehorned(int noDehorned) {
        this.noDehorned = noDehorned;
    }
    
    public void setNoMoved(int noMoved) {
        this.noMoved = noMoved;
    }
    
    public void setNoDied(int noDied) {
        this.noDied = noDied;
    }
    
    public void setTotalIntake(double totalIntake) {
        this.totalIntake = totalIntake;
    }
    
    //////////////////////////
    
    // getters
    public String getDate() {
        return this.date;
    }
 
    public int getTotalCalves() {
        return this.totalCalves;
    }
    
    public int getCheapCalves() {
        return this.cheapCalves;
    }
    
    public int getExpensiveCalves() {
        return this.expensiveCalves;
    }
    
    public int getNoDehorned() {
        return this.noDehorned;
    }
    
    public int getNoMoved() {
        return this.noMoved;
    }
    
    public int getNoDied() {
        return this.noDied;
    }
    
    public double getTotalIntake() {
        return this.totalIntake;
    }
}
