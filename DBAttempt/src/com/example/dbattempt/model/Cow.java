package com.example.dbattempt.model;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class Cow {
	String earTagNo;
    String gender;
    String dateArrived;
    int daysOnFarm;
    double price;
    String dateVaccinated;
    String dateBovivacBooster;
    String dateIBRRSPBooster;
    String dateMovedOffFarm;
    String movedTo;
    
    long dateArrivedInMillis;
    
    // constructors
    public Cow() {
    }
 
    public Cow(String earTagNo, String gender, String dateArrived, 
    				String dateVaccinated, String dateBovivacBooster, String dateIBRRSPBooster, String dateMovedOffFarm, String movedTo) {
        this.earTagNo = earTagNo;
        this.gender = gender;
        this.dateArrived = dateArrived;
        this.dateVaccinated = dateVaccinated;
        this.dateBovivacBooster = dateBovivacBooster;
        this.dateIBRRSPBooster = dateIBRRSPBooster;
        this.dateMovedOffFarm = dateMovedOffFarm;
        this.movedTo = movedTo;
    }
    
    // these methods parse the date arrived or date moved away string and return a jodatime date
    private DateTime getStartDate() {
    	int firstHyphenPosition = dateArrived.indexOf("-");
		int secondHyphenPosition = dateArrived.lastIndexOf("-");
		int origYears = Integer.parseInt(dateArrived.substring(0, firstHyphenPosition));
		int origMonths = Integer.parseInt(dateArrived.substring(firstHyphenPosition + 1, secondHyphenPosition));
		int origDays = Integer.parseInt(dateArrived.substring(secondHyphenPosition + 1));
	    
	    DateTime startDate = new DateTime(origYears, origMonths, origDays, 0, 0, 0, 0);
	    return startDate;
    }
    
    private DateTime getEndDate() {
    	int firstHyphenPosition = dateMovedOffFarm.indexOf("-");
		int secondHyphenPosition = dateMovedOffFarm.lastIndexOf("-");
		int origYears = Integer.parseInt(dateMovedOffFarm.substring(0, firstHyphenPosition));
		int origMonths = Integer.parseInt(dateMovedOffFarm.substring(firstHyphenPosition + 1, secondHyphenPosition));
		int origDays = Integer.parseInt(dateMovedOffFarm.substring(secondHyphenPosition + 1));
	    
	    DateTime endDate = new DateTime(origYears, origMonths, origDays, 0, 0, 0, 0);
	    return endDate;
    }
    //////
    
    private int calculateDaysOnFarm() {
    	DateTime endDate;
    	
    	if((dateMovedOffFarm == null) || (dateMovedOffFarm.equals("---"))) {
    		endDate = new DateTime();		// gets today's date (if cow hasn't moved off farm)
    	}
    	else {
    		endDate = getEndDate();			// gets date of moving off farm for no. days spent on farm
    	}
	    Days d = Days.daysBetween(getStartDate(), endDate);
	    int days = d.getDays() + 1;		// + 1 for day arrived to be included
	    
	    return days;
    }
    
    private double calculatePrice() {
    	double price;
    	if(daysOnFarm > 91) {
    		int daysOver91 = daysOnFarm - 91;
    		price = (91 * 0.5) + (daysOver91 * 0.85);
    	}
    	else {
    		price = daysOnFarm * 0.5;
    	}
    	
    	return price;
    }
    
    private long dateArrivedToMilliseconds() {
    	return getStartDate().getMillis();
    }
    
    /////////////////////////
    
    // setters
    public void setEarTagNo(String earTagNo) {
        this.earTagNo = earTagNo;
    }
 
    public void setGender(String gender) {
        this.gender = gender;
    }
 
    public void setDateArrived(String dateArrived) {
        this.dateArrived = dateArrived;
    }
    
    // used date arrived to figure out how many days on farm
    public void setDaysOnFarm(){
    	this.daysOnFarm = calculateDaysOnFarm();
    }
     
    // uses days on farm to figure out price
    public void setPrice(){
        this.price = calculatePrice();
    }
    
    public void setDateVaccinated(String dateVaccinated) {
    	this.dateVaccinated = dateVaccinated;
    }
    
    public void setDateBovivacBooster(String dateBovivacBooster) {
    	this.dateBovivacBooster = dateBovivacBooster;
    }

    public void setDateIBRRSPBooster(String dateIBRRSPBooster) {
    	this.dateIBRRSPBooster = dateIBRRSPBooster;
    }
 
    public void setDateMovedOffFarm(String dateMovedOffFarm) {
    	this.dateMovedOffFarm = dateMovedOffFarm;
    }
    
    public void setMovedTo(String movedTo) {
    	this.movedTo = movedTo;
    }
    
    
    public void setDateArrivedInMillis() {
    	this.dateArrivedInMillis = dateArrivedToMilliseconds();
    }
    
    //////////////////////////
    
    // getters
    public String getEarTagNo() {
        return this.earTagNo;
    }
 
    public String getGender() {
        return this.gender;
    }
 
    public String getDateArrived() {
        return this.dateArrived.toString();
    }
    
    public int getDaysOnFarm() {
    	return this.daysOnFarm;
    }
    
    public double getPrice() {
    	return this.price;
    }
    
    public String getDateVaccinated() {
    	return this.dateVaccinated;
    }
    
    public String getBovivacBoosterDate() {
    	return this.dateBovivacBooster;
    }
    
    public String getIBRRSPBoosterDate() {
    	return this.dateIBRRSPBooster;
    }
    
    public String getDateMovedOffFarm() {
    	return this.dateMovedOffFarm;
    }
    
    public String getMovedTo() {
    	return this.movedTo;
    }
    
    
    public long getDateArrivedInMillis() {
    	return this.dateArrivedInMillis;
    }
}
