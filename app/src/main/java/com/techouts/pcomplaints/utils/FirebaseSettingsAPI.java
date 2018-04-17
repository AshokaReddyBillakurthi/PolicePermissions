package com.techouts.pcomplaints.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.techouts.pcomplaints.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by TO-OW109 on 26-03-2018.
 */

public class FirebaseSettingsAPI {
    Context mContext;
    private SharedPreferences sharedSettings;

    public FirebaseSettingsAPI(Context context) {
        mContext = context;
        sharedSettings = mContext.getSharedPreferences(mContext.getString(R.string.settings_file_name), Context.MODE_PRIVATE);
    }

    public String readSetting(String key) {
        return sharedSettings.getString(key, "na");
    }

    public void addUpdateSettings(String key, String value) {
        SharedPreferences.Editor editor = sharedSettings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void deleteAllSettings() {
        sharedSettings.edit().clear().apply();
    }

    public List<String> readAll() {
        List<String> allUser = new ArrayList<>();
        Map<String, ?> allEntries = sharedSettings.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().contains("@"))
                allUser.add(entry.getKey() + " (" + entry.getValue() + ")");
        }
        return allUser;
    }
}
