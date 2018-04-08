package com.techouts.pcomplaints;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.techouts.pcomplaints.adapters.ApplicationsListAdapter;
import com.techouts.pcomplaints.datahandler.DatabaseHandler;
import com.techouts.pcomplaints.entities.PermissionApplication;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class MyCompliaintsActivity extends BaseActivity {

    private RecyclerView rvAppcationList;
    private EditText edtSearch;
    private ImageView ivCross,ivBack;
    private TextView tvTitle;
    private List<PermissionApplication> tempPermissionApplication;
    private ApplicationsListAdapter applicationsListAdapter;
    private List<PermissionApplication> permissionApplicationList;
    @Override
    public int getRootLayout() {
        return R.layout.activity_my_applications;
    }

    @Override
    public void initGUI() {
        rvAppcationList = findViewById(R.id.rvAppcationList);
        edtSearch = findViewById(R.id.edtSearch);
        ivCross = findViewById(R.id.ivCross);
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);

        rvAppcationList.setLayoutManager(new LinearLayoutManager(MyCompliaintsActivity.this));
        applicationsListAdapter = new ApplicationsListAdapter();
        rvAppcationList.setAdapter(applicationsListAdapter);

        tvTitle.setText(AppConstents.MY_COMPLAINTS);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0)
                    ivCross.setVisibility(View.VISIBLE);
                else
                    ivCross.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() >= 3) {
                    searchText(s.toString());
                } else if (s.length() == 0) {
                    ivCross.setVisibility(View.GONE);
                    searchText("");
                }
            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
                edtSearch.setHint("Search here");
                ivCross.setVisibility(View.GONE);
                searchText("");
            }
        });
    }

    private void searchText(String searchText) {
        try {
            tempPermissionApplication = new ArrayList<>();
            if (!TextUtils.isEmpty(searchText)) {
                for (PermissionApplication permissionApplication : permissionApplicationList) {
                    if ((permissionApplication.area.toLowerCase().contains(searchText))
                            || (permissionApplication.fullName.toLowerCase().contains(searchText))
                            || (permissionApplication.applicationType.toLowerCase().contains(searchText))) {
                        tempPermissionApplication.add(permissionApplication);
                    }
                }
                if (tempPermissionApplication != null && tempPermissionApplication.size() > 0) {
                    applicationsListAdapter.refresh(tempPermissionApplication);
                }
            } else {
                applicationsListAdapter.refresh(permissionApplicationList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initData() {
        new GetApplicationsAsyncTask().execute(SharedPreferenceUtils.getStringValue(AppConstents.EMAIL_ID)+"");
    }


    class GetApplicationsAsyncTask extends AsyncTask<String,Void,List<PermissionApplication>> {

        @Override
        protected List<PermissionApplication> doInBackground(String... strings) {
                return  DatabaseHandler.getInstance(getApplicationContext())
                        .permissionApplicationDao().getAllApplicationsByEmail(strings[0]);

        }
        @Override
        protected void onPostExecute(List<PermissionApplication> permissionApplications) {
            super.onPostExecute(permissionApplications);
            if(permissionApplications!=null&&!permissionApplications.isEmpty()){
                permissionApplicationList = permissionApplications;
                applicationsListAdapter.refresh(permissionApplications);
            }
        }
    }
}
