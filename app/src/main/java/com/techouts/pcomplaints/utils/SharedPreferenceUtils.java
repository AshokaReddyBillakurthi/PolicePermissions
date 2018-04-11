package com.techouts.pcomplaints.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by TO-OW109 on 09-02-2018.
 */

public class SharedPreferenceUtils {

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    public static String APPLICATION_NO = "APPLICATION_NO";

    public SharedPreferenceUtils(Context context){
        pref = context.getSharedPreferences("PermissionPref", 0);
        editor = pref.edit();
    }


    public static void putStaringValue(String key, String value){
        editor.putString(key,value);
        editor.commit();
    }

    public static void putLongValue(String key, long val){
        editor.putLong(key,val);
        editor.commit();
    }

    public static void putBooleanValue(String key,boolean boolVal){
        editor.putBoolean(key,boolVal);
        editor.commit();
    }

    public static String getStringValue(String key){
        return pref.getString(key,"");
    }

    public static long getLongValue(String key){
        return pref.getLong(key,0);
    }

    public static boolean getBoolValue(String key){
        return pref.getBoolean(key,false);
    }

}
