package com.techouts.pcomplaints;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.techouts.pcomplaints.R;

public class ApplicationActivity extends BaseActivity {

    private RecyclerView rvApplication;
    
    @Override
    public int getRootLayout() {
        return R.layout.activity_application;
    }

    @Override
    public void initGUI() {
        rvApplication = findViewById(R.id.rvApplication);
        rvApplication.setLayoutManager(new LinearLayoutManager(ApplicationActivity.this));

    }

    @Override
    public void initData() {

    }
}
