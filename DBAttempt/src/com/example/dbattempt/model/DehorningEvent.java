package com.example.dbattempt.model;

public class DehorningEvent {
	String dateDehorned;
	String groupRef;
    int noDehorned;
    String dCaine;
    
    
    // constructors
    public DehorningEvent() {
    }
 
    public DehorningEvent(String dateDehorned, String groupRef, int noDehorned, String dCaine) {
        this.dateDehorned = dateDehorned;
        this.groupRef = groupRef;
        this.noDehorned = noDehorned;
        this.dCaine = dCaine;
    }
    
    
    
    // setters
    public void setDateDehorned(String dateDehorned) {
        this.dateDehorned = dateDehorned;
    }
    
    public void setGroupRef(String groupRef) {
        this.groupRef = groupRef;
    }
 
    public void setNoDehorned(int noDehorned) {
        this.noDehorned = noDehorned;
    }
    
    public void setDCaine(String dCaine) {
        this.dCaine = dCaine;
    }
 
    //////////////////////////
    
    // getters
    public String getDateDehorned() {
        return this.dateDehorned;
    }
    
    public String getGroupRef() {
        return this.groupRef;
    }
 
    public int getNoDehorned() {
        return this.noDehorned;
    }
    
    public String getDCaine() {
        return this.dCaine;
    }
}
