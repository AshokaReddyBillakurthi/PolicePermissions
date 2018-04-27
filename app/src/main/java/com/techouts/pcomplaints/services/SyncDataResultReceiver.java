package com.techouts.pcomplaints.services;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class SyncDataResultReceiver extends ResultReceiver {

    private Receiver mReceiver;

    public SyncDataResultReceiver(Handler handler) {
        super(handler);
        // TODO Auto-generated constructor stub
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);

    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }

}