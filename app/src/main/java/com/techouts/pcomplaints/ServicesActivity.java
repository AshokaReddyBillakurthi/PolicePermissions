package com.techouts.pcomplaints;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techouts.pcomplaints.adapters.ServicesAdapter;
import com.techouts.pcomplaints.utils.AppConstents;

import java.util.ArrayList;

public class ServicesActivity extends BaseActivity {

    private TextView tvTitle;
    private ImageView ivBack;
    private RecyclerView rvServices;
    private ServicesAdapter servicesAdapter;
    private ArrayList<String> listServices;

    @Override
    public int getRootLayout() {
        return R.layout.activity_services;
    }

    @Override
    public void initGUI() {
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        rvServices = findViewById(R.id.rvServices);
        listServices = new ArrayList<>();
        rvServices.setLayoutManager(new GridLayoutManager(ServicesActivity.this,2));
        tvTitle.setText("Services");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        listServices.add(AppConstents.POLICE_PERMISSIONS);
        listServices.add(AppConstents.MATRIMONIAL_VERIFICATIONS);
        listServices.add(AppConstents.DRAFTING_COMPLAINTS);
        listServices.add(AppConstents.POLICE_IDENTITY_ADDRESS_TRACE);
        servicesAdapter = new ServicesAdapter(listServices);
        rvServices.setAdapter(servicesAdapter);
    }
}
