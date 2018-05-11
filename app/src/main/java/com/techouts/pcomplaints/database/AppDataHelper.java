package com.techouts.pcomplaints.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.techouts.pcomplaints.model.AddressModel;

import java.util.ArrayList;
import java.util.List;

public class AppDataHelper {

    public static void insertUpdateStates( SQLiteDatabase sqLiteDatabase, List<AddressModel.State> stateList){
        try{
            String insertQuery = "Insert into tblStates(stateCode,stateName)" +
                    " values(?,?)";
            String updateQuery = "Update tblStates set stateName = ?" +
                    " Where stateCode = ?";
            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);

            if(null!= stateList&&!stateList.isEmpty()){
                for(AddressModel.State state: stateList){
                    updateStmt.bindString(1,state.getStateName());
                    updateStmt.bindString(2,state.getStateCode());
                    if(updateStmt.executeUpdateDelete()<= 0){
                        insertStmt.bindString(1,state.getStateCode());
                        insertStmt.bindString(2,state.getStateName());
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

    public static void insertUpdateDistricts(SQLiteDatabase sqLiteDatabase, List<AddressModel.District> districtList){
        try{
            String insertQuery = "Insert into tblDistrict(districtCode,districtName,stateCode) " +
                    " values(?,?,?)";
            String updateQuery = "Update tblDistrict set districtName = ?,stateCode = ?" +
                    " Where districtCode = ?";
            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);
            if(null!= districtList&&!districtList.isEmpty()){
                for(AddressModel.District district: districtList){
                    updateStmt.bindString(1,district.getDistrictName());
                    updateStmt.bindString(2,district.getStateCode());
                    updateStmt.bindString(3,district.getDistrictCode());

                    if(updateStmt.executeUpdateDelete()<= 0){
                        insertStmt.bindString(1,district.getDistrictCode());
                        insertStmt.bindString(2,district.getDistrictName());
                        insertStmt.bindString(3,district.getStateCode());
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

    public static void insertUpdateSubDivisions(SQLiteDatabase sqLiteDatabase,
                                                List<AddressModel.SubDivision> subDivisionList){
        try{
            String insertQuery = "Insert into tblSubDivision " +
                    "(subDivisionCode,subDivisionName,districtCode) values(?,?,?)";
            String updateQuery = "Update tblSubDivision set subDivisionName = ?," +
                    "districtCode=? Where subDivisionCode = ?";

            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);

            if(null!= subDivisionList&&!subDivisionList.isEmpty()){
                for(AddressModel.SubDivision subDivision: subDivisionList){
                    updateStmt.bindString(1,subDivision.getSubDivisionName());
                    updateStmt.bindString(2,subDivision.getDistrictCode());
                    updateStmt.bindString(3,subDivision.getSubDivisionCode());

                    if(updateStmt.executeUpdateDelete()<= 0){
                        insertStmt.bindString(1,subDivision.getSubDivisionCode());
                        insertStmt.bindString(2,subDivision.getSubDivisionName());
                        insertStmt.bindString(3,subDivision.getDistrictCode());
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
    public static void insertUpdateDivisionPoliceStations( SQLiteDatabase sqLiteDatabase,
                                                          List<AddressModel.DivisionPoliceStation>
                                                                  divisionPoliceStationList) {
        try {
            String insertQuery = "Insert into tblDivisionPoliceStation " +
                    "(divisionPoliceStationCode,divisionPoliceStationName,subDivisionCode) values(?,?,?)";
            String updateQuery = "Update tblDivisionPoliceStation set divisionPoliceStationName = ?," +
                    " subDivisionCode=? Where divisionPoliceStationCode = ?";

            SQLiteStatement insertStmt = sqLiteDatabase.compileStatement(insertQuery);
            SQLiteStatement updateStmt = sqLiteDatabase.compileStatement(updateQuery);

            if (null != divisionPoliceStationList && !divisionPoliceStationList.isEmpty()) {
                for (AddressModel.DivisionPoliceStation divisionPoliceStation : divisionPoliceStationList) {
                    updateStmt.bindString(1, divisionPoliceStation.getDivisionName());
                    updateStmt.bindString(2, divisionPoliceStation.getSubDivisionCode());
                    updateStmt.bindString(3, divisionPoliceStation.getDivisionCode());

                    if (updateStmt.executeUpdateDelete() <= 0) {
                        insertStmt.bindString(1, divisionPoliceStation.getDivisionCode());
                        insertStmt.bindString(2, divisionPoliceStation.getDivisionName());
                        insertStmt.bindString(3, divisionPoliceStation.getSubDivisionCode());
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

    public static List<AddressModel.State> getAllStates(Context context){
        List<AddressModel.State> statesList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = " Select stateCode,stateName from tblStates ";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                AddressModel.State state = null;
                do{
                    state = new AddressModel.State();
                    state.setStateCode(cursor.getString(0));
                    state.setStateName(cursor.getString(1));
                    statesList.add(state);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return statesList;
    }
    public static List<AddressModel.District> getAllDistrictByStateCode(Context context,String stateCode){
        List<AddressModel.District> districtList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = " Select districtCode,districtName from tblDistrict Where stateCode='"+stateCode+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                AddressModel.District district = null;
                do{
                    district = new AddressModel.District();
                    district.setDistrictCode(cursor.getString(0));
                    district.setDistrictName(cursor.getString(1));
                    districtList.add(district);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return districtList;
    }

    public static List<AddressModel.SubDivision> getAllSubDivisionsByDistrictCode(Context context,
                                                                                  String districtCode){
        List<AddressModel.SubDivision> subDivisionList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = " Select subDivisionCode,subDivisionName from tblSubDivision " +
                    "Where districtCode='"+districtCode+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                AddressModel.SubDivision subDivision = null;
                do{
                    subDivision = new AddressModel.SubDivision();
                    subDivision.setSubDivisionCode(cursor.getString(0));
                    subDivision.setSubDivisionName(cursor.getString(1));
                    subDivisionList.add(subDivision);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return subDivisionList;
    }
    public static List<AddressModel.DivisionPoliceStation> getAllDivisionPoliceStationByDistrictCode(Context context,
                                                                                                     String subDivisionCode){
        List<AddressModel.DivisionPoliceStation> divisionPoliceStationList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = " Select divisionPoliceStationCode,divisionPoliceStationName " +
                    "from tblDivisionPoliceStation Where subDivisionCode = '"+subDivisionCode+"'";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                AddressModel.DivisionPoliceStation divisionPoliceStation = null;
                do{
                    divisionPoliceStation = new AddressModel.DivisionPoliceStation();
                    divisionPoliceStation.setDivisionCode(cursor.getString(0));
                    divisionPoliceStation.setDivisionName(cursor.getString(1));
                    divisionPoliceStationList.add(divisionPoliceStation);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return divisionPoliceStationList;
    }

    public static List<AddressModel.DivisionPoliceStation> getAllDivisionPoliceStations(Context context){
        List<AddressModel.DivisionPoliceStation> divisionPoliceStationList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        try{
            sqLiteDatabase = DatabaseHelper.getInstance(context).getWritableDatabase();
            String selectQuery = " Select divisionPoliceStationCode,divisionPoliceStationName " +
                    "from tblDivisionPoliceStation ";
            Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
            if(null!=cursor&&cursor.moveToFirst()){
                AddressModel.DivisionPoliceStation divisionPoliceStation = null;
                do{
                    divisionPoliceStation = new AddressModel.DivisionPoliceStation();
                    divisionPoliceStation.setDivisionCode(cursor.getString(0));
                    divisionPoliceStation.setDivisionName(cursor.getString(1));
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
