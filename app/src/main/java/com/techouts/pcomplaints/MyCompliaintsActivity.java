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
import com.techouts.pcomplaints.database.ApplicationHelper;
import com.techouts.pcomplaints.model.Application;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class MyCompliaintsActivity extends BaseActivity {

    private RecyclerView rvAppcationList;
    private EditText edtSearch;
    private ImageView ivCross,ivBack;
    private TextView tvTitle;
    private ApplicationsListAdapter applicationsListAdapter;
    private List<Application> tempApplication;
    private List<Application> applicationList;
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
            tempApplication = new ArrayList<>();
            if (!TextUtils.isEmpty(searchText)) {
                for (Application application : applicationList) {
                    if ((application.area.toLowerCase().contains(searchText))
                            || (application.firstName.toLowerCase().contains(searchText))
                            || (application.applicationType.toLowerCase().contains(searchText))) {
                        tempApplication.add(application);
                    }
                }
                if (tempApplication != null && tempApplication.size() > 0) {
                    applicationsListAdapter.refresh(tempApplication);
                }
            } else {
                applicationsListAdapter.refresh(applicationList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initData() {
        new GetApplicationsAsyncTask().execute(SharedPreferenceUtils.getStringValue(AppConstents.EMAIL_ID)+"");
    }


    class GetApplicationsAsyncTask extends AsyncTask<String,Void,List<Application>> {

        @Override
        protected List<Application> doInBackground(String... strings) {
            return ApplicationHelper.getAllApplicationsByEmailId(MyCompliaintsActivity.this,strings[0]);
        }
        @Override
        protected void onPostExecute(List<Application> applications) {
            super.onPostExecute(applications);
            if(applications!=null&&!applications.isEmpty()){
                applicationList = applications;
                applicationsListAdapter.refresh(applications);
            }
        }
    }
}
