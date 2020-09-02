package com.stepin.coronaapp.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = DBConfig.DATABASE_NAME;

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    //    Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static DatabaseHelper getInstance(Context context) {
        if(databaseHelper==null){
            synchronized (DatabaseHelper.class) {
                if(databaseHelper==null)
                    databaseHelper = new DatabaseHelper(context);
            }
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_CHECK_TABLE = "CREATE TABLE " + DBConfig.TABLE_CHECKIN + "("
                + DBConfig.COL_CHECK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConfig.COL_CHECK_PEOPLE + " TEXT , "
                + DBConfig.COL_CHECK_TIME + " TEXT , "
                + DBConfig.COL_CHECK_UTIL + " TEXT , "
                + DBConfig.COL_CHECK_ADDITION + " TEXT , "
                + DBConfig.COL_CHECK_CITY + " TEXT , "
                + DBConfig.COL_CHECK_STATE + " TEXT ,"
                + DBConfig.COL_CHECK_ADDRESS + " TEXT , "
                + DBConfig.COL_CHECK_COUNTRY + " TEXT ,"
                + DBConfig.COL_CHECK_LAT + " TEXT ,"
                + DBConfig.COL_CHECK_LON + " TEXT ,"
                + DBConfig.COL_CHECK_CREATED + " TEXT ,"
                + DBConfig.COL_CHECK_USERID + " TEXT "
                + ")";
        db.execSQL(CREATE_CHECK_TABLE);

        String CREATE_OFFICER_TABLE = "CREATE TABLE " + DBConfig.TABLE_OFFICER + "("
                + DBConfig.COL_OFFICER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConfig.COL_OFFICER_USERID + " TEXT ,"
                + DBConfig.COL_OFFICER_NAME + " TEXT , "
                + DBConfig.COL_OFFICER_TITLE + " TEXT , "
                + DBConfig.COL_OFFICER_CARDNUM + " TEXT ,"
                + DBConfig.COL_OFFICER_SERVICETYPE + " TEXT ,"
                + DBConfig.COL_OFFICER_TELNUM + " TEXT , "
                + DBConfig.COL_OFFICER_ADDRESS + " TEXT , "
                + DBConfig.COL_OFFICER_REGNUM + " TEXT ,"
                + DBConfig.COL_OFFICER_DESTINATION + " TEXT ,"
                + DBConfig.COL_OFFICER_DATE + " TEXT , "
                + DBConfig.COL_OFFICER_CREATED + " TEXT "
                + ")";

        db.execSQL(CREATE_OFFICER_TABLE);

        String CREATE_PASSENGER_TABLE = "CREATE TABLE " + DBConfig.TABLE_PASSENGER + "("
                + DBConfig.COL_PASSENGER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConfig.COL_PASSENGER_USERID + " TEXT , "
                + DBConfig.COL_PASSENGER_TEMP + "  TEXT , "
                + DBConfig.COL_PASSENGER_NAME + " TEXT , "
                + DBConfig.COL_PASSENGER_TITLE + " TEXT ,"
                + DBConfig.COL_PASSENGER_VNUM + " TEXT ,"
                + DBConfig.COL_PASSENGER_TELNUM + " TEXT , "
                + DBConfig.COL_PASSENGER_SEATNUM + " TEXT , "
                + DBConfig.COL_PASSENGER_PUBLISHDATE + " TEXT ,"
                + DBConfig.COL_PASSENGER_FVILLAGE + " TEXT ,"
                + DBConfig.COL_PASSENGER_TVILLAGE + " TEXT , "
                + DBConfig.COL_PASSENGER_CONTACT + " TEXT , "
                + DBConfig.COL_PASSENGER_CONTACTNUM + " TEXT , "
                + DBConfig.COL_PASSENGER_LOCATION + " TEXT , "
                + DBConfig.COL_PASSENGER_INFECT + " TEXT , "
                + DBConfig.COL_PASSENGER_HISTORY + " TEXT , "
                + DBConfig.COL_PASSENGER_IDNUM + " TEXT , "
                + DBConfig.COL_PASSENGER_CREATED + " TEXT "
                + ")";


        db.execSQL(CREATE_PASSENGER_TABLE);

        String CREATE_VISIT_TABLE = "CREATE TABLE " + DBConfig.TABLE_VISIT + "("
                + DBConfig.COL_VISIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConfig.COL_VISIT_USERID + " TEXT , "
                + DBConfig.COL_VISIT_TITLE + " TEXT , "
                + DBConfig.COL_VISIT_NAME + " TEXT , "
                + DBConfig.COL_VISIT_AGE + " TEXT , "
                + DBConfig.COL_VISIT_IDNUM + " TEXT , "
                + DBConfig.COL_VISIT_NHIF + " TEXT , "
                + DBConfig.COL_VISIT_VILLAGE + " TEXT , "
                + DBConfig.COL_VISIT_NEARNAME + " TEXT , "
                + DBConfig.COL_VISIT_MASK + " TEXT , "
                + DBConfig.COL_VISIT_WARD + " TEXT , "
                + DBConfig.COL_VISIT_PROVIDE + " TEXT , "
                + DBConfig.COL_VISIT_MAL + " TEXT , "
                + DBConfig.COL_VISIT_DIABET + " TEXT , "
                + DBConfig.COL_VISIT_HYPER + " TEXT , "
                + DBConfig.COL_VISIT_TB + " TEXT , "
                + DBConfig.COL_VISIT_INDICATE + " TEXT , "
                + DBConfig.COL_VISIT_REMARK + " TEXT , "
                + DBConfig.COL_VISIT_CREATED + " TEXT "
                + ")";

        db.execSQL(CREATE_VISIT_TABLE);


        String CREATE_MOTHER_TABLE = "CREATE TABLE " + DBConfig.TABLE_MOTHER + "("
                + DBConfig.COL_MOTHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConfig.COL_MOTHER_USERID + " TEXT , "
                + DBConfig.COL_MOTHER_TITLE + " TEXT , "
                + DBConfig.COL_MOTHER_NAME + " TEXT , "
                + DBConfig.COL_MOTHER_NHIF + " TEXT , "
                + DBConfig.COL_MOTHER_AGE + " TEXT , "
                + DBConfig.COL_MOTHER_MOTHERID + " TEXT , "
                + DBConfig.COL_MOTHER_VILLAGE + " TEXT , "
                + DBConfig.COL_MOTHER_WARD + " TEXT , "
                + DBConfig.COL_MOTHER_TELNUM + " TEXT , "
                + DBConfig.COL_MOTHER_CLINIC + " TEXT , "
                + DBConfig.COL_MOTHER_DUEDATE + " TEXT , "
                + DBConfig.COL_MOTHER_FOLIC + " TEXT , "
                + DBConfig.COL_MOTHER_MARY + " TEXT , "
                + DBConfig.COL_MOTHER_CHILDREN + " TEXT , "
                + DBConfig.COL_MOTHER_REMARK + " TEXT , "
                + DBConfig.COL_MOTHER_CREATED + " TEXT "
                + ")";

        db.execSQL(CREATE_MOTHER_TABLE);

        String CREATE_GBV_TABLE = "CREATE TABLE " + DBConfig.TABLE_GBV + "("
                + DBConfig.COL_GBV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConfig.COL_GBV_USERID + " TEXT , "
                + DBConfig.COL_GBV_COUNTY + " TEXT , "
                + DBConfig.COL_GBV_TITLE + " TEXT , "
                + DBConfig.COL_GBV_NATURE + " TEXT , "
                + DBConfig.COL_GBV_GENDER + " TEXT , "
                + DBConfig.COL_GBV_AGE + " TEXT , "
                + DBConfig.COL_GBV_REPORTDATE + " TEXT , "
                + DBConfig.COL_GBV_STATUS + " TEXT , "
                + DBConfig.COL_GBV_REPORTPLACE + " TEXT , "
                + DBConfig.COL_GBV_REMARK + " TEXT , "
                + DBConfig.COL_GBV_CREATED + " TEXT "
                + ")";

        db.execSQL(CREATE_GBV_TABLE);

        String CREATE_ENFORCE_TABLE = "CREATE TABLE " + DBConfig.TABLE_ENFORCE + "("
                + DBConfig.COL_ENFORCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConfig.COL_ENFORCE_USERID + " TEXT , "
                + DBConfig.COL_ENFORCE_TYPE + " TEXT , "
                + DBConfig.COL_ENFORCE_TITLE + " TEXT , "
                + DBConfig.COL_ENFORCE_DESCRIPTION + " TEXT , "
                + DBConfig.COL_ENFORCE_CREATED + " TEXT "
                + ")";

        db.execSQL(CREATE_ENFORCE_TABLE);

        String CREATE_REPORT_TABLE = "CREATE TABLE " + DBConfig.TABLE_REPORT + "("
                + DBConfig.COL_REPORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConfig.COL_REPORT_USERID + " TEXT , "
                + DBConfig.COL_REPORT_TYPE + " TEXT , "
                + DBConfig.COL_REPORT_SYMPTOM + " TEXT , "
                + DBConfig.COL_REPORT_LAN + " TEXT , "
                + DBConfig.COL_REPORT_LON + " TEXT , "
                + DBConfig.COL_REPORT_CITY + " TEXT , "
                + DBConfig.COL_REPORT_STATE + " TEXT , "
                + DBConfig.COL_REPORT_ADDRESS + " TEXT , "
                + DBConfig.COL_REPORT_COUNTRY + " TEXT , "
                + DBConfig.COL_REPORT_ADDITION + " TEXT , "
                + DBConfig.COL_REPORT_CREATED + " TEXT "
                + ")";

        db.execSQL(CREATE_REPORT_TABLE);

      }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DBConfig.TABLE_CHECKIN);
        db.execSQL("DROP TABLE IF EXISTS " + DBConfig.TABLE_VISIT);
        db.execSQL("DROP TABLE IF EXISTS " + DBConfig.TABLE_GBV);
        db.execSQL("DROP TABLE IF EXISTS " + DBConfig.TABLE_PASSENGER);
        db.execSQL("DROP TABLE IF EXISTS " + DBConfig.TABLE_MOTHER);
        db.execSQL("DROP TABLE IF EXISTS " + DBConfig.TABLE_OFFICER);
        db.execSQL("DROP TABLE IF EXISTS " + DBConfig.TABLE_REPORT);
        db.execSQL("DROP TABLE IF EXISTS " + DBConfig.TABLE_ENFORCE);
        // Create tables again
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

}
