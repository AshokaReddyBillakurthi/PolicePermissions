package com.techouts.pcomplaints.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.model.SubDivision;

import java.util.List;

public class SubDivisionAdapter extends RecyclerView.Adapter<SubDivisionAdapter.SubDivisionViewHolder>  {

    private List<SubDivision> subDivisionList;
    private CustomDialog.OnSubDivisionSelected onSubDivisionSelected;

    public SubDivisionAdapter(List<SubDivision> subDivisionList,CustomDialog.OnSubDivisionSelected onSubDivisionSelected){
        this.subDivisionList = subDivisionList;
        this.onSubDivisionSelected = onSubDivisionSelected;
    }

    @Override
    public SubDivisionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cell,parent,false);
        return new SubDivisionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubDivisionViewHolder holder, int position) {
        holder.tvArea.setText(subDivisionList.get(position).subDivisionName.toString());
    }

    public void refresh(List<SubDivision> subDivisionList){
        this.subDivisionList = subDivisionList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return subDivisionList.size();
    }

    class SubDivisionViewHolder extends RecyclerView.ViewHolder{
        TextView tvArea;
        public SubDivisionViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tvListName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSubDivisionSelected.OnSubDivisionSelected(subDivisionList.get(getAdapterPosition()));
                }
            });
        }
    }
}

