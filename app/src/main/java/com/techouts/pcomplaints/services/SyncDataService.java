package com.techouts.pcomplaints.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.techouts.pcomplaints.database.AppDataHelper;
import com.techouts.pcomplaints.model.District;
import com.techouts.pcomplaints.model.DivisionPoliceStation;
import com.techouts.pcomplaints.model.State;
import com.techouts.pcomplaints.model.SubDivision;
import com.techouts.pcomplaints.utils.ApiServiceConstants;
import com.techouts.pcomplaints.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SyncDataService extends IntentService {

    ResultReceiver resultReceiver;
    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     */
    public SyncDataService() {
        super("SyncDataService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        resultReceiver = intent.getParcelableExtra("receiver");
        getAllStates();
        getAllDistricts();
        getAllSubDivisions();
        getAllDivisionPoliceStations();
    }

    private void getAllStates(){
        try{
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL+ApiServiceConstants.STATES);
            builder.get();
            Request request = builder.build();
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String body = response.body().string().toString();
                    try {
                        List<State> stateList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArray = jsonObject.getJSONArray("states");
                        if(null!=jsonArray){
                            State state = null;
                            for(int i=0;i<jsonArray.length();i++){
                                state = new State();
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                state.stateCode = jsonObject1.getString("stateCode");
                                state.stateName = jsonObject1.getString("stateName");
                                stateList.add(state);
                            }
                            if(null!=stateList&&!stateList.isEmpty()){
                                AppDataHelper.insertUpdateStates(getApplicationContext(),stateList);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getAllDistricts(){
        try{
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL+ApiServiceConstants.DISTRICTS);
            builder.get();
            Request request = builder.build();
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String body = response.body().string().toString();
                    try {
                        List<District> districtsList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArray = jsonObject.getJSONArray("districts");
                        if(null!=jsonArray){
                            District district = null;
                            for(int i=0;i<jsonArray.length();i++){
                                district = new District();
                                JSONObject jsonObject1 =  jsonArray.getJSONObject(i);
                                district.districtCode = jsonObject1.getString("districtCode");
                                district.districtName = jsonObject1.getString("districtName");
                                district.stateCode = jsonObject1.getString("stateCode");
                                districtsList.add(district);
                            }
                            if(null!=districtsList&&!districtsList.isEmpty()){
                                AppDataHelper.insertUpdateDistricts(getApplicationContext(),districtsList);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getAllSubDivisions(){
        try{
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL+ApiServiceConstants.SUB_DIVISIONS);
            builder.get();
            Request request = builder.build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String body = response.body().string().toString();
                    try {
                        List<SubDivision> subDivisionList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArray = jsonObject.getJSONArray("subDivisions");
                        if(null!=jsonArray){
                            SubDivision subDivision = null;
                            for(int i=0;i<jsonArray.length();i++){
                                subDivision = new SubDivision();
                                JSONObject jsonObject1 =  jsonArray.getJSONObject(i);
                                subDivision.subDivisionCode = jsonObject1.getString("subDivisionCode");
                                subDivision.subDivisionName = jsonObject1.getString("subDivisionName");
                                subDivision.districtCode = jsonObject1.getString("districtCode");
                                subDivisionList.add(subDivision);
                            }
                            if(null!=subDivisionList&&!subDivisionList.isEmpty()){
                                AppDataHelper.insertUpdateSubDivisions(getApplicationContext(),subDivisionList);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getAllDivisionPoliceStations(){
        try{
            OkHttpClient client = OkHttpUtils.getOkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(ApiServiceConstants.MAIN_URL+ApiServiceConstants.DIVISION);
            builder.get();
            Request request = builder.build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String body = response.body().string().toString();
                    try {
                        List<DivisionPoliceStation> divisionPoliceStationList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray jsonArray = jsonObject.getJSONArray("divisionPoliceStations");
                        if(null!=jsonArray){
                            DivisionPoliceStation divisionPoliceStation = null;
                            for(int i=0;i<jsonArray.length();i++){
                                divisionPoliceStation = new DivisionPoliceStation();
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                divisionPoliceStation.divisionPoliceStationCode = jsonObject1.getString("divisionCode");
                                divisionPoliceStation.divisionPoliceStationName = jsonObject1.getString("divisionName");
                                divisionPoliceStation.subDivisionCode = jsonObject1.getString("subDivisionCode");
                                divisionPoliceStationList.add(divisionPoliceStation);
                            }
                            if(null!=divisionPoliceStationList&&!divisionPoliceStationList.isEmpty()){
                                AppDataHelper.insertUpdateDivisionPoliceStations(getApplicationContext(),divisionPoliceStationList);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

}
