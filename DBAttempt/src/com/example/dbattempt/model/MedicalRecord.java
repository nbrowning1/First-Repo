package com.example.dbattempt.model;


public class MedicalRecord {
	String earTagNo;
    String detailsOfMedication;
    String batchNo;
    String dateOfUse;
    int quantityUsed;
    String doctor;
    String comments;
    
    
    // constructors
    public MedicalRecord() {
    }
 
    public MedicalRecord(String earTagNo, String detailsOfMedication, String batchNo, 
    				String dateOfUse, int quantityUsed, String doctor, String comments) {
        this.earTagNo = earTagNo;
        this.detailsOfMedication = detailsOfMedication;
        this.batchNo = batchNo;
        this.dateOfUse = dateOfUse;
        this.quantityUsed = quantityUsed;
        this.doctor = doctor;
        this.comments = comments;
    }
    
    
    
    // setters
    public void setEarTagNo(String earTagNo) {
        this.earTagNo = earTagNo;
    }
 
    public void setDetailsOfMedication(String detailsOfMedication) {
        this.detailsOfMedication = detailsOfMedication;
    }
 
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
    
    public void setDateOfUse(String dateOfUse){
    	this.dateOfUse = dateOfUse;
    }
    
    public void setQuantityUsed(int quantityUsed) {
    	this.quantityUsed = quantityUsed;
    }
    
    public void setDoctor(String doctor) {
    	this.doctor = doctor;
    }

    public void setComments(String comments) {
    	this.comments = comments;
    }
    
    //////////////////////////
    
    // getters
    public String getEarTagNo() {
        return this.earTagNo;
    }
 
    public String getDetailsOfMedication() {
        return this.detailsOfMedication;
    }
 
    public String getBatchNo() {
        return this.batchNo;
    }
    
    public String getDateOfUse() {
    	return this.dateOfUse;
    }
    
    public int getQuantityUsed() {
    	return this.quantityUsed;
    }
    
    public String getDoctor() {
    	return this.doctor;
    }
    
    public String getComments() {
    	return this.comments;
    }
}
