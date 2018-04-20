package com.techouts.pcomplaints.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.techouts.pcomplaints.model.District;
import com.techouts.pcomplaints.model.DivisionPoliceStation;
import com.techouts.pcomplaints.model.State;
import com.techouts.pcomplaints.model.SubDivision;

import java.util.ArrayList;
import java.util.List;

public class AppDataHelper {

    public static void insertUpdateStates(Context context, List<State> stateList){
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();
            String insertQuery = "Insert into tblStates(stateCode,stateName)" +
                    " values(?,?)";
            String updateQuery = "Update tblStates set stateName = ?" +
                    " Where stateCode = ?";
            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);

            if(null!= stateList&&!stateList.isEmpty()){
                for(State state: stateList){
                    updateStmt.bindString(1,state.stateName);
                    updateStmt.bindString(2,state.stateCode);
                    if(updateStmt.executeUpdateDelete()<= 0){
                        insertStmt.bindString(1,state.stateCode);
                        insertStmt.bindString(2,state.stateName);
                        insertStmt.executeInsert();
                    }
                }
            }
            if(null!=updateStmt)
                updateStmt.close();
            if(null!=insertStmt)
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

    public static void insertUpdateDistricts(Context context, List<District> districtList){
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();
            String insertQuery = "Insert into tblDistrict(districtCode,districtName,stateCode) " +
                    " values(?,?,?)";
            String updateQuery = "Update tblDistrict set districtName = ?,stateCode = ?" +
                    " Where districtCode = ?";
            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);

            if(null!= districtList&&!districtList.isEmpty()){
                for(District district: districtList){
                    updateStmt.bindString(1,district.districtName);
                    updateStmt.bindString(2,district.stateCode);
                    updateStmt.bindString(3,district.districtCode);

                    if(updateStmt.executeUpdateDelete()<= 0){
                        insertStmt.bindString(1,district.districtCode);
                        insertStmt.bindString(2,district.districtName);
                        insertStmt.bindString(3,district.stateCode);
                        insertStmt.executeInsert();
                    }
                }
            }
            if(null!=updateStmt)
                updateStmt.close();
            if(null!=insertStmt)
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


    public static void insertUpdateSubDivisions(Context context, List<SubDivision> subDivisionList){
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();

            String insertQuery = "Insert into tblSubDivision " +
                    "(subDivisionCode,subDivisionName,districtCode) values(?,?,?)";
            String updateQuery = "Update tblSubDivision set subDivisionName = ?," +
                    "districtCode=? Where subDivisionCode = ?";

            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);

            if(null!= subDivisionList&&!subDivisionList.isEmpty()){
                for(SubDivision subDivision: subDivisionList){
                    updateStmt.bindString(1,subDivision.subDivisionName);
                    updateStmt.bindString(2,subDivision.districtCode);
                    updateStmt.bindString(3,subDivision.subDivisionCode);

                    if(updateStmt.executeUpdateDelete()<= 0){
                        insertStmt.bindString(1,subDivision.subDivisionCode);
                        insertStmt.bindString(2,subDivision.subDivisionName);
                        insertStmt.bindString(3,subDivision.districtCode);
                        insertStmt.executeInsert();
                    }
                }
            }
            if(null!=updateStmt)
                updateStmt.close();
            if(null!=insertStmt)
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
    public static void insertUpdateDivisionPoliceStations(Context context,
                                                          List<DivisionPoliceStation> divisionPoliceStationList) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();

            String insertQuery = "Insert into tblDivisionPoliceStation " +
                    "(divisionPoliceStationCode,divisionPoliceStationName,subDivisionCode) values(?,?,?)";
            String updateQuery = "Update tblDivisionPoliceStation set divisionPoliceStationName = ?," +
                    " subDivisionCode=? Where divisionPoliceStationCode = ?";

            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);

            if (null != divisionPoliceStationList && !divisionPoliceStationList.isEmpty()) {
                for (DivisionPoliceStation divisionPoliceStation : divisionPoliceStationList) {
                    updateStmt.bindString(1, divisionPoliceStation.divisionPoliceStationName);
                    updateStmt.bindString(2, divisionPoliceStation.subDivisionCode);
                    updateStmt.bindString(3, divisionPoliceStation.divisionPoliceStationCode);

                    if (updateStmt.executeUpdateDelete() <= 0) {
                        insertStmt.bindString(1, divisionPoliceStation.divisionPoliceStationCode);
                        insertStmt.bindString(2, divisionPoliceStation.divisionPoliceStationName);
                        insertStmt.bindString(3, divisionPoliceStation.subDivisionCode);
                        insertStmt.executeInsert();
                    }
                }
            }
            if (null != updateStmt)
                updateStmt.close();
            if (null != insertStmt)
                insertStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != sqLiteDatabase && sqLiteDatabase.isOpen())
                sqLiteDatabase.close();
        }
    }


    public static List<State> getAllStates(Context context){
        List<State> statesList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();
            String selectQuery = " Select stateCode,stateName from tblStates ";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                State state = null;
                do{
                    state = new State();
                    state.stateCode = cursor.getString(0);
                    state.stateName = cursor.getString(1);
                    statesList.add(state);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return statesList;
    }
    public static List<District> getAllDistrictByStateCode(Context context,String stateCode){
        List<District> districtList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();
            String selectQuery = " Select districtCode,districtName from tblDistrict Where stateCode='"+stateCode+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                District district = null;
                do{
                    district = new District();
                    district.districtCode = cursor.getString(0);
                    district.districtName = cursor.getString(1);
                    districtList.add(district);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return districtList;
    }

    public static List<SubDivision> getAllSubDivisionsByDistrictCode(Context context,String districtCode){
        List<SubDivision> subDivisionList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();
            String selectQuery = " Select subDivisionCode,subDivisionName from tblSubDivision " +
                    "Where districtCode='"+districtCode+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                SubDivision subDivision = null;
                do{
                    subDivision = new SubDivision();
                    subDivision.subDivisionCode = cursor.getString(0);
                    subDivision.subDivisionName = cursor.getString(1);
                    subDivisionList.add(subDivision);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return subDivisionList;
    }
    public static List<DivisionPoliceStation> getAllDivisionPoliceStationByDistrictCode(Context context,String subDivisionCode){
        List<DivisionPoliceStation> divisionPoliceStationList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).openDataBase();
            String selectQuery = " Select divisionPoliceStationCode,divisionPoliceStationName " +
                    "from tblDivisionPoliceStation Where subDivisionCode = '"+subDivisionCode+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                DivisionPoliceStation divisionPoliceStation = null;
                do{
                    divisionPoliceStation = new DivisionPoliceStation();
                    divisionPoliceStation.divisionPoliceStationCode = cursor.getString(0);
                    divisionPoliceStation.divisionPoliceStationName = cursor.getString(1);
                    divisionPoliceStationList.add(divisionPoliceStation);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return divisionPoliceStationList;
    }


}
