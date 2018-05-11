package com.techouts.pcomplaints.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

import com.techouts.pcomplaints.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDataHelper {

    public static void insertUserData(Context context,ArrayList<User> userList){
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();

            String insertQuery = "Insert into tblUsers(firstName,lastName," +
                    "email,password,mobileNo,state,city,area," +
                    "district,subDivision,circlePolicestation,userImg,userType)" +
                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);

            if(null!= userList&&!userList.isEmpty()){
                for(User user: userList){
                    insertStmt.bindString(1,user.firstName);
                    insertStmt.bindString(2,user.lastName);
                    insertStmt.bindString(3,user.email);
                    insertStmt.bindString(4,user.password);
                    insertStmt.bindString(5,user.mobileNo);
                    insertStmt.bindString(6,user.state);
                    insertStmt.bindString(7,user.city);
                    insertStmt.bindString(8,user.area);
                    insertStmt.bindString(9,user.district);
                    insertStmt.bindString(10,user.subDivision);
                    insertStmt.bindString(11,user.circlePolicestation);
                    insertStmt.bindString(12,user.userImg);
                    insertStmt.bindString(13,user.userType);
                    insertStmt.executeInsert();
                }
            }
            insertStmt.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(null!=sqLiteDatabase&&sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
    }

    public static boolean isValidUser(Context context,String email, String password){
        boolean isValidUser = false;
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = "Select email from tblUsers Where email = '"+email+"' and password = '"+password+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                String emailId = cursor.getString(0);
                if(!TextUtils.isEmpty(emailId))
                    isValidUser = true;
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
        return isValidUser;
    }

    public static List<User> getAllUsers(Context context){
        List<User> userList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = "Select firstName,lastName,email,mobileNo,state,city,area," +
                    "district,subDivision,circlePolicestation,userImg,userType from tblUsers";

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                do{
                    User user = new User();
                    user.firstName  = cursor.getString(0);
                    user.lastName = cursor.getString(1);
                    user.email = cursor.getString(2);
                    user.mobileNo = cursor.getString(3);
                    user.state = cursor.getString(4);
                    user.city = cursor.getString(5);
                    user.area = cursor.getString(6);
                    user.district = cursor.getString(7);
                    user.subDivision = cursor.getString(8);
                    user.circlePolicestation = cursor.getString(9);
                    user.userImg = cursor.getString(10);
                    user.userType = cursor.getString(11);
                    userList.add(user);

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
        return userList;
    }

    public static User getUserByEmailId(Context context,String email){
        User user = null;
        try{
            SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = "Select firstName,lastName,email,mobileNo,state,city,area," +
                    "district,subDivision,circlePolicestation,userImg,userType " +
                    " from tblUsers Where email = '"+email+"'";

            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                do{
                    user = new User();
                    user.firstName = cursor.getString(0);
                    user.lastName = cursor.getString(1);
                    user.email = cursor.getString(2);
                    user.mobileNo = cursor.getString(3);
                    user.state = cursor.getString(4);
                    user.city = cursor.getString(5);
                    user.area = cursor.getString(6);
                    user.district = cursor.getString(7);
                    user.subDivision = cursor.getString(8);
                    user.circlePolicestation = cursor.getString(9);
                    user.userImg = cursor.getString(10);
                    user.userType = cursor.getString(11);

                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
