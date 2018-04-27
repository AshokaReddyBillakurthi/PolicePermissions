package com.techouts.pcomplaints.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.techouts.pcomplaints.model.AddressModel;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper database;
    private static final String DATABASE_NAME = "XPoliceServicesDB.sqlite";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase sqLiteDatabase = null;

    // Database creation sql statement
    private static final String CREATE_TABLE_USER = "Create table if not exists tblUsers" +
            "(firstName text not null,lastName text not null,email text primary key,password text not null," +
            "mobileNo text not null, state text not null, city text not null," +
            "area text not null,district text not null, subDivision text not null," +
            "circlePolicestation text not null, userImg text not null,userType text not null)";

    private static final String CREATE_TABLE_XSERVICEMAN = "Create table if not exists tblXServiceMans " +
            "(firstName text not null,lastName text not null,email text primary key," +
            "password text not null,mobileNo text not null, state text not null," +
            " city text not null,area text not null, district text not null, " +
            "subDivision text not null,circlePolicestation text not null," +
            " userImg text not null,userType text not null,services text not null," +
            "reqDocs text not null, status text not null,isActive text not null)";

    private static final String CREATE_TABLE_APPLICATION = "Create table if not exists tblApplications " +
            "(applicationNo text primary key,firstName text not null, lastName text not null," +
            "email text not null,mobileNo text not null, state text not null, city text not null, " +
            "area text not null, district text not null, subDivision text not null," +
            "circlePolicestation text not null, userImg text not null,applicationType text not null," +
            "status text not null,isAccepted text not null, xServiceManEmail text, payableAmount text)";

    private static final String CREATE_TABLE_STATE = "Create table if not exists tblStates " +
            "(stateCode text primary key,stateName text not null)";

    private static final String CREATE_TABLE_DISTRICT = "Create table if not exists tblDistrict " +
            "(districtCode text primary key,districtName text not null, stateCode text not null)";

    private static final String CREATE_TABLE_SUBDIVISION = "Create table if not exists tblSubDivision" +
            " (subDivisionCode text primary key, subDivisionName text not null,districtCode text not null)";

    private static final String CREATE_TABLE_DIVISIONPOLICESTATION = "Create table if not exists tblDivisionPoliceStation " +
            "(divisionPoliceStationCode text primary key," +
            " divisionPoliceStationName text not null,subDivisionCode text not null)";

    private static final String CREATE_TABLE_SERVICES = "Create table if not exists tblServices" +
            "(serviceCode text primary key,serviceName text not null)";

    private static final String CREATE_TABLE_SUBSERVICES = "Create table if not exists tblSubServices " +
            "(subServiceCode text primary key, subServiceName text not null, serviceCode text not null)";

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (database == null)
            database = new DatabaseHelper(context,DATABASE_NAME,null, DATABASE_VERSION);
        return  database;
    }

    public SQLiteDatabase openDataBase(){
        try{
            if(sqLiteDatabase == null)
                sqLiteDatabase = this.getWritableDatabase();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return sqLiteDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_XSERVICEMAN);
        db.execSQL(CREATE_TABLE_APPLICATION);
        db.execSQL(CREATE_TABLE_STATE);
        db.execSQL(CREATE_TABLE_DISTRICT);
        db.execSQL(CREATE_TABLE_SUBDIVISION);
        db.execSQL(CREATE_TABLE_DIVISIONPOLICESTATION);
        db.execSQL(CREATE_TABLE_SERVICES);
        db.execSQL(CREATE_TABLE_SUBSERVICES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS tblUsers");
            db.execSQL("DROP TABLE IF EXISTS tblXServiceMans");
            db.execSQL("DROP TABLE IF EXISTS tblApplications");
            db.execSQL("DROP TABLE IF EXISTS tblStates");
            db.execSQL("DROP TABLE IF EXISTS tblDistrict");
            db.execSQL("DROP TABLE IF EXISTS tblSubDivision");
            db.execSQL("DROP TABLE IF EXISTS tblDivisionPoliceStation");
            db.execSQL("DROP TABLE IF EXISTS tblServices");
            db.execSQL("DROP TABLE IF EXISTS tblSubServices");
            onCreate(db);
        }
    }

    public static void insertUpdateStates(Context context,List<AddressModel.State> stateList){
        try{
            SQLiteDatabase sqLiteDatabase = getInstance(context).getWritableDatabase();
            AppDataHelper.insertUpdateStates(sqLiteDatabase,stateList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void insertUpdateDistricts(Context context,List<AddressModel.District> districtList){
        try{
            SQLiteDatabase sqLiteDatabase = getInstance(context).getWritableDatabase();
            AppDataHelper.insertUpdateDistricts(sqLiteDatabase,districtList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void insertUpdateSubDivisions(Context context, List<AddressModel.SubDivision> subDivisionList){
        try{
            SQLiteDatabase sqLiteDatabase = getInstance(context).getWritableDatabase();
            AppDataHelper.insertUpdateSubDivisions(sqLiteDatabase,subDivisionList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void insertUpdateDivisionPoliceStations(Context context,
                                                          List<AddressModel.DivisionPoliceStation>
            divisionPoliceStationList){
        try{
            SQLiteDatabase sqLiteDatabase = getInstance(context).getWritableDatabase();
            AppDataHelper.insertUpdateDivisionPoliceStations(sqLiteDatabase,divisionPoliceStationList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
