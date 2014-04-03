package com.example.dbattempt.helper;

import java.util.ArrayList;
import java.util.List;

import com.example.dbattempt.model.Cow;
import com.example.dbattempt.model.DehorningEvent;
import com.example.dbattempt.model.MealRecord;
import com.example.dbattempt.model.MedicalRecord;
import com.example.dbattempt.model.MilkRecord;
import com.example.dbattempt.model.MonthlyReport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static DatabaseHelper mInstance = null;
	
	// Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "cowsDatabase";
 
    // Table Names
    private static final String TABLE_COW = "cow";
    private static final String TABLE_VACCINATION_DETAILS = "vacDetails";
    private static final String TABLE_MEDICAL = "medical";
    private static final String TABLE_MEAL_RECORD = "mealRecord";
    private static final String TABLE_DEHORNED_RECORD = "dehornedRecord";
    private static final String TABLE_MILK_REPLACER = "milkReplacer";
    private static final String TABLE_MONTHLY_REPORT = "monthlyReports";
    
    // COMMON - column names
    private static final String KEY_EAR_TAG_NO = "earTagNo";
    
    // COW - column names
    private static final String KEY_GENDER = "gender";
    private static final String KEY_DATE_ARRIVED = "dateArrived";
    private static final String KEY_DATE_MOVED_OFF = "dateLeftFarm";
    private static final String KEY_MOVED_TO = "movedTo";
    
    // VACDETAILS - column names
    private static final String KEY_VACCINATION_DATE = "vaccinationDate";
    private static final String KEY_BOVIVAC_DATE = "bovivacBoosterDate";
    private static final String KEY_IBR_RSP_DATE = "ibrRSPBoosterDate";
    
    // MEDICAL - column names
    private static final String KEY_MED_DETAILS = "details";
    private static final String KEY_MED_BATCH_NO = "batchNo";
    private static final String KEY_MED_DATE_OF_USE = "dateOfUse";
    private static final String KEY_MED_QUANTITY_USED = "quantityUsed";
    private static final String KEY_MED_DOCTOR = "doctor";
    private static final String KEY_MED_COMMENTS = "comments";
    
    // MEALRECORD - column names
    private static final String KEY_MEAL_PURCHASE_DATE = "purchaseDate";
    private static final String KEY_MEAL_FOOD_NAME = "foodName";
    private static final String KEY_MEAL_SUPPLIER = "supplier";
    private static final String KEY_MEAL_INVOICE_NO = "invoiceNo";
    private static final String KEY_MEAL_QUANTITY = "quantity";
    private static final String KEY_MEAL_GROUPS_FED = "groupsFed";
    
    // DEHORNEDRECORD - column names
    private static final String KEY_DEHORNED_DATE = "dateDehorned";
    private static final String KEY_DEHORNED_GROUP_REF = "groupRef";
    private static final String KEY_DEHORNED_NUMBER = "noDehorned";
    private static final String KEY_DEHORNED_D_CAINE = "dCaine";
    
    // MILKREPLACER - column names
    private static final String KEY_MILK_PURCHASE_DATE = "purchaseDate";
    private static final String KEY_MILK_NAME = "milkName";
    private static final String KEY_MILK_MANUFACTURER = "manufacturer";
    private static final String KEY_MILK_SUPPLIER = "supplier";
    private static final String KEY_MILK_INVOICE_NO = "invoiceNo";
    private static final String KEY_MILK_QUANTITY = "quantity";
    
    // MONTHLYREPORT - column names
    private static final String KEY_MONTHLY_DATE = "date";
    private static final String KEY_MONTHLY_TOTAL_CALVES = "totalCalves";
    private static final String KEY_MONTHLY_CHEAP_CALVES = "cheapCalves";
    private static final String KEY_MONTHLY_EXPENSIVE_CALVES = "expensiveCalves";
    private static final String KEY_MONTHLY_NO_DEHORNED = "noDehorned";
    private static final String KEY_MONTHLY_NO_MOVED = "noMoved";
    private static final String KEY_MONTHLY_NO_DIED = "noDied";
    private static final String KEY_MONTHLY_TOTAL_INTAKE = "totalIntake";
    
    
