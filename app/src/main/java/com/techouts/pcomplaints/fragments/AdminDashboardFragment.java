package com.techouts.pcomplaints.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.adapters.ApplicationsListAdapter;
import com.techouts.pcomplaints.adapters.ExServiceManListAdapter;
import com.techouts.pcomplaints.database.ApplicationHelper;
import com.techouts.pcomplaints.database.XServiceManDataHelper;
import com.techouts.pcomplaints.model.Application;
import com.techouts.pcomplaints.model.ExServiceMan;

import java.util.List;

public class AdminDashboardFragment extends BaseFragment {

    private RecyclerView rvServiceMans;
    private RecyclerView rvApplications;
    private ExServiceManListAdapter exServiceManListAdapter;
    private ApplicationsListAdapter applicationsListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_admin_dashboard,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvServiceMans = view.findViewById(R.id.rvServiceMans);
        rvApplications = view.findViewById(R.id.rvApplications);

        rvServiceMans.setLayoutManager(new LinearLayoutManager(getContext()));
        exServiceManListAdapter = new ExServiceManListAdapter(getContext());
        rvServiceMans.setAdapter(exServiceManListAdapter);

        rvApplications.setLayoutManager(new LinearLayoutManager(getContext()));
        applicationsListAdapter = new ApplicationsListAdapter();
        rvApplications.setAdapter(applicationsListAdapter);

        new GetAllServiceMans().execute();
        new GetAllApplications().execute();
    }


    class GetAllServiceMans extends AsyncTask<Void,Void,List<ExServiceMan>>{

        @Override
        protected List<ExServiceMan> doInBackground(Void... voids) {
            return XServiceManDataHelper.getAllXServiceMansBasedOnStatus(getContext(),"0");
        }

        @Override
        protected void onPostExecute(List<ExServiceMan> exServiceMEN) {
            super.onPostExecute(exServiceMEN);
            if(null!=exServiceMEN&&exServiceMEN.size()>0){
                exServiceManListAdapter.refresh(exServiceMEN);
            }
        }
    }

    class GetAllApplications extends AsyncTask<Void,Void,List<Application>>{

        @Override
        protected List<Application> doInBackground(Void... voids) {
            return ApplicationHelper.getAllApplications(getContext());
        }

        @Override
        protected void onPostExecute(List<Application> applications) {
            super.onPostExecute(applications);
            if(null!=applications&&!applications.isEmpty()){
                applicationsListAdapter.refresh(applications);
            }
        }
    }
}
