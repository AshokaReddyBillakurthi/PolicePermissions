package com.techouts.pcomplaints.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.techouts.pcomplaints.database.DatabaseHelper;
import com.techouts.pcomplaints.model.AddressModel;
import com.techouts.pcomplaints.utils.ApiServiceConstants;
import com.techouts.pcomplaints.utils.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SyncDataService extends IntentService {

    public final static String MY_ACTION = "MY_ACTION";

    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     */
    public SyncDataService() {
        super("SyncDataService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            getAllData(ApiServiceConstants.GEO_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllData(final String name) {
        try {
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL + name);
            builder.get();
            Request request = builder.build();
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String body = response.body().string().toString();
                    parseAddressData(body);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseAddressData(String body){
        try{
            AddressModel addressModel = new Gson().fromJson(body,AddressModel.class);
            if(null!=addressModel){
                if(null!=addressModel.getStates()&&!addressModel.getStates().isEmpty()){
                    DatabaseHelper.insertUpdateStates(getApplicationContext(),addressModel.getStates());
                }
                if(null!=addressModel.getDistricts()&&!addressModel.getDistricts().isEmpty()){
                    DatabaseHelper.insertUpdateDistricts(getApplicationContext(),addressModel.getDistricts());
                }
                if(null!=addressModel.getSubDivisions()&&!addressModel.getSubDivisions().isEmpty()){
                    DatabaseHelper.insertUpdateSubDivisions(getApplicationContext(),addressModel.getSubDivisions());
                }
                if(null!=addressModel.getDivisionPoliceStations()&&!addressModel.getDivisionPoliceStations().isEmpty()){
                    DatabaseHelper.insertUpdateDivisionPoliceStations(getApplicationContext(),
                            addressModel.getDivisionPoliceStations());

                    Intent data = new Intent();
                    data.setAction(MY_ACTION);
                    data.putExtra("DATAPASSED", "Done");
                    sendBroadcast(data);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
