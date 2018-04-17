package com.techouts.pcomplaints.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.model.Area;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TO-OW109 on 09-02-2018.
 */

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaViewHolder>{
    private List<Area> areaList;
    private CustomDialog.NameSelectedListener nameSelectedListener;
    private boolean isCheckBxNeed = false;
    private List<Area> tempSelect;

    public AreaAdapter(List<Area> areaList,boolean isCheckBxNeed, CustomDialog.NameSelectedListener nameSelectedListener){
        this.areaList = areaList;
        this.nameSelectedListener = nameSelectedListener;
        this.isCheckBxNeed = isCheckBxNeed;
        this.tempSelect = new ArrayList<>();
    }

    @Override
    public AreaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cell,parent,false);
        return new AreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AreaViewHolder holder, final int position) {
        holder.tvArea.setText(areaList.get(position).areaName.toString());
        if(areaList.get(position).isSelected){
            holder.cbxChecked.setChecked(true);
        }
        else{
            holder.cbxChecked.setChecked(false);
        }

        holder.cbxChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbxChecked.isChecked()) {
                    areaList.get(position).isSelected = true;
                    tempSelect.add(areaList.get(position));
                }
                else {
                    areaList.get(position).isSelected = false;
                    tempSelect.remove(areaList.get(position));
                }
            }
        });
    }

    public void refresh(List<Area> areaList){
        this.areaList = areaList;
        notifyDataSetChanged();
    }

    public List<Area> getSelectedAreas(){
        return tempSelect;
    }

    @Override
    public int getItemCount() {
        return areaList.size();
    }

    class AreaViewHolder extends RecyclerView.ViewHolder{
        TextView tvArea;
        private CheckBox cbxChecked;
        public AreaViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tvListName);
            cbxChecked = itemView.findViewById(R.id.cbxChecked);

            if(isCheckBxNeed)
                cbxChecked.setVisibility(View.VISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isCheckBxNeed) {
                        String area = tvArea.getText().toString();
                        nameSelectedListener.onNameSelected(area);
                    }
                }
            });
        }
    }
}