//    CREATE  TABLE cow ("earTagNo" INTEGER PRIMARY KEY  NOT NULL , "gender" TEXT, "dateArrived" TEXT, "dateLeftFarm" TEXT, "movedTo" TEXT)
    
    // Table Create Statements
    private static final String CREATE_TABLE_COW = "CREATE TABLE "
            + TABLE_COW + "(" + KEY_EAR_TAG_NO + " TEXT PRIMARY KEY," + KEY_GENDER
            + " TEXT," + KEY_DATE_ARRIVED + " TEXT," + KEY_DATE_MOVED_OFF + " TEXT," + KEY_MOVED_TO + " TEXT" + ")";
    
    private static final String CREATE_TABLE_VAC_DETAILS = "CREATE TABLE "
            + TABLE_VACCINATION_DETAILS + "(" + KEY_EAR_TAG_NO + " TEXT PRIMARY KEY," + KEY_VACCINATION_DATE
            + " TEXT," + KEY_BOVIVAC_DATE + " TEXT," + KEY_IBR_RSP_DATE
            + " TEXT" + ")";
    
    private static final String CREATE_TABLE_MEDICAL = "CREATE TABLE "
            + TABLE_MEDICAL + "(" + KEY_EAR_TAG_NO + " TEXT," + KEY_MED_DETAILS
            + " TEXT," + KEY_MED_BATCH_NO + " TEXT," + KEY_MED_DATE_OF_USE + " DATE," 
            + KEY_MED_QUANTITY_USED + " INTEGER," + KEY_MED_DOCTOR + " TEXT," + KEY_MED_COMMENTS + " TEXT" + ")";
    
    private static final String CREATE_TABLE_DEHORNED_RECORD = "CREATE TABLE "
            + TABLE_DEHORNED_RECORD + "(" + KEY_DEHORNED_DATE + " DATE," + KEY_DEHORNED_GROUP_REF + " TEXT," 
    		+ KEY_DEHORNED_NUMBER + " INTEGER," + KEY_DEHORNED_D_CAINE + " TEXT" + ")";
    
    private static final String CREATE_TABLE_MEAL_RECORD = "CREATE TABLE "
            + TABLE_MEAL_RECORD + "(" + KEY_MEAL_PURCHASE_DATE + " DATE," + KEY_MEAL_FOOD_NAME + " TEXT," 
    		+ KEY_MEAL_SUPPLIER + " TEXT," + KEY_MEAL_INVOICE_NO + " TEXT," + KEY_MEAL_QUANTITY
    		+ " INTEGER," + KEY_MEAL_GROUPS_FED + " TEXT" + ")";
    
    private static final String CREATE_TABLE_MILK_REPLACER = "CREATE TABLE "
            + TABLE_MILK_REPLACER + "(" + KEY_MILK_PURCHASE_DATE + " DATE," + KEY_MILK_NAME + " TEXT," + KEY_MILK_MANUFACTURER
            + " TEXT," + KEY_MILK_SUPPLIER + " TEXT," + KEY_MILK_INVOICE_NO + " TEXT," + KEY_MILK_QUANTITY + " INTEGER" + ")";
    
    private static final String CREATE_TABLE_MONTHLY_REPORT = "CREATE TABLE "
            + TABLE_MONTHLY_REPORT + "(" + KEY_MONTHLY_DATE + " DATE PRIMARY KEY," + KEY_MONTHLY_TOTAL_CALVES + " INTEGER," + KEY_MONTHLY_CHEAP_CALVES
            + " INTEGER," + KEY_MONTHLY_EXPENSIVE_CALVES + " INTEGER," + KEY_MONTHLY_NO_DEHORNED + " INTEGER," + KEY_MONTHLY_NO_MOVED + " INTEGER," 
            +  KEY_MONTHLY_NO_DIED + " INTEGER," + KEY_MONTHLY_TOTAL_INTAKE + " INTEGER" +")";
 
    
    
    public static DatabaseHelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you 
        // don't accidentally leak an Activity's context.
        if (mInstance == null) {
          mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
      }
    
    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    
    
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_COW);
        db.execSQL(CREATE_TABLE_VAC_DETAILS);
        db.execSQL(CREATE_TABLE_MEDICAL);
        db.execSQL(CREATE_TABLE_MEAL_RECORD);
        db.execSQL(CREATE_TABLE_DEHORNED_RECORD);
        db.execSQL(CREATE_TABLE_MILK_REPLACER);
        db.execSQL(CREATE_TABLE_MONTHLY_REPORT);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINATION_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEAL_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEHORNED_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MILK_REPLACER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONTHLY_REPORT);
        
        // create new tables
        onCreate(db);
    }

    ////////////////////////////// COWS //////////////////////////////  
    
    /*
     * Creating a cow
     */
    public void createCow(Cow cow) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues cowValues = new ContentValues();
        cowValues.put(KEY_EAR_TAG_NO, cow.getEarTagNo());
        cowValues.put(KEY_GENDER, cow.getGender());
        cowValues.put(KEY_DATE_ARRIVED, cow.getDateArrived());
        cowValues.put(KEY_DATE_MOVED_OFF, cow.getDateMovedOffFarm());
        cowValues.put(KEY_MOVED_TO, cow.getMovedTo());
        
        ContentValues vacValues = new ContentValues();
        vacValues.put(KEY_EAR_TAG_NO, cow.getEarTagNo());
        vacValues.put(KEY_VACCINATION_DATE, cow.getDateVaccinated());
        vacValues.put(KEY_BOVIVAC_DATE, cow.getBovivacBoosterDate());
        vacValues.put(KEY_IBR_RSP_DATE, cow.getIBRRSPBoosterDate());
     
        db.insert(TABLE_VACCINATION_DETAILS, null, vacValues);
        db.insert(TABLE_COW, null, cowValues);
    }
    
    public void setCowDetails(Cow cow, Cursor c) {
    	cow.setEarTagNo(c.getString((c.getColumnIndex(KEY_EAR_TAG_NO))));
        cow.setGender((c.getString(c.getColumnIndex(KEY_GENDER))));
        cow.setDateArrived(c.getString(c.getColumnIndex(KEY_DATE_ARRIVED)));
        cow.setDateMovedOffFarm(c.getString(c.getColumnIndex(KEY_DATE_MOVED_OFF)));	// set before days on farm so days on farm can be properly calculated
        cow.setDaysOnFarm();
        cow.setPrice();
        cow.setDateVaccinated(c.getString(c.getColumnIndex(KEY_VACCINATION_DATE)));
        cow.setDateBovivacBooster(c.getString(c.getColumnIndex(KEY_BOVIVAC_DATE)));
        cow.setDateIBRRSPBooster(c.getString(c.getColumnIndex(KEY_IBR_RSP_DATE)));
        cow.setMovedTo(c.getString(c.getColumnIndex(KEY_MOVED_TO)));
    }
    
    /*
     * get single cow
     */
    public Cow getCow(String cow_id) {
        String selectQuery = "SELECT * FROM " + TABLE_COW + " INNER JOIN " + TABLE_VACCINATION_DETAILS 
        		+ " ON " + TABLE_COW + "." + KEY_EAR_TAG_NO + "="  + TABLE_VACCINATION_DETAILS + "." + KEY_EAR_TAG_NO + " WHERE "
                + TABLE_COW + "." + KEY_EAR_TAG_NO + " = '" + cow_id + "'";
   
        Log.e(LOG, selectQuery);    
        

        SQLiteDatabase db = this.getReadableDatabase();         
        Cursor c = db.rawQuery(selectQuery, null);
         
        if (c != null)
                c.moveToFirst();
         
        Cow cow = new Cow();
        setCowDetails(cow, c);
         
        c.close();
        return cow;
    }
    
    /*
     * get selected cows
     */
    public List<Cow> getCows(String partial_cow_id) {
    	List<Cow> cows = new ArrayList<Cow>();
     
        String selectQuery = "SELECT * FROM " + TABLE_COW + " INNER JOIN " + TABLE_VACCINATION_DETAILS 
        		+ " ON " + TABLE_COW + "." + KEY_EAR_TAG_NO + "="  + TABLE_VACCINATION_DETAILS + "." + KEY_EAR_TAG_NO + " WHERE "
                + TABLE_COW + "." + KEY_EAR_TAG_NO + " LIKE '%" + partial_cow_id + "%' ORDER BY " + TABLE_COW + "." + KEY_EAR_TAG_NO + " ASC";
     
        Log.e(LOG, selectQuery);
     

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
     // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Cow cow = new Cow();
                setCowDetails(cow, c);
     
                // adding to cow list
                cows.add(cow);
            } while (c.moveToNext());
        }
        
        c.close();
        return cows;
    }
    
    /*
     * getting all cows
     * */
    public List<Cow> getAllCows() {
        List<Cow> cows = new ArrayList<Cow>();
        String selectQuery = "SELECT * FROM " + TABLE_COW + " INNER JOIN " + TABLE_VACCINATION_DETAILS 
        		+ " ON " + TABLE_COW + "." + KEY_EAR_TAG_NO + "="  + TABLE_VACCINATION_DETAILS + "." + KEY_EAR_TAG_NO 
        		+ " ORDER BY " + KEY_EAR_TAG_NO + " ASC";
     
        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Cow cow = new Cow();
                setCowDetails(cow, c);
     
                // adding to cow list
                cows.add(cow);
            } while (c.moveToNext());
        }
     
        c.close();
        return cows;
    }
    
    public Cow getDateArrivedForCow(String cow_id) {
    	String getDateQuery = "SELECT " + KEY_DATE_ARRIVED + " FROM " + TABLE_COW + " WHERE " + KEY_EAR_TAG_NO + "='" + cow_id + "'";
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor c = db.rawQuery(getDateQuery, null);
    	
    	if(c != null)
    		c.moveToFirst();
    	
    	Cow cow = new Cow();
    	setCowDetails(cow, c);
    	
    	c.close();
    	return cow;
    }
    
    /*
     * edit cow
     */
    public void editCow(String cow_id, String vaccinationDate, String bovivacBoosterDate, String ibrRspBoosterDate, String dateMoved, String movedTo) {
    	String updateCowQuery = "UPDATE " + TABLE_COW + " SET " + KEY_DATE_MOVED_OFF + "=\"" + dateMoved + "\", " + KEY_MOVED_TO + "=\"" + movedTo + "\" WHERE " +
    			KEY_EAR_TAG_NO + "='" + cow_id + "'";
    	
    	
    	String updateVacQuery = "UPDATE " + TABLE_VACCINATION_DETAILS + " SET " + KEY_VACCINATION_DATE + "=\"" + vaccinationDate + "\", "
    			+ KEY_BOVIVAC_DATE + "=\"" + bovivacBoosterDate + "\", " + KEY_IBR_RSP_DATE + "=\"" + ibrRspBoosterDate 
    			+ "\" WHERE " + KEY_EAR_TAG_NO + "='" + cow_id + "'";
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	db.execSQL(updateCowQuery);
    	db.execSQL(updateVacQuery);
    }
    
    public void deleteCow(String cow_id) {
    	String deleteQuery = "DELETE FROM " + TABLE_COW + " WHERE " + KEY_EAR_TAG_NO + "='" + cow_id + "'";
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	db.execSQL(deleteQuery);
    }
    
    public void deleteAllCows() {
    	String deleteAllQuery = "DELETE FROM " + TABLE_COW;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	db.execSQL(deleteAllQuery);
    }
    
    ////////////////////////////// MEDICAL DETAILS //////////////////////////////  
    
    public void createMedicalRecord(MedicalRecord medRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues medValues = new ContentValues();
        medValues.put(KEY_EAR_TAG_NO, medRecord.getEarTagNo());
        medValues.put(KEY_MED_DETAILS, medRecord.getDetailsOfMedication());
        medValues.put(KEY_MED_BATCH_NO, medRecord.getBatchNo());
        medValues.put(KEY_MED_DATE_OF_USE, medRecord.getDateOfUse());
        medValues.put(KEY_MED_QUANTITY_USED, medRecord.getQuantityUsed());
        medValues.put(KEY_MED_DOCTOR, medRecord.getDoctor());
        medValues.put(KEY_MED_COMMENTS, medRecord.getComments());
        
        db.insert(TABLE_MEDICAL, null, medValues);
    }
    
    public void setMedicalRecordDetails(MedicalRecord medRecord, Cursor c) {
    	medRecord.setEarTagNo(c.getString((c.getColumnIndex(KEY_EAR_TAG_NO))));
    	medRecord.setDetailsOfMedication((c.getString(c.getColumnIndex(KEY_MED_DETAILS))));
    	medRecord.setBatchNo(c.getString(c.getColumnIndex(KEY_MED_BATCH_NO)));
    	medRecord.setDateOfUse(c.getString(c.getColumnIndex(KEY_MED_DATE_OF_USE)));
    	medRecord.setQuantityUsed(c.getInt(c.getColumnIndex(KEY_MED_QUANTITY_USED)));
    	medRecord.setDoctor(c.getString(c.getColumnIndex(KEY_MED_DOCTOR)));
    	medRecord.setComments(c.getString(c.getColumnIndex(KEY_MED_COMMENTS)));
    }
    
    /*
     * get medical record
     */
    public MedicalRecord getMedicalRecord(String cow_id) {
        String selectQuery = "SELECT * FROM " + TABLE_MEDICAL + " WHERE " + KEY_EAR_TAG_NO + " = '" + cow_id + "'";
   
        Log.e(LOG, selectQuery);    
        

        SQLiteDatabase db = this.getReadableDatabase();         
        Cursor c = db.rawQuery(selectQuery, null);
         
        if (c != null)
                c.moveToFirst();
         
        MedicalRecord medRecord = new MedicalRecord();
        setMedicalRecordDetails(medRecord, c);
         
        c.close();
        return medRecord;
    }
    
    /*
     * get selected medical records
     */
    public List<MedicalRecord> getMedicalRecords(String partial_cow_id) {
    	List<MedicalRecord> medRecords = new ArrayList<MedicalRecord>();
     
        String selectQuery = "SELECT * FROM " + TABLE_MEDICAL + " WHERE " + KEY_EAR_TAG_NO + " LIKE '%" + partial_cow_id + "%'  ORDER BY DATE(\"" + KEY_MED_DATE_OF_USE + "\") DESC";
     
        Log.e(LOG, selectQuery);
     

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MedicalRecord medRecord = new MedicalRecord();
                setMedicalRecordDetails(medRecord, c);
     
                // adding to list
                medRecords.add(medRecord);
            } while (c.moveToNext());
        }
        
        c.close();
        return medRecords;
    }
    
    /*
     * getting all medical records
     * */
    public List<MedicalRecord> getAllMedicalRecords() {
        List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
        String selectQuery = "SELECT * FROM " + TABLE_MEDICAL + " ORDER BY DATE(\"" + KEY_MED_DATE_OF_USE + "\") DESC";
     
        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
            	MedicalRecord medRecord = new MedicalRecord();
                setMedicalRecordDetails(medRecord, c);
     
                // adding to list
                medicalRecords.add(medRecord);
            } while (c.moveToNext());
        }
     
        c.close();
        return medicalRecords;
    }
    
    public void deleteMedicalRecord(String cow_id, String batchNo, String dateOfUse) {
    	String deleteQuery = "DELETE FROM " + TABLE_MEDICAL + " WHERE " + KEY_EAR_TAG_NO + "='" + cow_id
    						+ "' AND " + KEY_MED_BATCH_NO + "='" + batchNo + "' AND " + KEY_MED_DATE_OF_USE + "='" + dateOfUse + "'";
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	db.execSQL(deleteQuery);
    }
    
    public void deleteAllMedicalRecords() {
    	String deleteAllQuery = "DELETE FROM " + TABLE_MEDICAL;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	db.execSQL(deleteAllQuery);
    }
    
   
    
    ////////////////////////////// DEHORNING EVENTS ////////////////////////////// 
    
    public void createDehorningEvent(DehorningEvent dehornEvent) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues dehornedValues = new ContentValues();
        dehornedValues.put(KEY_DEHORNED_DATE, dehornEvent.getDateDehorned());
        dehornedValues.put(KEY_DEHORNED_GROUP_REF, dehornEvent.getGroupRef());
        dehornedValues.put(KEY_DEHORNED_NUMBER, dehornEvent.getNoDehorned());
        dehornedValues.put(KEY_DEHORNED_D_CAINE, dehornEvent.getDCaine());
        
        db.insert(TABLE_DEHORNED_RECORD, null, dehornedValues);
    }
    
    public void setDehorningEventDetails(DehorningEvent dehornEvent, Cursor c) {
    	dehornEvent.setDateDehorned(c.getString((c.getColumnIndex(KEY_DEHORNED_DATE))));
    	dehornEvent.setGroupRef(c.getString((c.getColumnIndex(KEY_DEHORNED_GROUP_REF))));
    	dehornEvent.setNoDehorned((c.getInt(c.getColumnIndex(KEY_DEHORNED_NUMBER))));
    	dehornEvent.setDCaine(c.getString((c.getColumnIndex(KEY_DEHORNED_D_CAINE))));
    }
    
    /*
     * getting all dehorning events
     * */
    public List<DehorningEvent> getAllDehorningEvents() {
        List<DehorningEvent> dehorningEvents = new ArrayList<DehorningEvent>();
        String selectQuery = "SELECT * FROM " + TABLE_DEHORNED_RECORD + " ORDER BY DATE(\"" + KEY_DEHORNED_DATE + "\") DESC";
     
        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
            	DehorningEvent dehornEvent = new DehorningEvent();
            	setDehorningEventDetails(dehornEvent, c);
     
                // adding to list
            	dehorningEvents.add(dehornEvent);
            } while (c.moveToNext());
        }
     
        c.close();
        return dehorningEvents;
    }
    
    public void deleteDehorningEvent(String dateDehorned, String groupRef) {
    	String deleteQuery = "DELETE FROM " + TABLE_DEHORNED_RECORD + " WHERE " + KEY_DEHORNED_DATE + "='" + dateDehorned + "' AND " + KEY_DEHORNED_GROUP_REF + "='" + groupRef + "'";
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	db.execSQL(deleteQuery);
    }
    
    public void deleteAllDehorningEvents() {
    	String deleteAllQuery = "DELETE FROM " + TABLE_DEHORNED_RECORD;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	db.execSQL(deleteAllQuery);
    }
    
    ////////////////////////////// MEAL RECORD ////////////////////////////// 
    
    public void createMealRecord(MealRecord mealRecord) {
    	SQLiteDatabase db = this.getWritableDatabase();

    	ContentValues mealValues = new ContentValues();
    	mealValues.put(KEY_MEAL_PURCHASE_DATE, mealRecord.getPurchaseDate());
    	mealValues.put(KEY_MEAL_FOOD_NAME, mealRecord.getFoodName());
    	mealValues.put(KEY_MEAL_SUPPLIER, mealRecord.getSupplier());
    	mealValues.put(KEY_MEAL_INVOICE_NO, mealRecord.getInvoiceNo());
    	mealValues.put(KEY_MEAL_QUANTITY, mealRecord.getQuantity());
    	mealValues.put(KEY_MEAL_GROUPS_FED, mealRecord.getGroupsFed());

    	db.insert(TABLE_MEAL_RECORD, null, mealValues);
    }

    public void setMealRecordDetails(MealRecord mealRecord, Cursor c) {
    	mealRecord.setPurchaseDate(c.getString((c.getColumnIndex(KEY_MEAL_PURCHASE_DATE))));
    	mealRecord.setFoodName((c.getString(c.getColumnIndex(KEY_MEAL_FOOD_NAME))));
    	mealRecord.setSupplier((c.getString(c.getColumnIndex(KEY_MEAL_SUPPLIER))));
    	mealRecord.setInvoiceNo((c.getString(c.getColumnIndex(KEY_MEAL_INVOICE_NO))));
    	mealRecord.setQuantity((c.getInt(c.getColumnIndex(KEY_MEAL_QUANTITY))));
    	mealRecord.setGroupsFed((c.getString(c.getColumnIndex(KEY_MEAL_GROUPS_FED))));
    }





    /*
     * getting all meal records
	* */
    public List<MealRecord> getAllMealRecords() {
    	List<MealRecord> mealRecords = new ArrayList<MealRecord>();
    	String selectQuery = "SELECT * FROM " + TABLE_MEAL_RECORD + " ORDER BY DATE(\"" + KEY_MEAL_PURCHASE_DATE + "\") DESC";
    	
    	Log.e(LOG, selectQuery);
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor c = db.rawQuery(selectQuery, null);
    	
    	// 	looping through all rows and adding to list
    	if (c.moveToFirst()) {
    		do {
    			MealRecord mealRecord = new MealRecord();
    			setMealRecordDetails(mealRecord, c);
    			
    			// 	adding to list
    			mealRecords.add(mealRecord);
    		} while (c.moveToNext());
    	}
    	
    	c.close();
    	return mealRecords;
    }

    public void deleteMealRecord(String purchaseDate, String foodName, String groupsFed) {
    	String deleteQuery = "DELETE FROM " + TABLE_MEAL_RECORD + " WHERE " + KEY_MEAL_PURCHASE_DATE + "='" + purchaseDate
    						+ "' AND " + KEY_MEAL_FOOD_NAME + "='" + foodName + "' AND " + KEY_MEAL_GROUPS_FED + "='" + groupsFed + "'";
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	db.execSQL(deleteQuery);
    }
    
    public void deleteAllMealRecords() {
    	String deleteAllQuery = "DELETE FROM " + TABLE_MEAL_RECORD;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	db.execSQL(deleteAllQuery);
    }
    
    
    ////////////////////////////// MILK REPLACER ////////////////////////////// 
    
    public void createMilkReplacerRecord(MilkRecord milkRecord) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues milkValues = new ContentValues();
    	milkValues.put(KEY_MILK_PURCHASE_DATE, milkRecord.getPurchaseDate());
    	milkValues.put(KEY_MILK_NAME, milkRecord.getMilkName());
    	milkValues.put(KEY_MILK_MANUFACTURER, milkRecord.getManufacturer());
    	milkValues.put(KEY_MILK_SUPPLIER, milkRecord.getSupplier());
    	milkValues.put(KEY_MILK_INVOICE_NO, milkRecord.getInvoiceNo());
    	milkValues.put(KEY_MILK_QUANTITY, milkRecord.getQuantity());
    	
    	db.insert(TABLE_MILK_REPLACER, null, milkValues);
    }

    public void setMilkReplacerRecordDetails(MilkRecord milkRecord, Cursor c) {
    	milkRecord.setPurchaseDate(c.getString((c.getColumnIndex(KEY_MILK_PURCHASE_DATE))));
    	milkRecord.setMilkName((c.getString(c.getColumnIndex(KEY_MILK_NAME))));
    	milkRecord.setManufacturer((c.getString(c.getColumnIndex(KEY_MILK_MANUFACTURER))));
    	milkRecord.setSupplier((c.getString(c.getColumnIndex(KEY_MILK_SUPPLIER))));
    	milkRecord.setInvoiceNo((c.getString(c.getColumnIndex(KEY_MILK_INVOICE_NO))));
    	milkRecord.setQuantity((c.getInt(c.getColumnIndex(KEY_MILK_QUANTITY))));
    }


    /*
     * getting all milk replacer records
     * */
    public List<MilkRecord> getAllMilkReplacerRecords() {
    	List<MilkRecord> milkRecords = new ArrayList<MilkRecord>();
    	String selectQuery = "SELECT * FROM " + TABLE_MILK_REPLACER + " ORDER BY DATE(\"" + KEY_MILK_PURCHASE_DATE + "\") DESC";
    	
    	Log.e(LOG, selectQuery);
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor c = db.rawQuery(selectQuery, null);
    	
    	// 	looping through all rows and adding to list
    	if (c.moveToFirst()) {
    		do {
    			MilkRecord milkRecord = new MilkRecord();
    			setMilkReplacerRecordDetails(milkRecord, c);

    			// 	adding to list
    			milkRecords.add(milkRecord);
    		} while (c.moveToNext());
    	}

    	c.close();
    	return milkRecords;
    }
    
    public void deleteMilkReplacerRecord(String purchaseDate, String milkName, String invoiceNo) {
    	String deleteQuery = "DELETE FROM " + TABLE_MILK_REPLACER + " WHERE " + KEY_MILK_PURCHASE_DATE + "='" + purchaseDate
    			+ "' AND " + KEY_MILK_NAME + "='" + milkName + "' AND " + KEY_MILK_INVOICE_NO + "='" + invoiceNo + "'";

    	SQLiteDatabase db = this.getReadableDatabase();
    	db.execSQL(deleteQuery);
    }

    public void deleteAllMilkReplacerRecords() {
    	String deleteAllQuery = "DELETE FROM " + TABLE_MILK_REPLACER;

    	SQLiteDatabase db = this.getReadableDatabase();
    	db.execSQL(deleteAllQuery);
    }
    
    
    
    ////////////////////////////// MONTHLY REPORT ////////////////////////////// 
    
    public void createMonthlyReport(MonthlyReport monthlyReport) {
    	SQLiteDatabase db = this.getWritableDatabase();

    	ContentValues monthlyValues = new ContentValues();
    	monthlyValues.put(KEY_MONTHLY_DATE, monthlyReport.getDate());
    	monthlyValues.put(KEY_MONTHLY_TOTAL_CALVES, monthlyReport.getTotalCalves());
    	monthlyValues.put(KEY_MONTHLY_CHEAP_CALVES, monthlyReport.getCheapCalves());
    	monthlyValues.put(KEY_MONTHLY_EXPENSIVE_CALVES, monthlyReport.getExpensiveCalves());
    	monthlyValues.put(KEY_MONTHLY_NO_DEHORNED, monthlyReport.getNoDehorned());
    	monthlyValues.put(KEY_MONTHLY_NO_MOVED, monthlyReport.getNoMoved());
    	monthlyValues.put(KEY_MONTHLY_NO_DIED, monthlyReport.getNoDied());
    	monthlyValues.put(KEY_MONTHLY_TOTAL_INTAKE, monthlyReport.getTotalIntake());

    	db.insert(TABLE_MONTHLY_REPORT, null, monthlyValues);
    }
    
    public void setMonthlyReportDetails(MonthlyReport monthlyReport, Cursor c) {
    	monthlyReport.setDate(c.getString((c.getColumnIndex(KEY_MONTHLY_DATE))));
    	monthlyReport.setTotalCalves(c.getInt((c.getColumnIndex(KEY_MONTHLY_TOTAL_CALVES))));
    	monthlyReport.setCheapCalves(c.getInt((c.getColumnIndex(KEY_MONTHLY_CHEAP_CALVES))));
    	monthlyReport.setExpensiveCalves(c.getInt((c.getColumnIndex(KEY_MONTHLY_EXPENSIVE_CALVES))));
    	monthlyReport.setNoDehorned(c.getInt((c.getColumnIndex(KEY_MONTHLY_NO_DEHORNED))));
    	monthlyReport.setNoMoved(c.getInt((c.getColumnIndex(KEY_MONTHLY_NO_MOVED))));
    	monthlyReport.setNoDied(c.getInt((c.getColumnIndex(KEY_MONTHLY_NO_DIED))));
    	monthlyReport.setTotalIntake(c.getDouble((c.getColumnIndex(KEY_MONTHLY_TOTAL_INTAKE))));
    }
    
    
    /*
     * 	getting all monthly reports
	* */
    public List<MonthlyReport> getAllMonthlyReports() {
    	List<MonthlyReport> monthlyReports = new ArrayList<MonthlyReport>();
    	String selectQuery = "SELECT * FROM " + TABLE_MONTHLY_REPORT + " ORDER BY DATE(\"" + KEY_MONTHLY_DATE + "\") ASC";
    	
    	Log.e(LOG, selectQuery);
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor c = db.rawQuery(selectQuery, null);
    	
    	//	looping through all rows and adding to list
    	if (c.moveToFirst()) {
    		do {
    			MonthlyReport monthlyReport = new MonthlyReport();
    			setMonthlyReportDetails(monthlyReport, c);
    			
    			// 	adding to list
    			monthlyReports.add(monthlyReport);
    		} while (c.moveToNext());
    	}
    	
    	c.close();
    	return monthlyReports;
    }
    
    public void deleteAllMonthlyReports() {
    	String deleteAllQuery = "DELETE FROM " + TABLE_MONTHLY_REPORT;

    	SQLiteDatabase db = this.getReadableDatabase();
    	db.execSQL(deleteAllQuery);
    }
    
    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
