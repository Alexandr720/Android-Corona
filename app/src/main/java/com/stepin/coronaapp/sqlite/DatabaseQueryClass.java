package com.stepin.coronaapp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.widget.Toast;

import com.stepin.coronaapp.activities.GbvActivity;
import com.stepin.coronaapp.model.CheckData;
import com.stepin.coronaapp.model.ChvMotherLocal;
import com.stepin.coronaapp.model.ChvVisitLocal;
import com.stepin.coronaapp.model.EnforceLocal;
import com.stepin.coronaapp.model.GbvLocal;
import com.stepin.coronaapp.model.ReportLocal;
import com.stepin.coronaapp.model.TracingOfficer;
import com.stepin.coronaapp.model.TracingPassenger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseQueryClass {
    private Context context;

    public DatabaseQueryClass(Context context){
        this.context = context;
   }
    public long insertCheckData(CheckData tmpfileData){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConfig.COL_CHECK_PEOPLE, tmpfileData.getPeople());
        contentValues.put(DBConfig.COL_CHECK_TIME, tmpfileData.getTime());
        contentValues.put(DBConfig.COL_CHECK_UTIL, tmpfileData.getUtil());
        contentValues.put(DBConfig.COL_CHECK_ADDITION, tmpfileData.getAddition());
        contentValues.put(DBConfig.COL_CHECK_CITY, tmpfileData.getCity());
        contentValues.put(DBConfig.COL_CHECK_STATE, tmpfileData.getState());
        contentValues.put(DBConfig.COL_CHECK_ADDRESS, tmpfileData.getAddress());
        contentValues.put(DBConfig.COL_CHECK_COUNTRY, tmpfileData.getCountry());
        contentValues.put(DBConfig.COL_CHECK_USERID, tmpfileData.getUser_id());
        contentValues.put(DBConfig.COL_CHECK_LON, tmpfileData.getLon());
        contentValues.put(DBConfig.COL_CHECK_LAT, tmpfileData.getLan());
        contentValues.put(DBConfig.COL_CHECK_CREATED, tmpfileData.getCreated_time());



        try {
            id = sqLiteDatabase.insertOrThrow(DBConfig.TABLE_CHECKIN, null, contentValues);
        } catch (SQLiteException e){

        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }
    public List<CheckData> getCheckData(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(DBConfig.TABLE_CHECKIN, null, null, null, null, null, null, null);
            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<CheckData> fileList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(DBConfig.COL_CHECK_ID));
                        String people = cursor.getString(cursor.getColumnIndex(DBConfig.COL_CHECK_PEOPLE));
                        String time = cursor.getString(cursor.getColumnIndex(DBConfig.COL_CHECK_TIME));
                        String util = cursor.getString(cursor.getColumnIndex(DBConfig.COL_CHECK_UTIL));
                        String addition = cursor.getString(cursor.getColumnIndex(DBConfig.COL_CHECK_ADDITION));
                        String city = cursor.getString(cursor.getColumnIndex(DBConfig.COL_CHECK_CITY));
                        String state = cursor.getString(cursor.getColumnIndex(DBConfig.COL_CHECK_STATE));
                        String address = cursor.getString(cursor.getColumnIndex(DBConfig.COL_CHECK_ADDRESS));
                        String country = cursor.getString(cursor.getColumnIndex(DBConfig.COL_CHECK_COUNTRY));
                        String user_id = cursor.getString(cursor.getColumnIndex(DBConfig.COL_CHECK_USERID));
                        String lat = cursor.getString(cursor.getColumnIndex(DBConfig.COL_CHECK_LAT));
                        String lon = cursor.getString(cursor.getColumnIndex(DBConfig.COL_CHECK_LON));
                        String created_time = cursor.getString(cursor.getColumnIndex(DBConfig.COL_CHECK_CREATED));
                        fileList.add(new CheckData(id, people, time, util, addition, city, state, address, country, user_id, lat, lon,created_time));
                    }   while (cursor.moveToNext());

                    return fileList;
                }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public boolean deleteCheckTable(int id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.delete(DBConfig.TABLE_CHECKIN,DBConfig.COL_CHECK_ID + "=" + "'"+id+"'",null)>0;
    }

    public long insertOfficerData(TracingOfficer tmpfileData){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConfig.COL_OFFICER_USERID, tmpfileData.getUser_id_local());
        contentValues.put(DBConfig.COL_OFFICER_NAME, tmpfileData.getLocal_name());
        contentValues.put(DBConfig.COL_OFFICER_TITLE, tmpfileData.getLocal_title());
        contentValues.put(DBConfig.COL_OFFICER_CARDNUM, tmpfileData.getLocal_card_num());
        contentValues.put(DBConfig.COL_OFFICER_SERVICETYPE, tmpfileData.getService_type());
        contentValues.put(DBConfig.COL_OFFICER_TELNUM, tmpfileData.getLocal_tel_num());
        contentValues.put(DBConfig.COL_OFFICER_ADDRESS, tmpfileData.getLocal_home_address());
        contentValues.put(DBConfig.COL_OFFICER_REGNUM, tmpfileData.getLocal_reg_num());
        contentValues.put(DBConfig.COL_OFFICER_DESTINATION, tmpfileData.getLocal_destination());
        contentValues.put(DBConfig.COL_OFFICER_DATE, tmpfileData.getLocal_date());
        contentValues.put(DBConfig.COL_OFFICER_CREATED, tmpfileData.getCreated_time());

        try {
            id = sqLiteDatabase.insertOrThrow(DBConfig.TABLE_OFFICER, null, contentValues);
        } catch (SQLiteException e){

        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }
    public List<TracingOfficer> getOfficerData(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(DBConfig.TABLE_OFFICER, null, null, null, null, null, null, null);
            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<TracingOfficer> fileList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(DBConfig.COL_OFFICER_ID));
                        String created_time =  cursor.getString(cursor.getColumnIndex(DBConfig.COL_OFFICER_CREATED));
                        String user_id_local = cursor.getString(cursor.getColumnIndex(DBConfig.COL_OFFICER_USERID));
                        String local_name = cursor.getString(cursor.getColumnIndex(DBConfig.COL_OFFICER_NAME));
                        String local_title = cursor.getString(cursor.getColumnIndex(DBConfig.COL_OFFICER_TITLE));
                        String local_card_num = cursor.getString(cursor.getColumnIndex(DBConfig.COL_OFFICER_CARDNUM));
                        String service_type = cursor.getString(cursor.getColumnIndex(DBConfig.COL_OFFICER_SERVICETYPE));
                        String local_tel_num = cursor.getString(cursor.getColumnIndex(DBConfig.COL_OFFICER_TELNUM));
                        String local_home_address = cursor.getString(cursor.getColumnIndex(DBConfig.COL_OFFICER_ADDRESS));
                        String local_reg_num = cursor.getString(cursor.getColumnIndex(DBConfig.COL_OFFICER_REGNUM));
                        String local_destination = cursor.getString(cursor.getColumnIndex(DBConfig.COL_OFFICER_DESTINATION));
                        String local_date =  cursor.getString(cursor.getColumnIndex(DBConfig.COL_OFFICER_DATE));

                        fileList.add(new TracingOfficer(id, user_id_local, local_name, local_title, local_card_num, service_type, local_tel_num, local_home_address, local_reg_num, local_destination, local_date,created_time));
                    }   while (cursor.moveToNext());

                    return fileList;
                }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public boolean deleteOfficerTable(int id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.delete(DBConfig.TABLE_OFFICER,DBConfig.COL_OFFICER_ID + "=" + "'"+id+"'",null)>0;
    }
    public long insertPassengerData(TracingPassenger tmpfileData){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConfig.COL_PASSENGER_USERID, tmpfileData.getUser_id());
        contentValues.put(DBConfig.COL_PASSENGER_TEMP, tmpfileData.getTemp());
        contentValues.put(DBConfig.COL_PASSENGER_NAME, tmpfileData.getPassenger_name());
        contentValues.put(DBConfig.COL_PASSENGER_TITLE, tmpfileData.getTracing_title());
        contentValues.put(DBConfig.COL_PASSENGER_VNUM, tmpfileData.getVehicle_num());
        contentValues.put(DBConfig.COL_PASSENGER_TELNUM, tmpfileData.getTel_number());
        contentValues.put(DBConfig.COL_PASSENGER_SEATNUM, tmpfileData.getSeat_num());
        contentValues.put(DBConfig.COL_PASSENGER_PUBLISHDATE, tmpfileData.getPublish_date());
        contentValues.put(DBConfig.COL_PASSENGER_FVILLAGE, tmpfileData.getFrom_village());
        contentValues.put(DBConfig.COL_PASSENGER_TVILLAGE, tmpfileData.getTo_village());
        contentValues.put(DBConfig.COL_PASSENGER_CONTACT, tmpfileData.getContact());
        contentValues.put(DBConfig.COL_PASSENGER_CONTACTNUM, tmpfileData.getContact_num());
        contentValues.put(DBConfig.COL_PASSENGER_LOCATION, tmpfileData.getLocation());
        contentValues.put(DBConfig.COL_PASSENGER_INFECT, tmpfileData.getInfect_str());
        contentValues.put(DBConfig.COL_PASSENGER_HISTORY, tmpfileData.getHistory_last());
        contentValues.put(DBConfig.COL_PASSENGER_IDNUM, tmpfileData.getId_num());
        contentValues.put(DBConfig.COL_PASSENGER_CREATED, tmpfileData.getCreated());


        try {
            id = sqLiteDatabase.insertOrThrow(DBConfig.TABLE_PASSENGER, null, contentValues);
        } catch (SQLiteException e){

        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }
    public List<TracingPassenger> getPassengerData(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(DBConfig.TABLE_PASSENGER, null, null, null, null, null, null, null);
            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<TracingPassenger> fileList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(DBConfig.COL_PASSENGER_ID));
                        String created_time =  cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_CREATED));
                        String luser_id = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_USERID));
                        String lid_num = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_IDNUM));
                        String lname = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_NAME));
                        String ltemp = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_TEMP));
                        String lvehicle_num = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_VNUM));
                        String lphone_num = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_TELNUM));
                        String lseat_num = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_SEATNUM));
                        String lfrom_village = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_FVILLAGE));
                        String lto_village = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_TVILLAGE));
                        String ltitle = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_TITLE));
                        String llocation = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_LOCATION));
                        String lcontact = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_CONTACT));
                        String lcontact_num = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_CONTACTNUM));
                        String lhistory_last = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_HISTORY));
                        String linfect_str = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_INFECT));
                        String ldate = cursor.getString(cursor.getColumnIndex(DBConfig.COL_PASSENGER_PUBLISHDATE));
                        fileList.add(new TracingPassenger(id, luser_id,ltemp, lname, ltitle, lvehicle_num, lphone_num, lseat_num, ldate, lfrom_village,lto_village,lcontact,lcontact_num, llocation,linfect_str,lhistory_last,lid_num,created_time));
                    }   while (cursor.moveToNext());

                    return fileList;
                }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public boolean deletePassengerTable(int id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.delete(DBConfig.TABLE_PASSENGER,DBConfig.COL_PASSENGER_ID + "=" + "'"+id+"'",null)>0;
    }

    public long insertVisitData(ChvVisitLocal tmpfileData){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConfig.COL_VISIT_USERID, tmpfileData.getUser_id());
        contentValues.put(DBConfig.COL_VISIT_TITLE, tmpfileData.getTitle());
        contentValues.put(DBConfig.COL_VISIT_NAME, tmpfileData.getName());
        contentValues.put(DBConfig.COL_VISIT_AGE, tmpfileData.getAge());
        contentValues.put(DBConfig.COL_VISIT_IDNUM, tmpfileData.getId_num());
        contentValues.put(DBConfig.COL_VISIT_NHIF, tmpfileData.getNhif());
        contentValues.put(DBConfig.COL_VISIT_VILLAGE, tmpfileData.getVillage());
        contentValues.put(DBConfig.COL_VISIT_NEARNAME, tmpfileData.getNearname());
        contentValues.put(DBConfig.COL_VISIT_MASK, tmpfileData.getMask());
        contentValues.put(DBConfig.COL_VISIT_WARD, tmpfileData.getWard());
        contentValues.put(DBConfig.COL_VISIT_PROVIDE, tmpfileData.getProvide());
        contentValues.put(DBConfig.COL_VISIT_MAL, tmpfileData.getMal());
        contentValues.put(DBConfig.COL_VISIT_DIABET, tmpfileData.getDiabet());
        contentValues.put(DBConfig.COL_VISIT_HYPER, tmpfileData.getHyper());
        contentValues.put(DBConfig.COL_VISIT_TB, tmpfileData.getTb());
        contentValues.put(DBConfig.COL_VISIT_INDICATE, tmpfileData.getIndicate());
        contentValues.put(DBConfig.COL_VISIT_REMARK, tmpfileData.getRemark());
        contentValues.put(DBConfig.COL_VISIT_CREATED, tmpfileData.getCreated_time());


        try {
            id = sqLiteDatabase.insertOrThrow(DBConfig.TABLE_VISIT, null, contentValues);
        } catch (SQLiteException e){

        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }
    public List<ChvVisitLocal> getVisitData(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(DBConfig.TABLE_VISIT, null, null, null, null, null, null, null);
            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<ChvVisitLocal> fileList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(DBConfig.COL_VISIT_ID));
                        String created_time =  cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_CREATED));
                        String luser_id = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_USERID));
                        String ltitle = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_TITLE));
                        String lname = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_NAME));
                        String lage = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_AGE));
                        String lid_num = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_IDNUM));
                        String lnhif = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_NHIF));
                        String lvillage = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_VILLAGE));
                        String lnearname = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_NEARNAME));
                        String lmask = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_MASK));
                        String lward = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_WARD));
                        String lprovide = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_PROVIDE));
                        String lmal = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_MAL));
                        String ldiabet =cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_DIABET));
                        String lhyper = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_HYPER));
                        String ltb = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_TB));
                        String lindicate = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_INDICATE));
                        String lremark = cursor.getString(cursor.getColumnIndex(DBConfig.COL_VISIT_REMARK));

                        fileList.add(new ChvVisitLocal(id, luser_id,ltitle, lname, lage, lid_num, lnhif,
                                lvillage, lnearname, lmask,lward,lprovide,lmal, ldiabet,lhyper,ltb,lindicate,lremark,created_time));
                    }   while (cursor.moveToNext());

                    return fileList;
                }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public boolean deleteVisitTable(int id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.delete(DBConfig.TABLE_VISIT,DBConfig.COL_VISIT_ID + "=" + "'"+id+"'",null)>0;
    }
    public long insertMotherData(ChvMotherLocal tmpfileData){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConfig.COL_MOTHER_USERID, tmpfileData.getUser_id());
        contentValues.put(DBConfig.COL_MOTHER_TITLE, tmpfileData.getTitle());
        contentValues.put(DBConfig.COL_MOTHER_NAME, tmpfileData.getName());
        contentValues.put(DBConfig.COL_MOTHER_NHIF, tmpfileData.getNhif());
        contentValues.put(DBConfig.COL_MOTHER_AGE, tmpfileData.getAge());
        contentValues.put(DBConfig.COL_MOTHER_MOTHERID, tmpfileData.getMother_id());
        contentValues.put(DBConfig.COL_MOTHER_VILLAGE, tmpfileData.getVillage());
        contentValues.put(DBConfig.COL_MOTHER_WARD, tmpfileData.getWard());
        contentValues.put(DBConfig.COL_MOTHER_TELNUM, tmpfileData.getTel_num());
        contentValues.put(DBConfig.COL_MOTHER_CLINIC, tmpfileData.getClinic());
        contentValues.put(DBConfig.COL_MOTHER_DUEDATE, tmpfileData.getDue_date());
        contentValues.put(DBConfig.COL_MOTHER_FOLIC, tmpfileData.getFolic());
        contentValues.put(DBConfig.COL_MOTHER_MARY, tmpfileData.getMary());
        contentValues.put(DBConfig.COL_MOTHER_CHILDREN, tmpfileData.getChildren());
        contentValues.put(DBConfig.COL_MOTHER_REMARK, tmpfileData.getRemark());
        contentValues.put(DBConfig.COL_MOTHER_CREATED, tmpfileData.getCreated_time());


        try {
            id = sqLiteDatabase.insertOrThrow(DBConfig.TABLE_MOTHER, null, contentValues);
        } catch (SQLiteException e){

        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }
    public List<ChvMotherLocal> getMotherData(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(DBConfig.TABLE_MOTHER, null, null, null, null, null, null, null);
            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<ChvMotherLocal> fileList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(DBConfig.COL_MOTHER_ID));
                        String created_time =  cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_CREATED));
                        String luser_id = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_USERID));
                        String ltitle = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_TITLE));
                        String lname = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_NAME));
                        String lage = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_AGE));
                        String lnhif = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_NHIF));
                        String lmother_id = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_MOTHERID));
                        String ltel_num = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_TELNUM));
                        String lclinic = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_CLINIC));
                        String ldue_date = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_DUEDATE));
                        String lfolic = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_FOLIC));
                        String lmary = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_MARY));
                        String lvillage = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_VILLAGE));
                        String lward = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_WARD));
                        String lchildren = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_CHILDREN));
                        String lremark = cursor.getString(cursor.getColumnIndex(DBConfig.COL_MOTHER_REMARK));
                        fileList.add(new ChvMotherLocal(id, luser_id,ltitle, lname, lage, lnhif, lmother_id, ltel_num, lclinic,
                                ldue_date,lfolic,lmary,lvillage, lward,lchildren,lremark,created_time));
                    }   while (cursor.moveToNext());

                    return fileList;
                }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public boolean deleteMotherTable(int id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.delete(DBConfig.TABLE_MOTHER,DBConfig.COL_MOTHER_ID + "=" + "'"+id+"'",null)>0;
    }

    public long insertGbvData(GbvLocal tmpfileData){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConfig.COL_GBV_USERID, tmpfileData.getUser_id());
        contentValues.put(DBConfig.COL_GBV_COUNTY, tmpfileData.getCounty());
        contentValues.put(DBConfig.COL_GBV_TITLE, tmpfileData.getTitle());
        contentValues.put(DBConfig.COL_GBV_NATURE, tmpfileData.getNature());
        contentValues.put(DBConfig.COL_GBV_GENDER, tmpfileData.getGender());
        contentValues.put(DBConfig.COL_GBV_AGE, tmpfileData.getAge());
        contentValues.put(DBConfig.COL_GBV_REPORTDATE, tmpfileData.getReport_date());
        contentValues.put(DBConfig.COL_GBV_STATUS, tmpfileData.getStatus());
        contentValues.put(DBConfig.COL_GBV_REPORTPLACE, tmpfileData.getReport_place());
        contentValues.put(DBConfig.COL_GBV_REMARK, tmpfileData.getRemark());
        contentValues.put(DBConfig.COL_GBV_CREATED, tmpfileData.getCreated());


        try {
            id = sqLiteDatabase.insertOrThrow(DBConfig.TABLE_GBV, null, contentValues);
        } catch (SQLiteException e){

        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }
    public List<GbvLocal> getGbvData(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(DBConfig.TABLE_GBV, null, null, null, null, null, null, null);
            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<GbvLocal> fileList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(DBConfig.COL_GBV_ID));
                        String created_time =  cursor.getString(cursor.getColumnIndex(DBConfig.COL_GBV_CREATED));
                        String luser_id = cursor.getString(cursor.getColumnIndex(DBConfig.COL_GBV_USERID));
                        String lcounty = cursor.getString(cursor.getColumnIndex(DBConfig.COL_GBV_COUNTY));
                        String ltitle = cursor.getString(cursor.getColumnIndex(DBConfig.COL_GBV_TITLE));
                        String lnature = cursor.getString(cursor.getColumnIndex(DBConfig.COL_GBV_NATURE));
                        String gender = cursor.getString(cursor.getColumnIndex(DBConfig.COL_GBV_GENDER));
                        String lage = cursor.getString(cursor.getColumnIndex(DBConfig.COL_GBV_AGE));
                        String report_date = cursor.getString(cursor.getColumnIndex(DBConfig.COL_GBV_REPORTDATE));
                        String lstatus = cursor.getString(cursor.getColumnIndex(DBConfig.COL_GBV_STATUS));
                        String lreport_place = cursor.getString(cursor.getColumnIndex(DBConfig.COL_GBV_REPORTPLACE));
                        String lremark = cursor.getString(cursor.getColumnIndex(DBConfig.COL_GBV_REMARK));
                        fileList.add(new GbvLocal(id, luser_id,ltitle, lcounty,  gender, lage,report_date,
                                lstatus, lreport_place, lremark,lnature,created_time));
                    }   while (cursor.moveToNext());

                    return fileList;
                }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public boolean deleteGbvTable(int id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.delete(DBConfig.TABLE_GBV,DBConfig.COL_GBV_ID + "=" + "'"+id+"'",null)>0;
    }


    public long insertEnforceData(EnforceLocal tmpfileData){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConfig.COL_ENFORCE_USERID, tmpfileData.getUser_id());
        contentValues.put(DBConfig.COL_ENFORCE_TYPE, tmpfileData.getEnforce_type());
        contentValues.put(DBConfig.COL_ENFORCE_TITLE, tmpfileData.getTitle());
        contentValues.put(DBConfig.COL_ENFORCE_DESCRIPTION, tmpfileData.getDescription());
        contentValues.put(DBConfig.COL_ENFORCE_CREATED, tmpfileData.getCreated());


        try {
            id = sqLiteDatabase.insertOrThrow(DBConfig.TABLE_ENFORCE, null, contentValues);
        } catch (SQLiteException e){

        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }
    public List<EnforceLocal> getEnforceData(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(DBConfig.TABLE_ENFORCE, null, null, null, null, null, null, null);
            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<EnforceLocal> fileList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(DBConfig.COL_ENFORCE_ID));
                        String created_time =  cursor.getString(cursor.getColumnIndex(DBConfig.COL_ENFORCE_CREATED));
                        String luser_id = cursor.getString(cursor.getColumnIndex(DBConfig.COL_ENFORCE_USERID));
                        String ltype = cursor.getString(cursor.getColumnIndex(DBConfig.COL_ENFORCE_TYPE));
                        String ltitle = cursor.getString(cursor.getColumnIndex(DBConfig.COL_ENFORCE_TITLE));
                        String ldescription = cursor.getString(cursor.getColumnIndex(DBConfig.COL_ENFORCE_DESCRIPTION));
                        fileList.add(new EnforceLocal(id , luser_id, ltype, ltitle,  ldescription,created_time));
                    }   while (cursor.moveToNext());

                    return fileList;
                }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public boolean deleteEnforceTable(int id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.delete(DBConfig.TABLE_ENFORCE,DBConfig.COL_ENFORCE_ID + "=" + "'"+id+"'",null)>0;
    }


    public long insertReportData(ReportLocal tmpfileData){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConfig.COL_REPORT_USERID, tmpfileData.getUserid());
        contentValues.put(DBConfig.COL_REPORT_TYPE, tmpfileData.getReport_type());
        contentValues.put(DBConfig.COL_REPORT_SYMPTOM, tmpfileData.getSyptom());
        contentValues.put(DBConfig.COL_REPORT_LAN, tmpfileData.getLan());
        contentValues.put(DBConfig.COL_REPORT_LON, tmpfileData.getLon());
        contentValues.put(DBConfig.COL_REPORT_CITY, tmpfileData.getCity());
        contentValues.put(DBConfig.COL_REPORT_STATE, tmpfileData.getState());
        contentValues.put(DBConfig.COL_REPORT_ADDRESS, tmpfileData.getAddress());
        contentValues.put(DBConfig.COL_REPORT_COUNTRY, tmpfileData.getCountry());
        contentValues.put(DBConfig.COL_REPORT_ADDITION, tmpfileData.getAddition());
        contentValues.put(DBConfig.COL_REPORT_CREATED, tmpfileData.getCreated());


        try {
            id = sqLiteDatabase.insertOrThrow(DBConfig.TABLE_REPORT, null, contentValues);
        } catch (SQLiteException e){

        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }
    public List<ReportLocal> getReportData(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(DBConfig.TABLE_REPORT, null, null, null, null, null, null, null);
            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<ReportLocal> fileList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(DBConfig.COL_REPORT_ID));
                        String luser_id = cursor.getString(cursor.getColumnIndex(DBConfig.COL_REPORT_USERID));
                        String lreport_type = cursor.getString(cursor.getColumnIndex(DBConfig.COL_REPORT_TYPE));
                        String symptom = cursor.getString(cursor.getColumnIndex(DBConfig.COL_REPORT_SYMPTOM));
                        String lcity = cursor.getString(cursor.getColumnIndex(DBConfig.COL_REPORT_CITY));
                        String lstate = cursor.getString(cursor.getColumnIndex(DBConfig.COL_REPORT_STATE));
                        String laddress = cursor.getString(cursor.getColumnIndex(DBConfig.COL_REPORT_ADDRESS));
                        String lcountry =  cursor.getString(cursor.getColumnIndex(DBConfig.COL_REPORT_COUNTRY));
                        String addition = cursor.getString(cursor.getColumnIndex(DBConfig.COL_REPORT_ADDITION));
                        String created_time =  cursor.getString(cursor.getColumnIndex(DBConfig.COL_REPORT_CREATED));
                        String lat = cursor.getString(cursor.getColumnIndex(DBConfig.COL_REPORT_LAN));
                        String lon = cursor.getString(cursor.getColumnIndex(DBConfig.COL_REPORT_LON));

                        fileList.add(new ReportLocal(id, luser_id, lreport_type, symptom,  lat,
                                lon, lcity, lstate, laddress, lcountry, addition,created_time));
                    }   while (cursor.moveToNext());

                    return fileList;
                }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public boolean deleteReportTable(int id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.delete(DBConfig.TABLE_REPORT,DBConfig.COL_REPORT_ID + "=" + "'"+id+"'",null)>0;
    }
    public void deleteReportAll(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("Delete from " + DBConfig.TABLE_REPORT);
        sqLiteDatabase.close();
    }
    public void deleteCheckAll(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("Delete from " + DBConfig.TABLE_CHECKIN);
        sqLiteDatabase.close();
    }
    public void deleteEnforceAll(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("Delete from " + DBConfig.TABLE_ENFORCE);
        sqLiteDatabase.close();
    }
    public void deleteOfficeAll(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("Delete from " + DBConfig.TABLE_OFFICER);
        sqLiteDatabase.close();
    }
    public void deletePassengerAll(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("Delete from " + DBConfig.TABLE_PASSENGER);
        sqLiteDatabase.close();
    }
    public void deleteVisitAll(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("Delete from " + DBConfig.TABLE_VISIT);
        sqLiteDatabase.close();
    }
    public void deleteMotherAll(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("Delete from " + DBConfig.TABLE_MOTHER);
        sqLiteDatabase.close();
    }
    public void deleteGbvAll(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("Delete from " + DBConfig.TABLE_GBV);
        sqLiteDatabase.close();
    }
}
