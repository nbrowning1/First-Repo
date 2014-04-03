package com.example.dbattempt.model;

public class MilkRecord {
	String purchaseDate;
    String milkName;
    String manufacturer;
    String supplier;
    String invoiceNo;
    int quantity;
    
    
    // constructors
    public MilkRecord() {
    }
 
    public MilkRecord(String purchaseDate, String milkName, String manufacturer, String supplier, String invoiceNo, int quantity) {
        this.purchaseDate = purchaseDate;
        this.milkName = milkName;
        this.manufacturer = manufacturer;
        this.supplier = supplier;
        this.invoiceNo = invoiceNo;
        this.quantity = quantity;
    }
    
    
    
    // setters
    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
 
    public void setMilkName(String milkName) {
        this.milkName = milkName;
    }
    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
    
    //////////////////////////
    
    // getters
    public String getPurchaseDate() {
        return this.purchaseDate;
    }
 
    public String getMilkName() {
        return this.milkName;
    }
    
    public String getManufacturer() {
        return this.manufacturer;
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
}
