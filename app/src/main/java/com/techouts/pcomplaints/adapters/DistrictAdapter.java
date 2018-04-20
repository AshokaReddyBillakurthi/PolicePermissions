package com.techouts.pcomplaints.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.model.District;

import java.util.List;

/**
 * Created by TO-OW109 on 09-02-2018.
 */

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder>  {

    private List<District> districtList;
    private CustomDialog.OnDistrictSelected onDistrictSelected;

    public DistrictAdapter(List<District> districtList,CustomDialog.OnDistrictSelected onDistrictSelected){
        this.districtList = districtList;
        this.onDistrictSelected = onDistrictSelected;
    }

    @Override
    public DistrictViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cell,parent,false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DistrictViewHolder holder, int position) {
        holder.tvArea.setText(districtList.get(position).districtName.toString());
    }

    public void refresh(List<District> districtList){
        this.districtList = districtList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return districtList.size();
    }

    class DistrictViewHolder extends RecyclerView.ViewHolder{
        TextView tvArea;
        public DistrictViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tvListName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDistrictSelected.onDistrictSelected(districtList.get(getAdapterPosition()));
                }
            });
        }
    }
}
