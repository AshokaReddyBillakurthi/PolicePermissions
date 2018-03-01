package com.techouts.pcomplaints.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.R;

import java.util.ArrayList;

/**
 * Created by TO-OW109 on 26-02-2018.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>  {

    private ArrayList<String> cityList;
    private OnLocationClickListener listener;

    public LocationAdapter(ArrayList<String> cityList, OnLocationClickListener listener){
        this.cityList = cityList;
        this.listener = listener;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.area_item_cell,parent,false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        holder.tvLocation.setText(cityList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder{
        TextView tvLocation;
        public LocationViewHolder(View itemView) {
            super(itemView);
            tvLocation = itemView.findViewById(R.id.tvArea);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String location = tvLocation.getText().toString();
                    listener.onLocationSelected(location);
                }
            });
        }
    }

    public interface OnLocationClickListener{
        void onLocationSelected(String location);
    }
}
