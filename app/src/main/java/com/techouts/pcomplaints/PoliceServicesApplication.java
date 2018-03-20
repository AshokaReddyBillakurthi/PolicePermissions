package com.techouts.pcomplaints;

import android.app.Application;
import android.content.Context;

/**
 * Created by TO-OW109 on 02-02-2018.
 */

public class PoliceServicesApplication extends Application {

    private static PoliceServicesApplication mInstance;
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public synchronized static PoliceServicesApplication getInstance() {
        if (mInstance == null) { //if there is no instance available... create new one
            synchronized (PoliceServicesApplication.class) {
                if (mInstance == null) mInstance = new PoliceServicesApplication();
            }
        }
        return mInstance;
    }

    public Context getContext() {
        return mContext;
    }
}
