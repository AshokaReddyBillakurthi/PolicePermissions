package com.techouts.pcomplaints.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.techouts.pcomplaints.model.ExServiceMan;

import java.util.ArrayList;

public class XServiceManDataHelper {

    public static void insertUserData(Context context,ArrayList<ExServiceMan> exServiceManList){
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();
            String insertQuery = "Insert into tblXServiceMans(firstName,lastName," +
                    "email,password,mobileNo,state,city,area" +
                    "district,subDivision,circlePolicestation," +
                    "userImg,userType,services,reqDocs,status) " +
                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);

            if(null!= exServiceManList&&!exServiceManList.isEmpty()){
                for(ExServiceMan exServiceMan: exServiceManList){
                    insertStmt.bindString(1,exServiceMan.firstName);
                    insertStmt.bindString(2,exServiceMan.lastName);
                    insertStmt.bindString(3,exServiceMan.email);
                    insertStmt.bindString(4,exServiceMan.password);
                    insertStmt.bindString(5,exServiceMan.mobileNo);
                    insertStmt.bindString(6,exServiceMan.state);
                    insertStmt.bindString(7,exServiceMan.city);
                    insertStmt.bindString(8,exServiceMan.area);
                    insertStmt.bindString(9,exServiceMan.district);
                    insertStmt.bindString(10,exServiceMan.subDivision);
                    insertStmt.bindString(11,exServiceMan.circlePolicestation);
                    insertStmt.bindString(12,exServiceMan.userImg);
                    insertStmt.bindString(13,exServiceMan.userType);
                    insertStmt.bindString(14,exServiceMan.services);
                    insertStmt.bindString(15,exServiceMan.reqDocs);
                    insertStmt.bindString(16,exServiceMan.status+"");
                    insertStmt.executeInsert();
                }
                if(null!=insertStmt)
                    insertStmt.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(null!=sqLiteDatabase&&sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
    }

    public static boolean updateStatus(Context context, String status,String email){
        SQLiteDatabase sqLiteDatabase = null;
        boolean isUpdated = false;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();
            String updateQuery = "Update tblXServiceMans set status='"+status+"' Where email ='"+email+"'";
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);
            int count = updateStmt.executeUpdateDelete();
            if(count>0)
                isUpdated = true;

            if(null!=updateStmt)
                updateStmt.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(null!=sqLiteDatabase&&sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return isUpdated;
    }

    public static boolean isValidXServiceMan(Context context,String email, String password){
        boolean isValidXServiceMan = false;
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();
            String selectQuery = "Select count(*) from tblXServiceMans Where email = '"+email+"'" +
                    " and password = '"+password+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                int count = cursor.getInt(0);
                if(count>0)
                    isValidXServiceMan = true;
            }

            if(null!=cursor&&!cursor.isClosed())
                cursor.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(null!=sqLiteDatabase&&sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return isValidXServiceMan;
    }

    public static ArrayList<ExServiceMan> getAllXServiceMans(Context context,String status){
        ArrayList<ExServiceMan> XServiceManList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();
            String selectQuery = "Select firstName,lastName,email,mobileNo," +
                    "state,city,area,district,subDivision,circlePolicestation," +
                    "userImg,userType,services,reqDocs,status from tblXServiceMans" +
                    " Where status = '"+status+"'";

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                do{
                    ExServiceMan xServiceMan = new ExServiceMan();
                    xServiceMan.firstName = cursor.getString(0);
                    xServiceMan.lastName = cursor.getString(1);
                    xServiceMan.email = cursor.getString(2);
                    xServiceMan.mobileNo = cursor.getString(3);
                    xServiceMan.state = cursor.getString(4);
                    xServiceMan.state = cursor.getString(5);
                    xServiceMan.state = cursor.getString(6);
                    xServiceMan.district = cursor.getString(7);
                    xServiceMan.subDivision = cursor.getString(8);
                    xServiceMan.circlePolicestation = cursor.getString(9);
                    xServiceMan.userImg = cursor.getString(10);
                    xServiceMan.userType = cursor.getString(11);
                    xServiceMan.services = cursor.getString(12);
                    xServiceMan.reqDocs = cursor.getString(13);
                    xServiceMan.status = Integer.parseInt(cursor.getString(14));
                    XServiceManList.add(xServiceMan);

                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(null!=sqLiteDatabase&&sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
        return XServiceManList;
    }
}
