package com.techouts.pcomplaints.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techouts.pcomplaints.ExServiceManListActivity;
import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.adapters.ExServiceManListAdapter;
import com.techouts.pcomplaints.datahandler.DatabaseHandler;
import com.techouts.pcomplaints.entities.ExServiceMan;

import java.util.List;

/**
 * Created by AshokaReddy on 3/5/2018.
 */

@SuppressLint("ValidFragment")
public class ExServiceManListFragment  extends BaseFragment {

    private RecyclerView rvServiceManList;
    private ExServiceManListAdapter serviceManListAdpter;
    private int status =0;
    private List<ExServiceMan> exServiceManList;

    @SuppressLint("ValidFragment")
    public ExServiceManListFragment(int status){
        this.status = status;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ex_service_man_list,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvServiceManList = view.findViewById(R.id.rvServiceManList);
        rvServiceManList.setLayoutManager(new LinearLayoutManager(getContext()));
        serviceManListAdpter = new ExServiceManListAdapter(getContext());
        rvServiceManList.setAdapter(serviceManListAdpter);
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetExserviceManListTask().execute(status);
    }

    class GetExserviceManListTask extends AsyncTask<Integer, Void, List<ExServiceMan>> {

        @Override
        protected List<ExServiceMan> doInBackground(Integer... integers) {
            exServiceManList = DatabaseHandler.getInstance(getContext()).exServiceManDao().getAll(integers[0]);
            return exServiceManList;
        }

        @Override
        protected void onPostExecute(List<ExServiceMan> exServiceManList) {
            super.onPostExecute(exServiceManList);
            if (exServiceManList != null && exServiceManList.size() > 0) {
                serviceManListAdpter.refresh(exServiceManList);
            }
        }
    }
}
