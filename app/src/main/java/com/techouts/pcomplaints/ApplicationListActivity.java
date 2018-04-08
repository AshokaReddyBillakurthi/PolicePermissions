package com.techouts.pcomplaints;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techouts.pcomplaints.adapters.ApplicationsListAdapter;
import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.datahandler.DatabaseHandler;
import com.techouts.pcomplaints.entities.PermissionApplication;
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

        if(searchBy.equalsIgnoreCase(AppConstents.SEARCH_BY_AREA))
            tvSearchBy.setHint("Select Area");
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
                if(searchBy.equalsIgnoreCase(AppConstents.SEARCH_BY_AREA)){
                    list = DataManager.getList(AppConstents.TYPE_AREA);
                    title = "Select Area";
                }
                else if(searchBy.equalsIgnoreCase(AppConstents.SEARCH_BY_APPLICATION_TYPE)){
                    list = DataManager.getAllServices();
                    title = "Select Application Type";
                }

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
        });
    }

    @Override
    public void initData() {
        /* need to do */
    }

    class GetApplicationsAsyncTask extends AsyncTask<String,Void,List<PermissionApplication>>{

        @Override
        protected List<PermissionApplication> doInBackground(String... strings) {
            if(searchBy.equalsIgnoreCase(AppConstents.SEARCH_BY_AREA))
                return  DatabaseHandler.getInstance(getApplicationContext())
                    .permissionApplicationDao().getAllApplicationsByArea(strings[0]);
            else
                return  DatabaseHandler.getInstance(getApplicationContext())
                        .permissionApplicationDao().getAllApplicationsByApplicationType(strings[0]);
        }

        @Override
        protected void onPostExecute(List<PermissionApplication> permissionApplications) {
            super.onPostExecute(permissionApplications);
            if(permissionApplications!=null&&!permissionApplications.isEmpty()){
                applicationsListAdapter.refresh(permissionApplications);
                tvNoApplications.setVisibility(View.GONE);
                rvApplications.setVisibility(View.VISIBLE);
            }
            else{
                tvNoApplications.setVisibility(View.VISIBLE);
                rvApplications.setVisibility(View.GONE);
            }
        }
    }
}
