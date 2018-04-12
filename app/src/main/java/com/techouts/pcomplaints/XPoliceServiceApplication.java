package com.techouts.pcomplaints;

import android.app.Application;
import android.content.Context;

import com.techouts.pcomplaints.database.DatabaseHelper;
import com.techouts.pcomplaints.database.UserDataHelper;
import com.techouts.pcomplaints.utils.PayUConfigDetails;

/**
 * Created by TO-OW109 on 02-02-2018.
 */

public class XPoliceServiceApplication extends Application {

    private static XPoliceServiceApplication mInstance;
    private PayUConfigDetails payUConfigDetails;
    private Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        payUConfigDetails = PayUConfigDetails.SANDBOX;
    }

    public synchronized static XPoliceServiceApplication getInstance() {
        if (mInstance == null) { //if there is no instance available... create new one
            synchronized (XPoliceServiceApplication.class) {
                if (mInstance == null) mInstance = new XPoliceServiceApplication();
            }
        }
        return mInstance;
    }

    public PayUConfigDetails getPayUConfigDetails() {
        return payUConfigDetails;
    }

    public void setPayUConfigDetails(PayUConfigDetails payUConfigDetails) {
        this.payUConfigDetails = payUConfigDetails;
    }

    public Context getContext() {
        return mContext;
    }
}
