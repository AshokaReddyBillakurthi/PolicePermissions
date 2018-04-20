package com.techouts.pcomplaints.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.R;
import com.techouts.pcomplaints.custom.CustomDialog;
import com.techouts.pcomplaints.model.District;
import com.techouts.pcomplaints.model.DivisionPoliceStation;

import java.util.List;

public class DivisionPoliceStationAdapter extends RecyclerView.Adapter<DivisionPoliceStationAdapter.DivisionPoliceStationViewHolder>  {

    private List<DivisionPoliceStation> divisionPoliceStationList;
    private CustomDialog.OnDivisionPoliceStation onDivisionPoliceStation;

    public DivisionPoliceStationAdapter(List<DivisionPoliceStation> divisionPoliceStationList,
                                        CustomDialog.OnDivisionPoliceStation onDivisionPoliceStation){
        this.divisionPoliceStationList = divisionPoliceStationList;
        this.onDivisionPoliceStation = onDivisionPoliceStation;
    }

    @Override
    public DivisionPoliceStationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cell,parent,false);
        return new DivisionPoliceStationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DivisionPoliceStationViewHolder holder, int position) {
        holder.tvArea.setText(divisionPoliceStationList.get(position).divisionPoliceStationName.toString());
    }

    public void refresh(List<DivisionPoliceStation> divisionPoliceStationList){
        this.divisionPoliceStationList = divisionPoliceStationList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return divisionPoliceStationList.size();
    }

    class DivisionPoliceStationViewHolder extends RecyclerView.ViewHolder{
        TextView tvArea;
        public DivisionPoliceStationViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tvListName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDivisionPoliceStation.onDivisionPoliceStation(divisionPoliceStationList.get(getAdapterPosition()));
                }
            });
        }
    }
}

