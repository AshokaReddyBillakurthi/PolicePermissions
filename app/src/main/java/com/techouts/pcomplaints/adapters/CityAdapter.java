package com.techouts.pcomplaints.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techouts.pcomplaints.R;

import java.util.ArrayList;

/**
 * Created by TO-OW109 on 09-02-2018.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private ArrayList<String> cityList;
    private CityAdapter.OnCityClickListener listener;

    public CityAdapter(ArrayList<String> cityList, CityAdapter.OnCityClickListener listener){
        this.cityList = cityList;
        this.listener = listener;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.area_item_cell,parent,false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.tvArea.setText(cityList.get(position).toString());
    }


    @Override
    public int getItemCount() {
        return cityList.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder{
        TextView tvArea;
        public CityViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tvArea);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String area = tvArea.getText().toString();
                    listener.onCitySelected(area);
                }
            });
        }
    }

    public interface OnCityClickListener{
        void onCitySelected(String city);
    }
}
