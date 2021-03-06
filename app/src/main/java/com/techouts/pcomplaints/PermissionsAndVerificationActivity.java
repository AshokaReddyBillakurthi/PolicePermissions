package com.techouts.pcomplaints;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.techouts.pcomplaints.adapters.PermissionListAdapter;
import com.techouts.pcomplaints.utils.AppConstents;

import java.util.ArrayList;

public class PermissionsAndVerificationActivity extends BaseActivity {

    private TextView tvTitle;
    private ImageView ivBack,ivCross;
    private RecyclerView rvPolicePermissions;
    private PermissionListAdapter permissionListAdapter;
    private ArrayList<String> permissionList;
    private EditText edtSearch;
    private ArrayList<String> tempSearchList;
    private String serviceName = "";

    @Override
    public int getRootLayout() {
        return R.layout.activity_permissions_and_verifications;
    }

    @Override
    public void initGUI() {
        if(getIntent().getExtras()!=null){
            serviceName = getIntent().getExtras().getString(AppConstents.EXTRA_SEARVICE_TYPE,"");
        }
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        ivCross = findViewById(R.id.ivCross);
        edtSearch = findViewById(R.id.edtSearch);
        rvPolicePermissions = findViewById(R.id.rvPolicePermissions);
        rvPolicePermissions.setLayoutManager(new LinearLayoutManager(PermissionsAndVerificationActivity.this));

        permissionList = new ArrayList<>();
        if(serviceName.equalsIgnoreCase(AppConstents.POLICE_PERMISSIONS)) {
            tvTitle.setText(AppConstents.POLICE_PERMISSIONS);
            permissionList.add(AppConstents.GUN_LICENCES);
            permissionList.add(AppConstents.INTERNET_CAFES);
            permissionList.add(AppConstents.SNOOKERS_PARLOURS);
            permissionList.add(AppConstents.PARKING_PLACES);
            permissionList.add(AppConstents.EVENTS_FUNCTIONS_MIKES);
            permissionList.add(AppConstents.LODGES_HOTELS);
            permissionList.add(AppConstents.FILM_TV_SHOOTINGS);
            permissionList.add(AppConstents.POLICE_BB_FOR_PVT_FUNCTIONS);
        }
        else if(serviceName.equalsIgnoreCase(AppConstents.MATRIMONIAL_VERIFICATIONS)){
            tvTitle.setText(AppConstents.MATRIMONIAL_VERIFICATIONS);
            permissionList.add(AppConstents.MARTIMONIAL_VERIFICATION);
//            permissionList.add(AppConstents.IMARTIMONIAL_ISSUES);
        }
        else if(serviceName.equalsIgnoreCase(AppConstents.DRAFTING_COMPLAINTS)){
            tvTitle.setText(AppConstents.DRAFTING_COMPLAINTS);
            permissionList.add(AppConstents.CRIME_REPORT);
            permissionList.add(AppConstents.Nocs);
            permissionList.add(AppConstents.LICENCES_RENEWALS);
            permissionList.add(AppConstents.CERTIFIED_COPIES);
            permissionList.add(AppConstents.RTI_AND_APPEALS_TO_HIGHER_UPS);
        }
        else if(serviceName.equalsIgnoreCase(AppConstents.POLICE_IDENTITY_ADDRESS_TRACE)){
            tvTitle.setText(AppConstents.POLICE_IDENTITY_ADDRESS_TRACE);
            permissionList.add(AppConstents.PHONE_ADDRESSES);
            permissionList.add(AppConstents.ADDHAR_ID_PROOFS);
            permissionList.add(AppConstents.DECLARED_AND_STATED_ADDRESS);
        }
        permissionListAdapter = new PermissionListAdapter(permissionList);
        rvPolicePermissions.setAdapter(permissionListAdapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null&&s.length()>0)
                    ivCross.setVisibility(View.VISIBLE);
                else
                    ivCross.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null&&s.length()>=3){
                    searchText(s.toString());
                }
                else if(s.length()==0){
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
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    private void searchText(String searchText){
        try{
            if(!TextUtils.isEmpty(searchText)){
                tempSearchList = new ArrayList<>();
                for(String permission :permissionList){
                    if(permission.contains(searchText)){
                        tempSearchList.add(permission);
                    }
                }
                if(tempSearchList!=null&&tempSearchList.size()>0)
                    permissionListAdapter.refresh(tempSearchList);
            }
            else {
                permissionListAdapter.refresh(permissionList);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
