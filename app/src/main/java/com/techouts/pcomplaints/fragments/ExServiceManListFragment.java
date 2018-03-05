package com.techouts.pcomplaints.fragments;

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

/**
 * Created by AshokaReddy on 3/5/2018.
 */

public class ExServiceManListFragment  extends BaseFragment {

    private RecyclerView rvServiceManList;
    private ExServiceManListAdapter serviceManListAdpter;

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
}
