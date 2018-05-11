package com.techouts.pcomplaints.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.adapters.ServicesAdapter;
import com.techouts.pcomplaints.utils.AppConstents;

import java.util.ArrayList;

public class UserDashBoardFragment extends BaseFragment {

    private RecyclerView rvServices;
    private ServicesAdapter servicesAdapter;
    private ArrayList<String> listServices;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_dashboard,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listServices = new ArrayList<>();

        rvServices = view.findViewById(R.id.rvServices);
        rvServices.setLayoutManager(new GridLayoutManager(getContext(),2));
        listServices.add(AppConstents.POLICE_PERMISSIONS);
        listServices.add(AppConstents.MATRIMONIAL_VERIFICATIONS);
        listServices.add(AppConstents.DRAFTING_COMPLAINTS);
        listServices.add(AppConstents.POLICE_IDENTITY_ADDRESS_TRACE);
        servicesAdapter = new ServicesAdapter(listServices);
        rvServices.setAdapter(servicesAdapter);
    }
}
