package com.techouts.pcomplaints;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.techouts.pcomplaints.datahandler.DatabaseHandler;
import com.techouts.pcomplaints.entities.PermissionApplication;
import com.techouts.pcomplaints.utils.AppConstents;
import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.adapters.ApplicationsListAdapter;
import com.techouts.pcomplaints.adapters.AreaAdapter;

import java.util.ArrayList;
import java.util.List;

public class ApplicationListActivity extends BaseActivity implements AreaAdapter.OnAreaClickListener {

    private RecyclerView rvApplications;
    private ApplicationsListAdapter applicationsListAdapter;
    private TextView tvTitle,tvArea,tvNoApplications;
    private ImageView ivBack;
    private PopupWindow popupWindow;

    @Override
    public int getRootLayout() {
        return R.layout.activity_application_list;
    }

    @Override
    public void initGUI() {
        rvApplications = findViewById(R.id.rvApplications);
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvNoApplications = findViewById(R.id.tvNoApplications);
        tvArea = findViewById(R.id.tvArea);
        rvApplications.setLayoutManager(new LinearLayoutManager(ApplicationListActivity.this));
        applicationsListAdapter = new ApplicationsListAdapter();
        rvApplications.setAdapter(applicationsListAdapter);

        tvTitle.setText("Applications");

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindow(v);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onAreaSelected(String area) {
        if(popupWindow!=null)
            popupWindow.dismiss();
        tvArea.setText(area);

        new GetApplicationsAsyncTask().execute(area);
    }


    class GetApplicationsAsyncTask extends AsyncTask<String,Void,List<PermissionApplication>>{

        @Override
        protected List<PermissionApplication> doInBackground(String... strings) {
            List<PermissionApplication> permissionApplicationList = DatabaseHandler.getInstance(getApplicationContext())
                    .permissionApplicationDao().getAllApplicationsByArea(strings[0]);
            return permissionApplicationList;
        }

        @Override
        protected void onPostExecute(List<PermissionApplication> permissionApplications) {
            super.onPostExecute(permissionApplications);
            if(permissionApplications!=null&&permissionApplications.size()>0){
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


    private void initiatePopupWindow(View v) {
        try {
            popupWindow  = new PopupWindow(this);
            popupWindow.setFocusable(true);
            View view = LayoutInflater.from(ApplicationListActivity.this).inflate(R.layout.popup_window_area_list,null);
            RecyclerView rvAreas = view.findViewById(R.id.rvAreas);
            ArrayList<String> areaList = new ArrayList<>();
            areaList.add("Domalguda");
            areaList.add("Secunderabad");
            areaList.add("Bansilapet");
            areaList.add("Liberty");
            areaList.add("Hyderguda");
            areaList.add("Shyamalal");
            areaList.add("Ligampalli");
            rvAreas.setLayoutManager(new LinearLayoutManager(ApplicationListActivity.this));
            rvAreas.setAdapter(new AreaAdapter(areaList,ApplicationListActivity.this));
            popupWindow.setContentView(view);
            popupWindow.showAsDropDown(v, Gravity.CENTER,0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
