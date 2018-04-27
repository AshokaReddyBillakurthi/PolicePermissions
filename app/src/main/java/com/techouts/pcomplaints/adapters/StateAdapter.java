package com.techouts.pcomplaints.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.model.AddressModel;

import java.util.List;

/**
 * Created by TO-OW109 on 09-02-2018.
 */

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder>  {

    private List<AddressModel.State> stateList;
    private CustomDialog.OnStateSelected onStateSelected;

    public StateAdapter(List<AddressModel.State> stateList,CustomDialog.OnStateSelected onStateSelected){
        this.stateList = stateList;
        this.onStateSelected = onStateSelected;
    }

    @Override
    public StateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cell,parent,false);
        return new StateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateViewHolder holder, int position) {
        holder.tvArea.setText(stateList.get(position).getStateName().toString());
    }

    public void refresh(List<AddressModel.State> stateList){
        this.stateList = stateList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return stateList.size();
    }

    class StateViewHolder extends RecyclerView.ViewHolder{
        TextView tvArea;
        public StateViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tvListName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStateSelected.onStateSelected(stateList.get(getAdapterPosition()));
                }
            });
        }
    }
}
