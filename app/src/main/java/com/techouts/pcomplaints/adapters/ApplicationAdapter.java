package com.techouts.pcomplaints.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by TO-OW109 on 12-02-2018.
 */

public class ApplicationAdapter  extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder>{


    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ApplicationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ApplicationViewHolder extends RecyclerView.ViewHolder{

        public ApplicationViewHolder(View itemView) {
            super(itemView);
        }
    }
}
