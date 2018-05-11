package com.techouts.pcomplaints;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techouts.pcomplaints.adapters.ApplicationsListAdapter;
import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.database.AppDataHelper;
import com.techouts.pcomplaints.database.ApplicationHelper;
import com.techouts.pcomplaints.model.AddressModel;
import com.techouts.pcomplaints.model.Application;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.DataManager;

import java.util.List;

public class ApplicationListActivity extends BaseActivity  {

    private RecyclerView rvApplications;
    private ApplicationsListAdapter applicationsListAdapter;
    private TextView tvSearchBy;
    private TextView tvNoApplications;
    private CustomDialog customDialog;
    private String searchBy = "";

    @Override
    public int getRootLayout() {
        return R.layout.activity_application_list;
    }

    @Override
    public void initGUI() {

        if(getIntent().getExtras()!=null){
            searchBy = getIntent().getStringExtra(AppConstents.EXTRA_SEARCH_BY);
        }
        rvApplications = findViewById(R.id.rvApplications);
        tvNoApplications = findViewById(R.id.tvNoApplications);
        tvSearchBy = findViewById(R.id.tvSearchBy);
        rvApplications.setLayoutManager(new LinearLayoutManager(ApplicationListActivity.this));
        applicationsListAdapter = new ApplicationsListAdapter();
        rvApplications.setAdapter(applicationsListAdapter);
        ImageView ivBack = findViewById(R.id.ivBack);
        TextView tvTitle = findViewById(R.id.tvTitle);

        tvTitle.setText("Applications");

        if(searchBy.equalsIgnoreCase(AppConstents.SEARCH_BY_DIVISION_POLICE_STATION))
            tvSearchBy.setHint("Select Division/Police Station");
        else if(searchBy.equalsIgnoreCase(AppConstents.SEARCH_BY_APPLICATION_TYPE))
            tvSearchBy.setHint("Select Application Type");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvSearchBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = null;
                String title = "";
                if(searchBy.equalsIgnoreCase(AppConstents.SEARCH_BY_DIVISION_POLICE_STATION)){
                    title = "Select Division/Police Station";
                    new GetAllDivisionPoliceStations().execute();
                }
                else if(searchBy.equalsIgnoreCase(AppConstents.SEARCH_BY_APPLICATION_TYPE)){
                    list = DataManager.getAllServices();
                    title = "Select Application Type";
                    customDialog = new CustomDialog(ApplicationListActivity.this, list, title,
                            true,false,
                            new CustomDialog.NameSelectedListener() {
                                @Override
                                public void onNameSelected(String listName) {
                                    tvSearchBy.setText(listName);
                                    new GetApplicationsAsyncTask().execute(listName);
                                    customDialog.dismiss();
                                }
                            });

                    customDialog.show();
                }
            }
        });
    }

    @Override
    public void initData() {
        /* need to do */
    }

    class GetApplicationsAsyncTask extends AsyncTask<String,Void,List<Application>>{

        @Override
        protected List<Application> doInBackground(String... strings) {
            if(searchBy.equalsIgnoreCase(AppConstents.SEARCH_BY_DIVISION_POLICE_STATION))
                return ApplicationHelper.getAllApplicationsByArea(ApplicationListActivity.this,strings[0]);
            else
                return ApplicationHelper.getAllApplicationsByApplicationType(ApplicationListActivity.this,
                        strings[0]);
        }

        @Override
        protected void onPostExecute(List<Application> applications) {
            super.onPostExecute(applications);
            if(applications!=null&&!applications.isEmpty()){
                applicationsListAdapter.refresh(applications);
                tvNoApplications.setVisibility(View.GONE);
                rvApplications.setVisibility(View.VISIBLE);
            }
            else{
                tvNoApplications.setVisibility(View.VISIBLE);
                rvApplications.setVisibility(View.GONE);
            }
        }
    }

    class GetAllDivisionPoliceStations extends AsyncTask<Void,Void,List<AddressModel.DivisionPoliceStation>>{

        @Override
        protected List<AddressModel.DivisionPoliceStation> doInBackground(Void... voids) {
            return AppDataHelper.getAllDivisionPoliceStations(ApplicationListActivity.this);
        }

        @Override
        protected void onPostExecute(List<AddressModel.DivisionPoliceStation> divisionPoliceStations) {
            super.onPostExecute(divisionPoliceStations);
            if(null!=divisionPoliceStations&&!divisionPoliceStations.isEmpty()){
                customDialog = new CustomDialog(ApplicationListActivity.this,
                        divisionPoliceStations,"Select Division/Police Station",
                        true, false, true,
                        new CustomDialog.OnDivisionPoliceStation() {
                            @Override
                            public void onDivisionPoliceStation(String divisionPoliceStation) {
                                if(null!=divisionPoliceStation){
                                    tvSearchBy.setText(divisionPoliceStation+"");
                                    new GetApplicationsAsyncTask().execute(divisionPoliceStation);
                                }
                                customDialog.dismiss();
                            }
                        });
                customDialog.show();
            }
        }
    }
}
