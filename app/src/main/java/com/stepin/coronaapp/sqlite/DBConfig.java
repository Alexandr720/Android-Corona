package com.stepin.coronaapp.sqlite;

public class DBConfig {
    public static final String DATABASE_NAME = "backup.db";

    public static final String TABLE_CHECKIN = "checkin";
    public static final String COL_CHECK_ID = "_id";
    public static final String COL_CHECK_PEOPLE = "people";
    public static final String COL_CHECK_TIME = "time";
    public static final String COL_CHECK_UTIL = "util";
    public static final String COL_CHECK_ADDITION = "addition";
    public static final String COL_CHECK_CITY = "city";
    public static final String COL_CHECK_STATE = "state";
    public static final String COL_CHECK_ADDRESS = "address";
    public static final String COL_CHECK_COUNTRY = "country";
    public static final String COL_CHECK_LAT = "lat";
    public static final String COL_CHECK_LON = "lon";
    public static final String COL_CHECK_USERID = "userid";
    public static final String COL_CHECK_CREATED = "created_time";

    public static final String TABLE_OFFICER = "officer";
    public static final String COL_OFFICER_ID = "_id";
    public static final String COL_OFFICER_USERID = "user_id";
    public static final String COL_OFFICER_NAME = "name";
    public static final String COL_OFFICER_TITLE = "title";
    public static final String COL_OFFICER_CARDNUM = "card_num";
    public static final String COL_OFFICER_SERVICETYPE = "type";
    public static final String COL_OFFICER_TELNUM = "tel_num";
    public static final String COL_OFFICER_ADDRESS = "address";
    public static final String COL_OFFICER_REGNUM = "reg_num";
    public static final String COL_OFFICER_DESTINATION = "destination";
    public static final String COL_OFFICER_DATE = "date";
    public static final String COL_OFFICER_CREATED = "created_time";

    public static final String TABLE_PASSENGER = "passenger";
    public static final String COL_PASSENGER_ID = "_id";
    public static final String COL_PASSENGER_USERID = "userid";
    public static final String COL_PASSENGER_TEMP = "temp";
    public static final String COL_PASSENGER_NAME = "name";
    public static final String COL_PASSENGER_TITLE = "title";
    public static final String COL_PASSENGER_VNUM = "vnum";
    public static final String COL_PASSENGER_TELNUM = "telnum";
    public static final String COL_PASSENGER_SEATNUM = "seatnum";
    public static final String COL_PASSENGER_PUBLISHDATE = "pdate";
    public static final String COL_PASSENGER_FVILLAGE = "fvillage";
    public static final String COL_PASSENGER_TVILLAGE = "tvillage";
    public static final String COL_PASSENGER_CONTACT = "contact";
    public static final String COL_PASSENGER_CONTACTNUM = "contactnum";
    public static final String COL_PASSENGER_LOCATION = "location";
    public static final String COL_PASSENGER_INFECT = "infect";
    public static final String COL_PASSENGER_HISTORY = "history";
    public static final String COL_PASSENGER_IDNUM = "idnum";
    public static final String COL_PASSENGER_CREATED = "created";

    public static final String TABLE_MOTHER = "mother";
    public static final String COL_MOTHER_ID = "_id";
    public static final String COL_MOTHER_USERID = "userid";
    public static final String COL_MOTHER_TITLE = "title";
    public static final String COL_MOTHER_NAME = "name";
    public static final String COL_MOTHER_NHIF = "nhif";
    public static final String COL_MOTHER_AGE = "age";
    public static final String COL_MOTHER_MOTHERID = "motherid";
    public static final String COL_MOTHER_VILLAGE = "village";
    public static final String COL_MOTHER_WARD = "ward";
    public static final String COL_MOTHER_TELNUM = "telnum";
    public static final String COL_MOTHER_CLINIC = "clinic";
    public static final String COL_MOTHER_DUEDATE = "duedate";
    public static final String COL_MOTHER_FOLIC = "folic";
    public static final String COL_MOTHER_MARY = "mary";
    public static final String COL_MOTHER_CHILDREN= "children";
    public static final String COL_MOTHER_REMARK = "remark";
    public static final String COL_MOTHER_CREATED = "created";


    public static final String TABLE_VISIT = "visit";
    public static final String COL_VISIT_ID = "_id";
    public static final String COL_VISIT_USERID = "userid";
    public static final String COL_VISIT_TITLE = "title";
    public static final String COL_VISIT_NAME = "name";
    public static final String COL_VISIT_AGE = "age";
    public static final String COL_VISIT_IDNUM = "id_num";
    public static final String COL_VISIT_NHIF = "nhif";
    public static final String COL_VISIT_VILLAGE = "village";
    public static final String COL_VISIT_NEARNAME = "near";
    public static final String COL_VISIT_MASK = "mask";
    public static final String COL_VISIT_WARD = "ward";
    public static final String COL_VISIT_PROVIDE = "provide";
    public static final String COL_VISIT_MAL = "mal";
    public static final String COL_VISIT_DIABET = "diabet";
    public static final String COL_VISIT_HYPER = "hyper";
    public static final String COL_VISIT_TB = "tb";
    public static final String COL_VISIT_INDICATE= "indicate";
    public static final String COL_VISIT_REMARK = "remark";
    public static final String COL_VISIT_CREATED = "created";

    public static final String TABLE_GBV = "gbv";
    public static final String COL_GBV_ID = "_id";
    public static final String COL_GBV_USERID = "userid";
    public static final String COL_GBV_COUNTY = "county";
    public static final String COL_GBV_TITLE = "title";
    public static final String COL_GBV_NATURE = "nature";
    public static final String COL_GBV_GENDER = "gender";
    public static final String COL_GBV_AGE = "age";
    public static final String COL_GBV_REPORTDATE = "date";
    public static final String COL_GBV_STATUS = "status";
    public static final String COL_GBV_REPORTPLACE = "place";
    public static final String COL_GBV_REMARK = "remark";
    public static final String COL_GBV_CREATED = "created";

    public static final String TABLE_ENFORCE = "enforce";
    public static final String COL_ENFORCE_ID = "_id";
    public static final String COL_ENFORCE_USERID = "userid";
    public static final String COL_ENFORCE_TYPE = "type";
    public static final String COL_ENFORCE_TITLE = "title";
    public static final String COL_ENFORCE_DESCRIPTION = "description";
    public static final String COL_ENFORCE_CREATED = "created";

    public static final String TABLE_REPORT = "report";
    public static final String COL_REPORT_ID = "_id";
    public static final String COL_REPORT_USERID = "userid";
    public static final String COL_REPORT_TYPE = "type";
    public static final String COL_REPORT_SYMPTOM = "symptom";
    public static final String COL_REPORT_LAN = "lon";
    public static final String COL_REPORT_LON = "lan";
    public static final String COL_REPORT_CITY = "city";
    public static final String COL_REPORT_STATE = "state";
    public static final String COL_REPORT_ADDRESS = "address";
    public static final String COL_REPORT_COUNTRY = "country";
    public static final String COL_REPORT_ADDITION = "addition";
    public static final String COL_REPORT_CREATED = "created";


}
