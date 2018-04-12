package com.techouts.pcomplaints.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.techouts.pcomplaints.entities.Application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationHelper {

    public static void insertApplicationData(Context context,ArrayList<Application> applicationList){
        try{
            SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();

            String insertQuery = "Insert into tblApplications(firstName,lastName,email,mobileNo,state," +
                    "district,subDivision,circlePolicestation,userImg,applicationType," +
                    "status,isAccepted,xServiceManEmail,payableAmount) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);

            if(null!= applicationList&&!applicationList.isEmpty()){
                for(Application application: applicationList){
                    insertStmt.bindString(1,application.firstName);
                    insertStmt.bindString(2,application.lastName);
                    insertStmt.bindString(3,application.email);
                    insertStmt.bindString(4,application.mobileNo);
                    insertStmt.bindString(5,application.state);
                    insertStmt.bindString(6,application.district);
                    insertStmt.bindString(7,application.subDivision);
                    insertStmt.bindString(8,application.circlePolicestation);
                    insertStmt.bindString(9,application.userImg);
                    insertStmt.bindString(10,application.applicationType);
                    insertStmt.bindString(11,application.status+"");
                    insertStmt.bindString(12,application.isAccepted+"");
                    insertStmt.bindString(13,application.xServiceManEmail);
                    insertStmt.bindString(14,application.payableAmount+"");
                    insertStmt.executeInsert();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Application> getAllApplications(Context context){
        List<Application> applicationList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();
            String selectQuery = "Select firstName,lastName,email,mobileNo,state," +
                    "district,subDivision,circlePolicestation,userImg,applicationType," +
                    " status,isAccepted,xServiceManEmail,payableAmount from tblApplications ";

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                do{
                    Application application = new Application();
                    application.firstName = cursor.getString(0);
                    application.lastName = cursor.getString(1);
                    application.email = cursor.getString(2);
                    application.mobileNo = cursor.getString(3);
                    application.state = cursor.getString(4);
                    application.district = cursor.getString(5);
                    application.subDivision = cursor.getString(6);
                    application.circlePolicestation = cursor.getString(7);
                    application.userImg = cursor.getString(8);
                    application.applicationType = cursor.getString(9);
                    application.status = Integer.parseInt(cursor.getString(10));
                    application.isAccepted =Integer.parseInt(cursor.getString(11));
                    application.xServiceManEmail = cursor.getString(12);
                    application.payableAmount = Integer.parseInt(cursor.getString(13));
                    applicationList.add(application);

                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return applicationList;
    }

    public static List<Application> getAllApplicationsByArea(Context context,String circlePolicestation){
        List<Application> applicationList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();
            String selectQuery = "Select firstName,lastName,email,mobileNo,state," +
                    "district,subDivision,circlePolicestation,userImg,applicationType," +
                    " status,isAccepted,xServiceManEmail,payableAmount from tblApplications " +
                    " Where circlePolicestation = '"+circlePolicestation+"' ";

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                do{
                    Application application = new Application();
                    application.firstName = cursor.getString(0);
                    application.lastName = cursor.getString(1);
                    application.email = cursor.getString(2);
                    application.mobileNo = cursor.getString(3);
                    application.state = cursor.getString(4);
                    application.district = cursor.getString(5);
                    application.subDivision = cursor.getString(6);
                    application.circlePolicestation = cursor.getString(7);
                    application.userImg = cursor.getString(8);
                    application.applicationType = cursor.getString(9);
                    application.status = Integer.parseInt(cursor.getString(10));
                    application.isAccepted =Integer.parseInt(cursor.getString(11));
                    application.xServiceManEmail = cursor.getString(12);
                    application.payableAmount = Integer.parseInt(cursor.getString(13));
                    applicationList.add(application);

                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return applicationList;
    }

    public static List<Application> getAllApplicationsByApplicationType(Context context,String applicationType){
        List<Application> applicationList = new ArrayList<>();
        try{
            SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();
            String selectQuery = "Select firstName,lastName,email,mobileNo,state," +
                    "district,subDivision,circlePolicestation,userImg,applicationType," +
                    " status,isAccepted,xServiceManEmail,payableAmount from tblApplications " +
                    " Where applicationType = '"+applicationType+"' ";

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                do{
                    Application application = new Application();
                    application.firstName = cursor.getString(0);
                    application.lastName = cursor.getString(1);
                    application.email = cursor.getString(2);
                    application.mobileNo = cursor.getString(3);
                    application.state = cursor.getString(4);
                    application.district = cursor.getString(5);
                    application.subDivision = cursor.getString(6);
                    application.circlePolicestation = cursor.getString(7);
                    application.userImg = cursor.getString(8);
                    application.applicationType = cursor.getString(9);
                    application.status = Integer.parseInt(cursor.getString(10));
                    application.isAccepted =Integer.parseInt(cursor.getString(11));
                    application.xServiceManEmail = cursor.getString(12);
                    application.payableAmount = Integer.parseInt(cursor.getString(13));
                    applicationList.add(application);

                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return applicationList;
    }
}
