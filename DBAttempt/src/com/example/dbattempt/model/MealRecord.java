package com.example.dbattempt.model;

public class MealRecord {
	String purchaseDate;
    String foodName;
    String supplier;
    String invoiceNo;
    int quantity;
    String groupsFed;
    
    
    // constructors
    public MealRecord() {
    }
 
    public MealRecord(String purchaseDate, String foodName, String supplier, String invoiceNo, int quantity, String groupsFed) {
        this.purchaseDate = purchaseDate;
        this.foodName = foodName;
        this.supplier = supplier;
        this.invoiceNo = invoiceNo;
        this.quantity = quantity;
        this.groupsFed = groupsFed;
    }
    
    
    
    // setters
    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
 
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
    
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
 
    public void setGroupsFed(String groupsFed) {
        this.groupsFed = groupsFed;
    }
    
    //////////////////////////
    
    // getters
    public String getPurchaseDate() {
        return this.purchaseDate;
    }
 
    public String getFoodName() {
        return this.foodName;
    }
    
    public String getSupplier() {
        return this.supplier;
    }
    
    public String getInvoiceNo() {
        return this.invoiceNo;
    }
    
    public int getQuantity() {
        return this.quantity;
    }
    
    public String getGroupsFed() {
        return this.groupsFed;
    }
}
